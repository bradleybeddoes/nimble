<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <meta name="layout" content="app"/>
  <title>Forgotten Password | External Account</title>
</head>

<body>

<div class="container">
  <div class="accountinformation">
    <h2>Forgotten Password - External Account</h2>

    <p>Unfortunately your account is managed by an external provider and you can't reset your password here.</p>

    <p>Please use the details below to make changes to your account or modify your login details</p>

    <g:if test="${user.federated}">
      <div class="details">
        <table>
          <tbody>

          <tr class="prop">
            <td valign="top" class="name">Provider</td>
            <td valign="top">
              <img src="${resource(dir: "images", file: user.federationProvider.details?.logoSmall)}" alt="${user.federationProvider.details?.displayName}"/>
              <a href="${user.federationProvider.details?.url?.location}" alt="${user.federationProvider.details?.url?.altText}">${user.federationProvider.details?.displayName}</a>
            </td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name">Description</td>
            <td>${user.federationProvider.details?.description}</td>
          </tr>

          </tbody>
        </table>
      </div>
    </g:if>

    <g:else>
      <div class="details">
        <table>
          <tbody>

          <tr class="prop">
            <td valign="top" class="name">Provider</td>
            <td valign="top">
              <img src="${grailsApplication.config.nimble.organization.logosmall}" alt="${grailsApplication.config.nimble.organization.alttext}"/>
              <a href="${grailsApplication.config.nimble.organization.url}" alt="${grailsApplication.config.nimble.organization.displayname}">${grailsApplication.config.nimble.organization.url}</a>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"></td>
            <td valign="top">
              <a href="${grailsApplication.config.nimble.organization.accountmanagement.url}" alt="${grailsApplication.config.nimble.organization.displayname}">Manage Account</a>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">Description</td>
            <td valign="top">
              ${grailsApplication.config.nimble.organization.description}
            </td>
          </tr>

          </tbody>
        </table>
      </div>
    </g:else>
  </div>
</div>

</body>
</html>