var groupSearchEndpoint = "${createLink(action:'searchgroups')}";
var groupListEndpoint = "${createLink(action:'listgroups')}";
var groupGrantEndpoint = "${createLink(action:'grantgroup')}";
var groupRemoveEndpoint = "${createLink(action:'removegroup')}"

$(function() {
    listGroups('${parent.id.encodeAsHTML()}');
    $("#addgroups").hide();

    $("#showaddgroupsbtn").click(function () {
      $("#showaddgroups").hide();
      $("#addgroups").show("blind");
    });

    $("#closegroupsearchbtn").click(function () {
      $("#addgroups").hide();
      $("#showaddgroups").show();
    });
});