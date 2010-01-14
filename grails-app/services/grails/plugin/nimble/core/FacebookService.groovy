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

import org.springframework.beans.factory.InitializingBean
import com.google.code.facebookapi.FacebookJsonRestClient
import com.google.code.facebookapi.FacebookSignatureUtil

/**
 * Provides methods for interacting with Facebook API, currently authentication focused
 * but to be extended to support a wider range of functionality.
 *
 * @author Bradley Beddoes
 */
class FacebookService implements InitializingBean {
  
    boolean transactional = true

    public static final federationProviderUid = "facebookconnect"
    public static final federationProviderDiscriminator = ":facebook"
    public static final socialMediaServiceUid = "facebook"
    public static final profileUrl = "http://www.facebook.com/profile.php?id="

    def grailsApplication

    def facebookMediaService
    def apiKey
    def secretKey
    boolean provisionUsers

    void afterPropertiesSet() {
        this.apiKey = grailsApplication.config.nimble.facebook.apikey
        this.secretKey = grailsApplication.config.nimble.facebook.secretkey
    }

    /**
     * Integrates with extended Nimble bootstrap process, sets up Facebook Environment
     * once all domain objects etc have dynamic methods available to them.
     */
    public void nimbleInit() {
        createFederationProvider()
    }

    /**
     * Provides an instance of a generic Facebook API client
     *
     * @return a client instance
     */
    FacebookJsonRestClient getFacebookClient() {
        log.debug("Providing base Facebook client")
        return getFacebookClient(this.apiKey, this.secretKey, null)
    }

    /**
     * Provides an instance of a Facebook API client scoped to the supplied session
     *
     * @param sessionID The users current sessionID on Facebook
     *
     * @return A client instance
     */
    FacebookJsonRestClient getFacebookClient(String sessionID) {
        log.debug("Providing Facebook client for specific Facebook session $sessionID")
        FacebookJsonRestClient client = new FacebookJsonRestClient(this.apiKey, this.secretKey, sessionID)
        return client
    }

    /**
     * Determines if the user has been successfully logged in via remote facebook servers.
     * Does not indicate the presence of a Nimble based session.
     *
     * @param cookies An array of cookies present in the users web session
     *
     * @return True if the users Facebook session is valid
     */
    def isLoggedIn(def cookies) {
        log.debug("Determining if user cookies indicate a logged in Facebook session")
        boolean validSignature = validateSignature(cookies)
        return validSignature
    }

    /**
     * Inspects cookies associated with user session per:
     * http://wiki.developers.facebook.com/index.php/Verifying_The_Signature
     *
     * To determine if their authentication as advised by Facebook is valid.
     *
     * @param cookies An array of cookies present in the users web session
     *
     * @return True if the users Facebook session is valid
     */
    private boolean validateSignature(def cookies) {

        def fbLoginParameters = extractFacebookLoginParameters(cookies)
        def signature = cookies.find {it?.name == this.apiKey}?.value

        if(fbLoginParameters && signature)
        return FacebookSignatureUtil.verifySignature(fbLoginParameters, this.secretKey, signature)

        return false
    }

    private Map<String, String> extractFacebookLoginParameters(def cookies) {
        Map<String, String> params = new HashMap<String, String>()
        cookies.each {
            if (it && it.name.startsWith("${this.apiKey}_")) {
                def key = it.name.replace("${this.apiKey}_", "")
                def value = it.value

                params.put(key, value)
            }
        }
        return params
    }

    private createFederationProvider() {
        if (grailsApplication.config.nimble.facebook.federationprovider.enabled) {
            def facebookFederationProvider = FederationProvider.findByUid(FacebookService.federationProviderUid)
            if (!facebookFederationProvider) {

                facebookFederationProvider = new FederationProvider()
                facebookFederationProvider.uid = FacebookService.federationProviderUid
                facebookFederationProvider.autoProvision = grailsApplication.config.nimble.facebook.federationprovider.autoprovision

                def details = new Details()
                details.name = grailsApplication.config.nimble.facebook.name
                details.displayName = grailsApplication.config.nimble.facebook.displayname
                details.description = grailsApplication.config.nimble.facebook.description

                def url = new Url()
                url.location = grailsApplication.config.nimble.facebook.url
                url.altText = grailsApplication.config.nimble.facebook.alttext
                def savedUrl = url.save(flush:true)

                if(url.hasErrors()) {
                    url.errors.each {
                        log.error(it)
                    }
                    throw new RuntimeException("Unable to create valid facebook federation provider (url)")
                }

                details.url = savedUrl

                facebookFederationProvider.details = details

                facebookFederationProvider.props = [:]

                facebookFederationProvider.save()
                if (facebookFederationProvider.hasErrors()) {
                    facebookFederationProvider.errors.each {
                        log.error(it)
                    }
                    throw new RuntimeException("Unable to create valid facebook federation provider")
                }
            }
        }
    }
}
