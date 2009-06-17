<g:if test="${users != null && users.size() > 0}">
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
        <td><span class="userhighlight user_${user.id}">${user.username.encodeAsHTML()}</span></td>
        <td>${user?.profile?.fullName.encodeAsHTML()}</td>
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
  <strong>No users matching your search request were found or all matches are already members.</strong>
  </div>
</g:else>