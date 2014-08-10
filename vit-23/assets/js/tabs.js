$(document).ready(function()
{
    var currentSelectedTab = $('.tabs .active a').attr('href');
    currentSelectedTab = currentSelectedTab.substring(1);
    console.info(currentSelectedTab);
    var temp;
    $('.tab-content').each(function(index) {
        temp = $(this).attr('id');
        if(temp!=currentSelectedTab)
        {
            $(this).css({display:"none"});
        }
    });
    $('.tabs li').click(function() {
        var tab=$(this).find('a').attr('href');
        $("#"+currentSelectedTab).css({display:"none"});
        currentSelectedTab = tab.substring(1);
        $("#"+currentSelectedTab).css({display:"block"});
        $('.tabs .active').removeClass('active');
        $(this).addClass('active');
        return false;
    });
});