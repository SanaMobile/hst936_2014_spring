$(function() {
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
        minDate: new Date(1920, 1, 1),
        changeMonth: true,
        changeYear: true,
        yearRange: '1924:2014',
        onChangeMonthYear: function (year, month, day) {
            $(this).datepicker('setDate', new Date(year, month - 1, day.selectedDay));
        }
    });

    $(".datetimepicker").datetimepicker({
        dateFormat: 'yy-mm-dd',
        timeFormat:  "HH:mm",
        minDate: new Date(1920, 1, 1),
        changeMonth: true,
        changeYear: true,
        yearRange: '1924:2014',
        onChangeMonthYear: function (year, month, day) {
            $(this).datepicker('setDate', new Date(year, month - 1, day.selectedDay));
        }
    });

    $(document).on('click',".remove-button", function(){
        if(confirm("Are you sure you want to remove this encounter ?")) {
            var id = $(this).attr('id');
            $.ajax({
                url: '/encounter/delete/' + id
            }).done(function(data){
                window.location.reload();
            });
        }
    });

    $('#logoutButton').click(function(){
       window.location.href = '/home/logout';
    });

    $('#visitTypeSelector').on('change', function(e){
        var optionSelected = $("option:selected", this);
        var valueSelected = this.value;
        window.location.href = '/home/dashboard?visit='+valueSelected;
    });
});