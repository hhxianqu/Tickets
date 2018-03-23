
$().ready(function () {
    showSetion("audit", "allVenues", "performWaitToCal", "countCharts");
    showAudit("venueAudit", "venueChangeAudit", "performAudit");
    showAuditVenue();

});

$('#auditBtn').click(
    function () {
        showSetion("audit", "allVenues", "performWaitToCal", "countCharts");
        showAudit("venueAudit", "performAudit");
        showAuditVenue();
    }
);

$('#performWaitToCalBtn').click(
    function () {
        showSetion("performWaitToCal", "allVenues", "audit", "countCharts");
        showSettled("unSettledPerform", "settledPerform");
        getUnsettledPerform();
    }
);

function drawBar(data) {
    let myChart = echarts.init(document.getElementById('chart'));
    option = {
        title: {
            text: '会员等级分布图'
        },
        xAxis: {
            type: 'category',
            data: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: data,
            type: 'bar'
        }]
    };
    myChart.setOption(option);
}

$('#countChartBtn').click(
    function () {
        showSetion("countCharts", "performWaitToCal", "allVenues", "audit");
        $.ajax({
            type: 'GET',
            url: '/getMemberLevel',
            async: true,
            data: {},
            success: function (result) {
                drawBar(result.data);
            },
            error: function (xhr) {
            }
        });

        $.ajax({
            type: 'GET',
            url: '/getManagerBalance',
            async: true,
            data: {},
            success: function (result) {
                $('#balance').text(result.data);
            },
            error: function (xhr) {
            }
        });

    }
);

$('#allVenuesBtn').click(
    function () {
        showSetion("allVenues", "audit", "performWaitToCal", "countCharts");
        $.ajax({
            type: "get",
            url: "http://localhost:8080/allVenues",
            dataType: "json",
            success: function (result) {
                if (result.succeed) {
                    $('#allVenues').empty();
                    for (let i = 0; i < result.data.length; i++) {
                        let venue = result.data[i];
                        $('#allVenues').append(
                            '<div class="col-lg-6">' +
                            '<div class="orderView" style="width: 565px">' +
                            '<div class="col-lg-4">' +
                            '<img class="imageView" src="images/venue-' + (i+1) + '.jpg">' +
                            '</div>' +
                            '<div class="col-lg-8">' +
                            '<h3>' + venue.venueName +  '</h3>' +
                            '<span>可容纳 : </span><label>' + venue.seat +  '</label><br>\n' +
                            '<span>所在地 : </span><label>' + venue.city +  '</label><br>\n' +
                            '<span>详细地址 : </span><label>' + venue.detailPosition + '</label><br>\n' +
                            '<span>联系方式 : </span><label>' + venue.venueEmail + '</label>\n' +
                            '</div>' +
                            '</div></div></div>'
                        );
                    }
                }
            }
        })
    }
);
/**
 * 显示待审核信息
 */
$('#performAuditBtn').click(
    function () {
        showAudit("performAudit", "venueAudit", "venueChangeAudit");
        showAuditPerform();
    }
);

$('#venueChangeAuditBtn').click(
    function () {
        showAudit("venueChangeAudit", "venueAudit", "performAudit");
        showChangedVenue();
    }
);

$('#venueAuditBtn').click(
    function () {
        showAudit("venueAudit", "performAudit", "venueChangeAudit");
        showAuditVenue();
    }
);
function showAudit(showName, hideName, hideName2) {
    $('#' + showName).show();
    $('#' + hideName).hide();
    $('#' + hideName2).hide();

    $('#' + showName + 'Btn').addClass("active");
    $('#' + hideName + 'Btn').removeClass("active");
    $('#' + hideName2 + 'Btn').removeClass("active");
}

/**
 * 显示待审核场馆
 */
