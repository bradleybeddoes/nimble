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
class SocialMediaServiceTests extends GrailsUnitTestCase {

    def uid 
    def details 
    def baseProfileUrl 

    protected void setUp() {
        super.setUp()

        uid = 'serviceuid'
        details = new Details()
        baseProfileUrl = new Url()
    }

    protected void tearDown() {
        super.tearDown()
    }

    SocialMediaService createValidSocialMediaService() {
        def service = new SocialMediaService(uid:uid, details:details, baseProfileUrl:baseProfileUrl)
        return service
    }

    void testSocialMediaServiceCreation() {
        def service = createValidSocialMediaService()

        assertEquals uid, service.uid
        assertEquals details, service.details
        assertEquals baseProfileUrl, service.baseProfileUrl
    }

    void testUidConstraint() {
        mockForConstraintsTests(SocialMediaService)
        def service = createValidSocialMediaService()
        assertTrue service.validate()

        service.uid = null
        assertFalse service.validate()

        service.uid = ''
        assertFalse service.validate()
    }

    void testDetailsConstraint() {
        mockForConstraintsTests(SocialMediaService)
        def service = createValidSocialMediaService()
        assertTrue service.validate()

        service.details = null
        assertFalse service.validate()
    }

    void testBaseProfileUrlConstraint() {
        mockForConstraintsTests(SocialMediaService)
        def service = createValidSocialMediaService()
        assertTrue service.validate()

        service.baseProfileUrl = null
        assertTrue service.validate()
    }

    void testPreferencesConstraint() {
        mockForConstraintsTests(SocialMediaService)
        def service = createValidSocialMediaService()
        assertTrue service.validate()

        service.preferences = null
        assertTrue service.validate()
    }
}
