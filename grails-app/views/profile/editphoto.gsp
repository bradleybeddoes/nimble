<html>

  <head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
    <title>Profile | Edit Photo</title>

  <n:growl/>
  <n:flashgrowl/>

  <link rel="stylesheet" href="${resource(dir: pluginContextPath, file: '/css/profile.css')}"/>

  <script type="text/javascript">
    $(function() {
      <g:if test="${!user.profile.gravatar}">
          $("#disablegravatar").hide();
      </g:if>
      <g:else>
          $("#enablegravatar").hide();
      </g:else>
    }
    )

    function enableGravatar() {
      var dataString = $("#enablegravatar").serialize();
      $.ajax({
        type: "POST",
        url: "${createLink(action:'enablegravatar')}",
        data: dataString,
        success: function(res) {
          $("#profilephoto").hide().empty().append(res).show('blind');
          $("#enablegravatar").hide();
          $("#disablegravatar").show();
          growl("success", "Your profile photo is now linked to Gravatar");
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl("error", "There was an error linking Gravatar to your account");
        }
      });
    }

    function disableGravatar() {
      var dataString = $("#disablegravatar").serialize();
      $.ajax({
        type: "POST",
        url: "${createLink(action:'disablegravatar')}",
        data: dataString,
        success: function(res) {
          $("#profilephoto").hide().empty().append(res).show('blind');
          $("#disablegravatar").hide();
          $("#enablegravatar").show();
          growl("success", "Your profile photo is no longer linked to Gravatar");
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl("error", "There was an error unlinking Gravatar from your account");
        }
      });
    }
  </script>
</head>

<body>
  <div class="container">
    <div class="profile">

      <g:render template="/templates/nimble/profile/left" model="[user:user, profile:user.profile]" />

      <div class="main edit">
        <g:render template="/templates/nimble/profile/banner" model="[user:user, profile:user.profile]" />

        <div class="section">
          <h4 class="icon icon_image">Profile photo</h4>
          <p>
            This photo will be used to identify you when you undertake certain actions such as
            leaving a comment or creating new content.
          </p>
          <p>
            For the best results you should use an image 200px wide by 200px high.
          </p>

          <n:errors bean="${user.profile}"/>

          <div class="add">
            <h5>Upload your photo</h5>
            <g:form name="uploadphoto" action="uploadphoto" method="post" enctype="multipart/form-data">
              <g:hiddenField name="id" value="${user.id}"/>
              <input type="file" name="photo" id="photo" class="easyinput"/>
              <br/><br/>
              <a href="#" class="button icon icon_image_add" onclick="if($('#photo').val() != '') document.uploadphoto.submit();">Upload</a>
            </g:form>
          </div>
        </div>

        <div class="section">
          <h4 class="icon icon_image">Gravatar</h4>

          <g:form name="enablegravatar" action="enablegravatar">
            <p>
              <a href="http://www.gravatar.com">Gravatar</a> is an online service that allow you to use the same photo or 'avatar' across multiple websites.
            </p>
            <p>
              If you'd like to use your Gravatar photo you can enable this below. Please ensure the email address associated with your profile is your Gravatar listed email.
            </p>
            <g:hiddenField name="id" value="${user.id}"/>
            <a href="#" class="button icon icon_image_add" onclick="enableGravatar(); return false;">I'd like to use Gravatar!</a>
          </g:form>

          <g:form name="disablegravatar" action="disablegravatar">
            <p>
              You're currently using a Gravatar photo. You may disable it below.
            </p>
            <g:hiddenField name="id" value="${user.id}"/>
            <a href="#" class="button icon icon_image_add" onclick="disableGravatar(); return false;">Disable Gravatar</a>
          </g:form>

        </div>

      </div>

    </div>
  </div>
</body>

</html>



