<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="admin"/>
  <title>Create Group</title>

  <g:render template="/templates/validate_name" contextPath="${pluginContextPath}"/>
</head>
<body>

<div class="container">
  <h2>Create Group</h2>

  <p>
    Create a new group below, fields marked with a bullet are required.
  </p>

  <n:errors bean="${group}"/>

  <g:form name="createGroup" action="save" method="post">
    <div class="dialog">
      <table>
        <tbody>

        <tr>
          <th>
            Name
          </th>
          <td valign="top" class="value">
            <input type="text" id="name" name="name" value="${fieldValue(bean: group, field: 'name')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
            <span id="nameavailable" class="icon">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <th>
            Description
          </th>
          <td valign="top" class="value">
            <input type="text" id="description" name="description" value="${fieldValue(bean: group, field: 'description')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <td/>
          <td>
            <div class="buttons">
              <button class="button icon icon_group_go" type="submit">Create Group</button>
              <g:link action="list" class="button icon icon_cancel">Cancel</g:link>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

    </div>
  </g:form>

</div>

</body>
</html>
