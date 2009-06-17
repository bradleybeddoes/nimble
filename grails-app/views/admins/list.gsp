<table class="adminslist">
  <thead>
  <tr>
    <th class="first">Username</th>
    <th class="">Full Name</th>
    <th class="last">&nbsp;</th>
  </tr>
  </thead>
  <tbody>
  <g:each in="${admins}" status="i" var="user">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
      <td><span class="userhighlight user_${user.id}">${user.username?.encodeAsHTML()}</span></td>
      <td>${user?.profile?.fullName.encodeAsHTML()}</td>
      <td>
        <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="button icon icon_user_go">View</g:link>

        <g:if test="${currentAdmin != user}">
          <a onClick="deleteAdministrator('${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="button icon icon_delete">Revoke</a>
        </g:if>

      </td>
    </tr>
  </g:each>
  </tbody>
</table>