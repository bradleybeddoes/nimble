<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
  "http://www.w3.org/TR/html4/strict.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Nimble | <g:layoutTitle default="Grails"/></title>

  <n:jquery/>
  <n:growl/>
  <n:flashgrowl/>
  <n:menu/>
  <n:userhighlight/>

  <link rel="stylesheet" href="${createLinkTo(dir: pluginContextPath, file: '/css/nimble.css')}"/>
  <link rel="stylesheet" href="${createLinkTo(dir: pluginContextPath, file: '/css/icons.css')}"/>

  <link rel="stylesheet" href="${createLinkTo(dir: pluginContextPath, file: '/css/administration.css')}"/>

  <!--[if IE]>
  <link rel="stylesheet" href="${createLinkTo(dir: pluginContextPath, file: '/css/ie.css')}"/>
  <![endif]-->

  <g:layoutHead/>
</head>

<body>

  <div id="doc">
    <div id="hd">
      <g:render template='/templates/nimble/navigation/topnavigation'/>
    </div>
    <div id="bd">
      <g:layoutBody/>
    </div>
    <div id="ft">

    </div>
  </div>

<g:render template="/templates/sessionterminated" contextPath="${pluginContextPath}"/>


</body>

</html>