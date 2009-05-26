class NimbleGrailsPlugin {

    // the plugin version
    def version = "0.1"

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"

    // the other plugins this plugin depends on
    def dependsOn = [ ki: 0.1,
                      mail: 0.5,
                    ]
    
    // resources that are excluded from plugin packaging
    def pluginExcludes = [

    ]

    def author = "Bradley Beddoes (Intient Pty Ltd) + Open Source Contributors"
    def authorEmail = "nimbleproject@googlegroups.com"
    def title = "Nimble"
    def description = '''\\
    Nimble is an extensive application base environment for Grails developers.
    '''

    // URL to the plugin's documentation
    def documentation = "http://intient.com/oss/nimble"

    def doWithSpring = {

    }

    def doWithApplicationContext = { applicationContext ->

    }

    def doWithWebDescriptor = { xml ->

    }

    def doWithDynamicMethods = { ctx ->

    }

    def onChange = { event ->

    }

    def onConfigChange = { event ->
        
    }
}
