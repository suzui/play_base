var api_tab = function () {
    element.tabDelete('tab', nav_name);
    var api_tab_html = $('#api_tab').html();
    element.tabAdd('tab', {title: nav_name, content: api_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    api_table();
}

var api_table = function (param) {
    $.post('/back/api/list', param, function (result, status) {
        table.render(table_param('api', result.data.array));
    })
}

form.on('submit(api_search)', function (data) {
    api_table(data.field);
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
    if (e === 'edit') {
        var api_form_html = laytpl($('#api_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_6_4,
            content: api_form_html
        });
        form.render('checkbox');
        form.on('submit(api_edit)', function (data) {
            var param = data.field;
            var codes = [], access = [];
            $.each($("input:checkbox[name='code']:checked"), function (index, item) {
                codes.push($(item).val());
                access.push($(item).attr('title'));
            });
            param['codes'] = codes.join(',');
            param['access'] = access.join(',');
            $.post('/back/api/edit', param, function (result, status) {
            });
        });
    } else if (e === 'del') {
        layer.confirm('确定删除权限', function (index) {
            $.post('/back/api/del', d, function (result, status) {
                obj.del();
            });
        });
    }
});