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

package intient.nimble.core

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Sha256Hash

import intient.nimble.InstanceGenerator

/**
 * Manages all common user account tasks.
 *
 * @author Bradley Beddoes
 */
class AccountController {

    static Map allowedMethods = [saveuser: 'POST', validusername: 'POST', forgottenpasswordprocess: 'POST', updatepassword: 'POST']

    def userService
    def recaptchaService

    def grailsApplication

    def changepassword = {
        def user = authenticatedUser()
        if(!user)
        	return

        [user:user]
    }

    def changedpassword = {
        
    }

    def updatepassword = {
        def user = authenticatedUser()
        if(!user)
        return

        if(!params.currentPassword) {
            log.warn("User [$user.id]$user.username attempting to change password but has not supplied current password")
            user.errors.reject('nimble.user.password.required')
            render (view:"changepassword", model:[user:user])
            return
        }

        def pwEnc = new Sha256Hash(params.currentPassword)
        def crypt = pwEnc.toHex()

        def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)
        if (human) {

            if(!crypt.equals(user.passwordHash)) {
                log.warn("User [$user.id]$user.username attempting to change password but has supplied invalid current password")
                user.errors.reject('nimble.user.password.nomatch')
                render (view:"changepassword", model:[user:user])
                return
            }

            user.pass = params.pass
            user.passConfirm = params.passConfirm
            user.validate()

            if(!user.hasErrors()) {
                userService.changePassword(user)
                if(!user.hasErrors()) {
                    log.info("Changed password for user [$user.id]$user.username successfully")
                    redirect action: "changedpassword"
                    return
                }
            }

            log.error("User [$user.id]$user.username password change was considered invalid")
            user.errors.allErrors.each {
                log.debug it
            }
            render (view:"changepassword", model:[user:user])
            return
        }
        
