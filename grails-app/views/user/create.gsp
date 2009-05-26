<head>
  <meta name="layout" content="admin"/>
  <title>Create User</title>

  <g:render template="/templates/validate_username" contextPath="${pluginContextPath}"/>
</head>

<body>

<div class="container">
  <h2>Create User Account</h2>

  <p>
    Create a new user account below, fields marked with green bullets are required.
  </p>

  <n:errors bean="${user}"/>

  <g:form action="save">
    <table>
      <tbody>

      <tr>
        <td class="name"><label for="username">Username</label></td>
        <td class="value">
          <input type="text" size="30" id="username" name="username" value="${user.username?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
          <span id="usernameavailable" class="icon">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="pass">Password</label></td>
        <td class="value">
          <input type="password" size="30" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" class="password easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>  <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help">&nbsp;</a>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="passConfirm">Password Confirmation</label></td>
        <td class="value">
          <input type="password" size="30" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="fullName">Full Name</label></td>
        <td class="value">
          <input type="text" size="30" id="fullName" name="fullName" value="${user.profile?.fullName?.encodeAsHTML()}" class="easyinput"/>
        </td>
      </tr>

      <tr>
        <td class="name"><label for="email">Email</label></td>
        <td class="value">
          <input type="text" size="30" id="email" name="email" value="${user.profile?.email?.encodeAsHTML()}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      </tbody>
    </table>

    <div>
      <button class="button icon icon_user_add" type="submit">Create User</button>
      <g:link action="list" class="button icon icon_cancel">Cancel</g:link>
    </div>

  </g:form>

</div>

<n:passwordpolicy/>
</body>
