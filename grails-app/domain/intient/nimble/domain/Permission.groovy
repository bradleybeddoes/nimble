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

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Our permission object encapsulates details that a normal Shiro deployment
 * would put into mapping tables to make life a little easier.
 *
 * @author Bradley Beddoes
 */
class Permission implements Serializable {

    static public final String defaultPerm = "intient.nimble.auth.WildcardPermission"
    static public final String wildcardPerm = "intient.nimble.auth.WildcardPermission"
    static public final String adminPerm = "intient.nimble.auth.AllPermission"

    String type
    String possibleActions = "*"
    String actions = "*"
    String target
    boolean managed

    // Explicitly declared to work around this issue:
    //
    //    http://jira.codehaus.org/browse/GRAILS-5238
    //
    // Once that's fixed, these three fields can be removed.
    UserBase user
    Role role
    Group group

    static belongsTo = [user: UserBase, role: Role, group:Group]

    static transients = [ "owner" ]

    static mapping = {
        cache usage: 'read-write', include: 'all'
    }

    static constraints = {
        type(nullable: false, blank: false)
        possibleActions(nullable: false, blank: false)
        actions(nullable: false, blank: false)
        target(nullable: false, blank: false)

        user(nullable:true)
        role(nullable:true)
        group(nullable:true)
    }

    def setOwner (def owner) {
        if (owner instanceof UserBase)
        this.user = owner

        if (owner instanceof Role)
        this.role = owner

        if (owner instanceof Group)
        this.group = owner
    }

    def getOwner() {
        if(this.user != null)
        return user

        if(this.role != null)
        return role

        if(this.group != null)
        return group

        return null
    }
}
