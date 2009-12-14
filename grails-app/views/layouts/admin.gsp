<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
  "http://www.w3.org/TR/html4/strict.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Nimble | <g:layoutTitle default="Grails"/></title>

  	<nh:nimblecore/>
    <nh:nimbleui/>
    <nh:admin/>

    <g:layoutHead/>
</head>

<body>

  <div id="doc">
    <div id="hd">
      <g:render template='/templates/nimble/navigation/topnavigation'/>
    </div>
    <div id="bd">
      	<div class="container">
	        <div class="localnavigation">
				<h3>Access Control Navigation</h3>
				<ul>
					<li>
						<g:link controller="user" action="list">Users</g:link>
					</li>
						<g:if test="${user}">
						    <ul>
								<li>
								<g:link controller="user" action="show" id="${user.id}">${user.profile.fullName}</g:link>
							
									<ul>
										<li>
									        <g:link controller="user" action="edit" id="${user.id}">Edit</g:link>
									      </li>
									      <li>
									          <g:link controller="user" action="changepassword" id="${user.id}">Change Password</g:link>
									      </li>

									      <li id="disableuser">
									        <a id="disableuserbtn">Disable User</a>
									      </li>
									      <li id="enableuser">
									        <a id="enableuserbtn">Enable User</a>
									      </li>

									      <li id="disableuserapi">
									        <a id="disableuserapibtn">Disable API</a>
									      </li>
									      <li id="enableuserapi">
									        <a id="enableuserapibtn">Enable API</a>
									      </li>
									  </ul>
								</li>
							</ul>
						</g:if>
					<li>
						<g:link controller="role" action="list">Roles</g:link>
					</li>
					<li>
						<g:link controller="group" action="list">Groups</g:link>
					</li>
					<li>
						<g:link controller="admins" action="index">Admins</g:link>
					</li>
				</ul>
			</div>
			<div class="content">
	      		<g:layoutBody/>
			</div>
		</div>
    </div>
    <div id="ft">

    </div>
  </div>

<n:sessionterminated/>


</body>

</html>