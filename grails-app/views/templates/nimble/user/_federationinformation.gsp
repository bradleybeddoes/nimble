<h3><g:message code="nimble.view.user.show.federated.heading" /></h3>
<table>
	<tbody>
		<tr>
			<th><g:message code="nimble.label.provider" /></th>
			<td valign="top">
			<a href="${user.federationProvider.details?.url?.location}" alt="${user.federationProvider.details?.url?.altText}"><g:message code="${user?.federationProvider?.details?.displayName}"/></a>
		</td>
		</tr>
		<tr>
			<th><g:message code="nimble.label.description" /></th>
			<td><g:message code="${user?.federationProvider?.details?.description}"/></td>
		</tr>
  </tbody>
</table>