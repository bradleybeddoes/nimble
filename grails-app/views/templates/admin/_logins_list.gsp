<g:if test="${logins?.size() > 0}">

  <p>
    ${logins.size()} most recent logins
  </p>

  <table class="details">
    <thead>
    <tr>
      <th class="first">Remote Address</th>
      <th class="">Remote Host</th>
      <th class="">User Agent</th>
      <th class="last">Time</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${logins}" status="i" var="login">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${login.remoteAddr?.encodeAsHTML()}</td>
        <td>${login.remoteHost?.encodeAsHTML()}</td>
        <td>${login.userAgent?.encodeAsHTML()}</td>
        <td>${login.time?.encodeAsHTML()}</td>
      </tr>
    </g:each>
    </tbody>
  </table>

</g:if>
<g:else>

  <p>
    This user has not yet authenticated to this service.
  </p>
  
</g:else>