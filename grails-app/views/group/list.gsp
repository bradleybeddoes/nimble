<%@ page import="intient.nimble.domain.Group" %>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="admin"/>
  <title>Group List | Nimble</title>
</head>
<body>

<div class="container">
  <h2>Group List</h2>
  <p>
    The following is the current list of system groups.
  </p>

  <div>
    <table class="grouplist">
      <thead>
      <tr>
        <g:sortableColumn property="name" title="Name" class="first icon icon_arrow_refresh"/>
        <th class="">Description</th>
        <th class="last"/>
      </tr>
      </thead>
      <tbody>
      <g:each in="${groups}" status="i" var="group">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

          <td>${fieldValue(bean: group, field: 'name')}</td>
          <td>${fieldValue(bean: group, field: 'description')}</td>
          <td><g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go">View Group</g:link>

        </tr>
      </g:each>
      </tbody>
    </table>
  </div>
  <div class="paginateButtons">
    <g:paginate total="${Group.count().encodeAsHTML()}"/>
  </div>

</div>

</body>

