$().ready(
    function () {
        preLoad();
    }
);

function preLoad() {
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
    loadPerform();
}

function getID() {
    let url = location.search;
    let args = url.substr(url.indexOf("?") + 1);
    let element = args.split("&");
    let type = element[0].substr(element[0].indexOf("=") + 1);
    let id = element[1].substr(element[1].indexOf("=") + 1);

    showDiv(type);
    if (type === "immediate") {
        $('#calculateImmeBtn').show();
        $('#calculateBtn').hide();
    } else {
        $('#calculateImmeBtn').hide();
        $('#calculateBtn').show();
    }
    return id;
}

/**
 * 加载演出信息
 */
function loadPerform() {
    let id = getID();
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getPerformById",
        dataType: "json",
        async: true,
        data: {
            id: id,
        },
        success: function (result) {
            if (result.succeed) {
                let perform = result.data;
                let normalLeft = perform.normalNum - perform.normalSale;
                let vipLeft = perform.vipNum - perform.vipSale;

                $('#performName').text(perform.performName);
                $('#venueName').text(perform.venueName);
                $('#city').text(perform.city);
                $('#detailPosition').text(perform.detailPosition);
                $('#startTime').text(perform.startTime);
                $('#endTime').text(perform.endTime);
                $('#vipPrice').text(perform.vipPrice);
                $('#normalPrice').text(perform.normalPrice);

                $('#choose').hide();
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
}

let immeType = "vip";
$('#vipBtn').click(
    function () {
        $('#vipBtn').addClass("active");
        $('#normalBtn').removeClass("active");

        immeType = "vip";
        $('#choose').show();
        let id = getID();
        let list = getVIPSeats(id);
        let seatMap = "seat-map";
        $('#seat-map').show();
        $('#normal-seat-map').hide();
        setSeat(seatMap, $('#vipPrice').text(), "vip", list, id);

    }
);

$('#normalBtn').click(
    function () {
        $('#normalBtn').addClass("active");
        $('#vipBtn').removeClass("active");

        immeType = "normal";

        $('#choose').show();
        let id = getID();
        let list = getNormalSeats(id);
        let seatMap = "normal-seat-map";
        $('#seat-map').hide();
        $('#normal-seat-map').show();
        setSeat(seatMap, $('#normalPrice').text(), "normal", list, id);
    }
);

/**
 * 加载vip座
 * @param id
 * @returns {Array}
 */
function getVIPSeats(id) {
    let list = [];
    $.ajax({
        type: "post",
        url: "http://localhost:8080/getVipSeats",
        dataType: "json",
        async: false,
        data: {
            performID: id
        },
        success: function (result) {
            if (result.succeed) {
                list = result.data;
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });

    return list;
}

/**
 * 加载普通座
 * @param id
 * @returns {Array}
 */
function getNormalSeats(id) {
    let list = [];
    $.ajax({
        type: "post",
        url: "http://localhost:8080/getNormalSeats",
        dataType: "json",
        async: false,
        data: {
            performID: id
        },
        success: function (result) {
            if (result.succeed) {
                list = result.data;
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
    return list;
}
/**
 * 获取已选择的Vip座位
 * @param id
 * @param sc
 */
function getVipSelectedSeats(id, sc) {
    let selectedSeat;
    $.ajax({
        type: "post",
        url: "http://localhost:8080/getVipSelectedSeats",
        dataType: "json",
        async: true,
        data: {
            performID: id
        },
        success: function (result) {
            if (result.succeed) {
                selectedSeat = result.data;
                sc.get(selectedSeat).status('unavailable');
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
}

/**
 * 获取已选择的Normal座位
 * @param id
 * @param sc
 */
function getNormalSelectedSeats(id, sc) {
    let selectedSeat;
    $.ajax({
        type: "post",
        url: "http://localhost:8080/getNormalSelectedSeats",
        dataType: "json",
        async: true,
        data: {
            performID: id
        },
        success: function (result) {
            if (result.succeed) {
                selectedSeat = result.data;
                sc.get(selectedSeat).status('unavailable');
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
}

let seats = [];

/**
 * 设定座位
 * @param seatMap
 * @param price
 * @param type
 * @param list
 * @param id
 */
function setSeat(seatMap, price, type, list, id) {
    let firstSeatLabel = 0;
    let num;
    if (type === "normal") {
        firstSeatLabel = list[0];
        list.splice(0, 1);
        num = $('#normalNum');

    } else {
        firstSeatLabel = 1;
        num = $('#vipNum');
    }

    let priceInt = parseInt(price);
    let $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total'),

        counter = parseInt($counter.text()),
        total = parseInt($total.text()),

        sc = $('#' + seatMap).seatCharts({
            map: list,
            seats: {
                f: {
                    price   : priceInt,
                    classes : type ,
                    category: price
                }

            },
            naming : {
                top : false,
                getLabel : function (character, row, column) {
                    return firstSeatLabel++;
                },
            },
            legend : {
                node : $('#legend'),
                items : [
                    [ 'f', 'available',   type === 'normal' ? '普通席' : 'VIP席'],
                    [ 'f', 'unavailable', '已预定']
                ]
            },
            click: function () {
                if (this.status() === 'unavailable') {
                    return 'unavailable';
                }

                if ((counter + sc.find('selected').length) < 6 ){
                    if (this.status() === 'available') {
                        $('<li>'+this.settings.label+'号座位'+'：<br/>价格：<b>$' + price + '</b> <a href="#" class="cancel-cart-item">[删除]</a></li>')
                            .attr('id','cart-item-'+this.settings.id)
                            .data('seatId', this.settings.id)
                            .appendTo($cart);
                        $counter.text(counter + sc.find('selected').length + 1);
                        num.text(sc.find('selected').length + 1);
                        $total.text(total + recalculateTotal(sc, priceInt)+priceInt);

                        reCollect(sc, $("#" + this.settings.id).html());

                        return 'selected';
                    } else if (this.status() === 'selected') {
                        $counter.text(counter + sc.find('selected').length - 1);
                        num.text(sc.find('selected').length - 1);

                        $total.text(total + recalculateTotal(sc, priceInt)-priceInt);

                        $('#cart-item-'+this.settings.id).remove();

                        reCollect(sc, $("#" + this.settings.id).html());

                        return 'available';
                    } else {
                        return this.style();
                    }

                } else {
                    if (this.status() === 'selected') {
                        $counter.text(counter + sc.find('selected').length - 1);
                        num.text(sc.find('selected').length - 1);
                        $total.text(total + recalculateTotal(sc, priceInt)-priceInt);

                        $('#cart-item-'+this.settings.id).remove();

                        return 'available';
                    } else {
                        swal({
                           title: "选座不可超过6张票",
                            type: "warning"
                        });
                        return this.style();
                    }
                }

            }
        });

    $('#selected-seats').on('click', '.cancel-cart-item', function () {
        sc.get($(this).parents('li:first').data('seatId')).click();
    });

    if (type === "normal") {
        getNormalSelectedSeats(id, sc);
    } else {
        getVipSelectedSeats(id, sc);
    }

}

function reCollect(sc, text) {
    let isExist = false;
    seats.splice(0, seats.length);
    sc.find('selected').each(function () {
        if ($("#" + this.settings.id).html() === text) {
            isExist = true;
            return;
        }
        seats.push($("#" + this.settings.id).html());
    });
    if (!isExist) {
        seats.push(text);
    }
    console.log(seats);
}

/**
 * 计算总价
 * @param sc
 * @param price
 * @returns {number}
 */
function recalculateTotal(sc, price) {
    let total = 0;

    sc.find('selected').each(function () {
        total += price;
    });

    return total;
}

function showDiv(showName) {
    $('#choose').hide();
    $('#immediate').hide();

    $('#' + showName).show();
}

$('#calculateBtn').click(function () {
    let price = $('#total').text();
    $('#orderPrice').text(price);
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
                        `<div class="discountView">
                            <div class="col-lg-4">
                            <label style="font-weight: lighter">优惠券编号 : ${discount.id}</label>
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
                        </div>
                        <hr width="550px">`
                    );
                }
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
});

$('#calculateImmeBtn').click(
    function () {
        if (immeType === "vip") {
            $('#vipNum').text($('#number').val());
            $('#total').text($('#vipPrice').text() * $('#vipNum').text());
        } else {
            $('#normalNum').text($('#number').val());
            $('#total').text($('#normalPrice').text() * $('#normalNum').text());
        }
        $('#orderPrice').text($('#total').text());
    }
);

$('#calculateConfirm').click(
    function () {
        let orderRegisterVO = {};

        orderRegisterVO.totalPrice = $('#total').text();
        orderRegisterVO.performID = getID();
        orderRegisterVO.vipNum = $('#vipNum').text();
        orderRegisterVO.normalNum = $('#normalNum').text();
        orderRegisterVO.seat = seats;

        if ($('#discountID').val() === "") {
            orderRegisterVO.discountID = 0;
        } else {
            orderRegisterVO.discountID = $('#discountID').val();
        }

        $.ajax({
            type: "post",
            url: "http://localhost:8080/makeOrder",
            contentType: "application/json",
            data: JSON.stringify(orderRegisterVO),
            success: function (result) {
                if (result) {
                    window.location.href = "userOrder.html";
                }
            },
            error: function (xhr, status, error) {
                console.log(xhr.status);
                console.log(error);
            }
        })
    }
)