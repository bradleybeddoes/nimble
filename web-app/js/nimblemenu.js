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

    $(".mlhorizmenu li.current").children("ul").css("left", "0px").show();

    $(".mlhorizmenu li").hover(function(){
        if(this.className.indexOf("current") == -1)  {
            getCurrent = $(this).parent().children("li.current:eq(0)");
            if (getCurrent = 1 ) {
                $(this).parent().children("li.current:eq(0)").children("ul").hide();
                $(this).parent().children("li.current:eq(0)").removeClass("current")
            }
            $(this).children("ul:eq(0)").css("left", "0px").show();
            $(this).addClass("current")
        }
    },function(){
        if(this.className.indexOf("current") == -1)  {
            getCurrent = $(this).parent().children("li.current:eq(0)");

            if (getCurrent = 1 ) {
                $(this).parent().children("li.current:eq(0)").children("ul").show();;
            }
            $(this).children("ul:eq(0)").css("left", "-99999px").hide();
        }
    });
});