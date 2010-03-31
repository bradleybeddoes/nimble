var permissionListEndpoint = "${createLink(action:'listpermissions')}";
var permissionCreateEndpoint = "${createLink(action:'createpermission')}";
var permissionRemoveEndpoint = "${createLink(action:'removepermission')}";

$(function() {
	listPermissions(${parent.id});
	$("#addpermissions").hide();

	$("#showaddpermissionsbtn").click(function () {
	  $("#showaddpermissions").hide();
	  $("#addpermissions").show("blind");
	});

	$("#closepermissionsaddbtn").click(function () {
	  $("#addpermissions").hide();
	  $("#showaddpermissions").show();
	});
});