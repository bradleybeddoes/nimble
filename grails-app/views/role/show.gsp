<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.role.show.title" args="[role.name.encodeAsHTML()]" /></title>
  <script type="text/javascript">
	<njs:permission parent="${role}"/>
	<njs:member parent="${role}"/>
	$(function() {
		$("#tabs").tabs();
	});
  </script>
</head>
<body>

  <h2><g:message code="nimble.view.role.show.heading" args="[role.name.encodeAsHTML()]" /></h2>

  <div class="details">
    <h3><g:message code="nimble.view.role.show.details.heading" /></h3>
    <table>
      <tbody>
      	<tr>
	        <td valign="top" class="name"><g:message code="nimble.label.name" /></td>
	        <td valign="top" class="value">${fieldValue(bean: role, field: 'name')}</td>
	      </tr>

	      <tr>
	        <td valign="top" class="name"><g:message code="nimble.label.description" /></td>
	        <td valign="top" class="value">${fieldValue(bean: role, field: 'description')}</td>
	      </tr>

	      <tr>
	        <td valign="top" class="name"><g:message code="nimble.label.protected" /></td>
	        <td valign="top" class="value">
	          <g:if test="${role.protect}">
	            <span class="icon icon_tick"><g:message code="nimble.label.yes" /></span>
	          </g:if>
	          <g:else>
	            <span class="icon icon_cross"><g:message code="nimble.label.no" /></span>
	          </g:else>
	        </td>
	      </tr>
      </tbody>
    </table>

  </div>

  <div id="tabs">

    <ul>
      <li><a href="#tab-permissions" class="icon icon_lock"><g:message code="nimble.label.permissions" /></a></li>
      <li><a href="#tab-members" class="icon icon_cog"><g:message code="nimble.label.members" /></a></li>
    </ul>

    <div id="tab-permissions">
      <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent:role]"/>
    </div>

    <div id="tab-members">
      <g:render template="/templates/admin/members" contextPath="${pluginContextPath}" model="[parent:role, protect:role.protect, groupmembers:true]"/>
    </div>

  </div>

  <g:form action="delete" name="deleterole">
  	<g:hiddenField name="id" value="${role.id.encodeAsHTML()}"/>
  </g:form>

	<n:confirm/>

</body>
