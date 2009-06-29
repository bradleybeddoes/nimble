<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Edit User</title>
</head>

<body>

<div class="container">
  <h2>Editing account ${user.username}</h2>

  <p>
    You can edit the details of this account below. Fields with bullets are required. <br/>
  </p>

  <n:errors bean="${user}"/>

  <g:form action="update" class="editaccount">
    <input type="hidden" name="id" value="${user.id}"/>
    <input type="hidden" name="version" value="${user.version}"/>

    <table>
      <tbody>

      <tr>
        <th><label for="username">Login Name</label></th>
        <td class="value">
          <input type="text" id="username" name="username" value="${user.username?.encodeAsHTML()}" class="easyinput"/>  <span class="icon icon_bullet_green">&nbsp;</span>
        </td>
      </tr>

      <tr>
        <th>External Account</th>
        <td>
          <g:if test="${user.external}">
            <input type="radio" name="external" value="true" checked="true"/> True
            <input type="radio" name="external" value="false"/> False
          </g:if>
          <g:else>
            <input type="radio" name="external" value="true"/> True
            <input type="radio" name="external" value="false" checked="true"/> False
          </g:else>
        </td>
      </tr>

      <tr>
        <th>Federated Account</th>
        <td>
          <g:if test="${user.federated}">
            <input type="radio" name="federated" value="true" checked="true"/> True
            <input type="radio" name="federated" value="false"/> False
          </g:if>
          <g:else>
            <input type="radio" name="federated" value="true"/> True
            <input type="radio" name="federated" value="false" checked="true"/> False
          </g:else>
        </td>
      </tr>

      <tr>
        <td/>
        <td>
          <div class="buttons">
            <button class="button icon icon_user_go" type="submit">Update User</button>
            <g:link action="show" id="${user.id}" class="button icon icon_cross">Cancel</g:link>
          </div>
        </td>
      </tr>

      </tbody>
    </table>

  </g:form>

</div>

</body>
