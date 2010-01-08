<g:if test="${logins?.size() > 0}">

  <p>
	<g:message code="nimble.template.logins.list.heading" args="[logins.size()]"/>
  </p>

  <table class="details">
    <thead>
    <tr>
      <th class="first"><g:message code="nimble.label.remoteaddress" /></th>
      <th class=""><g:message code="nimble.label.remotehost" /></th>
      <th class=""><g:message code="nimble.label.useragent" /></th>
      <th class="last"><g:message code="nimble.label.time" /></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${logins}" status="i" var="login">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${login.remoteAddr?.encodeAsHTML()}</td>
        <td>${login.remoteHost?.encodeAsHTML()}</td>
        <td>${login.userAgent?.encodeAsHTML()}</td>
        <td>${login.dateCreated?.encodeAsHTML()}</td>
      </tr>
    </g:each>
    </tbody>
  </table>

</g:if>
<g:else>

  <p>
    <g:message code="nimble.template.logins.list.heading" />
  </p>
  
</g:else>