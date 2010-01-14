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

import grails.plugin.nimble.core.*

/**
 * Provides generic, mostly UI related tags to the Nimble application
 *
 * @author Bradley Beddoes
 */
class NimbleHeaderTagLib {

    static namespace = "nh"

	// Enables js for Nimble core UI features
    def nimblecore = {attrs ->
        out << render(template: "/templates/header/nimblecore", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

	// Enables js for Nimble supplied UI features
    def nimbleui = {attrs ->
        out << render(template: "/templates/header/nimbleui", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    // Enables growl like message popup in the page
    def growl = {attrs, body ->
        out << render(template: "/templates/header/growl", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

	// Imports JQuery Javascript to make the JQuery library available to the current page
    def jquery = {attrs, body ->
        out << render(template: "/templates/header/jquerysetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

	// Imports css required to use FAM FAM FAM icons in buttons etc
    def famfamfam = {attrs, body ->
        out << render(template: "/templates/header/famfamfamsetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

	// Imports default Nimble login UI css
    def login = {attrs, body ->
        out << render(template: "/templates/header/loginsetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    // Imports layouts and javascript required for the administration layout
    def admin = {attrs, body ->
        out << render(template: "/templates/header/adminsetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    // Imports base Nimble UI CSS
    def basecss = {attrs, body ->
        out << render(template: "/templates/header/basecsssetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

}