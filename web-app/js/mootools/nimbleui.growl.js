var notimooManager = null;

//Growl
function growl(type, msg) {
    growl(type, msg, 2000);
}

function growl(type, msg, period) {
    if(!notimooManager)
        notimooManager = new Notimoo();

    if (type == 'success')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_tick\'>&nbsp;</span>' });

    if (type == 'error')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'info')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_information\'>&nbsp;</span>' });

    if (type == 'help')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'flagred')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_flag_red\'>&nbsp;</span>' });

    if (type == 'flaggreen')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_flag_green\'>&nbsp;</span>' });

    if (type == 'flagblue')
      notimooManager.show({message: msg, visibleTime: period, title: '<span class=\'icon icon_flag_blue\'>&nbsp;</span>' });
}
