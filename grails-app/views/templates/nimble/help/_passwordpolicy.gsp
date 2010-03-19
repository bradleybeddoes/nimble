<g:javascript src="${grailsApplication.config.nimble.layout.jslibrary}/pstrength.js" contextPath="${pluginContextPath}"/>
<script type="text/javascript">

  $(function() {
    $("#passwordpolicy").hide();
    $("#passwordpolicybtn").bt({contentSelector: $("#passwordpolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

    $('.password').pstrength();
    $('.password').keyup();
  });
</script>

<div id="passwordpolicy">
  <div class="ui-dialog ui-widget">
    <div class="ui-dialog-titlebar ui-widget-header ui-corner-all"><strong><g:message code="nimble.template.passwordpolicy.title" /></strong></div>
    <div class="ui-dialog-content ui-widget-content">
      <g:message code="nimble.template.passwordpolicy" />
    </div>
  </div>
</div>