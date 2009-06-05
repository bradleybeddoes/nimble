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

import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain.Role
import intient.nimble.domain.Permission

/**
 * Provides methods for granting and removing super administrator role.
 *
 * @author Bradley Beddoes
 */
class AdminsService {

    public static String ADMIN_ROLE = "SYSTEM ADMINISTRATOR"

    boolean transactional = true

    /**
     * Provides administrator capability to a user account.
     *
     * @param user A valid user object that should be assigned global admin rights
     *
     * @pre Passed user object must have been validated to ensure
     * that hibernate does not auto persist the object to the repository prior to service invocation
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def add(User user) {
        // Grant administrative role
        def adminRole = Role.findByName(AdminsService.ADMIN_ROLE)

        if (!adminRole) {
            log.error("Unable to located default administative role")
            throw new RuntimeException("Unable to locate default administrative role")
        }

        adminRole.addToUsers(user)
        user.addToRoles(adminRole)

        if (!adminRole.save()) {
            log.error "Unable to grant administration privilege to [$user.id]$user.username"
            adminRole.errors.each {
                log.error '[${user.username}] - ' + it
            }

            adminRole.discard()
            user.discard()
            return false
        }
        else {
            if (!user.save()) {
                log.error "Unable to grant administration role to [$user.id]$user.username failed to modify user account"
                user.errors.each {
                    log.error it
                }

                throw new RuntimeException("Unable to grant administration role to [$user.id]$user.username")
            }

            // Grant administrative 'ALL' permission
            Permission adminPermission = new Permission(target:'*')
            adminPermission.managed = true
            adminPermission.type = Permission.adminPerm
            user.addToPermissions(adminPermission)

            if (!user.save()) {
                log.error "Unable to grant administration permission to [$user.id]$user.username failed to modify user account"
                user.errors.each {
                    log.error it
                }

                throw new RuntimeException("Unable to grant administration permission to [$user.id]$user.username")
            }

            log.info "Granted administration privileges to [$user.id]$user.username"
            return true
        }
    }

    /**
     * Removes administrator capability from a user account.
     *
     * @param user A valid user object that should have global admin rights removed
     *
     * @pre Passed user object must have been validated to ensure
     * that hibernate does not auto persist the object to the repository prior to service invocation
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def remove(User user) {
        def adminRole = Role.findByName(AdminsService.ADMIN_ROLE)

        if (!adminRole) {
            log.error("Unable to located default administative role")
            throw new RuntimeException("Unable to locate default administrative role")
        }

        if(adminRole.users.size() < 2) {
            log.warn("Unable to remove user from administration, would leave no system administrator available")
            return false
        }

        adminRole.removeFromUsers(user)
        user.removeFromRoles(adminRole)

        if (!adminRole.save()) {
            log.error "Unable to revoke administration privilege from [$user.id]$user.username"
            adminRole.errors.each {
                log.error it
            }

            adminRole.discard()
            user.discard()
            return false
        }
        else {
            if (!user.save()) {
                log.error "Unable to revoke administration privilege from [$user.id]$user.username failed to modify user account"
                user.errors.each {
                    log.error it
                }

                throw new RuntimeException("Unable to revoke administration privilege from [$user.id]$user.username failed to modify user account")
            }

            // Revoke administrative 'ALL' permission(s)
            def permToRemove = []
            user.permissions.each {
                if (permission.type.equals(AllPermission.class.name) || permission.type.equals(intient.nimble.auth.AllPermission.class.name)) {
                    permToRemove.add(it)
                    log.debug("Found $permission.type for user [$user.id]$user.username adding to remove queue")
                }
            }

            permToRemove.each {
                user.permissions.remove(it)
                log.debug("Removing $it.type from user [$user.id]$user.username")
            }

            if (!user.save()) {
                log.error "Unable to revoke administration permission from [$user.id]$user.username failed to modify user account"
                user.errors.each {
                    log.error it
                }

                throw new RuntimeException("Unable to revoke administration permission from [$user.id]$user.username")
            }

            log.error "Revoked administration privilege from [$user.id]$user.username failed to modify user account"
            return true
        }
    }
}
