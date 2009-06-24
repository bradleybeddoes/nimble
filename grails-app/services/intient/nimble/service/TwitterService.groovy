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
package intient.nimble.service

import intient.nimble.domain.Details
import intient.nimble.domain.SocialMediaService
import intient.nimble.domain.SocialMediaAccount
import intient.nimble.domain.Url

class TwitterService {

    def grailsApplication

    def twitterMediaService
    
    boolean transactional = true

    /**
     * Integrates with extended Nimble bootstrap process, sets up Facebook Environment
     * once all domain objects etc ave dynamic methods available to them.
     */
    public void nimbleInit() {
        twitterMediaService = SocialMediaService.findByUid(grailsApplication.config.nimble.twitter.uid)
        if (!twitterMediaService) {
            twitterMediaService = new SocialMediaService()
            twitterMediaService.uid = grailsApplication.config.nimble.twitter.uid

            def details = new Details()
            details.name = grailsApplication.config.nimble.twitter.name
            details.displayName = grailsApplication.config.nimble.twitter.displayname
            details.description = grailsApplication.config.nimble.twitter.description

            def url = new Url()
            url.location = grailsApplication.config.nimble.twitter.url
            url.altText = grailsApplication.config.nimble.twitter.alttext
            def savedUrl = url.save()
            if(url.hasErrors()) {
                url.errors.each {
                    log.error(it)
                }
                throw new RuntimeException("Unable to create valid OpenID federation provider (url)")
            }

            details.url = savedUrl
            twitterMediaService.details = details

            twitterMediaService.baseProfileUrl = new Url(location: grailsApplication.config.nimble.twitter.profileurl, altText: grailsApplication.config.nimble.twitter.profilealttext)

            twitterMediaService.save()
            if (twitterMediaService.hasErrors()) {
                twitterMediaService.errors.each {
                    log.error(it)
                }
                throw new RuntimeException("Unable to create valid twitter media service")
            }
            log.info("Created Twitter social media service")
        }
    }

    /**
     * Creates all facets of a Twitter social media account for a local user.
     *
     * @param profile A valid profile object.
     * @param userID The twitter username of the current user
     *
     * @return The profile object populated with Twitter scoial account details.
     *
     * @throws RuntimeException If the local twitterMediaService instance is not valid
     */
    def create(def profile, def userID) {
        if(twitterMediaService){
            def twitterAccount = new SocialMediaAccount(accountID:userID, username:userID, service: twitterMediaService)

            def location = twitterMediaService.baseProfileUrl.location.replace(/ACCOUNTID/, twitterAccount.accountID)
            twitterAccount.profile = new Url(location: location, altText: twitterMediaService.baseProfileUrl.altText)
            twitterAccount.owner = profile
            profile.addToSocialAccounts(twitterAccount)

            return profile
        }

        log.error("Unable to locate Twitter social media service when attempting to add Twitter account for [$profile.owner.id]$profile.owner.username, invalid configuration?")
        throw new RuntimeException("Unable to locate Twitter social media service, invalid configuration?")
    }
}
