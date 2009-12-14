var memberListEndpoint = "${createLink(action:'listmembers')}";
var memberSearchEndpoint = "${createLink(action:'searchnewmembers')}";
var memberGroupSearchEndpoint = "${createLink(action:'searchnewgroupmembers')}";
var memberAddEndpoint = "${createLink(action:'addmember')}";
var memberRemoveEndpoint = "${createLink(action:'removemember')}";
var memberAddGroupEndpoint = "${createLink(action:'addgroupmember')}";
var memberRemoveGroupEndpoint = "${createLink(action:'removegroupmember')}";

$(function() {
	listMembers(${parent.id});
    $("#addmembers").hide();

    $("#memberaddgroups").hide();

    $("#showaddmembersbtn").click(function() {
      $("#showaddmembers").hide();
      $("#addmembers").show("blind");
    })

    $("#searchmembergroups").click(function() {
      $("#memberaddusers").hide();
      $("#memberaddgroups").show("blind");
    });

    $("#searchmemberusers").click(function() {
      $("#memberaddgroups").hide();
      $("#memberaddusers").show();
    });

    $("#closeaddmembersbtn").click(function() {
      $("#addmembers").hide();
      $("#showaddmembers").show();
    });

    $("#closeaddgroupmembersbtn").click(function() {
      $("#addmembers").hide();
      $("#showaddmembers").show();
    });	
});