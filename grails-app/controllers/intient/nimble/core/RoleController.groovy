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

/**
 * Manages Nimble roles including addition/removal of users, groups and permissions
 *
 * @author Bradley Beddoes
 */
class RoleController {

  def permissionService
  def roleService

  static Map allowedMethods = [	validname: 'POST', save: 'POST', update: 'POST', delete: 'POST', addmember: 'POST', removemember: 'POST', addgroupmember: 'POST', 
								removegroupmember: 'POST', searchnewmembers: 'POST', searchnewgroupmembers: 'POST', createpermission: 'POST', removepermission: 'POST'	]

  def index = {
    redirect action: list
  }

  def list = {
    if (!params.max) {
      params.max = 10
    }
    return [roles: Role.list(params)]
  }

  def show = {
    def role = Role.get(params.id)
    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Role not found with id $params.id"
      redirect action: list
      return
    }
    [role: role]
  }

  def create = {
    def role = new Role()
    [role: role]
  }

  def validname = {
    if (params?.val == null || params.val.length() < 4) {
      flash.message = "Role name is invalid"
      response.sendError(500)
      return
    }

    def roles = Role.findAllByName(params?.val)

    if (roles != null && roles.size() > 0) {
      flash.type = "error"
      flash.message = "Role already exists for ${params.name}"
      response.sendError(500)
      return
    }

    render "valid"
    return
  }

  def save = {
    def name = params.name
    def description = params.description

    def createdRole = roleService.createRole(name, description, false)
    if (createdRole.hasErrors()) {

      log.error("Unable to create new role $name with description $description")

      render view: 'create', model: [role: createdRole]
      return
    }
    else {
      log.info("Created new role $name with description $description")

      flash.type = "success"
      flash.message = "New role created"
      redirect action: show, params: [id: createdRole.id]
      return
    }
  }

  def edit = {
    def role = Role.get(params.id)
    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Role not found with id $params.id"
      redirect action: list
      return
    }

    log.debug("Editing role $role.name")
    [role: role]
  }

  def update = {
    def role = Role.get(params.id)
    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Role not found with id $params.id"
      redirect action: list
      return
    }

    if (role.protect) {
      log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")

      flash.type = "error"
      flash.message = "Role with id $params.id is protected update denied"
      redirect action: list
      return
    }

    role.properties['name', 'description'] = params

    if (role.validate()) {
      def updatedRole = roleService.updateRole(role)
      if (!updatedRole.hasErrors()) {
        log.info("Updated role $updatedRole.name with description $updatedRole.description")

        flash.type = "success"
        flash.message = "Role successfully updated"
        redirect action: show, params: [id: updatedRole.id]
        return
      }
    }

    log.debug("Submitted values for role are invalid")
    render(view: 'edit', model: [role: role])
  }

  def delete = {
    def role = Role.get(params.id)

    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Role to be deleted could not be found"
      redirect action: list
      return
    }

    if (role.protect) {
      log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")

      flash.type = "error"
      flash.message = "Role with id $params.id is protected delete denied"
      redirect action: list
      return
    }

    roleService.deleteRole(role)

    log.info("Deleted role $role.name with description $role.description")
    flash.type = "success"
    flash.message = "Role was deleted"
    redirect action: list
    return
  }

  def listmembers = {
    def role = Role.get(params.id)
    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "Role not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing role members")
    render(template: '/templates/admin/members_list', contextPath: pluginContextPath, model: [users: role.users, groups: role.groups, protect: role.protect, groupmembers: true])
  }

  def addmember = {
    def role = Role.get(params.id)
    def user = UserBase.get(params.userID)

    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate role'
      return
    }

    if (!user) {
      log.warn("Uer identified by id '$params.userID' was not located")

      response.sendError(500)
      render 'Unable to locate user'
      return
    }

    roleService.addMember(user, role)

    log.info("Added user [$user.id]$user.username to role [$role.id]$role.name")

    render 'Success'
    return
  }

  def removemember = {
    def role = Role.get(params.id)
    def user = UserBase.get(params.userID)

    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate role'
      return
    }

    if (!user) {
      log.warn("user identified by id '$params.userID' was not located")

      response.sendError(500)
      render 'Unable to locate user'
      return
    }

    roleService.deleteMember(user, role)

    log.info("Removed user [$user.id]$user.username from role [$role.id]$role.name")

    render 'Success'
    return

  }

  def addgroupmember = {
    def role = Role.get(params.id)
    def group = Group.get(params.groupID)

    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate role'
      return
    }

    if (!group) {
      log.warn("Group identified by id '$params.groupID' was not located")

      response.sendError(500)
      render 'Unable to locate group'
      return
    }

    roleService.addGroupMember(group, role)

    log.info("Added group [$group.id]$group.name to role [$role.id]$role.name")

    render 'Success'
    return
  }

  def removegroupmember = {
    def role = Role.get(params.id)
    def group = Group.get(params.groupID)

    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      response.sendError(500)
      render 'Unable to locate role'
      return
    }

    if (!group) {
      log.warn("Group identified by id '$params.groupID' was not located")

      response.sendError(500)
      render 'Unable to locate group'
      return
    }

    roleService.deleteGroupMember(group, role)

    log.info("Removed group [$group.id]$group.name from role [$role.id]$role.name")

    render 'Success'
    return
  }

  def searchnewmembers = {
    def q = "%" + params.q + "%"
    log.debug("Performing search for users matching $q")

    def users = UserBase.findAllByUsernameIlike(q)
    def profiles = ProfileBase.findAllByFullNameIlikeOrEmailIlike(q, q)
    def nonMembers = []

    def role = Role.get(params.id)
    users.each {
      if (!it.roles.contains(role)) {
        nonMembers.add(it)
        log.debug("Adding user identified as [$it.id]$it.username to search results")
      }
    }
    profiles.each {
      if (!it.owner.roles.contains(role) && !nonMembers.contains(it.owner)) {
        nonMembers.add(it.owner)
        log.debug("Adding user identified as [$it.owner.id]$it.owner.username based on profile to search results")
      }
    }

    log.info("Search for new role user members complete, returning $nonMembers.size records")
    render(template: '/templates/admin/members_search', contextPath: pluginContextPath, model: [users: nonMembers])
  }

  def searchnewgroupmembers = {
    def q = "%" + params.q + "%"
    log.debug("Performing search for groups matching $q")

    def groups = Group.findAllByNameIlike(q)
    def nonMembers = []

    def role = Role.get(params.id)
    groups.each {
      if (!it.roles.contains(role))  {
        nonMembers.add(it)    // Eject users that are already admins
        log.debug("Adding group identified as [$it.id]$it.name to search results")
      }
    }

    log.info("Search for new role group members complete, returning $nonMembers.size records")
    render(template: '/templates/admin/members_group_search', contextPath: pluginContextPath, model: [groups: nonMembers])
  }

  def listpermissions = {

    def role = Role.get(params.id)
    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.message = "Role not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing permissions for role [$role.id]$role.name")
    render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: role.permissions, ownerID: role.id])
  }

  def createpermission = {
    def role = Role.get(params.id)
    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")
      flash.message = "Role not found with id $params.id"
      render "Role not found with id $params.id"
      response.status = 500
      return
    }

    LevelPermission permission = new LevelPermission()
    permission.populate(params.first, params.second, params.third, params.fourth, params.fifth, params.sixth)
    permission.managed = false

    if (permission.hasErrors()) {
      log.debug("Submitted permission was not valid")

      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
      response.status = 500
      return
    }

    def savedPermission = permissionService.createPermission(permission, role)
    if (savedPermission.hasErrors()) {
      log.warn("Submitted permission was unable to be assigned to role [$role.id]$role.name")

      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: savedPermission])
      response.status = 500
      return
    }

    log.info("Assigned permission $savedPermission.id to role [$role.id]$role.name")
    render "success"
    return
  }

  def removepermission = {
    def role = Role.get(params.id)
    def permission = Permission.get(params.permID)

    if (!role) {
      log.warn("Role identified by id '$params.id' was not located")

      flash.message = "Role not found with id $params.id"
      render "Role not found with id $params.id"
      response.status = 500
      return
    }

    if (!permission) {
      log.warn("Permission identified by id '$params.permID' was not located")

      flash.message = "Permission not found with id $params.permID"
      render "Permission not found with id $params.permID"
      response.status = 500
      return
    }

    permissionService.deletePermission(permission)
    log.info("Removed permission $permission.id from role [$role.id]$role.name")

    render "success"
    return
  }

}
