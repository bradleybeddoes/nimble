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
package intient.nimble.controller

import org.apache.ki.SecurityUtils

import intient.nimble.domain.User
import intient.nimble.service.ProfileService

class ProfileController {

    def index = { redirect(action: 'show', params: params) }

    def show = {
        def user

        if(params.id == null) {
            log.debug("Didn't locate id in profile request, attempting to load profile of logged in user")
            user = User.get(SecurityUtils.getSubject()?.getPrincipal())     
        }
        else{
            log.debug("Attempting to load profile for user id $params.id")
            user = User.get(params.id)
        }

        if (!user) {
            log.warn("User was not located when attempting to show profile")
            response.sendError(500)
            return
        }

        def authUser = getAuthenticatedUser(response)

        log.debug("Showing profile for user [$user.id]$user.username")
        return [user: user, profile: user.profile, authUser: authUser]
    }

    def edit = {
        def user = User.get(params.id)
        if (!user) {
            log.warn("User was not located when attempting to edit profile")
            response.sendError(500)
            return
        }

        def authUser = getAuthenticatedUser(response)
        
        if(SecurityUtils.getSubject().isPermitted(ProfileService.editPermission + user.id)) {

        }
        else {
            log.warn("Security model denied attempt by user [$authUser.id]$authUser.username to edit profile for user [$user.id]$user.username")
            response.sendError(403)
            return
        }
    }

    private User getAuthenticatedUser(def response) {
        def authUser = User.get(SecurityUtils.getSubject()?.getPrincipal())
        if (!authUser) {
            log.warn("Authenticated user was not able to be obtained when performing profile action")
            throw new RuntimeException("Authenticated user was not able to be obtained when performing profile action")
        }

        return authUser
    }
}
