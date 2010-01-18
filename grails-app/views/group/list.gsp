<%@ page import="grails.plugin.nimble.core.Group" %>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.group.list.title" /></title>
</head>
<body>

  <h2><g:message code="nimble.view.group.list.heading" /></h2>
  <p>
    <g:message code="nimble.view.group.edit.descriptive" />
  </p>

  <div>
    <table class="grouplist">
      <thead>
      <tr>
        <g:sortableColumn property="name" titleKey="nimble.label.name" class="first icon icon_arrow_refresh"/>
        <th class=""><g:message code="nimble.label.description" /></th>
        <th class="last"/>
      </tr>
      </thead>
      <tbody>
      <g:each in="${groups}" status="i" var="group">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

          <td>${fieldValue(bean: group, field: 'name')}</td>
          <td>${fieldValue(bean: group, field: 'description')}</td>
          <td><g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="button icon icon_group_go"><g:message code="nimble.link.view" /></g:link>

        </tr>
      </g:each>
      </tbody>
    </table>
  </div>
  <div class="paginateButtons">
    <g:paginate total="${Group.count().encodeAsHTML()}"/>
  </div>

</body>

