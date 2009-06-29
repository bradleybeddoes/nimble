
<%@ page import="intient.nimble.domain.Gender" %>

<html>

  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title>Profile | Edit Account Details</title>

  <n:growl/>
  <n:flashgrowl/>

  <link rel="stylesheet" href="${createLinkTo(dir: pluginContextPath, file: '/css/profile.css')}"/>

</head>

<body>
  <div class="container">
    <div class="profile">

      <g:render template="/templates/nimble/profile/left" model="[user:user, profile:user.profile]" />

      <div class="main edit">
        <g:render template="/templates/nimble/profile/banner" model="[user:user, profile:user.profile]" />

        <n:errors bean="${user.profile}"/>

        <div class="section">
          <h4 class="icon icon_user">Account Details</h4>
          <p>You can edit your common profile details below. Information provided here is available to view by other users</p>
          <g:form action="updateaccount">
            <g:hiddenField name="id" value="${user.id}"/>
            <table>
              <tbody>
                <tr>
                  <th>Full Name</th>
                  <td>
                    <input type="text" size="40" id="fullName" name="fullName" value="${user?.profile?.fullName?.encodeAsHTML()}" class="easyinput"/>
                  </td>
                </tr>

                <tr>
                  <th>Nick Name</th>
                  <td>
                    <input type="text" size="40" id="nickName" name="nickName" value="${user?.profile?.nickName?.encodeAsHTML()}" class="easyinput"/>
                  </td>
                </tr>

                <tr>
                  <th>Bio</th>
                  <td>
                    <input type="text" size="40" id="bio" name="bio" value="${user?.profile?.bio?.encodeAsHTML()}" class="easyinput"/>
                  </td>
                </tr>

                <tr>
                  <th>Date of birth</th>
                  <td>
              <g:if test="${user.profile.dob != null}">
                Save your date of birth? <g:checkBox name="storeDOB" value="${true}" checked="${true}"/>
              </g:if>
              <g:else>
                Save your date of birth? <g:checkBox name="storeDOB" value="${true}" checked="${false}"/>
              </g:else>
              <br/>
              <g:datePicker name="dob" value="${user?.profile?.dob}" precision="day" noSelection="[none:' ']" class="easyinput"/>
              </td>
              </tr>

              <tr>
                <th>Gender</th>
                <td>
              <g:select name="gender" from="${Gender.values()}" value="${user?.profile?.gender?.encodeAsHTML()}" class="easyinput"/>
              </td>
              </tr>

              <tr>
                <td/>
                <td>
                  <button class="button icon icon_user_go" type="submit">Save</button>
                </td>
              </tr>
              </tbody>
            </table>
          </g:form>
        </div>
      </div>
    </div>
  </div>
</body>

</html>
