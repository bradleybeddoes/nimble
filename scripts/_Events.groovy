/*
*  Nimble, an extensive application base for Grails
*  Copyright (C) 2010 Chris Doty
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

import grails.util.GrailsUtil
import org.apache.catalina.loader.WebappLoader
import org.codehaus.groovy.grails.plugins.GrailsPluginUtils

includeTargets << grailsScript("_GrailsArgParsing")

createVirtualDirectory = { tomcat,name,path ->
  try {
    def aliasName = null
    new GrailsPluginUtils().getPluginInfos().each { it ->
      if(it.getName()=='nimble') {
        aliasName = serverContextPath + "/plugins/" + it.getName() + "-" + it.getVersion() + "/" + name
      }
    }

    if(aliasName) {
      def s=File.separator
      buildroot= "/nimble/WEB-INF/classes"
      webroot  = new File(nimblePluginDir.getCanonicalPath() + s + path).getCanonicalPath()
      println "Creating virtual directory of " + aliasName + " pointed to " + webroot
      context = tomcat.addWebapp(aliasName, webroot);
      context.reloadable = true
      WebappLoader loader = new WebappLoader(tomcat.class.classLoader)
      loader.addRepository(new File(buildroot).toURI().toURL().toString());
      context.loader = loader
      loader.container = context
    }
  } catch( Exception e ) {
    println 'failed to create virtual directory ' + name
    println e.message
  }
}

eventConfigureTomcat = {tomcat ->
  if(GrailsUtil.environment=="development")
    createVirtualDirectory(tomcat,"dev",'./src')
}

copyFile = { from,to ->
  def src=new File("${from}")
  def dest=new File("${to}")
  if(!dest.exists() || src.lastModified()>dest.lastModified()) {
    ant.copy(file:"${src.getCanonicalFile()}", tofile:"${dest.getCanonicalFile()}", overwrite: true, preservelastmodified:true)
  }
}

eventCleanStart = {
  if(ant.antProject.properties."base.name"=='nimble' && !buildConfig.nimble.resources.noclean) {
    def s=File.separator

    // clear the destination directory
    def js = "${nimblePluginDir}${s}web-app${s}js${s}"
    ant.delete(dir:"${js}")
    def css = "${nimblePluginDir}${s}web-app${s}css${s}"
    ant.delete(dir:"${css}")
    def images = "${nimblePluginDir}${s}web-app${s}images${s}"
    ant.delete(dir:"${images}")
  }
}

compressFiles = { fileType,compress ->
  // determine source and destination base paths
  def s=File.separatorChar
  def nimblePath = (new File("${nimblePluginDir.toString()}")).getCanonicalPath()

  def from = nimblePath + s + "src" + s + fileType + s
  def to = nimblePath + s +"web-app" + s + fileType + s

  // make sure base destination path exists
  ant.mkdir( dir:"${to}" )

  // remove js files that no longer exist
  def f = new File( to )
  f.eachFileRecurse() { file->
    if(!file.isDirectory()) {
      def srcfile = (from+file.getCanonicalPath()).replace(to,'')
      def file2 = new File(srcfile)

      // does the file exist
      if(!file2.exists()) {
        ant.delete(file:"${file.getCanonicalPath()}")
      }
    }
  }

  f = new File( from )
  f.eachFileRecurse() { file->
    def fromFile = file.getCanonicalPath()
    def tofile = to+fromFile.replace(from,'')

    // if it is a file
    if(!file.isDirectory()) {
      def file2 = new File(tofile)

      // if target does not exist or is older then copy/compress
      if((!file2.exists() || file2.lastModified()<file.lastModified()) && fromFile.indexOf('.')>0) {

        // if it is not already compressed
        if(tofile.indexOf('.min.')<0 && fromFile.endsWith('.'+fileType) && compress ) {

          // we need to compress so fire off a command to execute the yuicompressor
          println ' [compress] compressing ' +fromFile+' to '+tofile
          def command = "java -jar ${nimblePluginDir}"+s+"lib"+s+"yuicompressor-2.4.2.jar -o ${tofile} ${fromFile}"
          def proc = command.execute()                 // Call *execute* on the string
          proc.waitFor()

          // make the last modified of the compressed version same as source
          new File(tofile).setLastModified((new File("${fromFile}").lastModified()))
        } else {
          // copy the file
          copyFile(fromFile,tofile)
        }
      }
    } else {
      // if it is a directory make it
      ant.mkdir( dir:"${to}${file.name}" )
    }
  }
}

checkForChangeInSASS = { from,to ->
  def s = File.separator
  from = new File("${nimblePluginDir}${s}${from}").getCanonicalPath()
  to = new File("${nimblePluginDir}${s}${to}").getCanonicalPath()

  def hasChanged = false
  f = new File( from )

  // check each valid .SASS file to see if it has changed
  f.eachFileRecurse() { file->
    def fromFile = file.getCanonicalPath()
    def toFile = to+s+file.getName().replace(".sass",".css ").trim()

    // if it is a file
    if(!file.isDirectory()) {
      def file2 = new File(toFile)

      // if target does not exist or is older then copy/compress
      if((!file2.exists() || file2.lastModified()<file.lastModified()) && file2.getName().indexOf('_')!=0 && fromFile.indexOf('.sass-cache')<0) {
        hasChanged = true
      }
    }
  }

  return hasChanged
}

compileSASS = {
  // determine if compass is installed
  def gemPath = userHome.getCanonicalPath() + /\.gem\jruby\1.8\gems/
  def foundCompass = false
  new File(gemPath).eachDir { dir -> if(dir.getName().startsWith('chriseppstein-compass')) foundCompass = true }

  // compass installed so do checks and run it if needed
  if(foundCompass) {
    def from = "src/sass"
    def to = "src/css"

    // check source and destination paths for differences
    if(checkForChangeInSASS(from,to)) {
      def s = File.separator
      gemPath = userHome.getCanonicalPath() + /\.gem/

      // difference found so execute compass to compile SASS
      compass = gemPath+s+"bin"+s+"compass"
      command = "java -jar ${nimblePluginDir}${s}lib${s}jruby-complete-1.5.0.RC1.jar -S ${compass} --update --sass-dir ${from} --css-dir ${to}"
      println command
      proc = command.execute()                 // Call *execute* on the string
      proc.consumeProcessOutput(System.out, System.err)
      proc.waitFor()
    }
  }
}

eventCompileStart = {
  def compile = !buildConfig.nimble.resources.nocompilesass
  if(compile) {
    println 'nimble SASS compilation check'
    compileSASS()
  }

  def modcheck = !buildConfig.nimble.resources.nomodcheck
  if(modcheck) {
    println 'nimble resource modification check'
    def compress = !buildConfig.nimble.resources.nocompress
    compressFiles('js',compress)
    compressFiles('css',compress)
    compressFiles('images',compress)  // does not compress images, just copies, used to make sure resources are treated the same
  }
}

