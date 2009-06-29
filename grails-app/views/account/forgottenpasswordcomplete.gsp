<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title>Forgotten Password | Reset Complete</title>
</head>

<body>

<div class="container">
  <div class="accountinformation">
    <h2>Forgotten Password - Reset Complete</h2>

    <p>
      Your password was successfully reset. You'll soon recieve an email with this new password,
      be sure to change it straight away as you'll only be able to use the generated password a few times.
    </p>
    <p>
      <g:link controller="auth" action="login" class="button icon icon_user_go">Login using my new password</g:link>
    </p>

  </div>
</div>

</body>
</html>