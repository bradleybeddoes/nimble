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

/**
 * Represents generic details users may wish to enter about themselves within
 * a given application, special emphasis on integration with social media.
 *
 * @author Bradley Beddoes
 */
class Profile {

  String fullName
  String nickName
  String email
  String avatarURL

  Date dob

  ProfileGender gender
  byte[] avatar
  Map preferences

  boolean remoteAvatar = false

  static belongsTo = [owner: User]

  static hasMany = [
          websites: Url,
          alternateEmails: String,
          feeds: Feed,
          socialAccounts: SocialMediaAccount,
          associates: Profile
  ]

  static constraints = {
    fullName(nullable: true, blank: true)
    nickName(nullable: true, blank: true)
    email(nullable: true, blank: true, email: true, unique: true)
    avatarURL(nullable: true, blank: true, url: true)
    avatar(nullable: true)
    gender(nullable: true, blank: true)
    dob(nullable: true)

    preferences(nullable: true)
  }
}

public enum ProfileGender {
  MALE, FEMALE
}
