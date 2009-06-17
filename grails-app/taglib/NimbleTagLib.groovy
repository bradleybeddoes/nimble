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

import intient.nimble.domain.User

/**
 * Provides generic, mostly UI related tags to the Nimble application
 *
 * @author Bradley Beddoes
 */
class NimbleTagLib {

    static namespace = "n"

    def recaptchaService

    /**
     * Enables growl like message popup in the page
     */
    def growl = {attrs, body ->
        out << render(template: "/templates/growl", contextPath: pluginContextPath)
    }

    /**
     * Enables growl message popup when the Grails application stores a message in flash scope
     */
    def flashgrowl = {attrs, body ->
        out << render(template: "/templates/flashgrowl", contextPath: pluginContextPath)
    }

    /**
     * Provides an inline output of the Grails application message in flash scope
     */
    def flashembed = {attrs, body ->
        out << render(template: "/templates/flashembed", contextPath: pluginContextPath)
    }

    /**
     * Imports Bubbles javascript to make the mouseover bubble functionality available to the current page
     */
    def bubbles = {attrs, body ->
        out << render(template: "/templates/bubblesetup", contextPath: pluginContextPath)
    }

    /**
     * Imports JQuery Javascript to make the JQuery library available to the current page
     */
    def jquery = {attrs, body ->
        out << render(template: "/templates/jquerysetup", contextPath: pluginContextPath)
    }

    /**
     * Imports the JS required to create a Nimble menu
     */
    def menu = {attrs, body ->
        out << javascript(src: "nimblemenu.js")
    }

    /**
     * provides markup to render grails error messages for beans
     */
    def errors = {attrs, body ->
        def bean = attrs['bean']
        if (bean)
        out << render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: bean])
        else
        out << render("Error: Details not supplied to generate error content")
    }

    /**
     * Provides markup to render the default password policy
     */
    def passwordpolicy = {attrs, body ->
        out << render(template: "/templates/nimble/help/passwordpolicy")
    }

    /**
     * Provides markup to render the default username policy
     */
    def usernamepolicy = {attrs, body ->
        out << render(template: "/templates/nimble/help/usernamepolicy")
    }

    /**
     * Provides markup to render the default account creation policy
     */
    def accountcreationpolicy = {attrs, body ->
        out << render(template: "/templates/nimble/help/accountcreationpolicy")
    }

    /**
     * Provides markup to render a ReCaptcha instance. Supports the following attributes:
     *
     * theme - Can be one of 'red','white','blackglass','clean','custom'
     * lang  - Can be one of 'en','nl','fr','de','pt','ru','es','tr'
     * tabindex - Sets a tabindex for the ReCaptcha box
     * custom_theme_widget - Used when specifying a custom theme.
     */
    def recaptcha = {attrs ->
        def props = new Properties()
        attrs.each {
            if (attrs[it]) {
                props.setProperty(it, attrs[it])
            }
        }
        out << recaptchaService.createCaptcha(session, props)
    }

    /**
     * Provides markup to render the supplied users profile photo
     */
    def photo = {attrs ->
        if(attrs.id == null || attrs.size == null)
        throwTagError("Photo tag requires user id and size attributes")

        def id = attrs.id
        def size = attrs.size
        def user = User.get(id)

        if(user) {
            out << render(template: "/templates/profile/photo", contextPath: pluginContextPath, model: [profile: user.profile, size:size])
            return
        }
        throwTagError("No user located for supplied ID")
    }

    /**
     * Provides script to allow the user of the classes 'userhighlight' and 'user_X' on elements to provide
     * mini user profile popups on mouse hover.
     */
    def userhighlight = {
        out << render(template: "/templates/profile/userhighlight", contextPath: pluginContextPath)
    }

    /**
     * Providers markup to render the supplied users current status
     */
    def status = {attrs ->
        if(attrs.id == null)
        throwTagError("Status tag requires user id")

        def id = attrs.id
        def clear = attrs.clear ?:false
        def user = User.get(id)

        if(user) {
            out << render(template: "/templates/nimble/profile/currentstatus", model: [profile: user.profile, clear:clear])
            return
        }
        throwTagError("No user located for supplied ID")
    }

    /**
     * Allows Nimble core and Host Apps alike to access images provided by Nimble
     */
    def img = {attrs ->
        if(attrs.name == null || attrs.alt == null)
        throwTagError("Image tag requires name and alt attributes")

        def mkp = new groovy.xml.MarkupBuilder(out)
        mkp.img(src: resource(dir: pluginContextPath, file:"images/${attrs.name}"), alt: "$attrs.alt", width: attrs.size ?: '', height: attrs.size ?: '')
    }

    /**
     * Allows Nimble core and Host Apps alike to access images provided for social sites
     */
    def socialimg = {attrs ->
        if(attrs.alt == null || attrs.name == null || attrs.size == null)
        throwTagError("Social image tag requires size, name and alt attributes")

        def mkp = new groovy.xml.MarkupBuilder(out)
        mkp.img(src: resource(dir: pluginContextPath, file:"images/social/$attrs.size/${attrs.name}.png"), alt: "$attrs.alt")
    }


}

