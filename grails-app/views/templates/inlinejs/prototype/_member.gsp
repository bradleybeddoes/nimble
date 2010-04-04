var memberListEndpoint = "${createLink(action:'listmembers')}";
var memberSearchEndpoint = "${createLink(action:'searchnewmembers')}";
var memberGroupSearchEndpoint = "${createLink(action:'searchnewgroupmembers')}";
var memberAddEndpoint = "${createLink(action:'addmember')}";
var memberRemoveEndpoint = "${createLink(action:'removemember')}";
var memberAddGroupEndpoint = "${createLink(action:'addgroupmember')}";
var memberRemoveGroupEndpoint = "${createLink(action:'removegroupmember')}";

document.observe("dom:loaded", function() {
	listMembers(${parent.id});
    if($("addmembers")) $("addmembers").hide();
    if($("memberaddgroups")) $("memberaddgroups").hide();

    if($("showaddmembersbtn"))
    $("showaddmembersbtn").observe('click',function() {
      $("showaddmembers").hide();
      $("addmembers").show("blind");
    })

    if($("searchmembergroups"))
    $("searchmembergroups").observe('click',function() {
      $("memberaddusers").hide();
      $("memberaddgroups").show("blind");
    });

    if($("searchmemberusers"))
    $("searchmemberusers").observe('click',function() {
      $("memberaddgroups").hide();
      $("memberaddusers").show();
    });

    if($("closeaddmembersbtn"))
    $("closeaddmembersbtn").observe('click',function() {
      $("addmembers").hide();
      $("showaddmembers").show();
    });

    if($("closeaddgroupmembersbtn"))
    $("closeaddgroupmembersbtn").observe('click',function() {
      $("addmembers").hide();
      $("showaddmembers").show();
    });	
});