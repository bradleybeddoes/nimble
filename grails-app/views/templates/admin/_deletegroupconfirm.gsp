<script type="text/javascript">
  $(function() {
    $("#deleteconfirm").hide();
    $("#deleteconfirmbtn").modal({hide_on_overlay_click:false});
  });
</script>

<div id="deleteconfirm">
  <div class="deletepopup">
    <div class="banner"><strong><span class="icon icon_exclamation">&nbsp;</span><strong>Delete this group?</strong></strong></div>
    <div class="content">

      Deleting groups removes rights from many users and may cause access problems.

      <g:form action="delete">
        <g:hiddenField name="id" value="${group.id.encodeAsHTML()}"/>

        <div class="buttons">
          <a id="canceldelete" class="modal_close button icon icon_cross">Cancel</a>
          <button class="button icon icon_tick">Ok</button>
        </div>
      </g:form>

    </div>
  </div>
</div>