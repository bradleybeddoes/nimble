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
package intient.nimble.service

import intient.nimble.domain.User
import intient.nimble.domain.Group
import intient.nimble.domain.Role

/**
 * Provides methods for interacting with Nimble group membership.
 *
 * @author Bradley Beddoes
 */
class GroupService {

  boolean transactional = true

  /**
   * Creates a new group.
   *
   * @param name Name to assign to group
   * @param description Description to assign to group
   * @param protect Boolean indicating if this is a protected group (True disables modification in UI)
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def createGroup(String name, String description, boolean protect) {
    def group = new Group()
    group.name = name
    group.description = description
    group.protect = protect

    if (!group.validate()) {
      log.debug("Supplied details for new group were invalid")
      return group
    }

    def savedGroup = group.save()
    if (savedGroup) {
      log.info("Created group [$savedGroup.id]$savedGroup.name")
      return savedGroup
    }

    log.error("Error creating new group")
    group.errors.each {
      log.error it
    }

    throw new RuntimeException("Error creating new group, object persistance failed")
  }

  /**
   * Deletes an exisiting group.
   * 
   * @pre Passed group object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param group The group to be deleted
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def deleteGroup(Group group) {

    // Terminate all roles associated with this group
    def roles = []
    roles.addAll(group.roles)
    roles.each {
      it.removeFromGroups(group)
      it.save()

      if (it.hasErrors()) {
        log.error("Error updating role [$it.id]$it.name to remove group [$group.id]$group.name")
        it.errors.each {err ->
          log.error err
        }

        throw new RuntimeException("Error updating role [$it.id]$it.name to remove group [$group.id]$group.name")
      }
    }

    // Remove all users from this group
    def users = []
    users.addAll(group.users)
    users.each {
      it.removeFromGroups(group)
      it.save()

      if (it.hasErrors()) {
        log.error("Error updating user [$it.id]$it.username to remove group [$group.id]$group.name")
        it.errors.each {err ->
          log.error err
        }

        throw new RuntimeException("Error updating user [$it.id]$it.username to remove group [$group.id]$group.name")
      }
    }

    group.delete()
    log.info("Deleted group [$group.id]$group.name")
  }

  /**
   * Updates group details in persistant storage.
   *
   * @pre Passed group object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param group The Group to be updated
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def updateGroup(Group group) {

    def updatedGroup = group.save()
    if (updatedGroup) {
      log.info("Updated group [$group.id]$group.name")
      return updatedGroup
    }

    log.error("Error updating group [$group.id]$group.name")
    it.errors.each {err ->
      log.error err
    }

    throw new RuntimeException("Error updating group [$group.id]$group.name")
  }

  /**
   * Adds a user to a group.
   *
   * @pre Passed group and user object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param user The user which should be added
   * @param group The group which should extend its membership with the supplied user
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def addMember(User user, Group group) {
    group.addToUsers(user)
    user.addToGroups(group)

    def savedGroup = group.save()
    if (!savedGroup) {
      log.error("Unable to persist changes to group [$group.id]$group.name")
      group.errors.each {
        log.error(it)
      }

      // Invoke transaction rollback
      throw new RuntimeException("Unable to persist group [$group.id]$group.name when attempting to add user [$user.id]$user.name membership")
    }

    def savedUser = user.save()
    if (!savedUser) {
      log.error("Unable to persist changes to user [$user.id]$user.name")
      user.errors.each {
        log.error(it)
      }

      // Invoke transaction rollback
      throw new RuntimeException("Unable to persist user [$user.id]$user.name when attempting to add user to group [$group.id]$group.name membership")
    }

    log.info("Added user [$user.id]$user.username to group [$group.id]$group.name successfully")
  }

  /**
   * Removes a user from a group.
   *
   * @pre Passed group and user object must have been validated to ensure
   * that hibernate does not auto persist the objects to the repository prior to service invocation
   *
   * @param user The user which should be removed
   * @param group The group which should reduce its membership by the supplied user
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def deleteMember(User user, Group group) {
    user.removeFromGroups(group)
    group.removeFromUsers(user)

    def savedGroup = group.save()
    if (!savedGroup) {
      log.error("Unable to persist changes to group [$group.id]$group.name")
      group.errors.each {
        log.error(it)
      }

      // Invoke transaction rollback
      throw new RuntimeException("Unable to persist group [$group.id]$group.name when attempting to remove user [$user.id]$user.name membership")
    }

    def savedUser = user.save()
    if (!savedUser) {
      log.error("Unable to persist changes to user [$user.id]$user.name")
      user.errors.each {
        log.error(it)
      }

      // Invoke transaction rollback
      throw new RuntimeException("Unable to persist user [$user.id]$user.name when attempting to remove user from group [$group.id]$group.name membership")
    }

    log.info("Removed user [$user.id]$user.username from group [$group.id]$group.name successfully")
  }
}
