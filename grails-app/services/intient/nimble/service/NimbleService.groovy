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
