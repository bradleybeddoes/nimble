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

USAGE = """
    jack-be-nimble PKG USER PROFILE

where
    PKG  = The package name to use for your User and Profile classes.
    USER = The name of your User domain class (which will be created).
    PROFILE = The name of your Profile domain class (which will be created).
"""

target ( default : 'Sets up a new project with a common Nimble base environment ready for customization' ) {
	
  if(new File("${basedir}/grails-app/conf/NimbleConfig.groovy").exists()) {
        println "Existing NimbleConfig.groovy located"
	println "This script does not currently manage Nimble upgrades, please see release documentation for any required changed to NimbleConfig.groovy or other components"
	System.exit(1)
  }
	
  def user, profile, pack, packdir
  (pack, user, profile) = parseArgs()
  packdir = pack.replace('.', '/')
  
  def userbinding = [ 'pack':pack, 'classname':user, 'baseclassname':'UserBase' ]
  def profilebinding = [ 'pack':pack, 'classname':profile, 'baseclassname':'ProfileBase' ]
  def configbinding = [ 'pack':pack, 'user':user, 'profile':profile ]

  def engine = new SimpleTemplateEngine()
  def usertemplate = engine.createTemplate(new FileReader("${nimblePluginDir}/src/templates/domain/Base.groovy")).make(userbinding)
  def profiletemplate = engine.createTemplate(new FileReader("${nimblePluginDir}/src/templates/domain/Base.groovy")).make(profilebinding)
  def configtemplate = engine.createTemplate(new FileReader("${nimblePluginDir}/src/templates/conf/NimbleConfig.groovy")).make(configbinding)
  
  echo(" Jack be nimble \n Jack be quick \n Jack jump over \n The candlestick.")

  // Config
  new File("${basedir}/grails-app/conf/NimbleConfig.groovy").write(configtemplate.toString())
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleBootStrap.groovy", tofile: "${basedir}/grails-app/conf/NimbleBootStrap.groovy", overwrite: false)
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleSecurityFilters.groovy", tofile: "${basedir}/grails-app/conf/NimbleSecurityFilters.groovy", overwrite: false)
  copy(file:"${nimblePluginDir}/src/templates/conf/NimbleUrlMappings.groovy", tofile: "${basedir}/grails-app/conf/NimbleUrlMappings.groovy", overwrite: false)

  // Domain Objects
  mkdir(dir:"${basedir}/grails-app/domain/${packdir}")
  new File("${basedir}/grails-app/domain/${packdir}/${user}.groovy").write(usertemplate.toString())
  new File("${basedir}/grails-app/domain/${packdir}/${profile}.groovy").write(profiletemplate.toString())

  // Templates
  copy( todir: "${basedir}/grails-app/views/templates/nimble" , overwrite: false ) { fileset ( dir : "${nimblePluginDir}/grails-app/views/templates/nimble" ) }

  // Sass
  mkdir( dir:"${basedir}/src/sass" )
  copy(file:"${nimblePluginDir}/src/sass/_rounded.sass", todir: "${basedir}/src/sass", overwrite: false)
  copy(file:"${nimblePluginDir}/src/sass/_uielements.sass", todir: "${basedir}/src/sass", overwrite: false)

  // i18n
  copy(file:"${nimblePluginDir}/grails-app/i18n/messages-nimble.properties", todir: "${basedir}/grails-app/i18n", overwrite: false)
  copy(file:"${nimblePluginDir}/grails-app/i18n/messages-nimble_de.properties", todir: "${basedir}/grails-app/i18n", overwrite: false)

}

def parseArgs() {
	args = args ? args.split('\n') : []
    switch (args.size()) {
            case 3:
            		println "Using package ${args[0]} to create custom classes"
                    println "Setting up nimble with custom User domain class: ${args[1]}"
                    println "Setting up nimble with custom Profile domain class: ${args[2]}"
                    return [args[0], args[1], args[2]]
                    break
            default:
                	usage()
                	break
    }
}

private void usage() {
	println "Usage:\n${USAGE}"
	System.exit(1)
}
 
