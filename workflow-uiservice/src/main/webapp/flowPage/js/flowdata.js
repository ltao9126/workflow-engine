/**
 * Created by Administrator on 2017-8-28.
 */
//生成出口条件
function showCondition(Boolean,node) {
  var parent = $('#props_condition')[0];
  var a = $(parent).find('.condition').length;

  if($('#props_condition_content').length==0){
    $(parent).append('<div class="row props-row">' +
      '<div class="col-xs-3 props-row-text">名称</div>' +
      '<div class="col-xs-8"><label><input type="text" class="form-control" value="'+node.text+'" onchange="saveConText(event)">' +
      '</label></div>' +
      '</div>' +
      '<div id="props_condition_content"></div>');
  }


  var parent1=$('#props_condition_content')[0];
  var z = a + 1;//条件的序号
  //以Boolean值判断是添加还是生成dom
  if (Boolean) {
    $(parent1).append('<div class="row props-row">' +
      '<div class="col-xs-3 props-row-text">' + "条件" + z + '</div>' +
      '<div class="col-xs-10"><label><input type="text" class="form-control pull-left" placeholder="值">' +
      '<select class="form-control condition pull-left"></select>' +
      '<input type="text" class="form-control pull-left" placeholder="范围">' +
      '</label></div>' +
      '<div class="col-xs-2"><button class="btn btn-danger" onclick="delCondition(event)">删除</button></div>' +
      '</div>');
    //为select添加选项
    for (var k = 0; k < judge.length; k++) {
      $($(parent1).find('select.condition').eq(a)).append('<option value=' + judge[k].key + '>' + judge[k].name + '</option>')
    }
  } else {
    $(parent1).empty();
    for (var i = 0; i < modalPropsObj.condition.length; i++) {
      z = i + 1;
      $(parent1).append('<div class="row props-row">' +
        '<div class="col-xs-3 props-row-text">' + "条件" + z + '</div>' +
        '<div class="col-xs-10"><label><input type="text" class="form-control pull-left" placeholder="值"  value=' + modalPropsObj.condition[i].value + '>' +
        '<select class="form-control condition pull-left"></select>' +
        '<input type="text" class="form-control pull-left" placeholder="范围" value=' + modalPropsObj.condition[i].property + '>' +
        '</label></div>' +
        '<div class="col-xs-2"><button class="btn btn-danger" onclick="delCondition(event)">删除</button></div>' +
        '</div>');
      //为select添加选项
      for (var k = 0; k < judge.length; k++) {
        $($(parent1).find('select.condition').eq(i)).append('<option value=' + judge[k].key + '>' + judge[k].name + '</option>')
      }
      //获取select的值
      $(parent1).find('select.condition').eq(i).val(modalPropsObj.condition[i].operaterType)
    }

  }


}

