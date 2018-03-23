$().ready(function () {
    getAllOrder();
    // $('#order').hide();
    // loadDiscountMarket();
    $('#discount').hide();
    $.ajax({
        type: "get",
        url: 'http://localhost:8080/check',
        dataType:"json",
        success: function (result) {
            if (result.succeed && result.message === "user") {
                document.getElementById('usernameToShow').innerText = result.data.username;
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
});

function preLoader() {
    $.ajax({
        type: "get",
        url: 'http://localhost:8080/check',
        dataType:"json",
        async: false,
        success: function (result) {
            if (result.succeed && result.message === "user") {
                document.getElementById('usernameToShow').innerText = result.data.username;
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
}

/**
 * 加载用户自有优惠券
 */
function loadDiscountCoupon() {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/getSelfDiscount",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#discountSelf').show();
                $('#discountSelf').empty();
                $('#discountMarket').hide();
                for (let i = 0; i < result.data.length; i++) {
                    let discount = result.data[i];
                    $('#discountSelf').append(
                        `<div class="col-lg-6 discountView">
                            <div class="col-lg-4 discount text-center">
                                <h2>¥ ${discount.discountNum}</h2>
                                <span style="font-size: 12px;margin-top: 25px">直降</span>
                            </div>
                            <div class="col-lg-8" align="center">
                                <label style="margin-top: 40px">拥有 ${discount.ownNum} 张</label><br>
                                <label style="font-size: 10px; font-weight: lighter">
                                    有效期 : ${discount.startDate} - ${discount.endDate}
                                </label><br>
                                <label style="font-size: 10px; font-weight: lighter">此优惠券不支持叠加使用</label>
                            </div>
                        </div>`
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

/**
 * 加载所有优惠券
 */
function loadDiscountMarket() {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/getAllDiscount",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                $('#discountMarket').show();
                $('#discountMarket').empty();
                $('#discountSelf').hide();
                for (let i = 0; i < result.data.length; i++) {
                    let discount = result.data[i];
                    $('#discountMarket').append(
                        `<div class="col-lg-6 discountView">
                            <div class="col-lg-4 discount text-center">
                                <h2>¥ ${discount.discountNum}</h2>
                                <span style="font-size: 12px;margin-top: 25px">直降</span>
                            </div>
                            <div class="col-lg-8" align="center">
                                <button class="btn btn-info" id="convert${discount.id}">${discount.scoreToDeduct}积分兑换</button><br>
                                <span style="font-size: 10px; font-weight: lighter">
                                    有效期 : ${discount.startDate} - ${discount.endDate}
                                </span><br>
                                <span style="font-size: 10px; font-weight: lighter">此优惠券不支持叠加使用</span>
                            </div>
                        </div>
                        <script>
                            $('#convert${discount.id}').click(
                                function() {
                                    convert(${discount.id});
                                }
                            );
                    </script>`);
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
 * 兑换优惠券
 * @param discountID
 */
function convert(discountID) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/convert",
        dataType: "json",
        data: {
            discountID: discountID,
        },
        success: function (result) {
            if (result.succeed) {
                swal({
                    title: result.message,
                    type: "success",
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


function showActive(showName) {
    $('#allOrder').removeClass("active");
    $('#finishedOrder').removeClass("active");
    $('#notPaidOrder').removeClass("active");
    $('#notAllocateOrder').removeClass("active");
    $('#cancelOrder').removeClass("active");
    $('#refundOrder').removeClass("active");

    $('#' + showName).addClass("active");
}

/**
 * 支付订单
 * @param orderID
 */
function payOrder(orderID) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/payOrder",
        dataType: "json",
        data: {
            orderID: orderID
        },
        success: function (result) {
            if (result) {
                swal({
                    title: "支付成功",
                    type: "success",
                    closeOnConfirm: true,
                },
                    function () {
                        getNotPaidOrder();
                    });
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}
function getNotPaidOrder() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getNotPaidOrder",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#orderShow').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let order = result.data[i];
                    $('#orderShow').append(
                        `<div class="orderView" style="height: 200px">
                            <div class="col-lg-8">
                                <h3>${order.performName}</h3>
                                <label> ${order.performStartTime} - </label>
                                <label class="position"> ${order.performVenue}</label><br>
                                <span> Vip票(${order.vipNum}张) </span> - <span> 普通票(${order.normalNum}张) </span><br>
                                <label>${order.seat}</label><br>
                                <span>下单时间 : ${order.createTime}</span>
                            </div>
                            <div class="col-lg-4" align="right">
                                <label class="notPaid">${order.state}订单</label>
                                <br>
                                <button class="btn btn-primary" id="pay${order.id}">支付</button>
                                <h3 class="price" style="margin-top: 37%">¥ ${order.price}</h3>
                            </div>
                        </div>
                        <script>
                            $('#pay${order.id}').click(
                                function() {
                                    payOrder(${order.id});
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

function getAllOrder() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getAllOrder",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#orderShow').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let order = result.data[i];
                    $('#orderShow').append(
                        `<div class="orderView" style="height: 200px">
                            <div class="col-lg-8">
                                <h3>${order.performName}</h3>
                                <label> ${order.performStartTime} - </label>
                                <label class="position"> ${order.performVenue}</label><br>
                                <span> Vip票(${order.vipNum}张) </span> - <span> 普通票(${order.normalNum}张) </span><br>
                                <label>${order.seat}</label><br>
                                <span>下单时间 : ${order.createTime}</span>
                            </div>
                            <div class="col-lg-4" align="right">
                                <label>${order.state}订单</label>
                                <br>
                                <h3 class="price" style="margin-top: 45%">¥ ${order.price}</h3>
                            </div>
                        </div>`
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

function cancelOrder(orderID) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/cancelOrder",
        dataType: "json",
        data: {
            orderID: orderID
        },
        success: function (result) {
            if (result) {
                swal({
                        title: "支付成功",
                        type: "success",
                        closeOnConfirm: true,
                    },
                    function () {
                        getFinishedOrder();
                    });
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

function getFinishedOrder() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getFinishedOrder",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#orderShow').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let order = result.data[i];
                    $('#orderShow').append(
                        `<div class="orderView" style="height: 200px">
                            <div class="col-lg-8">
                                <h3>${order.performName}</h3>
                                <label> ${order.performStartTime} - </label>
                                <label class="position"> ${order.performVenue}</label><br>
                                <span> Vip票(${order.vipNum}张) </span> - <span> 普通票(${order.normalNum}张) </span><br>
                                <label>${order.seat}</label><br>
                                <span>下单时间 : ${order.createTime}</span>
                            </div>
                            <div class="col-lg-4" align="right">
                                <label class="finished">${order.state}订单</label>
                                <br>
                                <button class="btn btn-primary" id="cancel${order.id}">退款</button>
                                <h3 class="price" style="margin-top: 40%">¥ ${order.price}</h3>
                            </div>
                        </div>
                        <script>
                            $('#cancel${order.id}').click(
                                function() {
                                    cancelOrder(${order.id});
                                }
                            );
</script>
                        `
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

function getCancelOrder() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getCancelOrder",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#orderShow').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let order = result.data[i];
                    $('#orderShow').append(
                        `<div class="orderView" style="height: 200px">
                            <div class="col-lg-8">
                                <h3>${order.performName}</h3>
                                <label> ${order.performStartTime} - </label>
                                <label class="position"> ${order.performVenue}</label><br>
                                <span> Vip票(${order.vipNum}张) </span> - <span> 普通票(${order.normalNum}张) </span><br>
                                <label>${order.seat}</label><br>
                                <span>下单时间 : ${order.createTime}</span>
                            </div>
                            <div class="col-lg-4" align="right">
                                <label class="cancel">${order.state}订单</label>
                                <br>
                                <h3 class="price" style="margin-top: 45%">¥ ${order.price}</h3>
                            </div>
                        </div>`
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


function getNotAllocateOrder() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getNotAllocateOrder",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#orderShow').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let order = result.data[i];
                    $('#orderShow').append(
                        `<div class="orderView" style="height: 200px">
                            <div class="col-lg-8">
                                <h3>${order.performName}</h3>
                                <label> ${order.performStartTime} - </label>
                                <label class="position"> ${order.performVenue}</label><br>
                                <span> Vip票(${order.vipNum}张) </span> - <span> 普通票(${order.normalNum}张) </span><br>
                                <label>${order.seat}</label><br>
                                <span>下单时间 : ${order.createTime}</span>
                            </div>
                            <div class="col-lg-4" align="right">
                                <label class="notAllocate">${order.state}订单</label>
                                <br>
                                <h3 class="price" style="margin-top: 45%">¥ ${order.price}</h3>
                            </div>
                        </div>`
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



function getRefundOrder() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getRefundOrder",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#orderShow').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let order = result.data[i];
                    $('#orderShow').append(
                        `<div class="orderView" style="height: 200px">
                            <div class="col-lg-8">
                                <h3>${order.performName}</h3>
                                <label> ${order.performStartTime} - </label>
                                <label class="position"> ${order.performVenue}</label><br>
                                <span> Vip票(${order.vipNum}张) </span> - <span> 普通票(${order.normalNum}张) </span><br>
                                <label>${order.seat}</label><br>
                                <span>下单时间 : ${order.createTime}</span>
                            </div>
                            <div class="col-lg-4" align="right">
                                <label class="refund">${order.state}订单</label>
                                <br>
                                <h3 class="price" style="margin-top: 45%">¥ ${order.price}</h3>
                            </div>
                        </div>`
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


$('#allOrder').click(
    function () {
        showActive('allOrder');
        getAllOrder();
    }
);

$('#finishedOrder').click(
    function () {
        showActive('finishedOrder');
        getFinishedOrder();
    }
);

$('#notPaidOrder').click(
    function () {
        showActive('notPaidOrder');
        getNotPaidOrder();
    }
);

$('#cancelOrder').click(
    function () {
        showActive('cancelOrder');
        getCancelOrder();
    }
);

$('#notAllocateOrder').click(
    function () {
        showActive('notAllocateOrder');
        getNotAllocateOrder();
    }
);

$('#refundOrder').click(
    function () {
        showActive('refundOrder');
        getRefundOrder();
    }
);

/**
 * 进入订单页面
 */
$('#orderBtn').click(
    function () {
        $('#order').show();
        $('#discount').hide();
    }
);

/**
 * 进入优惠券页面
 */
$('#discountBtn').click(
    function () {
        $('#discount').show();
        $('#order').hide();
        loadDiscountMarket();
    }
);

/**
 * 我的优惠券
 */
$('#selfDiscountBtn').click(
    function () {
        $('#selfDiscountBtn').addClass("active");
        $('#discountMarketBtn').removeClass("active");
        loadDiscountCoupon();
    }
);

/**
 * 优惠券商城
 */
$('#discountMarketBtn').click(
    function () {
        $('#discountMarketBtn').addClass("active");
        $('#selfDiscountBtn').removeClass("active");
        loadDiscountMarket();
    }
);