
// General
function verifyUnique(elem, elemstatus, endpoint, success, failure) {
   var dataString = 'val=' + $(elem).getValue();
   new Ajax.Request(endpoint,{
     	method: "POST",
		parameters: dataString,
		onSuccess: function(xhr) {
		  growl('flaggreen', xhr.responseText, 3000);
		  $(elem).setStyle({'background': '#fff', 'color':'#000'});
		  $(elemstatus).addClassName('icon');
		  $(elemstatus).addClassName('icon_flag_green');
		  $(elemstatus).removeClassName('icon_flag_red');
          $(elemstatus).show();  
		},
		onFailure: function (xhr) {
		  growl('flagred', xhr.responseText);
		  $(elem).setStyle({'color': '#9c3333'});
		  $(elemstatus).addClassName('icon');
		  $(elemstatus).addClassName('icon_flag_red');
		  $(elemstatus).removeClassName('icon_flag_green');
          $(elemstatus).show();
		}
	});
}

function wasConfirmed(title, msg, accept, cancel) {
    var content=
      '<p id="confirmationcontent">'+msg+'</p>'+
      '<div class="buttons">'+
	  '	  <button type="submit" id="confirmaccept" class="modal_close button icon icon_accept" onClick="confirmAction()">'+accept+'</button>'+
      '   <a id="confirmcancel" onClick="$(\'confirmationdialog\').fireEvent(\'close\');" class="modal_close button icon icon_cancel">'+cancel+'</a>'+
      '</div>';

    new SimpleDialog({'id':'confirmationdialog','title':title,'content':content}).show();
    return false;
}

function changeLogin(ident) {
  $$(".flash").hide();
  $$(".loginselector").removeClassName("current");
  $$(".loginmethod").hide();
  $(ident).show().highlight();
}

function enableFacebookContinue() {
  $("loginfacebookcontinue").show();
  $("loginfacebookenable").hide();
}

function disableFacebookContinue() {
  $("loginfacebookcontinue").hide();
}

function createTip(e,tle,msg) {
    document.observe("dom:loaded", function() {
		new Tooltip($(e), {title:tle, content:msg});
    });
}

function createTabs(id) {
    document.observe("dom:loaded", function() {
        nimble.Tabs(id);
    });
}


