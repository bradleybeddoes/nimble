window.nimble = window.nimble || {};
window.nimble.growlManager = null;

//Growl
window.nimble.growl = function(type, msg, period) {
    if(!period) period=2000; 
    if(!window.nimble.growlManager)
        window.nimble.growlManager = new Notimoo();
    
    var m = window.nimble.growlManager;
    if (type == 'success')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_tick\'>&nbsp;</span>' });

    if (type == 'error')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'info')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_information\'>&nbsp;</span>' });

    if (type == 'help')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'flagred')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_flag_red\'>&nbsp;</span>' });

    if (type == 'flaggreen')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_flag_green\'>&nbsp;</span>' });

    if (type == 'flagblue')
      m.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_flag_blue\'>&nbsp;</span>' });
};
