<n:javascript src='jquery/jquery.pstrength.min.js'/>
<script type="text/javascript">
$(function() {
  $("#passwordpolicy").hide();
  $("#passwordpolicybtn").bt({contentSelector: $("#passwordpolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

  if($('#username')) $('#username').blur(function() {$('#pass').focus();});
  if($('#pass')) $('#pass').blur(function() {$('#passConfirm').focus();});  
  $('.password').pstrength();
  $('.password').keyup();
});
</script>
