/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
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
package grails.plugin.nimble.core

import grails.test.*

/**
 * @author Bradley Beddoes
 */
class PermissionTests extends GrailsUnitTestCase {
    def type
    def possibleActions
    def actions
    def target
    def managed
    def owner
            
    protected void setUp() {
        super.setUp()
        type = 'type'
        possibleActions = 'possibleActions'
        actions = 'actions'
        target = 'target'
        managed = false
        owner = new UserBase(username: "dilbert")
    }

    protected void tearDown() {
        super.tearDown()
    }

    Permission createValidPermission() {
        def permission = new Permission(type:type, possibleActions:possibleActions,
            actions:actions, target:target, managed:managed, owner:owner)
    }

    void testPermissionCreation() {
        def permission = createValidPermission()

        assertEquals type, permission.type
        assertEquals possibleActions, permission.possibleActions
        assertEquals actions, permission.actions
        assertEquals target, permission.target
        assertEquals managed, permission.managed
        assertEquals owner, permission.owner
    }

    void testDefaultPermissionCreation() {
        def permission = new Permission()

        assertEquals '*', permission.possibleActions
        assertEquals '*', permission.actions
    }

    void testPermissionConstants() {
        assertEquals "grails.plugin.nimble.auth.AllPermission", Permission.adminPerm
        assertEquals "grails.plugin.nimble.auth.WildcardPermission", Permission.defaultPerm
    }

    void testTypeConstraint() {
        mockForConstraintsTests(Permission)
        def permission = createValidPermission()
        assertTrue permission.validate()

        permission.type = ""
        assertFalse permission.validate()

        permission.type = null
        assertFalse permission.validate()
    }

    void testPossibleActionsConstraint() {
        mockForConstraintsTests(Permission)
        def permission = createValidPermission()
        assertTrue permission.validate()

        permission.possibleActions = ""
        assertFalse permission.validate()

        permission.possibleActions = null
        assertFalse permission.validate()
    }

    void testActionsConstraint() {
        mockForConstraintsTests(Permission)
        def permission = createValidPermission()
        assertTrue permission.validate()

        permission.actions = ""
        assertFalse permission.validate()

        permission.actions = null
        assertFalse permission.validate()
    }

    void testTargetConstraint() {
        mockForConstraintsTests(Permission)
        def permission = createValidPermission()
        assertTrue permission.validate()

        permission.target = ""
        assertFalse permission.validate()

        permission.target = null
        assertFalse permission.validate()
    }
}
