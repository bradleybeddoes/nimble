<script type="text/javascript">

  $(function() {
    listRoles();
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

  function searchRoles() {
    var dataString = "id=" + ${ownerID.encodeAsHTML()} + "&q=" + $('#qroles').val();
    $.ajax({
      type: "POST",
      url: "${createLink(action:'searchroles')}",
      data: dataString,
      success: function(res) {
        $("#rolesearchresponse").empty().hide();
        $("#rolesearchresponse").append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "There was an error executing your search");
      }
    });
  }

  function listRoles() {
    var dataString = 'id=${ownerID.encodeAsHTML()}';
    $.ajax({
      type: "GET",
      url: "${createLink(action:'listroles')}",
      data: dataString,
      success: function(res) {
        $("#assignedroles").empty().append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to list roles');
      }
    });
  }

  function grantRole(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'grantrole')}",
      data: dataString,
      success: function(res) {
        listRoles();
        searchRoles();
        growl('success', 'Role was assigned');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to assign role');
      }
    });
  }

  function removeRole(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'removerole')}",
      data: dataString,
      success: function(res) {
        listRoles();
        growl('success', 'Role removed');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to remove role');
      }
    });
  }

</script>

<div id="roles" class="section">
  <h3>Assigned Roles</h3>

  <div id="assignedroles">
  </div>

  <div id="showaddroles">
    <a id="showaddrolesbtn" class="button icon icon_cog_add">Add Roles</a>
  </div>

  <div id="addroles">
    <h4>Add Roles</h4>
    <p>
      To add additional roles simply search for the role below. You can search on role name and description.
    </p>

    <div class="searchbox">
      <g:textField name="qroles" class=""/>
      <button onClick="searchRoles();" class="button icon icon_magnifier">Search</button>
      <button id="closerolesearchbtn" class="button icon icon_cross">Close</button>
    </div>

    <div id="rolesearchresponse" class="clear">
    </div>
  </div>

</div>
