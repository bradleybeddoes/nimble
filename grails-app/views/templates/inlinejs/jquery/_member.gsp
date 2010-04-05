jQuery.extend(nimble.endpoints,{
    member: {
     'list':'${createLink(action:'listmembers')}',
     'search':'${createLink(action:'searchnewmembers')}',
     'remove':'${createLink(action:'removemember')}',
     'add':'${createLink(action:'addmember')}',
     'groupSearch':'${createLink(action:'searchnewgroupmembers')}',
     'groupAdd':'${createLink(action:'addgroupmember')}',
     'groupRemove':'${createLink(action:'removegroupmember')}'
    }
});

$(function() {
	nimble.listMembers(${parent.id});
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