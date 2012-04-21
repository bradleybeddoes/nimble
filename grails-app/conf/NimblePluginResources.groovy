
modules = {
    'nimble-core' {
		dependsOn "jquery, jquery-ui"
		resource id:'base-css', url:[plugin:'nimble', dir:'css', file:'nimble.css'], disposition:'head'
		resource id:'famfam', url:[plugin:'nimble', dir:'css', file:'famfamfam.css'], disposition:'head'
		resource id:'core-js', url:[plugin:'nomble', dir:'js', file:'nimblecore.js'], disposition:'head'
    }
	'nimble-ui' {
		dependsOn "nimble-core"
		resource id:'ui-js', url:[plugin:'nimble', dir:'js', file:'nimbleui.js'], disposition:'head'
	}
	
	'jgrawl' {
		dependsOn 'jquery'
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'jquery.jgrowl.css'], disposition:'head'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.jgrowl_minimized.js'] , disposition:'head', nominify: true
	}
	
	'jquery-url' {
		dependsOn 'jquery'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.url.packed.js'], disposition:'head'
	}
	
	'jquery-bt' {
		dependsOn 'jquery'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.bt.js'], disposition:'head'
	}
	
	'nimble-admin' {
		dependsOn 'nimble-core, nimble-ui, jgrawl, jquery-url, jquery-bt'
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'administration.css'], disposition:'head'
	} 
	
	'nimble-login' {
		dependsOn 'nimble-admin'
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'login.css'], disposition:'head'
	}
}