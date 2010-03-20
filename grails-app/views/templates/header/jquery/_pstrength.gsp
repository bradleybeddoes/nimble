<script type="text/javascript" src="${resource(dir: nimblePath, file: '/js/jquery/jquery.pstrength.js')}"></script>
<script type="text/javascript">

  $(function() {
    $("#passwordpolicy").hide();
    $("#passwordpolicybtn").bt({contentSelector: $("#passwordpolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

    $('.password').pstrength();
    $('.password').keyup();
  });
</script>
