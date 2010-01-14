<%@ page import="intient.nimble.core.UserBase" %>
<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.user.list.title" /></title>
</head>

<body>

  <h2><g:message code="nimble.view.user.list.heading" /></h2>

  <table class="userlist">
    <thead>
    <tr>
      <g:sortableColumn property="username" titleKey="nimble.label.username" class="first icon icon_arrow_refresh"/>
      <th><g:message code="nimble.label.fullname" /></th>
      <g:sortableColumn property="enabled" titleKey="nimble.label.state" class="icon icon_arrow_refresh"/>
      <th class="last">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${users}" status="i" var="user">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

        <g:if test="${user.username.length() > 50}">
        	<td>${user.username?.substring(0,50).encodeAsHTML()}...</td>
		</g:if>
		<g:else>
			<td>${user.username?.encodeAsHTML()}</td>
		</g:else>

        <g:if test="${user.profile?.fullName}">
          <td valign="top" class="value">${user.profile?.fullName?.encodeAsHTML()}</td>
        </g:if>
        <g:else>
          <td>&nbsp;</td>
        </g:else>

        <td>
          <g:if test="${user.enabled}">
            <span class="icon icon_tick">&nbsp;</span><g:message code="nimble.label.enabled" />
          </g:if>
          <g:else>
            <span class="icon icon_cross">&nbsp;</span><g:message code="nimble.label.disabled" />
          </g:else>
        </td>
        <td class="actionButtons">
          <span class="actionButton">
            <g:link action="show" id="${user.id}" class="button icon icon_user_go"><g:message code="nimble.link.view" /></g:link>
          </span>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="paginateButtons">
    <g:paginate total="${UserBase.count()}"/>
  </div>

</body>
