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
package intient.nimble.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Represents a remote party that provides federated authentication
 *
 * @author Bradley Beddoes
 */
class FederationProvider {

  static config = ConfigurationHolder.config
	
  String uid
  Details details
  boolean autoProvision
  
  Map props

  static mapping = {
      table FederationProvider.config.nimble.tablenames.federationprovider
      uid column: FederationProvider.config.nimble.fieldnames.uid
  }

  static constraints = {
    uid(nullable:false)
    details(nullable:false)
    props(nullable: true)
  }

}


