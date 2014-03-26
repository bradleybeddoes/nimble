<html>
<head>
	<meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
	<title><g:message code="nimble.view.group.show.title"  args="[group.name.encodeAsHTML()]" /></title>
	<script type="text/javascript">
		<njs:permission parent="${group}"/>
		<njs:role parent="${group}"/>
		<njs:member parent="${group}"/>
		nimble.createTabs('tabs');
	</script>
</head>
<body>

  <h2><g:message code="nimble.view.group.show.heading" args="[group.name.encodeAsHTML()]" /></h2>

  <div class="details">
    <h3><g:message code="nimble.view.group.show.details.heading" /></h3>
    <table class="datatable">
      <tbody>
      <tr>
        <th><g:message code="nimble.label.name" /></th>
        <td valign="top" class="value">${fieldValue(bean: group, field: 'name')}</td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.description" /></th>
        <td valign="top" class="value">${fieldValue(bean: group, field: 'description')}</td>

      </tr>

      <tr>
        <th><g:message code="nimble.label.protected" /></th>
        <td valign="top" class="value">
          <g:if test="${group.protect}">
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
        <li><a href="#tab-roles" class="icon icon_cog"><g:message code="nimble.label.roles" /></a></li>
        <li><a href="#tab-members" class="icon icon_group"><g:message code="nimble.label.members" /></a></li>
      </ul>

    <div id="tab-permissions">
      <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent:group]"/>
    </div>
    <div id="tab-roles">
      <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[parent:group]"/>
    </div>
    <div id="tab-members">
      <g:render template="/templates/admin/members" contextPath="${pluginContextPath}" model="[parent:group, protect:group.protect, groupmembers:false]"/>
    </div>
  </div>

  <g:form action="delete" name="deletegroup">
  	<g:hiddenField name="id" value="${group.id.encodeAsHTML()}"/>
  </g:form>

</body>
</html>
