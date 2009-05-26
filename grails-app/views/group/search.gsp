<g:if test="${users != null && users.size() > 0}">
  <table>
    <thead>
    <tr>
      <th class="first">Username</th>
      <th class="last">Full Name</th>
      <th class="nobg"></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${users}" status="i" var="user">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${user.username.encodeAsHTML()}</td>
        <td>${user.fullName.encodeAsHTML()}</td>
        <td>
          <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="button icon icon_user_go">View User</g:link>
          <a onClick="addMember('${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="button icon icon_add">Grant Membership</a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <div class="info">
    <strong>No users matching your search request were found (or they are already a group member!)</strong>
  </div>
</g:else>