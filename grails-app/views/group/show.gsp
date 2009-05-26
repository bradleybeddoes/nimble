<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="admin"/>
  <title>Group</title>
</head>
<body>

<div class="container">
  <h2>Group ${group.name.encodeAsHTML()}</h2>

  <g:if test="${!group.protect}">
    <div class="actions">
      <ul class="horizmenu">
        <li>
          <g:link action="edit" id="${group.id.encodeAsHTML()}" class="icon icon_group_edit">Edit Group</g:link>
        </li>
        <li>
          <a href="#" id="deleteconfirmbtn" rel="deleteconfirm" class="icon icon_group_delete">Delete Group</a>
        </li>
      </ul>
    </div>
  </g:if>

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

  <div class="sections">

    <div>
      <ul id="sections_" class="horizmenu">
        <li class="current"><a href="permissions_" class="icon icon_lock">Permissions</a></li>
        <li class=""><a href="roles_" class="icon icon_cog">Roles</a></li>
        <li class=""><a href="members_" class="icon icon_group">Members</a></li>
      </ul>
    </div>

    <div class="active_ sections_ permissions_">
      <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[ownerID:group.id.encodeAsHTML()]"/>
    </div>
    <div class="sections_ roles_">
      <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[ownerID:group.id.encodeAsHTML()]"/>
    </div>
    <div class="sections_ members_">
      <g:render template="/templates/admin/members" contextPath="${pluginContextPath}" model="[parentID:group.id.encodeAsHTML(), protect:group.protect, groupmembers:false]"/>
    </div>
  </div>

</div>

<g:render template="/templates/admin/deletegroupconfirm"/>

</body>
</html>
