Element.addMethods({
  setPngBackground: (function() {
    var IEBelow7 = (function(agent) {
      var version = new RegExp('MSIE ([\\d.]+)').exec(agent);
      return version ? parseFloat(version[1]) < 7 : false;
    })(navigator.userAgent);
 
    return function(element, url) {
      element = $(element);
      var options = Object.extend({
        align: 'top left',
        repeat: 'no-repeat',
        sizingMethod: 'scale',
        backgroundColor: ''
      }, arguments[2] || {});
 
      element.setStyle(IEBelow7 ? {
        filter: 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'' + url + '\'\', sizingMethod=\'' +
          options.sizingMethod + '\')'
        } : {
        background: options.backgroundColor + ' url(' + url + ') ' + options.align + ' ' + options.repeat    
      });
      return element;
    }
  })()
});