function showAuditVenue() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/auditedVenue",
        dataType: "json",
        data: {
            audited: false,
        },
        success: function (result) {
            if (result.succeed) {
                $('#empty').hide();
                $('#venueAudit').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let venue = result.data[i];
                    $('#venueAudit').append(
                    `<div class="orderView">
                        <div class="col-lg-8">
                            <h3>${venue.venueName}</h3>
                            <span>可容纳 : </span><label>${venue.seat}</label><br>
                            <span>所在地 : </span><label>${venue.city}</label><br>
                            <span>详细地址 : </span><label>${venue.detailPosition}</label><br>
                            <span>联系方式 : </span><label>${venue.venueEmail}</label>
                        </div>
                        <div class="col-lg-4" align="right">
                            <button class="btn btn-block" id="pass${venue.id}">合 格</button>
                            <br>
                            <button class="btn btn-block" id="notPass${venue.id}">不合格</button>
                        </div>
                    </div>
                    <script>
                        $("#pass${venue.id}").click(
                            function() { 
                                passVenue(true, ${venue.id}) 
                            }
                        );
                        $("#notPass${venue.id}").click(
                            function() { 
                                passVenue(false, ${venue.id}) 
                            }
                        );
                     </script>`
                    );
                }
                if (result.data.length === 0) {
                    $('#empty').show();
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
 * 通过场馆
 * @param isPass
 * @param id
 */
function passVenue(isPass, id) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/passVenue",
        data: {
            pass: isPass,
            id: id
        },
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

/**
 * 显示待审核的演出
 */
function showAuditPerform() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/auditedPerform",
        dataType: "json",
        data: {
            audited: false,
        },
        success: function (result) {
            if (result.succeed) {
                if (result.data.length === 0) {
                    $('#emptyPerform').show();
                }
                $('#performAudit').empty();
                $('#emptyPerform').hide();
                for (let i = 0; i < result.data.length; i++) {
                    let perform = result.data[i];
                    $('#performAudit').append(
                        '<div class="orderView">' +
                        '<div class="col-lg-8">' +
                        '<h3>' + perform.performName + '</h3>' +
                        '<label> ' + perform.startTime + ' - ' + perform.endTime + ' </label>' +
                        '<label> ' + perform.venueName + '</label><br>\n' +
                        '<span> Vip票 : <label>' + perform.vipNum + '</label>张</span>\n' +
                        '<span> 普通票 : <label>' + perform.normalNum + '</label>张</span><br>\n' +
                        '<span> Vip票价 : <label>' + perform.vipPrice + '</label>元/张</span>\n' +
                        '<span> 普通票价 : <label>' + perform.normalPrice + '</label>元/张</span>\n' +
                        '</div>\n' +
                        '<div class="col-lg-4" align="right">\n' +
                        '<button class="btn btn-block" id="pass'+ perform.id +'">合格</button>\n' +
                        '<br>\n' +
                        '<button class="btn btn-block" id="notPass'+ perform.id +'">不合格</button>\n' +
                        '</div>\n' +
                        '</div>'+
                        '<script>' +
                        '$("#pass' + perform.id + '").click(function() { ' +
                        'passPerform(true, ' + perform.id + ') });' +
                        '$("#notPass' + perform.id + '").click(function() { ' +
                        'passPerform(false, ' + perform.id + ') });' +
                        '</script>'
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
 * 通过演出
 * @param isPass
 * @param id
 */
function passPerform(isPass, id) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/passPerform",
        dataType: "json",
        data: {
            pass: isPass,
            id: id
        },
        success: function (result) {
            if (result.succeed) {
                showAuditPerform();
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

/**
 * 显示修改信息的场馆
 */
function showChangedVenue() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/auditedChangedVenue",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                $('#venueChangeAudit').empty();
                if (result.data.length === 0) {
                    $('#emptyChange').show();
                } else {
                    $('#emptyChange').hide();
                    for (let i = 0; i < result.data.length; i++) {
                        let venue = result.data[i];
                        $('#venueChangeAudit').append(
                            `<div class="orderView" style="width: 850px; height: 225px" >
                        <div class="col-lg-3">
                            <h3>${venue.venueName}</h3>
                            <span>可容纳 : </span><label>${venue.seat}</label><br>
                            <span>所在地 : </span><label>${venue.city}</label><br>
                            <span>详细地址 : </span><label>${venue.detailPosition}</label><br>
                            <span>联系方式 : </span><label>${venue.email}</label>
                        </div>
                        <div class="col-lg-2 text-center" style="margin-top: 10%; color: #2b669a; font-size: 18px">
                            <label> -> 修改为 -> </label> 
                        </div>
                        <div class="col-lg-3">
                            <h3>${venue.venueNameChange}</h3>
                            <span>可容纳 : </span><label>${venue.seatChange}</label><br>
                            <span>所在地 : </span><label>${venue.cityChange}</label><br>
                            <span>详细地址 : </span><label>${venue.detailPositionChange}</label><br>
                            <span>联系方式 : </span><label>${venue.emailChange}</label>
                        </div>
                        <div class="col-lg-4" align="right">
                            <button class="btn btn-block" id="pass${venue.id}">通过</button>
                            <br>
                            <button class="btn btn-block" id="notPass${venue.id}">未通过</button>
                        </div>
                    </div>
                    <script>
                        $("#pass${venue.id}").click(
                            function() { 
                               passChange(true, ${venue.id});
                            }
                        );
                        $("#notPass${venue.id}").click(
                            function() { 
                               passChange(false, ${venue.id})
                            }
                        );
                     </script>`
                        );
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
 * 通过修改
 * @param pass
 * @param id
 */
function passChange(pass, id) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/passChangedVenue",
        dataType: "json",
        async: false,
        data: {
            pass: pass,
            id: id
        },
        success: function (result) {
            if (result.succeed) {
                showChangedVenue();
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}

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

function showSettled(showName, hideName) {
    $('#' + showName + "Btn").addClass("active");
    $('#' + hideName + "Btn").removeClass("active");
    $('#' + showName).show();
    $('#' + hideName).hide();
}

/**
 * 加载已结算的演出
 */
function getSettledPerform() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/settledPerformAll",
        dataType: "json",
        success: function (result) {
            $('#settledPerform').empty();
            for (let i = 0; i < result.data.length; i++) {
                let totalSale = result.data[i].vipSale * result.data[i].vipPrice
                    + result.data[i].normalSale * result.data[i].normalPrice;
                let divide = (totalSale * 0.3).toFixed(0);
                let pay = totalSale - divide;
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
                            <span>实际分成所得 : <label>${divide}</label>元</span>
                        </div>
                        <div>
                            <span > 已结算
                                <label>${pay}元 </label> 给
                                <label style="color: #2b669a">${result.data[i].venueName}</label>
                            </span>
                        </div>
                        <hr width="800px">
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
 * 加载未结算的演出
 */
function getUnsettledPerform() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/unsettledPerformAll",
        dataType: "json",
        success: function (result) {
            if (result.succeed) {
                if (result.data.length === 0) {
                    $('#settled').show();
                }
                $('#settled').hide();
                $('#unSettledPerform').empty();
                for (let i = 0; i < result.data.length; i++) {
                    let totalSale = result.data[i].vipSale * result.data[i].vipPrice
                        + result.data[i].normalSale * result.data[i].normalPrice;
                    let divide = (totalSale * 0.3).toFixed(0);
                    let pay = totalSale - divide;
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
                            <span>实际分成所得 : <label>${divide}</label>元</span>
                        </div>
                        <div class="col-lg-9">
                            <span > 应结算
                                <label>${pay}元</label>给
                                <label style="color: #2b669a">${result.data[i].venueName}</label>
                            </span>
                        </div>
                        <div class="col-lg-3">
                            <button class="btn btn-link" id="pay${i}">核算无误，确认付款</button>
                        </div>
                        <hr width="800px">
                    </div>
                    <script>
                        $('#pay${i}').click(
                            function() {
                              pay(${result.data[i].id}, ${divide}, ${pay});
                            }
                        );
                    </script>
                `);
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
 * 结算演出所得费用
 * @param performID
 * @param divide
 * @param pay
 */
function pay(performID, divide, pay) {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/pay",
        dataType: "json",
        async: true,
        data: {
            performID: performID,
            divide: divide,
            pay: pay
        },
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


function showSetion(showName, hideName, hideName2, hideName3) {
    $('#' + showName).show();
    $('#' + hideName).hide();
    $('#' + hideName2).hide();
    $('#' + hideName3).hide();
}