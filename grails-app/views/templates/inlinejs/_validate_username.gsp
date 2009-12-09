    $(function() {

      $("#username").blur(function () {
        var dataString = 'username=' + $("#username").val();
        $.ajax({
          type: "POST",
          url: "${createLink(action:'validusername')}",
          data: dataString,
          success: function(res) {
            growl('flaggreen', 'This name is available', 3000);
            $("#username").css({'background': '#fff', 'color':'#000'});
            $("#usernameavailable").addClass('icon');
            $("#usernameavailable").addClass('icon_flag_green');
            $("#usernameavailable").removeClass('icon_flag_red');
          },
          error: function (xhr, ajaxOptions, thrownError) {
            growl('flagred', 'Ooops this name is being used already or is invalid');
            $("#usernameavailable").css({'color': '#9c3333'});
            $("#usernameavailable").addClass('icon');
            $("#usernameavailable").addClass('icon_flag_red');
            $("#usernameavailable").removeClass('icon_flag_green');
          }
        });
      })

    });