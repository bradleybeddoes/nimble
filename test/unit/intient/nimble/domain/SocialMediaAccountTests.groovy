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
