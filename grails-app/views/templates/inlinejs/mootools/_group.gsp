nimble.endpoints.extend({
group: {
    'list':'${createLink(action:'listgroups')}',
    'search':'${createLink(action:'searchgroups')}',
    'remove':'${createLink(action:'removegroup')}',
    'grant':'${createLink(action:'grantgroup')}'
  }
});

window.addEvent('domready', function() {
  $("addgroups").hide();

  $("showaddgroupsbtn").addEvent('click',function () {
    $("showaddgroups").hide();
    $("addgroups").show("blind");
  });

  $("closegroupsearchbtn").addEvent('click',function () {
    $("addgroups").hide();
    $("showaddgroups").show();
  });

  nimble.listGroups('${parent.id.encodeAsHTML()}');
});