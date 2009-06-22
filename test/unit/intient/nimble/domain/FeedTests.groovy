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

class FeedTests extends GrailsUnitTestCase {

    def details = new Details()
    def feedUrl = new Url()
    def preferences = [:]

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Feed createValidFeed() {
        def feed = new Feed(details:details, feedUrl:feedUrl, preferences:preferences)
    }

    void testFeedCreation() {
        def feed = createValidFeed()

        assertEquals details, feed.details
        assertEquals feedUrl, feed.feedUrl
        assertEquals preferences, feed.preferences
    }

    void testDetailsConstraint() {
        mockForConstraintsTests(Feed)
        def feed = createValidFeed()

        assertTrue feed.validate()

        feed.details = null
        assertFalse feed.validate()
    }

    void testFeedUrlConstraint() {
        mockForConstraintsTests(Feed)
        def feed = createValidFeed()

        assertTrue feed.validate()

        feed.feedUrl = null
        assertFalse feed.validate()
    }

    void testPreferencesConstraint() {
        mockForConstraintsTests(Feed)
        def feed = createValidFeed()

        assertTrue feed.validate()

        feed.preferences = null
        assertTrue feed.validate()
    }
}
