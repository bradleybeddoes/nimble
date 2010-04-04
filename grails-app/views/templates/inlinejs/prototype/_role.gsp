var roleSearchEndpoint = "${createLink(action:'searchroles')}";
var roleListEndpoint = "${createLink(action:'listroles')}";
var roleGrantEndpoint = "${createLink(action:'grantrole')}";
var roleRemoveEndpoint = "${createLink(action:'removerole')}";

document.observe("dom:loaded", function() {
	listRoles(${parent.id});
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