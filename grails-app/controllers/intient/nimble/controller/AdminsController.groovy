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

import intient.nimble.domain.Role
import intient.nimble.service.AdminsService
import intient.nimble.domain.User
import intient.nimble.domain.Profile

/**
 * Manages addition and removal of super administrator role to user accounts
 *
 * @author Bradley Beddoes
 */
class AdminsController {

  static Map allowedMethods = [list: 'POST', create: 'POST', delete: 'POST', search: 'POST']

  def adminsService

  def index = { }

  def list = {
    def adminAuthority = Role.findByName(AdminsService.ADMIN_ROLE)
    return [admins: adminAuthority?.users]
  }

  def create = {
    def user = User.get(params.id)
    if (!user) {
      log.warn("User identified by id $params.id was not located")

      response.sendError(500)
      render 'Unable to locate user'
      return
    }

    def result = adminsService.create(user)
    if (result) {
      log.debug("User identified as [$user.id]$user.username was added as an administrator")
      render 'Success'
      return
    }
    else {
      log.warn("User identified as [$user.id]$user.username was unable to be made an administrator")
      response.sendError(500)
      render 'Unable to save administrator changes'
      return
    }
  }

  def delete = {
    def user = User.get(params.id)
    def authenticatedUser = User.get(SecurityUtils.getSubject()?.getPrincipal())

    if (!user) {
      log.warn("User identified by id $params.id was not located")

      response.sendError(500)
      render 'Unable to save administrator changes'
      return
    }

    if(user == authenticatedUser) {
      log.warn("Administrators are not able to remove themselves from the administrative role")
      response.sendError(500)
      render 'Unable to save administrator changes. Attempt to remove own administrative rights'
      return
    }

    def result = adminsService.delete(user)
    if (result) {
      render 'Success'
      return
    }
    else {
      log.warn("User identified as [$user.id]$user.username was unable to be removed as an administrator")
      response.sendError(500)
      render 'Unable to save administrator changes'
      return
    }
  }

  def search = {
    def q = "%" + params.q + "%"

    log.debug("Performing search for users matching $q")

    def users = User.findAllByUsernameIlike(q)
    def profiles = Profile.findAllByFullNameIlikeOrEmailIlike(q, q)
    def nonAdmins = []

    def adminAuthority = Role.findByName(AdminsService.ADMIN_ROLE)
    users.each {
      if (!it.roles.contains(adminAuthority)) {
        nonAdmins.add(it)    // Eject users that are already admins
        log.debug("Adding user identified as [$it.id]$it.username to search results")
      }
    }
    profiles.each {
      if (!it.owner.roles.contains(adminAuthority) && !nonAdmins.contains(it.owner)) {
        nonAdmins.add(it.owner)    // Eject users that are already admins
        log.debug("Adding user identified as [$it.owner.id]$it.owner.username based on profile to search results")
      }
    }

    log.info("Search for new administrators complete, returning $nonAdmins.size records")
    return [users: nonAdmins]   // Should always be the case
  }
}
