var debug = true;
function loginfo(str){
    if(debug){
        console.log(str);
    }
}
function localStorageSupport() {
    return "localStorage" in window && null !== window.localStorage
}
if(typeof toastr != "undefined" ? true : false){
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "positionClass": "toast-middle-right",
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
}

/**
 * ajax通用调用
 *
 * @param opt ajax选项
 * @param callback ajax回调函数
 * @param returnFlag 自定义接受数据标志
 */
var ajaxCommon = function(opt, callback, returnFlag) {
    // 默认ajax选项
    var options = {
        dataType: "json",
        data: {},
        async: true,
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        cache: true
    };

    // 获取ajax选项
    $.extend(options, opt);

    loginfo(options.url+"\n"+options.data);

    $.ajax({
        url: options.url,
        data: options.data,
        dataType: options.dataType,
        async: options.async,
        type: options.type,
        contentType: options.contentType,
        cache: options.cache,
        headers: {
            "token": $.cookie("Token")
        },
        complete: function(jqXHR, textStatus) {
            var result = eval('(' + jqXHR.responseText + ')');
            loginfo(result);
            // 获取ajax返回的信息
            if(textStatus == "success"){
                // 无需通用跳转，自定义处理
                if(!!returnFlag){
                    if(typeof callback == "function") {
                        callback(result);
                        return false;
                    }
                }
                // 通用处理，200直接返回，其他重新登录 ，-1弹出提示
                if(result.code == 200){
                    // 调用回调函数，以ajax返回的信息为参数
                    if(typeof callback == "function") {
                        callback(result);
                        return false;
                    }
                } else if(result.code == 401 || result.code == 403 || result.code == 500){
                    // 用户token过期或者非法访问，重新登录
                    alert("用户状态过期，请重新登录！");
                    window.location.href = "login.html";
                    return false;
                } else if(result.code == 502){
                    alert("用户已失效！");
                    return false;
                } else if(result.code == 100){
                    // alert("参数缺失!");
                    alert("系统错误!请联系管理员！100");
                    return false;
                } else if(result.code < 0){
                    alert("系统错误!请联系管理员！-1");
                    return false;
                }
            } else {
                // 没调通
                alert("网络繁忙!");
                return false;
            }

            // 如果响应头中包含跳转方式信息，则执行相应跳转操作
            // if("html" == jqXHR.getResponseHeader("Jump-By")) {
            //     document.write(result);
            //     return false;
            // }
        }
    });
};

function getUserId(){
    var result = eval('(' + window.localStorage.getItem("User") + ')');
    if(result == null || !result.userId){
        window.location.href="login.html"
    }
    return result.userId;
}

function getAdminId(){
    var result = eval('(' + window.localStorage.getItem("Admin") + ')');
    return result.userId;
}

function updateTodoJudgeCount(callback){
// 设置待评价的数量
    ajaxCommon({
        url: 'query/toDoJudge?userId=' + getUserId(),
        type: 'GET'
    }, function(obj) {
        if(obj.code == 200){
            var data = obj.data;
            //无数据则显示空白样式
            if(data !== 0){
                $("#toDoJudge", parent.document).text(data);
            } else {
                $("#toDoJudge", parent.document).text("");
            }
            if(typeof callback == "function") {
                callback();
                return false;
            }
        }
    }, false);
}

function commonAlert(msg) {
    layer.alert(msg, {
        skin: 'layui-layer-lan',
        closeBtn: 0,
        shift: 1 //动画类型
    });
}

function postMessage(condition, url, callback){
    ajaxCommon({
        url: url,
        async: false,
        data: JSON.stringify(condition)
    }, function(obj) {
        if(obj.code == 200){
            if(typeof callback == "function") {
                callback(obj.data);
                return false;
            }
        }
    }, false);
}