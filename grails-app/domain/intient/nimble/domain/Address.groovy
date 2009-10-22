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

class Address {

    public static String HOME = "Home"
    public static String BUSINESS = "Business"
    public static String OTHER = "Other"

    String line1
    String line2
    String line3

    String suburb
    String city

    String state
    String postCode
    String country

    String category

    static belongsTo = [owner:ProfileBase]

    static constraints = {
        line1(nullable: false, blank: false)
        line2(nullable: true, blank: true)
        line3(nullable: true, blank: true)

        suburb(nullable: true, blank: true)
        city(nullable: true, blank: true)

        state(nullable: true, blank: true)
        postCode(nullable: true, blank: true)
        country(nullable: true, blank:true)

        category(nullable: false, blank: false)
    }

    def String markup() {
        def markup = "$line1 <br/>"

        if(line2)
        markup = "$markup $line2 <br/>"

        if(line3)
        markup = "$markup $line3 <br/>"

        if(suburb)
        markup  = "$markup $suburb <br/>"

        if(city)
        markup =  "$markup $city <br/>"

        if(state)
        markup =  "$markup $state <br/>"

        if(country)
        markup  = "$markup $country <br/>"

        if(postCode)
        markup  = "$markup $postCode <br/>"

        return markup
    }
}
