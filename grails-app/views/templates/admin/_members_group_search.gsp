<g:if test="${groups != null && groups.size() > 0}">
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
          <a onClick="addGroupMember('${parent.id.encodeAsHTML()}', '${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="button icon icon_add">Grant Membership</a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <div class="info">
  <strong>No groups matching your search request were found or all matches are already members.</strong>
  </div>
</g:else>