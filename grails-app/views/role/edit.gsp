<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="admin"/>
  <title>Edit Group</title>
</head>
<body>

<h2>Edit Role - ${role.name.encodeAsHTML()}</h2>

<p>
  You can modify values associated with this role below. Fields with a green bullet are required.
</p>

<n:errors bean="${role}"/>

<g:form method="post" name="editRole" action="update">
  <input type="hidden" name="id" value="${role?.id.encodeAsHTML()}"/>

  <table>
    <tbody>

    <tr>
      <td valign="top" class="name">
        <label for="name">Name</label>
      </td>
      <td valign="top" class="value ">
        <input type="text" id="name" name="name" value="${fieldValue(bean: role, field: 'name')}"/> <span class="icon icon_bullet_green">&nbsp;</span>
      </td>
    </tr>

    <tr>
      <td valign="top" class="name">
        <label for="description">Description</label>
      </td>
      <td valign="top" class="value ">
        <input type="text" id="description" name="description" value="${fieldValue(bean: role, field: 'description')}"/>  <span class="icon icon_bullet_green">&nbsp;</span>
      </td>
    </tr>

    </tbody>
  </table>

  <div>
    <a onClick="document.editRole.submit()" class="button icon icon_cog_go" alt="Submit role update">Update Role</a>
    <a id="deleteconfirmbtn" rel="deleteconfirm" class="button icon icon_cog_delete">Delete Role</a>
    <g:link action="show" id="${role.id}" class="button icon icon_cross">Cancel</g:link>
  </div>

</g:form>

<g:render template="/templates/admin/deleteroleconfirm"/>

</body>
</html>
