<g:javascript src="jquery/jquery.pstrength.js"/>
<script type="text/javascript">

  $(function() {
    $("#passwordpolicy").hide();
    $("#passwordpolicybtn").bt({contentSelector: $("#passwordpolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right'], fill: '#fbfbfb', strokeWidth: 2, strokeStyle: '#f2f2f2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});

    $('.password').pstrength();
    $('.password').keyup();
  });
</script>

<div id="passwordpolicy">
  <div class="helppopup">
    <div class="banner"><strong>Setting a password</strong></div>
    <div class="content">
      <p>To ensure security passwords must meet the following requirements</p>
      <ul>
        <li>Be at least 8 characters in length</li>
        <li>Contain upper and lower case characters</li>
        <li>Contain at least one number</li>
        <li>Contain at least one symbol</li>
        <li>Not previously used as a password on this account</li>
      </ul>
      <p>Ideally set a password that is considered to be in the category 'strongest' by the indicator bar.</p>
    </div>
  </div>
</div>