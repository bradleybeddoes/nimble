window.nimble = window.nimble || {};
var nimble = window.nimble;

// General
nimble.verifyUnique = function(elem, elemstatus, endpoint) {
   elem='#'+elem; elemstatus='#'+elemstatus;
   var dataString = 'val=' + $(elem).val();
   $.ajax({
     	type: "POST",
		url: endpoint,
		data: dataString,
		success: function(res) {
		  nimble.growl('flaggreen', res, 3000);
		  $(elem).css({'background': '#fff', 'color':'#000'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_green');
		  $(elemstatus).removeClass('icon_flag_red');
          $(elemstatus).show();
		},
		error: function (xhr) {
		  nimble.growl('flagred', xhr.responseText);
		  $(elem).css({'color': '#9c3333'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_red');
		  $(elemstatus).removeClass('icon_flag_green');
          $(elemstatus).show();
		}
	});
};

// Dialog support
$(function() {
    $('<div id="confirmationdialog" title="" style="display:none">'+
      '<p id="confirmationcontent">&nbsp;</p>'+
      '<div class="buttons">'+
	  '	  <button type="submit" id="confirmaccept" class="modal_close button icon icon_accept" onClick="confirmAction()">Accept</button>'+
      '   <a id="confirmcancel" onClick="$(\'#confirmationdialog\').dialog(\'close\');" class="modal_close button icon icon_cancel">Cancel</a>'+    
      '</div>'+
      '</div>').appendTo(document.body);

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

nimble.wasConfirmed = function(title, msg, accept, cancel) {
	$("#confirmationtitle").html(title);
	$("#confirmationcontent").html(msg); 
	$("#confirmaccept").html(accept);
	$("#confirmcancel").html(cancel);
	
	$("#confirmationdialog").dialog('option', 'title', title);
	$("#confirmationdialog").dialog('open');		
};

nimble.changeLogin = function(ident) {
  $(".flash").hide();
  $(".loginselector").removeClass("current");
  $(".loginmethod").hide();
  $("#" + ident).show("highlight");
};

nimble.enableFacebookContinue = function() {
  $("#loginfacebookcontinue").show();
  $("#loginfacebookenable").hide();
};

nimble.disableFacebookContinue = function() {
  $("#loginfacebookcontinue").hide();
};

// Session Termination
$(function() {
    $('<div id="sessionterminateddialog" style="display:none">'+
      '  <div class="errorpopup">'+
      '    <div class="content" style="width:auto">'+
      '	     <p id="sessionterminateddialogmsg"><g:message code="nimble.template.sessionterminated.descriptive" /></p>'+
      '      <div class="buttons">'+
      '        <a href="#" onClick="window.location.reload();return false;" id="sessionterminatedbtn" class="button icon icon_flag_blue">Login</a>'+
      '      </div>'+
      '    </div>'+
      '  </div>'+
      '</div>').appendTo(document.body);

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
        $("#sessionterminateddialogmsg").text($('#sessionterminatedmsg').val());
        $("#sessionterminatedbtn").text($('#sessionterminatedlogin').val());
	    $("#sessionterminateddialog").dialog('open','title', $('#sessionterminatedtitle').val());
	  }
	});
});

nimble.createTip = function(id,tle,msg) {
    $(function() {
        $(document.body).after(
                '<div id="'+id+'_tip">'+
                    '<div class="ui-dialog ui-widget">'+
                    '<div class="ui-dialog-titlebar ui-widget-header ui-corner-all"><strong>'+tle+'</strong></div>'+
                    '<div class="ui-dialog-content ui-widget-content">'+msg+'</div>'+
                '</div></div>');
        $("#"+id+"_tip").hide();
        $("#"+id).bt({contentSelector: $("#"+id+"_tip"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});
    });
};

nimble.createTabs = function(id) {
    $(function() {
        $('#'+id).tabs();
    });
};



