 <script type="text/javascript">
    $(function() {

      $("#name").blur(function () {
        var dataString = 'name=' + $("#name").val();
        $.ajax({
          type: "POST",
          url: "${createLink(action:'validname')}",
          data: dataString,
          success: function(res) {
            growl('flaggreen', 'This name is available', 3000);
            $("#name").css({'background': '#fff', 'color':'#000'});
            $("#nameavailable").addClass('icon');
            $("#nameavailable").addClass('icon_flag_green');
            $("#nameavailable").removeClass('icon_flag_red');
          },
          error: function (xhr, ajaxOptions, thrownError) {
            growl('flagred', 'Ooops this name is being used already or is invalid');
            $("#name").css({'color': '#9c3333'});
            $("#nameavailable").addClass('icon');
            $("#nameavailable").addClass('icon_flag_red');
            $("#nameavailable").removeClass('icon_flag_green');
          }
        });
      })

    });
  </script>