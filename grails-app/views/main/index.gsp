<%@ page import="grails.plugin.nimble.core.AdminsService" %>

<html>
  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title>Welcome to Nimble!</title>
  </head>

<body>

    <div class="welcome cleanlist">

      <g:if test="${user.profile?.fullName?.length() > 0}">
        <h1><span class="userhighlight">${user.profile.fullName.encodeAsHTML()}</span>, Welcome to Nimble!</h1>
      </g:if>
      <g:else>
        <h1><span class="userhighlight"><n:photo id="${user.id}" size="50"/>${user.username.encodeAsHTML()}</span>, Welcome to Nimble!</h1>
      </g:else>

      <p>
        Nimble is an easy to use application base that takes advantage of Java, Groovy and the Grails web framework. Nimble takes care of all the common
        bits a web based application needs to be useful, saving developers months of work.
      </p>
      <p>
        A Nimble powered application has all the following features out of the box and it works with any existing
        Java libraries you might have as well..
      </p>
      <ul>
        <li>Extensive support for the Social Web. Allow users to login using Facebook, OpenID and others</li>
        <li>A full user management layer including user self management and administrative management</li>
        <li>
          A fine grained access control engine to allow implementation of even the most complex of authorization policies including:
          <ul>
            <li>Roles</li>
            <li>Groups</li>
            <li>Permissions</li>
          </ul>
        </li>
        <li>A wonderful set of professionally designed UI's to manage all Nimble features</li>
        <li>A range of Javascript components based on <a href="http://jquery.com">JQuery</a></li>
        <li>An extensive set of CSS classes for use with Nimble powered applications including over 1000 icons and integration with Sass</li>
      </ul>
      <p>

      </p>
      <p>
        Below are the operations currently available to your account
      </p>
      <ul>
        <n:hasRole name="${AdminsService.ADMIN_ROLE}">
          <li>
          <g:link controller="admins" action="index" class="icon icon_user_go">Administer Application</g:link>
          </li>
        </n:hasRole>
        <li><g:link controller="auth" action="logout" class="icon icon_cross">Logout</g:link></li>
      </ul>

    </div>

</body>
</html>