//生成流程数据
function getFlowData(modalData, svgData, judge, flowData) {
  flowData.flowTable = {};
  //将线条与模块分组
  svgData.states = [];
  svgData.paths = [];
  for (var c = 0; c < svgData.childs[0].childs.length; c++) {
    if (svgData.childs[0].childs[c].elementType == "node") {
      svgData.states.push(svgData.childs[0].childs[c])
    } else {
      svgData.paths.push(svgData.childs[0].childs[c])
    }
  }
  var svgData1=changeSvgData(svgData1,svgData);
  //获取流程起始模块
  var rects = getObjArray(rects, svgData1.states);
  var attribute1 = 'deviceA';
  var pathTo = getObjArray(pathTo, svgData1.paths, attribute1);
  var attribute2 = 'deviceZ';
  var pathFrom = getObjArray(pathFrom, svgData1.paths, attribute2);
  //流程名及启动条件
  if ($('#flowName')[0].value==""){
    alert("请定义流程名");
    return false
  }else {
    flowData.flowName=$('#flowName')[0].value;
    flowData.flowDec=$('#flowDec')[0].value
  }

  if (flowKey) {
    flowData.flowKey = flowKey;
    flowData.isenable = 1;
  } else {
    flowData.flowKey = new Date().getTime();
    flowData.isenable = 0;
  }
  flowData.startCheckClass = svgData1.startCheckClass;
  flowData.startConditions = svgData1.startConditions;
  var j = 0;
  //模块的value
  for (var i = 0; i < svgData1.states.length; i++) {
    var a = {};
    (svgData1.states[i].deviceId == getFirstNode(svgData.states[i].deviceId,pathFrom)) ? a.startNode = true : a.startNode = false;
    (svgData1.states[i].deviceId == getFirstNode(svgData.states[i].deviceId,pathTo)) ? a.endNode = true : a.endNode = false;
    a.nodeKey = svgData1.states[i].deviceId;
    a.bussObject = {};
    a.bussObject.classConfigList = [];
    var q = 0;
    for (var z in svgData1.states[i].props) {
      a.bussObject.classConfigList[q] = {};
      a.bussObject.classConfigList[q].property = svgData1.states[i].props[z].property;
      if (typeof(svgData1.states[i].props[z].value) == 'object') {
        a.bussObject.classConfigList[q].value = [];
        for (var ind = 0; ind < svgData1.states[i].props[z].value.length; ind++) {
          a.bussObject.classConfigList[q].value.push(svgData1.states[i].props[z].value[ind].value)
        }
      } else {
        a.bussObject.classConfigList[q].value = svgData1.states[i].props[z].value;
      }
      q++
    }
    //a.bussObject.classConfigList[q]={};
    //a.bussObject.classConfigList[q].property='condition';
    //a.bussObject.classConfigList[q].value=svgData.states[i].condition;
    a.resultObject = {};
        a.bussObject.bussClass = svgData1.states[i].bussClass;
        a.resultObject.resultClass = svgData1.states[i].bussClass;

    //a.bussObject.bussClass = modalData[svgData.states[i]['type']].bussClass;

    a.resultObject.needStartSubFlow = "false";
    a.resultObject.startSubFlow = "";
    //a.resultObject.resultClass = modalData[svgData.states[i]['type']].bussClass;
    a.resultObject.defaultNodeKey = svgData1.states[i].deviceId;
    a.resultObject.waitTime = transWatingTime(svgData1.states[i]);
    a.resultObject.resultConditions = getConditions(a.resultObject.resultConditions,judge,svgData1.paths,svgData1.states[i].deviceId);
    flowData.flowTable[i] = a;
    j++;
  }
  if (a.resultObject.resultConditions.length == 0) {
    a.resultObject.resultClass = '';
  }
  return flowData
}

//获取流程是否是首个或末尾
function getFirstNode(rect, array2) {
  for (var z = 0; z < array2.length; z++) {
    if (array2.indexOf(rect) == -1) {
      return rect
    }
  }
  return -1
}

//配置启动条件
$('#myflow_startConditions').bind('click', function () {
  flowCondition({});
  $('#flow_startCondition').modal();
});
//增加启动条件
function addFlowCon() {
  var a = $('.condition').length;
  var z = a + 1;
  $('#startCondition_content').append('<div class="row props-row">' +
    '<div class="col-xs-3 props-row-text">' + "条件" + z + '</div>' +
    '<div class="col-xs-10"><label><input type="text" class="form-control pull-left" style="width: 20%" placeholder="值">' +
    '<span class="	glyphicon glyphicon-minus pull-left" style="margin:0 2px;height: 34px;line-height: 34px"></span>' +
    '<select class="form-control condition pull-left" style="width: 40%"></select>' +
    '<span class="	glyphicon glyphicon-minus pull-left" style="margin:0 2px;height: 34px;line-height: 34px"></span>' +
    '<input type="text" class="form-control pull-left" style="width: 30%" placeholder="范围">' +
    '</label></div>' +
      //'<div class="col-xs-4"><label><select class="form-control condition"></select></label></div>' +
      //'<div class="col-xs-3"><label><input type="text" class="form-control" placeholder="范围"></label></div>' +
    '<div class="col-xs-2"><button class="btn btn-danger">删除</button></div>' +
    '</div>');
  for (var k = 0; k < judge.length; k++) {
    $($('#startCondition_content').find('select.condition').eq(a)).append('<option value=' + judge[k].key + '>' + judge[k].name + '</option>')
  }
}
//保存启动条件
function saveStartCon() {
  startFlowObj.startCheckClass = $('#startCheckClass').val();
  var row = $('#startCondition_content').find('.props-row');
  for (var i = 0; i < $(row).length - 1; i++) {
    var a = i + 1;
    var inputs = $(row).eq(i + 1).find('.form-control');
    startFlowObj.startConditions.push({
      "value": $(inputs).eq(0).val(),
      "operaterType": $(inputs).eq(1).val(),
      "property": $(inputs).eq(2).val()
    })
  }
}

