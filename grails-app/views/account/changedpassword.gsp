
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title><g:message code="nimble.account.changepassword.complete.label" /></title>
</head>

<body>

  <div class="accountinformation">
    <h2><g:message code="nimble.account.changepassword.complete.label" /></h2>

    <p>
	  <g:message code="nimble.account.changepassword.complete.confirmation" args="${}"/>
      Your password has been changed. We recommend you now <g:link controller="auth" action="logout">Logout</g:link> to update your credentials.
    </p>
    
  </div>

</body>

</html>