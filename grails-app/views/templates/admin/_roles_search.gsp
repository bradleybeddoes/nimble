<g:if test="${roles?.size() > 0}">
  <table class="details">
    <thead>
    <tr>
      <th class="first"><g:message code="nimble.label.name" /></th>
      <th class=""><g:message code="nimble.label.description" /></th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${roles}" status="i" var="role">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${role.name?.encodeAsHTML()}</td>
        <td>${role.description?.encodeAsHTML()}</td>
        <td>
          <g:link controller="role" action="show" id="${role.id.encodeAsHTML()}" class="button icon icon_cog_go"><g:message code="nimble.link.view" /></g:link>
          <a onClick="nimble.grantRole('${ownerID.encodeAsHTML()}', '${role.id.encodeAsHTML()}');" class="button icon icon_add"><g:message code="nimble.link.assign" /></a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    <strong><g:message code="nimble.template.roles.add.noresults" /></strong>
  </p>
</g:else>