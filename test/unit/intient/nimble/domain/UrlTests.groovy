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
class UrlTests extends GrailsUnitTestCase {

    def name 
    def description 
    def location
    def altText 
    def lang 

    protected void setUp() {
        super.setUp()

         name = 'name'
         description = 'description'
         location = 'http://unit.test.com'
         altText = 'altText'
         lang = new Language()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Url createValidUrl() {
        def url = new Url(name:name, description:description, location:location, altText:altText, lang:lang)
        return url
    }

    void testUrlCreation() {
        def url = createValidUrl()
        
        assertEquals name, url.name
        assertEquals description, url.description
        assertEquals location, url.location
        assertEquals altText, url.altText
        assertEquals lang, url.lang
    }

    void testNameConstraint() {
        mockForConstraintsTests(Url)
        def url = createValidUrl()
        assertTrue url.validate()

        url.name = ''
        assertFalse url.validate()

        url.name = null
        assertTrue url.validate()
    }

    void testDescriptionConstraint() {
        mockForConstraintsTests(Url)
        def url = createValidUrl()
        assertTrue url.validate()

        url.description = ''
        assertFalse url.validate()

        url.description = null
        assertTrue url.validate()
    }

    void testLocationConstraint() {
        mockForConstraintsTests(Url)
        def url = createValidUrl()
        assertTrue url.validate()

        url.location = ''
        assertFalse url.validate()

        url.location = null
        assertFalse url.validate()

        url.location = 'asdfasdf'
        assertFalse url.validate()
    }

    void testAltTextConstraint() {
        mockForConstraintsTests(Url)
        def url = createValidUrl()
        assertTrue url.validate()

        url.altText = ''
        assertFalse url.validate()

        url.altText = null
        assertTrue url.validate()
    }

    void testLangConstraint() {
        mockForConstraintsTests(Url)
        def url = createValidUrl()
        assertTrue url.validate()

        url.lang = null
        assertTrue url.validate()
    }
}
