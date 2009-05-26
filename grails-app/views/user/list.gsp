<%@ page import="intient.nimble.domain.User" %>
<head>
  <meta name="layout" content="admin"/>
  <title>Users</title>
</head>

<body>

<div class="container">

  <h2>User List</h2>

  <table class="userlist">
    <thead>
    <tr>
      <g:sortableColumn property="username" title="Login Name" class="first icon icon_arrow_refresh"/>
      <th>Full Name</th>
      <g:sortableColumn property="enabled" title="Status" class="icon icon_arrow_refresh"/>
      <th class="last">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${users}" status="i" var="user">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

        <td>${user.username?.encodeAsHTML()}</td>

        <g:if test="${user.profile?.fullName}">
          <td valign="top" class="value">${user.profile?.fullName?.encodeAsHTML()}</td>
        </g:if>
        <g:else>
          <td>&nbsp;</td>
        </g:else>

        <td>
          <g:if test="${user.enabled}">
            <span class="icon icon_tick">&nbsp;</span>Enabled
          </g:if>
          <g:else>
            <span class="icon icon_cross">&nbsp;</span>Disabled
          </g:else>
        </td>
        <td class="actionButtons">
          <span class="actionButton">
            <g:link action="show" id="${user.id}" class="button icon icon_user_go">View User</g:link>
          </span>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="paginateButtons">
    <g:paginate total="${User.count()}"/>
  </div>

</div>

</body>
