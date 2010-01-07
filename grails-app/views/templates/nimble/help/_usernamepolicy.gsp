<script type="text/javascript">

  $(function() {
    $("#usernamepolicy").hide();
    $("#usernamepolicybtn").bt({contentSelector: $("#usernamepolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

  });
</script>

<div id="usernamepolicy">
  <div class="ui-dialog ui-widget">
    <div class="ui-dialog-titlebar ui-widget-header ui-corner-all"><strong><g:message code="nimble.template.usernamepolicy.title" /></strong></div>
    <div class="ui-dialog-content ui-widget-content">
      <g:message code="nimble.template.usernamepolicy" />
    </div>
  </div>
</div>