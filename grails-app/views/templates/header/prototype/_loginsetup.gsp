<n:css src='login.css'/>
<script type="text/javascript">
document.observe("dom:loaded", function() {
    $$(".loginmethod").each(function(e) { e.hide();});
    $("loginfacebookcontinue").hide();

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
