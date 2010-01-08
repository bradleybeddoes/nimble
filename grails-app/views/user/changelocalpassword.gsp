<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.user.changelocalpassword.title" /></title>
  <script type="text/javascript">
  	<njs:user user="${user}"/>
  </script>
</head>

<body>

  <h2><g:message code="nimble.view.user.changelocalpassword.heading" args="[user.username]" /></h2>

  <p>
    <g:message code="nimble.view.user.changelocalpassword.descriptive" />
  </p>

  <n:errors bean="${user}"/>

  <g:form action="savepassword" class="passwordchange">
    <g:hiddenField name="id" value="${user.id.encodeAsHTML()}"/>
    <table>
      <tbody>
      <tr>
        <th><g:message code="nimble.label.password" /></th>
        <td>
          <input type="password" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" class="password easyinput"/>
          <span class="icon icon_bullet_green">&nbsp;</span>
          <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
        </td>
      </tr>

      <tr>
        <th><g:message code="nimble.label.password.confirmation" /></th>
        <td>
          <input type="password" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/>
          <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td/>
        <td>
          <div>
            <button type="submit" class="button icon icon_key_go"><g:message code="nimble.link.changepassword" /></button>
            <g:link action="show" id="${user.id}" class="button icon icon_cross"><g:message code="nimble.link.cancel" /></g:link>
          </div>
        </td>
      </tr>

      </tbody>
    </table>

  </g:form>

<n:passwordpolicy/>

</body>