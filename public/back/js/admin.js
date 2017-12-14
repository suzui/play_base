var admin_tab = function () {
    element.tabDelete('tab', nav_name);
    var admin_tab_html = $('#admin_tab').html();
    element.tabAdd('tab', {title: nav_name, content: admin_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    admin_table();
}

var admin_table = function (param) {
    $.post('/back/admin/list', param, function (result, status) {
        table.render(table_param('admin', result.data.array));
    })
}

form.on('submit(admin_search)', function (data) {
    admin_table(data.field);
});

form.on('submit(admin_form)', function () {
    var d = {adminId: '', username: '', phone: '', email: '', name: '', avatar: '', intro: ''};
    var admin_form_html = laytpl($('#admin_form').html()).render(d);
    layer_index = layer.open({
        type: 1,
        area: area_7_6,
        content: admin_form_html
    });
    image_upload('admin_avatar');
    wang_editor('admin_intro');
});
form.on('submit(admin_add)', function (data) {
    var param = data.field;
    param['intro'] = editor.txt.html();
    param['password'] = CryptoJS.MD5("Message").toString(CryptoJS.enc.Hex);
    $.post('/back/admin/add', param, function (result, status) {
        admin_table();
    });
});

form.on('submit(admin_list_del)', function () {
    var checkStatus = table.checkStatus('admin_table');
    var adminIds = checkStatus.data.map(function (item) {
        return item.adminId;
    });
    layer.confirm('确定删除批量用户', function (index) {
        $.post('/back/admin/dels', {adminIds: JSON.stringify(adminIds)}, function (result, status) {
            admin_table();
        });
    });
});

table.on('tool(admin_table)', function (obj) {
    var e = obj.event, d = obj.data;
    if (e === 'edit') {
        var admin_form_html = laytpl($('#admin_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_8_6,
            content: admin_form_html
        });
        image_upload('admin_avatar');
        wang_editor('admin_intro');
        editor.txt.html(d.intro);
        form.on('submit(admin_edit)', function (data) {
            var param = data.field;
            param['intro'] = editor.txt.html();
            $.post('/back/admin/edit', param, function (result, status) {
                obj.update(param);
            });
        });
    } else if (e === 'password') {
        var admin_password_form_html = laytpl($('#admin_password_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            content: admin_password_form_html
        });
        form.on('submit(admin_password)', function (data) {
            var param = data.field;
            $.post('/back/admin/password', param, function (result, status) {
            });
        });
    } else if (e === 'auth') {
        $.post('/back/admin/auths', d, function (result, status) {
            d['AUTH'] = result.data.array;
        });
        var admin_auth_form_html = laytpl($('#admin_auth_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_6_4,
            content: admin_auth_form_html
        });
        form.render('checkbox');
        form.on('submit(admin_auth)', function (data) {
            var param = data.field;
            var authIds = [];
            $.each($("input:checkbox[name='auth']:checked"), function (index, item) {
                authIds.push($(item).val());
            });
            param['authIds'] = JSON.stringify(authIds);
            $.post('/back/admin/auth', param, function (result, status) {
            });
        });
    } else if (e === 'del') {
        layer.confirm('确定删除用户', function (index) {
            $.post('/back/admin/del', d, function (result, status) {
                obj.del();
            });
        });
    }
});