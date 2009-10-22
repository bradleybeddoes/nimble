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
class RoleTests extends GrailsUnitTestCase {

    def description 
    def name 
    def protect 

    def user1 
    def user2 
    def user3 

    def group1 
    def group2 

    def dateCreated 
    def lastUpdated 

    protected void setUp() {
        super.setUp()

        description = 'description'
        name = 'name'
        protect = true

        user1 = new UserBase()
        user2 = new UserBase()
        user3 = new UserBase()

        group1 = new Group()
        group2 = new Group()

        dateCreated = new Date()
        lastUpdated = new Date()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Role createValidRole() {
        def role = new Role(description:description, name:name, protect:protect,
            users:[user1,user2,user3], groups:[group1,group2],
            dateCreated:dateCreated, lastUpdated:lastUpdated)

        return role
    }

    void testRoleCreation() {
        def role = createValidRole()

        assertEquals description, role.description
        assertEquals name, role.name
        assertEquals protect, role.protect
        assertTrue role.users.containsAll([user1,user2,user3])
        assertTrue role.groups.containsAll([group1,group2])
    }

    void testNameConstraint() {
        mockForConstraintsTests(Role)
        def role = createValidRole()

        assertTrue role.validate()

        role.name = ''
        assertFalse role.validate()

        role.name = null
        assertFalse role.validate()

        // must be unique
        def role2 = createValidRole()
        role2.name = 'name2'
        role.name = name
        mockForConstraintsTests(Role, [role, role2])

        assertTrue role.validate()
        assertTrue role2.validate()

        role2.name = name
        assertFalse role.validate()
        assertFalse role2.validate()

        role2.name = 'name2'
        assertTrue role.validate()
        assertTrue role2.validate()

        //min size
        role.name = '123'
        assertFalse role.validate()

        //max size
        role.name = 'abcd'.center(532)
        assertFalse role.validate()
    }

    void testDescriptionConstraint() {
        mockForConstraintsTests(Role)
        def role = createValidRole()

        assertTrue role.validate()

        role.description = ''
        assertFalse role.validate()

        role.description = null
        assertTrue role.validate()
    }

    void testDateCreatedConstraint() {
        mockForConstraintsTests(Role)

        def role = createValidRole()
        assertTrue role.validate()

        role.dateCreated = null
        assertTrue role.validate()
    }

    void testLastUpdatedConstraint() {
        mockForConstraintsTests(Role)

        def role = createValidRole()
        assertTrue role.validate()

        role.lastUpdated = null
        assertTrue role.validate()
    }
}
