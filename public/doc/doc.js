$(function () {
    if ($("#json_data")) {
        var raw_json = $("#json_data").val();
        if (!raw_json) {
            return;
        }
        var json = JSON.parse(raw_json);

        $("#json").JSONView(json);

        $("#json-collapsed").JSONView(json, {collapsed: true, nl2br: true});

        $('#collapse-btn').on('click', function () {
            $('#json').JSONView('collapse');
        });

        $('#expand-btn').on('click', function () {
            $('#json').JSONView('expand');
        });
    }
});