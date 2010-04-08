/*
 * SimpleDialog Tabs 0.1
 *
 * Copyright (c) 2010 Chris Doty
 * MIT (licensors/polar/MIT-LICENSE.txt)
 *
 * Depends:
 *	prototype.js
 */

var SimpleDialog=Class.create();

SimpleDialog.prototype =
{
options: {
    id:'',              // the id of the dialog
    isModal:true,       // show as a modal dialog
    width:400,          // the width of the dialog
    height:false,       // the height of the dialog
    canMove:true,       // true shows move button
    canClose:true,      // true shows close button
    cssBase:'ui-dialog',// base css name
    title:null,         // title for the dialog
    imagePath:null,     // path to images
    content:null,       // content to inject into dialog
    position:null       // hash with position parameters, defaults to center {'position':'center'}
},

initialize: function(options)
{
    this.setOptions(options);
    this.toDOM();
},

setOptions: function(options) {
    Object.extend(this.options, options || {});
},
    
// build dialog dom elements
toDOM: function()
{
    var self=this;
    var o=self.options;

    // create main dialog space
    var d=$(o.id);
    if(d) d.remove3();
    var wrp=$(o.id+'_wrapper');
    if(wrp) wrp.remove();
    d=new Element('div',{'id':o.id,'class':'ui-dialog-content ui-widget-content'});
    wrp=new Element('div',{'id':o.id+'_wrapper','class':'ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable',style:'display:none'});
    wrp.insert(d);
    document.body.insert(wrp);
    self.element=wrp;

    // add events
    var closeIt=function() {self.close();return false;}.bindAsEventListener(this);
    wrp.observe('dialog:close',closeIt);
    d.observe('dialog:close',closeIt);

    if(o.title || o.canClose) {
        var tbar=new Element('div',{'id':o.id+'_titlebar','class':'ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix'});
        d.insert({'before':tbar});
        
        // add the title
        tbar.insert(new Element('span',{'href':'#','class':'ui-dialog-title'}).insert(o.title));

        if(o.canClose) {
            // add close button
            var cls=new Element('a',{'href':'#','class':'ui-dialog-titlebar-close ui-corner-all'});
            tbar.insert(cls);
            cls.insert(new Element('span',{'href':'#','class':'ui-icon ui-icon-closethick'}).insert('close'));
            // add close event for dialog close icon
            cls.observe('click',closeIt);
            cls.observe('mouseover',function() {cls.addClassName('ui-state-hover');});
            cls.observe('mouseout',function() {cls.removeClassName('ui-state-hover');});
        }
    }

    // set dimensions if required
    if(o.width) wrp.style.width=parseInt(o.width)+'px';
    if(o.height) tbar.style.height=parseInt(o.height)+'px';

    // add the content
    var pc=new Element('p',{'id':o.id+'_content'});
    pc.insert(o.content);
    d.insert(pc);
    pc.observe('mousedown',function(e) {
        Event.stop(e);
    });

    // make it movable 
    if(o.canMove) new Draggable(wrp.identify());

    // position it
    Position.center(wrp);

    return d;
},

showModal: function()
{
    var self=this;

    var m=$('SimpleModal');
    if(m) m.remove();
    var h=Position.getWindowSize().height;
    h=(h>document.body.scrollHeight?h:document.body.scrollHeight)+'px';
    m=new Element('div',{'id':'SimpleModal','class':'ui-widget-overlay'});
    m.setStyle({
                'position':'absolute',
                'top':'0px',
                'left':'0px',
                'width':document.body.scrollWidth+'px',
                'height':h,
                'zIndex':500
      });
      document.body.insert(m);

    Event.observe(document.onresize ? document : window, "resize", function() {self.showModal(); });
    return m;
},

hideModal: function()
{
    var self=this;
    var m=$('SimpleModal');
    if(m) {
        Event.stopObserving(document.onresize ? document : window, 'resize');
        m.remove();
    }
},

close: function()
{
    var self=this;
    var o=this.options;
    self.hideModal();
    if(this.element) this.element.remove();
    self.element=null;
},

show: function()
{
    var self=this;
    var o=this.options;
    if(o.isModal) self.showModal();
    self.element.show();
}

};

Position.getWindowSize = function(w) {
    var width, height;
    w = w ? w : window;
    width = w.innerWidth || (w.document.documentElement.clientWidth || w.document.body.clientWidth);
    height = w.innerHeight || (w.document.documentElement.clientHeight || w.document.body.clientHeight);
    return { width: width, height: height };
};

Position.center = function(element){
  var options = Object.extend({
      zIndex: 999,
      update: false
  }, arguments[1] || {});

  element = $(element);

  if(!element._centered){
      Element.setStyle(element, {position: 'absolute', zIndex: options.zIndex });
      element._centered = true;
  }

  var dims = Element.getDimensions(element);

  Position.prepare();
  var winSize = Position.getWindowSize();
  var winWidth = winSize.width;
  var winHeight = winSize.height;

  var offLeft = (Position.deltaX + Math.floor((winWidth-dims.width)/2));
  var offTop = (Position.deltaY + Math.floor((winHeight-dims.height)/2));
  element.style.top = ((offTop != null && offTop > 0) ? offTop : '0')+ 'px';
  element.style.left = ((offLeft != null && offLeft > 0) ? offLeft :'0') + 'px';

  if (options.update) {
    Event.observe(window, 'resize', function(evt) { Position.center(element); }, false);
    Event.observe(window, 'scroll', function(evt) { Position.center(element); }, false);
  }
};