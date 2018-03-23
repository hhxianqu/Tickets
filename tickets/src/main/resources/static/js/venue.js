$().ready(
    function () {
        showSetion("home");
        showHistory();
    }
);

$('#homeBtn').click(
    function () {
        showSetion("home");
        showHistory();
    }
);

$('#applyBtn').click(
    function () {
        showSetion("apply");
    }
);

$('#checkSeatBtn').click(
    function () {
        showSetion("checkSeat");
        $.ajax({
            type: "post",
            url: "http://localhost:8080/getNotStartPerform",
            dataType: "json",
            success: function (result) {
                if (result.succeed) {
                $('#checkSeat').empty();
                for (let i = 0; i < result.data.length; i++ ){
                    $('#checkSeat').append(
                        `<div class="container">
                            <div class="col-lg-8">
                                <div class="performanceView">
                                    <h3>${result.data[i].performName}</h3>
                                    <h5>${result.data[i].startTime} - ${result.data[i].endTime}</h5>
                                    <span class="position">${result.data[i].venueName}</span><br>
                                    <label>${result.data[i].city} - ${result.data[i].detailPosition}</label>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <button data-toggle="modal" data-target="#seatInfo" class="btn btn-primary">检票</button>
                            </div>
                        </div>
                        <hr width="1000px">
                        <div class="modal fade" id="seatInfo" tabindex="-1" role="dialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">检票</div>
                                </div>
                                <div class="modal-body" style="height: 200px; background-color: white">
                                    <div style="margin-left: 100px">
                                        <label>订单编号 : </label>
                                        <input type="text" class="input" placeholder="请输入订单号" id="orderID"><br>
                                         <label>座位号 : </label>
                                        <input type="text" class="input" placeholder="请输入座位号" id="seatNum">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button id="check${result.data[i].id}" class="btn btn-primary" data-dismiss="modal">确认检票</button>
                                </div>
                            </div>
                        </div>
                        <script>
                            $('#check${result.data[i].id}').click(
                                function() {
                                  checkSeat($('#orderID').val(), $('#seatNum').val());
                                }
                            );
                        </script>`
                    );
                }
                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
);


function checkSeat(orderID, seatNum) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/checkSeat",
        dataType: "json",
        data: {
            orderID: orderID,
            seatNum: seatNum
        },
        success: function (result) {
            if (result) {
                swal({
                    title: "检票成功",
                    type: "success",
                    closeOnConfirm: true
                })
            } else {
                swal({
                    title: "检票失败",
                    type: "error",
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
/**
 * 发布活动申请
 */
$('#confirm').click(
    function () {
        let performRegister = {};
        performRegister.performName = $('#performName').val();
        performRegister.startDate = $('#startDate').val();
        performRegister.endDate = $('#endDate').val();
        performRegister.vipNum = $('#vipNum').val();
        performRegister.vipPrice  = $('#vipPrice').val();
        performRegister.normalNum = $('#normalNum').val();
        performRegister.normalPrice = $('#normalPrice').val();
        performRegister.venueNum = getVenueNum();

        let performType = $('#performType').val();
        let type;
        if (performType === "演唱会") {
            type = "concert";
        } else if (performType === "舞蹈芭蕾") {
            type = "dance";
        } else if (performType === "音乐会") {
            type = "musical";
        } else if (performType === "体育赛事") {
            type = "dance";
        } else if (performType === "休闲展览") {
            type = "dance";
        } else if (performType === "话剧歌剧") {
            type = "opera";
        }
        performRegister.performType =  type;

        $.ajax({
            type: "post",
            url: "http://localhost:8080/apply",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(performRegister),
            success: function (result) {
                if (result.succeed) {
                    swal({
                            title: result.message,
                            type: "success",
                            closeOnConfirm: true,
                            confirmButtonText: "确认"
                        },
                        function () {
                            showSetion("home");
                            showHistory();
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


$('#calBtn').click(
    function () {
        showSetion("calculate");
        getUnsettledPerform();
    }
);

$('#unSettledPerformBtn').click(
    function () {
        showSettled("unSettledPerform", "settledPerform");
        getUnsettledPerform();
    }
);

$('#settledPerformBtn').click(
    function () {
        showSettled("settledPerform", "unSettledPerform");
        getSettledPerform();
    }
);

/**
 * 修改场馆信息
 */
$('#infoBtn').click(
    function () {
        showSetion("information");
        getVenueInfo(getVenueNum());
    }
);

$('#change').click(
    function () {
        $('#emailChange').show();
        $('#venueNameChange').show();
        $('#cityChange').show();
        $('#detailPositionChange').show();
        $('#seatChange').show();
        $('#changeConfirm').show();

        $('#email').hide();
        $('#venueName').hide();
        $('#city').hide();
        $('#detailPosition').hide();
        $('#seat').hide();
        $('#change').hide();

        $.ajax({
            type: "get",
            url: "http://localhost:8080/getVenueInfo",
            dataType: "json",
            data: {
                venueNum: getVenueNum(),
            },
            success:function (result) {
                if (result.succeed) {
                    $('#emailChange').val(result.data.venueEmail);
                    $('#venueNameChange').val(result.data.venueName);
                    $('#cityChange').val(result.data.city);
                    $('#detailPositionChange').val(result.data.detailPosition);
                    $('#seatChange').val(result.data.seat);
                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
);

$('#changeConfirm').click(
    function () {
        let venueChange = {};
        venueChange.email = $('#emailChange').val();
        venueChange.venueName = $('#venueNameChange').val();
        venueChange.city = $('#cityChange').val();
        venueChange.detailPosition = $('#detailPositionChange').val();
        venueChange.seat = $('#seatChange').val();
        venueChange.venueNum = getVenueNum();

        console.log(venueChange);
        $.ajax({
            type: "post",
            url: "http://localhost:8080/changeVenueInfo",
            contentType: "application/json",
            data: JSON.stringify(venueChange),
            success: function (result) {
                if (result.succeed) {
                    swal({
                        type: "success",
                        title: result.message,
                        closeOnConfirm: true
                    }, function () {
                        window.location.reload();
                    });
                }
            }
        })
    }
);

/**
 * 加载未结算的演出
 */
function getUnsettledPerform() {
    let venueNum = getVenueNum();
    $.ajax({
        type: "get",
        url: "http://localhost:8080/unsettledPerform",
        dataType: "json",
        data: {
            venueNum: venueNum
        },
        success: function (result) {
            $('#unSettledPerform').empty();
            for (let i = 0; i < result.data.length; i++) {
                let totalSale = result.data[i].vipSale * result.data[i].vipPrice
                    + result.data[i].normalSale * result.data[i].normalPrice;
                let divide = (totalSale * 0.7).toFixed(0);
                $('#unSettledPerform').append(
                    `<div style="margin-left: 5%">
                        <div class="performanceView col-lg-8">
                            <h3>${result.data[i].performName}</h3>
                            <h5> ${result.data[i].startTime} - ${result.data[i].endTime} </h5>
                            <span class="position"> ${result.data[i].venueName}</span><br>
                            <label> ${result.data[i].city} - ${result.data[i].detailPosition}</label>
                        </div>
                        <div class="col-lg-4">
                            <span> Vip票售出 : <label>${result.data[i].vipSale}</label>张</span>
                            <span> 普通票售出 : <label>${result.data[i].normalSale}</label>张</span><br>
                            <span> Vip票价 : <label>${result.data[i].vipPrice}</label>元/张</span>
                            <span> 普通票价 : <label>${result.data[i].normalPrice}</label>元/张</span><br>
                            <span>总售价 : <label>${totalSale}</label>元</span><br>
                            <span>实际分成应得 : 
                                <label style="color: lightblue; font-size: 23px">${divide}</label>元
                            </span>
                        </div>
                    </div>`
                );
            }

        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

/**
 * 加载已结算的演出
 */
function getSettledPerform() {
    let venueNum = getVenueNum();
    $.ajax({
        type: "get",
        url: "http://localhost:8080/settledPerform",
        dataType: "json",
        data: {
            venueNum: venueNum
        },
        success: function (result) {
            $('#settledPerform').empty();
            for (let i = 0; i < result.data.length; i++) {
                let totalSale = result.data[i].vipSale * result.data[i].vipPrice
                                + result.data[i].normalSale * result.data[i].normalPrice;
                let divide = (totalSale * 0.7).toFixed(0);
                $('#settledPerform').append(
                    `<div style="margin-left: 5%">
                        <div class="performanceView col-lg-8">
                            <h3>${result.data[i].performName}</h3>
                            <h5> ${result.data[i].startTime} - ${result.data[i].endTime} </h5>
                            <span class="position"> ${result.data[i].venueName}</span><br>
                            <label> ${result.data[i].city} - ${result.data[i].detailPosition}</label>
                        </div>
                        <div class="col-lg-4">
                            <span> Vip票售出 : <label>${result.data[i].vipSale}</label>张</span>
                            <span> 普通票售出 : <label>${result.data[i].normalSale}</label>张</span><br>
                            <span> Vip票价 : <label>${result.data[i].vipPrice}</label>元/张</span>
                            <span> 普通票价 : <label>${result.data[i].normalPrice}</label>元/张</span><br>
                            <span>总售价 : <label>${totalSale}</label>元</span><br>
                            <span>实际分成所得 : <label style="color: #fec503; font-size: 23px">${divide}</label>元</span>
                        </div>
                    </div>`
                );
            };
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

/**
 * 查看所有演出
 */
function showHistory() {
    let venueNum = getVenueNum();
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getPerformanceByVenueNum",
        dataType: "json",
        data: {
            venueNum: venueNum,
        },
        success: function (result) {
            if (result.succeed) {
                $('#perHistory').empty();
                for (let i = 0; i < result.data.length; i++ ){
                    if (result.data[i].settled) {
                        let totalSale = result.data[i].vipSale * result.data[i].vipPrice
                            + result.data[i].normalSale * result.data[i].normalPrice;
                        let divide = (totalSale * 0.7).toFixed(0);
                        $('#perHistory').append(
                            `<div class="container">
                            <div class="col-lg-8">
                                <div class="performanceView">
                                    <h3>${result.data[i].performName}</h3>
                                    <h5>${result.data[i].startTime} - ${result.data[i].endTime}</h5>
                                    <span class="position">${result.data[i].venueName}</span><br>
                                    <label>${result.data[i].city} - ${result.data[i].detailPosition}</label>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <span> Vip票售出 : <label>${result.data[i].vipSale}</label>张</span>
                                <span> Vip票价 : <label>${result.data[i].vipPrice}</label>元/张</span><br>
                                <span> 普通票售出 : <label>${result.data[i].normalSale}</label>张</span>
                                <span> 普通票价 : <label>${result.data[i].normalPrice}</label>元/张</span><br>
                                <span>总售价 : <label>${totalSale}</label>元</span><br>
                                <span>实际分成所得 : 
                                    <label style="color: #fec503; font-size: 23px">${divide}</label>元
                                </span>
                            </div>
                        </div>
                        <hr width="1000px">`
                        )
                    } else {
                        $('#perHistory').append(
                            `<div class="container">
                            <div class="col-lg-8">
                                <div class="performanceView">
                                    <h3>${result.data[i].performName}</h3>
                                    <h5>${result.data[i].startTime} - ${result.data[i].endTime}</h5>
                                    <span class="position">${result.data[i].venueName}</span><br>
                                    <label>${result.data[i].city} - ${result.data[i].detailPosition}</label>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <label style="color: darkred; font-size: 20px">本场演出还未结算</label><br> 
                                <span> Vip票售出 : <label>${result.data[i].vipSale}</label>张</span>
                                <span> Vip票价 : <label>${result.data[i].vipPrice}</label>元/张</span><br>
                                <span> 普通票售出 : <label>${result.data[i].normalSale}</label>张</span>
                                <span> 普通票价 : <label>${result.data[i].normalPrice}</label>元/张</span>
                            </div>
                        </div>
                        <hr width="1000px">`
                        )
                    }

                }
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

/**
 * 绑定calendar和input
 */
$('#startCalendar').calendar({
    trigger: '#startDate',
    zIndex: 999,
    format: 'yyyy-mm-dd'
});

$('#endCalendar').calendar({
    trigger: '#endDate',
    zIndex: 999,
    format: 'yyyy-mm-dd'
});

$('.abc li a').click(function(){
    $('.dropdown-menu li').removeClass('ac');
    $(this).parent().addClass('ac');
    document.getElementById("performType").value = document.getElementsByClassName('ac').item(0).innerText;
});

/**
 * 获取场馆信息
 * @param venueNum
 */
function getVenueInfo(venueNum) {
    $('#changeConfirm').hide();
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getVenueInfo",
        dataType: "json",
        data: {
            venueNum: venueNum
        },
        success:function (result) {
            if (result.succeed) {
                $('#email').text(result.data.venueEmail);
                $('#venueName').text(result.data.venueName);
                $('#city').text(result.data.city);
                $('#detailPosition').text(result.data.detailPosition);
                $('#venueNum').text(venueNum);
                $('#seat').text(result.data.seat);
                $('#balance').text(result.data.balance + "元");

                $('#emailChange').hide();
                $('#venueNameChange').hide();
                $('#cityChange').hide();
                $('#detailPositionChange').hide();
                $('#seatChange').hide();
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

function getVenueNum() {
    let venueNum;
    $.ajax({
        type: "get",
        url: "http://localhost:8080/check",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.succeed && result.message === "venue") {
                venueNum = result.data.venueNumber;
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });

    return venueNum;
}

function showSetion(showName) {
    $('#home').hide();
    $('#calculate').hide();
    $('#information').hide();
    $('#apply').hide();
    $('#checkSeat').hide();
    $('#' + showName).show();
}

function showSettled(showName, hideName) {
    $('#' + showName + "Btn").addClass("active");
    $('#' + hideName + "Btn").removeClass("active");
    $('#' + showName).show();
    $('#' + hideName).hide();
}