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
package intient.nimble.realms

import org.openid4java.consumer.ConsumerManager
import org.openid4java.discovery.DiscoveryInformation
import org.openid4java.message.AuthRequest
import intient.nimble.domain.User
import intient.nimble.domain.Profile
import intient.nimble.service.OpenIDService
import intient.nimble.domain.FederationProvider
import intient.nimble.auth.OpenIDToken
import intient.nimble.domain.ProfileGender
import org.apache.ki.authc.UnknownAccountException
import org.apache.ki.authc.DisabledAccountException
import org.apache.ki.authc.SimpleAccount
import org.apache.ki.authc.IncorrectCredentialsException

/**
 * Integrates with Ki to establish a session for users accessing the system based
 * on authentication with an OpenID provider.
 *
 * @author Bradley Beddoes
 */
public class OpenIDRealm {

  static authTokenClass = intient.nimble.auth.OpenIDToken

  def openIDService
  def userService

  def authenticate(OpenIDToken authToken) {
    log.info "Attempting to authenticate user via OpenID"

    def userID = authToken.getUserID()
    def user = User.findByUsername(userID + OpenIDService.federationProviderDiscriminator)
    if (!user) {
      log.info("No account representing user $userID$OpenIDService.federationProviderDiscriminator exists")
      def openidFederationProvider = FederationProvider.findByUid(OpenIDService.federationProviderUid)

      if (openidFederationProvider && openidFederationProvider.autoProvision) {

        User newUser = new User()
        newUser.username = userID + OpenIDService.federationProviderDiscriminator
        newUser.enabled = true
        newUser.external = true
        newUser.federated = true
        newUser.federationProvider = openidFederationProvider

        newUser.profile = new Profile()
        newUser.profile.owner = newUser
        
        newUser.profile.fullName = authToken.fullName
        newUser.profile.nickName = authToken.nickName
        newUser.profile.email = authToken.email

        if (authToken.gender) {
          if (authToken.gender.equalsIgnoreCase('M'))
            newUser.profile.gender = ProfileGender.MALE
          if (authToken.gender.equalsIgnoreCase('F'))
            newUser.profile.gender = ProfileGender.FEMALE
        }

        user = userService.createUser(newUser)

        if (user.hasErrors()) {
          log.warn("Error creating user account from openID credentials for $userID$OpenIDService.federationProviderDiscriminator")
          user.errors.each {
            log.error(it)
          }
          throw new RuntimeException("Account creation exception for new openID based account");
        }
        log.info("Created new user [$user.id]$user.username from openID credentials")
      }
      else
        throw new UnknownAccountException("No account found for openID federated user ${userID}")
    }

    if (!user.enabled) {
      log.warn("Attempt to authenticate using using Facebook Account with locally disabled account [$user.id]$user.username")
      throw new DisabledAccountException("The openID federated account ${userID} is currently disabled")
    }

    def account = new SimpleAccount(user.id, authToken.principal, "OpenIDRealm")

    log.info("Successfully logged in user [$user.id]$user.username using OpenID")
    return account
  }
}