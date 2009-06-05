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
package intient.nimble.realms

import intient.nimble.domain.Permission
import intient.nimble.domain.User
import org.apache.ki.authz.permission.AllPermission
import org.apache.ki.authz.permission.WildcardPermission
import org.apache.ki.authc.*

/**
 * Integrates with Ki to establish a session for users accessing the system based
 * on authentication with locally stored accounts.
 *
 * @author Bradley Beddoes
 */
class LocalizedRealm {
  static authTokenClass = org.apache.ki.authc.UsernamePasswordToken

  def credentialMatcher
  def sessionFactory
  def grailsApplication

  def authenticate(authToken) {

    if (!grailsApplication.config.nimble.internal.authentication.enabled) {
      log.debug("Authentication for username/password based tokens is not enabled for internal repository, skipping LocalizedRealm")
      return
    }

    log.info "Attempting to authenticate ${authToken.username} from local repository"
    def username = authToken.username

    if (!username) {
      throw new AccountException('Null usernames are not supported by this realm.')
    }

    def user = User.findByUsername(username)
    if (!user) {
      throw new UnknownAccountException("No account found for user [${username}]")
    }

    log.info "Located user [$user.id]$user.username in data repository, starting authentication process"

    if (!user.enabled) {
      log.warn "User [$user.id]$user.username is disabled preventing authentication"
      throw new DisabledAccountException("This account is currently disabled")
    }

    def account = new SimpleAccount(user.id, user.passwordHash, "intient.nimble.realms.LocalizedRealm")
    if (!credentialMatcher.doCredentialsMatch(authToken, account)) {
      log.warn "Supplied password for user [$user.id]$user.username is incorrect"
      throw new IncorrectCredentialsException("Invalid password for user '${username}'")
    }

    log.info("Successfully logged in user [$user.id]$user.username using local repository")
    return account
  }

  def hasRole(principal, roleName) {
    def session
    def result = false

    try {
      session = sessionFactory.openSession()
      def user = session.get(User.class, new Long(principal))

      if (user) {
        log.debug("Determining if user [$user.id]$user.username is assigned role $roleName")

        user.roles.each {
          if (it.name.equals(roleName)) {
            log.debug("User [$user.id]$user.username is assigned role $roleName")
            result = true
          }
        }

        if (!result) {
          log.debug("Determining if group membership for user [$user.id]$user.username is assigned role $roleName")
          // No match on directly assigned roles, check groups
          user.groups.each {group ->
            group.roles.each {
              if (it.name.equals(roleName)) {
                log.debug("Group [$group.id]$group.name which user [$user.id]$user.username is a member of is assigned role the $roleName")
                result = true
              }
            }
          }
        }

        if (!result)
          log.debug("Neither user [$user.id]$user.username nor groups this user is a member of is assigned the role $roleName")

        return result
      }

      log.warn("No user for $principal exists when attempting to verify role")
      return false
    }
    finally {
      if (session) {
        session.clear()
        session.close()
      }
    }
  }

  def hasAllRoles(principal, roles) {

    def session
    def result = false

    try {
      session = sessionFactory.openSession()
      def user = session.get(User.class, new Long(principal))

      log.debug("Determining if user [$user.id]$user.username is assigned multiple roles")

      if (user) {

        rolesearch:
        for (role in roles) {

          log.debug("Determining if user [$user.id]$user.username is assigned role $role.name")

          for (ur in user.roles) {
            if (ur.name.equals(role.roleName)) {
              log.debug("User [$user.id]$user.username is assigned role $role.name")
              continue rolesearch
            }
          }

          // No match on directly assigned roles, check groups
          for (group in user.groups) {
            if (group.hasRole(roleName)) {
              log.debug("Group [$group.id]$group.name which user [$user.id]$user.username is a member of is assigned role the $role.name")
              continue rolesearch
            }
          }

          // No match for current role so we can return
          log.debug("Neither user [$user.id]$user.username nor groups this user is a member of is assigned the role $role.name")
          return false
        }

        // Matches for all roles so we can return
        log.debug("User [$user.id]$user.username was directly or via group membership assigned all required roles")
        return true
      }

      log.warn("No user for $principal exists when attempting to verify multiple roles")
      return false
    }
    finally {
      if (session) {
        session.clear()
        session.close()
      }
    }
  }

  def isPermitted(principal, requiredPermission) {

    def session
    def permitted = false

    /**
     * After extensive bench marking of the reflection approach
     * it was decided that we couldn't wear the performance hit
     * it was bringing. Some numbers:
     *
     * Over 10 million instantiations:
     * New Time: 4571ms
     * Ref Time: 114321ms
     * Difference: 109750ms
     *
     * Thus this realm looks at WildcardPermission and AllPermission only.
     * Users wishing to check other permission types will require a custom realm.
     */

    try {
      session = sessionFactory.openSession()
      def user = session.get(User.class, new Long(principal))

      log.debug("Determining if permissions assigned to user [$user.id]$user.username contains a permission that implies $requiredPermission")
      // Try all directly assigned permissions
      for (permission in user.permissions) {
        permitted = validatePermission(permission, requiredPermission)
        if (permitted)
          break
      }

      // If we have no positive match try all permissions assigned to roles
      if (!permitted) {
        log.debug("Determining if roles assigned to user [$user.id]$user.username contains a permission that implies $requiredPermission")

        rolepermsearch:
        for (role in user.roles) {
          for (permission in role.permissions) {
            permitted = validatePermission(permission, requiredPermission)
            if (permitted)
              break rolepermsearch
          }
        }
      }

      // If we have no positive match try all permissions assigned to member groups and to any roles associated with each group
      if (!permitted) {
        log.debug("""Determining if groups (incl roles assigned to each group) which user [$user.id]$user.username
                     is a member of are assigned a permission that implies $requiredPermission""")

        grouppermsearch:
        for (group in user.groups) {
          // Try all directly assigned permissions
          for (permission in group.permissions) {
            permitted = validatePermission(permission, requiredPermission)
            if (permitted)
              break grouppermsearch
          }

          for (role in group.roles) {
            for (permission in role.permissions) {
              permitted = validatePermission(permission, requiredPermission)
              if (permitted)
                break grouppermsearch
            }
          }
        }
      }

      if (permitted)
        log.debug("User [$user.id]$user.username has a permission which implies $requiredPermission directly or via role/group memberships")
      else
        log.debug("User [$user.id]$user.username does not have a permission which implies $requiredPermission either directly or via role/group memberships")

      return permitted
    }
    finally {
      if (session) {
        session.clear()
        session.close()
      }
    }
  }

  protected boolean validatePermission(permission, requiredPermission) {
    boolean permitted = false;

    if (permission.type.equals(WildcardPermission.class.name) || permission.type.equals(intient.nimble.auth.WildcardPermission.class.name)) {
      def perm = new WildcardPermission(permission.target, false)

      if (perm.implies(requiredPermission)) {
        log.debug("Permission $permission does imply $requiredPermission")
        permitted = true
      }
      else {
        log.debug("Permission $permission does not imply $requiredPermission")
      }
    }
    else if (permission.type.equals(AllPermission.class.name) || permission.type.equals(intient.nimble.auth.AllPermission.class.name)) {
      def perm = new AllPermission()

      if (perm.implies(requiredPermission)) {
        log.debug("Located instance of AllPermission which automatically implies $requiredPermission")
        permitted = true
      }
    }

    return permitted
  }

}
