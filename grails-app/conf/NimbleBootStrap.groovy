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

import intient.nimble.boot.NimbleInternalBoot
import intient.nimble.domain.LevelPermission
import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain._Group
import intient.nimble.service.AdminsService
import intient.nimble.service.UserService
import intient.nimble.domain.Profile

/*
 * Allows applications using Nimble to undertake process at BootStrap that are related to Nimbe provided objects
 * such as Users, Role, Groups, Permissions etc.
 *
 * Utilizing this BootStrap class ensures that the Nimble environment is populated in the backend data repository correctly
 * before the application attempts to make any extenstions.
 */
class NimbleBootStrap {

  def grailsApplication
  def userService

  def init = {servletContext ->

    // The following must be executed
    internalBootStap(servletContext)

    // Execute any custom Nimble related BootStrap for your application below

    // Create example User accounts
    for (i in 0..30) {
      def user = new User()
      user.username = "user$i"

      user.pass = 'User123!'
      user.passConfirm = 'User123!'

      user.enabled = true

      Profile userProfile = new Profile()
      userProfile.fullName = "User $i"
      userProfile.email = "user$i@test.user"
      userProfile.owner = user
      user.profile = userProfile

      userService.createUser(user)
    }

    // Create example Administrative account
    def admins = Role.findByName(AdminsService.ADMIN_ROLE)
    def admin = new User()
    admin.username = "admin"
    admin.pass = "admiN123!"
    admin.passConfirm = "admiN123!"
    admin.enabled = true

    Profile adminProfile = new Profile()
    adminProfile.fullName = "Administrator"
    adminProfile.owner = admin
    admin.profile = adminProfile

    def savedAdmin = userService.createUser(admin)
    if (savedAdmin.hasErrors()) {
      savedAdmin.errors.each {
        log.error(it)
      }
      throw new RuntimeException("Error creating administrator")
    }

    admin.addToRoles(admins)
    admins.addToUsers(admin)
    admin.save()
    admins.save()

    for (i in 0..30) {
      def group = new _Group()
      group.name = "group${i}"
      group.description = "a test group"
      group.save()

      if (group.hasErrors()) {
        group.errors.each {
          log.error(it)
        }

        throw new RuntimeException("Error creating groups")
      }
    }

    for (j in 0..30) {
      def role = new Role()
      role.name = "role${j}"
      role.description = "a test role"
      role.save()

      if (role.hasErrors()) {
        role.errors.each {
          log.error(it)
        }

        throw new RuntimeException("Error creating roles")
      }
    }
  }

  def destroy = {

  }

  private internalBootStap(def servletContext) {
    def nimbleInternalBoot = new NimbleInternalBoot(log: log, grailsApplication: grailsApplication)
    nimbleInternalBoot.init(servletContext)
  }
} 