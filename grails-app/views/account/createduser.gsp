
<html>

<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
  <title><g:message code="nimble.view.account.registeraccount.complete.title" /></title>
</head>

<body>

    <h2><g:message code="nimble.view.account.registeraccount.complete.heading" /></h2>
    <p>
      <g:message code="nimble.view.account.registeraccount.complete.descriptive" />
    </p>
	<g:if test="${messaging}">
	<p>
		<g:message code="nimble.view.account.registeraccount.complete.sentemail" />
    </p>
	</g:if>
	<g:else>
	<p>
		<a href="${createLink(controller:'profile')}"><g:message code="nimble.view.account.registeraccount.complete.continue" default="Continue..."/></a>
	</p>
	</g:else>
</body>

</html>
