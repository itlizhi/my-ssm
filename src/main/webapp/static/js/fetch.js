var Axios = axios.create({
    timeout: 30000,
    responseType: "json",
    withCredentials: true, // 是否允许带cookie
    headers: {
        "Content-Type": "application/json;charset=utf-8"
    }
});

var pending = [] //定义
var CancelToken = axios.CancelToken;
var removePending = (config, cancelToken) => {
    var url = config.url + '&' + config.method

    if (pending.indexOf(url) >= 0) {
        if (cancelToken) {
            cancelToken(url + "：request execution....") // 执行取消操作
        } else {
            //从数组中移除
            setTimeout(function () {
                pending.splice(pending.indexOf(url), 1)
            }, 500)
        }
    } else {
        if (cancelToken) {
            pending.push(url)
        }
    }
}

//添加请求拦截器
Axios.interceptors.request.use(function (config) {
    // 请求前
    if (config.method === 'post') {
        config.cancelToken = new CancelToken((c) => {
            removePending(config, c)
        })
    }
    return config


}, function (error) {
    // 请求出错
    return Promise.reject(error);

});

//返回状态判断(添加响应拦截器)
Axios.interceptors.response.use(function (response) {
    if (response.config.method == "post") {

        removePending(response.config)

        new Vue().$message({
            showClose: true,
            message: "操作成功",
            type: 'success'
        });

    }

    return response.data;

}, function (error) {
    pending = [];
    console.log(error);
    if (!error.response) {
        return Promise.reject(error);
    }
    if (error.response.status === 400) {
        var result = error.response.data;
        switch (result.code) {
            case 201:
                if (result.jsCallBack) { //自定义js
                    eval(result.callBack)
                }
                break;
            case 400:
                if (result.message) {
                    new Vue().$message({
                        showClose: true,
                        message: result.message,
                        type: 'error'
                    });
                }
                break;
            default:
        }
    }
    else if (error.response.status === 404) {
        new Vue().$message({
            showClose: true,
            message: "请求地址不存在",
            type: 'error'
        });
    }
    else if (error.response.status === 500) {
        new Vue().$message({
            showClose: true,
            message: "server error 500",
            type: 'error'
        });
    }
    return Promise.reject(error);

});

