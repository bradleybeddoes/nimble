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
package intient.nimble.controller

import intient.nimble.domain.User
import intient.nimble.domain.Role
import intient.nimble.service.AdminsService
import intient.nimble.domain.LevelPermission
import intient.nimble.domain.Permission
import intient.nimble.domain.Group
import intient.nimble.domain.FederationProvider
import intient.nimble.domain.Profile
import intient.nimble.domain.LoginRecord

import intient.nimble.InstanceGenerator

/**
 * Manages Nimble user accounts
 *
 * @author Bradley Beddoes
 */
class UserController {

  static Map allowedMethods = [list: 'GET', show: 'GET', edit: 'GET', create: 'GET', save: 'POST', update: 'POST',
          save: 'POST', enable: 'POST', disable: 'POST', enableapi: 'POST', disableapi: 'POST',
          changepassword: 'GET', changelocalpassword: 'GET', savepassword: 'POST',
          validusername: 'POST', listgroups: 'GET', searchgroups: 'POST', grantgroup: 'POST',
          removegroup: 'POST', listpermissions: 'GET', createpermission: 'POST', removepermisson: 'POST',
          listroles: 'GET', searchroles: 'POST', grantrole: 'POST', removerole: 'POST', listlogins: 'GET']

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
    [users: User.list(params)]
  }

  def show = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect action: list
      return
    }

    log.debug("Showing user [$user.id]$user.username")
    [user: user]
  }

  def edit = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect action: list
      return
    }

    log.debug("Editing user [$user.id]$user.username")
    [user: user]
  }

  def update = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect action: edit, id: params.id
      return
    }

    user.properties['username', 'external', 'federated'] = params
    if (user.validate()) {
      def updatedUser = userService.updateUser(user)

      if (updatedUser.hasErrors()) {
        log.warn("Attempt to persist updated details for user [$user.id]$user.username failed")

        user.discard()
        render view: 'edit', model: [user: updatedUser]
        return
      }
      else {
        log.info("Successfully updated details for user [$user.id]$user.username")

        flash.type = "success"
        flash.message = "Updated user details"
        redirect action: show, id: updatedUser.id
        return
      }
    }

    log.debug("Updated details for user [$user.id]$user.username are invalid")
    user.discard()
    render view: 'edit', model: [user: user]
    return
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
      return
    }

    log.info("Successfully created new user [$savedUser.id]$savedUser.username")
    redirect action: show, id: user.id
    return
  }

  def enable = {
    def user = User.get(params.id)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id, could not enable user"
      redirect action: list
      return
    }

    def enabledUser = userService.enableUser(user)
    if (enabledUser.hasErrors()) {
      log.warn("Failed to enable user [$user.id]$user.username")

      render "error"
      response.status = 500
      return
    }

    log.info("Enabled user [$user.id]$user.username")
    render "success"
    return
  }

  def disable = {
    def user = User.get(params.id)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id, could not disable user"
      redirect action: list
      return
    }

    def disabledUser = userService.disableUser(user)
    if (disabledUser.hasErrors()) {
      log.warn("Failed to disable user [$user.id]$user.username")
      render "error"
      response.status = 500
      return
    }

    log.info("Disabled user [$user.id]$user.username")
    render "success"
    return
  }

  def enableapi = {
    def user = User.get(params.id)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id, could not enable users remote api"
      redirect action: list
      return
    }

    def enabledUser = userService.enableRemoteApi(user)
    if (enabledUser.hasErrors()) {
      log.warn("Failed to enable remote api for user [$user.id]$user.username")
      render "error"
      response.status = 500
      return
    }

    log.info("Enabled remote api for user [$user.id]$user.username")
    render "success"
    return
  }

  def disableapi = {
    def user = User.get(params.id)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id, could not disable users remote api"
      redirect action: list
      return
    }

    def disabledUser = userService.disableRemoteApi(user)
    if (disabledUser.hasErrors()) {
      log.warn("Failed to disable remote api for user [$user.id]$user.username")

      render "error"
      response.status = 500
      return
    }

    log.info("Disabled remote api for user [$user.id]$user.username")
    render "success"
    return
  }

  def changepassword = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect action: list
      return
    }

    if (user.external) {
      log.warn("Attempt to change password on user [$user.id]$user.username that is externally managed denied")

      flash.type = "error"
      flash.message = "intient.nimble.domain.User account is managed by external authentication source, can't change password"
      redirect action: show, id: user.id
    }

    log.debug("Starting password change for user [$user.id]$user.username")
    [user: user]
  }

  def changelocalpassword = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect action: list
      return
    }

    log.debug("Starting local password change for user [$user.id]$user.username")
    [user: user]
  }

  def savepassword = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect action: list
      return
    }

    user.properties['pass', 'passConfirm'] = params
    if (user.validate()) {
      def savedUser = userService.changePassword(user)
      if (savedUser.hasErrors()) {
        log.warn("Unable to save password change for user [$user.id]$user.username")
        user.discard()
        render view: 'changepassword', model: [user: savedUser]
        return
      }
      else {
        log.info("Successfully saved password change for user [$user.id]$user.username")

        flash.type = "success"
        flash.message = "Successfully updated users password"
        redirect action: show, id: savedUser.id
        return
      }
    }

    user.discard()

    log.debug("Password change for [$user.id]$user.username was invalid")
    render view: 'changepassword', model: [user: user]
    return
  }

  def validusername = {

    if (params.username == null || params.username.length() < 4) {
      flash.message = "Username is invalid"
      response.sendError(500)
      return
    }

    def users = User.findAllByUsername(params?.username)

    if (users != null && users.size() > 0) {
      flash.message = "User already exists for ${params.username}"
      response.sendError(500)
      return
    }

    render "valid"
  }

  def listlogins = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing login events for user [$user.id]$user.username")
    def c = LoginRecord.createCriteria()
    def logins = c.list {
      eq("owner", user)
      order("dateCreated")
      maxResults(20)
    }

    render(template: '/templates/admin/logins_list', contextPath: pluginContextPath, model: [logins: logins, ownerID: user.id])

  }

  def listgroups = {

    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing groups user [$user.id]$user.username is a member of")
    render(template: '/templates/admin/groups_list', contextPath: pluginContextPath, model: [groups: user.groups, ownerID: user.id])
  }

  def searchgroups = {
    def q = "%" + params.q + "%"
    log.debug("Performing search for groups matching $q")

    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

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

  def grantgroup = {
    def user = User.get(params.id)
    def group = Group.get(params.groupID)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    if (!group) {
      log.warn("Group identified by id '$params.groupID' was not located")

      flash.message = "Group not found with id $params.roleID"
      response.sendError(500)
      return
    }

    if (group.protect) {
      log.warn("Can't add user [$user.id]$user.username to group [$group.id]$group.name as group is protected")

      flash.message = "Unable to grant group with id $group.id to user with id $params.id via web interface, group is protected"
      response.sendError(500)
      return
    }

    groupService.addMember(user, group)
    log.info("Added user [$user.id]$user.username to group [$group.id]$group.name")
    render "success"
    return
  }

  def removegroup = {
    def user = User.get(params.id)
    def group = Group.get(params.groupID)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    if (!group) {
      log.warn("Group identified by id '$params.groupID' was not located")

      flash.message = "Group not found with id $params.groupID"
      response.sendError(500)
      return
    }

    if (group.protect) {
      log.warn("Can't remove user [$user.id]$user.username from group [$group.id]$group.name as group is protected")

      flash.message = "Unable to remove group with id $params.groupID from user with id $params.id via web interface, group is protected"
      response.sendError(500)
      return
    }

    groupService.deleteMember(user, group)

    log.info("Removed user [$user.id]$user.username from group [$group.id]$group.name")
    render "success"
    return
  }

  def listpermissions = {

    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing permissions user [$user.id]$user.username is granted")
    render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: user.permissions, ownerID: user.id])
  }

  def createpermission = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      render "User not found with id $params.id"
      response.status = 500
      return
    }

    LevelPermission permission = new LevelPermission()
    permission.populate(params.first, params.second, params.third, params.fourth, params.fifth, params.sixth)
    permission.managed = false

    if (permission.hasErrors()) {
      log.warn("Creating new permission for user [$user.id]$user.username failed, permission is invalid")
      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
      response.status = 500
      return
    }

    def savedPermission = permissionService.createPermission(permission, user)
    if (savedPermission.hasErrors()) {
      log.warn("Creating new permission for user [$user.id]$user.username failed, permission had errors")

      render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: savedPermission])
      response.status = 500
      return
    }

    log.info("Creating new permission for user [$user.id]$user.username succeeded")
    render "success"
    return
  }

  def removepermission = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      render "User not found with id $params.id"
      response.status = 500
      return
    }

    def permission = Permission.get(params.permID)
    if (!permission) {
      log.warn("Permission identified by id '$params.permID' was not located")

      flash.message = "Permission not found with id $params.permID"
      render "Permission not found with id $params.permID"
      response.status = 500
      return
    }

    permissionService.deletePermission(permission)
    log.info("Removing permission [$permission.id] from user [$user.id]$user.username succeeded")
    render "success"
    return
  }

  def listroles = {

    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    log.debug("Listing roles user [$user.id]$user.username is granted")
    render(template: '/templates/admin/roles_list', contextPath: pluginContextPath, model: [roles: user.roles, ownerID: user.id])
  }

  def searchroles = {
    def q = "%" + params.q + "%"
    log.debug("Performing search for roles matching $q")

    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

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
    return
  }

  def grantrole = {
    def user = User.get(params.id)
    def role = Role.get(params.roleID)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    if (!role) {
      log.warn("Role identified by id '$params.roleID' was not located")

      flash.message = "Role not found with id $params.roleID"
      response.sendError(500)
      return
    }

    if (role.protect) {
      log.warn("Can't assign user [$user.id]$user.username role [$role.id]$role.name as role is protected")

      flash.message = "Unable to grant role with id $params.roleID to user with id $params.id via web interface, role is protected"
      response.sendError(500)
      return
    }

    roleService.addMember(user, role)
    log.info("Assigned user [$user.id]$user.username role [$role.id]$role.name")
    render "success"
    return
  }

  def removerole = {
    def user = User.get(params.id)
    def role = Role.get(params.roleID)

    if (!user) {
      log.warn("User identified by id '$params.id' was not located")

      flash.message = "User not found with id $params.id"
      response.sendError(500)
      return
    }

    if (!role) {
      log.warn("Role identified by id '$params.roleID' was not located")

      flash.message = "Role not found with id $params.roleID"
      response.sendError(500)
      return
    }

    if (role.protect) {
      log.warn("Can't assign user [$user.id]$user.username role [$role.id]$role.name as role is protected")

      flash.message = "Unable to remove role with id $params.roleID from user with id $params.id via web interface, role is protected"
      response.sendError(500)
      return
    }

    roleService.deleteMember(user, role)
    log.info("Removed user [$user.id]$user.username from role [$role.id]$role.name")
    render "success"
    return
  }

}

