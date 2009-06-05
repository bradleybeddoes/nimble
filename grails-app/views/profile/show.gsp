<html>

  <head>
    <meta name="layout" content="app"/>
    <title>Profile | ${profile.fullName}</title>

  <n:growl/>
  <n:flashgrowl/>

  <link rel="stylesheet" href="${createLinkTo(dir: pluginContextPath, file: '/css/profile.css')}"/>


  <g:render template="/templates/validate_username" contextPath="${pluginContextPath}"/>
</head>

<body>

  <div class="container">
    <div class="profile">

      <div class="profileleft">
        <g:if test="${profile.photo == null}">
          <img src="/nimble/images/silhouette.png" alt="No photo yet"/>
        </g:if>
      </div>

      <div class="profilemain">
        <h2>${profile.fullName}</h2>
        <g:if test="${user == authUser}">
          <g:link action="edit" id="${user.id}">Edit Profile</g:link>
        </g:if>
        <table>
          <tbody>
            <tr>
              <th>Username</th>
              <td>${user.username.encodeAsHTML()}</td>
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

          <g:if test="${profile.dob}">
            <tr>
              <th>Date of birth</th>
              <td><g:formatDate format="yyyy-MM-dd" date="${profile.dob}"/></td>
            </tr>
          </g:if>

          <g:if test="${profile.gender}">
            <tr>
              <th>Gender</th>
              <td>${profile.gender.encodeAsHTML()}</td>
            </tr>
          </g:if>

          </tbody>
        </table>

        <h3>Contact Details</h3>
        <table>
          <tbody>

          <g:if test="${profile.email}">
            <tr>
              <th>Primary Email Address</th>
              <td>${profile.email.encodeAsHTML()}</td>
            </tr>
          </g:if>

          <g:each in="${profile.alternateEmails}" var="email" status="i">
            <tr>
              <th>Alternate Email Address ${i + 1}</th>
              <td>${email.encodeAsHTML()}</td>
            </tr>
          </g:each>

          <g:each in="${profile.phoneNumbers}" var="phone" status="i">
            <tr>
              <th>${phone.type.encodeAsHTML()} Phone</th>
              <td>
                ${phone.markup()}
              </td>
            </tr>
          </g:each>

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

        <g:if test="${profile.socialAccounts?.size() > 0}">
          <h3>Social Networks</h3>

          <div class="socialnetworks">
            <g:each in="${profile.socialAccounts}" var="social" status="i">
              <g:render template="/templates/nimble/account/socialmedia" model="[account:social]"/>
            </g:each>
          </div>

        </g:if>
      </div>
    </div>
  </div>

</body>
</html>
