
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title>Account Registration</title>

  <n:growl/>
  <n:flashgrowl/>

  <g:render template="/templates/validate_username" contextPath="${pluginContextPath}"/>
</head>

<body>

<div class="container">

  <div class="accountinformation">
    <h2>Account Registration</h2>

    <p>
      You can register for a new account below, fields with a green bullet are required.
    </p>

    <n:errors bean="${user}"/>

    <g:form action="saveuser">
      <table>
        <tbody>

        <tr>
          <td valign="top" class="name"><label for="username">Username</label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'username', 'errors')}">
            <input type="text" size="30" id="username" name="username" value="${user.username?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span><a href="#" id="usernamepolicybtn" rel="usernamepolicy" class="empty icon icon_help"></a>
            <span id="usernameavailable" class="icon">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="pass">Password</label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'pass', 'errors')}">
            <input type="password" size="30" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" class="password easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span><a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="passConfirm">Password Confirmation</label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'passConfirm', 'errors')}">
            <input type="password" size="30" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="fullName">Full Name</label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'profile.fullName', 'errors')}">
            <input type="text" size="30" id="fullName" name="fullName" value="${user.profile?.fullName?.encodeAsHTML()}" class="easyinput"/>
          </td>
        </tr>

        <tr>
          <td valign="top" class="name"><label for="email">Email</label></td>
          <td valign="top" class="value ${hasErrors(bean: user, field: 'profile.email', 'errors')}">
            <input type="text" size="30" id="email" name="email" value="${user.profile?.email?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
          </td>
        </tr>

        <tr>
          <th>Captcha</th>
          <td>
            <n:recaptcha/>
          </td>
        </tr>

        <tr>
          <td/>
          <td>
            <button class="button icon icon_user" type="submit">Create My Account!</button>
          </td>  
        </tr>

        </tbody>
      </table>
    </g:form>
  </div>
</div>

<n:usernamepolicy/>
<n:passwordpolicy/>

</body>

</html>