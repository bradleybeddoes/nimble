/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
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
package grails.plugin.nimble.core

import org.apache.shiro.authz.permission.AllPermission

/**
 * Provides methods for granting and removing super administrator role.
 *
 * @author Bradley Beddoes
 */
class AdminsService {

    public static String ADMIN_ROLE = "SYSTEM ADMINISTRATOR"

    boolean transactional = true

    def permissionService

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
    def add(UserBase user) {
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

            permissionService.createPermission(adminPermission, user)

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
    def remove(UserBase user) {
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
                if (it.type.equals(AllPermission.class.name) || it.type.equals(grails.plugin.nimble.auth.AllPermission.class.name)) {
                    permToRemove.add(it)
                    log.debug("Found $it.type for user [$user.id]$user.username adding to remove queue")
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

            log.info "Revoked administration privilege from [$user.id]$user.username"
            return true
        }
    }
}
