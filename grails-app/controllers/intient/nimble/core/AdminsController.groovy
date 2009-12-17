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

package intient.nimble.core

import org.apache.shiro.SecurityUtils

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
    def authenticatedUser = UserBase.get(SecurityUtils.getSubject()?.getPrincipal())

    if(!authenticatedUser) {
      log.error("Not able to determine currently authenticated user")
      response.sendError(403)
      return
    }

    return [currentAdmin:authenticatedUser, admins: adminAuthority?.users]
  }

  def create = {
    def user = UserBase.get(params.id)
    if (!user) {
      log.warn("User identified by id $params.id was not located")

      response.sendError(500)
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      return
    }

    def result = adminsService.add(user)
    if (result) {
      log.debug("User identified as [$user.id]$user.username was added as an administrator")
      render message(code: 'nimble.admin.grant.success', args: [user.username])
      return
    }
    else {
      log.warn("User identified as [$user.id]$user.username was unable to be made an administrator")
      response.sendError(500)
      render message(code: 'nimble.admin.grant.failed', args: [user.username])
      return
    }
  }

  def delete = {
    def user = UserBase.get(params.id)

    if (!user) {
      log.warn("User identified by id $params.id was not located")  
      render message(code: 'nimble.user.nonexistant', args: [params.id])
      response.sendError(500)
      return
    }

    if(user == authenticatedUser) {
      log.warn("Administrators are not able to remove themselves from the administrative role") 
      render message(code: 'nimble.admin.revoke.self', args: [user.username])
      response.sendError(500)
      return
    }

    def result = adminsService.remove(user)
    if (result) {
      render message(code: 'nimble.admin.revoke.success', args: [user.username])
      return
    }
    else {
      log.warn("User identified as [$user.id]$user.username was unable to be removed as an administrator")
      render message(code: 'nimble.admin.revoke.error', args: [user.username])
	  response.sendError(500)
      return
    }
  }

  def search = {
    def q = "%" + params.q + "%"

    log.debug("Performing search for users matching $q")

    def users = UserBase.findAllByUsernameIlike(q)
    def profiles = ProfileBase.findAllByFullNameIlikeOrEmailIlike(q, q)
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
