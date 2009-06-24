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
package intient.nimble.service

import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain.Group

/**
 * Provides methods for interacting with Nimble roles.
 *
 * @author Bradley Beddoes
 */
class RoleService {

  boolean transactional = true

  /**
   * Creates a new role.
   *
   * @param name Name to assign to group
   * @param description Description to assign to group
   * @param protect Boolean indicating if this is a protected group (True disables modification in UI)
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def createRole(String name, String description, boolean protect) {
    def role = new Role()
    role.name = name
    role.description = description
    role.protect = protect

    if(!role.validate()) {
      log.debug("Supplied values for new role are invalid")
      role.errors.each {
        log.debug it
      }
      return role
    }

    def savedRole = role.save()
    if (savedRole) {
      log.info("Created role [$role.id]$role.name")
      return savedRole
    }

    log.error("Error creating new group")
    role.errors.each {
      log.error it
    }

    throw new RuntimeException("Error creating new group, object persistance failed")
  }

  /**
   * Deletes an exisiting Role.
   *
   * @pre Passed role object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param role The role instance to be deleted
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def deleteRole(Role role) {

    // Remove all users from this role
    def users = []
    users.addAll(role.users)
    users.each {
      it.removeFromRoles(role)
      it.save()

      if (it.hasErrors()) {
        log.error("Error updating user [$it.id]$it.name to remove role [$role.id]$role.name")
        it.errors.each {err ->
          log.error err
        }

        throw new RuntimeException("Error updating user [$it.id]$it.name to remove role [$role.id]$role.name")
      }
    }

    // Remove all groups from this role
    def groups = []
    groups.addAll(role.groups)
    groups.each {
      it.removeFromRoles(role)
      it.save()

      if (it.hasErrors()) {
        log.error("Error updating group [$it.id]$it.name to remove role [$role.id]$role.name")
        it.errors.each {err ->
          log.error err
        }

        throw new RuntimeException("Error updating group [$it.id]$it.name to remove role [$role.id]$role.name")
      }
    }

    role.delete()
    log.info("Deleted role [$role.id]$role.name")
  }

  /**
   * Updates and existing role.
   *
   * @pre Passed role object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param role The role to be updated.
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def updateRole(Role role) {

    def updatedRole = role.save()
    if (updatedRole) {
      log.info("Updated role [$role.id]$role.name")
      return updatedRole
    }

    log.error("Error updating role [$role.id]$role.name")
    role.errors.each {err ->
      log.error err
    }

    throw new RuntimeException("Error updating role [$role.id]$role.name")
  }

  /**
   * Assigns a role to a user.
   *
   * @pre Passed role and user object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param user The user whole the referenced role should be assigned to
   * @param role The role to be assigned
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def addMember(User user, Role role) {
    role.addToUsers(user)
    user.addToRoles(role)

    def savedRole = role.save()
    if (!savedRole) {
      log.error("Error updating role [$role.id]$role.name to add user [$user.id]$user.username")

      role.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to persist role [$role.id]$role.name when adding user [$user.id]$user.username")
    }

    def savedUser = user.save()
    if (!savedUser) {
      log.error("Error updating user [$user.id]$user.username when adding role [$role.id]$role.name")

      user.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Error updating user [$user.id]$user.username when adding role [$role.id]$role.name")
    }

    log.info("Successfully added role [$role.id]$role.name to user [$user.id]$user.username")
  }

  /**
   * Removes a role from a user.
   * 
   * @pre Passed role and user object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param user The user whole the referenced role should be removed from
   * @param role The role to be assigned
   * 
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def deleteMember(User user, Role role) {
    role.removeFromUsers(user)
    user.removeFromRoles(role)

    def savedRole = role.save()
    if (!savedRole) {
      log.error("Error updating role [$role.id]$role.name to add user [$user.id]$user.username")

      role.errors.each {
        log.error(it)
      }

     throw new RuntimeException("Unable to persist role [$role.id]$role.name when removing user [$user.id]$user.username")
    }

    def savedUser = user.save()
    if (!savedUser) {
      log.error("Error updating user [$user.id]$user.username when adding role [$role.id]$role.name")
      user.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Error updating user [$user.id]$user.username when removing role [$role.id]$role.name")
    }

    log.info("Successfully removed role [$role.id]$role.name to user [$user.id]$user.username")
  }

  /**
   * Adds a role to a group.
   *
   * @pre Passed role and group object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param group The group whole the referenced role should be assigned to
   * @param role The role to be assigned
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def addGroupMember(Group group, Role role) {
    role.addToGroups(group)
    group.addToRoles(role)

    def savedRole = role.save()
    if (!savedRole) {
      log.error("Error updating role [$role.id]$role.name to add group [$group.id]$group.name")
      role.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to persist role [$role.id]$role.name when adding group [$group.id]$group.name")
    }

    def savedGroup = group.save()
    if (!savedGroup) {
      log.error("Error updating group [$group.id]$group.name when adding role [$role.id]$role.name")
      group.errors.each {
        println it
      }

     throw new RuntimeException("Error updating group [$group.id]$group.name when adding role [$role.id]$role.name")
    }

    log.info("Successfully added role [$role.id]$role.name to group [$group.id]$group.name")
  }

  /**
   * Removes a role from a group.
   * 
   * @pre Passed role and user object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param group The gruop whole the referenced role should be removed from
   * @param role The role to be assigned
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def deleteGroupMember(Group group, Role role) {
    role.removeFromGroups(group)
    group.removeFromRoles(role)

    def savedRole = role.save()
    if (!savedRole) {
      log.error("Error updating role [$role.id]$role.name to remove group [$group.id]$group.name")
      role.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to persist role [$role.id]$role.name when removing group [$group.id]$group.name")
    }

    def savedGroup = group.save()
    if (!savedGroup) {
       log.error("Error updating group [$group.id]$group.name when removing role [$role.id]$role.name")
      group.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Error updating group [$group.id]$group.name when removing role [$role.id]$role.name")
    }

    log.info("Successfully removed role [$role.id]$role.name to group [$group.id]$group.name")
  }
}
