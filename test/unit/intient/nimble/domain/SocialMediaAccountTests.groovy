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
class SocialMediaAccountTests extends GrailsUnitTestCase {
    def username
    def accountID
    def profile
    def service
    def owner

    def feed1
    def feed2

    def url1
    def url2

    protected void setUp() {
        super.setUp()

        username = 'username'
        accountID = '123456'
        profile = new Url()
        service = new SocialMediaService()
        owner = new Profile()

        feed1 = new Feed()
        feed2 = new Feed()

        url1 = new Url()
        url2 = new Url()
    }

    protected void tearDown() {
        super.tearDown()
    }

    SocialMediaAccount createValidSocialMediaAccount() {
        def account = new SocialMediaAccount(username:username, accountID:accountID,
            profile:profile, service:service, owner:owner, feeds:[feed1,feed2],
            urls:[url1,url2])

        return account
    }

    void testSocialMediaAccountCreate() {
        def account = createValidSocialMediaAccount()

        assertEquals username, account.username
        assertEquals accountID, account.accountID
        assertEquals profile, account.profile
        assertEquals service, account.service
        assertEquals owner, account.owner

        assertTrue account.feeds.containsAll([feed1,feed2])
        assertTrue account.urls.containsAll([url1, url2])
    }

    void testUsernameConstraint() {
        mockForConstraintsTests(SocialMediaAccount)
        def account = createValidSocialMediaAccount()

        assertTrue account.validate()

        account.username = ''
        assertFalse account.validate()

        account.username = null
        assertTrue account.validate()
    }

    void testAccountIDConstraint() {
        mockForConstraintsTests(SocialMediaAccount)
        def account = createValidSocialMediaAccount()

        assertTrue account.validate()

        account.accountID = ''
        assertFalse account.validate()

        account.accountID = null
        assertTrue account.validate()
    }

    void testProfileConstraint() {
        mockForConstraintsTests(SocialMediaAccount)
        def account = createValidSocialMediaAccount()

        assertTrue account.validate()

        account.profile = null
        assertTrue account.validate()
    }

    void testUrlsConstraint() {
        mockForConstraintsTests(SocialMediaAccount)
        def account = createValidSocialMediaAccount()

        assertTrue account.validate()

        account.urls = null
        assertTrue account.validate()
    }

    void testFeedsConstraint() {
        mockForConstraintsTests(SocialMediaAccount)
        def account = createValidSocialMediaAccount()

        assertTrue account.validate()

        account.feeds = null
        assertTrue account.validate()
    }

    void testPreferencesConstraint() {
        mockForConstraintsTests(SocialMediaAccount)
        def account = createValidSocialMediaAccount()

        assertTrue account.validate()

        account.preferences = null
        assertTrue account.validate()
    }
}
