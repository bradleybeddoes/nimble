<script type="text/javascript">

  $(function() {
    $("#usernamepolicy").hide();
    $("#usernamepolicybtn").bt({contentSelector: $("#usernamepolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['top', 'right', 'left'], fill: '#fbfbfb', strokeWidth: 2, strokeStyle: '#f2f2f2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

  });
</script>

<div id="usernamepolicy">
  <div class="helppopup">
    <div class="banner"><strong>Choosing a username</strong></div>
    <div class="content">
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