<g:if test="${users?.size() > 0}">
  <h4><span class="empty icon icon_user"/>Users</h4>
  <table class="details">
    <thead>
    <tr>
      <th class="first">Username</th>
      <th class="">Full Name</th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${users}" status="i" var="user">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><span class="userhighlight user_${user.id}">${user.username}</span></td>
        <g:if test="${user.profile?.fullName}">
          <td>${user.profile?.fullName?.encodeAsHTML()}</td>
        </g:if>
        <g:else>
          <td>&nbsp;</td>
        </g:else>
        <td>
          <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="button icon icon_user_go">View User</g:link>
          <g:if test="${!protect}">
            <a onClick="removeMember('${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="button icon icon_delete">Remove User</a>
          </g:if>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    Currently there are no users as members.
  </p>
</g:else>

<g:if test="${groupmembers}">
  <g:if test="${groups?.size() > 0}">
    <h4><span class="empty icon icon_group"/>Groups</h4>
    <table class="details">
      <thead>
      <tr>
        <th class="first">Name</th>
        <th class="">Description</th>
        <th class="last"></th>
      </tr>
      </thead>
      <tbody>
      <g:each in="${groups}" status="i" var="group">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td>${group.name.encodeAsHTML()}</td>
          <td>${group.description.encodeAsHTML()}</td>
          <td>
            <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go">View Group</g:link>
            <g:if test="${!protect}">
              <a onClick="removeGroupMember('${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="button icon icon_delete">Remove Group</a>
            </g:if>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>
  </g:if>
  <g:else>
    <p>
      Currently there are no groups as members.
    </p>
  </g:else>
</g:if>