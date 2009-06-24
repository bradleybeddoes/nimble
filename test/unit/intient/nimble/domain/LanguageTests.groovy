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
class LanguageTests extends GrailsUnitTestCase {
    def scheme 
    def codes

    protected void setUp() {
        super.setUp()
        scheme = 'scheme'
        codes = ['val','val2']
    }

    protected void tearDown() {
        super.tearDown()
    }

    Language createValidLanguage() {
        def language = new Language(scheme:scheme, codes:codes)
    }

    void testLanguageCreation() {
        def language = createValidLanguage()

        assertEquals scheme, language.scheme
        assertTrue codes.containsAll(language.codes)
    }

    void testSchemeConstraint() {
        mockForConstraintsTests(Language)
        
        def language = createValidLanguage()
        assertTrue language.validate()

        language.scheme = null
        assertFalse language.validate()

        language.scheme = ""
        assertFalse language.validate()
    }
}
