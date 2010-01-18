<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.user.show.title" args="[user.username?.encodeAsHTML()]" /></title>
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

  <h2><g:message code="nimble.view.user.show.heading" args="[user.username?.encodeAsHTML()]" /></h2></span>

  <div class="details">
    <h3><g:message code="nimble.view.user.show.details.heading" /></h3>
    <table class="datatable">
      <tbody>

      <tr>
        <th><g:message code="nimble.label.username" /></th>
		<td>${user.username?.encodeAsHTML()}</td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.created" /></th>
        <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.dateCreated}"/></td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.lastupdated" /></th>
        <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.lastUpdated}"/></td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.type" /></th>
        <g:if test="${user.external}">
          <td class="value"><g:message code="nimble.label.external.managment" /></td>
        </g:if>
        <g:else>
          <td class="value"><g:message code="nimble.label.local.managment" /></td>
        </g:else>
      </tr>

      <tr>
        <th><g:message code="nimble.label.state" /></th>
        <td class="value">

          <div id="disableduser">
            <span class="icon icon_tick">&nbsp;</span><g:message code="nimble.label.enabled" />
          </div>
          <div id="enableduser">
            <span class="icon icon_cross">&nbsp;</span><g:message code="nimble.label.disabled" />
          </div>

        </td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.remoteapi" /></th>
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

  <div id="tabs">

    <ul>
	  <li><a href="#tab-extendedinfo" class="icon icon_user"><g:message code="nimble.label.extendedinformation" /></a></li>
	  <g:if test="${user.federated}">
	  <li><a href="#tab-federation" class="icon icon_world"><g:message code="nimble.label.federatedaccount" /></a></li>
	  </g:if>
      <li><a href="#tab-permissions" class="icon icon_lock"><g:message code="nimble.label.permissions" /></a></li>
      <li><a href="#tab-roles" class="icon icon_cog"><g:message code="nimble.label.roles" /></a></li>
      <li><a href="#tab-groups" class="icon icon_group"><g:message code="nimble.label.groups" /></a></li>
      <li><a href="#tab-logins" class="icon icon_key"><g:message code="nimble.label.logins" /></a></li>
    </ul>

    <div id="tab-extendedinfo">
		<g:render template="/templates/nimble/user/extendedinformation" contextPath="/" model="[user:user]"/>	
    </div>

	  <g:if test="${user.federated}">
	    <div id="tab-federation">
	      <h3><g:message code="nimble.view.user.show.federated.heading" /></h3>
	      <table>
	        <tbody>
	        <tr>
	          <th><g:message code="nimble.label.provider" /></th>
	          <td valign="top">
	            <a href="${user.federationProvider.details?.url?.location}" alt="${user.federationProvider.details?.url?.altText}">${user?.federationProvider?.details?.displayName}</a>

	          </td>
	        </tr>
	        <tr>
	          <th><g:message code="nimble.label.description" /></th>
	          <td>${user?.federationProvider?.details?.description}</td>
	        </tr>

	        </tbody>
	      </table>
	    </div>
	  </g:if>

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
