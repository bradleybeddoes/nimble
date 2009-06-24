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

import intient.nimble.domain.Role
import intient.nimble.domain.User
import intient.nimble.domain.Group

/**
 * Our permission object encapsulates details that a normal Ki deployment
 * would put into mapping tables to make life a little easier.
 *
 * By default type, possibleActions and actions are all setup to be used in the context of being
 * a WildcardPermsission when constructed requiring only target to be set. Of course any of these can
 * be modified or the class extended if custom permission types are required.
 */
class Permission implements Serializable {

    static public final String defaultPerm = "intient.nimble.auth.WildcardPermission"
    static public final String adminPerm = "intient.nimble.auth.AllPermission"

    String type
    String possibleActions = "*"
    String actions = "*"
    String target
    boolean managed

    static belongsTo = [owner:PermissionAware]

    static mapping = {
        cache usage: 'read-write', include: 'all'
    }

    static constraints = {
        type(nullable: false, blank: false)
        possibleActions(nullable: false, blank: false)
        actions(nullable: false, blank: false)
        target(nullable: false, blank: false)
    }
}
