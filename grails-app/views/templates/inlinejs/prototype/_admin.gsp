nimble.endpoints=$H(nimble.endpoints).merge({
 admin: {
  'list':'${createLink(controller:'admins', action:'list')}',
  'search':'${createLink(action:'search')}',
  'remove':'${createLink(action:'delete')}',
  'grant':'${createLink(action:'create')}' 
 }
}).toObject();
	
document.observe("dom:loaded", function() {
  nimble.listAdministrators();
});