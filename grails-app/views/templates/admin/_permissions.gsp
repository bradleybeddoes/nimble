<script type="text/javascript">

  $(function() {
    listPermissions();

    $("#addpermissions").hide();

    $("#showaddpermissionsbtn").click(function () {
      $("#showaddpermissions").hide();
      $("#addpermissions").show("blind");
    });

    $("#closepermissionsaddbtn").click(function () {
      $("#addpermissions").hide();
      $("#showaddpermissions").show();
    });
  });

  function listPermissions() {
    var dataString = 'id=${ownerID.encodeAsHTML()}';
    $.ajax({
      type: "GET",
      url: "${createLink(action:'listpermissions')}",
      data: dataString,
      success: function(res) {
        $("#currentpermission").empty().append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to list permissions');
      }
    });
  }

  function createPermission() {
    var dataString = 'id=${ownerID.encodeAsHTML()}' + '&first=' + $('#first_p').val() + '&second=' + $('#second_p').val() + '&third=' + $('#third_p').val() + '&fourth=' + $('#fourth_p').val();
    $.ajax({
      type: "POST",
      url: "${createLink(action:'createpermission')}",
      data: dataString,
      success: function(res) {
        $("#addpermissionserror").empty()
        listPermissions();
        growl('success', 'Permission granted');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        $("#addpermissionserror").empty().append(xhr.responseText)
        growl('error', 'Failed to create permission');
      }
    });
  }

  function removePermission(userID, permID) {
    var dataString = 'id=' + userID + '&permID=' + permID;
    $.ajax({
      type: "POST",
      url: "${createLink(action:'removepermission')}",
      data: dataString,
      success: function(res) {
        listPermissions();
        growl('success', 'Permission removed');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to remove permission from user');
      }
    });
  }
</script>

<div id="permissions" class="section">

  <h3>Assigned Permissions</h3>
  <div id="currentpermission">
  </div>

  <div id="showaddpermissions">
    <a id="showaddpermissionsbtn" class="button icon icon_group_add">Add Permission</a>
  </div>

  <div id="addpermissions">
    <h4>Add Permission</h4>
    <p>
      The following will create a new Wildcard permission and assign it to this user.
    </p>

    <div id="addpermissionserror"></div>

    <table>
      <tbody>
      <tr>
        <td>
          <g:textField name="first_p" class="easyinput"/> <strong>:</strong>
          <g:textField name="second_p" class="easyinput"/> <strong>:</strong>
          <g:textField name="third_p" class="easyinput"/> <strong>:</strong>
          <g:textField name="fourth_p" class="easyinput"/>
        </td>
      </tr>
      </tbody>
    </table>
    <button onClick="createPermission();" class="button icon icon_add">Add</button>
    <button id="closepermissionsaddbtn" class="button icon icon_cross">Close</button>
  </div>
</div>