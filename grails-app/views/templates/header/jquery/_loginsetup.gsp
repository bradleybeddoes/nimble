<n:css src='login.css'/>
<script type="text/javascript">
  $(function() {
    $(".loginmethod").hide();
    $("#loginfacebookcontinue").hide();

    var active = jQuery.url.param("active")
    if (active)
      nimble.changeLogin(active);
    else
      nimble.changeLogin('local');

    $("#loginlocal").show();
    $(".flash").show();
    $("#username").focus();
  });
</script>
