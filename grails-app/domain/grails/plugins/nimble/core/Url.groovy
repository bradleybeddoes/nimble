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
 * Represents a web based url with extended information for display purposes.
 * A Url object does not belong to any parent object as its use is general in nature,
 * developers are advised they are responsible for saving and deleting this object. Grails
 * will not automatically do this for you.
 *
 * @author Bradley Beddoes
 */
class Url {

    String name
    String description

    String location
    String altText

    static belongsTo = [Details, ProfileBase]

    static mapping = {
        table ConfigurationHolder.config.nimble.tablenames.url
    }
    static constraints = {
        name(nullable: true, blank:false)
        description(nullable: true, blank:false)
        location(nullable: false, blank: false, url: true)
        altText(nullable: true, blank: false)
    }
}
