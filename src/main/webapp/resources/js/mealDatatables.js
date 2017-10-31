var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
           "url": ajaxUrl,
           "dataSrc": ""
        },
        "paging":false,
        "info":true,

        "columns":[
            {
                "data": "dateTime",
              "render": function (data, type, row) {
                  data = data.replace('T', ' ');
                  return data;
               }
            },
            {
                "data":"description"
            },
            {
                "data": "calories"
            },
            {
                "orderAble":false,
                "defaultContent":"",
                "render":renderEditBtn
            },
            {
                "orderAble":false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [[
            0,"desc"
        ]],

        "createdRow": function (row, data, dataIndex ) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        },

       "initComplete":makeEditable
    });
});
$(function () {
    $('#dateTimePicker').datetimepicker({
        format: 'Y-m-d H:i'
    });

    var startDate = $('#startDate');
    var endDate = $('#endDate');
    var startTime = $('#startTime');
    var endTime = $('#endTime');
    startDate.datetimepicker({
        timepicker:false,
       format: 'Y-m-d'
    });
    endDate.datetimepicker({
        timepicker:false,
        format: 'Y-m-d'
    });
    startTime.datetimepicker({
        datepicker:false,
        format: 'H:i'
    });
    endTime.datetimepicker({
        datepicker:false,
        format: 'H:i'
    });

});
