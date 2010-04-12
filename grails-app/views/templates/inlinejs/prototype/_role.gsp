nimble.endpoints=$H(nimble.endpoints).merge({
 role: { 
  'list':'${createLink(action: 'listroles')}',
  'search':'${createLink(action: 'searchroles')}',
  'remove':'${createLink(action: 'removerole')}',
  'grant':'${createLink(action: 'grantrole')}'
 }
}).toObject();

document.observe("dom:loaded", function() {
  nimble.listRoles(${parent.id});
  $("addroles").hide();

  $("showaddrolesbtn").observe('click',function () {
    $("showaddroles").hide();
    $("addroles").show("blind");
  });

  $("closerolesearchbtn").observe('click',function () {
    $("addroles").hide();
    $("showaddroles").show();
  });
});