        log.debug("Captcha entry was invalid for user account creation")
        resetNewUser(user)
        user.errors.reject('nimble.invalid.captcha')
        render(view: 'changepassword', model: [user: user])
        return
    }

    def createuser = {
        if (!grailsApplication.config.nimble.localusers.registration.enabled) {
            log.warn("Account registration is not enabled for local users, skipping request")
            response.sendError(404)
            return
        }

        def user = InstanceGenerator.user()
        user.profile = InstanceGenerator.profile()

        log.debug("Starting new user creation")
        [user: user]
    }

    def saveuser = {
        if (!grailsApplication.config.nimble.localusers.registration.enabled) {
            log.warn("Account registration is not enabled for local users, skipping request")
            response.sendError(404)
            return
        }
        
        def user = InstanceGenerator.user()
        user.profile = InstanceGenerator.profile()
        user.profile.owner = user
        user.properties['username', 'pass', 'passConfirm'] = params
        user.profile.properties['fullName', 'email'] = params
        user.enabled = false
        user.external = false

        user.validate()

        log.debug("Attempting to create new user account identified as $user.username")

        // Enforce username restrictions on local accounts, letters + numbers only
        if (user.username == null || user.username.length() < grailsApplication.config.nimble.localusers.usernames.minlength || !user.username.matches(grailsApplication.config.nimble.localusers.usernames.validregex)) {
            log.debug("Supplied username of $user.username does not meet requirements for local account usernames")
            user.errors.rejectValue('username', 'nimble.user.username.invalid')
        }

        // Enforce email address for account registrations
        if (user.profile.email == null || user.profile.email.length() == 0)
        user.profile.email = 'invalid'


        if (user.hasErrors()) {
            log.debug("Submitted values for new user are invalid")
            user.errors.each {
                log.debug it
            }

            resetNewUser(user)
            render(view: 'createuser', model: [user: user])
            return
        }

        def savedUser
        def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)

        if (human) {

            savedUser = userService.createUser(user)
            if (savedUser.hasErrors()) {
                log.debug("UserService returned invalid account details when attempting account creation")
                resetNewUser(user)
                render(view: 'createuser', model: [user: user])
                return
            }
        }
        else {
            log.debug("Captcha entry was invalid for user account creation")
            resetNewUser(user)
            user.errors.reject('nimble.invalid.captcha')
            render(view: 'createuser', model: [user: user])
            return
        }

        log.info("Sending account registration confirmation email to $user.profile.email with subject $grailsApplication.config.nimble.messaging.registration.subject")
        sendMail {
            to user.profile.email		
			from grailsApplication.config.nimble.messaging.mail.from
            subject grailsApplication.config.nimble.messaging.registration.subject
            html g.render(template: "/templates/nimble/mail/accountregistration_email", model: [user: savedUser]).toString()
        }

        log.info("Created new account identified as $user.username with internal id $savedUser.id")

        redirect action: createduser
        return
    }

    def createduser = {

    }

    def validateuser = {
        def user = UserBase.get(params.id)

        if (!user) {
            log.warn("User identified as [$params.id] was not located")
            flash.type = "error"
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nimble.user.label'), params.id])
            redirect controller: 'auth', action: 'login'
            return
        }

        if (!user.enabled && user.actionHash != null && user.actionHash.equals(params.activation)) {
            user.enabled = true

            // Reset hash
            userService.generateValidationHash(user)
            user.save()

            if (user.hasErrors()) {
                log.warn("Unable to enable user identified as [$user.id]$user.username after successful action hash validation")
                user.errors.each {
                    log.debug(it)
                }

                flash.type = "error"
                flash.message = "${message(code: 'nimble.user.validate.error')}"
                redirect controller: 'auth', action: 'login'
                return
            }

            log.info("Successful validate for user identified as [$user.id]$user.username")

            flash.type = "success"
            flash.message = message(code: 'nimble.user.validate.success')
            redirect controller: 'auth', action: 'login'

            return
        }

        if (!user.enabled)
        log.warn("Attempt to validate user identified by [$user.id]$user.username and when the account is already active")

        if (!user.actionHash == null)
        log.warn("Attempt to validate user identified by [$user.id]$user.username and when the account action hash is null")

        if (!user.actionHash.equals(params.activation))
        log.warn("Attempt to validate user identified by [$user.id]$user.username but activation action hash did not match data store")

        flash.type = "error"
        flash.message = message(code: 'nimble.user.validate.error')
        redirect controller: 'auth', action: 'login'
        return
    }

    def validusername = {
        if (params.username == null || params.username.length() < grailsApplication.config.nimble.localusers.usernames.minlength || !params.username.matches(grailsApplication.config.nimble.localusers.usernames.validregex)) {
            flash.message = message(code: 'nimble.user.username.invalid')
            response.sendError(500)
            return
        }

        def users = UserBase.findAllByUsername(params?.username)

        if (users != null && users.size() > 0) {
            flash.message = message(code: 'nimble.user.username.invalid')
            response.sendError(500)
            return
        }

        render message(code: 'nimble.user.username.valid')
    }

    def forgottenpassword = {

    }

    def forgottenpasswordemail = {

    }

    def forgottenpasswordprocess = {
        def profile = ProfileBase.findByEmail(params.email)
        if (profile) {

            def user = profile.owner

            if (user.external || user.federated) {
                log.info("User identified by [$user.id]$user.username is external or federated")

                log.info("Sending account password reset email to $user.profile.email with subject $grailsApplication.config.nimble.messaging.passwordreset.external.subject")
                sendMail {
                    to user.profile.email
					from grailsApplication.config.nimble.messaging.mail.from
                    subject grailsApplication.config.nimble.messaging.passwordreset.external.subject
                    html g.render(template: "/templates/nimble/mail/forgottenpassword_external_email", model: [user: user]).toString()
                }

                redirect(action: "forgottenpasswordcomplete", id: user.id)
                return
            }

            def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)
            if (human) {

                userService.setRandomPassword(user)

                log.info("Sending account password reset email to $user.profile.email with subject $grailsApplication.config.nimble.messaging.passwordreset.subject")
                sendMail {
                    to user.profile.email
					from grailsApplication.config.nimble.messaging.mail.from
                    subject grailsApplication.config.nimble.messaging.passwordreset.subject
                    html g.render(template: "/templates/nimble/mail/forgottenpassword_email", model: [user: user]).toString()
                }

                log.info("Successful password reset for user identified as [$user.id]$user.username")

                redirect(action: "forgottenpasswordcomplete")
                return
            }
            else {
                log.debug("Captcha entry was invalid when attempting to process forgotten password for user identified by [$user.id]$user.username")
                flash.type = "error"
                flash.message = message(code: 'nimble.invalid.captcha')
                redirect(action: "forgottenpassword")
            }
        }
        else {
            log.debug("User account for supplied email address $params.email was not found when attempting to process forgotten password")
            flash.type = "error"
            flash.message = message(code: 'nimble.user.forgottenpassword.noaccount')
            redirect(action: "forgottenpassword")
        }
    }

    def forgottenpasswordcomplete = {

    }

    private def resetNewUser = {user ->
        log.debug("New user creation failed, resetting user input to accepted state")

        if (user.profile?.email.equals('invalid'))
        	user.profile.email = ''

        user.pass = ""
        user.passConfirm = ""
    }
}
