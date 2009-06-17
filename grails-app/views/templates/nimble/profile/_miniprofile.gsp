    <div class="miniprofile">

      <div class="intro">
        <table>
          <tbody>
            <tr>
              <td>
          <n:photo id="${user.id}" size="50"/>
          </td>
          <td>
          <g:render template="/templates/nimble/profile/banner" model="[user:user, profile:profile, clear:false]" />
          </td>
          </tr>
          </tbody>
        </table>
      </div>

      <div class="overview">
        <table>
          <tbody>
            <tr>
              <th>Username</th>
              <td>${user.username.encodeAsHTML()}</td>
            </tr>

          <g:if test="${user.profile.nickName}">
            <tr>
              <th>Nick Name</th>
              <td>${user.profile.nickName.encodeAsHTML()}</td>
            </tr>
          </g:if>

          <g:if test="${user.profile.email}">
            <tr>
              <th>Email Address</th>
              <td><a href="mailto:${user.profile.email.encodeAsHTML()}" class="icon icon_email">${user.profile.email.encodeAsHTML()}</a></td>
            </tr>
          </g:if>

          </tbody>
        </table>
        <g:link controller="profile" action="show" id="${user.id}">View full profile</g:link>
      </div>

    </div>