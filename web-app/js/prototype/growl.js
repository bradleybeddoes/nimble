/*
 * growl-prototype
 *
 * Copyright (c) 2009 Prateek Saxena
 * MIT (licensors/prototype/Saxena-MIT-LICENSE.txt)
 *
 * modified by Chris Doty (2010) - added icon class to make more nimble friendly
 *                               - killed sound
 *                               - killed image option, replace with icon class
 */
var Growl = Class.create({
  initialize: function(folder){
    this.n = 0; //Its only to help in giving the ID's to DIV's
    this.mediaFolder = folder || "../src/media/"; //Choose the images folder given by user or use default
    this.keeper = new Element('div', {'class': 'growl-keeper', 'id': 'growl_keeper' }); //Create a container where
    $(document.body).insert({bottom: this.keeper});
  },
  display: function(text) {
    this.n++;
    var gid = "growl-alert-"+this.n, opt = Object.extend({icon:null ,delay: 2, autohide:true}, arguments[1] || {});
    this.keeper.insert({bottom: new Element('div', {'class': "growl-alert",  'id': gid})});
    var me = this;

    if(opt.icon) $(gid).insert({top: '<span class="growl-icon icon '+opt.icon+'">&nbsp;</span>' } );
    $(gid).insert({bottom: new Element('p', {'class': 'growl-text'}).update(text)}).setOpacity(0).appear({to: 0.8}).observe('click',function(e) {me.remove(Event.findElement(e,'DIV').id);}).setPngBackground(opt.image,{ backgroundColor: '#000000'});
		$(gid).observe('mouseover',function() {this.addClassName('growl-alert-hover')});
		$(gid).observe('mouseout',function() {this.removeClassName('growl-alert-hover')});
    if(opt.autohide) this.remove.delay(opt.delay, gid);
		return $(gid);
    },
	remove: function(gid){
		if(!$(gid).hasClassName('growl-alert-hover')) Effect.Fade(gid);
		else Effect.Fade.delay(1,gid);
	}
});