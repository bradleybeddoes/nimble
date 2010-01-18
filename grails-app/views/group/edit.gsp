<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.group.edit.title" /></title>
</head>

<body>

  <h2><g:message code="nimble.view.group.edit.heading" args="[group.name.encodeAsHTML()]"/></h2>

  <p>
    <g:message code="nimble.view.group.edit.descriptive" />
  </p>

  <n:errors bean="${group}"/>

  <g:form method="post" action="update">
	    <input type="hidden" name="id" value="${group?.id.encodeAsHTML()}"/>
	
	    <table class="datatable">
	      <tbody>
	
	      <tr>
	        <th>
	          <label for="name"><g:message code="nimble.label.name" /></label>
	        </th>
	        <td valign="top" class="value ">
	          <input type="text" id="name" name="name" value="${fieldValue(bean: group, field: 'name')}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
	        </td>
	      </tr>
	
	      <tr>
	        <th>
	          <label for="description"><g:message code="nimble.label.description" /></label>
	        </th>
	        <td valign="top" class="value ">
	          <input type="text" id="description" name="description" value="${fieldValue(bean: group, field: 'description')}" class="easyinput"/>  <span class="icon icon_bullet_green">&nbsp;</span>
	        </td>
	      </tr>
	
	      <tr>
	        <td/>
	        <td>
	          <div>
	            <button class="button icon icon_group_go" type="submit"><g:message code="nimble.link.updategroup" /></button>
	            <g:link action="show" id="${group.id}" class="button icon icon_cross"><g:message code="nimble.link.cancel" /></g:link>
	          </div>
	        </td>
	      </tr>
	      </tbody>
	    </table>
	    
  </g:form>

</body>
</html>
