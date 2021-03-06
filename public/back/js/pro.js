var pro_tab = function () {
    element.tabDelete('tab', nav_name);
    var pro_tab_html = $('#pro_tab').html();
    element.tabAdd('tab', {title: nav_name, content: pro_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    pro_table();
}

var pro_table = function (param) {
    $.post('/back/pro/list', param, function (result, status) {
        table.render(table_param('pro', result.data.array));
    })
}

form.on('submit(pro_search)', function (data) {
    pro_table(data.field);
});

form.on('submit(pro_form)', function () {
    var d = {proId: '', name: '', location: '', git: '', branch: '', user: '', password: '', playid: '', npmapp: '', url: '', port: '', shell: ''};
    var pro_form_html = laytpl($('#pro_form').html()).render(d);
    layer_index = layer.open({
        type: 1,
        area: area_7_5,
        content: pro_form_html
    });
});
form.on('submit(pro_add)', function (data) {
    var param = data.field;
    $.post('/back/pro/add', param, function (result, status) {
        pro_table();
    });
});

form.on('submit(pro_list_del)', function () {
    var checkStatus = table.checkStatus('pro_table');
    var proIds = checkStatus.data.map(function (item) {
        return item.proId;
    });
    layer.confirm('确定删除批量项目', function (index) {
        $.post('/back/pro/dels', {adminIds: JSON.stringify(proIds)}, function (result, status) {
            pro_table();
        });
    });
});

table.on('tool(pro_table)', function (obj) {
    var e = obj.event, d = obj.data;
    if (e === 'update') {
        layer.confirm('确定更新' + d.name, function (index) {
            $.post('/back/pro/update', d, function (result, status) {

            });
        });
    } else if (e === 'stop') {
        layer.confirm('确定停止' + d.name, function (index) {
            $.post('/back/pro/stop', d, function (result, status) {

            });
        });
    } else if (e === 'start') {
        layer.confirm('确定启动' + d.name, function (index) {
            $.post('/back/pro/start', d, function (result, status) {

            });
        });
    } else if (e === 'restart') {
        layer.confirm('确定重启' + d.name, function (index) {
            $.post('/back/pro/restart', d, function (result, status) {

            });
        });
    } else if (e === 'git') {
        $.post('/back/pro/git', d, function (result, status) {
            d = result.data;
        });
        var pro_git_form = laytpl($('#pro_git_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_8_8,
            content: pro_git_form
        });
        element.render("collapse");
        layui.code({
            title: '', skin: 'notepad'
        });
    } else if (e === 'edit') {
        var pro_form_html = laytpl($('#pro_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_7_5,
            content: pro_form_html
        });
        form.on('submit(pro_edit)', function (data) {
            var param = data.field;
            $.post('/back/pro/edit', param, function (result, status) {
                obj.update(param);
            });
        });
    } else if (e === 'del') {
        layer.confirm('确定删除项目', function (index) {
            $.post('/back/pro/del', d, function (result, status) {
                obj.del();
            });
        });
    }
});