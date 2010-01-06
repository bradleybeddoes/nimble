
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title><g:message code="nimble.view.account.registeraccount.initiate.title" /></title>
</head>

<body>

  <div class="accountinformation">
    <h2><g:message code="nimble.view.account.registeraccount.initiate.heading" /></h2>

    <p>
      <g:message code="nimble.view.account.registeraccount.initiate.descriptive" />
    </p>

    <n:errors bean="${user}"/>

    <g:form action="saveuser">
      <table>
        <tbody>

        <tr>
          <td valign="top" class="name"><label for="username"><g:message code="nimble.label.username" /></label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'username', 'errors')}">
            <input type="text" size="30" id="username" name="username" value="${user.username?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span><a href="#" id="usernamepolicybtn" rel="usernamepolicy" class="empty icon icon_help"></a>
            <span id="usernameavailable" class="icon">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="pass"><label for="username"><g:message code="nimble.label.password" /></label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'pass', 'errors')}">
            <input type="password" size="30" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" class="password easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span><a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="passConfirm"><g:message code="nimble.label.password.confirmation" /></label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'passConfirm', 'errors')}">
            <input type="password" size="30" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="fullName"><g:message code="nimble.label.fullname" /></label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'profile.fullName', 'errors')}">
            <input type="text" size="30" id="fullName" name="fullName" value="${user.profile?.fullName?.encodeAsHTML()}" class="easyinput"/>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="email"><g:message code="nimble.label.email" /></label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'profile.email', 'errors')}">
            <input type="text" size="30" id="email" name="email" value="${user.profile?.email?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
          </td>
        </tr>

        <n:recaptcharequired>
          <tr>
            <th><g:message code="nimble.label.captcha" /></th>
            <td>
              <n:recaptcha/>
            </td>
          </tr>
        </n:recaptcharequired>

        <tr>
          <td/>
          <td>
            <button class="button icon icon_user" type="submit"><g:message code="nimble.button.registeraccount" /></button>
          </td>  
        </tr>

        </tbody>
      </table>
    </g:form>
  </div>

<n:usernamepolicy/>
<n:passwordpolicy/>

</body>

</html>