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
});