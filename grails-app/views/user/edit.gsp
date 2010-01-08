<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.user.edit.title" /></title>
  <script type="text/javascript">
  	<njs:user user="${user}"/>
  </script>
</head>

<body>
	
  <h2><g:message code="nimble.view.user.edit.heading" args="[user.username]" /></h2>

  <p>
    <g:message code="nimble.view.user.edit.descriptive" />
  </p>

  <n:errors bean="${user}"/>

  <g:form action="update" class="editaccount">
    <input type="hidden" name="id" value="${user.id}"/>
    <input type="hidden" name="version" value="${user.version}"/>

    <table>
      <tbody>

      <tr>
        <th><label for="username"><g:message code="nimble.label.username" /></label></th>
        <td class="value">
          <input type="text" id="username" name="username" value="${user.username?.encodeAsHTML()}" class="easyinput"/>  <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.externalaccount" /></th>
        <td>
          <g:if test="${user.external}">
            <input type="radio" name="external" value="true" checked="true"/><g:message code="nimble.label.true" />
            <input type="radio" name="external" value="false"/><g:message code="nimble.label.false" />
          </g:if>
          <g:else>
            <input type="radio" name="external" value="true"/><g:message code="nimble.label.true" />
            <input type="radio" name="external" value="false" checked="true"/><g:message code="nimble.label.false" />
          </g:else>
        </td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.federatedaccount" /></th>
        <td>
          <g:if test="${user.federated}">
            <input type="radio" name="federated" value="true" checked="true"/><g:message code="nimble.label.true" />
            <input type="radio" name="federated" value="false"/><g:message code="nimble.label.false" />
          </g:if>
          <g:else>
            <input type="radio" name="federated" value="true"/><g:message code="nimble.label.true" />
            <input type="radio" name="federated" value="false" checked="true"/><g:message code="nimble.label.false" />
          </g:else>
        </td>
      </tr>

      <tr>
        <td/>
        <td>
          <div class="buttons">
            <button class="button icon icon_user_go" type="submit"><g:message code="nimble.link.updateuser" /></button>
            <g:link action="show" id="${user.id}" class="button icon icon_cross"><g:message code="nimble.link.cancel" /></g:link>
          </div>
        </td>
      </tr>

      </tbody>
    </table>

  </g:form>

</body>
