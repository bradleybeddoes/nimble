<div id="members" class="section">

  <h3>Current Members</h3>
  <div id="currentmembers">
  </div>

  <g:if test="${!protect}">
    <div id="showaddmembers">
      <a id="showaddmembersbtn" class="button icon icon_group_add">Add Members</a>
    </div>

    <div id="addmembers">
      <h4>Add Members</h4>

      <g:if test="${groupmembers}">
        <g:radio name="memberselect" id="searchmembergroups"/><span class="icon icon_group"></span>&nbsp;Groups
        <g:radio name="memberselect" id="searchmemberusers" checked="true"/><span class="icon icon_user"></span>&nbsp;Users
      </g:if>

      <div id="memberaddusers">
        <p>
          To add additional users as members simply search for the user below. You can search on username, full name and email address.
        </p>

        <div class="searchbox">
          <g:textField name="qmembers" class="enhancedinput"/>
          <button onClick="searchMembers(${parent.id.encodeAsHTML()});" class="button icon icon_magnifier">Search</button>
          <button id="closeaddmembersbtn" class="button icon icon_cross">Close</button>
        </div>

        <div id="membersearchresponse" class="clear">
        </div>
      </div>

      <g:if test="${groupmembers}">
        <div id="memberaddgroups">
          <p>
            To add additional groups as members simply search for a group name below.
          </p>

          <div class="searchbox">
            <g:textField name="qmembersgroup" class="enhancedinput"/>
            <button onClick="searchGroupMembers(${parent.id.encodeAsHTML()});" class="button icon icon_magnifier">Search</button>
            <button id="closeaddgroupmembersbtn" class="button icon icon_cross">Close</button>
          </div>

          <div id="membergroupsearchresponse" class="clear">
          </div>
        </div>
      </g:if>

    </div>
  </g:if>
</div>