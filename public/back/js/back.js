var $ = layui.jquery;
var jQuery = layui.jquery;
var element = layui.element;
var form = layui.form;
var laytpl = layui.laytpl;
var tree = layui.tree;
var table = layui.table;
var layer = layui.layer;
var upload = layui.upload;
var laydate = layui.laydate;
var carousel = layui.carousel;
var photos = layui.photos;
var E = window.wangEditor;

var area_8_8 = ['800px', '800px'], area_7_8 = ['700px', '800px'], area_6_8 = ['600px', '800px'],
    area_8_7 = ['800px', '700px'], area_7_7 = ['700px', '700px'], area_6_7 = ['600px', '700px'],
    area_8_6 = ['800px', '600px'], area_7_6 = ['700px', '600px'], area_6_6 = ['600px', '600px'],
    area_8_5 = ['800px', '500px'], area_7_5 = ['700px', '500px'], area_6_5 = ['600px', '500px'],
    area_8_4 = ['800px', '400px'], area_7_4 = ['700px', '400px'], area_6_4 = ['600px', '400px'],
    area_8_3 = ['800px', '300px'], area_7_3 = ['700px', '300px'], area_6_3 = ['600px', '300px'];

var ACCESS = JSON.parse($('#access').val());

var layer_index = null;
var nav_name = null;
var nav_type = null;
var editor = null;

element.on('nav(nav)', function (elem) {
    nav_name = elem.text();
    nav_type = elem.attr("data");
    window[nav_type + '_tab']();
});

var table_param = function (id, data) {
    var param = {
        id: id + '_table',
        elem: '#' + id + '_table',
        data: data,
        page: true,
        limits: [10, 30, 50, 100],
        limit: 30,
        height: 'full-10',
        even: true,
        cols: [window[id + '_table_title']]
    }
    return param;
}

var table_param_page = function (id, url) {
    var param = {
        id: id + '_table',
        elem: '#' + id + '_table',
        page: true,
        url: url,
        request: {
            pageName: 'page' //页码的参数名称，默认：page
            , limitName: 'size' //每页数据量的参数名，默认：limit
        },
        response: {
            statusName: 'code' //数据状态的字段名称，默认：code
            , statusCode: 0 //成功的状态码，默认：0
            , msgName: 'msg' //状态信息的字段名称，默认：msg
            , countName: 'count' //数据总数的字段名称，默认：count
            , dataName: 'data' //数据列表的字段名称，默认：data
        },
        done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            console.log(res);
            //得到当前页码
            console.log(curr);
            //得到数据总量
            console.log(count);
        },
        limits: [10, 30, 50, 100],
        limit: 30,
        height: 'full-10',
        even: true,
        cols: [window[id + '_table_title']]
    }
    return param;
}

var image_upload = function (upload_id) {
    upload.render({
        elem: '#' + upload_id,
        url: 'http://oss.iclass.cn/formImage',
        field: 'qqfile',
        data: {bucketName: 'smallfiles', source: 'WEB'},
        done: function (result) {
            $('#' + upload_id + '_img').attr('src', result.html);
            $('#' + upload_id + '_input').val(result.html);
        }
    });
}

var wang_editor = function (editor_id) {
    editor = new E('#' + editor_id);
    editor.customConfig.uploadImgMaxSize = 3 * 1000 * 1000;
    editor.customConfig.customAlert = function (info) {
        layer.msg(info);
    }
    editor.customConfig.customUploadImg = function (files, insert) {
        var formData = new FormData();
        formData.append('qqfile', files[0]);
        formData.append('bucketName', 'smallfiles');
        formData.append('source', 'WEB');
        $.ajax({
            url: 'http://oss.iclass.cn/formImage',
            type: 'POST',
            data: formData,
            async: false,
            contentType: false,
            processData: false,
            success: function (result) {
                insert($.parseJSON(result).html);
            },
            error: function (result) {

            }
        });
    }
    editor.create();
}

$(document).ajaxSuccess(function (event, request, settings) {
    var result = request.responseJSON;
    if (result.status == 'succ') {
        layer.close(layer.index)
    } else {
        if (result.message) {
            layer.msg(result.message);
        }
    }
});

$.ajaxSetup({
    async: false
});

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}