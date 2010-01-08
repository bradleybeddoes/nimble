<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title><g:message code="nimble.view.account.forgottenpassword.initiate.title" /></title>
</head>

<body>
	
  <div class="accountinformation">
    <h2><g:message code="nimble.view.account.forgottenpassword.initiate.heading" /></h2>

	<p>
    	<g:message code="nimble.view.account.forgottenpassword.initiate.descriptive" />
	</p>
	
    <n:flashembed/>

    <g:form action="forgottenpasswordprocess">

      <table>
        <tbody>
        <tr>
          <td valign="top" class="name"><label for="email"><g:message code="nimble.label.email" /></label></td>
          <td valign="top" class="value">
            <input type="text" size="30" id="email" name="email" class="easyinput"/>
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

        </tbody>
      </table>

      <div>
        <button class="button icon icon_user_go" type="submit"><g:message code="nimble.link.resetpassword" /></button>
      </div>

    </g:form>
  </div>

</body>
</html>