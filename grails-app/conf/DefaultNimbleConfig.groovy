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

nimble {

    tablenames {
        user = "_user"
        role = "_role"
        group = "_group"
    }

    layout {
        application = 'app'
        administration = 'admin'
    }

    localusers {
        usernames {
            minlength = 4
            validregex = '[a-zA-Z0-9]*'
        }
    }

    passwords {
        mustcontain {
            lowercase = true
            uppercase = true
            numbers = true
            symbols = true
        }
        minlength = 8
    }

    facebook {
        uid = "facebook"
        name = "Facebook"
        displayname = "Facebook"
        description = "Facebook helps you connect and share with the people in your life."
        url = "http://www.facebook.com"
        alttext = "Facebook Homepage"
        profileurl = "http://www.facebook.com/profile.php?id=ACCOUNTID"
        profilealttext = "Facebook Profile"

        federationprovider {
            enabled = false
            autoprovision = false
        }
    }

    twitter {
        uid = "twitter"
        name = "Twitter"
        displayname = "Twitter"
        description = "Lets you keep in touch with people through the exchange of quick, frequent answers to one simple question: What are you doing?"
        url = "http://www.twitter.com"
        alttext = "Twitter Homepage"
        profileurl = "http://www.twitter.com/ACCOUNTID"
        profilealttext = "Twitter Profile"
    }

    openid {
        name = "OpenID"
        displayname = "OpenID"
        description = "OpenID is an open and decentralized identity system, designed \"not to crumble if one company turns evil or goes out of business\" "
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
        changeemail {
            subject = "Your email address has been changed"
        }
    }
}
