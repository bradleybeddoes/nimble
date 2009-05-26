<script type="text/javascript">
  $(function() {
    $("#sessionterminated").hide();
    $("#sessionterminatedmodal").modal({hide_on_overlay_click:false});

    $().ajaxError(function (event, xhr, ajaxOptions, thrownError) {
      if ((xhr.status == 403) && (xhr.getResponseHeader("N-Session-Invalid") != null)) {
        $("#sessionterminatedmodal").click();
      }
    });
  });
</script>


<div id="sessionterminated">
  <div class="errorpopup">
    <div class="banner"><strong><span class="icon icon_exclamation">&nbsp;</span><strong>Session has expired</strong></strong></div>
    <div class="content">
      Unfortunately your session has expired. This usually happens when a page is left open for long periods of time without
      performing any action. If you're experiencing this problem often contact your helpdesk for assistance.
      <div class="buttons">
        <a href="#" onClick="window.location.reload();return false;" id="sessionterminatedbtn" class="button icon icon_flag_blue">Login Again</a>
      </div>
    </div>
  </div>
</div>
<a href="#" id="sessionterminatedmodal" rel="sessionterminated">&nbsp;</a>