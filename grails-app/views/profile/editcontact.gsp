
<html>

  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title>Profile | Edit Contact Details</title>

  <n:growl/>
  <n:flashgrowl/>

  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/profile.css')}"/>
</head>

<body>
  <div class="container">
    <div class="profile">

      <g:render template="/templates/nimble/profile/left" model="[user:user, profile:user.profile]" />

      <div class="main edit">
        <g:render template="/templates/nimble/profile/banner" model="[user:user, profile:user.profile]" />

        <div class="section">
          <h4 class="icon icon_email">Email Address</h4>
          <g:render template="/templates/profile/email_edit" contextPath="${pluginContextPath}" model="[user:user]" />
        </div>

        <div class="section">
          <h4 class="icon icon_phone">Phone Numbers</h4>
          <g:render template="/templates/profile/phone_edit" contextPath="${pluginContextPath}" model="[user:user]" />
        </div>

      </div>

    </div>
  </div>
</body>

</html>
