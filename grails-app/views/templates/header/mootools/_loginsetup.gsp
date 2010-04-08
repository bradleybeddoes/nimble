<n:css src='login.css'/>
<script type="text/javascript">
window.addEvent('domready', function() {
    $$(".loginmethod").each(function(e) { e.hide();});
    if($("loginfacebookcontinue")) $("loginfacebookcontinue").hide();

    var active=document.location.href.toURI().getData('active');
    if (active)
      nimble.changeLogin(active);
    else                 
      nimble.changeLogin('local');

    $$(".loginlocal").show();
    $$(".flash").show();
    $("username").focus();
  });
</script>
