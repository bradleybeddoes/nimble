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

import javax.servlet.http.HttpServletRequest
import org.apache.ki.crypto.hash.Sha256Hash
import org.apache.ki.SecurityUtils

import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain._Group
import intient.nimble.domain.LoginRecord

/**
 * Provides methods for interacting with Nimble users.
 *
 * @author Bradley Beddoes
 */
class UserService {

  public static String USER_ROLE = "USER"

  boolean transactional = true

  def grailsApplication

  /**
   * Activates a disabled user account
   *
   * @param user The user to enable
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def enableUser(User user) {
    user.enabled = true

    def savedUser = user.save()
    if (savedUser) {
      log.info("Successfully enabled user [$user.id]$user.username")
      return savedUser
    }

    log.error("Error enabling user [$user.id]$user.username")
    user.errors.each {
      log.error it
    }

    throw new RuntimeException("Error enabling user [$user.id]$user.username")
  }

  /**
   * Disables an active user account
   *
   * @param user The user to disable
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def disableUser(User user) {
    user.enabled = false

    def savedUser = user.save()
    if (savedUser) {
      log.info("Successfully disabled user [$user.id]$user.username")
      return savedUser
    }

    log.error("Error disabling user [$user.id]$user.username")
    user.errors.each {
      log.error it
    }

    throw new RuntimeException("Error disabling user [$user.id]$user.username")
  }

  /**
   * Enables remote api access flag
   *
   * @param user The user to enable remote api for
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def enableRemoteApi(User user) {
    user.remoteapi = true

    def savedUser = user.save()
    if (savedUser) {
      log.info("Successfully enabled user [$user.id]$user.username remote api")
      return savedUser
    }

    log.error("Error enabling user [$user.id]$user.username remote api")
    user.errors.each {
      log.error it
    }

    throw new RuntimeException("Error enabling user [$user.id]$user.username remote api")
  }

  /**
   * Disables remote api access flag
   *
   * @param user The user to disable remote api for
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def disableRemoteApi(User user) {
    user.remoteapi = false

    def savedUser = user.save()
    if (savedUser) {
      log.info("Successfully disabled user [$user.id]$user.username remote api")
      return savedUser
    }

    log.error("Error disabling user [$user.id]$user.username remote api")
    user.errors.each {
      log.error it
    }

    throw new RuntimeException("Error disabling user [$user.id]$user.username remote api")
  }

  /**
   * Changes password for a local user.
   *
   * @pre Passed user object must have been validated to ensure
   * that hibernate does not auto persist the object to the repository prior to service invocation
   *
   * @param user The user whose password should be changed 
   *
   * @return The user object, with errors populated if change problem occured
   */
  def changePassword(User user) {
    user.validate()
    validatePass(user)

    if (!user.hasErrors()) {
      def savedUser = user.save()
      if (savedUser) {
        log.info("Successfully changed password for user [$user.id]$user.username")
        return savedUser
      }
    }

    log.error("Error changing password for user [$user.id]$user.username")
    user.errors.each {
      log.error it
    }

    return user
  }

  /**
   * Creates a new user account in data repository.
   *
   * @pre User object must be newly created and not yet associated with backend data repository
   *
   * @param user A populated user object to persist
   *
   * @return The stored user object if successful or the supplied user with errors on failure
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def createUser(User user) {
    user.validate()

    if (!user.external) {
      if (validatePass(user))
        generateValidationHash(user)
      else
        return user
    }

    if (!user.hasErrors()) {

      def defaultRole = Role.findByName(UserService.USER_ROLE)
      user.addToRoles(defaultRole)

      def savedUser = user.save(flush: true)
      if (savedUser) {
        defaultRole.addToUsers(savedUser)
        defaultRole.save()

        if (defaultRole.hasErrors()) {
          log.error("Unable to assign default role to new user [$savedUser.id]$savedUser.username")
          defaultRole.errors.each {
            log.error(it)
          }

          throw new RuntimeException("Unable to assign default role to new user [$savedUser.id]$savedUser.username")
        }
        else {
          log.info("Successfully created user [$user.id]$user.username")
          return savedUser
        }
      }
    }

    // Validation or save errors ocured
    log.debug("Submitted details for new user account are invalid")
    user.errors.each {
      log.debug it
    }
    return user
  }

  /**
   * Updates a current user account.
   *
   * @pre Passed user object must have been validated to ensure
   * that hibernate does not auto persist the object to the repository prior to service invocation
   *
   * @param user The user to update
   *
   * @throws RuntimeException When internal state requires transaction rollback
   */
  def updateUser(User user) {

    def updatedUser = user.save()
    if (updatedUser) {
      log.error("Updated user [$user.id]$user.username")
      return updatedUser
    }

    log.error("Unable to update user [$user.id]$user.username")
    user.errors.each {
      log.error(it)
    }

    throw new RuntimeException("Unable to update user [$user.id]$user.username")
  }

