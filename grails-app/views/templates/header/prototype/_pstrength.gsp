<n:javascript src='prototype/pstrength.js'/>
<script type="text/javascript">
document.observe("dom:loaded", function() {
  $$('.password').each( function(e){
    nimble.PStrength(e); e.fire('keyup');
  });
  if($('username')) $('username').observe('blur',function() {$('pass').focus();});
  if($('pass')) $('pass').observe('blur',function() {$('passConfirm').focus();});
});
</script>
