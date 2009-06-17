<head>
  <meta name="layout" content="admin"/>
  <title>Administrators</title>

  <script type="text/javascript">
    $(function() {
      listAdministrators();
    });

    function listAdministrators() {
      $.ajax({
        type: "POST",
        url: "${createLink(action:'list')}",
        success: function(res) {
          $("#admins").empty();
          $("#admins").append(res);
          bindUserHighlight();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'Failed to list administrators');
        }
      });
    }

    function searchAdministrators() {
      var dataString = "q=" + $('#q').val();
      $.ajax({
        type: "POST",
        url: "${createLink(action:'search')}",
        data: dataString,
        success: function(res) {
          $("#searchresponse").empty();
          $("#searchresponse").append(res);
          bindUserHighlight();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'No users found');
        }
      });
    }

    function deleteAdministrator(userID, username) {
      var dataString = 'id=' + userID;
      $.ajax({
        type: "POST",
        url: "${createLink(action:'delete')}",
        data: dataString,
        success: function(res) {
          growl('success', 'Revoked administrator rights from ' + username);
          listAdministrators();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'Failed to remove administrator rights from ' + username);
        }
      });
    }

    function grantAdministrator(userID, username) {
      var dataString = 'id=' + userID;
      $.ajax({
        type: "POST",
        url: "${createLink(action:'create')}",
        data: dataString,
        success: function(res) {
          growl('success', 'Granted administration rights to ' + username);
          searchAdministrators();
          listAdministrators();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'Failed to grant administration rights to ' + username);
        }
      });
    }

  </script>

</head>

<body>

<div class="container">
  <h2>Administrators</h2>
  <p>
    The following users have the administration role.

    This means they can undertake any operation and access any content regardless of other security measures in place.
  </p>

  <div id="admins">
  </div>

  <h3>Add Administrator</h3>
  <p>
    To add additional administrators simply search for the users below. You can search on username, full name and email address.
  </p>

  <div class="searchbox">
    <g:textField name="q" class="easyinput"/>
    <button onClick="searchAdministrators();" class="button icon icon_magnifier">Search</button>
  </div>

  <div id="searchresponse" class="clear">

  </div>
</div>
</body>