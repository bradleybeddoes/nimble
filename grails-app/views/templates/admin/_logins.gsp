<script type="text/javascript">

  $(function() {
    listLogins();
  });

  function listLogins() {
    var dataString = 'id=${ownerID.encodeAsHTML()}';
    $.ajax({
      type: "GET",
      url: "${createLink(action:'listlogins')}",
      data: dataString,
      success: function(res) {
        $("#loginhistory").empty().append(res).show();
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('error', 'Failed to list recent user logins');
      }
    });
  }

</script>

<div id="logins" class="section">
  <h3>Recent Logins</h3>

  <div id="loginhistory">
  </div>

</div>