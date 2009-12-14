<div id="roles" class="section">
  <h3>Assigned Roles</h3>

  <div id="assignedroles">
  </div>

  <div id="showaddroles">
    <a id="showaddrolesbtn" class="button icon icon_cog_add">Add Roles</a>
  </div>

  <div id="addroles">
    <h4>Add Roles</h4>
    <p>
      To add additional roles simply search for the role below. You can search on role name and description.
    </p>

    <div class="searchbox">
      <g:textField name="qroles" class=""/>
      <button onClick="searchRoles(${parent.id.encodeAsHTML()});" class="button icon icon_magnifier">Search</button>
      <button id="closerolesearchbtn" class="button icon icon_cross">Close</button>
    </div>

    <div id="rolesearchresponse" class="clear">
    </div>
  </div>

</div>
