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
