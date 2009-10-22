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
package intient.nimble.controller

import intient.nimble.domain.UserBase
import org.apache.shiro.SecurityUtils

class MainController {

    def index = {
      // This relies on the fact that the session is autentication which is enforced by
      // NimbleSecurityFilters, authenticatedUser is auto populated to controllers
      [user:authenticatedUser]
    }
}
