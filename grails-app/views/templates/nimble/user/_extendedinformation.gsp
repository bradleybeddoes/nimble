<h3><g:message code="nimble.view.user.show.extendedinformation.heading" /></h3>

<table class="datatable">
 	<tbody>
		<tr>
	    	<th><g:message code="nimble.label.fullname" /></th>
			<td>${user.profile?.fullName?.encodeAsHTML()}</td>
		</tr>
		<tr>
	    	<th><g:message code="nimble.label.email" /></th>
			<td>${user.profile?.email?.encodeAsHTML()}</td>
		</tr>
	</tbody>
</table>