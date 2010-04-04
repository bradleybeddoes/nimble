var growlManager = null;

//Growl
function growl(type, msg, period) {
    if(!period) period = 3000;
    if(!growlManager)
        growlManager = new Growl;

    if (type == 'success')
        growlManager.display(msg, {autohide:true, icon:'icon_tick', delay:period});

    if (type == 'error')
        growlManager.display(msg, {autohide:true, icon:'icon_cross', delay:period});

    if (type == 'info')
        growlManager.display(msg, {autohide:true, icon:'icon_information', delay:period});

    if (type == 'help')
        growlManager.display(msg, {autohide:true, icon:'icon_cross', delay:period});

    if (type == 'flagred')
        growlManager.display(msg, {autohide:true, icon:'icon_flag_red', delay:period});

    if (type == 'flaggreen')
        growlManager.display(msg, {autohide:true, icon:'icon_flag_green', delay:period});

    if (type == 'flagblue')
        growlManager.display(msg, {autohide:true, icon:'icon_flag_blue', delay:period});
}
