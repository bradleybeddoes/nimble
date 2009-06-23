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
