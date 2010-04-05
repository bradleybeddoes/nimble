nimble.endpoints=$H(nimble.endpoints).merge({
  member: {
   'list':'${createLink(action:'listmembers')}',
   'search':'${createLink(action:'searchnewmembers')}',
   'remove':'${createLink(action:'removemember')}',
   'add':'${createLink(action:'addmember')}',
   'groupSearch':'${createLink(action:'searchnewgroupmembers')}',
   'groupAdd':'${createLink(action:'addgroupmember')}',
   'groupRemove':'${createLink(action:'removegroupmember')}'
  }
}).toObject();

document.observe("dom:loaded", function() {
  nimble.listMembers(${parent.id});
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