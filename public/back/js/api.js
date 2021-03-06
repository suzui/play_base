var api_tab = function () {
    element.tabDelete('tab', nav_name);
    var api_tab_html = $('#api_tab').html();
    element.tabAdd('tab', {title: nav_name, content: api_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    var startTime = new Date(new Date().setDate(new Date().getDate() - 3));
    var endTime = new Date(new Date().setDate(new Date().getDate() + 1));
    laydate.render({elem: '#apiStartTime', type: 'datetime', value: startTime});
    laydate.render({elem: '#apiEndTime', type: 'datetime', value: endTime});
    form.render('checkbox');
    api_table();
}

var api_table = function () {
    table.render(table_param_page('api', "/back/api/page"));
}

form.on('submit(api_search)', function (data) {
    var param = data.field;
    param.startTime = new Date(param.startTime).getTime();
    param.endTime = new Date(param.endTime).getTime();
    param.error = param.error == 1 ? 1 : 0;
    param.mock = param.mock == 1 ? 1 : 0;
    table.reload('api_table', {where: param, page: {curr: 1}});
});

table.on('tool(api_table)', function (obj) {
    var e = obj.event;
    var d = obj.data;
    if (e === 'view') {
        $.post('/back/api/info', {apiId: d.apiId}, function (result, status) {
            d = result.data;
        });
        var api_form_html = laytpl($('#api_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_8_8,
            content: api_form_html
        });
        element.render("collapse");
        layui.code({
            title: '', skin: 'notepad'
        });

        form.on('submit(api_mock)', function (data) {
            $.post('/back/api/mock', {
                apiId: data.field.apiId,
                url:data.field.url,
                header: data.field.header,
                param: data.field.param
            }, function (result, status) {
            });
            return false;
        });

    } else if (e === 'mock') {
        $.post('/back/api/mock', {apiId: d.apiId}, function (result, status) {
        });
    }
});