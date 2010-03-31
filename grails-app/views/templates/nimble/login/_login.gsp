<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.login}"/>
  <title><g:message code="nimble.template.login.title" /></title>
  <nh:nimbleui />
  <nh:login />
</head>

<body>

<g:if test="${facebook || openid}">
  <div class="container">

    <div class="login">

      <div class="flash">
        <n:flashembed/>
      </div>

      <div class="logininteraction">
        <g:if test="${local}">
          <div id="local" class="loginmethod">
            <h2><g:message code="nimble.template.login.local.heading" /></h2>

            <g:form id="locallogin" action="signin" name="signin">
              <fieldset>
                <label for="username" class="append-1"><g:message code="nimble.label.username" /></label>
                <input id="username" type="text" name="username" class="title"/>

                <label for="password" class="append-1"><g:message code="nimble.label.password" /></label>
                <input id="password" type="password" name="password" class="title"/>
              </fieldset>

              <fieldset>
                <g:checkBox id="rememberme" name="rememberme"/>
                <label><g:message code="nimble.label.rememberme" /></label>
              </fieldset>

              <fieldset class="loginbuttons">
                <button type="submit" class="button darkbutton icon icon_user_green"><g:message code="nimble.link.login.basic" /></button>
              </fieldset>

            </g:form>

            <div class="accountoptions">
              <g:link controller="account" action="forgottenpassword" class="textlink icon icon_flag_purple"><g:message code="nimble.link.forgottenpassword" /></g:link>
              <g:if test="${registration}">
                <g:link controller="account" action="createuser" class="textlink icon icon_user_go"><g:message code="nimble.link.newuser" /></g:link>
              </g:if>
            </div>
          </div>
        </g:if>

        <g:if test="${facebook}">
          <div id="facebook" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.facebook.heading" /></h2>
            <p>
              <n:socialimg name="facebook" size="64" alt="Login using Facebook"/>
			  <g:message code="nimble.template.login.facebook.descriptive" />
            </p>
            <div id="loginfacebookcontinue">
              <a href="${createLink(controller: "auth", action: "facebook")}" class="button icon icon_user_green"><g:message code="nimble.link.login.basic" /></a>
            </div>

            <div id="loginfacebookenable">
              <fb:login-button autologoutlink="true" size="large" background="white" length="long" onlogin="enableFacebookContinue();" onlogout="disableFacebookContinue();"></fb:login-button>
            </div>
          </div>
        </g:if>

        <g:if test="${openid}">
          <div id="google" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.google.heading" /></h2>
            <p>
              <n:socialimg name="google" size="64" alt="Login using Google"/>
			  <g:message code="nimble.template.login.google.descriptive" />
            </p>
            <p>
              <a href="${createLink(controller: "auth", action: "googlereq")}" class="button icon icon_user_green"><g:message code="nimble.link.login.basic" /></a>
            </p>
          </div>

          <div id="yahoo" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.yahoo.heading" /></h2>
            <p>
              <n:socialimg name="yahoo" size="64" alt="Login using Yahoo"/>
			  <g:message code="nimble.template.login.yahoo.descriptive" />
            </p>
            <p>
              <a href="${createLink(controller: "auth", action: "yahooreq")}" class="button icon icon_user_green"><g:message code="nimble.link.login.basic" /></a>
            </p>
          </div>

          <div id="openid" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.openid.heading" /></h2>
            <p>
              <n:socialimg name="openid" size="64" alt="openid"/>
			  <g:message code="nimble.template.login.openid.descriptive" />
            </p>
            <g:form controller="auth" action="openidreq">
              <strong><g:message code="nimble.template.login.openid.identifier" /></strong>
              <input id="openiduri" type="text" name="openiduri" class="easyinput" value="http://"/>
              <button type="submit" class="button icon icon_user_green"><g:message code="nimble.link.login.basic" /></button>
            </g:form>
          </div>

          <div id="blogger" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.blogger.heading" /></h2>
            <p>
              <n:socialimg name="blogger" size="64" alt="Login using Blogger"/>
              <g:message code="nimble.template.login.blogger.descriptive" />
            </p>
            <g:form controller="auth" action="bloggerreq">
              <label for="openiduri" class="append-1"><g:message code="nimble.template.login.blogger.identifier" /></label>
              <input id="openiduri" type="text" name="openiduri" class="easyinput"/>
              <fieldset>
                <button type="submit" class="button darkbutton icon icon_user_green"><g:message code="nimble.link.login.basic" /></button>
              </fieldset>
            </g:form>
          </div>

          <div id="wordpress" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.wordpress.heading" /></h2>
            <p>
              <n:socialimg name="wordpress" size="64" alt="Login using Wordpress"/>
              <g:message code="nimble.template.login.wordpress.descriptive" />
            </p>
            <g:form controller="auth" action="wordpressreq">
              <label for="openiduri" class="append-1"><g:message code="nimble.template.login.wordpress.identifier" /></label>
              <input id="openiduri" type="text" name="openiduri" class="easyinput"/>
              <fieldset>
                <button type="submit" class="button darkbutton icon icon_user_green"><g:message code="nimble.link.login.basic" /></button>
              </fieldset>
            </g:form>
          </div>

          <div id="technorati" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.technorati.heading" /></h2>
            <p>
              <n:socialimg name="technorati" size="64" alt="Login using Technorati"/>
              <g:message code="nimble.template.login.technorati.descriptive" />
            </p>
            <g:form controller="auth" action="technoratireq">
              <label for="openiduri" class="append-1"><g:message code="nimble.template.login.technorati.identifier" /></label>
              <input id="openiduri" type="text" name="technoratiusername" class="easyinput"/>
              <fieldset>
                <button type="submit" class="button darkbutton icon icon_user_green"><g:message code="nimble.link.login.basic" /></button>
              </fieldset>
            </g:form>
          </div>

          <div id="flickr" class="loginmethod externalloginmethod">
            <h2><g:message code="nimble.template.login.flickr.heading" /></h2>
            <p>
              <n:socialimg name="flickr" size="64" alt="Login using Flickr"/>
              <g:message code="nimble.template.login.flickr.descriptive" />
            </p>
            <p>
              <a href="${createLink(controller: "auth", action: "flickreq")}" class="button icon icon_user_green"><g:message code="nimble.link.login.basic" /></a>
            </p>
          </div>
        </g:if>
      </div>


      <div class="loginchoices">
        <g:if test="${local}">
          <h2><a href="#" onClick="changeLogin('local');" class="icon icon_user_go"><g:message code="nimble.template.login.local.heading" /></a></h2>
        </g:if>
        <h2><g:message code="nimble.template.login.external.heading" /></h2>
        <table>
          <tbody>
          <tr>
            <td>
              <g:if test="${openid}">
                <a href="#" class="" onClick="changeLogin('openid');"><n:socialimg name="openid" size="64" alt="Login using OpenID"/><g:message code="nimble.label.openid" /></a>
              </g:if>
            </td>
            <td>
              <g:if test="${facebook}">
                <a href="#" class="" onClick="changeLogin('facebook');"><n:socialimg name="facebook" size="64" alt="Login using Facebook"/><g:message code="nimble.label.facebook" /></a>
              </g:if>
            </td>
          </tr>
          <g:if test="${openid}">
            <tr>
              <td>
                <a class="" onClick="changeLogin('google');"><n:socialimg name="google" size="64" alt="Login using Google"/><g:message code="nimble.label.google" /></a>
              </td>
              <td>
                <a class="" onClick="changeLogin('yahoo');"><n:socialimg name="yahoo" size="64" alt="Login using Yahoo!"/><g:message code="nimble.label.yahoo" /></a>
              </td>
            </tr>
            <tr>
              <td>
                <a href="#" class="" onClick="changeLogin('blogger');"><n:socialimg name="blogger" size="64" alt="Login using Blogger"/><g:message code="nimble.label.blogger" /></a>
              </td>
              <td>
                <a href="#" class="" onClick="changeLogin('wordpress');"><n:socialimg name="wordpress" size="64" alt="Login using Wordpress"/><g:message code="nimble.label.wordpress" /></a>
              </td>
            </tr>
            <tr>
              <td>
                <a href="#" class="" onClick="changeLogin('technorati');"><n:socialimg name="technorati" size="64" alt="Login using Technorati"/><g:message code="nimble.label.technorati" /></a>
              </td>
              <td>
                <a href="#" class="" onClick="changeLogin('flickr');"><n:socialimg name="flickr" size="64" alt="Login using Flickr"/><g:message code="nimble.label.flickr" /></a>
              </td>
            </tr>
          </g:if>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</g:if>
