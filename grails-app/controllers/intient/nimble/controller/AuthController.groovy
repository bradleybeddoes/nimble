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

import org.apache.ki.SecurityUtils
import org.apache.ki.authc.AuthenticationException
import org.apache.ki.authc.UsernamePasswordToken
import intient.nimble.auth.FacebookConnectToken
import javax.servlet.http.Cookie
import intient.nimble.service.FacebookService
import intient.nimble.auth.AccountCreatedException
import org.openid4java.message.ParameterList
import intient.nimble.service.OpenIDService
import org.apache.ki.authc.IncorrectCredentialsException
import org.apache.ki.authc.DisabledAccountException

/**
 * Manages all authentication processes including integration with OpenID, Facebook etc.
 *
 * @author Bradley Beddoes
 */
class AuthController {

  def kiSecurityManager 
  def facebookService
  def openIDService
  def grailsApplication

  static Map allowedMethods = [ login: 'GET', signin: 'POST', logout: 'GET', signout: 'GET',
                                unauthorized: 'GET', facebook: 'GET']


  def index = { redirect(action: 'login', params: params) }

  def login = {
    def facebook = grailsApplication.config.nimble.facebook.federationprovider.enabled
    def openid = grailsApplication.config.nimble.openid.federationprovider.enabled

    render(template: "/templates/nimble/login/login", model: [facebook: facebook, openid: openid, username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri])
  }

  def signin = {
    def authToken = new UsernamePasswordToken(params.username, params.password)

    if (params.rememberMe) {
      authToken.rememberMe = true
    }

    log.info("Attempting to authenticate user, $params.username. RememberMe is $authToken.rememberMe")

    try {
      this.kiSecurityManager.login(authToken)

      def targetUri = params.targetUri ?: "/"

      log.info("Authenticated user, $params.username. Directing to content $targetUri")

      log.info "Redirecting to '${targetUri}'."
      redirect(uri: targetUri)
    }
    catch (IncorrectCredentialsException e) {
      log.info "Credentials failure for user '${params.username}'."
      log.debug(e)
      
      flash.type = 'error'
      flash.message = message(code: "login.failed.credentials")
      redirect(action: 'login')
    }
    catch (DisabledAccountException e) {
      log.info "Attempt to login to disabled account for user '${params.username}'."
      log.debug(e)

      flash.type = 'error'
      flash.message = message(code: "login.failed.disabled")
      redirect(action: 'login')
    }
    catch (AuthenticationException e) {
      log.info "General authentication failure for user '${params.username}'."
      log.debug(e)

      flash.type = 'error'
      flash.message = message(code: "login.failed.general")
      redirect(action: 'login')
    }
  }

  def logout = {
    signout()
  }

  def signout = {
    log.info("Signing out user")
    SecurityUtils.subject?.logout()
    redirect(uri: '/')
  }

  def unauthorized = {
    response.sendError(403)
  }

  /**
   * OpenID integration
   */
  def openidreq = {
    log.debug("Performing openidreq")
    performOpenIDRequest("openid", params, request, response)
  }

  def openidresp = {
    log.debug("Performing openidresp")
    processOpenIDResponse("openid", params, request, response)
  }

  def yahooreq = {
    log.debug("Performing yahooreq")
    performOpenIDRequest("yahoo", params, request, response)
  }

  def yahooresp = {
    log.debug("Performing yahooresp")
    processOpenIDResponse("yahoo", params, request, response)
  }

  def flickrreq = {
    log.debug("Performing flickrreq")
    performOpenIDRequest("flickr", params, request, response)
  }

  def flickrresp = {
    log.debug("Performing flickrresp")
    processOpenIDResponse("flickr", params, request, response)
  }

  def googlereq = {
    log.debug("Performing googlereq")
    performOpenIDRequest("google", params, request, response)
  }

  def googleresp = {
    log.debug("Performing googleresp")
    processOpenIDResponse("google", params, request, response)
  }

  def bloggerreq = {
    log.debug("Performing bloggerreq")
    performOpenIDRequest("blogger", params, request, response)
  }

  def bloggerresp = {
    log.debug("Performing bloggerresp")
    processOpenIDResponse("blogger", params, request, response)
  }

  def wordpressreq = {
    log.debug("Performing wordpressreq")
    performOpenIDRequest("wordpress", params, request, response)
  }

  def wordpressresp = {
    log.debug("Performing wordpressresp")
    processOpenIDResponse("wordpress", params, request, response)
  }

  def technoratireq = {
    if (params.technoratiusername) {
      log.debug("Performing technoratireq for $params.technoratiusername")
      params.put('openiduri', 'http://technorati.com/people/technorati/' + params.technoratiusername)
      performOpenIDRequest("technorati", params, request, response)
    }
    else {
      log.debug("Erronous technoratireq no username")
      flash.type = 'error'
      flash.message = message(code: "login.technorati.no.username")
      redirect(action: 'login', params: [active: 'technorati'])
    }
  }

