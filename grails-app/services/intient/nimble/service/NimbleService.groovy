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

import org.apache.ki.SecurityUtils

import intient.nimble.domain.User
import intient.nimble.domain.Role

/**
 * Various Nimble specific pieces of logic, should need to be called by any
 * host application.
 *
 * @author Bradley Beddoes
 */
class NimbleService {

    def grailsApplication
    
    boolean transactional = true

    /**
     * Integrates with extended Nimble bootstrap process, sets up basic Nimble environment
     * once all domain objects etc ave dynamic methods available to them.
     */
    def init = {

        // Perform all base Nimble setup
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

        // Execute all service init that relies on base Nimble environment
        def services = grailsApplication.getArtefacts("Service")
        for (service in services) {
            if(service.clazz.methods.find{it.name == 'nimbleInit'} != null) {
                def serviceBean = grailsApplication.mainContext.getBean(service.propertyName)
                serviceBean.nimbleInit()
            }
        }

        /**
         * This is some terribly hacky shit to fix a major problem once Nimble
         * was upgraded to use 1.1.1 (possibly groovy not grails specific)
         *
         * TODO: remove this ugly ugly piece of crud when 1.1.2 or 1.2 comes out
         * BUG: http://jira.codehaus.org/browse/GRAILS-4580
         */
        def domains = grailsApplication.getArtefacts("Domain")
        for (domain in domains) {
            domain.clazz.count()
        }
    }
}
