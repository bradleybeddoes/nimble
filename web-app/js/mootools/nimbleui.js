
// General
function verifyUnique(elem, elemstatus, endpoint, success, failure) {
   var dataString = 'val=' + $(elem).get('value');
   new Request({
     	method: "POST",
		url: endpoint,
		data: dataString,
		onSuccess: function(res) {
		  growl('flaggreen', res, 3000);
		  $(elem).setStyles({'background': '#fff', 'color':'#000'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_green');
		  $(elemstatus).removeClass('icon_flag_red');
          $(elemstatus).show();  
		},
		onFailure: function (xhr) {
		  growl('flagred', xhr.responseText);
		  $(elem).setStyles({'color': '#9c3333'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_red');
		  $(elemstatus).removeClass('icon_flag_green');
          $(elemstatus).show();
		}
	}).send();
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
  $$(".loginselector").removeClass("current");
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
    window.addEvent('domready', function() {
        var tip=$(e);new Tips(tip);
        tip.store('tip:title',tle);
        tip.store('tip:text',msg);
    });
}

function createTabs(id) {
    window.addEvent('domready', function() {
        nimble.Tabs(id);
    });
}

//when the dom is ready...
Request.implement({
        onFailure: function() {
        if ((this.xhr.status == 403) && (this.xhr.getResponseHeader("X-Nim-Session-Invalid") != null)) {
            var title=$('sessionterminatedtitle').get('value');
            var msg=$('sessionterminatedmsg').get('value');
            var login=$('sessionterminatedlogin').get('value');
            var content=
              '<p id="sessionterminatedcontent">'+msg+'</p>'+
              '<div class="buttons">'+
              '   <a href="#" onClick="window.location.reload();return false;" id="sessionterminatedbtn" class="button icon icon_flag_blue">'+login+'</a>'+
              '</div>';
            new SimpleDialog({'id':'confirmationdialog','title':title,'content':content}).show();
            return true; // it was handled
        }
        return false;  // it was not handled
    }
});

//time to implement basic show / hide
Element.implement({
    //implement show
    show: function() {
        this.setStyle('display','');
        return this;
    },
    //implement hide
    hide: function() {
        this.setStyle('display','none');
        return this;
    }
});

