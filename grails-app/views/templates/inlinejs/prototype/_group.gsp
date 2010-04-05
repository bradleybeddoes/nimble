nimble.endpoints=$H(nimble.endpoints).merge({
group: {
  'list':'${createLink(action:'listgroups')}',
  'search':'${createLink(action:'searchgroups')}',
  'remove':'${createLink(action:'removegroup')}',
  'grant':'${createLink(action:'grantgroup')}'
 }
}).toObject();

document.observe("dom:loaded", function() {
  $("addgroups").hide();

  $("showaddgroupsbtn").observe('click',function () {
    $("showaddgroups").hide();
    $("addgroups").show("blind");
  });

  $("closegroupsearchbtn").observe('click',function () {
    $("addgroups").hide();
    $("showaddgroups").show();
  });

  nimble.listGroups('${parent.id.encodeAsHTML()}');
});