<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.user.create.title" /></title>
</head>

<body>

  <h2><g:message code="nimble.view.user.create.heading" /></h2>

  <p>
    <g:message code="nimble.view.user.create.descriptive" />
  </p>

  <n:errors bean="${user}"/>

  <g:form action="save">
    <table>
      <tbody>

      <tr>
        <td class="name"><label for="username"><g:message code="nimble.label.username" /></label></td>
        <td class="value">
		  <n:verifyfield id="username" class="easyinput" name="username" value="${fieldValue(bean: user, field: 'username')}" required="true" controller="user" action="validusername" validmsg="valid" invalidmsg="invalid" />
        </td>
      </tr>

      <tr>
        <td class="name"><label for="pass"><g:message code="nimble.label.password" /></label></td>
        <td class="value">
          <input type="password" size="30" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" class="password easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>  <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help">&nbsp;</a>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="passConfirm"><g:message code="nimble.label.password.confirmation" /></label></td>
        <td class="value">
          <input type="password" size="30" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="fullName"><g:message code="nimble.label.fullname" /></label></td>
        <td class="value">
          <input type="text" size="30" id="fullName" name="fullName" value="${user.profile?.fullName?.encodeAsHTML()}" class="easyinput"/>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="email"><g:message code="nimble.label.email" /></label></td>
        <td class="value">
          <input type="text" size="30" id="email" name="email" value="${user.profile?.email?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      </tbody>
    </table>

    <div>
      <button class="button icon icon_user_add" type="submit"><g:message code="nimble.link.createuser" /></button>
      <g:link action="list" class="button icon icon_cancel"><g:message code="nimble.link.cancel" /></g:link>
    </div>

  </g:form>

<n:passwordpolicy/>
</body>
