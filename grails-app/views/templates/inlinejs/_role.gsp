var roleSearchEndpoint = "${createLink(action:'searchroles')}";
var roleListEndpoint = "${createLink(action:'listroles')}";
var roleGrantEndpoint = "${createLink(action:'grantrole')}";
var roleRemoveEndpoint = "${createLink(action:'removerole')}";

$(function() {
	listRoles(${parent.id});
    $("#addroles").hide();

    $("#showaddrolesbtn").click(function () {
      $("#showaddroles").hide();
      $("#addroles").show("blind");
    });

    $("#closerolesearchbtn").click(function () {
      $("#addroles").hide();
      $("#showaddroles").show();
    });
});