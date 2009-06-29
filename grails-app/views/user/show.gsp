<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>User</title>

  <script type="text/javascript">
    $(function() {
      $("#enableuserbtn").click(function () {
        enableUser();
      });

      $("#disableuserbtn").click(function () {
        disableUser();
      });

      $("#enableuserapibtn").click(function () {
        enableAPI();
      });

      $("#disableuserapibtn").click(function () {
        disableAPI();
      });

    <g:if test="${user.enabled}">
      $("#enableuser").hide();
      $("#enableduser").hide();
    </g:if>
    <g:else>
      $("#disableuser").hide();
      $("#disableduser").hide();
    </g:else>

    <g:if test="${user.remoteapi}">
      $("#disabledapi").hide();
      $("#enableuserapi").hide();
    </g:if>
    <g:else>
      $("#enabledapi").hide();
      $("#disableuserapi").hide();
    </g:else>
    });

    function enableUser() {
      var dataString = "id=${user.id}";
      $.ajax({
        type: "POST",
        url: "${createLink(action:'enable')}",
        data: dataString,
        success: function(res) {
          $("#enableuser").hide();
          $("#enableduser").hide();
          $("#disableuser").show();
          $("#disableduser").show();
          growl('success', 'Account enabled');

        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'There was an internal error when attempting to enable this account');
        }
      });
    }

    function disableUser() {
      var dataString = "id=${user.id}";
      $.ajax({
        type: "POST",
        url: "${createLink(action:'disable')}",
        data: dataString,
        success: function(res) {
          $("#disableuser").hide();
          $("#disableduser").hide();
          $("#enableuser").show();
          $("#enableduser").show();
          growl('success', 'Account disabled');

        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'There was an internal error when attempting to disable this account');
        }
      });
    }

    function enableAPI() {
      var dataString = "id=${user.id}";
      $.ajax({
        type: "POST",
        url: "${createLink(action:'enableapi')}",
        data: dataString,
        success: function(res) {
          $("#disabledapi").hide();
          $("#enabledapi").show();

          $("#disableuserapi").show();
          $("#enableuserapi").hide();
          growl('success', 'Remote API access enabled');

        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'There was an internal error when attempting to enable remote api access account');
        }
      });
    }

    function disableAPI() {
      var dataString = "id=${user.id}";
      $.ajax({
        type: "POST",
        url: "${createLink(action:'disableapi')}",
        data: dataString,
        success: function(res) {
          $("#disabledapi").show();
          $("#enabledapi").hide();

          $("#disableuserapi").hide();
          $("#enableuserapi").show();
          growl('success', 'Remote API access disabled');

        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', 'There was an internal error when attempting to disable remote api access for this account');
        }
      });
    }

  </script>

</head>

<body>

<div class="container">

  <h2>User ${user.username?.encodeAsHTML()}</h2></span>

  <div class="actions">
    <ul class="horizmenu">
      <li>
        <span class="userhighlight user_${user.id}"><g:link controller="profile" action="show" id="${user.id.encodeAsHTML()}" class="icon icon_user_go">Profile</g:link></span>
      </li>

      <li>
        <g:link action="edit" id="${user.id.encodeAsHTML()}" class="icon icon_user_gray">Edit</g:link>
      </li>

      <li>
        <g:if test="${!user.external}">
          <g:link action="changepassword" id="${user.id.encodeAsHTML()}" class="icon icon_key_go">Change Password</g:link>
        </g:if>
        <g:else>
          <g:link action="changelocalpassword" id="${user.id.encodeAsHTML()}" class="icon icon_key_go">Change Local Password</g:link>
        </g:else>
      </li>

      <li id="disableuser">
        <a id="disableuserbtn" class="icon icon_user_red">Disable</a>
      </li>
      <li id="enableuser">
        <a id="enableuserbtn" class="icon icon_user_green">Enable</a>
      </li>

      <li id="disableuserapi">
        <a id="disableuserapibtn" class="icon icon_world_delete">Disable Remote API</a>
      </li>
      <li id="enableuserapi">
        <a id="enableuserapibtn" class="icon icon_world_add">Enable Remote API</a>
      </li>

    </ul>
  </div>

  <div class="details">
    <h3>Account Details</h3>
    <table class="datatable">
      <tbody>

      <tr>
        <th>Login Name</th>
        <td>${user.username?.encodeAsHTML()}</td>
      </tr>

      <tr>
        <th>Created</th>
        <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.dateCreated}"/></td>
      </tr>

      <tr>
        <th>Last Updated</th>
        <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.lastUpdated}"/></td>
      </tr>

      <tr>
        <th>Type</th>
        <g:if test="${user.external}">
          <td class="value">Externally Managed Account</td>
        </g:if>
        <g:else>
          <td class="value">Locally Managed Account</td>
        </g:else>
      </tr>

      <tr>
        <th>State</th>
        <td class="value">

          <div id="disableduser">
            <span class="icon icon_tick">&nbsp;</span>Enabled
          </div>
          <div id="enableduser">
            <span class="icon icon_cross">&nbsp;</span>Disabled
          </div>

        </td>
      </tr>

      <tr>
        <th>Remote API Access</th>
        <td class="value">

          <div id="enabledapi">
            <span class="icon icon_tick">&nbsp;</span>Enabled
          </div>
          <div id="disabledapi">
            <span class="icon icon_cross">&nbsp;</span>Disabled
          </div>

        </td>
      </tr>

      </tbody>
    </table>
  </div>



  <g:if test="${user.federated}">
    <div class="details">
      <h3>Federation Provider</h3>
      <table>
        <tbody>
        <tr>
          <th>Provider</th>
          <td valign="top">
            <img src="${resource(dir: "images", file: user.federationProvider.details?.logoSmall)}" alt="${user.federationProvider.details?.displayName}"/>
            <a href="${user.federationProvider.details?.url?.location}" alt="${user.federationProvider.details?.url?.altText}">${user.federationProvider.details?.displayName}</a>

          </td>
        </tr>
        <tr>
          <th>Description</th>
          <td>${user.federationProvider.details?.description}</td>
        </tr>

        </tbody>
      </table>
    </div>
  </g:if>


  <div class="sections">

    <ul id="sections_" class="horizmenu">
      <li class="current"><a href="permissions_" class="icon icon_lock">Permissions</a></li>
      <li><a href="roles_" class="icon icon_cog">Roles</a></li>
      <li><a href="groups_" class="icon icon_group">Groups</a></li>
      <li><a href="logins_" class="icon icon_key">Logins</a></li>
    </ul>

    <div class="active_ sections_ permissions_">
      <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[ownerID:user.id.encodeAsHTML()]"/>
    </div>

    <div class="sections_ roles_">
      <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[ownerID:user.id.encodeAsHTML()]"/>
    </div>

    <div class="sections_ groups_">
      <g:render template="/templates/admin/groups" contextPath="${pluginContextPath}" model="[ownerID:user.id.encodeAsHTML()]"/>
    </div>

    <div class="sections_ logins_">
      <g:render template="/templates/admin/logins" contextPath="${pluginContextPath}" model="[ownerID:user.id.encodeAsHTML()]"/>      
    </div>

  </div>

</div>

</body>
