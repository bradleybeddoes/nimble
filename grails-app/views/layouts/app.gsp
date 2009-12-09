<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
  <title><g:layoutTitle default="Grails"/></title>

  <nh:jquery/>

  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/screen.css')}"/>
  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/nimble.css')}"/>
  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/icons.css')}"/>

  <!--[if IE]>
  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/ie.css')}"/>
  <![endif]-->

  <g:layoutHead/>
</head>

<body>

<div id="doc">
  <div id="hd">

  </div>

  <div id="bd" class="clear">
    <g:layoutBody/>
  </div>

  <div id="ft">

  </div>
</div>


<g:render template="/templates/sessionterminated" contextPath="${pluginContextPath}"/>

</body>
</html>