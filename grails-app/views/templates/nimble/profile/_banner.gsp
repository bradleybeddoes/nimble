

<div class="banner">
  <p>
  <g:link action="show" id="${user.id}">
    <g:if test="${profile.fullName != null}">
      <strong>${profile.fullName}</strong>
    </g:if>
    <g:else>
      <strong>${user.username}</strong>
    </g:else>
  </g:link>
  <span id="activestatus">
    &nbsp;<n:status id="${user.id}" clear="${clear}"/>
  </span>
</p>
</div>
