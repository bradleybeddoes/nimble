/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2009 Intient Pty Ltd
 *
 *  Open Source Use - GNU Affero General Public License, version 3
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Commercial/Private Use
 *
 *  You may purchase a commercial version of this software which
 *  frees you from all restrictions of the AGPL by visiting
 *  http://intient.com/products/nimble/licenses
 *
 *  If you have purchased a commercial version of this software it is licensed
 *  to you under the terms of your agreement made with Intient Pty Ltd.
 */
package intient.nimble.controller

import intient.nimble.domain._Group
import intient.nimble.domain.User
import intient.nimble.domain.Role
import intient.nimble.domain.LevelPermission
import intient.nimble.domain.Permission
import intient.nimble.domain.Profile
import org.apache.ki.authz.annotation.RequiresRoles

/**
 * Manages Nimble groups including addition/removal of users and permissions
 *
 * @author Bradley Beddoes
 */
class GroupController {

  def groupService
  def roleService
  def permissionService

  static Map allowedMethods = [list: 'GET', show: 'GET', create: 'GET', save: 'POST', edit: 'GET', update: 'POST',
          delete: 'POST', validname: 'POST', listmembers: 'GET', addmember: 'POST',
          removemember: 'POST', searchnewmembers: 'POST', listpermissions: 'GET',
          createpermission: 'POST', removepermission: 'POST', listroles: 'GET',
          searchroles: 'POST', grantrole: 'POST', removerole: 'POST']

  def index = {
    redirect action: list
  }

  def list = {
    if (!params.max) {
      params.max = 10
    }
    return [groups: _Group.list(params)]
  }

