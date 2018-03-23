$('#logout').click(
    function () {
        $.ajax({
            type: "get",
            url: "http://localhost:8080/logout",
            success: function (result) {
                if (result.succeed){
                    swal({
                        title: result.message,
                        type: "success",
                        confirmButtonText: "确认",
                        closeOnConfirm: true
                    }, function () {
                        window.location.href = "home.html";
                        $('#signUp').show();
                        $('#welcome').hide();
                    })
                } else {
                    swal({
                        title: result.message,
                        type: 'error',
                        confirmButtonText: '确认',
                        closeOnConfirm: true,
                    });
                }
            }
        })
    });

function showSetion(showName, hideName, hideName2) {
    $('#' + showName).show();
    $('#' + hideName).hide();
    $('#' + hideName2).hide();
}