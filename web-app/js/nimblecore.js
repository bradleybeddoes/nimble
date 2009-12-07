// General Purpose

$(function() {

  function verifyUnique(elem, elemstatus, endpoint) {
    var dataString = 'val=' + $(elem).val();
    $.ajax({
      type: "POST",
      url: endpoint,
      data: dataString,
      success: function(res) {
        growl('flaggreen', 'This name is available', 3000);
        $(elem).css({'background': '#fff', 'color':'#000'});
        $(elemstatus).addClass('icon');
        $(elemstatus).addClass('icon_flag_green');
        $(elemstatus).removeClass('icon_flag_red');
      },
      error: function (xhr, ajaxOptions, thrownError) {
        growl('flagred', 'Ooops this name is being used already or is invalid');
        $("#name").css({'color': '#9c3333'});
        $("#nameavailable").addClass('icon');
        $("#nameavailable").addClass('icon_flag_red');
        $("#nameavailable").removeClass('icon_flag_green');
      }
    });
}

// Users
function enableUser(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: enableUserEndpoint,
    data: dataString,
    success: function(res) {
      $("#enableuser").hide();
      $("#enableduser").hide();
      $("#disableuser").show();
      $("#disableduser").show();
      growl('success', 'Account enabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to enable this account');
    }
  });
}

function disableUser(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: disableUserEndpoint,
    data: dataString,
    success: function(res) {
      $("#disableuser").hide();
      $("#disableduser").hide();
      $("#enableuser").show();
      $("#enableduser").show();
      growl('success', 'Account disabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to disable this account');
    }
  });
}

function enableAPI(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: enableAPIEndpoint,
    data: dataString,
    success: function(res) {
      $("#disabledapi").hide();
      $("#enabledapi").show();

      $("#disableuserapi").show();
      $("#enableuserapi").hide();
      growl('success', 'Remote API access enabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to enable remote api access account');
    }
  });
}

function disableAPI(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: disableAPIEndpoint,
    data: dataString,
    success: function(res) {
      $("#disabledapi").show();
      $("#enabledapi").hide();

      $("#disableuserapi").hide();
      $("#enableuserapi").show();
      growl('success', 'Remote API access disabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to disable remote api access for this account');
    }
  });
}