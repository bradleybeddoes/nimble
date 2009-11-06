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

import org.apache.shiro.crypto.hash.Md5Hash

/**
 * Represents generic details users may wish to store about themselves within
 * a given application.
 *
 * @author Bradley Beddoes
 */
class ProfileBase {

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
    
    static belongsTo = [owner:UserBase]

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

        photo(nullable: true, maxSize: 4194304)
        photoType(nullable: true, blank:false)
    
        currentStatus(nullable:true)
        gender(nullable: true)
        dob(nullable: true)

        dateCreated(nullable: true) // must be true to enable grails
        lastUpdated(nullable: true) // auto-inject to be useful which occurs post validation
    }
}

public enum Gender {
    Male, Female
}
