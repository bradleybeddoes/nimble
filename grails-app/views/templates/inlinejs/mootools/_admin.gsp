nimble.endpoints.extend({
 admin: {
  'list':'${createLink(controller:'admins', action:'list')}',
  'search':'${createLink(action:'search')}',
  'remove':'${createLink(action:'delete')}',
  'grant':'${createLink(action:'create')}' 
 }
});
	
window.addEvent('domready', function() {
  nimble.listAdministrators();
});