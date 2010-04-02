var groupSearchEndpoint = "${createLink(action:'searchgroups')}";
var groupListEndpoint = "${createLink(action:'listgroups')}";
var groupGrantEndpoint = "${createLink(action:'grantgroup')}";
var groupRemoveEndpoint = "${createLink(action:'removegroup')}"

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

    listGroups('${parent.id.encodeAsHTML()}');
});