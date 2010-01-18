<html>

<head>

</head>

<body>
	
	<p>
		<g:message code="nimble.template.mail.emailchange.descriptive" />
	</p>

	<p>
  		<a href="${createLink(absolute:true, controller: 'profile', action: 'validateemail', id: user.id, params: [activation: user.actionHash])}"><g:message code="nimble.link.verifyemail" /></a>
	</p>

	<p>
		<g:message code="nimble.template.mail.accountregistration.trouble" />
	</p>
	<p>
		${createLink(absolute:true, controller: 'profile', action: 'validateemail', id: user.id, params: [activation: user.actionHash])}
	</p>

</body>

</html>
