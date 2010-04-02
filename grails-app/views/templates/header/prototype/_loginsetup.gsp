<link rel="stylesheet" href="${resource(dir: nimblePath, file: '/css/login.css')}"/>
<script type="text/javascript">
window.addEvent('domready', function() {
    $$(".loginmethod").each(function(e) { e.hide();});
    $("loginfacebookcontinue").hide();

    var active=document.location.href.toURI().getData('active');
    if (active)
      changeLogin(active);
    else                 
      changeLogin('local');

    $$(".loginlocal").show();
    $$(".flash").show();
    $("username").focus();
  });
</script>
