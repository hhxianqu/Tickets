$().ready(function () {
    $.ajax({
        type: "get",
        url: 'http://localhost:8080/check',
        dataType:"json",
        success: function (result) {
            if (result.succeed && result.message === "user") {
                document.getElementById('usernameToShow').innerText = result.data.username;
                document.getElementById('memberName').innerText = result.data.username;
                document.getElementById('email').value = result.data.email;
                document.getElementById('level').innerText = result.data.level;
                document.getElementById('score').innerText = result.data.score;
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });

});

$('#delete').click(
    function () {
        swal({
            title: "注销确认",
            type: "warning",
            text: "一经注销，账号无法恢复，确认注销吗？",
            showCancelButton: true,
            cancelButtonText: "取消",
            confirmButtonText: "确认注销",
        },
            function () {
            let email = $('#email').val();
                $.ajax({
                    type: "get",
                    url: "http://localhost:8080/delete",
                    data: {
                        email: email
                    },
                    success: function (result) {
                        window.location.href = "home.html";
                    },
                    error: function (xhr, status, error) {
                        console.log(xhr.status);
                        console.log(error);
                    }
                })
            })
    }
);

$('#change').click(
    function () {
        $('#nameChanged').show();
        $('#confirm').show();
        $('#memberName').hide();
        $('#change').hide();
    }
);

$('#confirm').click(
    function () {
        let nameToChange = $('#nameChanged').val();
        let email = $('#email').val();

        $.ajax({
            type: "post",
            url: "http://localhost:8080/update",
            dataType: "json",
            data: {
                username: nameToChange,
                email: email
            },
            success: function (result) {
                if (result.succeed) {
                    swal({
                        title: result.message,
                        type: "success",
                        confirmButtonText: "确认",
                        closeOnConfirm: true,
                    },
                        function () {
                            $('#memberName').text = result.data.username;
                            window.location.reload();
                        })
                } else {
                    swal({
                        title: result.message,
                        type: "error",
                        confirmButtonText: "确认",
                        closeOnConfirm: true,
                    })
                }
            }
        })
    }
);