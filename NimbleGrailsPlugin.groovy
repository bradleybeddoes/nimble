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
 
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.mail.javamail.JavaMailSenderImpl

import org.apache.shiro.authc.credential.Sha256CredentialsMatcher
import org.apache.shiro.SecurityUtils

import grails.plugins.nimble.core.UserBase

class NimbleGrailsPlugin {

    // the plugin version
    def version = "0.4-SNAPSHOT"

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"

    // the other plugins this plugin depends on
    def dependsOn = [ shiro: "1.1.4", mail: "0.6 > *"]
    
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
                      'grails-app/conf/NimbleConfig.groovy',
                      'grails-app/conf/NimbleUrlMappings.groovy',
                      'grails-app/conf/NimbleSecurityFilters.groovy',
                      'grails-app/conf/NimbleBootStrap.groovy',
    ]

    def author = "Bradley Beddoes and  open source contributors"
    def authorEmail = "nimbleproject@googlegroups.com"
    def title = "Nimble"
    def description = '''\\
    Nimble is an extensive application base environment for Grails.
    '''

    // URL to the plugin's documentation
    def documentation = "http://sites.google.com/site/nimbledoc/"

    def observe = ['controllers']

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

		// Supply functionality to filters
		application.filtersClasses.each { filter ->
			// Should be used after verified call to 'accessControl'
			log.debug("Injecting Nimble methods to Filter ${filter}")
			injectAuthn(filter, application)			
		}

        // Supply functionality to controllers
        application.controllerClasses?.each { controller ->
			log.debug("Injecting Nimble methods to Controller ${controller}")
			injectAuthn(controller, application)
        }

        // Supply functionality to services
        application.serviceClasses?.each { service ->
			log.debug("Injecting Nimble methods to Service ${service}")
			injectAuthn(service, application)
        }
    }

    def onChange = { event ->
        doWithDynamicMethods()
    }

    def onConfigChange = { event ->
        
    }

    private void injectAuthn(def clazz, def application) {
		clazz.metaClass.getAuthenticatedSubject = {
        	def subject = SecurityUtils.getSubject()
        }
        clazz.metaClass.getAuthenticatedUser = {
        	def principal = SecurityUtils.getSubject()?.getPrincipal()		
			def authUser
			
            if(application.config?.nimble?.implementation?.user)
    			authUser = grailsApplication.classLoader.loadClass(application.config.nimble.implementation.user).get(principal)
    		else
    			authUser = UserBase.get(principal)

            if (!authUser) {
                log.error("Authenticated user was not able to be obtained from metaclass")
                return null
            }

            return authUser
        }
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
