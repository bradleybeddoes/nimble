/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
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
package grails.plugins.nimble.core

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Represents a grouping of users in a Nimble based appication
 *
 * @author Bradley Beddoes
 */
class Group implements Serializable {

    String name
    String description
	String realm
	
	boolean external = false
    boolean protect = false

    Date dateCreated
    Date lastUpdated

    static hasMany = [
        roles: Role,
        users: UserBase,
        permissions: Permission
    ]

    static mapping = {
        cache usage: 'read-write', include: 'all'
        table ConfigurationHolder.config.nimble.tablenames.group

        users cache: true
        roles cache: true
        permissions cache: true
    }

    static constraints = {
        name(blank: false, unique: true, minSize:4, maxSize: 255)
        description(nullable: true, blank: false)
		realm(nullable: true, blank: false)

        dateCreated(nullable: true) // must be true to enable grails
        lastUpdated(nullable: true) // auto-inject to be useful which occurs post validation

        permissions(nullable:true)
    }
}
