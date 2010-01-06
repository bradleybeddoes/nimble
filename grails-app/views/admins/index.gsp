<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.admins.title" /></title>

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

  <h2><g:message code="nimble.view.admins.heading" /></h2>
  <p>
    <g:message code="nimble.view.admins.descriptive" />
  </p>

  <div id="admins">
  </div>

  <h3><g:message code="nimble.view.admins.addadmin.heading" /></h3>
  <p>
    <g:message code="nimble.view.admins.addadmin.descriptive" />
  </p>

  <div class="searchbox">
    <g:textField name="q" class="easyinput"/>
    <button onClick="searchAdministrators();" class="button icon icon_magnifier"><g:message code="nimble.button.search" /></button>
  </div>

  <div id="searchresponse" class="clear">
  </div>

</body>