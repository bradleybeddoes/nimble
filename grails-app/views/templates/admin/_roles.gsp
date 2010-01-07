<div id="roles" class="section">
  <h3><g:message code="nimble.template.roles.heading" /></h3>

  <div id="assignedroles">
  </div>

  <div id="showaddroles">
    <a id="showaddrolesbtn" class="button icon icon_cog_add"><g:message code="nimble.button.addroles" /></a>
  </div>

  <div id="addroles">
    <h4><g:message code="nimble.template.roles.add.heading" /></h4>
    <p>
      <g:message code="nimble.template.roles.add.descriptive" />
    </p>

    <div class="searchbox">
      <g:textField name="qroles" class=""/>
      <button onClick="searchRoles(${parent.id.encodeAsHTML()});" class="button icon icon_magnifier"><g:message code="nimble.button.search" /></button>
      <button id="closerolesearchbtn" class="button icon icon_cross"><g:message code="nimble.button.close" /></button>
    </div>

    <div id="rolesearchresponse" class="clear">
    </div>
  </div>

</div>
