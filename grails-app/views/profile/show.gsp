

<html>
  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title>Profile | ${profile.fullName}</title>

  <n:growl/>
  <n:flashgrowl/>

  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/profile.css')}"/>
</head>

<body>

  <div class="container">
    <div class="profile">

      <g:render template="/templates/nimble/profile/left" model="[user:user, profile:profile]" />

      <div class="main">
        <g:render template="/templates/nimble/profile/banner" model="[user:user, profile:profile, clear:true]" />

        <g:render template="/templates/profile/status_create" contextPath="${pluginContextPath}" model="[user:user, profile:profile]" />

        <div class="common">
          <h3>Account Details</h3>
          <table>
            <tbody>
              <tr>
                <th>Username</th>
                <td>${user.username.encodeAsHTML()}</td>
              </tr>

              <tr>
                <th>Created</th>
                <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.profile.dateCreated}"/></td>
            </tr>

            <tr>
              <th>Last Updated</th>
              <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.profile.lastUpdated}"/></td>
            </tr>

            <g:if test="${profile.fullName}">
              <tr>
                <th>Full Name</th>
                <td>${profile.fullName.encodeAsHTML()}</td>
              </tr>
            </g:if>

            <g:if test="${profile.nickName}">
              <tr>
                <th>Nick Name</th>
                <td>${profile.nickName.encodeAsHTML()}</td>
              </tr>
            </g:if>

            <g:if test="${profile.dob != null}">
              <tr>
                <th>Date of Birth</th>
                <td><g:formatDate format="dd/MM/yyyy" date="${profile.dob}"/></td>
              </tr>
            </g:if>

            <g:if test="${profile.gender != null}">
              <tr>
                <th>Gender</th>
                <td>${profile.gender.encodeAsHTML()}</td>
              </tr>
            </g:if>

            </tbody>
          </table>
        </div>

        <g:if test="${profile.email?.size() > 0 || profile.alternateEmails?.size() > 0 || profile.phoneNumbers?.size() > 0 || profile.addresses?.size() > 0}">
          <h3>Contact Details</h3>
          <table>
            <tbody>

            <g:if test="${profile.email}">
              <tr>
                <th>Email Address</th>
                <td><a href="mailto:${profile.email.encodeAsHTML()}" class="icon icon_email">${profile.email.encodeAsHTML()}</a></td>
              </tr>
            </g:if>

            <g:each in="${profile.alternateEmails}" var="email" status="i">
              <tr>
                <th>Alternate Email Address ${i + 1}</th>
                <td>${email.encodeAsHTML()}</td>
              </tr>
            </g:each>

            <g:if test="${profile.phoneNumbers?.size() > 0}">
              <g:each in="${profile.phoneNumbers}" var="phone" status="i">
                <tr>
                  <th>${phone.type.encodeAsHTML()} Phone</th>
                  <td>${phone.number.encodeAsHTML()}</td>
                </tr>
              </g:each>
            </g:if>

            <g:each in="${profile.addresses}" var="address" status="i">
              <tr>
                <th>${address.category.encodeAsHTML()} Address</th>
                <td>
${address.markup()}
                </td>
              </tr>
            </g:each>

            </tbody>
          </table>
        </g:if>

        <g:if test="${profile.socialAccounts?.size() > 0}">
          <h3>Social Networks</h3>
          <div class="socialnetworks">
            <g:each in="${profile.socialAccounts}" var="social" status="i">
              <g:render template="/templates/nimble/profile/socialmedia" model="[account:social, remove:false]"/>
            </g:each>
          </div>

        </g:if>
      </div>
    </div>
  </div>

</body>
</html>
