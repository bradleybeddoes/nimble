<script type="text/javascript">
$(function() {
	$("#dialog").dialog({
		bgiframe: true,
		resizable: false,
		modal: true,
		autoOpen: false,
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		}
	});
});
</script>

<div id="dialog" title="">
      <p id="confirmationcontent">Example confirmation message</p>

      <div class="buttons">
		<button type="submit" id="confirmaccept" class="modal_close button icon icon_accept" onClick="confirmAction()">Accept</button>
        <a id="confirmcancel" onClick="$('#dialog').dialog('close');" class="modal_close button icon icon_cancel">Cancel</a>    
      </div>
</div>
