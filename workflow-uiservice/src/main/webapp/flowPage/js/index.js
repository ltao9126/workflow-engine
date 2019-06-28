/**
 * Created by Administrator on 2017/7/24.
 */
//url
var allFlowUrl = '/uinode/flowName.do';//查找所有流程(本地)
var searchFlowUrl = '/uinode/flowData.do';//查找某流程,参数为flowKey=“”
var deleteFlowUrl = '/uinode/deleteFlow.do';//删除流程(丁钰)

//list Dom
var title = '<thead><th class="text-center">序号</th><th class="text-center">流程ID</th><th class="text-center">流程名</th><th class="text-center">流程描述</th><th class="text-center">创建时间</th><th class="text-center">状态</th><th class="text-center">操作</th></thead>';


//请求列表数据
function getFlowData(url) {
  var data;
  var list = $('#flow_list');
  $.get(url, function (res) {
    res = JSON.parse(res);
    if (res.status.success == "OK") {
      data = res.data.flow;
      $(list).empty();
      $(list).append(title);
      for (var i = 0; i < data.length; i++) {
        var enable;
        var createTime = switchTime(data[i].createTime);
        (data[i].isenable == 0) ? enable = "已禁用" : enable = "已启用";
        var a = i + 1;
        $(list).append(
          '<tbody><tr><td>' + a +
          '</td><td>' + data[i].flowKey + '</td><td>' + data[i].flowName + '</td><td>' + data[i].flowDec + '</td><td>' + createTime + '</td><td>' + enable + '</td>' + '<td><button class="btn btn-info btn-sm admin-btn" onclick="changeEnable(event,1)">启用</button>' +
          '<button class="btn btn-danger btn-sm admin-btn" onclick="changeEnable(event,0)">禁用</button>' +
          '<button class="btn btn-info btn-sm admin-btn" onclick="updateFlow(event)">修改</button>' +
          '<button class="btn btn-danger btn-sm admin-btn" onclick="deleteFlow(event)">删除</button></td></tr>'
        );
      }
    } else {
      alert(res.status.errorMsg)
    }

  })
}
getFlowData(allFlowUrl);
//
function changeEnable(e, num) {
  e = e || window.event;
  var id = $(e.target).parent().parent().children()[1].innerText;
  var url = searchFlowUrl + '?id=' + id;
  $.get(url, function (res) {
    res = JSON.parse(res);
    if (res.status.success == "OK") {
      var data = res.data.jsonString;
      data = JSON.parse(data);
      data.flowData.isenable = num;
      getFlowData(allFlowUrl);
    } else {
      alert(res.status.errorMsg)
    }
  })
}

//新增流程
function addFlow() {
  localStorage.data = "";
  window.location.href = 'flow.html'
}
//编辑流程
function updateFlow(e) {
  e = e || window.event;
  var id = $(e.target).parent().parent().children()[1].innerText;
  var url = searchFlowUrl + '?flowKey=' + id;
  $.get(url, function (res) {
    res = JSON.parse(res);
    if (res.status.success == "OK") {
      //var data = res.data.jsonString;
      var data=JSON.stringify(res.datas[0]);
      localStorage.data = data;
      window.location.href = 'flow.html'
    } else {
      alert(res.status.errorMsg)
    }
  })
}
//删除流程
function deleteFlow(e) {
  e = e || window.event;
  var id = $(e.target).parent().parent().children()[1].innerText;
  var url = deleteFlowUrl + '?flowKey=' + id;
  var r = confirm('是否确认删除');
  if (r) {
    $.post(url, {}, function (res) {
      res = JSON.parse(res);
      if (res.status.success == "OK") {
        alert('删除成功');
        getFlowData(allFlowUrl);
      } else {
        alert(res.status.errorMsg)
      }
    })
  }

}

//时间转换函数
function switchTime(time) {
  if (typeof (time)=='string')
  time=parseInt(time);
  time = new Date(time);
  var year = time.getFullYear();
  var month = time.getMonth() + 1;
  var date = time.getDate();
  var hour = time.getHours() + '时';
  var minutes = time.getMinutes() + '分';
  time = [year, month, date].join('-') + '&nbsp;&nbsp;' + hour + minutes;
  return time
}