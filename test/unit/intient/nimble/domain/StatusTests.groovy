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
