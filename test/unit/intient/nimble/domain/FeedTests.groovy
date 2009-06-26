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
class FeedTests extends GrailsUnitTestCase {
    def details
    def feedUrl
    def properties    

    protected void setUp() {
        super.setUp()
        details = new Details()
        feedUrl = new Url()
        properties = [:]
    }

    protected void tearDown() {
        super.tearDown()
    }

    Feed createValidFeed() {
        def feed = new Feed(details:details, feedUrl:feedUrl, properties:properties)
    }

    void testFeedCreation() {
        def feed = createValidFeed()

        assertEquals details, feed.details
        assertEquals feedUrl, feed.feedUrl
        assertEquals properties, feed.properties
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

        feed.properties = null
        assertTrue feed.validate()
    }
}
