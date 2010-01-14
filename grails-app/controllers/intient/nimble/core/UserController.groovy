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

import intient.nimble.InstanceGenerator

/**
 * Manages Nimble user accounts
 *
 * @author Bradley Beddoes
 */
class UserController {

  static Map allowedMethods = [	save: 'POST', update: 'POST', enable: 'POST', disable: 'POST', enableapi: 'POST', disableapi: 'POST',
          						savepassword: 'POST', validusername: 'POST', searchgroups: 'POST', grantgroup: 'POST', removegroup: 'POST',
 								createpermission: 'POST', removepermisson: 'POST', searchroles: 'POST', grantrole: 'POST', removerole: 'POST']

  def userService
  def groupService
  def roleService
  def permissionService

  def index = {
    redirect action: list, params: params
  }

  def list = {
    if (!params.max) {
      params.max = 10
    }
    log.debug("Listing users")
    [users: UserBase.list(params)]
  }

  def show = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      flash.type = "error"
      flash.message = message(code: 'nimble.user.nonexistant', args: [params.id])
      redirect action: list
    }
	else {
    	log.debug("Showing user [$user.id]$user.username")
	    [user: user]
	}
  }

  def edit = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = message(code: 'nimble.user.nonexistant', args: [params.id])
      redirect action: list
    }
	else {
    	log.debug("Editing user [$user.id]$user.username")
	    [user: user]
	}
  }

  def update = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      flash.type = "error"
      flash.message = message(code: 'nimble.user.nonexistant', args: [params.id])
      redirect action: edit, id: params.id
    }
	else {
    	user.properties['username', 'external', 'federated'] = params
	    if (!user.validate()) {
	    	log.debug("Updated details for user [$user.id]$user.username are invalid")
		    render view: 'edit', model: [user: user]
	    } 
		else {
	      	def updatedUser = userService.updateUser(user)
	        log.info("Successfully updated details for user [$user.id]$user.username")
	        flash.type = "success"
	        flash.message = message(code: 'nimble.user.update.success', args: [user.username])
	        redirect action: show, id: updatedUser.id
	    }
	}
  }

  def create = {
    def user = InstanceGenerator.user()
    user.profile = InstanceGenerator.profile()
    log.debug("Starting user creation process")
    [user: user]
  }

  def save = {
    def user = InstanceGenerator.user()
    user.profile = InstanceGenerator.profile()
    user.properties['username', 'pass', 'passConfirm'] = params
    user.profile.properties['fullName', 'email'] = params
    user.enabled = false
    user.external = false

    def savedUser = userService.createUser(user)
    if (savedUser.hasErrors()) {
      log.info("Failed to save new user")
      render view: 'create', model: [roleList: Role.list(), user: user]
    }
	else {
    	log.info("Successfully created new user [$savedUser.id]$savedUser.username")
	    redirect action: show, id: user.id
    }
  }

  def changepassword = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      flash.type = "error"
      flash.message = message(code: 'nimble.user.nonexistant', args: [params.id])
      redirect action: list
    }
	else {
    	if (user.external) {
	      log.warn("Attempt to change password on user [$user.id]$user.username that is externally managed denied")
	      flash.type = "error"
	      flash.message = message(code: 'nimble.user.password.external.nochange', args: [user.username])
	      redirect action: show, id: user.id
	    }
		else {
	    	log.debug("Starting password change for user [$user.id]$user.username")
		    [user: user]
		}
	}
  }

  def changelocalpassword = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
	    flash.type = "error"
		flash.message = message(code: 'nimble.user.nonexistant', args: [params.id])
		redirect action: list
    }
	else {
		if (!user.external) {
			log.warn("Attempt to change password on user [$user.id]$user.username that is externally managed denied")
			flash.type = "error"
			flash.message = message(code: 'nimble.user.password.internal.nochange', args: [user.username])
			redirect action: show, id: user.id
	    }
		else {
    		log.debug("Starting local password change for user [$user.id]$user.username")
		    [user: user]
		}
	}
  }

  def savepassword = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      flash.type = "error"
      flash.message = message(code: 'nimble.user.nonexistant', args: [params.id])
      redirect action: list
    }
	else {
    	user.properties['pass', 'passConfirm'] = params
	    if (!user.validate() || !userService.validatePass(user, true)) {
			log.debug("Password change for [$user.id]$user.username was invalid")
		    render view: 'changepassword', model: [user: user]
		}
		else {
	      	def savedUser = userService.changePassword(user)
	        log.info("Successfully saved password change for user [$user.id]$user.username")
	        flash.type = "success"
	        flash.message = message(code: 'nimble.user.password.change.success', args: [params.id])
	        redirect action: show, id: user.id
	    }
	}
  }

  // AJAX related actions
  def enable = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	def enabledUser = userService.enableUser(user)
	    log.info("Enabled user [$user.id]$user.username")
	    render message(code: 'nimble.user.enable.success', args: [user.username])
	}
  }

  def disable = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render = message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	def disabledUser = userService.disableUser(user)
	    log.info("Disabled user [$user.id]$user.username")
	    render message(code: 'nimble.user.disable.success', args: [user.username])
    }
  }

  def enableapi = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
	  message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	def enabledUser = userService.enableRemoteApi(user)
	    log.info("Enabled remote api for user [$user.id]$user.username")
	    render message(code: 'nimble.user.enableapi.success', args: [user.username])
    }
  }

  def disableapi = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
		message(code: 'nimble.user.nonexistant', args: [params.id])
	    response.status = 500
    }
	else {
    	def disabledUser = userService.disableRemoteApi(user)
	    log.info("Disabled remote api for user [$user.id]$user.username")
	    render message(code: 'nimble.user.disableapi.success', args: [user.username])
    }
  }

  def validusername = {
    if (params?.val == null || params?.val?.length() < 4) {
      render message(code: 'nimble.user.username.invalid')
      response.status = 500
    }
	else {
    	def users = UserBase.findAllByUsername(params?.val) 
	    if (users != null && users.size() > 0) {
	      render message(code: 'nimble.user.username.invalid')
	      response.status = 500
	    }
		else {
	    	render message(code: 'nimble.user.username.valid')
		}
	}
  }

  def listlogins = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	log.debug("Listing login events for user [$user.id]$user.username")
	    def c = LoginRecord.createCriteria()
	    def logins = c.list {
	      	eq("owner", user)
		    order("dateCreated")
		    maxResults(20)
	    }
	    render(template: '/templates/admin/logins_list', contextPath: pluginContextPath, model: [logins: logins, ownerID: user.id])
	}
  }

  def listgroups = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
	    render message(code: 'nimble.user.nonexistant', args: [params.id])
	    response.status = 500
    }
	else {
    	log.debug("Listing groups user [$user.id]$user.username is a member of")
	    render(template: '/templates/admin/groups_list', contextPath: pluginContextPath, model: [groups: user.groups, ownerID: user.id])
	}
  }

  def searchgroups = {
	def q = "%" + params.q + "%"
    log.debug("Performing search for groups matching $q")
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	def groups = Group.findAllByNameIlike(q)
	    def nonMembers = []
	    groups.each {
	      if (!it.users.contains(user) && !it.protect) {
	        nonMembers.add(it)    // Eject groups user is already a part of
	        log.debug("Adding group identified as [$it.id]$it.name to search results")
	      }
	    }
	    log.info("Search for new groups user [$user.id]$user.username can join complete, returning $nonMembers.size records")
	    render(template: '/templates/admin/groups_search', contextPath: pluginContextPath, model: [groups: nonMembers, ownerID: user.id])
	}
  }

  def grantgroup = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
		def group = Group.get(params.groupID)
    	if (!group) {
	      log.warn("Group identified by id '$params.groupID' was not located")
	      render message(code: 'nimble.group.nonexistant', args: [params.groupID])
	      response.status = 500
	    }
		else {
	    	if (group.protect) {
		      	log.warn("Can't add user [$user.id]$user.username to group [$group.id]$group.name as group is protected")
		      	render message(code: 'nimble.group.protected.no.modification', args: [group.name, user.username])
		      	response.status = 500
		    }
			else {
		    	groupService.addMember(user, group)
			    log.info("Added user [$user.id]$user.username to group [$group.id]$group.name")
			    render message(code: 'nimble.group.addmember.success', args: [group.name, user.username])
			}
		}
    }
  }

  def removegroup = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
		def group = Group.get(params.groupID)
    	if (!group) {
	      log.warn("Group identified by id '$params.groupID' was not located")
	      render message(code: 'nimble.group.nonexistant', args: [params.groupID])
	      response.status = 500
	    }
		else {
	    	if (group.protect) {
		      	log.warn("Can't remove user [$user.id]$user.username from group [$group.id]$group.name as group is protected")
				render message(code: 'nimble.group.protected.no.modification', args: [group.name, user.username])
		      	response.status = 500
		    }
			else {
		    	groupService.deleteMember(user, group)
			    log.info("Removed user [$user.id]$user.username from group [$group.id]$group.name")
			    render message(code: 'nimble.group.removemember.success', args: [group.name, user.username])
			}
		}
    }
  }

  def listpermissions = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
	    render message(code: 'nimble.user.nonexistant', args: [params.id])
	    response.status = 500
    }
	else {
    	log.debug("Listing permissions user [$user.id]$user.username is granted")
	    render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: user.permissions, parent: user])
	}
  }

  def createpermission = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
      	render message(code: 'nimble.user.nonexistant', args: [params.id])
      	response.status = 500
    }
	else {
    	LevelPermission permission = new LevelPermission()
	    permission.populate(params.first, params.second, params.third, params.fourth, params.fifth, params.sixth)
	    permission.managed = false

	    if (permission.hasErrors()) {
	      	log.warn("Creating new permission for user [$user.id]$user.username failed, permission is invalid")
	      	render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
	      	response.status = 500
	    }
		else {
	    	def savedPermission = permissionService.createPermission(permission, user)
		    log.info("Creating new permission for user [$user.id]$user.username succeeded")
			render message(code: 'nimble.permission.create.success', args: [user.username])
		}
    }
  }

  def removepermission = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
      	render message(code: 'nimble.user.nonexistant', args: [params.id])
      	response.status = 500
    }
	else {
    	def permission = Permission.get(params.permID)
	    if (!permission) {
	      	log.warn("Permission identified by id '$params.permID' was not located")
	      	render message(code: 'nimble.permission.nonexistant', args: [params.permID])
	      	response.status = 500
	    }
		else {
	    	permissionService.deletePermission(permission)
		    log.info("Removing permission [$permission.id] from user [$user.id]$user.username succeeded")
		    render message(code: 'nimble.permission.remove.success', args: [user.username])
		}
    }
  }

  def listroles = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
      	render message(code: 'nimble.user.nonexistant', args: [params.id])
      	response.status = 500
    }
	else {
    	log.debug("Listing roles user [$user.id]$user.username is granted")
    	render(template: '/templates/admin/roles_list', contextPath: pluginContextPath, model: [roles: user.roles, ownerID: user.id])
	}
  }

  def searchroles = {
    def q = "%" + params.q + "%"
    log.debug("Performing search for roles matching $q")
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
    	def roles = Role.findAllByNameIlikeOrDescriptionIlike(q, q, false)
	    def respRoles = []
	    roles.each {
	      if (!user.roles.contains(it) && !it.protect) {
	        respRoles.add(it)    // Eject already assigned roles for this user
	        log.debug("Adding role identified as [$it.id]$it.name to search results")
	      }
	    }
	    log.info("Search for new roles user [$user.id]$user.username can be assigned complete, returning $respRoles.size records")
	    render(template: '/templates/admin/roles_search', contextPath: pluginContextPath, model: [roles: respRoles, ownerID: user.id])
    }
  }

  def grantrole = {
    def user = UserBase.get(params.id)
    if (!user) {
      	log.warn("User identified by id '$params.id' was not located")
	    render message(code: 'nimble.user.nonexistant', args: [params.id])
	    response.status = 500
    }
	else {
		def role = Role.get(params.roleID)
    	if (!role) {
	      log.warn("Role identified by id '$params.roleID' was not located")
	      render message(code: 'nimble.role.nonexistant', args: [params.roleID])
	      response.status = 500
	    }
		else {
	    	if (role.protect) {
		      	log.warn("Can't assign user [$user.id]$user.username role [$role.id]$role.name as role is protected")
			    render message(code: 'nimble.role.protected.no.modification', args: [role.name, user.username])
			    response.status = 500
		    }
			else {
		    	roleService.addMember(user, role)
			    log.info("Assigned user [$user.id]$user.username role [$role.id]$role.name")
			    render message(code: 'nimble.role.addmember.success', args: [role.name, user.username])
			}
		}
    }
  }

  def removerole = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.status = 500
    }
	else {
		def role = Role.get(params.roleID)
    	if (!role) {
	      log.warn("Role identified by id '$params.roleID' was not located")
	      render message(code: 'nimble.role.nonexistant', args: [params.roleID])
	      response.status = 500
	    }
		else {
	    	if (role.protect) {
		      	log.warn("Can't assign user [$user.id]$user.username role [$role.id]$role.name as role is protected")
			    render message(code: 'nimble.role.protected.no.modification', args: [role.name, user.username])
			    response.status = 500
		    }
			else {
		    	roleService.deleteMember(user, role)
			    log.info("Removed user [$user.id]$user.username from role [$role.id]$role.name")
			    render message(code: 'nimble.role.removemember.success', args: [role.name, user.username])
			}
		}
    }
  }

}

