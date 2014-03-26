nimble.endpoints.extend({
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

window.addEvent('domready', function() {
  nimble.listMembers(${parent.id});
  if($("addmembers")) $("addmembers").hide();
  if($("memberaddgroups")) $("memberaddgroups").hide();

  if($("showaddmembersbtn"))
  $("showaddmembersbtn").addEvent('click',function() {
    $("showaddmembers").hide();
    $("addmembers").show("blind");
  })

  if($("searchmembergroups"))
  $("searchmembergroups").addEvent('click',function() {
    $("memberaddusers").hide();
    $("memberaddgroups").show("blind");
  });

  if($("searchmemberusers"))
  $("searchmemberusers").addEvent('click',function() {
    $("memberaddgroups").hide();
    $("memberaddusers").show();
  });

  if($("closeaddmembersbtn"))
  $("closeaddmembersbtn").addEvent('click',function() {
    $("addmembers").hide();
    $("showaddmembers").show();
  });

  if($("closeaddgroupmembersbtn"))
  $("closeaddgroupmembersbtn").addEvent('click',function() {
    $("addmembers").hide();
    $("showaddmembers").show();
  });
});