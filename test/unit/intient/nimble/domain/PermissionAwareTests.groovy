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

class PermissionAwareTests extends GrailsUnitTestCase {

    def perm1 = new Permission()
    def perm2 = new Permission()
    def perm3 = new Permission()

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    PermissionAware createValidPermissionAware() {
        def permissionAware = new PermissionAware(permissions:[perm1,perm2,perm3])
        return permissionAware
    }

    void testPermissionAwareCreation() {
        def permissionAware = createValidPermissionAware()

        assertTrue permissionAware.permissions.contains(perm1)
        assertTrue permissionAware.permissions.contains(perm2)
        assertTrue permissionAware.permissions.contains(perm3)
    }

    void testPermissionsConstraint() {
        mockForConstraintsTests(PermissionAware)
        def permissionAware = createValidPermissionAware()

        assertTrue permissionAware.validate()

        permissionAware.permissions = []
        assertTrue permissionAware.validate()

        permissionAware.permissions = null
        assertTrue permissionAware.validate()
    }
}
