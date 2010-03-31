var roleSearchEndpoint = "${createLink(action:'searchroles')}";
var roleListEndpoint = "${createLink(action:'listroles')}";
var roleGrantEndpoint = "${createLink(action:'grantrole')}";
var roleRemoveEndpoint = "${createLink(action:'removerole')}";

window.addEvent('domready', function() {
	listRoles(${parent.id});
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