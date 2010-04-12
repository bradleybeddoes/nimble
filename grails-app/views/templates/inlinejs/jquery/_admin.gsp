jQuery.extend(nimble.endpoints,{
 admin: {
  'list':'${createLink(controller:'admins', action:'list')}',
  'search':'${createLink(action:'search')}',
  'remove':'${createLink(action:'delete')}',
  'grant':'${createLink(action:'create')}' 
 }
});
	
$(function() {
  nimble.listAdministrators();
});