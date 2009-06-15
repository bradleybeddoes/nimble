<g:if test="${user.profile.phoneNumbers?.size() > 0}">
  <p>The following phone numbers are associated with your profile.</p>
  <table  class="phonelist">
    <thead>
      <tr>
        <th>Type</th>
        <th>Number</th>
        <th/>
    </thead>
    <tbody>

    <g:each in="${user.profile.phoneNumbers}" var="phone" status="i">
      <tr>
        <th>${phone.type}</th>
        <td>
          ${phone.number}
        </td>
        <td>
        <a href="#" class="button icon icon_phone_delete" onclick="deletePhone(${user.id}, ${phone.id})">Delete</a>
      </td>
      </tr>
    </g:each>

    </tbody>
  </table>
</g:if>
<g:else>
  <p>There are no phone numbers stored for this profile.</p>
</g:else>