//发送数据前对数据进行处理
function changeSvgData(svgData1,svgData) {
  svgData1=JSON.stringify(svgData);
  svgData1=JSON.parse(svgData1);
  function delStates(){
    for (var a =0;a<svgData1.states.length;a++) {
      //找到开始、分支等无属性的节点,相应处理
      if (svgData1.states[a].text == '开始') {
        for (var b in svgData1.paths) {
          if (svgData1.paths[b].deviceA == svgData1.states[a].deviceId) {
            svgData1.paths.splice(b,1);
          }
        }
        svgData1.states.splice(a,1);
        delStates();
        break
      }
      if (svgData1.states[a].text == '结束') {
        for (var c in svgData1.paths) {
          if (svgData1.paths[c].deviceZ == svgData1.states[a].deviceId) {
            svgData1.paths.splice(c,1);
          }
        }
        svgData1.states.splice(a,1);
        delStates();
        break
      }
      if (svgData1.states[a].text == '分支') {
        for (var d in svgData1.paths) {
          if (svgData1.paths[d].deviceZ == svgData1.states[a].deviceId) {
            var from = svgData1.paths[d].deviceA;
            svgData1.paths.splice(d,1);
          }
        }
        for (var e in svgData1.paths) {
          if (svgData1.paths[e].deviceA == svgData1.states[a].deviceId) {
            svgData1.paths[e].deviceA = from;
          }
        }
        svgData1.states.splice(a,1);
        delStates();
        break
      }
    }
  }
  delStates();
  return svgData1
}

//生成启动条件属性窗
function flowCondition(obj) {
  $('#startCondition_content').empty();
  $('#startCondition_content').append('<div class="row props-row">' +
    '<div class="col-xs-3 props-row-text">启动类：</div>' +
    '<div class="col-xs-9">' +
    '<label class="control-label"><select id="startCheckClass" class="form-control"></select></label></div>' +
    '<div class="col-xs-3"><button class="btn btn-primary" onclick="addFlowCon()">添加条件</button></div></div>');
  for (var i = 0; i < startCheckClasses.length; i++) {
    $('#startCheckClass').append('<option value=' + startCheckClasses[i].value + '>' + startCheckClasses[i].name + '</option>')
  }
  if (obj && obj.startCheckClass) {
    $('#startCheckClass').val(obj.startCheckClass)
  }
  if (obj && obj.startConditions) {
    for (var z = 0; z < obj.startConditions.length; z++) {
      var c = z + 1;
      $('#startCondition_content').append('<div class="row props-row">' +
        '<div class="col-xs-4 props-row-text">' + "条件" + c + ':</div>' +
        '<div class="col-xs-2"><label><input type="text"  class="form-control" placeholder="值" value=' + obj.startConditions[z].value + '></label></div>' +
        '<div class="col-xs-4"><label><select class="form-control condition"></select></label></div>' +
        '<div class="col-xs-2"><label><input type="text" class="form-control" placeholder="范围" value=' + obj.startConditions[z].property + '></label></div>' +
        '</div>');
      for (var k = 0; k < judeItems.length; k++) {
        $($('#startCondition_content').find('select.condition').eq(z)).append('<option value=' + judeItems[k].key + '>' + judeItems[k].name + '</option>')
      }
      $($('#startCondition_content').find('select.condition').eq(z)).val(obj.startConditions[z].operaterType)
    }
  }
}
flowCondition(restoreData);

//删除入口条件项
function delCondition(e) {
  e = e || window.event;
  $(e.target).parent().parent().remove()
}

//获取流程判断条件数组
function getConditions(array, judge, obj, rect) {
  array = [];
  for (var a =0;a<obj.length;a++) {
    if (rect == obj[a].deviceA) {
      array[a] = {};
      array[a].nodeKey = obj[a].deviceZ;
      array[a].needStartSubFlow = "false";
      array[a].startSubFlow = "";
      array[a].order = a + 1;
      array[a].classConfigs = obj[a].condition;
    }
  }
  return array
}