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

import org.apache.ki.authc.UnknownAccountException
import org.apache.ki.authc.DisabledAccountException
import org.apache.ki.authc.SimpleAccount
import org.apache.ki.authc.IncorrectCredentialsException
import intient.nimble.domain.User
import com.google.code.facebookapi.ProfileField
import grails.converters.JSON
import intient.nimble.domain.FederationProvider
import intient.nimble.service.FacebookService
import intient.nimble.domain.Profile
import intient.nimble.auth.AccountCreatedException
import intient.nimble.domain.SocialMediaAccount
import intient.nimble.domain.SocialMediaService
import intient.nimble.domain.Url
import intient.nimble.domain.Details

/**
 * Integrates with Ki to establish a session for users accessing the system based
 * on authentication via Facebook Connect.
 *
 * @author Bradley Beddoes
 */
public class FacebookRealm {
  static authTokenClass = intient.nimble.auth.FacebookConnectToken

  def grailsApplication
  def facebookService
  def userService

  def authenticate(authToken) {

    if (!grailsApplication.config.nimble.facebook.federationprovider.enabled) {
      log.error("Authentication attempt for Facebook federation provider, denying attempt as Facebook disabled")
      throw new UnknownAccountException("Authentication attempt for Facebook federation provider, denying attempt as Facebook disabled")
    }

    log.info "Attempting to authenticate user via Facebook connect"

    if (facebookService.isLoggedIn(authToken.getCredentials())) {


      def facebookClient = facebookService.getFacebookClient(authToken.principal)
      def userID = facebookClient.users_getLoggedInUser()

      log.info("Facebook security verification succeeded for [$authToken.userID]$userID")

      def user = User.findByUsername(userID + FacebookService.federationProviderDiscriminator)
      if (!user) {
        log.info("No account representing user $userID$FacebookService.federationProviderDiscriminator exists")
        def facebookFederationProvider = FederationProvider.findByUid(FacebookService.federationProviderUid)

        if (facebookFederationProvider && facebookFederationProvider.autoProvision) {

          log.debug("Facebook auto provision is enabled, creating user account for $userID$FacebookService.federationProviderDiscriminator")

          def userList = []
          userList.add(userID)
          def fbProfiles = facebookClient.users_getInfo(userList, EnumSet.of(ProfileField.NAME))

          if (fbProfiles.length() == 1) {
            User newUser = new User()
            newUser.username = userID + FacebookService.federationProviderDiscriminator
            newUser.enabled = true
            newUser.external = true
            newUser.federated = true
            newUser.federationProvider = facebookFederationProvider

            newUser.profile = new Profile()
            newUser.profile.owner = newUser

            newUser.profile.fullName = fbProfiles.getJSONObject(0).get('name')


            SocialMediaAccount facebook = new SocialMediaAccount()
            SocialMediaService facebookMediaService = SocialMediaService.findByUid(FacebookService.socialMediaServiceUid)
            if (facebookMediaService) {
              facebook.username = userID
              facebook.service = facebookMediaService
              Url fbProfile = new Url()
              fbProfile.location = FacebookService.profileUrl + userID
              fbProfile.altText = "Facebook Profile"
              fbProfile.name = SocialMediaAccount.accountProfileUrl

              newUser.profile.addToSocialAccounts(facebook)
            }


            user = userService.createUser(newUser)

            if (user.hasErrors()) {
              log.warn("Error creating user account from facebook credentials for $userID$FacebookService.federationProviderDiscriminator")
              user.errors.each {
                log.warn(it)
              }
              throw new RuntimeException("Account creation exception for new facebook based account");
            }
            log.info("Created new user [$user.id]$user.username from facebook credentials")
          }
        }
        else
          throw new UnknownAccountException("No account representing user $userID$FacebookService.federationProviderDiscriminator exists and creation is disabled")
      }

      if (!user.enabled) {
        log.warn("Attempt to authenticate using using Facebook Account with locally disabled account [$user.id]$user.username")
        throw new DisabledAccountException("The account [$user.id]$user.username accessed via Facebook Connect is disabled")
      }

      def account = new SimpleAccount(user.id, authToken.principal, "FacebookRealm")

      log.info("Successfully logged in user [$user.id]$user.username using Facebook Connect")
      return account
    }
    else {
      throw new IncorrectCredentialsException('Facebook security verification failed, terminating authentication request')
    }
  }
}