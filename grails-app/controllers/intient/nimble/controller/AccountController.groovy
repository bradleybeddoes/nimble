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

package intient.nimble.controller

import intient.nimble.domain.User
import intient.nimble.domain.Profile
import intient.nimble.domain.Role

/**
 * Manages all common user account tasks.
 *
 * @author Bradley Beddoes
 */
class AccountController {

  static Map allowedMethods = [ createuser: 'GET', saveuser: 'POST', createduser: 'GET',
                              validateuser: 'GET', validateusername: 'GET', forgottenpassword: 'GET',
                              forgottenpasswordprocess: 'POST', forgottenpasswordexternal: 'GET',
                              forgottenpasswordcomplete: 'GET' ]

  def userService
  def recaptchaService

  def grailsApplication

  def createuser = {
    def user = new User()
    user.profile = new Profile()

    log.debug("Starting new user creation")
    [user: user]
  }

  def saveuser = {
    def user = new User()
    user.profile = new Profile()
    user.profile.owner = user
    user.properties['username', 'pass', 'passConfirm'] = params
    user.profile.properties['fullName', 'email'] = params

    log.debug("Attempting to create new user account identified as $user.username")

    // Manually enforce email address on account registrations
    if (user.profile.email == null || user.profile.email.length() == 0)
      user.profile.email = 'invalid'

    user.enabled = false
    user.external = false

    def savedUser
    def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)

    if (human) {

      savedUser = userService.createUser(user)
      if (savedUser.hasErrors()) {
        log.debug("UserService returned invalid account details when attempting account creation")
        resetNewUser(user)
        render view: 'createuser', model: [user: user]
        return
      }
    }
    else {
      log.debug("Captcha entry was invalid for user account creation")
      resetNewUser(user)
      user.errors.reject('invalid.captcha')
      render view: 'createuser', model: [user: user]
      return
    }

    log.info("Sending account registration confirmation email to $user.profile.email with subject $grailsApplication.config.nimble.messaging.registration.subject")
    sendMail {
      to user.profile.email
      subject grailsApplication.config.nimble.messaging.registration.subject
      html g.render(template: "/templates/nimble/mail/accountregistration_email", model: [user: savedUser])
    }

    log.info("Created new account identified as $user.username with internal id $savedUser.id")

    redirect action: createduser
    return
  }

  def createduser = {

  }

  def validateuser = {
    def user = User.get(params.id)

    if (!user) {
      log.warn("User identified as [$params.id] was not located")
      flash.type = "error"
      flash.message = "User not found with id $params.id"
      redirect controller: 'auth', action: 'login'
      return
    }

    if (!user.enabled && user.actionHash != null && user.actionHash.equals(params.activation)) {
      user.enabled = true
      user.actionHash = null
      user.save()

      if (user.hasErrors()) {
        log.warn("Unable to enable user identified as [$user.id]$user.username after successful action hash validation")
        user.errors.each {
          log.debug(it)
        }

        flash.type = "error"
        flash.message = "Error when attempting to validate account please try again later"
        redirect controller: 'auth', action: 'login'
        return
      }

      log.info("Successful validate for user identified as [$user.id]$user.username")

      flash.type = "success"
      flash.message = "Your account is now valid, login to continue"
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
    flash.message = "Error when attempting to validate account please try again later"
    redirect controller: 'auth', action: 'login'
    return
  }

  def validusername = {

    if (params.username == null || params.username.length() < 4 || !params.username.matches('[a-zA-Z0-9]*')) {
      flash.message = "Username is invalid"
      response.sendError(500)
      return
    }

    def users = User.findAllByUsername(params?.username)

    if (users != null && users.size() > 0) {
      flash.message = "User already exists for ${params.username}"
      response.sendError(500)
      return
    }

    render "valid"
  }

  def forgottenpassword = {

  }

  def forgottenpasswordemail = {

  }

  def forgottenpasswordprocess = {
    def profile = Profile.findByEmail(params.email)
    if (profile) {

      def user = profile.owner

      if (user.external || user.federated) {
        log.debug("User identified by [$user.id]$user.username is external or federated, redirecting to forgottenpasswordexternal")
        redirect(action: "forgottenpasswordexternal", id: user.id)
        return
      }

      def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)
      if (human) {

        userService.setRandomPassword(user)

        log.info("Sending account password reset email to $user.profile.email with subject $grailsApplication.config.nimble.messaging.passwordreset.subject")
        sendMail {
          to user.profile.email
          subject grailsApplication.config.nimble.messaging.passwordreset.subject
          html g.render(template: "forgottenpassword_email", model: [user: user])
        }

        log.info("Successful password reset for user identified as [$user.id]$user.username")

        redirect(action: "forgottenpasswordcomplete")
        return
      }
      else {
        log.debug("Captcha entry was invalid when attempting to process forgotten password for user identified by [$user.id]$user.username")
        flash.type = "error"
        flash.message = message(code: "invalid.captcha")
        redirect(action: "forgottenpassword")
      }
    }
    else {
      log.debug("User account for supplied email address $params.email was not found when attempting to process forgotten password")
      flash.type = "error"
      flash.message = "Unable to locate details associated with the supplied email address"
      redirect(action: "forgottenpassword")
    }
  }

  def forgottenpasswordexternal = {
    def user = User.get(params.id)
    [user: user, orgname: grailsApplication.config.nimble.organization.displayname]
  }

  def forgottenpasswordcomplete = {

  }

  private def resetNewUser = {user ->

    log.debug("New user creation failed, resetting user input to accepted state")
    // Ensure we present all error messages
    user.validate()

    if (user.profile?.email.equals('invalid'))
      user.profile.email = ''

    // Clear password, force re-entry
    user.pass = ""
    user.passConfirm = ""
  }
}
