<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title><g:message code="nimble.view.account.forgottenpassword.complete.title" /></title>
</head>

<body>

  <div class="accountinformation">
    <h2><g:message code="nimble.view.account.forgottenpassword.complete.heading" /></h2>

    <p>
      <g:message code="nimble.view.account.forgottenpassword.complete.descriptive" />
    </p>
    <p>
      <g:link controller="auth" action="login" class="button icon icon_user_go"><g:message code="nimble.link.login.basic" /></g:link>
    </p>
  </div>

</body>
</html>