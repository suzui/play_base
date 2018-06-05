var api_tab = function () {
    element.tabDelete('tab', nav_name);
    var api_tab_html = $('#api_tab').html();
    element.tabAdd('tab', {title: nav_name, content: api_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    var startTime = new Date(new Date().setDate(new Date().getDate() - 7));
    var endTime = new Date();
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
    table.reload('api_table', {where: param, page: {curr: 1}});
});

form.on('submit(api_form)', function () {
    var d = {apiId: '', name: '', codes: ''};
    var api_form_html = laytpl($('#api_form').html()).render(d);
    layer_index = layer.open({
        type: 1,
        area: area_6_4,
        content: api_form_html
    });
    form.render('checkbox');
});
form.on('submit(api_add)', function (data) {
    var param = data.field;
    var codes = [], access = [];
    $.each($("input:checkbox[name='code']:checked"), function (index, item) {
        codes.push($(item).val());
        access.push($(item).attr('title'));
    });
    param['codes'] = codes.join(',');
    param['access'] = access.join(',');
    $.post('/back/api/add', param, function (result, status) {
        api_table();
    });
});

form.on('submit(api_list_del)', function () {
    var checkStatus = table.checkStatus('api_table');
    var apiIds = checkStatus.data.map(function (item) {
        return item.apiId;
    });
    layer.confirm('确定删除批量权限', function (index) {
        $.post('/back/api/dels', {apiIds: JSON.stringify(apiIds)}, function (result, status) {
            api_table();
        });
    });
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
    }
});