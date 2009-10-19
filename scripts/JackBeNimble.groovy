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

import groovy.text.SimpleTemplateEngine

includeTargets << grailsScript("_GrailsArgParsing")

target ( default : 'Sets up a new project with a common Nimble base environment ready for customization' ) {

  def user, profile
  (user,profile) = parseArgs()
  
  def userbinding = [ 'classname':user, 'baseclassname':'User' ]
  def profilebinding = [ 'classname':profile, 'baseclassname':'Profile' ]

  def engine = new SimpleTemplateEngine()
  def usertemplate = engine.createTemplate(new FileReader("${nimblePluginDir}/src/templates/domain/Base.groovy")).make(userbinding)
  def profiletemplate = engine.createTemplate(new FileReader("${nimblePluginDir}/src/templates/domain/Base.groovy")).make(profilebinding)

  echo(" Jack be nimble \n Jack be quick \n Jack jump over \n The candlestick.")

  // Config
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleConfig.groovy", tofile: "${basedir}/grails-app/conf/NimbleConfig.groovy", overwrite: false)
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleBootStrap.groovy", tofile: "${basedir}/grails-app/conf/NimbleBootStrap.groovy", overwrite: false)
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleSecurityFilters.groovy", tofile: "${basedir}/grails-app/conf/NimbleSecurityFilters.groovy", overwrite: false)
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleUrlMappings.groovy", tofile: "${basedir}/grails-app/conf/NimbleUrlMappings.groovy", overwrite: false)

  // Domain Objects
  new File("${basedir}/grails-app/domain/${user}.groovy").write(usertemplate.toString())
  new File("${basedir}/grails-app/domain/${profile}.groovy").write(profiletemplate.toString())

  // Templates
  copy( todir: "${basedir}/grails-app/views/templates/nimble" , overwrite: false ) { fileset ( dir : "${nimblePluginDir}/grails-app/views/templates/nimble" ) }

  // Sass
  mkdir( dir:"${basedir}/src/sass" )
  copy(file:"${nimblePluginDir}/src/sass/_rounded.sass", todir: "${basedir}/src/sass", overwrite: false)
  copy(file:"${nimblePluginDir}/src/sass/_uielements.sass", todir: "${basedir}/src/sass", overwrite: false)
}

def parseArgs() {
	args = args ? args.split('\n') : []
    switch (args.size()) {
            case 0:
            	    return [ "User", "Profile" ]
                    break
            case 2:
                    println "Setting up nimble with custom User domain class: ${args[0]}"
                    println "Setting up nimble with custom Profile domain class: ${args[1]}"
                    return [args[0], args[1]]
                    break
            default:
                	usage()
                	break
    }
}

private void usage() {
	println 'Usage: grails jack-be-nimble <User class name> <Profile class name>'
	System.exit(1)
}
 
