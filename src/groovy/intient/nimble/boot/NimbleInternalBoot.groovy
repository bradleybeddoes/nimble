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
package intient.nimble.boot

import intient.nimble.service.AdminsService
import intient.nimble.domain.Role
import intient.nimble.service.UserService
import intient.nimble.domain.FederationProvider
import intient.nimble.domain.Details
import intient.nimble.domain.Url
import intient.nimble.domain.SocialMediaService
import intient.nimble.service.OpenIDService
import intient.nimble.service.FacebookService

/**
 * Bootstraps the Nimble base environment on first execution.
 *
 * @author Bradley Beddoes
 */
class NimbleInternalBoot {

  def grailsApplication
  def log

  def init = {servletContext ->

    def userRole = Role.findByName(UserService.USER_ROLE)
    if (!userRole) {
      userRole = new Role()
      userRole.description = 'Issued to all users'
      userRole.name = UserService.USER_ROLE
      userRole.protect = true
      userRole.save()

      if (userRole.hasErrors()) {
        userRole.errors.each {
          log.error(it)
        }
        throw new RuntimeException("Unable to create valid users role")
      }
    }

    def adminRole = Role.findByName(AdminsService.ADMIN_ROLE)
    if (!adminRole) {
      adminRole = new Role()
      adminRole.description = 'Assigned to users who are considered to be system wide administrators'
      adminRole.name = AdminsService.ADMIN_ROLE
      adminRole.protect = true
      adminRole.save()

      if (adminRole.hasErrors()) {
        adminRole.errors.each {
          log.error(it)
        }
        throw new RuntimeException("Unable to create valid administrative role")
      }
    }

    // Setup all supported Social Media services
    def facebookMediaService = SocialMediaService.findByUid(FacebookService.socialMediaServiceUid)
    if (!facebookMediaService) {
      facebookMediaService = new SocialMediaService()
      facebookMediaService.uid = FacebookService.socialMediaServiceUid

      def details = new Details()
      details.name = grailsApplication.config.nimble.facebook.name
      details.displayName = grailsApplication.config.nimble.facebook.displayname
      details.description = grailsApplication.config.nimble.facebook.description
      details.logo = grailsApplication.config.nimble.facebook.logo
      details.logoSmall = grailsApplication.config.nimble.facebook.logosmall

      def url = new Url()
      url.location = grailsApplication.config.nimble.facebook.url
      url.altText = grailsApplication.config.nimble.facebook.alttext

      details.url = url

      facebookMediaService.details = details
      facebookMediaService.save()
      if (facebookMediaService.hasErrors()) {
        facebookMediaService.errors.each {
          log.error(it)
        }
        throw new RuntimeException("Unable to create valid Facebook media service")
      }
    }


    // Setup all supported Federation Providers
    // Facebook
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
        details.logo = grailsApplication.config.nimble.facebook.logo
        details.logoSmall = grailsApplication.config.nimble.facebook.logosmall

        def url = new Url()
        url.location = grailsApplication.config.nimble.facebook.url
        url.altText = grailsApplication.config.nimble.facebook.alttext

        details.url = url

        facebookFederationProvider.details = details

        facebookFederationProvider.preferences = [:]

        facebookFederationProvider.save()
        if (facebookFederationProvider.hasErrors()) {
          facebookFederationProvider.errors.each {
            log.error(it)
          }
          throw new RuntimeException("Unable to create valid Facebook federation provider")
        }
      }
    }

    // OpenID
    if (grailsApplication.config.nimble.openid.federationprovider.enabled) {
      def openidFederationProvider = FederationProvider.findByUid(OpenIDService.federationProviderUid)
      if (!openidFederationProvider) {

        openidFederationProvider = new FederationProvider()
        openidFederationProvider.uid = OpenIDService.federationProviderUid
        openidFederationProvider.autoProvision = grailsApplication.config.nimble.openid.federationprovider.autoprovision

        def details = new Details()
        details.name = grailsApplication.config.nimble.openid.name
        details.displayName = grailsApplication.config.nimble.openid.displayname
        details.description = grailsApplication.config.nimble.openid.description
        details.logo = grailsApplication.config.nimble.openid.logo
        details.logoSmall = grailsApplication.config.nimble.openid.logosmall

        def url = new Url()
        url.location = grailsApplication.config.nimble.openid.url
        url.altText = grailsApplication.config.nimble.openid.alttext

        details.url = url

        openidFederationProvider.details = details

        // Setup OpenID providers whom don't require user details for discovery
        openidFederationProvider.preferences = [:]
        openidFederationProvider.preferences.put(OpenIDService.yahooDiscoveryService, grailsApplication.config.nimble.openid.discovery.yahoo)
        openidFederationProvider.preferences.put(OpenIDService.googleDiscoveryService, grailsApplication.config.nimble.openid.discovery.google)
        openidFederationProvider.preferences.put(OpenIDService.flickrDiscoveryService, grailsApplication.config.nimble.openid.discovery.flickr)

        openidFederationProvider.save()
        if (openidFederationProvider.hasErrors()) {
          openidFederationProvider.errors.each {
            log.error(it)
          }
          throw new RuntimeException("Unable to create valid OpenID federation provider")
        }
      }
    }

    log.info("Completed all internal Nimble bootstrapping")
  }
} 
