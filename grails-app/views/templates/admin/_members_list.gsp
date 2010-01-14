<h4><span class="empty icon icon_user"/><g:message code="nimble.template.members.list.users.heading" /></h4>
<g:if test="${users?.size() > 0}">
  <table class="details">
    <thead>
    <tr>
      <th class="first"><g:message code="nimble.label.username" /></th>
      <th class=""><g:message code="nimble.label.fullname" /></th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${users}" status="i" var="user">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <g:if test="${user.username.length() > 30}">
        	<td>${user.username?.substring(0,30).encodeAsHTML()}...</td>
		</g:if>
		<g:else>
			<td>${user.username?.encodeAsHTML()}</td>
		</g:else>
        <g:if test="${user.profile?.fullName}">
          <td>${user.profile?.fullName?.encodeAsHTML()}</td>
        </g:if>
        <g:else>
          <td>&nbsp;</td>
        </g:else>
        <td>
          <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="button icon icon_user_go"><g:message code="nimble.link.view" /></g:link>
          <g:if test="${!protect}">
            <a onClick="removeMember('${parent.id.encodeAsHTML()}', '${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="button icon icon_delete"><g:message code="nimble.link.remove" /></a>
          </g:if>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    <g:message code="nimble.template.members.list.users.noresults" />
  </p>
</g:else>

<h4><span class="empty icon icon_group"/><g:message code="nimble.template.members.list.groups.heading" /></h4>
<g:if test="${groupmembers}">
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
          <td>${group.name.encodeAsHTML()}</td>
          <td>${group.description.encodeAsHTML()}</td>
          <td>
            <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go"><g:message code="nimble.link.view" /></g:link>
            <g:if test="${!protect}">
              <a onClick="removeGroupMember('${parent.id.encodeAsHTML()}', '${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="button icon icon_delete"><g:message code="nimble.link.remove" /></a>
            </g:if>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>
  </g:if>
  <g:else>
    <p>
      <g:message code="nimble.template.members.list.groups.noresults" />
    </p>
  </g:else>
</g:if>