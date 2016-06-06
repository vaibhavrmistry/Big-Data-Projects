$(function() {
	alert("hi");
        $.ajax({
            url: '/bigdata/getscore.htm',
            dataType: 'json',
            type: 'GET',

            success: function(data) {
            	alert("hello");
            	alert(data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
});