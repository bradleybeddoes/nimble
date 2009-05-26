<g:if test="${groups?.size() > 0}">
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
        <td>${group.name?.encodeAsHTML()}</td>
        <td>${group.description?.encodeAsHTML()}</td>
        <td>
          <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go">View Group</g:link>
          <a onClick="grantGroup('${ownerID.encodeAsHTML()}', '${group.id.encodeAsHTML()}');" class="button icon icon_add">Assign Group</a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    <strong>No groups matching your search request were found (or all matching groups are already assigned)</strong>
  </p>
</g:else>