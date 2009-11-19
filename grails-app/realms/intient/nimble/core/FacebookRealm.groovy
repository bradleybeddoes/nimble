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

import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.DisabledAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authc.IncorrectCredentialsException

import com.google.code.facebookapi.ProfileField
import grails.converters.JSON

import intient.nimble.auth.AccountCreatedException
import intient.nimble.InstanceGenerator

/**
 * Integrates with Shiro to establish a session for users accessing the system based
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

            def user = UserBase.findByUsername(userID + FacebookService.federationProviderDiscriminator)
            if (!user) {
                log.info("No account representing user $userID$FacebookService.federationProviderDiscriminator exists")
                def facebookFederationProvider = FederationProvider.findByUid(FacebookService.federationProviderUid)

                if (facebookFederationProvider && facebookFederationProvider.autoProvision) {

                    log.debug("Facebook auto provision is enabled, creating user account for $userID$FacebookService.federationProviderDiscriminator")

                    def userList = []
                    userList.add(userID)
                    def fbProfiles = facebookClient.users_getInfo(userList, EnumSet.of(ProfileField.NAME))

                    if (fbProfiles.length() == 1) {
                        UserBase newUser = InstanceGenerator.user()
                        newUser.username = userID + FacebookService.federationProviderDiscriminator
                        newUser.enabled = true
                        newUser.external = true
                        newUser.federated = true
                        newUser.federationProvider = facebookFederationProvider

                        newUser.profile = InstanceGenerator.profile()
                        newUser.profile.owner = newUser
                        newUser.profile.fullName = fbProfiles.getJSONObject(0).get('name')

                        // Create a social media account for facebook, FB Connect implies an account :).
                        facebookService.create(newUser.profile, userID)

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