  /**
   * Generates a new Hash to provide to the user (generally over email) to validate some account action.
   *
   * @param user The user undertaking an action requiring validation 
   *
   * @pre Passed user object must have been validated to ensure
   * that hibernate does not auto persist the object to the repository prior to service invocation
   */
  def generateValidationHash(User user) {
    String input = user.username + user.profile?.fullName + user.profile?.email + System.currentTimeMillis()

    def enc = new Sha256Hash(input)
    user.actionHash = enc.toHex()
    log.debug("Generated validation hash of $user.actionHash for user [$user.id]$user.username")
  }

  /**
   * Generates a random password for a user.
   *
   * @param user The user to set  random password for 
   *
   * @pre Passed user object must have been validated to ensure
   * that hibernate does not auto persist the object to the repository prior to service invocation
   */
  def setRandomPassword(User user) {
    String input = user.username + user.profile?.fullName + user.profile?.email + System.currentTimeMillis()

    def enc = new Sha256Hash(input)

    user.pass = enc.toBase64()[0..12]
    def pwEnc = new Sha256Hash(user.pass)
    def crypt = pwEnc.toHex()
    user.passwordHash = crypt
    user.addToPasswdHistory(crypt)

    user.save()
    if (user.hasErrors()) {
      log.error("Unable to assign random password to user [$user.id]$user.username")
      user.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to assign random password to user [$user.id]$user.username")
    }

    log.debug("Assigned random password to user [$user.id]$user.username")
  }


  /**
   * Stores details of a successful login by a user.
   */
  def createLoginRecord(HttpServletRequest request) {
    def user = User.get(SecurityUtils.getSubject()?.getPrincipal())
    if(!user)
    {
      log.error("Attempt to create login record for unauthenticated session")
      throw new RuntimeException("Attempt to create login record for unauthenticated session")
    }

    log.debug("Creating new record for user [$user.id]$user.username login")
    def record = new LoginRecord()

    record.remoteAddr = request.getRemoteAddr()
    record.remoteHost = request.getRemoteHost()
    record.userAgent = request.getHeader("User-Agent")
    record.time = new Date()

    record.owner = user
    user.addToLoginRecords(record)

    record.save()
    if (record.hasErrors()) {
      log.error("Unable to save login record for user [$user.id]$user.username")
      record.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to save login record for user [$user.id]$user.username")
    }

    user.save()
    if (record.hasErrors()) {
      log.error("Unable to update user [$user.id]$user.username with new login record")
      user.errors.each {
        log.error(it)
      }

      throw new RuntimeException("Unable to update user [$user.id]$user.username with new login record")
    }

    log.info("User [$user.id]$user.username logged in successfully from remote host $record.remoteHost with UA $record.userAgent")
  }

  /**
   * Applies password processing rules to determine if the user
   * pass and passConfirm values are valid. If not error objects are added to the user
   * object. If the pass is valid it is encrypted and set as the value of user.passwd and
   * added to the password history
   */
  private def validatePass(User user) {
    log.debug("Validating user entered password")

    if (user.pass == null || user.pass.length() < grailsApplication.config.nimble.passwords.minlength) {
      log.debug("Password to short")
      user.errors.rejectValue('pass', 'user.password.required')
      return false
    }

    if (user.passConfirm == null || user.passConfirm.length() < grailsApplication.config.nimble.passwords.minlength) {
      log.debug("Confirmation password to short")

      user.errors.rejectValue('passConfirm', 'user.passconfirm.required')
      return false
    }

    if (!user.pass.equals(user.passConfirm)) {
      log.debug("Password does not match confirmation")
      user.errors.rejectValue('pass', 'user.password.nomatch')
      return false
    }

    if (grailsApplication.config.nimble.passwords.mustcontain.lowercase && !(user.pass =~ /^.*[a-z].*$/)) {
      log.debug("Password does not contain lower case letters")
      user.errors.rejectValue('pass', 'user.password.no.lowercase')
    }

    if (grailsApplication.config.nimble.passwords.mustcontain.uppercase && !(user.pass =~ /^.*[A-Z].*$/)) {
      log.debug("Password does not contain uppercase letters")
      user.errors.rejectValue('pass', 'user.password.no.uppercase')
    }

    if (grailsApplication.config.nimble.passwords.mustcontain.numbers && !(user.pass =~ /^.*[0-9].*$/)) {
      log.debug("Password does not contain numbers")
      user.errors.rejectValue('pass', 'user.password.no.numbers')
    }

    if (grailsApplication.config.nimble.passwords.mustcontain.symbols && !(user.pass =~ /^.*\W.*$/)) {
      log.debug("Password does not contain symbols")
      user.errors.rejectValue('pass', 'user.password.no.symbols')
    }

    def pwEnc = new Sha256Hash(user.pass)
    def crypt = pwEnc.toHex()

    if (user.passwdHistory != null && user.passwdHistory.contains(crypt)) {
      log.debug("Password was previously utilized")
      user.errors.rejectValue('pass', 'user.password.duplicate')
    }

    if (!user.hasErrors()) {
      user.passwordHash = crypt
      user.addToPasswdHistory(crypt)
    }

    return true
  }
}
