
File icons = new File('/Users/beddoes/Development/workspace/nimble/web-app/css/icons')

println '.icon { background-repeat:no-repeat; padding-left:18px; background-position:center left;}'
icons.list().each {
	cssName = it.substring(0, it.lastIndexOf('.'))
	println '.icon_' + cssName + '{background-image:url(\'icons/'+it+'\');}'
}
