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
        out << render(template: "/templates/growl", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Enables growl message popup when the Grails application stores a message in flash scope
     */
    def flashgrowl = {attrs, body ->
        out << render(template: "/templates/flashgrowl", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Provides an inline output of the Grails application message in flash scope
     */
    def flashembed = {attrs, body ->
        out << render(template: "/templates/flashembed", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Imports JQuery Javascript to make the JQuery library available to the current page
     */
    def jquery = {attrs, body ->
        out << render(template: "/templates/jquerysetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Imports css and javascript required to create a Nimble menu
     */
    def menu = {attrs, body ->
        out << render(template: "/templates/nmenusetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Imports css required to use FAM FAM FAM icons in buttons etc
     */
    def famfamfam = {attrs, body ->
        out << render(template: "/templates/famfamfamsetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Imports layouts and javascript required for the administration layout
     */
    def admin = {attrs, body ->
        out << render(template: "/templates/adminsetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Imports base Nimble provided CSS
     */
    def basecss = {attrs, body ->
        out << render(template: "/templates/basecsssetup", contextPath: pluginContextPath, model:[nimblePath:pluginContextPath])
    }

    /**
     * Provides nimble session terminated message
     */
    def sessionterminated = {attrs, body ->
        out << render(template: "/templates/sessionterminated", contextPath: pluginContextPath)
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
     * Renders body if captcha is currently required
     */
    def recaptcharequired = { attrs, body ->
        if (recaptchaService.enabled) {
            out << body()
        }
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

