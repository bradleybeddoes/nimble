<div class="localnavigation">
	<h3><g:message code="nimble.template.sidenavigation.heading" /></h3>
	<ul>
		<li>
			<g:link controller="user" action="list"><g:message code="nimble.link.users" /></g:link>
		</li>
			<g:if test="${controllerName == 'user' && actionName == 'list'}">
				<ul>
					<li>
						<g:link controller="user" action="create"><g:message code="nimble.link.createuser" /></g:link>
					</li>
				</ul>
			</g:if>
			<g:if test="${controllerName == 'user' && actionName in ['show', 'edit', 'changepassword', 'changelocalpassword']}">
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
						        <g:link controller="user" action="edit" id="${user.id}"><g:message code="nimble.link.edit" /></g:link>
						      </li>
							  <g:if test="${user.external}">
						      <li>
						          <g:link controller="user" action="changelocalpassword" id="${user.id}"><g:message code="nimble.link.changelocalpassword" /></g:link>
						      </li>
							  </g:if>
							  <g:else>
								<g:link controller="user" action="changepassword" id="${user.id}"><g:message code="nimble.link.changepassword" /></g:link>
							  </g:else>
							  <g:if test="${actionName in ['show']}">
						      	<li id="disableuser">
							        <a onClick="disableUser('${user.id}'); return false;"><g:message code="nimble.link.disableaccount" /></a>
							      </li>
							      <li id="enableuser">
							        <a onClick="enableUser('${user.id}'); return false;"><g:message code="nimble.link.enableaccount" /></a>
							      </li>

							      <li id="disableuserapi">
							        <a onClick="disableAPI('${user.id}'); return false;"><g:message code="nimble.link.disableapi" /></a>
							      </li>
							      <li id="enableuserapi">
							        <a onClick="enableAPI('${user.id}'); return false;"><g:message code="nimble.link.enableapi" /></a>
							      </li>
								</g:if>
						  </ul>
					</li>
				</ul>
			</g:if>
		<li>
			<g:link controller="role" action="list"><g:message code="nimble.link.roles" /></g:link>
			<g:if test="${controllerName == 'role' && actionName in ['list', 'create']}">
				<ul>
					<li>
						<g:link controller="role" action="create"><g:message code="nimble.link.createrole" /></g:link>
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
						        <g:link controller="role" action="edit" id="${role.id}"><g:message code="nimble.link.edit" /></g:link>
						    </li>
							<li>
								<n:confirmaction action="document.deleterole.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}" msg="${message(code: 'nimble.role.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class=""><g:message code="nimble.link.delete" /></n:confirmaction>								
							</li>
						</ul>
						</g:if>
					</li>
				</ul>
			</g:if>
		</li>
		<li>
			<g:link controller="group" action="list"><g:message code="nimble.link.groups" /></g:link>
			<g:if test="${controllerName == 'group' && actionName in ['list', 'create']}">
				<ul>
					<li>
						<g:link controller="group" action="create"><g:message code="nimble.link.creategroup" /></g:link>
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
						        <g:link controller="group" action="edit" id="${group.id}"><g:message code="nimble.link.edit" /></g:link>
						    </li>
							<li>
								<n:confirmaction action="document.deletegroup.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}" msg="${message(code: 'nimble.group.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class=""><g:message code="nimble.link.delete" /></n:confirmaction>
							</li>			
						</ul>
						</g:if>
					</li>
				</ul>
			</g:if>
		</li>
		<li>
			<g:link controller="admins" action="index"><g:message code="nimble.link.admins" /></g:link>
		</li>
	</ul>
</div>