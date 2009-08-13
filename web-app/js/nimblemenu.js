$(function() {
    $('.horizmenu').children().bind("click", function() {
        if(!$(this).hasClass('disableclick')) {
            $(this).siblings().removeClass('current');
            $(this).addClass('current');
            var s = $(this).children("a").attr("href");
            var h = $(this).parent().attr("id");
            $("." + h).hide();
            $("." + s).show();
            return false;
        }
    });

    var menus = $('.horizmenu');
    if (menus) {
        for (i = 0; i < menus.length; i++) {
            var menuidentifier = $(menus[i]).attr("id");
            if (menuidentifier) {
                var menucontent = $('.' + menuidentifier);

                for (j = 0; j < menucontent.length; j++) {
                    if (!($(menucontent[j]).hasClass('active_')))
                        $(menucontent[j]).hide();
                }
            }
        }
    }

    $(".mlhorizmenu .submenu").hide();
    $(".mlhorizmenu .submenu-active_").show();

    $(".mlhorizmenu li").mouseover(function() {
        $(this).parent().find(".current").removeClass("current");
        $(this).parent().find(".submenu").hide();
        $(this).addClass('current');
        $(this).children('.submenu').show();
    });
});