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
package grails.plugins.nimble.core

import org.codehaus.groovy.grails.commons.ConfigurationHolder
/**
 * Represents an object that we wish to store a basic set of information about
 *
 * @author Bradley Beddoes
 */
class Details implements Serializable {

  String name
  String displayName
  String description
  Url url = null
  
  String logo
  String logoSmall

  static belongsTo = [FederationProvider]
  
    static mapping = {
        table ConfigurationHolder.config.nimble.tablenames.details
    } 
  static constraints = {
    name(nullable: true, blank: false)
    displayName(nullable: true, blank: false)
    logo(nullable: true, blank: false)
    logoSmall(nullable: true, blank: false)
    url(nullable: true)
    description(nullable: true, blank: false)
  }
}


