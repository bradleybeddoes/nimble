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

import grails.test.*

class ProfileTests extends GrailsUnitTestCase {

    def fullName = 'fullName'
    def nickName = 'nickName'
    def email = 'test@unit.org'
    def nonVerifiedEmail = 'non@verified.org'
    def bio = 'unit test user biography'
    def dob = new Date()
    def gender = Gender.Female

    def photo = 'thiscouldbephotodatareally...'.bytes
    def photoType = 'png'

    def gravatar = false
    def currentStatus = new Status()

    def dateCreated = new Date()
    def lastUpdated = new Date()

    def website1 = new Url()
    def website2 = new Url()

    def alternateEmail1 = 'test@junit.org'
    def alternateEmail2 = 'test@nounit.org'

    def feed1 = new Feed()
    def feed2 = new Feed()

    def socialAccount1 = new SocialMediaAccount()
    def socialAccount2 = new SocialMediaAccount()

    def address1 = new Address()
    def address2 = new Address()

    def phoneNumber1 = new Phone()
    def phoneNumber2 = new Phone()

    def histStatus1 = new Status()
    def histStatus2 = new Status()
        
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    Profile createValidProfile() {
        def profile = new Profile(fullName:fullName, nickName:nickName, email:email,
            nonVerifiedEmail:nonVerifiedEmail, bio:bio, dob:dob, gender:gender, photo:photo,
            photoType:photoType, gravatar:gravatar, currentStatus:currentStatus, dateCreated:dateCreated,
            lastUpdated:lastUpdated, websites:[website1,website2], alternateEmails:[alternateEmail1,alternateEmail2],
            feeds:[feed1,feed2], socialAccounts:[socialAccount1,socialAccount2],
            addresses:[address1,address2], phoneNumbers:[phoneNumber1,phoneNumber2],
            statuses:[histStatus1,histStatus2])

        return profile
    }

    void testProfileCreation() {
        def profile = createValidProfile()    

        assertEquals fullName, profile.fullName
        assertEquals nickName, profile.nickName
        assertEquals email, profile.email
        assertEquals nonVerifiedEmail, profile.nonVerifiedEmail
        assertEquals bio, profile.bio
        assertEquals dob, profile.dob
        assertEquals gender, profile.gender
        assertEquals photo, profile.photo
        assertEquals photoType, profile.photoType
        assertEquals gravatar, profile.gravatar
        assertEquals currentStatus, profile.currentStatus
        assertEquals dateCreated, profile.dateCreated
        assertEquals lastUpdated, profile.lastUpdated

        assertTrue profile.websites.contains(website1)
        assertTrue profile.websites.contains(website2)

        assertTrue profile.alternateEmails.contains(alternateEmail1)
        assertTrue profile.alternateEmails.contains(alternateEmail2)

        assertTrue profile.feeds.contains(feed1)
        assertTrue profile.feeds.contains(feed2)

        assertTrue profile.socialAccounts.contains(socialAccount1)
        assertTrue profile.socialAccounts.contains(socialAccount2)

        assertTrue profile.addresses.contains(address1)
        assertTrue profile.addresses.contains(address2)

        assertTrue profile.phoneNumbers.contains(phoneNumber1)
        assertTrue profile.phoneNumbers.contains(phoneNumber2)

        assertTrue profile.statuses.contains(histStatus1)
        assertTrue profile.statuses.contains(histStatus2)
    }
}
