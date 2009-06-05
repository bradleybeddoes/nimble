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

class Phone {

    // Default values for Type
    public static String HOME = "Home"
    public static String WORK = "Work"
    public static String MOBILE = "Mobile"
    public static String OTHER = "Other"

    String countryCode
    String areaCode
    String number
    String ext

    String type

    static mapping = {
        sort type:'desc'
    }

    static constraints = {
        countryCode(nullable:true, blank:true)
        areaCode(nullable:true, blank:true)
        number(nullable:false, blank:false)
        ext(nullable:true, blank: true)
    }

    public String markup() {
        def num = ""

        if(countryCode)
        num = "$num +$countryCode"

        if(areaCode)
        num = "$num ($areaCode)"

        num = "$num $number"

        if(ext)
        num = "$num <br/>Ext: $ext"

        return num
    }
}
