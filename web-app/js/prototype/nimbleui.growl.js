window.nimble = window.nimble || {};

window.nimble.growlManager = null;

//Growl
window.nimble.growl = function(type, msg, period) {
    if(!period) period = 3000;
    if(!window.nimble.growlManager)
        window.nimble.growlManager = new Growl;

    var m = window.nimble.growlManager;
    if (type == 'success')
        m.display(msg, {autohide:true, icon:'icon_tick', delay:period});

    if (type == 'error')
        m.display(msg, {autohide:true, icon:'icon_cross', delay:period});

    if (type == 'info')
        m.display(msg, {autohide:true, icon:'icon_information', delay:period});

    if (type == 'help')
        m.display(msg, {autohide:true, icon:'icon_cross', delay:period});

    if (type == 'flagred')
        m.display(msg, {autohide:true, icon:'icon_flag_red', delay:period});

    if (type == 'flaggreen')
        m.display(msg, {autohide:true, icon:'icon_flag_green', delay:period});

    if (type == 'flagblue')
        m.display(msg, {autohide:true, icon:'icon_flag_blue', delay:period});
};
