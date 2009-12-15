//Growl
function growl(type, msg) {
    growl(type, msg, 2000);
}

function growl(type, msg, period) {
    if (type == 'success')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_tick\'>&nbsp;</span>Success' });

    if (type == 'error')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>Error' });

    if (type == 'info')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_information\'>&nbsp;</span>Information' });

    if (type == 'help')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>Help' });

    if (type == 'flagred')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_red\'>&nbsp;</span>' });

    if (type == 'flaggreen')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_green\'>&nbsp;</span>' });

    if (type == 'flagblue')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_blue\'>&nbsp;</span>' });
}

// General
function verifyUnique(elem, elemstatus, endpoint, success, failure) {
   var dataString = 'val=' + $(elem).val();
   $.ajax({
     	type: "POST",
		url: endpoint,
		data: dataString,
		success: function(res) {
		  growl('flaggreen', success, 3000);
		  $(elem).css({'background': '#fff', 'color':'#000'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_green');
		  $(elemstatus).removeClass('icon_flag_red');
		},
		error: function (xhr, ajaxOptions, thrownError) {
		  growl('flagred', failure);
		  $(elem).css({'color': '#9c3333'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_red');
		  $(elemstatus).removeClass('icon_flag_green');
		}
	});
}

// Dialog support
$(function() {
	$("#confirmationdialog").dialog({
		bgiframe: true,
		resizable: false,
		modal: true,
		autoOpen: false,
		width: 400,
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		}
	});
});

function wasConfirmed(title, msg, accept, cancel) {
	$("#confirmationtitle").html(title);
	$("#confirmationcontent").html(msg); 
	$("#confirmaccept").html(accept);
	$("#confirmcancel").html(cancel);
	
	$("#confirmationdialog").dialog('option', 'title', title);
	$("#confirmationdialog").dialog('open');		
}

// Login
$(function() {
	$("#accountcreationpolicydialog").dialog({
		bgiframe: true,
		resizable: false,
		modal: true,
		autoOpen: false,
		title: 'Welcome!',
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		}
	});
});

function changeLogin(ident) {
  $(".flash").hide();
  $(".loginselector").removeClass("current");
  $(".loginmethod").hide();
  $("#" + ident).show("highlight");
}

function enableFacebookContinue() {
  $("#loginfacebookcontinue").show();
  $("#loginfacebookenable").hide();
}

function disableFacebookContinue() {
  $("#loginfacebookcontinue").hide();
}

// Session Termination
$(function() {
	$("#sessionterminateddialog").dialog({
		bgiframe: true,
		resizable: false,
		modal: true,
		autoOpen: false,
		title: 'Session Terminated',
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		}
	});

	$().ajaxError(function (event, xhr, ajaxOptions, thrownError) {
	  if ((xhr.status == 403) && (xhr.getResponseHeader("X-Nim-Session-Invalid") != null)) {
	    $("#sessionterminateddialog").dialog('open');
	  }
	});
});