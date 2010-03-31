
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
window.addEvent('domready', function() {
    Request.implement({
            onFailure: function(xhr) {
            if ((this.xhr.status == 403) && (this.xhr.getResponseHeader("X-Nim-Session-Invalid") != null)) {
                //$("sessionterminateddialog").dialog('open');
                alert('session termintated dialog');
                return true; // it was handled
            }
            return false;  // it was not handled
        }
    });
    
    Array.implement({
        removeEvents: function() { this.each(function(e) { $(e).removeEvents(); }); return this; },
        removeEvent: function(cls,func) { this.each(function(e) { $(e).removeEvent(cls,func); }); return this; },
        addEvent: function(cls,func) { this.each(function(e) { $(e).addEvent(cls,func); }); return this; },
        addClass: function(cls) { this.each(function(e) { $(e).addClass(cls); }); return this; },
        removeClass: function(cls) { this.each(function(e) { $(e).removeClass(cls); }); return this; }
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
		},
        // implement queue
        queue: function(p1,p2) {
            var name="fx";
            if($type(p1)=='string') name=p1;
            if($type(p1)=='function' || $type(p1)=='array') p2=p1;
            
            var q=this.retrieve("queue",$H({}));
            if(!q[name]) q[name]=$A([]);
            if($type(p2)=='array') { q[name].empty(); q[name].combine(p2); }
            if($type(p2)=='function') q[name].push(p2);

            this.store("queue",q);            
            return q[name];
        },
        // implement dequeue
        dequeue: function(name) {
            var q=this.retrieve("queue",$H({}));
            if(!name) name="fx";
            if(!q[name]) return this;
            if(q[name].length==0) return this;
            var func=q[name].pop();
            this.store("queue",q);
            if(!func) return;
            if(q[name].length>0) func(q[name][0]);
            else func();
            return this;
        }
	});
});

