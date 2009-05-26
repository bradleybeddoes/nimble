<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="admin"/>
  <title>Create Group</title>

  <g:render template="/templates/validate_name" contextPath="${pluginContextPath}"/>
</head>
<body>

<div class="container">
  <h2>Create Role</h2>

  <p>
    Create a new role below, fields marked with green bullets are required.
  </p>

  <n:errors bean="${role}"/>

  <g:form action="save" method="post">

    <table>
      <tbody>

      <tr>
        <th>Name</th>
        <td class="value">
          <input type="text" id="name" name="name" value="${fieldValue(bean: role, field: 'name')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
          <span id="nameavailable" class="icon">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <th>Description</th>
        <td class="value">
          <input type="text" id="description" name="description" value="${fieldValue(bean: role, field: 'description')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td/>
        <td>
          <div class="buttons">
            <button class="button icon icon_group_add" type="submit">Create Role</button>
            <g:link action="list" class="button icon icon_cancel">Cancel</g:link>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

  </g:form>

</div>
</body>
</html>
