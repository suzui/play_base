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