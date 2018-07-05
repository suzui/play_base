var job_tab = function () {
    element.tabDelete('tab', nav_name);
    var job_tab_html = $('#job_tab').html();
    element.tabAdd('tab', {title: nav_name, content: job_tab_html, id: nav_name});
    element.tabChange('tab', nav_name);
    var startTime = new Date(new Date().setDate(new Date().getDate() - 7));
    var endTime = new Date();
    laydate.render({elem: '#jobStartTime', type: 'datetime', value: startTime});
    laydate.render({elem: '#jobEndTime', type: 'datetime', value: endTime});
    form.render('checkbox');
    job_table();
}

var job_table = function () {
    table.render(table_param_page('job', "/back/job/page"));
}

form.on('submit(job_search)', function (data) {
    var param = data.field;
    param.startTime = new Date(param.startTime).getTime();
    param.endTime = new Date(param.endTime).getTime();
    param.error = param.error == 1 ? 1 : 0;
    table.reload('job_table', {where: param, page: {curr: 1}});
});

form.on('submit(job_form)', function () {
    var d = {jobId: '', name: '', codes: ''};
    var job_form_html = laytpl($('#job_form').html()).render(d);
    layer_index = layer.open({
        type: 1,
        area: area_6_4,
        content: job_form_html
    });
    form.render('checkbox');
});
form.on('submit(job_add)', function (data) {
    var param = data.field;
    var codes = [], access = [];
    $.each($("input:checkbox[name='code']:checked"), function (index, item) {
        codes.push($(item).val());
        access.push($(item).attr('title'));
    });
    param['codes'] = codes.join(',');
    param['access'] = access.join(',');
    $.post('/back/job/add', param, function (result, status) {
        job_table();
    });
});

form.on('submit(job_list_del)', function () {
    var checkStatus = table.checkStatus('job_table');
    var jobIds = checkStatus.data.map(function (item) {
        return item.jobId;
    });
    layer.confirm('确定删除批量权限', function (index) {
        $.post('/back/job/dels', {jobIds: JSON.stringify(jobIds)}, function (result, status) {
            job_table();
        });
    });
});

table.on('tool(job_table)', function (obj) {
    var e = obj.event;
    var d = obj.data;
    if (e === 'view') {
        $.post('/back/job/info', {jobId: d.jobId}, function (result, status) {
            d = result.data;
        });
        var job_form_html = laytpl($('#job_form').html()).render(d);
        layer_index = layer.open({
            type: 1,
            area: area_8_8,
            content: job_form_html
        });
        element.render("collapse");
        layui.code({
            title: '', skin: 'notepad'
        });
    }
});