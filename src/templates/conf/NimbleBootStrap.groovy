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
 
import intient.nimble.domain.LevelPermission
import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain.Group
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
  
  def nimbleService
  def userService
  def adminsService

  def init = {servletContext ->

    // The following must be executed
    internalBootStap(servletContext)

    // Execute any custom Nimble related BootStrap for your application below

    // Create example User account
    def user = new User()
    user.username = "user"
    user.pass = 'useR123!'
    user.passConfirm = 'useR123!'
    user.enabled = true

    Profile userProfile = new Profile()
    userProfile.fullName = "Test User"
    userProfile.owner = user
    user.profile = userProfile

    def savedUser = userService.createUser(user)
    if (savedUser.hasErrors()) {
      savedUser.errors.each {
        log.error(it)
      }
      throw new RuntimeException("Error creating example user")
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

    adminsService.add(admin)
  }

  def destroy = {

  }

  private internalBootStap(def servletContext) {
    nimbleService.init()
  }
} 