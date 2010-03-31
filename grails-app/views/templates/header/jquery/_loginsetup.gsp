<link rel="stylesheet" href="${resource(dir: nimblePath, file: '/css/login.css')}"/>
<script type="text/javascript">
  $(function() {
    $(".loginmethod").hide();
    $("#loginfacebookcontinue").hide();

    var active = jQuery.url.param("active")
    if (active)
      changeLogin(active);
    else
      changeLogin('local');

    $("#loginlocal").show();
    $(".flash").show();
    $("#username").focus();
  });
</script>
