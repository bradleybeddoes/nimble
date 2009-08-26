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
package intient.nimble

import intient.nimble.domain.User
import intient.nimble.domain.Profile

/**
 * Determines correct version of class to load for classes commonly overloaded by host applications
 *
 * @author Bradley Beddoes
 */
class InstanceGenerator {

    static user = { try { InstanceGenerator.class.classLoader.loadClass("User").newInstance()} catch(ClassNotFoundException e){User.newInstance()} }
    static profile = { try { InstanceGenerator.class.classLoader.loadClass("Profile").newInstance()} catch(ClassNotFoundException e){Profile.newInstance()} }

}

