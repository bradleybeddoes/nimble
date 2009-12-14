<div id="groups" class="section">
  <h3>Group Membership</h3>

  <div id="assignedgroups">
  </div>

  <div id="showaddgroups">
    <a id="showaddgroupsbtn" class="button icon icon_group_add">Add Groups</a>
  </div>

  <div id="addgroups">
    <h4>Add Groups</h4>
    <p>
      To add additional groups simply search for the group name below.
    </p>

    <div class="searchbox">
      <g:textField name="qgroups" class="enhancedinput"/>
      <button onClick="searchGroups('${parent.id.encodeAsHTML()}');" class="button icon icon_magnifier">Search</button>
      <button id="closegroupsearchbtn" class="button icon icon_cross">Close</button>
    </div>

    <div id="groupsearchresponse" class="clear">
    </div>

  </div>
</div>