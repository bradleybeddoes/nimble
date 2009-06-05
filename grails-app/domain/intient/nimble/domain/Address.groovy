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

import intient.nimble.domain.User
import intient.nimble.domain.Profile

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

    static belongsTo = [owner:Profile]

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
