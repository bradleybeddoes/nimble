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

/**
 * Outlines a default set of recommended URL mappings for Nimble components.
 *
 * @author Bradley Beddoes
 */
class UrlMappings {
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

    "/"
    {
      controller = "main"
      action = "index"
    }

    "500"(view: '/error')
  }
}
