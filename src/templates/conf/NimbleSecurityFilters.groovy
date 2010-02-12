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
import grails.plugins.nimble.core.AdminsService

/**
 * Filter that works with Nimble security model to protect controllers, actions, views
 *
 * @author Bradley Beddoes
 */
public class NimbleSecurityFilters extends grails.plugins.nimble.security.NimbleFilterBase {

    def filters = {

        // Content requiring users to be authenticated
        secure(controller: "main") {
            before = {
                accessControl {
                    true
                }
            }
        }

        // Account management requiring authentication
        accountsecure(controller: "account", action: "(changepassword|updatepassword|changedpassword)") {
            before = {
                accessControl {
                    true
                }
            }
        }

        // This should be extended as the application adds more administrative functionality
        administration(controller: "(admins|user|group|role)") {
            before = {
                accessControl {
                    role(AdminsService.ADMIN_ROLE)
                }
            }
        }

    }

}