<div id="permissions" class="section">

  <h3><g:message code="nimble.template.permissions.heading" /></h3>
  <div id="currentpermission">
  </div>

  <div id="showaddpermissions">
    <a id="showaddpermissionsbtn" class="button icon icon_group_add"><g:message code="nimble.button.addpermission" /></a>
  </div>

  <div id="addpermissions">
    <h4><g:message code="nimble.template.permission.add.heading" /></h4>
    <p>
      <g:message code="nimble.template.permission.add.descriptive" />
    </p>

    <div id="addpermissionserror"></div>

    <table>
      <tbody>
      <tr>
        <td>
          <g:textField size="15" name="first_p" class="easyinput"/> <strong>:</strong>
          <g:textField size="15" name="second_p" class="easyinput"/> <strong>:</strong>
          <g:textField size="15" name="third_p" class="easyinput"/> <strong>:</strong>
          <g:textField size="15" name="fourth_p" class="easyinput"/>
        </td>
      </tr>
      </tbody>
    </table>
    <button onClick="createPermission(${parent.id.encodeAsHTML()});" class="button icon icon_add"><g:message code="nimble.button.createpermission" /></button>
    <button id="closepermissionsaddbtn" class="button icon icon_cross"><g:message code="nimble.button.close" /></button>
  </div>
</div>