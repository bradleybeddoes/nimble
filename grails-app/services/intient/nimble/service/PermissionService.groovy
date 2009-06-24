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

import intient.nimble.domain.User

/**
 * Provides methods for interacting with Nimble permissions.
 *
 * @author Bradley Beddoes
 */
class PermissionService {

  boolean transactional = true

  /**
   * Creates a new LevelPermission object, generates target and assigns to owner.
   *
   * @param permission The populated permission object to persist
   * @param owner An object that extends PermissionOwner (e.g. User, Group, Role)
   *
   * @return A permission object. The saved object is all was successful or the permission object with error details if persistence fails.
   * 
   * @throws RuntimeException if an unrecoverable/unexpected error occurs (Rolls back transaction)
   */
  def createPermission(permission, owner) {

    permission.owner = owner
    def savedPermission = permission.save()
    if (!savedPermission) {
      log.error("Unable to persist new permission")
      permission.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to persist new permission")
    }

    owner.addToPermissions(permission)
    def savedOwner = owner.save(flush: true)

    if (!savedOwner) {
      log.error("Unable to add permission $savedPermission.id to owner $owner.id")
      owner.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to add permission $savedPermission.id to owner $owner.id")
    }

    log.info("Successfully added permission $savedPermission.id to owner $owner.id")
    return savedPermission
  }

  /**
   * Removes permission from owner and deletes reference in data repository.
   *
   * @permission The populated permission object to delete. If permission is managed reference must be removed from management object before invocation
   *
   * @throws RuntimeException if an unrecoverable/unexpected error occurs (Rolls back transaction)
   */
  def deletePermission(permission) {
    def owner = permission.owner

    owner.removeFromPermissions(permission)
    def savedOwner = owner.save()

    if (!savedOwner) {
      log.error("Unable to remove permission $savedPermission.id from user [$owner.id]$owner.name")
      owner.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to remove permission $savedPermission.id from user [$owner.id]$owner.name")
    }

    permission.delete();
    log.info("Successfully removed permission $permission.id from owner $owner.id")
  }

}
