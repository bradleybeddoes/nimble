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
class PhoneTests extends GrailsUnitTestCase {
    def number 
    def type
    def owner 
        
    protected void setUp() {
        super.setUp()
        number = '1234567890'
        type = PhoneType.Mobile
        owner = new Profile()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Phone createValidPhone() {
        def phone = new Phone(number:number, type:type, owner:owner)
        return phone
    }

    void testPhoneCreation() {
        def phone = createValidPhone()

        assertEquals number, phone.number
        assertEquals type, phone.type
        assertEquals owner, phone.owner
    }

    void testNumberConstraints() {
        mockForConstraintsTests(Phone)
        def phone = createValidPhone()

        assertTrue phone.validate()

        phone.number = null
        assertFalse phone.validate()

        phone.number = ''
        assertFalse phone.validate()

        phone.number = '123'
        assertTrue phone.validate()

        phone.number = 'abc'
        assertFalse phone.validate()

        phone.number = '+61 (07) 1234 5678'
        assertTrue phone.validate()

        phone.number = 'Mobile: 1234 5678 90'
        assertFalse phone.validate()

        phone.number = '1234 5678 x90'
        assertTrue phone.validate()
    }
}
