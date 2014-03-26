<n:javascript src='mootools/pstrength.js'/>
<script type="text/javascript">
window.addEvent('domready', function() {
  $$('.password').each(function(e){ nimble.PStrength(e); e.fireEvent('keyup'); });
  if($('username')) $('username').addEvent('blur',function() {$('pass').focus();});
  if($('pass')) $('pass').addEvent('blur',function() {$('passConfirm').focus();});
});
</script>