<g:else>
  <div class="container">
    <div class="login">

      <div class="flash">
        <n:flashembed/>
      </div>

      <div id="local" class="localonlymethod">
        <h2>Login</h2>

        <g:form action="signin" name="signin">
          <input type="hidden" name="targetUri" value="${targetUri}"/>

          <fieldset>
            <label for="username" class="append-1"><g:message code="nimble.label.username" /></label>
            <input id="username" type="text" name="username" class="title"/>

            <label for="password" class="append-1"><g:message code="nimble.label.password" /></label>
            <input id="password" type="password" name="password" class="title"/>
          </fieldset>

          <fieldset>
            <g:checkBox id="rememberme" name="rememberme"/>
            <label><g:message code="nimble.label.rememberme" /></label>
          </fieldset>

          <fieldset class="loginbuttons">
            <button type="submit" class="button darkbutton icon icon_user_green"><g:message code="nimble.link.login.basic" /></button>
          </fieldset>

        </g:form>

        <div class="accountoptions">
          <g:link controller="account" action="forgottenpassword" class="textlink icon icon_flag_purple"><g:message code="nimble.link.forgottenpassword" /></g:link>
          <g:if test="${registration}">
            <g:link controller="account" action="createuser" class="textlink icon icon_user_go"><g:message code="nimble.link.newuser" /></g:link>
          </g:if>
        </div>
      </div>

    </div>
  </div>

</g:else>

<n:facebookConnect/>

</body>
