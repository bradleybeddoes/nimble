<html>

<head>

</head>

<body>
	
	<p>
		<g:message code="nimble.template.mail.accountregistration.descriptive" />
	</p>

	<p>
  		<a href="${createLink(absolute:true, controller: 'account', action: 'validateuser', id: user.id, params: [activation: user.actionHash])}"><g:message code="nimble.link.activateaccount" /></a>
	</p>

	<p>
  		<g:message code="nimble.template.mail.accountregistration.trouble" />
	</p>
	<p>
		${createLink(absolute:true, controller: 'account', action: 'validateuser', id: user.id, params: [activation: user.actionHash])}
	</p>

</body>

</html>