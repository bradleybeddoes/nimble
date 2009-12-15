<html>
<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Create Group</title>
</head>
<body>

  <h2>Create Group</h2>

  <p>
    Create a new group below, fields marked with a bullet are required.
  </p>

  <n:errors bean="${group}"/>

  <g:form name="createGroup" action="save" method="post">
      <table>
        <tbody>

        <tr>
          <th>
            Name
          </th>
          <td valign="top" class="value">
            <n:verifyfield id="name" class="easyinput" name="name" value="${fieldValue(bean: group, field: 'name')}" required="true" controller="group" action="validname" validmsg="valid" invalidmsg="invalid" />
          </td>
        </tr>

        <tr>
          <th>
            Description
          </th>
          <td valign="top" class="value">
            <input type="text" size="30" id="description" name="description" value="${fieldValue(bean: group, field: 'description')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
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
  </g:form>

</body>
</html>
