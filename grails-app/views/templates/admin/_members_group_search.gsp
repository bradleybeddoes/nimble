<g:if test="${groups != null && groups.size() > 0}">
  <table class="details">
    <thead>
    <tr>
      <th class="first"><g:message code="nimble.label.name" /></th>
      <th class=""><g:message code="nimble.label.description" /></th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${groups}" status="i" var="group">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${group.name.encodeAsHTML()}</td>
        <td>${group.description.encodeAsHTML()}</td>
        <td>
          <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go"><g:message code="nimble.link.view" /></g:link>
          <a onClick="nimble.addGroupMember('${parent.id.encodeAsHTML()}', '${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="button icon icon_add"><g:message code="nimble.link.grant" /></a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <div class="info">
  	<strong><g:message code="nimble.template.members.add.group.noresults" /></strong>
  </div>
</g:else>