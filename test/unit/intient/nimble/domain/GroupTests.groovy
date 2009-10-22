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
class GroupTests extends GrailsUnitTestCase {
    def name 
    def description 
    def protect 

    def user1 
    def user2 

    def role1 
    def role2 

    def dateCreated 
    def lastUpdated 

    protected void setUp() {
        super.setUp()
        name = 'name'
        description = 'description'
        protect = true

        user1 = new UserBase()
        user2 = new UserBase()

        role1 = new Role()
        role2 = new Role()

        dateCreated = new Date()
        lastUpdated = new Date()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Group createValidGroup() {
        def group = new Group(name:name, description:description, protect:protect,
            users:[user1,user2], roles:[role1,role2], dateCreated:dateCreated, lastUpdated:lastUpdated)
    }

    void testGroupCreation() {
        def group = createValidGroup()

        assertEquals name, group.name
        assertEquals description, group.description
        assertEquals protect, group.protect

        assertTrue group.users.containsAll([user1,user2])
        assertTrue group.roles.containsAll([role1,role2])
    }

    void testNameConstraints() {
        mockForConstraintsTests(Group)
        def group = createValidGroup()

        group.validate()
        group.errors.each {
            println it
        }

        assertTrue group.validate()

        group.name = ''
        assertFalse group.validate()

        group.name = null
        assertFalse group.validate()

        // must be unique
        def group2 = createValidGroup()
        group2.name = 'name2'
        group.name = name
        mockForConstraintsTests(Group, [group, group2])

        assertTrue group.validate()
        assertTrue group2.validate()

        group2.name = name
        assertFalse group.validate()
        assertFalse group2.validate()

        group2.name = 'name2'
        assertTrue group.validate()
        assertTrue group2.validate()

        //min size
        group.name = '123'
        assertFalse group.validate()

        //max size
        group.name = 'abcd'.center(532)
        assertFalse group.validate()
    }

    void testDescriptionConstraint() {
        mockForConstraintsTests(Group)
        def group = createValidGroup()

        assertTrue group.validate()

        group.description = ''
        assertFalse group.validate()

        group.description = null
        assertTrue group.validate()
    }

    void testDateCreatedConstraint() {
        mockForConstraintsTests(Group)

        def group = createValidGroup()
        assertTrue group.validate()

        group.dateCreated = null
        assertTrue group.validate()
    }

    void testLastUpdatedConstraint() {
        mockForConstraintsTests(Group)

        def group = createValidGroup()
        assertTrue group.validate()

        group.lastUpdated = null
        assertTrue group.validate()
    }
}
