
<link rel="stylesheet" href="${createLinkTo(dir: nimblePath, file: '/css/jquery.jgrowl.css')}"/>
<script type="text/javascript" src="${createLinkTo(dir: nimblePath, file: '/js/jquery/jquery.jgrowl_minimized.js')}">
  
</script>

<script type="text/javascript">
  function growl(type, msg) {
    growl(type, msg, 2000);
  }

  function growl(type, msg, period) {
    if (type == 'success')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_tick\'>&nbsp;</span>Success' });

    if (type == 'error')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>Error' });

    if (type == 'info')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_information\'>&nbsp;</span>Information' });

    if (type == 'help')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>Help' });

    if (type == 'flagred')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_red\'>&nbsp;</span>' });

    if (type == 'flaggreen')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_green\'>&nbsp;</span>' });

    if (type == 'flagblue')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_blue\'>&nbsp;</span>' });
  }
</script>