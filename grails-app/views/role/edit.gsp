<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.role.edit.title" /></title>
</head>
<body>

	<h2><g:message code="nimble.view.role.edit.heading" args="[role.name.encodeAsHTML()]" /></h2>
	
	<p>
	  <g:message code="nimble.view.role.edit.descriptive" />
	</p>
	
	<n:errors bean="${role}"/>
	
	<g:form method="post" name="editRole" action="update">
	  <input type="hidden" name="id" value="${role?.id.encodeAsHTML()}"/>
	
	  <table>
	    <tbody>
	
	    <tr>
	      <td valign="top" class="name">
	        <label for="name"><g:message code="nimble.label.name" /></label>
	      </td>
	      <td valign="top" class="value ">
	        <input type="text" id="name" name="name" value="${fieldValue(bean: role, field: 'name')}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
	      </td>
	    </tr>
	
	    <tr>
	      <td valign="top" class="name">
	        <label for="description"><g:message code="nimble.label.description" /></label>
	      </td>
	      <td valign="top" class="value ">
	        <input type="text" id="description" name="description" value="${fieldValue(bean: role, field: 'description')}" class="easyinput"/>  <span class="icon icon_bullet_green">&nbsp;</span>
	      </td>
	    </tr>
	    
	    <tr>
	        <td/>
	        <td>
	          <div>
	          	<button class="button icon icon_cog_go" type="submit"><g:message code="nimble.button.updaterole" /></button>
	    		<g:link action="show" id="${role.id}" class="button icon icon_cross"><g:message code="nimble.link.cancel" /></g:link>
	          </div>
	        </td>
	      </tr>
	
	    </tbody>
	  </table>
	
	</g:form>

</body>
</html>
