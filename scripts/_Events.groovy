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

import org.apache.tools.ant.taskdefs.Ant
import org.apache.catalina.loader.WebappLoader
import grails.util.GrailsUtil

includeTargets << grailsScript("_GrailsArgParsing")

createVirtualDirectory = { tomcat,name,path ->
  // nimbletest/plugins/nimble-0.4-SNAPSHOT/dev/js/jquery/
  def s=File.separator
  def plugin = new NimbleGrailsPlugin()
  buildroot= "/nimble/WEB-INF/classes"
  webroot  = new File(nimblePluginDir.getCanonicalPath() + s + path).getCanonicalPath()
  name = serverContextPath + "/plugins/" + plugin.title.toLowerCase() + "-" + plugin.version + "/" + name
  println "Creating virtual directory of " + name + " pointed to " + webroot
  context = tomcat.addWebapp(name, webroot);
  context.reloadable = true
  WebappLoader loader = new WebappLoader(tomcat.class.classLoader)
  loader.addRepository(new File(buildroot).toURI().toURL().toString());
  context.loader = loader
  loader.container = context
}

eventConfigureTomcat = {tomcat ->
  if(GrailsUtil.environment=="development")
    createVirtualDirectory(tomcat,"dev",'./src')
}

copyFile = { from,to ->
  def src=new File(from)
  def dest=new File(to)
  if(!dest.exists() || src.lastModified()>dest.lastModified()) {
    ant.copy(file:"${src.getCanonicalFile()}", tofile:"${dest.getCanonicalFile()}", overwrite: true, preservelastmodified:true)
  }
}

eventCleanStart = {
  if(ant.antProject.properties."base.name"=='nimble') {
    def s=File.separatorChar

    // clear the destination directory
    def js = "${nimblePluginDir}"+s+"web-app"+s+"js"+s
    ant.delete(dir:"${js}")
    def css = "${nimblePluginDir}"+s+"web-app"+s+"css"+s
    ant.delete(dir:"${css}")
    def images = "${nimblePluginDir}"+s+"web-app"+s+"images"+s
    ant.delete(dir:"${images}")
  }
}

compressFiles = { fileType ->
  // determine source and destination base paths
  def s=File.separatorChar
  def nimblePath = (new File(nimblePluginDir.toString())).getCanonicalPath()

  def from = nimblePath + s + "src" + s + fileType + s
  def to = nimblePath + s +"web-app" + s + fileType + s

  // make sure base destination path exists
  ant.mkdir( dir:"${to}" )

  // remove js files that no longer exist
  def f = new File( to )
  f.eachFileRecurse() { file->
    if(!file.isDirectory()) {
      def srcfile = (from+file.getAbsolutePath()).replace(to,'')
      def file2 = new File(srcfile)

      // does the file exist
      if(!file2.exists()) {
        ant.delete(file:"${file.getAbsolutePath()}")
      }
    }
  }

  f = new File( from )
  f.eachFileRecurse() { file->
    def fromFile = file.getAbsolutePath()
    def tofile = to+fromFile.replace(from,'')

    // if it is a file
    if(!file.isDirectory()) {
      def file2 = new File(tofile)

      // if target does not exist or is older then copy/compress
      if((!file2.exists() || file2.lastModified()<file.lastModified()) && fromFile.indexOf('.')>0) {

        // if it is not already compressed
        if(tofile.indexOf('.min.')<0 && fromFile.endsWith('.'+fileType) ) {

          // we need to compress so fire off a command to execute the yuicompressor
          println ' [compress] compressing ' +fromFile+' to '+tofile
          def command = "java -jar ${nimblePluginDir}"+s+"lib"+s+"yuicompressor-2.4.2.jar -o ${tofile} ${fromFile}"
          def proc = command.execute()                 // Call *execute* on the string
          proc.waitFor()

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

eventCompileStart = {
  compressFiles('js')
  compressFiles('css')
  compressFiles('images')  // does not compress images, just copies, used to make sure resources are treated the same 
}

