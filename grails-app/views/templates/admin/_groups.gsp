<div id="groups" class="section">
  <h3><g:message code="nimble.template.groups.heading" /></h3>

  <div id="assignedgroups">
  </div>

  <div id="showaddgroups">
    <a id="showaddgroupsbtn" class="button icon icon_group_add"><g:message code="nimble.button.addgroups" /></a>
  </div>

  <div id="addgroups">
    <h4><g:message code="nimble.template.groups.add.heading" /></h4>
    <p>
      <g:message code="nimble.template.groups.add.descriptive" />
    </p>

    <div class="searchbox">
      <g:textField name="qgroups" class="enhancedinput"/>
      <button onClick="searchGroups('${parent.id.encodeAsHTML()}');" class="button icon icon_magnifier"><g:message code="nimble.button.search" /></button>
      <button id="closegroupsearchbtn" class="button icon icon_cross"><g:message code="nimble.button.close" /></button>
    </div>

    <div id="groupsearchresponse" class="clear">
    </div>

  </div>
</div>