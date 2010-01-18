<html>
<body>

	<p>
		<g:message code="nimble.template.mail.forgottenpassword.descriptive" />
	</p>
	<p>
		<g:message code="nimble.template.mail.forgottenpassword.instructions" />
	</p>
	<p>
		${createLink(absolute:true, controller: 'auth', action: 'login')}
	</p>

	<table>
	  	<tbody>
			  <tr>
			    <th><g:message code="nimble.label.username" /></th>
			    <td>${user.username}</td>
			  </tr>
			  <tr>
			    <th><g:message code="nimble.label.password" /></th>
			    <td>${user.pass}</td>
			  </tr>
		  </tbody>
	</table>

</body>
</html>