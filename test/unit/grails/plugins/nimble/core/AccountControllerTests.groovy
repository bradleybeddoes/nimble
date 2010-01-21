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

import grails.test.*
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.validation.ObjectError

class AccountControllerTests extends ControllerUnitTestCase {
    def suMock
    def pass
    def passConfirm
    def currentPassword

    protected void setUp() {
        super.setUp()

        mockLogging(AccountController, true)
        pass = 'pass'
        passConfirm = pass
        currentPassword = 'currentPassword'
    }

    protected void tearDown() {
        super.tearDown()
        suMock?.verify()
        suMock = null
    }

    def createValidUser() {
        def pwEnc = new Sha256Hash(currentPassword)
        def crypt = pwEnc.toHex()


        //further fill out user....

        def user = new UserBase(id:1, username:'username', passwordHash:crypt, profile: new ProfileBase())
        mockDomain(UserBase, [user])

        return user
    }

    def createValidRecaptchaMock() {
        def rsMock = mockFor(RecaptchaService)
        rsMock.demand.verifyAnswer {session, request, params ->
            assertEquals mockSession, session
            assertEquals mockRequest.getRemoteAddr(), request

            return true
        }
        controller.recaptchaService = rsMock.createMock()

        return rsMock
    }

    def createValidUserServiceMock() {
        def usMock = mockFor(UserService)
        return usMock
    }

    void testAllowedMethods() {
        def get = 'GET'
        def post = 'POST'
        
        assertEquals get, controller.allowedMethods.get('createuser')
        assertEquals get, controller.allowedMethods.get('createduser')
        assertEquals get, controller.allowedMethods.get('validateuser')
        assertEquals get, controller.allowedMethods.get('forgottenpassword')
        assertEquals get, controller.allowedMethods.get('forgottenpasswordcomplete')
        assertEquals get, controller.allowedMethods.get('changepassword')

        assertEquals post, controller.allowedMethods.get('saveuser')
        assertEquals post, controller.allowedMethods.get('validusername')
        assertEquals post, controller.allowedMethods.get('forgottenpasswordprocess')
        assertEquals post, controller.allowedMethods.get('updatepassword')
    }

    void testChangePasswordComplete() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> [getPrincipal:{return 1}] as Subject}

        def user = new UserBase(id:1)
        mockDomain(UserBase, [user])

        def model = controller.changepassword()

        assertEquals user, model.user

        suMock.verify()
    }

    void testChangePasswordNoAuth() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> null}

        def user = new UserBase(id:1)
        mockDomain(UserBase, [user])

        def model = controller.changepassword()

        assertEquals 403, mockResponse.status
        assertEquals null, model

        suMock.verify()
    }

    void testUpdatePasswordComplete() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> [getPrincipal:{return 1}] as Subject}
        
        def user = createValidUser()
        def rsMock = createValidRecaptchaMock()
        

        def usMock = createValidUserServiceMock()
        usMock.demand.changePassword{u ->
            assertEquals user, u
            assertEquals pass, u.pass
            assertEquals passConfirm, u.passConfirm
            return user
        }
        controller.userService = usMock.createMock()
        
        mockParams.putAll( [pass:pass, passConfirm:passConfirm, currentPassword:currentPassword] )
        
        controller.updatepassword()
        
        assertEquals 200, mockResponse.status
        assertEquals 'changedpassword', controller.redirectArgs.action

        rsMock.verify()
        usMock.verify()
        suMock.verify()
    }

    void testUpdatePasswordNoAuth() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> null}

        def user = new UserBase(id:1)
        mockDomain(UserBase, [user])

        def model = controller.updatepassword()

        assertEquals 403, mockResponse.status
        assertEquals null, model

        suMock.verify()
    }

    void testUpdatePasswordNoCurrent() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> [getPrincipal:{return 1}] as Subject}

        def pass = 'pass'
        def passConfirm = pass
        def currentPassword = 'currentPassword'

        mockLogging(AccountController, true)

        mockParams.putAll( [pass:pass, passConfirm:passConfirm] )

        def user = createValidUser()
        assertFalse user.hasErrors()
        
        def model = controller.updatepassword()

        assertEquals user, controller.renderArgs.model.user
        assertEquals 'changepassword', controller.renderArgs.view
        assertTrue user.hasErrors()
    }

    void testUpdatePasswordNotHuman() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> [getPrincipal:{return 1}] as Subject}

        def rsMock = mockFor(RecaptchaService)
        rsMock.demand.verifyAnswer {session, request, params ->
            assertEquals mockSession, session
            assertEquals mockRequest.getRemoteAddr(), request

            return false
        }
        controller.recaptchaService = rsMock.createMock()

        def user = createValidUser()
        assertFalse user.hasErrors()

        mockParams.putAll( [pass:pass, passConfirm:passConfirm, currentPassword:currentPassword] )
        controller.updatepassword()

        assertEquals user, controller.renderArgs.model.user
        assertEquals 'changepassword', controller.renderArgs.view
        assertTrue user.hasErrors()

        rsMock.verify()
    }

    void testUpdatePasswordEmptyCurrent() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> [getPrincipal:{return 1}] as Subject}

        def user = createValidUser()
        assertFalse user.hasErrors()

        mockParams.putAll( [pass:pass, passConfirm:passConfirm, currentPassword:''] )
        controller.updatepassword()

        assertEquals user, controller.renderArgs.model.user
        assertEquals 'changepassword', controller.renderArgs.view
        assertTrue user.hasErrors()
    }

    void testUpdatePasswordInvalidPass() {
        suMock = mockFor(SecurityUtils)
        suMock.demand.static.getSubject {-> [getPrincipal:{return 1}] as Subject}
        
        def user = createValidUser()
        def rsMock = createValidRecaptchaMock()

        def usMock = createValidUserServiceMock()
        usMock.demand.changePassword{u ->
            assertEquals user, u
            assertEquals 'notvalid', u.pass
            assertEquals passConfirm, u.passConfirm
            u.errors.rejectValue('pass', 'user.password.xyz')
            return u
        }
        controller.userService = usMock.createMock()
        
        assertFalse user.hasErrors()

        mockParams.putAll( [pass:'notvalid', passConfirm:passConfirm, currentPassword:currentPassword] )
        controller.updatepassword()

        assertEquals user, controller.renderArgs.model.user
        assertEquals 'changepassword', controller.renderArgs.view
        assertTrue user.hasErrors()

        usMock.verify()
        rsMock.verify()
    }

    void testCreateUserComplete() {
        mockDomain(UserBase, [])
        
        // Mock the application configuration.
        controller.grailsApplication = new Expando(config: [nimble: [localusers: [registration: [enabled: true]]]])
        
        def model = controller.createuser()
        assertNotNull model.user
    }

    void testSaveUserComplete() {
        def user = createValidUser()
    }
}
