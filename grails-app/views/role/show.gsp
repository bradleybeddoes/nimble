<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Role</title>
  <script type="text/javascript">
	<njs:permission parent="${role}"/>
	<njs:member parent="${role}"/>
	$(function() {
		$("#tabs").tabs();
	});
  </script>
</head>
<body>

  <h2>Role ${role.name.encodeAsHTML()}</h2>


  <g:if test="${!role.protect}">
    <div class="actions">
      <ul class="horizmenu">
        <li>
          <g:link action="edit" id="${role.id.encodeAsHTML()}" class="icon icon_cog_edit">Edit Role</g:link>
        </li>
        <li>
          <a href="#" id="deleteconfirmbtn" rel="deleteconfirm" class="icon icon_delete">Delete Role</a>
        </li>
      </ul>
    </div>
  </g:if>

  <div class="details">
    <h3>Role Details</h3>
    <table>
      <tbody>
      <tr>
        <td valign="top" class="name">Name</td>
        <td valign="top" class="value">${fieldValue(bean: role, field: 'name')}</td>
      </tr>

      <tr>
        <td valign="top" class="name">Description</td>
        <td valign="top" class="value">${fieldValue(bean: role, field: 'description')}</td>
      </tr>

      <tr>
        <td valign="top" class="name">Protected</td>
        <td valign="top" class="value">
          <g:if test="${role.protect}">
            <span class="icon icon_tick">&nbsp;Yes</span>
          </g:if>
          <g:else>
            <span class="icon icon_cross">&nbsp;No</span>
          </g:else>
        </td>
      </tr>

      <tr>
        <td></td>
        <td>

        </td>
      </tr>

      </tbody>
    </table>

  </div>

  <div id="tabs">

    <ul>
      <li><a href="#tab-permissions" class="icon icon_lock">Permissions</a></li>
      <li><a href="#tab-members" class="icon icon_cog">Members</a></li>
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

</body>
