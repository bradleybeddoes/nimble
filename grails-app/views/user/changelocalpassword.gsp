<head>
  <meta name="layout" content="admin"/>
  <title>Change Password</title>
</head>

<body>

<div class="container">
  <h2>Change local password for ${user.username}</h2>

  <p>
    You can set a local password for this user below, passwords must conform to the password policy. Local passwords
    can be used for non browser based applications to authenticate as the user to this system.
  </p>
  <p>
    A change here has <strong>no effect</strong> on the users externally maintained account or password.
  </p>

  <n:errors bean="${user}"/>

  <g:form action="savepassword" class="passwordchange">
    <g:hiddenField name="id" value="${user.id.encodeAsHTML()}"/>
    <table>
      <tbody>
      <tr>
        <th>Password</th>
        <td>
          <input type="password" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" class="password easyinput"/>
          <span class="icon icon_bullet_green">&nbsp;</span>
          <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
        </td>
      </tr>

      <tr>
        <th>Password confirmation</th>
        <td>
          <input type="password" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/>
          <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <td/>
        <td>
          <div>
            <button type="submit" class="button icon icon_key_go">Change Password</button>
            <g:link action="show" id="${user.id}" class="button icon icon_cross">Cancel</g:link>
          </div>
        </td>
      </tr>

      </tbody>
    </table>

  </g:form>

</div>
<n:passwordpolicy/>

</body>