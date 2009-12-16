<script type="text/javascript">

  $(function() {
    $("#usernamepolicy").hide();
    $("#usernamepolicybtn").bt({contentSelector: $("#usernamepolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

  });
</script>

<div id="usernamepolicy">
  <div class="ui-dialog ui-widget">
    <div class="ui-dialog-titlebar ui-widget-header ui-corner-all"><strong>Choosing a username</strong></div>
    <div class="ui-dialog-content ui-widget-content">
      <p>Usernames must meet the following requirements</p>
      <ul>
        <li>Be at least 4 characters in length</li>
        <li>Don't contain any spaces</li>
        <li>Contain only letters and numbers</li>
        <li>Not previously used as a username for another account</li>
      </ul>
    </div>
  </div>
</div>