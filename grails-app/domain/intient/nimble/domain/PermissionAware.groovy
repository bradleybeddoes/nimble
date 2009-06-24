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

/**
 * Indicates that an object extending this class is integrated into the Nimble permissions structure
 * and may provide permissions suitable for accessing specific resources.
 *
 * @author Bradley Beddoes
 */
class PermissionAware {

    static hasMany = [permissions: Permission]

    static mapping = {
        cache usage: 'read-write', include: 'all'
        permissions cache: true
        tablePerHierarchy false
    }

    static constraints = {
        permissions(nullable:true)
    }
}