  def technoratiresp = {
    log.debug("Performing technoratiresp")
    processOpenIDResponse("technorati", params, request, response)
  }


  /**
   * Facebook Connect integration
   */
  def facebook = {

    if (!grailsApplication.config.nimble.facebook.federationprovider.enabled) {
      log.error("Authentication attempt for Facebook federation provider, denying attempt as Facebook disabled")
      response.sendError(403)
      return
    }

    log.info("Attempting to authenticate facebook user")

    if (request.cookies != null) {
      def currentFBSessionKey = request.cookies.find { it.name.equals(facebookService.apiKey + '_session_key') }?.value
      def currentFBSessionCookies = new Cookie[request.cookies.length]

      // Process Facebook supplied cookies per FB Connect API docs
      int i = 0;
      request.cookies.each {cookie ->
        if (cookie.name.startsWith(facebookService.apiKey)) {
          currentFBSessionCookies[i] = cookie
          i++
        }
      }

      def authToken = new FacebookConnectToken(currentFBSessionKey, currentFBSessionCookies)
      try {
        this.kiSecurityManager.login(authToken)

        def targetUri = params.targetUri ?: "/"

        log.info("Authenticated facebook user. Directing to content $targetUri")
        redirect(uri: targetUri)
      }
      catch (AuthenticationException ex) {
        log.warn "Facebook authentication failure - ${ex.getLocalizedMessage()}"
        log.debug ex.printStackTrace()
        flash.type = 'error'
        flash.message = message(code: "login.facebook.failed")
        redirect(action: 'login', params: [active: 'facebook'])
      }
    }
    else {
      log.warn "Facebook authentication failure - no session cookies present"
      flash.type = 'error'
      flash.message = message(code: "login.facebook.cookies")
      redirect(action: 'login', params: [active: 'facebook'])
    }
  }

  def facebookxdreciever = {

  }

  def facebookxdrecieverssl = {

  }

  private performOpenIDRequest = {service, params, request, response ->

    if (!grailsApplication.config.nimble.openid.federationprovider.enabled) {
      log.error("Authentication attempt (request) for OpenID based federation provider, denying attempt as OpenID disabled")
      response.sendError(403)
      return
    }

    log.info("Attempting to authenticate $service openID user")

    def serviceIdentifier
    def discovered
    def authRequest

    def responseUrl = createLink(action: "${service}resp", absolute: true)

    if (params.openiduri != null) {
      serviceIdentifier = params.openiduri
      (discovered, authRequest) = openIDService.establishRequest(serviceIdentifier, responseUrl)
    }
    else {
      serviceIdentifier = service
      (discovered, authRequest) = openIDService.establishDiscoveryRequest(serviceIdentifier, responseUrl)
    }

    if (discovered && authRequest) {

      log.info("Successfully discovered details for openid service $serviceIdentifier redirecting client")

      session.setAttribute("discovered", discovered)
      if (!discovered.isVersion2()) {
        response.sendRedirect(authRequest.getDestinationUrl(true))
        return
      }

      render(view: "openidreq", model: [openidreqparams: authRequest.getParameterMap(), destination: authRequest.getDestinationUrl(false)])
    }
    else {
      log.warn("Unable to discover details for openid service $serviceIdentifier redirecting client")

      flash.type = 'error'
      flash.message = message(code: "login.${service}.internal.error.req")
      redirect(action: 'login', params: [active: service])
    }
  }

  private processOpenIDResponse = {service, params, request, response ->

    if (!grailsApplication.config.nimble.openid.federationprovider.enabled) {
      log.error("Authentication attempt (response) for OpenID based federation provider, denying attempt as OpenID disabled")
      response.sendError(403)
      return
    }
    
    def discovered = session.getAttribute("discovered")
    ParameterList openIDResponse = new ParameterList(request.getParameterMap());

    StringBuffer receivingUrl = new StringBuffer(createLink(action: "${service}resp", absolute: true))
    String queryString = request.getQueryString();
    if (queryString != null && queryString.length() > 0)
      receivingUrl.append("?").append(request.getQueryString());

    def authToken = openIDService.processResponse(discovered, openIDResponse, receivingUrl.toString())

    if (authToken) {
      try {
        this.kiSecurityManager.login(authToken)

        def targetUri = params.targetUri ?: "/"

        log.info("Authenticated openID user $authToken.userID. Directing to content $targetUri")
        redirect(uri: targetUri)
      }
      catch (AuthenticationException ex) {
        log.warn "OpenID authentication failure for $authToken.userID - ${ex.getLocalizedMessage()}"
        log.debug ex.printStackTrace()
        flash.type = 'error'
        flash.message = message(code: "login.openid.${service}.failed")
        redirect(action: 'login', params: [active: service])
      }
    }
    else {
      log.debug ("OpenID authentication failure")
      
      flash.type = 'error'
      flash.message = message(code: "login.openid.${service}.internal.error.res")
      redirect(action: 'login', params: [active: service])
    }
  }
}
