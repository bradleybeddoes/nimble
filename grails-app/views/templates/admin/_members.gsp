<div id="members" class="section">

  <h3><g:message code="nimble.template.members.heading" /></h3>
  <div id="currentmembers">
  </div>

  <g:if test="${!protect}">
    <div id="showaddmembers">
      <a id="showaddmembersbtn" class="button icon icon_group_add"><g:message code="nimble.link.addmembers" /></a>
    </div>

    <div id="addmembers">
      <h4><g:message code="nimble.template.members.add.heading" /></h4>

      <g:if test="${groupmembers}">
        <g:radio name="memberselect" id="searchmembergroups"/><span class="icon icon_group"></span><g:message code="nimble.label.groups" />
        <g:radio name="memberselect" id="searchmemberusers" checked="true"/><span class="icon icon_user"></span><g:message code="nimble.label.users" />
      </g:if>

      <div id="memberaddusers">
        <p>
          <g:message code="nimble.template.members.add.user.descriptive" />
        </p>

        <div class="searchbox">
          <g:textField name="qmembers" class="enhancedinput"/>
          <button onClick="nimble.searchMembers(${parent.id.encodeAsHTML()});" class="button icon icon_magnifier"><g:message code="nimble.link.search" /></button>
          <button id="closeaddmembersbtn" class="button icon icon_cross"><g:message code="nimble.link.close" /></button>
        </div>

        <div id="membersearchresponse" class="clear">
        </div>
      </div>

      <g:if test="${groupmembers}">
        <div id="memberaddgroups">
          <p>
            <g:message code="nimble.template.members.add.group.descriptive" />
          </p>

          <div class="searchbox">
            <g:textField name="qmembersgroup" class="enhancedinput"/>
            <button onClick="nimble.searchGroupMembers(${parent.id.encodeAsHTML()});" class="button icon icon_magnifier"><g:message code="nimble.link.search" /></button>
            <button id="closeaddgroupmembersbtn" class="button icon icon_cross"><g:message code="nimble.link.close" /></button>
          </div>

          <div id="membergroupsearchresponse" class="clear">
          </div>
        </div>
      </g:if>

    </div>
  </g:if>
</div>