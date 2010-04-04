var permissionListEndpoint = "${createLink(action:'listpermissions')}";
var permissionCreateEndpoint = "${createLink(action:'createpermission')}";
var permissionRemoveEndpoint = "${createLink(action:'removepermission')}";

document.observe("dom:loaded", function() {
	listPermissions(${parent.id});
	$("addpermissions").hide();

	$("showaddpermissionsbtn").observe('click',function () {
	  $("showaddpermissions").hide();
	  $("addpermissions").show("blind");
	});

	$("closepermissionsaddbtn").observe('click',function () {
	  $("addpermissions").hide();
	  $("showaddpermissions").show();
	});
});