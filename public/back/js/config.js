var config_tab = function () {
    element.tabDelete('tab', nav_name);
    var config_tab_html = $('#config_tab').html();
    element.tabAdd('tab', {title: nav_name, content: config_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    config_table();
}

var config_table = function (param) {
    $.post('/back/config/list', param, function (result, status) {
        table.render(table_param('config', result.data.array));
    })
}

form.on('submit(config_search)', function (data) {
    config_table(data.field);
});

table.on('tool(config_table)', function (obj) {
    var e = obj.event, d = obj.data;
    if (e === 'edit') {
        var config_form_html = laytpl($('#config_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_7_4,
            content: config_form_html
        });
        form.on('submit(config_edit)', function (data) {
            var param = data.field;
            $.post('/back/config/edit', param, function (result, status) {
                obj.update(param);
            });
        });
    }
});