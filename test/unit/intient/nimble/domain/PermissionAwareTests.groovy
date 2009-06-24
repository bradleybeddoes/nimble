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
class PermissionAwareTests extends GrailsUnitTestCase {

    def perm1 
    def perm2 
    def perm3 

    protected void setUp() {
        super.setUp()
        perm1 = new Permission()
        perm2 = new Permission()
        perm3 = new Permission()
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
