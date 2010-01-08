
<html>

  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title><g:message code="nimble.view.account.changepassword.initiate.title" /></title>
</head>

<body>

    <div class="accountinformation">
      <h2><g:message code="nimble.view.account.changepassword.initiate.heading" /></h2>

      <p>
        <g:message code="nimble.view.account.changepassword.initiate.descriptive" />
      </p>

      <n:errors bean="${user}"/>

      <g:form action="updatepassword">
        <table>
          <tbody>

            <tr>
              <td><label for="currentPassword"><g:message code="nimble.label.currentpassword" /></label></td>
              <td>
                <input type="password" size="30" id="currentPassword" name="currentPassword" class="easyinput"/>
              </td>
            </tr>

            <tr>
              <td><label for="pass"><g:message code="nimble.label.newpassword" /></label></td>
              <td>
                <input type="password" size="30" id="pass" name="pass" class="password easyinput"/><a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
              </td>
            </tr>

            <tr>
              <td><label for="passConfirm"><g:message code="nimble.label.newpassword.confirmation" /></label></td>
              <td>
                <input type="password" size="30" id="passConfirm" name="passConfirm" class="easyinput"/>
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
              <button class="button icon icon_lock_go" type="submit"><g:message code="nimble.link.changepassword" /></button>
            </td>
            <td/>
          </tr>

          </tbody>
        </table>
      </g:form>
    </div>

<n:passwordpolicy/>

</body>

</html>