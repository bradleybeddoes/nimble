nimble.endpoints.extend({
role: { 'list':'${createLink(action: 'listroles')}',
            'search':'${createLink(action: 'searchroles')}',
            'remove':'${createLink(action: 'removerole')}',
            'grant':'${createLink(action: 'grantrole')}'
    }
});

window.addEvent('domready', function() {
  nimble.listRoles(${parent.id});
  $("addroles").hide();

  $("showaddrolesbtn").addEvent('click',function () {
    $("showaddroles").hide();
    $("addroles").show("blind");
  });

  $("closerolesearchbtn").addEvent('click',function () {
    $("addroles").hide();
    $("showaddroles").show();
  });
});