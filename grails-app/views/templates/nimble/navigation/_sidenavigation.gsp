<div class="localnavigation">
	<h3>Access Control Navigation</h3>
	<ul>
		<li>
			<g:link controller="user" action="list">Users</g:link>
		</li>
			<g:if test="${controllerName == 'user' && actionName == 'list'}">
				<ul>
					<li>
						<g:link controller="user" action="create">Create User</g:link>
					</li>
				</ul>
			</g:if>
			<g:if test="${controllerName == 'user' && actionName in ['show', 'edit', 'changepassword']}">
			    <ul>
					<li>
						<g:if test="${user?.profile?.fullName}">
							<g:link controller="user" action="show" id="${user.id}">${user.profile.fullName?.encodeAsHTML()}</g:link>
						</g:if>
						<g:else>
							<g:link controller="user" action="show" id="${user.id}">${user.username?.encodeAsHTML()}</g:link>
						</g:else>
				
						<ul>
							<li>
						        <g:link controller="user" action="edit" id="${user.id}">Edit</g:link>
						      </li>
						      <li>
						          <g:link controller="user" action="changepassword" id="${user.id}">Change Password</g:link>
						      </li>
							  <g:if test="${actionName in ['show']}">
						      	<li id="disableuser">
							        <a onClick="disableUser('${user.id}'); return false;">Disable Account</a>
							      </li>
							      <li id="enableuser">
							        <a onClick="enableUser('${user.id}'); return false;">Enable Account</a>
							      </li>

							      <li id="disableuserapi">
							        <a onClick="disableAPI('${user.id}'); return false;">Disable API</a>
							      </li>
							      <li id="enableuserapi">
							        <a onClick="enableAPI('${user.id}'); return false;">Enable API</a>
							      </li>
								</g:if>
						  </ul>
					</li>
				</ul>
			</g:if>
		<li>
			<g:link controller="role" action="list">Roles</g:link>
			<g:if test="${controllerName == 'role' && && actionName in ['list', 'create']}">
				<ul>
					<li>
						<g:link controller="role" action="create">Create Role</g:link>
					</li>
				</ul>
			</g:if>
			<g:if test="${controllerName == 'role' && actionName in ['show', 'edit']}">
			    <ul>
					<li>
						<g:link controller="role" action="show" id="${role.id}">${role.name?.encodeAsHTML()}</g:link>
				
						<g:if test="${!role.protect}">
						<ul>
							<li>
						        <g:link controller="role" action="edit" id="${role.id}">Edit</g:link>
						    </li>
							<li>
								<n:confirmaction action="document.deleterole.submit();" title="${message(code: 'delete.confirm.title')}" msg="${message(code: 'role.delete.confirm.msg')}" accept="${message(code: 'default.button.accept.label')}" cancel="${message(code: 'default.button.cancel.label')}" class=""><g:message code="role.delete.label" /></n:confirmaction>								
							</li>
						</ul>
						</g:if>
					</li>
				</ul>
			</g:if>
		</li>
		<li>
			<g:link controller="group" action="list">Groups</g:link>
			<g:if test="${controllerName == 'group' && actionName in ['list', 'create']}">
				<ul>
					<li>
						<g:link controller="group" action="create">Create Group</g:link>
					</li>
				</ul>
			</g:if>
			<g:if test="${controllerName == 'group' && actionName in ['show', 'edit']}">
			    <ul>
					<li>
						<g:link controller="group" action="show" id="${group.id}">${group.name?.encodeAsHTML()}</g:link>
						<g:if test="${!group.protect}">
						<ul>
							<li>
						        <g:link controller="group" action="edit" id="${group.id}">Edit</g:link>
						    </li>
							<li>
								<n:confirmaction action="document.deletegroup.submit();" title="${message(code: 'delete.confirm.title')}" msg="${message(code: 'group.delete.confirm.msg')}" accept="${message(code: 'default.button.accept.label')}" cancel="${message(code: 'default.button.cancel.label')}" class=""><g:message code="group.delete.label" /></n:confirmaction>
							</li>			
						</ul>
						</g:if>
					</li>
				</ul>
			</g:if>
		</li>
		<li>
			<g:link controller="admins" action="index">Admins</g:link>
		</li>
	</ul>
</div>