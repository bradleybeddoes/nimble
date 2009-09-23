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

import intient.nimble.domain.Url

/**
 * Represents an external Social media service that is utilized by a user
 *
 * @author Bradley Beddoes
 */
class SocialMediaAccount {

	static config = ConfigurationHolder.config

    String username
    String accountID
    Url profile = null
    SocialMediaService service

    Map properties

    static belongsTo = [owner: Profile]

    static hasMany = [
        feeds: Feed,
        urls: Url
    ]

    static mapping = {
	      table SocialMediaAccount.config.nimble.tablenames.socialmediaaccount
	}

    static constraints = {
        username(nullable: true, blank:false)
        accountID(nullable: true, blank:false)
        profile(nullable:true)
        urls(nullable: true)
        feeds(nullable: true)
        properties(nullable: true)
    }
}
