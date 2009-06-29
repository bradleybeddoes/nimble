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

/**
 * Outlines a default set of recommended URL mappings for Nimble components.
 *
 * @author Bradley Beddoes
 */
class NimbleUrlMappings {
    static mappings = {

    "/administration/adminstrators/$action?/$id?" {
            controller = "admins"
        }

    "/administration/users/$action?/$id?" {
            controller = "user"
        }

    "/administration/groups/$action?/$id?" {
            controller = "group"
        }

    "/administration/roles/$action?/$id?" {
            controller = "role"
        }

    "/register" {
            controller = "account"
            action = "createuser"
        }

    "/register/validusername" {
            controller = "account"
            action = "validusername"
        }

    "/register/save" {
            controller = "account"
            action = "saveuser"
        }

    "/registered" {
            controller = "account"
            action = "createduser"
        }

    "/validateuser/$id" {
            controller = "account"
            action = "validateuser"
        }

    "/forgottenpassword" {
            controller = "account"
            action = "forgottenpassword"
        }

    "/forgottenpassword/submit" {
            controller = "account"
            action = "forgottenpasswordprocess"
        }

    "/forgottenpassword/complete" {
            controller = "account"
            action = "forgottenpasswordcomplete"
        }

    "/forgottenpassword/external/$id?" {
            controller = "account"
            action = "forgottenpasswordexternal"
        }

    "/login" {
            controller = "auth"
            action = "login"
        }

    "/logout" {
            controller = "auth"
            action = "logout"
        }

    "/unauthorized" {
            controller = "auth"
            action = "unauthorized"
        }

    "/auth/$action" {
            controller = "auth"
        }

    "/profile/$action?/$id?" {
            controller = "profile"
        }
    }
}
