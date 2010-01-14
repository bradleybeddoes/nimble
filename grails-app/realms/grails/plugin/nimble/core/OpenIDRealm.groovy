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

import org.openid4java.consumer.ConsumerManager
import org.openid4java.discovery.DiscoveryInformation
import org.openid4java.message.AuthRequest

import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.DisabledAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authc.IncorrectCredentialsException

import grails.plugin.nimble.InstanceGenerator
import grails.plugin.nimble.auth.OpenIDToken

/**
 * Integrates with Shiro to establish a session for users accessing the system based
 * on authentication with an OpenID provider.
 *
 * @author Bradley Beddoes
 */
public class OpenIDRealm {

  static authTokenClass = grails.plugin.nimble.auth.OpenIDToken

  def grailsApplication
  def openIDService
  def userService

  def authenticate(OpenIDToken authToken) {

    if (!grailsApplication.config.nimble.openid.federationprovider.enabled) {
      log.error("Authentication attempt for OpenID based federation provider, denying attempt as OpenID disabled")
      throw new UnknownAccountException("Authentication attempt for OpenID federation provider, denying attempt as OpenID disabled")
    }

    log.info "Attempting to authenticate user via OpenID"

    def userID = authToken.getUserID()
    def user = UserBase.findByUsername(userID + OpenIDService.federationProviderDiscriminator)
    if (!user) {
      log.info("No account representing user $userID$OpenIDService.federationProviderDiscriminator exists")
      def openidFederationProvider = FederationProvider.findByUid(OpenIDService.federationProviderUid)

      if (openidFederationProvider && openidFederationProvider.autoProvision) {

        UserBase newUser = InstanceGenerator.user()
        newUser.username = userID + OpenIDService.federationProviderDiscriminator
        newUser.enabled = true
        newUser.external = true
        newUser.federated = true
        newUser.federationProvider = openidFederationProvider

        newUser.profile = InstanceGenerator.profile()
        newUser.profile.owner = newUser
        
        newUser.profile.fullName = authToken.fullName
        newUser.profile.nickName = authToken.nickName
        newUser.profile.email = authToken.email

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