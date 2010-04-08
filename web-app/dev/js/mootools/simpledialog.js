/*
 * SimpleDialog Tabs 0.1
 *
 * Copyright (c) 2010 Chris Doty
 * MIT (licensors/polar/MIT-LICENSE.txt)
 *
 * Depends:
 *	mootools - core
 *  mootools - more - drag,position
 */

var SimpleDialog = new Class({
Implements:[Options,Events],
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

// build dialog dom elements
toDOM: function()
{
    var self=this;
    var o=self.options;

    // create main dialog space
    var d=$(o.id);
    if(d) d.destroy();
    var wrp=$(o.id+'_wrapper');
    if(wrp) wrp.destroy();
    d=new Element('div',{'id':o.id,'class':'ui-dialog-content ui-widget-content'}).inject(document.body);
    wrp=new Element('div',{'id':o.id+'_wrapper','class':'ui-dialog ui-widget ui-widget-content ui-corner-all  ui-draggable',styles:{'display':'none'}}).wraps(d);
    self.element=wrp;

    // add events
    var closeIt=function() {self.close();return false;};
    wrp.addEvent('close',closeIt);
    d.addEvent('close',closeIt);

    if(o.title || o.canClose) {
        var tbar=new Element('div',{'id':o.id+'_titlebar','class':'ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix'}).inject(d,'before');

        // add the title
        new Element('span',{'href':'#','text':o.title,'class':'ui-dialog-title'}).inject(tbar);

        if(o.canClose) {
            // add close button
            var cls=new Element('a',{'href':'#','class':'ui-dialog-titlebar-close ui-corner-all'}).inject(tbar);
            new Element('span',{'href':'#','text':'close','class':'ui-icon ui-icon-closethick'}).inject(cls);
            // add close event for dialog close icon
            cls.addEvent('click',closeIt);
            cls.addEvent('mouseover',function() {cls.addClass('ui-state-hover');});
            cls.addEvent('mouseout',function() {cls.removeClass('ui-state-hover');});
        }
    }

    // set dimensions if required
    if(o.width) wrp.style.width=parseInt(o.width)+'px';
    if(o.height) tbar.style.height=parseInt(o.height)+'px';

    // add the content
    var pc=new Element('p',{'id':o.id+'_content'}).inject(d);
    if(o.content) pc.set('html',o.content);
    pc.addEvent('mousedown',function(e) { e=new Event(e).stopPropagation();});

    // make it movable 
    if(o.canMove) new Drag.Move(wrp);

    // position it
    wrp.style.zIndex=999;
    wrp.style.position='absolute';
    wrp.position(o.position);

    return d;
},

showModal: function()
{
    var self=this;

    var m=$('SimpleModal');
    if(m) m.destroy();
    var h=document.getCoordinates().height;
    h=(h>document.body.scrollHeight?h:document.body.scrollHeight)+'px';
    m=new Element('div',{'id':'SimpleModal','class':'ui-widget-overlay', styles:{
                'position':'absolute',
                'top':'0px',
                'left':'0px',
                'width':document.body.scrollWidth+'px',
                'height':h,
                'zIndex':500
      }}).inject(document.body);

    window.addEvent('resize', function() {self.showModal(); });
    return m;
},

hideModal: function()
{
    var self=this;
    var m=$('SimpleModal');
    if(m) {
        window.removeEvent('resize', function() {self.showModal(); });
        m.destroy();
    }
},

close: function()
{
    var self=this;
    var o=this.options;
    self.hideModal();
    if(this.element) this.element.destroy();
},

show: function()
{
    var self=this;
    var o=this.options;
    if(o.isModal) self.showModal();
    self.element.show();
}

});
