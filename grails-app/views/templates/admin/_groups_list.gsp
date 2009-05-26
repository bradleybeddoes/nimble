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
          <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go">View</g:link>
          <g:if test="${group.protect == false}">
            <a onClick="removeGroup('${ownerID.encodeAsHTML()}', '${group.id.encodeAsHTML()}');" class="button icon icon_delete">Remove</a>
          </g:if>
          <g:else>&nbsp;</g:else>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    This user is not currently a member of any group.
  </p>
</g:else>