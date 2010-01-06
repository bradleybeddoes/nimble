<html>
<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.group.create.title" /></title>
</head>
<body>

  <h2><g:message code="nimble.view.group.create.heading" /></h2>

  <p>
    <g:message code="nimble.view.group.create.descriptive" />
  </p>

  <n:errors bean="${group}"/>

  <g:form name="createGroup" action="save" method="post">
      <table>
        <tbody>

        <tr>
          <th>
            <g:message code="nimble.label.name" />
          </th>
          <td valign="top" class="value">
            <n:verifyfield id="name" class="easyinput" name="name" value="${fieldValue(bean: group, field: 'name')}" required="true" controller="group" action="validname" validmsg="valid" invalidmsg="invalid" />
          </td>
        </tr>

        <tr>
          <th>
            <g:message code="nimble.label.description" />
          </th>
          <td valign="top" class="value">
            <input type="text" size="30" id="description" name="description" value="${fieldValue(bean: group, field: 'description')}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <td/>
          <td>
            <div class="buttons">
              <button class="button icon icon_group_go" type="submit"><g:message code="nimble.button.creategroup" /></button>
              <g:link action="list" class="button icon icon_cancel"><g:message code="nimble.link.cancel" /></g:link>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
  </g:form>

</body>
</html>
