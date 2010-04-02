<script type="text/javascript" src="${resource(dir: nimblePath, file: '/js/prototype/pstrength.js')}"></script>
<script type="text/javascript">
window.addEvent('domready', function() {
  $$('.password').each(function(e){ nimble.PStrength(e); e.fireEvent('keyup'); });
  if($('username')) $('username').addEvent('blur',function() {$('pass').focus();});
  if($('pass')) $('pass').addEvent('blur',function() {$('passConfirm').focus();});
});
</script>
