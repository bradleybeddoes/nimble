window.nimble = window.nimble || {};

//Growl
window.nimble.growl = function(type, msg, period) {
    if(!period) period = 2000;

    if (type == 'success')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_tick\'>&nbsp;</span>' });

    if (type == 'error')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'info')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_information\'>&nbsp;</span>' });

    if (type == 'help')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'flagred')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_red\'>&nbsp;</span>' });

    if (type == 'flaggreen')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_green\'>&nbsp;</span>' });

    if (type == 'flagblue')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_blue\'>&nbsp;</span>' });
};

