/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2009 Intient Pty Ltd
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package intient.nimble.core

import org.apache.shiro.authz.annotation.RequiresRoles

/**
 * Manages Nimble groups including addition/removal of users and permissions
 *
 * @author Bradley Beddoes
 */
class GroupController {

  def groupService
  def roleService
  def permissionService

  static Map allowedMethods = [	save: 'POST', update: 'POST', delete: 'POST', validname: 'POST', addmember: 'POST', removemember: 'POST', searchnewmembers: 'POST', 
								createpermission: 'POST', removepermission: 'POST', searchroles: 'POST', grantrole: 'POST', removerole: 'POST']

  def index = {
    redirect action: list
  }

  def list = {
    if (!params.max) {
      params.max = 10
    }
    [groups: Group.list(params)]
  }

  def show = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = message(code: 'nimble.group.nonexistant', args: [params.id])
      redirect action: list
    }
	else
    	[group: group]
  }

  def create = {
    def group = new Group()
    [group: group]
  }

  def save = {
    def name = params.name
    def description = params.description

	log.debug("Attempting to create new group with name '$name' and description '$description'")
    def createdGroup = groupService.createGroup(name, description, false)
    if (createdGroup.hasErrors()) {
      	// Error state for non validation or save problems
      	log.warn("Failure attempting to create new group with name '$name' and description '$description'")
		flash.type = "error"
	  	flash.message = message(code: 'nimble.group.create.error', args: [createdGroup.name])
      	render view: 'create', model: [group: createdGroup]
    }
	else {
    	log.info("Created new group with name '$name' and description '$description'")
	    flash.type = "success"
	    flash.message = message(code: 'nimble.group.create.success', args: [createdGroup.name])
	    redirect action: show, params: [id: createdGroup.id]
	}
  }

  def edit = {
    def group = Group.get(params.id)
    if (!group) {
      	log.warn("Group identified by id '$params.id' was not located")
      	flash.type = "error"
      	flash.message = message(code: 'nimble.group.nonexistant', args: [params.id])
      	redirect action: list
    }
	else
    	[group: group]
  }

  def update = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      flash.type = "error"
      flash.message = message(code: 'nimble.group.nonexistant', args: [params.id])
      redirect action: list
    }
	else {
    	group.properties['name', 'description'] = params
	    if (!group.validate()) {
			log.warn("Attempt to update group [$group.id]$group.name failed")
			flash.type = "error"
		    flash.message = message(code: 'nimble.group.update.error', args: [updatedGroup.name])
			render view: 'edit', model: [group: group]
		}
		else {
	    	def updatedGroup = groupService.updateGroup(group)
		    if (updatedGroup.hasErrors()) {
		       log.warn("Attempt to update group [$group.id]$group.name failed")
			   flash.type = "error"
		       flash.message = message(code: 'nimble.group.update.error', args: [updatedGroup.name])
		       render view: 'edit', model: [group: updatedGroup]
		     }
		     else {
		       log.info("Attempt to update group [$group.id]$group.name succeeded")
		       flash.type = "success"
		       flash.message = message(code: 'nimble.group.update.success', args: [updatedGroup.name])
		       redirect action: show, params: [id: updatedGroup.id]
		     }
		}
	}
  }

  def delete = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      flash.type = "error"
      flash.message = message(code: 'nimble.group.nonexistant', args: [params.id])
      redirect action: list
    }
	else {
    	groupService.deleteGroup(group)
	    flash.type = "success"
	    flash.message = message(code: 'nimble.group.delete.success', args: [params.id])
	    redirect action: list
	}
  }

  def validname = {
	if (params?.val == null || params.val.length() < 4) {
      render message(code: 'nimble.group.name.invalid', args: [params.val])
      response.sendError(500)
    }
	else {
    	def groups = Group.findAllByName(params?.val)
	    if (groups != null && groups.size() > 0) {
		  render message(code: 'nimble.group.name.invalid', args: [params.val])
	      response.sendError(500)
	    }
		else
	    	render message(code: 'nimble.group.name.valid', args: [params.val])
	}
  }

  def listmembers = {
    def group = Group.get(params.id)
    if (!group) {
      	log.warn("Group identified by id '$params.id' was not located")
	    render message(code: 'nimble.group.nonexistant', args: [params.id])
	    response.sendError(500)
    }
	else {
    	log.debug("Listing members of group $group.name")
	    render(template: '/templates/admin/members_list', contextPath: pluginContextPath, model: [parent: group, users: group.users, protect: group.protect, groupmembers: false])
	}
  }

  def addmember = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
	  response.sendError(500)
    }
	else {
		def user = UserBase.get(params.userID)
    	if (!user) {
	      log.warn("User identified by id '$params.userID' was not located")
		  render message(code: 'nimble.user.nonexistant', args: [params.id])
	      response.sendError(500)
	    }
		else {
	    	groupService.addMember(user, group)
		    log.info("Added user [$user.id]$user.username to group $group.name")
		    render message(code: 'nimble.group.addmember.success', args: [group.id, user.id])
    	}
	}
  }

  def removemember = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
	  render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else {
		def user = UserBase.get(params.userID)
    	if (!user) {
			log.warn("User identified by id '$params.userID' was not located")
			render message(code: 'nimble.user.nonexistant', args: [params.id])
			response.sendError(500)
	    }
		else {
	    	groupService.deleteMember(user, group)
		    log.info("Removed user [$user.id]$user.username from group $group.name")
		    render message(code: 'nimble.group.removemember.success', args: [group.id, user.id])
	    }
	}
  }

  def searchnewmembers = {
    def q = "%" + params.q + "%"

    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
	  render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else {
    	log.debug("Performing search for users matching $q")

	    def users = UserBase.findAllByUsernameIlike(q)
	    def profiles = ProfileBase.findAllByFullNameIlikeOrEmailIlike(q, q)
	    def nonMembers = []

	    users?.each {
	      if (!it.groups.contains(group)) {
	        nonMembers.add(it)
	        log.debug("Adding user identified as [$it.id]$it.username to search results")
	      }
	    }
	    profiles.each {
	      if (!it.owner.groups.contains(group) && !nonMembers.contains(it.owner)) {
	        nonMembers.add(it.owner)
	        log.debug("Adding user identified as [$it.owner.id]$it.owner.username based on profile to search results")
	      }
	    }
	    log.info("Search for new group members complete, returning $nonMembers.size records")
	    render(template: '/templates/admin/members_search', contextPath: pluginContextPath, model: [parent: group, users: nonMembers])
	}
  }

  def listpermissions = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else
    	render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: group.permissions, parent: group])
  }

  def createpermission = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	LevelPermission permission = new LevelPermission()
	    permission.populate(params.first, params.second, params.third, params.fourth, params.fifth, params.sixth)
	    permission.managed = false

	    if (permission.hasErrors()) {
	      log.debug("Unable to create new permission for group [$group.id]$group.name, permission details are invalid")
	      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
	      response.status = 500
	    }
		else {
	    	def savedPermission = permissionService.createPermission(permission, group)
		    if (savedPermission.hasErrors()) {
		      log.warn("Unable to create new permission for group [$group.id]$group.name")
		      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: savedPermission])
		      response.status = 500
		    }
			else {
		    	log.info("Created new permission $savedPermission.id for group [$group.id]$group.name")
			    render message(code: 'nimble.permission.create.success', args: [group.name])
			}
		}
    }
  }

  def removepermission = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	def permission = Permission.get(params.permID)
	    if (!permission) {
	      render message(code: 'nimble.permission.nonexistant', args: [params.permID])
	      response.status = 500
	    }
		else {
	    	permissionService.deletePermission(permission)
		    log.info("Removed permission $permission.id for group [$group.id]$group.name")
		    render message(code: 'nimble.permission.remove.success', args: [group.name])
	    }
	}
  }

  def listroles = {

    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' wass not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else
    	render(template: '/templates/admin/roles_list', contextPath: pluginContextPath, model: [roles: group.roles, ownerID: group.id])
  }

  def searchroles = {
    def q = "%" + params.q + "%"

    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
	  render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else {
    	log.debug("Performing search for roles matching $q")
	    def roles = Role.findAllByNameIlikeOrDescriptionIlike(q, q)
	    def respRoles = []
	    roles.each {
	      if (!group.roles.contains(it) && !it.protect)
	        respRoles.add(it)    // Eject already assigned roles for this group
	    }

	    log.info("Search for new group roles complete, returning $respRoles.size records")
	    render(template: '/templates/admin/roles_search', contextPath: pluginContextPath, model: [roles: respRoles, ownerID: group.id])
    }
  }

  def grantrole = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else {
		def role = Role.get(params.roleID)
	    if (!role) {
	      log.warn("Role identified by id '$roleID.id' was not located")
	      render message(code: 'nimble.role.nonexistant', args: [params.roleID])
	      response.sendError(500)
	    }
		else {
	    	if (role.protect) {
		      log.warn("Role [$roleID.id]$role.name is protected and can not be modified via the web interface")
		      render message(code: 'nimble.role.addmember.protected', args: [group.name, role.name])
		      response.sendError(500)
		    }
			else {
		    	roleService.addGroupMember(group, role)
			    log.info("Granted role [$role.id]$role.name to group [$group.id]$group.name")
			    render message(code: 'nimble.role.addmember.success', args: [role.name, group.name])
			}
		}
    }
  }

  def removerole = {
    def group = Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")
      render message(code: 'nimble.group.nonexistant', args: [params.id])
      response.sendError(500)
    }
	else {
		def role = Role.get(params.roleID)
	    if (!role) {
	      log.warn("Role identified by id '$roleID.id' was not located")
	      render message(code: 'nimble.role.nonexistant', args: [params.roleID])
	      response.sendError(500)
	    }
		else {
	    	if (role.protect) {
		      log.warn("Role [$roleID.id]$role.name is protected and can not be modified via the web interface")
		      render message(code: 'nimble.role.removemember.protected', args: [group.name, role.name])
		      response.sendError(500)
		    }
			else {
		    	roleService.deleteGroupMember(group, role)
			    log.info("Removed role [$role.id]$role.name to group [$group.id]$group.name")
			    render message(code: 'nimble.role.removemember.success', args: [role.name, group.name])
			}
		}
    }
  }
}
