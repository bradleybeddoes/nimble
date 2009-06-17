<script type="text/javascript">

  $(function() {

    listMembers();

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

  function searchMembers() {
    var dataString = "id=" + ${parentID.encodeAsHTML()} + "&q=" + $('#qmembers').val();
    $.ajax({
      type: "POST",
      url: "${createLink(action:'searchnewmembers')}",
      data: dataString,
      success: function(res) {
        $("#membersearchresponse").empty();
        $("#membersearchresponse").append(res).show();
        bindUserHighlight();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "There was an error executing your search");
      }
    });
  }

  function searchGroupMembers() {
    var dataString = "id=" + ${parentID.encodeAsHTML()} + "&q=" + $('#qmembersgroup').val();
    $.ajax({
      type: "POST",
      url: "${createLink(action:'searchnewgroupmembers')}",
      data: dataString,
      success: function(res) {
        $("#membergroupsearchresponse").empty();
        $("#membergroupsearchresponse").append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "There was an error executing your search");
      }
    });
  }

  function addMember(userID, username) {
    var dataString = 'id=' + ${parentID.encodeAsHTML()} + '&userID=' + userID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'addmember')}",
      data: dataString,
      success: function(res) {
        growl("success", "Added the user " + username + " as a member");
        listMembers();
        searchMembers();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "Failed to add user as a member");
      }
    });
  }

  function removeMember(userID, username) {
    var dataString = 'id=' + ${parentID.encodeAsHTML()} + '&userID=' + userID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'removemember')}",
      data: dataString,
      success: function(res) {
        growl("success", "Removed membership of the user " + username);
        listMembers();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "Failed to remove users membership");
      }
    });
  }

  <g:if test="${groupmembers}">
  function addGroupMember(groupID, name) {
    var dataString = 'id=' + ${parentID.encodeAsHTML()} + '&groupID=' + groupID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'addgroupmember')}",
      data: dataString,
      success: function(res) {
        growl("success", "Added the group " + name + " as a member");
        listMembers();
        searchGroupMembers();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "Failed to add group as a member");
      }
    });
  }

  function removeGroupMember(groupID, name) {
    var dataString = 'id=' + ${parentID.encodeAsHTML()} + '&groupID=' + groupID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'removegroupmember')}",
      data: dataString,
      success: function(res) {
        growl("success", "Removed membership of the group " + name);
        listMembers();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "Failed to remove groups membership");
      }
    });
  }
  </g:if>

  function listMembers() {
    var dataString = 'id=${parentID.encodeAsHTML()}';
    $.ajax({
      type: "GET",
      url: "${createLink(action:'listmembers')}",
      data: dataString,
      success: function(res) {
        $("#currentmembers").empty().hide();
        $("#currentmembers").append(res).show();
        bindUserHighlight();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "Failed to list members");
      }
    });
  }
</script>

<div id="members" class="section">

  <h3>Current Members</h3>
  <div id="currentmembers">
  </div>

  <g:if test="${!protect}">
    <div id="showaddmembers">
      <a id="showaddmembersbtn" class="button icon icon_group_add">Add Members</a>
    </div>

    <div id="addmembers">
      <h4>Add Members</h4>

      <g:if test="${groupmembers}">
        <g:radio name="memberselect" id="searchmembergroups"/><span class="icon icon_group"></span>&nbsp;Groups
        <g:radio name="memberselect" id="searchmemberusers" checked="true"/><span class="icon icon_user"></span>&nbsp;Users
      </g:if>

      <div id="memberaddusers">
        <p>
          To add additional users as members simply search for the user below. You can search on username, full name and email address.
        </p>

        <div class="searchbox">
          <g:textField name="qmembers" class="enhancedinput"/>
          <button onClick="searchMembers();" class="button icon icon_magnifier">Search</button>
          <button id="closeaddmembersbtn" class="button icon icon_cross">Close</button>
        </div>

        <div id="membersearchresponse" class="clear">
        </div>
      </div>

      <g:if test="${groupmembers}">
        <div id="memberaddgroups">
          <p>
            To add additional groups as members simply search for a group name below.
          </p>

          <div class="searchbox">
            <g:textField name="qmembersgroup" class="enhancedinput"/>
            <button onClick="searchGroupMembers();" class="button icon icon_magnifier">Search</button>
            <button id="closeaddgroupmembersbtn" class="button icon icon_cross">Close</button>
          </div>

          <div id="membergroupsearchresponse" class="clear">
          </div>
        </div>
      </g:if>

    </div>
  </g:if>
</div>