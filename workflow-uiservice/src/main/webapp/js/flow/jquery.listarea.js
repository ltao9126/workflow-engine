function AddNewCategory(e) {
    e = e || window.event;
    //添加一行
    var i = '';
    i++;
    var keyId = "txtName" + i;
    var valueId = "txtName" + i;
    var deleteId = "btnDelete" + i;
    var newTr = $(e.target).prev()[0].insertRow();
    newTr
    //添加列
    var keyId = newTr.insertCell();
    var valueId = newTr.insertCell();
    var deleteTd = newTr.insertCell();
    //设置列内容和属性
    keyId.innerHTML = '<input id="' + keyId + '" type="text" width="190px" class="keys"/>';
    valueId.innerHTML = '<input id="' + valueId + '" type="text" width="190px" class="values"/>'
    deleteTd.innerHTML = '<input id="' + deleteId + '" type="button" value="Delete" class="delete" onclick="DelRow(event)" />';
}

/*获取value*/
function AddNewValue(e) {
    e = e || window.event;
    //添加一行
    var i = '';
    i++;
    var valueId = "txtName" + i;
    var deleteId = "btnDelete" + i;
    var newTr = $(e.target).prev()[0].insertRow();

    //添加列
    var valueId = newTr.insertCell();
    var deleteTd = newTr.insertCell();
    //设置列内容和属性
    valueId.innerHTML = '<input class="adds" id="' + valueId + '" type="text" width="190px" value=""/>'
    deleteTd.innerHTML = '<input id="' + deleteId + '" type="button" value="Delete" onclick="DelRow(event)" />';
}

function DelRow(e) {
    e = e || window.event;
    var getRow = window.event.srcElement.parentElement.parentElement.rowIndex;
    $(e.target).parent().parent().parent()[0].deleteRow(getRow);
}

var tiana = function (e) {
    e = e || window.event;
    var dataType = $(e.target).attr('data-type');
    if (dataType == 'add-property') {
        // $('.yuancheng.urls').eq(1).append($(".clone-content").eq(0).clone(true).addClass('urls'));
        $(e.target).closest(".modal-footer").before($(".clone-content").eq(0).clone(true).addClass('urls'));
    }
}
var deletes = function (e) {
    e = e || window.event;
    var dataType = $(e.target).attr('data-type');
    if (dataType == 'delete-property') {
        $(e.target).parent().remove();
    }
}

$(function () {
    $("#urlmodal").on('click', '.btn', function () {
        var dataType = $(this).attr('data-type');
        if (dataType == 'add-property') {
            // $('.url').eq(0).append($(".clone-contents").eq(0).clone(true).addClass('url'));
            $('#urlmodal .modal-footer').before($('.clone-yc .clone-contents').clone().addClass('url'));
        }
        var dataType = $(this).attr('data-type');
        if (dataType == 'delete-property') {
            $(this).parent().remove();
        }
    });
});
var xunT = function () {
    var s = $('#urlmodal .modal-footer').before($('.clone-yc .clone-contents')).clone();
    s.clone().addClass('url').val("");
    var dataType = $(this).attr('data-type');
    if (dataType == 'delete-property') {
        $(this).parent().remove();
    }
}

$(function () {
    $("#addValue").click(function () {
        $("#valuemodals").modal("show");
    });
    $("#addRequest").click(function () {
        $("#urlmodals").modal("show");
    });
})