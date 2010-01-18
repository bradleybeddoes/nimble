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

import org.apache.shiro.SecurityUtils

import grails.plugin.nimble.auth.WildcardPermission

import grails.plugin.nimble.core.UserBase
import grails.plugin.nimble.core.AdminsService
import grails.plugin.nimble.core.FacebookService

/**
 * Provides authentication related tags to the Nimble application
 *
 * @author Bradley Beddoes
 */
class NimbleAuthTagLib {

    def facebookService
    static namespace = "n"

    /**
     * Provides markup that renders the username of the logged in user
     */
    def principal = {
        Long id = SecurityUtils.getSubject()?.getPrincipal()

        if (id) {
            def user = UserBase.get(id)

            if (user)
            out << user.username
        }
    }

    /**
     * Provides markup the renders the name of the logged in user
     */
    def principalName = {attrs, body ->
        Long id = SecurityUtils.getSubject()?.getPrincipal()

        if (id) {
            def user = UserBase.get(id)

            if (user?.profile?.fullName)
            out << user.profile.fullName
        }
    }

    /**
     * Provides markup that renders a link to the administrative view of the logged in principal
     */
    def principalLink = {attrs, body ->
        Long id = SecurityUtils.getSubject()?.getPrincipal()

        if (id) {
            def user = UserBase.get(id)

            if (user) {
                def mkp = new groovy.xml.MarkupBuilder(out)
                mkp.a('href': createLink(controller: 'user', action: 'show', id: id), 'class': 'icon icon_user', body()) {
                }
            }
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * is logged in.
     */
    def isLoggedIn = {attrs, body ->
        if (checkAuthenticated()) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * is not logged in.
     */
    def isNotLoggedIn = {attrs, body ->
        if (!checkAuthenticated()) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * is an administrator
     */
    def isAdministrator = { attrs, body ->
        if(SecurityUtils.subject.hasRole(AdminsService.ADMIN_ROLE)) {
            out << body()
        }
    }

    /**
     * A synonym for 'isLoggedIn'. This is the same name as used by
     * the standard JSecurity tag library.
     */
    def authenticated = isLoggedIn

    /**
     * A synonym for 'isNotLoggedIn'. This is the same name as used by
     * the standard JSecurity tag library.
     */
    def notAuthenticated = isNotLoggedIn

    /**
     * This tag only writes its body to the output if the current user
     * is either logged in or remembered from a previous session (via
     * the "remember me" cookie).
     */
    def user = {attrs, body ->
        if (SecurityUtils.subject.principal != null) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * is neither logged in nor remembered from a previous session (via
     * the "remember me" cookie).
     */
    def notUser = {attrs, body ->
        if (SecurityUtils.subject.principal == null) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * is remembered from a previous session (via the "remember me"
     * cookie) but not currently logged in.
     */
    def remembered = {attrs, body ->
        if (SecurityUtils.subject.principal != null && !checkAuthenticated()) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * is not remembered from a previous session (via the "remember me"
     * cookie). This is the case if they are a guest user or logged in.
     */
    def notRemembered = {attrs, body ->
        if (SecurityUtils.subject.principal == null || checkAuthenticated()) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * has the given role.
     */
    def hasRole = {attrs, body ->
        // Does the user have the required role?
        if (checkRole(attrs)) {
            // Output the body text.
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * does not have the given role.
     */
    def lacksRole = {attrs, body ->
        // Does the user have the required role?
        if (!checkRole(attrs)) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * has all the given roles (inversion of lacksAnyRole).
     */
    def hasAllRoles = {attrs, body ->
        def inList = attrs['in']
        if (!inList)
            throwTagError('Tag [hasAllRoles] must have [in] attribute.')

        if (SecurityUtils.subject.hasAllRoles(inList)) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * doesn't have all of the given roles (inversion of hasAnyRole).
     */
    def lacksAllRoles = {attrs, body ->
        def inList = attrs['in']
        if (!inList)
            throwTagError('Tag [lacksAllRoles] must have [in] attribute.')

        if (inList.every { !SecurityUtils.subject.hasRole(it) } ) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * has any of the given roles (inversion of lacksAllRoles).
     */
    def hasAnyRole = {attrs, body ->
        def inList = attrs['in']
        if (!inList)
            throwTagError('Tag [hasAnyRole] must have [in] attribute.')

        if(inList.any { SecurityUtils.subject.hasRole(it) } ) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * has none of the given roles (inversion of hasAllRoles).
     */
    def lacksAnyRole = {attrs, body ->
        // Extract the name of the role from the attributes.
        def inList = attrs['in']
        if (!inList)
            throwTagError('Tag [lacksAnyRole] must have [in] attribute.')

        if(inList.any { !SecurityUtils.subject.hasRole(it) } ) {
            out << body()
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * has the given permission.
     */
    def hasPermission = {attrs, body ->
        def target = attrs['target']
        if (target) {
            if (checkPermission(target)) {
                out << body()
            }
        }
        else {
            throwTagError('Tag [hasPermission] must have [in] attribute.')
        }
    }

    /**
     * This tag only writes its body to the output if the current user
     * does not have the given permission.
     */
    def lacksPermission = {attrs, body ->
        def target = attrs['target']
        if (target) {
            if (!checkPermission(target)) {
                out << body()
            }
        }
        else {
            throwTagError('Tag [lacksPermission] must have [in] attribute.')
        }
    }

    private boolean checkAuthenticated() {
        // Get the user's security context.
        return SecurityUtils.subject.authenticated
    }

    /**
     * Checks whether the current user has the role specified in the
     * given tag attributes. Returns <code>true</code> if the user
     * has the role, otherwise <code>false</code>.
     */
    private boolean checkRole(attrs) {
        // Extract the name of the role from the attributes.
        def roleName = attrs['name']
        def inList = attrs['in']
        if (roleName) {
            // Does the user have the required role?
            return SecurityUtils.subject.hasRole(roleName)
        }

        throwTagError('Tag [hasRole] must have one of [name] or [in] attributes.')
    }

    /**
     * Determine if the user currently has referenced target as a WildcardPermission,
     * does not deal with any other type of permission.
     */
    private boolean checkPermission(String target) {
        WildcardPermission permission = new WildcardPermission(target, false)
        return SecurityUtils.subject.isPermitted(permission)
    }

    def facebookConnect = {attrs, body ->

        if (attrs['secure']?.equals('true'))
        out << render(template: "/templates/auth/facebookjs", contextPath: pluginContextPath, model: [secure: true, apikey: facebookService.apiKey])
        else
        out << render(template: "/templates/auth/facebookjs", contextPath: pluginContextPath, model: [secure: false, apikey: facebookService.apiKey])

    }
}
