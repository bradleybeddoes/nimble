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


