nimble.endpoints=$H(nimble.endpoints).merge({
  permission: {
  'list':'${createLink(action:'listpermissions')}',
  'remove':'${createLink(action:'removepermission')}',
  'create':'${createLink(action:'createpermission')}'
  }
}).toObject();

document.observe("dom:loaded", function() {
  nimble.listPermissions(${parent.id});
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