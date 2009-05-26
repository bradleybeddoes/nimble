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

public class NimbleFilterBase {

  def onNotAuthenticated(subject, filter) {

    def request = filter.request
    def response = filter.response

    // If this is an ajax request we want to send a 403 so the UI can act accordingly (generally log the user in again)
    if (request.getHeader('X-REQUESTED-WITH')) {
      response.status = 403
      response.setHeader("N-Session-Invalid", "true")
      return false
    }

    // Default behaviour is to redirect to the login page.
    def targetUri = request.forwardURI - request.contextPath
    def query = request.queryString
    if (query) {
      if (!query.startsWith('?')) {
        query = '?' + query
      }
      targetUri += query
    }

    filter.redirect(
            controller: 'auth',
            action: 'login',
            params: [targetUri: targetUri])
  }

}