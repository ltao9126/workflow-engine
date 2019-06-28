/**
 * Created by Administrator on 2017/7/24.
 */
//url
//var addFlowUrl='/flow-page/hlsmecs/Flow/flow';//增加流程
//var searchFlowUrl='/flow-page/hlsmecs/Flow/flow';//查找某流程,参数为id=“”
//
//var deleteFlowUrl='/flow-page/hlsmecs/Flow/flow/delete';//删除流程
//var allFlowUrl='http://localhost:8080/workflow-uiservice/uinode/flowName.do';//查找所有流程
var addFlowUrl = '/flow-page/hlsmecs/Flow/flow';//增加流程
//var searchFlowUrl = '/flow-page/hlsmecs/Flow/flow';//查找某流程,参数为id=“”

//var deleteFlowUrl = '/flow-page/hlsmecs/Flow/flow/delete';//删除流程
//var allFlowUrl = 'dingyu/uinode/flowName.do';//查找所有流程(丁钰)
//var searchFlowUrl = 'dingyu/uinode/flowData.do';//查找某流程,参数为flowKey=“”
//var deleteFlowUrl = '/dingyu/uinode/deleteFlow.do';//删除流程(丁钰)
var allFlowUrl = '/local/workflow-uiservice/uinode/flowName.do';//查找所有流程(本地)
var searchFlowUrl = '/local/workflow-uiservice/uinode/flowData.do';//查找某流程,参数为flowKey=“”
var deleteFlowUrl = '/local/workflow-uiservice/uinode/deleteFlow.do';//删除流程(丁钰)
var attributeUrl = 'busActivityList.do';  //业务活动列表
var attributeData = 'busActivityData.do'; //业务活动查看详情
var attributeUpdate = 'busActivityUpdata.do'; //业务活动修改
var attributeDelete = 'busActivityDelete.do'; //业务活动删除

//list Dom
var title = '<thead><th class="text-center">序号</th><th class="text-center">业务活动名称</th><th class="text-center"></th><th class="text-center">操作</th></thead>';
var page = "";
var size = "";
var totals = "";
//请求列表数据（业务活动）
function getFlowData(url) {
    var list = $('#flow_list');
    var ul = $('#page-ul');
    $.get(url + '?page=' + page + '&size=' + size,
        function (res) {
            localStorage.data = res;
            res = JSON.parse(res);
            if (res.status.success == "OK") {
                data = res.data.flow;
                page = res.data.page;
                size = res.data.size;
                totals = res.data.total;

                //处理数据
                ul.empty();
                if (!res.data.flow) {
                    res.data.list = [];
                }
                var data = res.data.flow;
                var array = [];
                for (var z = 0; z < data.length; z++) {
                    array[z] = {
                        value: data[z].etypename,
                        name: data[z].pointno,
                        type: 'equip'
                    };
                }
                // createCheckBox(array, parent, pages.index);
                //处理分页
                //获取页码
                var pages = {}
                pages.num = [];
                pages.num.splice(0, 0, "<<", "<");
                pages.num.splice(pages.num.length, 0, ">", ">>");
                var pa = page + 1
                for (var p = 0; p < pages.num.length; p++) {
                    if (pages.num[p] == page) {
                        $(ul).append('<li class="active"><a onclick="turnPages(event)">' + pages.num[p] + '</a></li>');
                    } else {
                        $(ul).append('<li onclick="turnPages(event)"><a>' + pages.num[p] + '</a></li>');
                    }
                }
                $('#page_operation').empty();
                $('#page_operation').append('<span>共</span>' + totals + '<span>条</span>');
                //每页显示多少条
                $('#page_size').val(size);
                $('#page_turn').val(pa);

                $(list).empty();
                $(list).append(title);
                for (var i = 0; i < data.length; i++) {
                    var a = i + 1;
                    $(list).append(
                        '<tbody><tr><td>' + a +
                        '</td><td>' + data[i] + '<td>' + '' +
                        '<td><button class="btn btn-info btn-sm admin-btn" onclick="changeEnable(event)">修改</button>' +
                        '<button class="btn btn-danger btn-sm admin-btn" onclick="deleteFlow(event)">删除</button></td></tr>'
                    );
                }
            }
            else {
                alert(res.status.errorMsg)
            }

        })
}
getFlowData(attributeUrl);

function turnPages(e) {
    e = e || window.event;
    var num = e.target.text;
    var res = localStorage.data;
    res = JSON.parse(res);
    ress = res.data;
    page = ress.page;
    size = ress.size;
    total = ress.total;
    switch (num) {
        case '<<':
            num = 0;
            break;
        case '<':
            num = page - 1;
            if (num < 0) {
                return false
            }
            break;
        case '>':
            num = page + 1;
            if (num == total) {
                return false
            }
            break;
        case '>>':
            num = total - 1;
            break;
        default:
            num = parseInt(num) - 1;
    }
    page = num;
    getFlowData(attributeUrl);
}
//每页显示多少条，跳转具体页面
function changePageSize(e) {
    e = e || window.event;
    size = e.target.value;
    getFlowData(attributeUrl);
}

function changePage(e) {
    e = e || window.event;
    if (parseInt(e.target.value) > totals) {
        e.target.value = totals
    }
    page = parseInt(e.target.value) - 1;
    getFlowData(attributeUrl);
}

//查看详情
function changeEnable(e, num) {
    e = e || window.event;
    var id = $(e.target).parent().parent().children()[1].innerText;
    var u = attributeData + '?bussCaption=' + id
    var url = decodeURI(u)
    $.get(url, function (res) {
        res = JSON.parse(res);
        if (res.status.success == "OK") {
            var data = JSON.stringify(res.data.flow[0])
            localStorage.data = data;
            localStorage.id = id;
            window.location.href = 'update.html'
        } else {
            alert(res.status.errorMsg)
        }
    })
}

//新增业务活动
function addFlow() {
    localStorage.data = "";
    window.location.href = 'insert.html'
}

/*//编辑(修改)流程
 function updateFlow(e) {
 e = e || window.event;
 var id = localStorage.id;
 var url = attributeUpdate + '?bussCaption=' + id;
 $.get(url, function (res) {
 res = JSON.parse(res);
 if (res.status.success == "OK") {
 //var data = res.data.jsonString;
 var data = JSON.stringify(res.datas[0]);
 localStorage.data = data;
 window.location.href = 'index.html'
 } else {
 alert(res.status.errorMsg)
 }
 })
 }*/

//删除流程
function deleteFlow(e) {
    e = e || window.event;
    var id = $(e.target).parent().parent().children()[1].innerText;
    var u = attributeDelete + '?bussCaption=' + id
    var url = decodeURI(u)
    var r = confirm('是否确认删除');
    if (r) {
        $.get(url, function (res) {
            res = JSON.parse(res);
            if (res.status.success == "OK") {
                alert('删除成功');
                getFlowData(attributeUrl);
            } else {
                alert(res.status.errorMsg)
            }
        })
    }

}

//时间转换函数
function switchTime(time) {
    if (typeof (time) == 'string')
        time = parseInt(time);
    time = new Date(time);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var date = time.getDate();
    var hour = time.getHours() + '时';
    var minutes = time.getMinutes() + '分';
    time = [year, month, date].join('-') + '&nbsp;&nbsp;' + hour + minutes;
    return time
}
