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
package intient.nimble.auth

import org.openid4java.discovery.Identifier
import org.apache.ki.authc.AuthenticationToken

/**
 * Token used with OpenID Ki realm for authentication purposes
 *
 * @author Bradley Beddoes
 */
public class OpenIDToken implements AuthenticationToken {

  Identifier principal;

  def fullName, nickName, email, gender

  public OpenIDToken(Identifier identifier) {
    this.principal = identifier
  }

  /**
   * Returns the users validated OpenID identifier
   */
  public Object getPrincipal() {
    return this.principal
  }

  /**
   * Returns null for OpenID
   */
  public Object getCredentials() {
    return null
  }

  public String getUserID() {
    return this.principal.getIdentifier()
  }

  public Identifier getOpenIDIdentifier() {
    return this.principal
  }
}