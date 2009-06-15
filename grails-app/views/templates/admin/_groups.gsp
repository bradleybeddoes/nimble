<script type="text/javascript">

  $(function() {
    listGroups();
    $("#addgroups").hide();

    $("#showaddgroupsbtn").click(function () {
      $("#showaddgroups").hide();
      $("#addgroups").show("blind");
    });

    $("#closegroupsearchbtn").click(function () {
      $("#addgroups").hide();
      $("#showaddgroups").show();
    });
  });

  function searchGroups() {
    var dataString = "id=" + ${ownerID.encodeAsHTML()} + "&q=" + $('#qgroups').val();
    $.ajax({
      type: "POST",
      url: "${createLink(action:'searchgroups')}",
      data: dataString,
      success: function(res) {
        $("#groupsearchresponse").empty().hide();
        $("#groupsearchresponse").append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "There was an error executing your search");
      }
    });
  }

  function listGroups() {
    var dataString = 'id=${ownerID.encodeAsHTML()}';
    $.ajax({
      type: "GET",
      url: "${createLink(action:'listgroups')}",
      data: dataString,
      success: function(res) {
        $("#assignedgroups").empty().append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to list groups');
      }
    });
  }

  function grantGroup(groupID) {
    var dataString = 'id=' + ${ownerID.encodeAsHTML()} + '&groupID=' + groupID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'grantgroup')}",
      data: dataString,
      success: function(res) {
        listGroups();
        searchGroups();
        growl('success', 'Group was assigned');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to assign group');
      }
    });
  }

  function removeGroup(groupID) {
    var dataString = 'id=' + ${ownerID.encodeAsHTML()} + '&groupID=' + groupID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'removegroup')}",
      data: dataString,
      success: function(res) {
        listGroups();
        growl('success', 'Group removed');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to remove group');
      }
    });
  }

</script>

<div id="groups" class="section">
  <h3>Group Membership</h3>

  <div id="assignedgroups">
  </div>

  <div id="showaddgroups">
    <a id="showaddgroupsbtn" class="button icon icon_group_add">Add Groups</a>
  </div>

  <div id="addgroups">
    <h4>Add Groups</h4>
    <p>
      To add additional groups simply search for the group name below.
    </p>

    <div class="searchbox">
      <g:textField name="qgroups" class="enhancedinput"/>
      <button onClick="searchGroups();" class="button icon icon_magnifier">Search</button>
      <button id="closegroupsearchbtn" class="button icon icon_cross">Close</button>
    </div>

    <div id="groupsearchresponse" class="clear">
    </div>

  </div>
</div>