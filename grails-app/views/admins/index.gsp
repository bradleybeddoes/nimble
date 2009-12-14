<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Administrators</title>

  <script type="text/javascript">
    var adminListEndpoint = "${createLink(controller:'admins', action:'list')}";
	var adminSearchEndpoint = "${createLink(action:'search')}";
	var adminDeleteEndpoint = "${createLink(action:'delete')}";
	var adminGrantEndpoint = "${createLink(action:'create')}";

    $(function() {	  
      listAdministrators();
    });
  </script>

</head>

<body>

  <h2>Administrators</h2>
  <p>
    The following users have the administration role.

    This means they can undertake any operation and access any content regardless of other security measures in place.
  </p>

  <div id="admins">
  </div>

  <h3>Add Administrator</h3>
  <p>
    To add additional administrators simply search for the users below. You can search on username, full name and email address.
  </p>

  <div class="searchbox">
    <g:textField name="q" class="easyinput"/>
    <button onClick="searchAdministrators();" class="button icon icon_magnifier">Search</button>
  </div>

  <div id="searchresponse" class="clear">

  </div>

</body>