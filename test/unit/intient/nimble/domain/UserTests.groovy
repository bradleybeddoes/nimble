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
class UserTests extends GrailsUnitTestCase {

    def username 
    def passwordHash 
    def actionHash 
    def enabled 
    def external 
    def federated 
    def remoteapi 
    def expiration 
    def federationProvider 
    def profile 
    def role1 
    def role2 
    def group1 
    def group2 
    def pw1 
    def pw2 
    def login1 
    def login2 
    def follows1 
    def follows2 
    def follower1 
    def follower2 
    def dateCreated 
    def lastUpdated 
        
    protected void setUp() {
        super.setUp()

        username = 'username'
        passwordHash = 'passwordHash'
        actionHash = 'actionHash'
        enabled = true
        external = true
        federated = true
        remoteapi = true
        expiration = new Date()

        federationProvider = new FederationProvider()
        profile = new Profile()

        role1 = new Role()
        role2 = new Role()

        group1 = new Group()
        group2 = new Group()

        pw1 = 'pw1'
        pw2 = 'pw2'

        login1 = new LoginRecord()
        login2 = new LoginRecord()

        follows1 = new User()
        follows2 = new User()

        follower1 = new User()
        follower2 = new User()

        dateCreated = new Date()
        lastUpdated = new Date()
    }

    protected void tearDown() {
        super.tearDown()
    }

    User createValidUser() {
        def user = new User(username:username, passwordHash:passwordHash, actionHash:actionHash, enabled:enabled,
            external:external, federated:federated, remoteapi:remoteapi, expiration:expiration,
            federationProvider:federationProvider, profile:profile, roles:[role1,role2],
            groups:[group1,group2], passwdHistory:[pw1,pw2], loginRecords:[login1,login2], 
            follows:[follows1,follows2], followers:[follower1,follower2],
            dateCreated:dateCreated, lastUpdated:lastUpdated)

        return user
    }

    void testUserCreation() {
        def user = createValidUser()
        
        assertEquals username, user.username
        assertEquals passwordHash, user.passwordHash
        assertEquals actionHash, user.actionHash
        assertEquals enabled, user.enabled
        assertEquals external, user.external
        assertEquals federated, user.federated
        assertEquals remoteapi, user.remoteapi
        assertEquals expiration, user.expiration
        assertEquals federationProvider, user.federationProvider
        assertEquals profile, user.profile
        assertEquals dateCreated, user.dateCreated
        assertEquals lastUpdated, user.lastUpdated

        assertTrue user.roles.containsAll([role1,role2])
        assertTrue user.groups.containsAll([group1,group2])
        assertTrue user.passwdHistory.containsAll([pw1,pw2])
        assertTrue user.loginRecords.containsAll([login1,login2])
        assertTrue user.follows.containsAll([follows1,follows2])
        assertTrue user.followers.containsAll([follower1,follower2])
    }

    void testUsernameConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.username = null
        assertFalse user.validate()

        user.username = ''
        assertFalse user.validate()

        // Unique usernames
        def user2 = createValidUser()
        user2.username = 'username2'
        user.username = username
        mockForConstraintsTests(User, [user,user2])

        assertTrue user.validate()
        assertTrue user2.validate()

        user2.username = username
        assertFalse user.validate()
        assertFalse user2.validate()
        assertTrue user.errors.errorCount == 1
        assertEquals 'username', user.errors.fieldError.field

        user2.username = 'username2'
        assertTrue user.validate()
        assertTrue user2.validate()

        // Min length
        user.username = 'ab'
        assertFalse user.validate()

        user.username = username
        assertTrue user.validate()

        // Max length
        user.username = 'abc'.center(2256)
        assertFalse user.validate()
    }

    void testPasswordHashConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.passwordHash = null
        assertTrue user.validate()

        user.passwordHash = ''
        assertFalse user.validate()
    }

    void testActionHashConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.actionHash = null
        assertTrue user.validate()

        user.actionHash = ''
        assertFalse user.validate()
    }

    void testFederationProviderConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.federationProvider = null
        assertTrue user.validate()
    }

    void testProfileConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.profile = null
        assertFalse user.validate()
    }

    void testExpirationConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.expiration = null
        assertTrue user.validate()
    }

    void testDateCreatedConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.dateCreated = null
        assertTrue user.validate()
    }

    void testLastUpdatedConstraint() {
        mockForConstraintsTests(User)

        def user = createValidUser()
        assertTrue user.validate()

        user.lastUpdated = null
        assertTrue user.validate()
    }
}
