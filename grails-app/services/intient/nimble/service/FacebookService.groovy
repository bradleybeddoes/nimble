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
  def apiKey
  def secretKey
  boolean provisionUsers

  void afterPropertiesSet() {
    this.apiKey = grailsApplication.config.facebook.apikey
    this.secretKey = grailsApplication.config.facebook.secretkey
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
}