<g:if test="${permissions?.size() > 0}">
  <table class="details">
    <thead>
    <tr>
      <th class="first">Type</th>
      <th class="">Target</th>
      <th class="">Managed</th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>

    <g:each in="${permissions}" status="i" var="perm">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${perm.type.encodeAsHTML()}</td>
        <td>${perm.target.encodeAsHTML()}</td>
        <td>
          <g:if test="${perm.managed}"><span class="icon icon_tick">&nbsp;Yes</span></g:if>
          <g:else><span class="icon icon_cross">&nbsp;No</span></g:else>
        </td>
        <td>
          <g:if test="${!perm.managed}"><button onClick="removePermission('${parent.id.encodeAsHTML()}', '${g.fieldValue(bean:perm, field:'id')}');" class="button icon icon_delete">Remove</button></g:if>
          <g:else>&nbsp;</g:else>
        </td>
      </tr>
    </g:each>

    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    There are no permissions currently assigned.
  </p>
</g:else>