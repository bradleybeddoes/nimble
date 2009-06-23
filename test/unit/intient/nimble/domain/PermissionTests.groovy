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
        owner = new PermissionAware()
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
        assertEquals "intient.nimble.auth.AllPermission", Permission.adminPerm
        assertEquals "intient.nimble.auth.WildcardPermission", Permission.defaultPerm
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
