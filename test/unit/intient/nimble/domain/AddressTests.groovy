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
        profile = new Profile()
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
