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
class FederationProviderTests extends GrailsUnitTestCase {

    def uid 
    def details 
    def autoProvision 
    def properties

    protected void setUp() {
        super.setUp()
        uid = 'uid'
        details = new Details()
        autoProvision = true
        properties = [:]
    }

    protected void tearDown() {
        super.tearDown()
    }

    FederationProvider createValidFederationProvider() {
        def federationProvider = new FederationProvider(uid:uid, details:details, autoProvision:autoProvision, properties:properties)
        return federationProvider
    }

    void testFederationProviderCreation() {
        def federationProvider = createValidFederationProvider()
        
        assertEquals uid, federationProvider.uid
        assertEquals details, federationProvider.details
        assertEquals autoProvision, federationProvider.autoProvision
        assertEquals properties, federationProvider.properties
    }

    void testUidConstraint() {
        mockForConstraintsTests(FederationProvider)
        def federationProvider = createValidFederationProvider()
        assertTrue federationProvider.validate()

        federationProvider.uid = null
        assertFalse federationProvider.validate()
    }

    void testDetailsConstraint() {
        mockForConstraintsTests(FederationProvider)
        def federationProvider = createValidFederationProvider()
        assertTrue federationProvider.validate()

        federationProvider.details = null
        assertFalse federationProvider.validate()
    }

    void testPreferencesConstraint() {
        mockForConstraintsTests(FederationProvider)
        def federationProvider = createValidFederationProvider()
        assertTrue federationProvider.validate()

        federationProvider.properties = null
        assertTrue federationProvider.validate()
    }
}