  def show = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      redirect action: list
      return
    }
    [group: group]
  }

  def create = {
    def group = new _Group()
    [group: group]
  }

  def save = {
    def name = params.name
    def description = params.description

    def newGroup = new _Group()
    newGroup.name = name
    newGroup.description = description

    log.debug("Attempting to create new group with name '$name' and description '$description'")

    def createdGroup = groupService.createGroup(name, description, false)
    if (createdGroup.hasErrors()) {
      // Error state for non validation or save problems
      log.warn("Failure attempting to create new group with name '$name' and description '$description'")
      render view: 'create', model: [group: createdGroup]
      return
    }
    else {
      log.info("Created new group with name '$name' and description '$description'")

      flash.type = "success"
      flash.message = "New group created"
      redirect action: show, params: [id: createdGroup.id]
      return
    }
  }

  def edit = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      redirect action: list
      return
    }

    [group: group]
  }

  def update = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      redirect action: list
      return
    }

    group.properties['name', 'description'] = params

    if (group.validate()) {
      def updatedGroup = groupService.updateGroup(group)
      if (updatedGroup.hasErrors()) {
        log.warn("Attempt to update group [$group.id]$group.name failed")
        render view: 'edit', model: [group: updateGroup]
        return
      }
      else {
        log.info("Attempt to update group [$group.id]$group.name succeeded")

        flash.type = "success"
        flash.message = "Group successfully updated"
        redirect action: show, params: [id: updatedGroup.id]
        return
      }
    }

    render view: 'edit', model: [group: group]
    return
  }

  def delete = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group to be deleted could not be found"
      redirect action: list
      return
    }

    groupService.deleteGroup(group)

    flash.type = "success"
    flash.message = "Group was deleted"
    redirect action: list
    return
  }

  def validname = {
    def groups = _Group.findAllByName(params?.name)

    if (groups != null && groups.size() > 0) {
      flash.type = "error"
      flash.message = "Group already exists for ${params.name}"
      response.sendError(500)
      return
    }

    render "valid"
  }

  def listmembers = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing members of group $group.name")
    render(template: '/templates/admin/members_list', contextPath: pluginContextPath, model: [users: group.users, protect: group.protect, groupmembers: false])
  }

  def addmember = {
    def group = _Group.get(params.id)
    def user = User.get(params.userID)

    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate group'
      return
    }

    if (!user) {
      log.warn("User identified by id '$params.userID' was not located")

      response.sendError(500)
      render 'Unable to locate user'
      return
    }
    groupService.addMember(user, group)

    log.info("Added user [$user.id]$user.username to group $group.name")
    render 'Success'
    return
  }

  def removemember = {
    def group = _Group.get(params.id)
    def user = User.get(params.userID)

    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate group'
      return
    }

    if (!user) {
      log.warn("User identified by id '$params.userID' was not located")

      response.sendError(500)
      render 'Unable to locate user'
      return
    }

    groupService.deleteMember(user, group)

    log.info("Removed user [$user.id]$user.username from group $group.name")
    render 'Success'
    return
  }

  def searchnewmembers = {
    def q = "%" + params.q + "%"

    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate group'
      return
    }

    log.debug("Performing search for users matching $q")

    def users = User.findAllByUsernameIlike(q)
    def profiles = Profile.findAllByFullNameIlikeOrEmailIlike(q, q)
    def nonMembers = []

    users.each {
      if (!it.groups.contains(group)) {
        nonMembers.add(it)
        log.debug("Adding user identified as [$it.id]$it.username to search results")
      }
    }
    profiles.each {
      if (!it.owner.roles.contains(group) && !nonMembers.contains(it.owner)) {
        nonMembers.add(it.owner)
        log.debug("Adding user identified as [$it.owner.id]$it.owner.username based on profile to search results")
      }
    }

    log.info("Search for new group members complete, returning $nonMembers.size records")
    render(template: '/templates/admin/members_search', contextPath: pluginContextPath, model: [users: nonMembers])
  }

  def listpermissions = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.message = "Group not found with id $params.id"
      response.sendError(500)
      return
    }

    render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: group.permissions, ownerID: group.id])
  }

  def createpermission = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.message = "Group not found with id $params.id"
      render "Group not found with id $params.id"
      response.status = 500
      return
    }

    LevelPermission permission = new LevelPermission()
    permission.populate(params.first, params.second, params.third, params.fourth, params.fifth, params.sixth)
    permission.managed = false

    if (permission.hasErrors()) {
      log.debug("Unable to create new permission for group [$group.id]$group.name, permission details are invalid")
      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
      response.status = 500
      return
    }

    def savedPermission = permissionService.createPermission(permission, group)
    if (savedPermission.hasErrors()) {
      log.warn("Unable to create new permission for group [$group.id]$group.name")
      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: savedPermission])
      response.status = 500
      return
    }

    log.info("Created new permission $savedPermission.id for group [$group.id]$group.name")
    render "success"
    return
  }

  def removepermission = {
    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.message = "Group not found with id $params.id"
      render "Group not found with id $params.id"
      response.status = 500
      return
    }

    def permission = Permission.get(params.permID)
    if (!permission) {
      flash.message = "Permission not found with id $params.permID"
      render "Permission not found with id $params.permID"
      response.status = 500
      return
    }

    permissionService.deletePermission(permission)

    log.info("Removed permission $permission.id for group [$group.id]$group.name")
    render "success"
    return
  }

  def listroles = {

    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      response.sendError(500)
      return
    }

    render(template: '/templates/admin/roles_list', contextPath: pluginContextPath, model: [roles: group.roles, ownerID: group.id])
  }

  def searchroles = {
    def q = "%" + params.q + "%"

    def group = _Group.get(params.id)
    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate group'
      return
    }

    log.debug("Performing search for roles matching $q")

    def roles = Role.findAllByNameIlikeOrDescriptionIlike(q, q)
    def respRoles = []


    roles.each {
      if (!group.roles.contains(it) && !it.protect)
        respRoles.add(it)    // Eject already assigned roles for this group
    }

    log.info("Search for new group roles complete, returning $respRoles.size records")
    render(template: '/templates/admin/roles_search', contextPath: pluginContextPath, model: [roles: respRoles, ownerID: group.id])
    return
  }

  def grantrole = {
    def group = _Group.get(params.id)
    def role = Role.get(params.roleID)

    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      response.sendError(500)
      return
    }

    if (!role) {
      log.warn("Role identified by id '$roleID.id' was not located")

      flash.type = "error"
      flash.message = "Role not found with id $params.roleID"
      response.sendError(500)
      return
    }

    if (role.protect) {
      log.warn("Role [$roleID.id]$role.name is protected and can not be modified via the web interface")

      flash.type = "error"
      flash.message = "Unable to grant role $role.name to group $group.name via web interface, role is protected"
      response.sendError(500)
      return
    }

    roleService.addGroupMember(group, role)

    log.info("Granted role [$role.id]$role.name to group [$group.id]$group.name")
    render "success"
    return
  }

  def removerole = {
    def group = _Group.get(params.id)
    def role = Role.get(params.roleID)

    if (!group) {
      log.warn("Group identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Group not found with id $params.id"
      response.sendError(500)
      return
    }

    if (!role) {
      log.warn("Role identified by id '$roleID.id' was not located")

      flash.type = "error"
      flash.message = "Role not found with id $params.roleID"
      response.sendError(500)
      return
    }

    if (role.protect) {
      log.warn("Role [$roleID.id]$role.name is protected and can not be modified via the web interface")
      
      flash.type = "error"
      flash.message = "Unable to remove role with id $params.roleID from group with id $params.id via web interface, role is protected"
      response.sendError(500)
      return
    }

    roleService.deleteGroupMember(group, role)

    log.info("Removed role [$role.id]$role.name to group [$group.id]$group.name")
    render "success"
    return
  }
}
