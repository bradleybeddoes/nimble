<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title>Forgotten Password</title>
</head>

<body>
	
  <div class="accountinformation">
    <h2>Forgotten Password</h2>

    <p>Enter your email address and captcha answers below and we'll reset your password to something secure.</p>

    <p>You'll then recieve an email with this new password, be sure to change it straight away to something only you'll know.</p>

    <n:flashembed/>

    <g:form action="forgottenpasswordprocess">

      <table>
        <tbody>
        <tr>
          <td valign="top" class="name"><label for="email">Email</label></td>
          <td valign="top" class="value">
            <input type="text" size="30" id="email" name="email" class="easyinput"/>
          </td>
        </tr>

        <n:recaptcharequired>
          <tr>
            <th>Captcha</th>
            <td>
              <n:recaptcha/>
            </td>
          </tr>
        </n:recaptcharequired>

        </tbody>
      </table>

      <div>
        <button class="button icon icon_user_go" type="submit">Reset Password</button>
      </div>

    </g:form>
  </div>

</body>
</html>