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

import intient.nimble.domain.Group
import intient.nimble.service.AdminsService
import intient.nimble.service.UserService

import intient.nimble.domain.User
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
        userProfile.owner = user
        
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
            def group = new Group()
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