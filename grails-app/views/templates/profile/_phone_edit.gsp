<%@ page import="intient.nimble.domain.PhoneType" %>

<script type="text/javascript">
  function newPhone() {
    if($("#newnumber").val() != '')
    {
      var dataString = $("#newphonenumber").serialize();
      $.ajax({
        type: "POST",
        url: "${createLink(action:'newphone')}",
        data: dataString,
        success: function(res) {
          $("#newnumber").val('')
          $("#newnumbermessageerror").empty();
          $("#currentphonenumbers").empty().append(res).show('highlight')
          growl("success", "Your new phone number was added");
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl("error", "There was an error adding your phone number");
          $("#newnumbermessageerror").hide().empty().append(xhr.responseText).show('blind');
        }
      });
    }
  }

  function deletePhone(id, phoneID) {
    var dataString = "id=" + id + "&phoneID=" + phoneID;
      $.ajax({
        type: "POST",
        url: "${createLink(action:'deletephone')}",
        data: dataString,
        success: function(res) {
          $("#currentphonenumbers").empty().append(res)
          $("#newnumber").val('')
          growl("success", "Your phone number was deleted");
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl("error", "There was an error removing your phone number");   
        }
      });
  }
</script>

<div id="currentphonenumbers">
  <g:render template="/templates/profile/phone_edit_list" contextPath="${pluginContextPath}" model="[user:user]" />
</div>

<div id="newnumbermessageerror">
</div>
<div class="add">
  <h5>Add new phone number</h5>
  <g:form name="newphonenumber" action="newphone" onsubmit="newPhone(); return false;">
    <g:hiddenField name="id" value="${user.id}"/>
    <table>
      <tbody>
        <tr>
          <th>Location</th>
          <td>
      <g:select name="type" from="${PhoneType.values()}" value="${user?.profile?.gender?.encodeAsHTML()}" class="easyinput"/>
      </td>
      </tr>
      <tr>
        <th>Number</th>
        <td>
      <g:textField id="newnumber" name="number" class="easyinput"/>
      </td>
      </tr>
      <tr>
        <td/>
        <td>
          <a href="#" onclick="newPhone(); return false;" class="button icon icon_phone_add">Create</a>
        </td>
      </tr>
      </tbody>
    </table>

  </g:form>
</div>
