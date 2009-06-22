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

class LoginRecordTests extends GrailsUnitTestCase {
    def remoteAddr = '1.1.1.1'
    def remoteHost = 'remote.host'
    def userAgent = 'userAgent'
    def dateCreated = new Date()
    def owner = new User()

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    LoginRecord createValidLoginRecord() {
        def loginRecord = new LoginRecord(owner:owner, remoteAddr:remoteAddr, remoteHost:remoteHost, userAgent:userAgent, dateCreated:dateCreated)
    }

    void testLoginRecordCreation() {
        def loginRecord = createValidLoginRecord()
        
        assertEquals remoteAddr, loginRecord.remoteAddr
        assertEquals remoteHost, loginRecord.remoteHost
        assertEquals userAgent, loginRecord.userAgent
        assertEquals dateCreated, loginRecord.dateCreated
        assertEquals owner, loginRecord.owner
    }

    void testRemoteAddrConstraint() {
        mockForConstraintsTests(LoginRecord)
        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.remoteAddr = ""
        assertFalse loginRecord.validate()

        loginRecord.remoteAddr = null
        assertFalse loginRecord.validate()
    }

    void testRemoteHostConstraint() {
        mockForConstraintsTests(LoginRecord)
        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.remoteHost = ""
        assertFalse loginRecord.validate()

        loginRecord.remoteHost = null
        assertFalse loginRecord.validate()
    }

    void testUserAgentConstraint() {
        mockForConstraintsTests(LoginRecord)
        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.userAgent = ""
        assertFalse loginRecord.validate()

        loginRecord.userAgent = null
        assertFalse loginRecord.validate()
    }

    void testDateCreatedConstraint() {
        mockForConstraintsTests(LoginRecord)
        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.dateCreated = null
        assertFalse loginRecord.validate()
    }

    void testOwnerConstraint() {
        mockForConstraintsTests(LoginRecord)
        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.owner = null
        assertFalse loginRecord.validate()
    }
}
