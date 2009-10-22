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
import intient.nimble.domain.*

/**
 * @author Bradley Beddoes
 */
class AddressTests extends GrailsUnitTestCase {

    def l1 
    def l2 
    def l3 
    def suburb 
    def city 
    def state 
    def postCode 
    def country 
    def category
    def profile 

    protected void setUp() {
        super.setUp()
        l1 = 'line1'
        l2 = 'line2'
        l3 = 'line3'
        suburb = 'suburb'
        city = 'city'
        state = 'state'
        postCode = '1111'
        country = 'australia'
        category = Address.HOME
        profile = new ProfileBase()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Address createValidAddress() {
        def addr = new Address(line1:l1, line2:l2, line3:l3,
            suburb:suburb, city:city, state:state, postCode:postCode,
            country:country, category:category, owner:profile)

        return addr
    }

    void testCreation() {
        def addr = createValidAddress()
        
        assertEquals l1, addr.line1
        assertEquals l2, addr.line2
        assertEquals l3, addr.line3

        assertEquals suburb, addr.suburb
        assertEquals city, addr.city
        assertEquals state, addr.state
        assertEquals postCode, addr.postCode
        assertEquals country, addr.country
        assertEquals category, addr.category

        assertEquals profile, addr.owner
    }

    void testTypes() {
        assertEquals 'Home', Address.HOME
        assertEquals 'Business', Address.BUSINESS
        assertEquals 'Other', Address.OTHER
    }

    void testLine1Constraint() {
        mockForConstraintsTests(Address)

        def addr = createValidAddress()
        assertTrue addr.validate()

        addr.line1 = ""
        assertFalse addr.validate()

        addr.line1 = null
        assertFalse addr.validate()
    }

    void testCategoryConstraint() {
        mockForConstraintsTests(Address)

        def addr = createValidAddress()
        assertTrue addr.validate()

        addr.category = ""
        assertFalse addr.validate()

        addr.category = null
        assertFalse addr.validate()
    }

    void testCompleteMarkupGeneration() {
        def addr = createValidAddress()
        def markup = addr.markup()

        assertTrue markup.contains("$l1 <br/>")
        assertTrue markup.contains("$l2 <br/>")
        assertTrue markup.contains("$l3 <br/>")
        assertTrue markup.contains("$suburb <br/>")
        assertTrue markup.contains("$city <br/>")
        assertTrue markup.contains("$state <br/>")
        assertTrue markup.contains("$country <br/>")
        assertTrue markup.contains("$postCode <br/>")
    }

    void testPartialMarkupGeneration() {
        def addr = createValidAddress()
        addr.suburb = null
        addr.country = null
        def markup = addr.markup()

        assertTrue markup.contains("$l1 <br/>")
        assertTrue markup.contains("$l2 <br/>")
        assertTrue markup.contains("$l3 <br/>")
        assertFalse markup.contains("$suburb <br/>")
        assertTrue markup.contains("$city <br/>")
        assertTrue markup.contains("$state <br/>")
        assertFalse markup.contains("$country <br/>")
        assertTrue markup.contains("$postCode <br/>")
    }
}
