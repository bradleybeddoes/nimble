<script type="text/javascript" src="${resource(dir: nimblePath, file: '/js/mootools/pstrength-min.js')}"></script>
<script type="text/javascript">
window.addEvent('domready', function() {
  $$('.password').each(function(e){ nimble.PStrength(e); e.fireEvent('keyup'); });
});
</script>
