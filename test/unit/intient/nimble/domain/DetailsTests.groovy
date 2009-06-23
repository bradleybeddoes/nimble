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
package intient.nimble.domain

import grails.test.*

/**
 * @author Bradley Beddoes
 */
class DetailsTests extends GrailsUnitTestCase {
    
    def name 
    def displayName 
    def description 
    def url 
    def logo 
    def logoSmall 
        
    protected void setUp() {
        super.setUp()
        name = 'name'
        displayName = 'displayName'
        description = 'description'
        url = new Url()
        logo = 'logo'
        logoSmall = 'logoSmall'
    }

    protected void tearDown() {
        super.tearDown()
    }

    Details createValidDetails() {
        def details = new Details(name:name, displayName:displayName, description:description,
            url:url, logo:logo, logoSmall:logoSmall)

        return details
    }

    void testCreateValidDetails() {
        def details = createValidDetails()

        assertEquals name, details.name
        assertEquals displayName, details.displayName
        assertEquals description, details.description
        assertEquals url, details.url
        assertEquals logo, details.logo
        assertEquals logoSmall, details.logoSmall
    }

    void testNameConstraint() {
        mockForConstraintsTests(Details)
        def details = createValidDetails()

        assertTrue details.validate()

        details.name = ""
        assertFalse details.validate()

        details.name = null
        assertTrue details.validate()
    }

    void testDisplayNameConstraint() {
        mockForConstraintsTests(Details)
        def details = createValidDetails()

        assertTrue details.validate()

        details.displayName = ""
        assertFalse details.validate()

        details.displayName = null
        assertTrue details.validate()
    }

    void testLogoConstraint() {
        mockForConstraintsTests(Details)
        def details = createValidDetails()

        assertTrue details.validate()

        details.logo = ""
        assertFalse details.validate()

        details.logo = null
        assertTrue details.validate()
    }

    void testLogoSmallConstraint() {
        mockForConstraintsTests(Details)
        def details = createValidDetails()

        assertTrue details.validate()

        details.logoSmall = ""
        assertFalse details.validate()

        details.logoSmall = null
        assertTrue details.validate()
    }

    void testUrlConstraint() {
        mockForConstraintsTests(Details)
        def details = createValidDetails()

        assertTrue details.validate()

        details.url = null
        assertTrue details.validate()
    }

    void testDescriptionConstraint() {
        mockForConstraintsTests(Details)
        def details = createValidDetails()

        assertTrue details.validate()

        details.description = ""
        assertFalse details.validate()

        details.description = null
        assertTrue details.validate()
    }
}


