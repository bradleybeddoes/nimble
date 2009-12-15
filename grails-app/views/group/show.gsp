<html>
<head>
	<meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
	<title>Group</title>
	<script type="text/javascript">
		<njs:permission parent="${group}"/>
		<njs:role parent="${group}"/>
		<njs:member parent="${group}"/>
		$(function() {
			$("#tabs").tabs();
		});
	</script>
</head>
<body>

  <h2>Group ${group.name.encodeAsHTML()}</h2>

  <div class="details">
    <h3>Group Details</h3>
    <table class="datatable">
      <tbody>
      <tr>
        <th>Name</th>
        <td valign="top" class="value">${fieldValue(bean: group, field: 'name')}</td>
      </tr>

      <tr>
        <th>Description</th>
        <td valign="top" class="value">${fieldValue(bean: group, field: 'description')}</td>

      </tr>

      <tr>
        <th>Protected</th>
        <td valign="top" class="value">
          <g:if test="${group.protect}">
            <span class="icon icon_tick">&nbsp;Yes</span>
          </g:if>
          <g:else>
            <span class="icon icon_cross">&nbsp;No</span>
          </g:else>
        </td>
      </tr>

      </tbody>
    </table>

  </div>

  <div id="tabs">

      <ul>
        <li><a href="#tab-permissions" class="icon icon_lock">Permissions</a></li>
        <li><a href="#tab-roles" class="icon icon_cog">Roles</a></li>
        <li><a href="#tab-members" class="icon icon_group">Members</a></li>
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
