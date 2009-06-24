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
class LoginRecordTests extends GrailsUnitTestCase {
    def remoteAddr
    def remoteHost
    def userAgent 
    def dateCreated 
    def owner 

    protected void setUp() {
        super.setUp()
        remoteAddr = '1.1.1.1'
        remoteHost = 'remote.host'
        userAgent = 'userAgent'
        dateCreated = new Date()
        owner = new User()
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
        assertTrue loginRecord.validate()
    }

    void testLastUpdatedConstraint() {
        mockForConstraintsTests(LoginRecord)

        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.lastUpdated = null
        assertTrue loginRecord.validate()
    }

    void testOwnerConstraint() {
        mockForConstraintsTests(LoginRecord)
        def loginRecord = createValidLoginRecord()
        assertTrue loginRecord.validate()

        loginRecord.owner = null
        assertFalse loginRecord.validate()
    }
}
