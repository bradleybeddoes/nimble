<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>User</title>
  <script type="text/javascript">
  	<njs:user user="${user}"/>
	<njs:permission parent="${user}"/>
	<njs:role parent="${user}"/>
	<njs:group parent="${user}"/>
	
	$(function() {
		$("#tabs").tabs();
	});
  </script>
</head>

<body>

  <h2>User ${user.username?.encodeAsHTML()}</h2></span>

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

  <div id="tabs">

    <ul>
      <li><a href="#tab-permissions" class="icon icon_lock">Permissions</a></li>
      <li><a href="#tab-roles" class="icon icon_cog">Roles</a></li>
      <li><a href="#tab-groups" class="icon icon_group">Groups</a></li>
      <li><a href="#tab-logins" class="icon icon_key">Logins</a></li>
    </ul>

    <div id="tab-permissions">
      <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent:user]"/>
    </div>

    <div id="tab-roles">
      <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[parent:user]"/>
    </div>

    <div id="tab-groups">
      <g:render template="/templates/admin/groups" contextPath="${pluginContextPath}" model="[parent:user]"/>
    </div>

    <div id="tab-logins">
      <g:render template="/templates/admin/logins" contextPath="${pluginContextPath}" model="[parent:user]"/>      
    </div>

  </div>

</body>
