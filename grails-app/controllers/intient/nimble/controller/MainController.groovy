package intient.nimble.controller

import intient.nimble.domain.User
import org.apache.ki.SecurityUtils

class MainController {

    def index = {

      // This relies on the fact that the session is autentication which is enforced by
      // NimbleSecurityFilters
      def user = User.get(SecurityUtils.getSubject()?.getPrincipal())
      [user:user]
    }
}
