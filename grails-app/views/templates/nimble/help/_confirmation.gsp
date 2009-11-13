<script type="text/javascript">
  	var confirmAction = function() { alert('test act'); };

  	$(function() {		
    	$("#confirmation").hide();
    	$(".confirmationbtn").modal({ hide_on_overlay_click:false});	
  	});

  	function wasConfirmed(title, msg) {
		$("#confirmationtitle").html(title);
		$("#confirmationcontent").html(msg); 
		$(".confirmationbtn").click();
	}
</script>

<div id="confirmation">
  <div class="helppopup">
    <div class="banner"><strong id="confirmationtitle">Confirm</strong></div>
    <div class="content">
      <p id="confirmationcontent">Example confirmation message</p>

      <div class="buttons">
		<button type="submit" id="confirmaccept" class="modal_close button icon icon_accept" onClick="confirmAction()">Accept</button>
        <a id="confirmcancel" class="modal_close button icon icon_cancel">Cancel</a>    
      </div>
      
    </div>
  </div>
</div>
<a href="#" class="confirmationbtn" rel="confirmation">&nbsp;</a>