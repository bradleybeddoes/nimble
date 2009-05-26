<head>
  <meta name="layout" content="admin"/>
  <title>Role</title>
</head>
<body>

<div class="container">
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

  <div class="sections">

    <ul id="sections_" class="horizmenu">
      <li class="current menuoption"><a href="permissions_" class="icon icon_lock">Permissions</a></li>
      <li class="menuoption"><a href="members_" class="icon icon_cog">Members</a></li>
    </ul>

    <div class="active_ sections_ permissions_">
      <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[ownerID:role.id.encodeAsHTML()]"/>
    </div>

    <div class="sections_ members_">
      <g:render template="/templates/admin/members" contextPath="${pluginContextPath}" model="[parentID:role.id.encodeAsHTML(), protect:role.protect, groupmembers:true]"/>
    </div>

  </div>

</div>
<g:render template="/templates/admin/deleteroleconfirm"/>

</body>
