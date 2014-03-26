<g:if test="${groups?.size() > 0}">
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
        <td>${group.name?.encodeAsHTML()}</td>
        <td>${group.description?.encodeAsHTML()}</td>
        <td>
          <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go"><g:message code="nimble.link.view" /></g:link>
          <a onClick="nimble.grantGroup('${ownerID.encodeAsHTML()}', '${group.id.encodeAsHTML()}');" class="button icon icon_add"><g:message code="nimble.link.assign" /></a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    <strong><g:message code="nimble.template.groups.add.noresults" /></strong>
  </p>
</g:else>