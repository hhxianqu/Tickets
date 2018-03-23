/**
 * 记录当前演出类型
 */
var performType;

$().ready(function () {
    let url = location.search;
    preLoad(url);
    $.ajax({
        type: "get",
        url: 'http://localhost:8080/check',
        dataType:"json",
        success: function (result) {
            if (result.succeed && result.message === "user") {
                document.getElementById('usernameToShow').innerText = result.data.username;
                $('#welcome').show();
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    });
});

/**
 * 预加载
 * @param url
 */
function preLoad(url) {
    if (url.indexOf("?") !== -1) {
        performType = url.substr(url.indexOf("=") + 1);
    }
    $('#' + performType).addClass("active");
    loadPerform(performType, "all");
    getCity(performType);
}

/**
 * 加载演出信息
 * @param type
 * @param city
 */
function loadPerform(type, city) {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/perform",
        data: {
            type: type,
            city: city,
        },
        dataType:"json",
        success: function (result) {
            if (result.succeed) {
                console.log(result);
                $('#perform').empty();
                for(let i = 0; i < result.data.length; i++ ) {
                    $('#perform').append(
                        `<div class="col-md-6">
                            <div class="performanceView">
                                <div class="col-lg-8">
                                    <h3>${result.data[i].performName}</h3>
                                    <h5>${result.data[i].startTime} - ${result.data[i].endTime}</h5>
                                    <span class="position">${result.data[i].venueName}</span><br>
                                    <label>${result.data[i].city} - ${result.data[i].detailPosition}</label>
                                </div>
                                <div class="col-lg-4 button" align="right">
                                    <button class="btn btn-primary" id="choose${result.data[i].id}">选座订票</button>
                                    <button class="btn btn-info" id="immediate${result.data[i].id}">立即购买</button>
                                </div>
                            </div>
                        </div>
                        <script>
                            $('#choose${result.data[i].id}').click(
                                function() {
                                    window.location.href = "makeOrder.html?type=choose&id=" + ${result.data[i].id};
                                }
                            );
                            $("#immediate${result.data[i].id}").click(
                                function() {
                                  window.location.href = "makeOrder.html?type=immediate&id=" + ${result.data[i].id};
                                }
                            );
                        </script>`
                    );
                }
            }
        },
        error: function(xhr, status, error) {
            console.log(xhr.status);
            console.log(error);
        }
    })
}


/**
 * 根据类型获取对应城市的演出
 * @param type
 */
function getCity(type) {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getCity",
        data: {
          type: type,
        },
        success: function (result) {
            $('#city').empty();
            if (result.succeed) {
                for (let i = 0; i < result.data.length; i++ ) {
                    $('#city').append(
                        `<th>
                            <a class="btn cityCondition" id="${i}">${result.data[i]}</a>
                         </th>
                         <script>
                            $("#${i}").click(
                                function() { 
                                    document.getElementById("cityChoose").innerText = "${result.data[i]}";
                                    loadPerform(performType, "${result.data[i]}"); 
                                }
                            );
                         </script>`
                    )
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
 * 设置active
 * @param showName
 * @param hideName
 * @param hideName2
 * @param hideName3
 * @param hideName4
 * @param hideName5
 */
function showActive(showName, hideName, hideName2, hideName3, hideName4, hideName5) {
    $('#' + showName).addClass("active");
    $('#' + hideName).removeClass("active");
    $('#' + hideName2).removeClass("active");
    $('#' + hideName3).removeClass("active");
    $('#' + hideName4).removeClass("active");
    $('#' + hideName5).removeClass("active");
}

$('#concert').click(
    function () {
        showActive('concert', 'opera', 'musical', 'dance', 'sports', 'exhibit');
        loadPerform("concert", "all");
        performType = "concert";
        getCity(performType);
    }
);

$('#opera').click(
    function () {
        showActive('opera', 'musical', 'dance', 'sports', 'exhibit', 'concert');
        loadPerform("opera", "all");
        performType = "opera";
        getCity(performType);
    }
);

$('#musical').click(
    function () {
        showActive('musical', 'dance', 'sports', 'exhibit', 'concert', 'opera');
        loadPerform("musical", "all");
        performType = "musical";
        getCity(performType);
    }
);

$('#sports').click(
    function () {
        showActive('sports', 'exhibit', 'concert', 'opera', 'musical', 'dance');
        loadPerform("sports", "all");
        performType = "sports";
        getCity(performType);
    }
);

$('#dance').click(
    function () {
        showActive('dance', 'sports', 'exhibit', 'concert', 'opera', 'musical');
        loadPerform("dance","all");
        performType = "dance";
        getCity(performType);
    }
);

$('#exhibit').click(
    function () {
        showActive('exhibit', 'sports', 'dance', 'concert', 'opera', 'musical');
        loadPerform("exhibit", "all");
        performType = "exhibit";
        getCity(performType);
    }
);
$('#all').click(
    function () {
        document.getElementById("cityChoose").innerText = "全部城市";
        loadPerform(performType, "all");
    }
);

