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
   * @param owner An object that extends PermissionOwner (e.g. User, _Group, Role)
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
