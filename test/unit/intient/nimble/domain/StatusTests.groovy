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
class StatusTests extends GrailsUnitTestCase {

    def currentStatus 
    def url 
    def dateCreated 
    def lastUpdated 
    def owner 

    protected void setUp() {
        super.setUp()

        currentStatus = 'unit test status'
        url = new Url()
        dateCreated = new Date()
        lastUpdated = new Date()
        owner = new Profile()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Status createValidStatus() {
        def status = new Status(status:currentStatus, url:url, dateCreated:dateCreated,
            lastUpdated:lastUpdated, owner:owner)
        return status
    }

    void testStatusCreation() {
        def status = createValidStatus()

        assertEquals currentStatus, status.status
        assertEquals url, status.url
        assertEquals dateCreated, status.dateCreated
        assertEquals lastUpdated, status.lastUpdated
        assertEquals owner, status.owner
    }

    void testStatusConstraint() {
        mockForConstraintsTests(Status)
        def status = createValidStatus()
        assertTrue status.validate()

        status.status = ''
        assertFalse status.validate()

        status.status = null
        assertFalse status.validate()
    }

    void testUrlConstraint() {
        mockForConstraintsTests(Status)
        def status = createValidStatus()
        assertTrue status.validate()

        status.url = null
        assertTrue status.validate()
    }

    void testDateCreatedConstraint() {
        mockForConstraintsTests(Status)
        def status = createValidStatus()
        assertTrue status.validate()

        status.dateCreated = null
        assertTrue status.validate()
    }

    void testLastUpdatedConstraint() {
        mockForConstraintsTests(Status)
        def status = createValidStatus()
        assertTrue status.validate()

        status.lastUpdated = null
        assertTrue status.validate()
    }
}
