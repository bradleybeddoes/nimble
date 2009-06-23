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

/**
 * @author Bradley Beddoes
 */
class ProfileTests extends GrailsUnitTestCase {

    def fullName 
    def nickName 
    def email
    def nonVerifiedEmail
    def bio 
    def dob 
    def gender

    def photo
    def photoType 

    def gravatar 
    def currentStatus 

    def dateCreated 
    def lastUpdated 

    def website1 
    def website2 

    def alternateEmail1
    def alternateEmail2

    def feed1 
    def feed2 

    def socialAccount1 
    def socialAccount2 

    def address1 
    def address2 

    def phoneNumber1 
    def phoneNumber2 

    def histStatus1 
    def histStatus2 

    def owner 
        
    protected void setUp() {
        super.setUp()

        fullName = 'fullName'
        nickName = 'nickName'
        email = 'test@unit.org'
        nonVerifiedEmail = 'non@verified.org'
        bio = 'unit test user biography'
        dob = new Date()
        gender = Gender.Female

        photo = 'thiscouldbephotodatareally...'.bytes
        photoType = 'png'

        gravatar = false
        currentStatus = new Status()

        dateCreated = new Date()
        lastUpdated = new Date()

        website1 = new Url()
        website2 = new Url()

        alternateEmail1 = 'test@junit.org'
        alternateEmail2 = 'test@nounit.org'

        feed1 = new Feed()
        feed2 = new Feed()

        socialAccount1 = new SocialMediaAccount()
        socialAccount2 = new SocialMediaAccount()

        address1 = new Address()
        address2 = new Address()

        phoneNumber1 = new Phone()
        phoneNumber2 = new Phone()

        histStatus1 = new Status()
        histStatus2 = new Status()

        owner = new User()
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
            statuses:[histStatus1,histStatus2], owner:owner)

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

    void testFullNameConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.fullName = ""
        assertFalse profile.validate()

        profile.fullName = null
        assertTrue profile.validate()
    }

    void testNickNameConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.nickName = ""
        assertFalse profile.validate()

        profile.nickName = null
        assertTrue profile.validate()
    }

    void testEmailConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()
        
        assertTrue profile.validate()

        profile.email = ""
        assertFalse profile.validate()

        profile.email = null
        assertTrue profile.validate()

        profile.email = '121abcs'
        assertFalse profile.validate()

        profile.email = 'john@doe.com'
        assertTrue profile.validate()

        def profile2 = createValidProfile()
        mockForConstraintsTests(Profile, [profile, profile2])

        assertTrue profile.validate()
        assertTrue profile2.validate()

        profile2.email = 'john@doe.com'
        assertFalse profile2.validate()
        assertTrue profile2.errors.errorCount == 1
        assertEquals 'email', profile2.errors.fieldError.field
    }
    
    void testNonVerifiedEmailConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()
        
        assertTrue profile.validate()

        profile.nonVerifiedEmail = ""
        assertFalse profile.validate()

        profile.nonVerifiedEmail = null
        assertTrue profile.validate()

        profile.nonVerifiedEmail = '121abcs'
        assertFalse profile.validate()

        profile.nonVerifiedEmail = 'john@doe.com'
        assertTrue profile.validate()
    }

    void testPhotoConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.photo = null
        assertTrue profile.validate()
    }

    void testPhotoTypeConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.photoType = ""
        assertFalse profile.validate()

        profile.photoType = null
        assertTrue profile.validate()
    }

    void testCurrentStatusConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.currentStatus = null
        assertTrue profile.validate()
    }

    void testGenderConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.gender = null
        assertTrue profile.validate()
    }

    void testDOBConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.dob = null
        assertTrue profile.validate()
    }

    void testPreferencesConstraint() {
        mockForConstraintsTests(Profile)
        def profile = createValidProfile()

        assertTrue profile.validate()

        profile.preferences = null
        assertTrue profile.validate()
    }

    void testDateCreatedConstraint() {
        mockForConstraintsTests(Profile)

        def profile = createValidProfile()
        assertTrue profile.validate()

        profile.dateCreated = null
        assertTrue profile.validate()
    }

    void testLastUpdatedConstraint() {
        mockForConstraintsTests(Profile)

        def profile = createValidProfile()
        assertTrue profile.validate()

        profile.lastUpdated = null
        assertTrue profile.validate()
    }

}
