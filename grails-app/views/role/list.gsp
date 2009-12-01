<%@ page import="intient.nimble.core.Role" %>
<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Roles</title>
</head>

<body>

  <h2>Role List</h2>

  <table class="rolelist">
    <thead>
    <tr>
      <g:sortableColumn property="name" title="Name" class="first icon icon_arrow_refresh"/>
      <th>Description</th>
      <th class="last">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${roles}" status="i" var="role">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${role.name?.encodeAsHTML()}</td>
        <td>${role.description?.encodeAsHTML()}</td>
        <td class="actionButtons">
          <span class="actionButton">
            <g:link action="show" id="${role.id}" class="button icon icon_user_go">View Role</g:link>
          </span>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="paginateButtons">
    <g:paginate total="${Role.count()}"/>
  </div>

</body>
