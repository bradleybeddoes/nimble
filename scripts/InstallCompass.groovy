includeTargets << grailsScript("Init")

target(main: "Execute jruby install to install compass gem!") {

  def s = File.separator
  def gemPath = userHome.getCanonicalPath() + /\.gem/

  // add gem source
  def command = "java -jar ${nimblePluginDir}"+s+"lib"+s+"jruby-complete-1.5.0.RC1.jar -S gem sources -a http://gems.github.com"
  println command
  def proc = command.execute()                 // Call *execute* on the string
  proc.consumeProcessOutput(System.out, System.err)
  proc.waitFor()

  // install compass
  command = "java -jar ${nimblePluginDir}"+s+"lib"+s+"jruby-complete-1.5.0.RC1.jar -S gem install -i ${gemPath} chriseppstein-compass --no-rdoc --no-ri"
  println command
  proc = command.execute()                 // Call *execute* on the string
  proc.consumeProcessOutput(System.out, System.err)
  proc.waitFor()

  def foundCompass = false
  new File(userHome.getCanonicalPath() + /\.gem\jruby\1.8\gems/).eachDir { dir -> if(dir.getName().startsWith('chriseppstein-compass')) foundCompass = true }
  if(foundCompass) {
    println "Compass Successfully Installed"

    compass = gemPath+s+"bin"+s+"compass"
    command = "java -jar ${nimblePluginDir}${s}lib${s}jruby-complete-1.5.0.RC1.jar -S ${compass} -h"
    println command
    proc = command.execute()                 // Call *execute* on the string
    proc.consumeProcessOutput(System.out, System.err)
    proc.waitFor()    
  }
}

setDefaultTarget(main)
