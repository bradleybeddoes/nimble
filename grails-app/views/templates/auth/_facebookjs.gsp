<g:if test="${secure}">
  <script type="text/javascript" src="https://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php"></script>
</g:if>
<g:else>
  <script type="text/javascript" src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php"></script>
</g:else>

<script type="text/javascript">
  <g:if test="${secure}">
  FB.init('${apikey}', '${createLink(controller:'auth', action:'facebookxdrecieverssl')}', {"ifUserConnected" : enableFacebookContinue});
  </g:if>
  <g:else>
  FB.init('${apikey}', '${createLink(controller:'auth', action:'facebookxdreciever')}', {"ifUserConnected" : enableFacebookContinue});
  </g:else>
</script>
