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

        user1 = new User()
        user2 = new User()

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
