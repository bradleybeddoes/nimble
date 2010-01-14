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
package grails.plugin.nimble.security

public class NimbleFilterBase {

  def onNotAuthenticated(subject, filter) {

    def request = filter.request
    def response = filter.response

    // If this is an ajax request we want to send a 403 so the UI can act accordingly (generally log the user in again)
    if (request.getHeader('X-REQUESTED-WITH')) {
      response.status = 403
      response.setHeader("X-Nim-Session-Invalid", "true")
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