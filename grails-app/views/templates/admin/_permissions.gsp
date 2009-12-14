<div id="permissions" class="section">

  <h3>Assigned Permissions</h3>
  <div id="currentpermission">
  </div>

  <div id="showaddpermissions">
    <a id="showaddpermissionsbtn" class="button icon icon_group_add">Add Permission</a>
  </div>

  <div id="addpermissions">
    <h4>Add Permission</h4>
    <p>
      The following will create a new Wildcard permission and assign it to this user.
    </p>

    <div id="addpermissionserror"></div>

    <table>
      <tbody>
      <tr>
        <td>
          <g:textField name="first_p" class="easyinput"/> <strong>:</strong>
          <g:textField name="second_p" class="easyinput"/> <strong>:</strong>
          <g:textField name="third_p" class="easyinput"/> <strong>:</strong>
          <g:textField name="fourth_p" class="easyinput"/>
        </td>
      </tr>
      </tbody>
    </table>
    <button onClick="createPermission(${parent.id.encodeAsHTML()});" class="button icon icon_add">Add</button>
    <button id="closepermissionsaddbtn" class="button icon icon_cross">Close</button>
  </div>
</div>