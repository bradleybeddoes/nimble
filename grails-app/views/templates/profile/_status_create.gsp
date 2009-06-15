<%@ page import="intient.nimble.service.ProfileService" %>

<script type="text/javascript">
function updateStatus() {
  if($("#newstatus").val() != '')
  {
    var dataString = $("#statusentry").serialize();
    $.ajax({
      type: "POST",
      url: "${createLink(action:'updatestatus')}",
      data: dataString,
      success: function(res) {
        $("#activestatus").hide().empty().append(res).show('highlight');
        $("#newstatus").val('')
        growl("success", "Your status was updated");
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl("error", "There was an error updating your status");
      }
    });
  }
}

</script>

<n:hasPermission target="${ProfileService.editPermission + profile.owner.id}">
  <div id="createstatus">
    <h3>What are you upto?</h3>
    <form id="statusentry" onsubmit="updateStatus(); return false;">
      <g:hiddenField name="id" value="${user.id}"/>
      <input type="text" size="48" id="newstatus" name="newstatus" class="easyinput"/>
      <a class="button icon icon_comment_add" onclick="updateStatus();">Share</a>
    </form>
  </div>
</n:hasPermission>
