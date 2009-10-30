<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title>Edit Group</title>
</head>

<body>

<div class="container">
  <h2>Edit Group - ${group.name.encodeAsHTML()}</h2>

  <p>
    You can modify values associated with this group below. Fields with bullets are required.
  </p>

  <n:errors bean="${group}"/>

  <g:form method="post" action="update">
	    <input type="hidden" name="id" value="${group?.id.encodeAsHTML()}"/>
	
	    <table class="datatable">
	      <tbody>
	
	      <tr>
	        <th>
	          <label for="name">Name</label>
	        </th>
	        <td valign="top" class="value ">
	          <input type="text" id="name" name="name" value="${fieldValue(bean: group, field: 'name')}" class="easyinput"/> <span class="icon icon_bullet_green">&nbsp;</span>
	        </td>
	      </tr>
	
	      <tr>
	        <th>
	          <label for="description">Description</label>
	        </th>
	        <td valign="top" class="value ">
	          <input type="text" id="description" name="description" value="${fieldValue(bean: group, field: 'description')}" class="easyinput"/>  <span class="icon icon_bullet_green">&nbsp;</span>
	        </td>
	      </tr>
	
	      <tr>
	        <td/>
	        <td>
	          <div>
	            <button class="button icon icon_group_go" type="submit">Update Group</button>
	            <a id="deleteconfirmbtn" rel="deleteconfirm" class="button icon icon_group_delete">Delete Group</a>
	            <g:link action="show" id="${group.id}" class="button icon icon_cross">Cancel</g:link>
	          </div>
	        </td>
	      </tr>
	      </tbody>
	    </table>
	    
  </g:form>

  <g:render template="/templates/admin/deletegroupconfirm"/>

</div>

</body>
</html>
