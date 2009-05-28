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
 
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.apache.ki.authc.credential.Sha256CredentialsMatcher

class NimbleGrailsPlugin {

    // the plugin version
    def version = "0.1"

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"

    // the other plugins this plugin depends on
    def dependsOn = [ ki: 0.1,
                      mail: 0.6,
                    ]
    
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
                      'grails-app/conf/NimbleConfig.groovy',
                      'grails-app/conf/NimbleUrlMappings.groovy',
                      'grails-app/conf/NimbleSecurityFilters.groovy',
                      'grails-app/conf/NimbleBootStrap.groovy',
    ]

    def author = "Intient Pty Ltd + Open Source Contributors"
    def authorEmail = "nimbleproject@googlegroups.com"
    def title = "Nimble"
    def description = '''\\
    Nimble is an extensive application base environment for Grails.
    '''

    // URL to the plugin's documentation
    def documentation = "http://intient.com/oss/nimble"

    def doWithSpring = {
      loadNimbleConfig(application)

      credentialMatcher(Sha256CredentialsMatcher) {
        storedCredentialsHexEncoded = true
      }

      /*
       * Ok we have all the config the user has supplied for Nimble,
       * recreate any objects from dependent plugins who previously had
       * no config
       */
       
      // Redefine mailSender
      def mailConfig = application.config.nimble.messaging.mail
      mailSender(JavaMailSenderImpl) {
            host = mailConfig.host ?: "localhost"
            defaultEncoding = mailConfig.encoding ?: "utf-8"
            if(mailConfig.port)
                port = mailConfig.port
            if(mailConfig.username)
                username = mailConfig.username
            if(mailConfig.password)
                password = mailConfig.password
            if(mailConfig.protocol)
                protocol = mailConfig.protocol
            if(mailConfig.props instanceof Map && mailConfig.props)
                javaMailProperties = mailConfig.props
       } 
    }

    def doWithApplicationContext = { applicationContext ->

    }

    def doWithWebDescriptor = { xml ->

    }

    def doWithDynamicMethods = { ctx ->

    }

    def onChange = { event ->

    }

    def onConfigChange = { event ->
        
    }

    private ConfigObject loadNimbleConfig(GrailsApplication grailsApplication) {
      def config = grailsApplication.config
      GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)

      // Merging default Nimble config into main application config
      config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('DefaultNimbleConfig')))

      // Merging user-defined Nimble config into main application config if provided
      try {
          config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('NimbleConfig')))
      } catch (Exception ignored) {
          // ignore, just use the defaults
      }

      return config
    }
}
