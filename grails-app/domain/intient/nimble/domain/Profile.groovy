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

import org.apache.ki.crypto.hash.Md5Hash

/**
 * Represents generic details users may wish to represent about themselves within
 * a given application.
 *
 * @author Bradley Beddoes
 */
class Profile {

    String fullName
    String nickName
    String email
    String nonVerifiedEmail
    String emailHash
    String bio

    Date dob
    Gender gender

    byte[] photo
    String photoType
    
    boolean gravatar = false
    Status currentStatus

    Map preferences

    Date dateCreated
    Date lastUpdated

    def beforeInsert = {
        hashEmail()
    }

    def beforeUpdate = {
        hashEmail()
    }

    def hashEmail = {
        // Do MD5 hash of email for Gravatar
        if(email) {
            def hasher = new Md5Hash(email)
            emailHash = hasher.toHex()
        }
    }
    
    static belongsTo = [owner:User]

    static hasMany = [
        websites: Url,
        alternateEmails: String,
        feeds: Feed,
        socialAccounts: SocialMediaAccount,
        addresses: Address,
        phoneNumbers: Phone,
        statuses: Status,
    ]

    static mapping = {
        cache usage: 'read-write', include: 'all'
    }

    static constraints = {
        fullName(nullable: true, blank: false)
        nickName(nullable: true, blank: false)
        email(nullable:true, blank:false, email: true, unique: true)
        nonVerifiedEmail(nullable:true, blank:false, email: true)
        emailHash(nullable: true, blank:true)
        bio(nullable: true, blank: false)

        photo(nullable: true)
        photoType(nullable: true, blank:false)
    
        currentStatus(nullable:true)
        gender(nullable: true)
        dob(nullable: true)

        preferences(nullable: true)

        dateCreated(nullable: true) // must be true to enable grails
        lastUpdated(nullable: true) // auto-inject to be useful which occurs post validation
    }
}

public enum Gender {
    Male, Female
}
