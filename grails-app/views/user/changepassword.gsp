<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Change Password</title>
  <script type="text/javascript">
  	<njs:user user="${user}"/>
  </script>
</head>

<body>

<h2>Change password for ${user.username}</h2>

<p>
  Set a new password below. Passwords must conform to the password policy.
</p>

<n:errors bean="${user}"/>

<g:form action="savepassword" class="passwordchange">
  <g:hiddenField name="id" value="${user.id.encodeAsHTML()}"/>
  <table>
    <tbody>
    <tr>
      <th>Password</th>
      <td>
        <input type="password" id="pass" name="pass" class="password easyinput" value="${user.pass?.encodeAsHTML()}"/>
        <span class="icon icon_bullet_green">&nbsp;</span>
        <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"></a>
      </td>
    </tr>

    <tr>
      <th>Password confirmation</th>
      <td>
        <input type="password" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="easyinput"/><span class="icon icon_bullet_green">&nbsp;</span>
      </td>
    </tr>

    <tr>
      <td/>
      <td>
        <div class="buttons">
          <button type="submit" class="button icon icon_key_go">Change Password</button>
          <g:link action="show" id="${user.id}" class="button icon icon_cross">Cancel</g:link>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</g:form>

<n:passwordpolicy/>