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

import intient.nimble.domain.LevelPermission
import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain._Group
import intient.nimble.service.AdminsService
import intient.nimble.service.UserService
import intient.nimble.domain.Profile
import intient.nimble.domain.Phone
import intient.nimble.domain.Gender
import intient.nimble.domain.Address
import intient.nimble.domain.SocialMediaService
import intient.nimble.domain.SocialMediaAccount
import intient.nimble.domain.Url
import intient.nimble.domain.PhoneType

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
    def facebookService
    def twitterService

    def nimbleInternalBootStrap

    def init = {servletContext ->

        // The following must be executed
        internalBootStap(servletContext)

        // Execute any custom Nimble related BootStrap for your application below

        // Create example User accounts
        for (i in 0..15) {
            def user = new User()
            user.username = "user$i"

            user.pass = 'useR123!'
            user.passConfirm = 'useR123!'

            user.enabled = true

            Profile userProfile = new Profile()
            userProfile.fullName = "User $i"
            userProfile.email = "user$i@test.user"
            userProfile.owner = user
            user.profile = userProfile

            userService.createUser(user)
        }

        //Create a full featured user account
        def user = new User()
        user.username = "beddoes"
        user.pass = "beddoeS123!"
        user.passConfirm = "beddoeS123!"
        user.enabled = true

        Profile userProfile = new Profile()
        userProfile.fullName = "Bradley Beddoes"
        userProfile.email = "beddoes@intient.com"
        userProfile.bio = "Director and Lead Software Architect at Intient Pty Ltd"
        userProfile.dob = new Date()
        userProfile.gender = Gender.Male
        
        def workPh = new Phone(number:'+61 7 3102 4560  ', type: PhoneType.Business)
        def mobilePh = new Phone(number:'+61 403 768 802', type: PhoneType.Mobile)
        userProfile.addToPhoneNumbers(workPh)
        userProfile.addToPhoneNumbers(mobilePh)

        /*
        // Facebook Account
        facebookService.create(userProfile, '1241927789')
        
        // Twitter account
        twitterService.create(userProfile, 'bradleybeddoes')
        */
       
        userProfile.owner = user
        user.profile = userProfile
        userService.createUser(user)

        // Create example Administrative account
        def admin = new User()
        admin.username = "admin"
        admin.pass = "admiN123!"
        admin.passConfirm = "admiN123!"
        admin.enabled = true

        Profile adminProfile = new Profile()
        adminProfile.fullName = "Administrator"
        adminProfile.email = "test@test.com"
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

        // Create example groups
        for (i in 0..15) {
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

        // Create example roles
        for (j in 0..15) {
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
        nimbleService.init()
    }
} 