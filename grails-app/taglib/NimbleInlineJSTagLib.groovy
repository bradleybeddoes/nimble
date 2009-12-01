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

import intient.nimble.core.*

/**
 * Provides generic, mostly UI related tags to the Nimble application
 *
 * @author Bradley Beddoes
 */
class NimbleInlineJSTagLib {

    static namespace = "njs"

	// Enables growl message popup when the Grails application stores a message in flash scope
    def flashgrowl = {attrs, body ->
        out << render(template: "/templates/inlinejs/flashgrowl", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

	// User management
	def user = {attrs ->
		if(attrs.user == null)
        	throwTagError("User state tag requires user attribute")
		
		 out << render(template: "/templates/inlinejs/user", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath, user:attrs.user])
	}

}