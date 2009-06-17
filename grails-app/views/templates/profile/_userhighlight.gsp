<script type="text/javascript">
  function resolveUserID(elem) {
    var userID = '';
    var classes = elem.attr('class').split(' ');
     jQuery.each(classes, function(i, val) {
      if(val.indexOf('user_') != -1) {
        userID = val.slice(5);
      }
    });

    return "${createLink(controller: "profile", action: "miniprofile")}/" + userID;
  }

  function bindUserHighlight() {
    $('.userhighlight').bt({getAjaxPath: resolveUserID, ajaxLoading: 'Loading...', width: '300px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['bottom'], fill: '#fbfbfb', strokeWidth: 2, strokeStyle: '#f2f2f2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 2000}})
  }

  $(function() {
    bindUserHighlight();
  });
</script>