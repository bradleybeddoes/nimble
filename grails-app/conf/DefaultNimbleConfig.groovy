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

nimble {
  facebook {
    name = "facebook connect"
    displayname = "Facebook Connect"
    description = "Facebook Connect allows users to login using their credentials provided via their Facebook Account"
    logo = "social/128/facebook.png"
    logosmall = "social/16/facebook.png"
    url = "http://www.facebook.com"
    alttext = "Facebook Homepage"

    federationprovider {
      enabled = false
      autoprovision = false
    }
  }

  openid {
    name = "OpenID"
    displayname = "OpenID"
    description = "OpenID is an open and decentralized identity system, designed \"not to crumble if one company turns evil or goes out of business\" "
    logo = "social/128/openid.png"
    logosmall = "social/16/openid.png"
    url = "http://openid.net/"
    alttext = "OpenID Foundation"

    federationprovider {
      enabled = false
      autoprovision = false
    }

    discovery {
      google = "https://www.google.com/accounts/o8/id"
      yahoo = "http://yahoo.com"
      flickr = "http://flickr.com"
    }
  }

  messaging {
    registration {
      subject = "Your new account is ready!"
    }
    passwordreset {
      subject = "Your password has been reset"
    }
  }
}