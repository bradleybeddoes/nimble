nimble.endpoints.extend({
  permission: { 'list':'${createLink(action:'listpermissions')}',
                'remove':'${createLink(action:'removepermission')}',
                'create':'${createLink(action:'createpermission')}' }
});

window.addEvent('domready', function() {
  nimble.listPermissions(${parent.id});
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