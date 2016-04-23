$(function() {
        $.ajax({
            url: '/bigdata/getscore.htm',
            dataType: 'json',
            type: 'GET',

            success: function(data) {
            	alert(data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
});