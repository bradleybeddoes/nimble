<g:if test="${roles?.size() > 0}">
  <table class="details">
    <thead>
    <tr>
      <th class="first">Name</th>
      <th class="">Description</th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${roles}" status="i" var="role">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${role.name?.encodeAsHTML()}</td>
        <td>${role.description?.encodeAsHTML()}</td>
        <td>
          <g:link controller="role" action="show" id="${role.id.encodeAsHTML()}" class="button icon icon_cog_go">View Role</g:link>
          <a onClick="grantRole('${ownerID.encodeAsHTML()}', '${role.id.encodeAsHTML()}');" class="button icon icon_add">Assign Role</a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    <strong>No roles matching your search request were found (or all matching roles are already assigned)</strong>
  </p>
</g:else>