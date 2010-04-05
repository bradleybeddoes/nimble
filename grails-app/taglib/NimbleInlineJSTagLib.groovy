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

import grails.plugins.nimble.core.*

/**
 * Provides generic, mostly UI related tags to the Nimble application
 *
 * @author Bradley Beddoes
 */
class NimbleInlineJSTagLib {

    static namespace = "njs"

	// Enables growl message popup when the Grails application stores a message in flash scope
    def flashgrowl = {attrs, body ->
        out << render(template: "/templates/inlinejs/" + grailsApplication.config.nimble.layout.jslibrary + "/flashgrowl", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

	// User management
	def user = {attrs ->
		if(attrs.user == null)
        	throwTagError("User management tag requires user attribute [njs]")

		 out << render(template: "/templates/inlinejs/" + grailsApplication.config.nimble.layout.jslibrary + "/user", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath, user:attrs.user])
	}

	def permission = {attrs ->
		if(attrs.parent == null)
        	throwTagError("Permission management tag requires owner attribute [njs]")

		 out << render(template: "/templates/inlinejs/" + grailsApplication.config.nimble.layout.jslibrary + "/permission", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath, parent:attrs.parent])
	}

	def role = {attrs ->
		if(attrs.parent == null)
        	throwTagError("Role management tag requires user attribute [njs]")

		 out << render(template: "/templates/inlinejs/" + grailsApplication.config.nimble.layout.jslibrary + "/role", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath, parent:attrs.parent])
	}

	def group = {attrs ->
		if(attrs.parent == null)
        	throwTagError("Group management tag requires user attribute [njs]")

		 out << render(template: "/templates/inlinejs/" + grailsApplication.config.nimble.layout.jslibrary + "/group", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath, parent:attrs.parent])
	}

	def member = {attrs ->
		if(attrs.parent == null)
        	throwTagError("Member management tag requires user attribute [njs]")

		 out << render(template: "/templates/inlinejs/" + grailsApplication.config.nimble.layout.jslibrary + "/member", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath, parent:attrs.parent])
	}

}