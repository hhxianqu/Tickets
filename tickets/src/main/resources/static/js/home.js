
$().ready(function () {
    $.ajax({
        type: "get",
        url: 'http://localhost:8080/check',
        dataType:"json",
        success: function (result) {
            if (result.succeed) {
                if (result.message === "user"){
                    document.getElementById('usernameToShow').innerText = result.data.username;
                    $('#welcome').show();
                    $('#signUp').hide();
                } else if (result.message === "manager") {
                    window.location.href = "manager.html";
                } else {
                    window.location.href = "venue.html";
                }
            } else {
                $('#signUp').show();
                $('#welcome').hide();
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
    showSign('user', 'venue');
    showLogin('user', 'venue', 'manager');
    hideWarning();
});

function hideWarning() {
    $('#emailWarning').hide();
    $('#passwordWarning').hide();
    $('#venueNumWarning').hide();
    $('#venuePasswordWarning').hide();
    $('#managerNumWarning').hide();
    $('#managerPasswordWarning').hide();

}

/**
 * 会员、经理和场馆的登录注册
 * @param showName
 * @param hideName
 */
function showSign(showName, hideName) {
    $('#'+ showName).show();
    $('#'+ hideName).hide();
    $('#'+ showName + 'Sign').show();
    $('#'+ hideName + 'Sign').hide();
}

function showLogin(showName, hideName, hideName2) {
    $('#'+ showName + 'Login').show();
    $('#'+ hideName + 'Login').hide();
    $('#'+ hideName2 + 'Login').hide();
    $('#'+ showName + 'LoginBtn').show();
    $('#'+ hideName + 'LoginBtn').hide();
    $('#'+ hideName2 + 'LoginBtn').hide();
}

function showActive(showName, hideName, hideName2) {
    $('#' + showName).addClass("active");
    $('#' + hideName).removeClass("active");
    $('#' + hideName2).removeClass("active");
}

$('#signUp').click(
    function () {
        hideWarning();
        showActive('userLoginChoose', 'venueLoginChoose', 'managerLoginChoose');
        showLogin('user', 'venue', 'manager');
});

$('#email').click(
    function () {
        hideWarning();
    }
);

$('#userBtn').click(
    function () {
        $('#userBtn').addClass("active");
        $('#venueBtn').removeClass("active");
        showSign('user', 'venue');
    }
);
$('#venueBtn').click(
    function () {
        $('#userBtn').removeClass("active");
        $('#venueBtn').addClass("active");
        showSign('venue', 'user');
    }
);

$('#userLoginChoose').click(
    function () {
        showActive('userLoginChoose', 'venueLoginChoose', 'managerLoginChoose');
        showLogin('user', 'venue', 'manager');
        hideWarning();
    }
);

$('#venueLoginChoose').click(
    function () {
        showActive('venueLoginChoose', 'userLoginChoose', 'managerLoginChoose');
        showLogin('venue', 'user', 'manager');
        hideWarning();
    }
);

$('#managerLoginChoose').click(
    function () {
        showActive('managerLoginChoose', 'userLoginChoose', 'venueLoginChoose');
        showLogin('manager', 'user', 'venue');
        hideWarning();
    }
);

/* 用户登录 */
$('#userLoginBtn').click(
    function () {
        let email = $('#email').val();
        let password = $('#password').val();

        if (email === ""){
            $('#emailWarning').show();
        }
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/login',
            data: {
                username: email,
                password: password,
                identity: "user"
            },
            dataType: "json",
            success: function (result) {
                console.log(result);
                if (result.succeed) {
                    swal({
                            title: result.message,
                            type: 'success',
                            confirmButtonText: '确认',
                            closeOnConfirm: true,
                        },
                        function () {
                            document.getElementById('usernameToShow').innerText = result.data.username;
                            $('#welcome').show();
                            $('#signUp').hide();
                        }
                    )
                } else {
                    swal({
                        title: result.message,
                        type: 'error',
                        confirmButtonText: '确认',
                        closeOnConfirm: true,
                    })
                }

            },
            error: function (xhr, status, error) {
                console.log(status);
            }
        })
    }
);

/*场馆登录*/
$('#venueLoginBtn').click(
    function () {
        let venueNum = $('#venueNum').val();
        let password = $('#venuePassword').val();

        if (venueNum == null) {
            $('#venueNumWarning').show();
        }
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/login',
            data: {
                username: venueNum,
                password: password,
                identity: "venue",
            },
            success: function (result) {
                if (result.succeed) {
                    window.location.href = "venue.html";
                } else {
                    swal({
                        type: "error",
                        title: result.message,
                        closeOnConfirm: true
                    })
                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
);
/*经理登录*/
$('#managerLoginBtn').click(
    function () {
        let manager = $('#managerNum').val();
        let password = $('#managerPassword').val();

        if (manager == null) {
            $('#managerNumWarning').show();
        }
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/login',
            data: {
                username: manager,
                password: password,
                identity: "manager",
            },
            success: function (result) {
                if (result.succeed) {
                    window.location.href = "manager.html";
                } else {

                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
);

function perform(type) {
    window.location.href = "performance.html?" + type + "=" + type;
}
$('#concert').click(
    function () {
        perform("concert");
    }
);
$('#opera').click(
    function () {
        perform("opera");
    }
);
$('#musical').click(
    function () {
        perform("musical");
    }
);
$('#dance').click(
    function () {
        perform("dance");
    }
);
$('#sports').click(
    function () {
        perform("sports");
    }
);
$('#exhibit').click(
    function () {
        perform("exhibit");
    }
);

/**
 * 发送验证码
 */
$('#verifyBtn').click(
    function () {
        let email = $('#emailS').val();
        sendMail(email);
    }
);
function sendMail(emailAddress) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/sendEmail",
        dataType: "json",
        data: {
            emailAddress: emailAddress
        },
        success: function (result) {},
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
}
function checkVerification(email, verificationCode) {
    console.log(email);
    let check;
    $.ajax({
        type: "post",
        url: "http://localhost:8080/checkVerification",
        dataType: "json",
        async: false,
        data: {
            emailAddress: email,
            verificationCode: verificationCode,
        },
        success: function (result) {
            check = result;
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
    return check;
}

/**
 * 用户注册
 */
$('#userSign').click(
    function () {
        let userRegister = {};
        userRegister.username = $('#username').val();
        userRegister.email = $('#emailS').val();
        userRegister.password = $('#passwordS').val();
        let passwordAgain = $('#passAgain').val();
        let verificationCode = $('#verification').val();
        let check = checkVerification(userRegister.email, verificationCode);

        if (userRegister.password !== passwordAgain){
            swal({
                title: "密码不一致，请重新输入",
                type: "error",
                confirmButtonText: "确认",
                closeOnConfirm: true,
            });
            return;
        }
        if (!check) {
            swal({
                title: "验证码错误，请重新发送",
                type: "error",
                confirmButtonText: "确认",
                closeOnConfirm: true,
            });
            return;
        }
        $.ajax({
            type: "post",
            url: "http://localhost:8080/userSignIn",
            contentType: "application/json",
            data: JSON.stringify(userRegister),
            async: true,
            success: function (result) {
                console.log(result);
                if (result.succeed) {
                    window.location.href = "home.html";
                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
);
/**
 * 场馆注册
 */
$('#venueSign').click(
    function () {
        let venueRegister = {};
        venueRegister.email = $('#venueEmail').val();
        venueRegister.venueName = $('#venueName').val();
        venueRegister.city = $('#city').val();
        venueRegister.detailPosition = $('#detailPosition').val();
        venueRegister.seat = $('#seats').val();
        console.log(venueRegister.seat);
        venueRegister.venuePassword = $('#venuePasswordS').val();
        let password = $('#venuePasswordAgain').val();

        if (password !== venueRegister.venuePassword) {
            swal({
                title: "密码不一致，请重新输入",
                type: "error",
                confirmButtonText: "确认",
                closeOnConfirm: true,
            });
            return;
        }
        $.ajax({
            type: "post",
            url: "http://localhost:8080/venueSignIn",
            contentType: "application/json",
            data: JSON.stringify(venueRegister),
            success: function (result) {
                if (result.succeed) {
                    window.location.reload();
                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
);