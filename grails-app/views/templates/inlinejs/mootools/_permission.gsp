var permissionListEndpoint = "${createLink(action:'listpermissions')}";
var permissionCreateEndpoint = "${createLink(action:'createpermission')}";
var permissionRemoveEndpoint = "${createLink(action:'removepermission')}";

window.addEvent('domready', function() {
	listPermissions(${parent.id});
	$("addpermissions").hide();

	$("showaddpermissionsbtn").addEvent('click',function () {
	  $("showaddpermissions").hide();
	  $("addpermissions").show("blind");
	});

	$("closepermissionsaddbtn").addEvent('click',function () {
	  $("addpermissions").hide();
	  $("showaddpermissions").show();
	});
});