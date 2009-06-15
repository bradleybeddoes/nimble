<script type="text/javascript">
function updateEmail() {
  if($("#newemail").val() != '') {
      var dataString = $("#updateemail form").serialize();
      $.ajax({
        type: "POST",
        url: "${createLink(action:'updateemail')}",
        data: dataString,
        success: function(res) {
          $("#updateemail").hide();
          $("#emailmessageerror").hide();
          $("#emailmessage").hide().empty().append(res).show('blind');
          growl("success", "Your email address was updated");
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl("error", "There was an error updating your email address");
          $("#emailmessageerror").empty().append(xhr.responseText).show('blind');
        }
      });
    }
  }

</script>

<div id="emailmessage">
  <g:if test="${user?.profile?.email != null}">
    <p>
      The email address associated with this account is <strong>${user?.profile?.email?.encodeAsHTML()}</strong>.
    </p>
    <p>
      This email address can be updated below. All email changes must be confirmed before they are activated.
    </p>
  </g:if>
  <g:else>
    <p>
      There is no email address associated with this account.
    </p>
    <p>
      An email address can be added below. All email changes must be confirmed before they are activated.
    </p>
  </g:else>
</div>

<div id="emailmessageerror">
</div>
<div id="updateemail"class="add">
  <h5>New email address</h5>
  <g:form action="updateemail" onsubmit="updateEmail(); return false;">
    <g:hiddenField name="id" value="${user.id}"/>
    <input type="text" size="40" id="newemail" name="newemail" value="" class="easyinput"/>
    <a href="#" class="button icon icon_email_add" onclick="updateEmail();">Update</a>
  </g:form>
</div>
