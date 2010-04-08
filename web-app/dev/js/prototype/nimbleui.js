window.nimble = window.nimble || {};
var nimble = window.nimble;

// General
nimble.verifyUnique = function(elem, elemstatus, endpoint) {
   var dataString = 'val=' + $(elem).getValue();
   new Ajax.Request(endpoint,{
     	method: "POST",
		parameters: dataString,
		onSuccess: function(xhr) {
		  growl('flaggreen', xhr.responseText);
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
};

nimble.wasConfirmed = function(title, msg, accept, cancel) {
    var content=
      '<p id="confirmationcontent">'+msg+'</p>'+
      '<div class="buttons">'+
      '	  <a id="confirmaccept" class="modal_close button icon icon_accept" onClick="confirmAction(); $(\'confirmationdialog\').fire(\'dialog:close\');">Accept</a>'+
      '   <a id="confirmcancel" onClick="$(\'confirmationdialog\').fire(\'dialog:close\');" class="modal_close button icon icon_cancel">'+cancel+'</a>'+
      '</div>';

    new SimpleDialog({'id':'confirmationdialog','title':title,'content':content}).show();
    return false;
};

nimble.changeLogin = function(id) {
  $$(".flash").hide();
  $$(".loginselector").removeClassName("current");
  $$(".loginmethod").hide();
  $(id).show().highlight();
};

nimble.enableFacebookContinue = function() {
  $("loginfacebookcontinue").show();
  $("loginfacebookenable").hide();
};

nimble.disableFacebookContinue = function() {
  $("loginfacebookcontinue").hide();
};

nimble.createTip = function(e,tle,msg) {
    document.observe("dom:loaded", function() {
		new Tooltip($(e), {title:tle, content:msg});
    });
};

nimble.createTabs = function(id) {
    document.observe("dom:loaded", function() {
        nimble.Tabs(id);
    });
};
