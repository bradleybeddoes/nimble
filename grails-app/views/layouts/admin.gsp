<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
  "http://www.w3.org/TR/html4/strict.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title><g:message code="nimble.layout.admin.title" /> <g:layoutTitle default="Grails"/></title>

  	<nh:nimblecore/>
    <nh:nimbleui/>
    <nh:admin/>

	<nh:growl/>
    <script type="text/javascript">
      <njs:flashgrowl/>
    </script>

    <g:layoutHead/>
</head>

<body>

  <div id="doc">

    <div id="hd">
      <g:render template='/templates/nimble/nimbleheader' model="['navigation':true]"/>
    </div>

    <div id="bd">
      	<div class="contentcontainer">
	        <g:render template="/templates/nimble/navigation/sidenavigation" />
			<div class="content">
	      		<g:layoutBody/>
			</div>
		</div>
    </div>

    <div id="ft">
    </div>

  </div>

<n:sessionterminated/>

</body>

</html>