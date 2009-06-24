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
