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

import intient.nimble.domain.LoginRecord
import intient.nimble.domain.Permission
import intient.nimble.domain.Role
import intient.nimble.domain.Group

/**
 * Represents a user within a Nimble Application
 *
 * @author Bradley Beddoes
 */

class User {

    static config = ConfigurationHolder.config

    String username
    String passwordHash
    String actionHash

    boolean enabled
    boolean external
    boolean federated
    boolean remoteapi = false

    FederationProvider federationProvider
    Profile profile

    Date expiration
    Date dateCreated
    Date lastUpdated

    static belongsTo = [Role, Group]

    static hasMany = [
        passwdHistory: String,
        loginRecords: LoginRecord,
        follows: User,
        followers: User,
        roles: Role,
        groups: Group,
        permissions: Permission
    ]

    static fetchMode = [
        roles: 'eager',
        groups: 'eager'
    ]

    static mapping = {
        sort username:'desc'
    
        cache usage: 'read-write', include: 'all'
        table User.config.nimble.tablenames.user

        roles cache: true, cascade: 'none'
        groups cache: true, cascade: 'none'
        permissions cache: true, cascade: 'none'
    }

    static constraints = {
        username(nullable: false, blank: false, unique: true, size: 4..511)
        passwordHash(nullable: true, blank: false)
        actionHash(nullable: true, blank: false)
   
        federationProvider(nullable: true)
        profile(nullable:false)
        
        expiration(nullable: true)

        dateCreated(nullable: true) // must be true to enable grails
        lastUpdated(nullable: true) // auto-inject to be useful which occurs post validation

        permissions(nullable:true)
    }

    // Transients
    static transients = ['pass', 'passConfirm']
    String pass
    String passConfirm

}
