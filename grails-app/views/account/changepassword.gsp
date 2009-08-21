
<html>

  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title>Change Password</title>

  <n:growl/>
  <n:flashgrowl/>
</head>

<body>

  <div class="container">

    <div class="accountinformation">
      <h2>Change Password</h2>

      <p>
        You can change your current password below. Passwords should be changed reguarly to ensure security.
      </p>

      <n:errors bean="${user}"/>

      <g:form action="updatepassword">
        <table>
          <tbody>

            <tr>
              <td><label for="currentPassword">Current Password</label></td>
              <td>
                <input type="password" size="30" id="currentPassword" name="currentPassword" class="easyinput"/>
              </td>
            </tr>

            <tr>
              <td><label for="pass">New Password</label></td>
              <td>
                <input type="password" size="30" id="pass" name="pass" class="password easyinput"/><a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
              </td>
            </tr>

            <tr>
              <td><label for="passConfirm">New Password Confirmation</label></td>
              <td>
                <input type="password" size="30" id="passConfirm" name="passConfirm" class="easyinput"/>
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


          <tr>
            <td/>
            <td>
              <button class="button icon icon_lock_go" type="submit">Change Password</button>
            </td>
            <td/>
          </tr>

          </tbody>
        </table>
      </g:form>
    </div>
  </div>

<n:passwordpolicy/>

</body>

</html>