/**
 * Created by Administrator on 2017/8/29.
 */
//动态生成checkBox
function createCheckBox(array, parent, index) {
  //生成checkBox
  parent.empty();
  for (var i = 0; i < array.length; i++) {
    $('<div class="checkbox-modal col-xs-4"><label><input type="checkbox" value="' + array[i].value + '" name="checkBoxProp" etype="' + array[i].name + '" type1="' + array[i].type + '">' + array[i].value + '</label></div>').appendTo(parent)
  }
  var ac = document.getElementsByName('checkBoxProp');
  var ar = Object.keys(modalPropsObj.props).sort();

  for (var i = 0; i < ac.length; i++) {
    if (modalPropsObj.type == 'recLinked') {
      for (var a in modalPropsObj.props[ar[index]].value[index1 - 2]) {
        if ($(ac[i]).attr('etype') == modalPropsObj.props[index].value[index1 - 2][a].value) {
          ac[i].checked = true
        }
      }
    }
    for (var z in modalPropsObj.props[ar[index]].value) {
      if ($(ac[i]).attr('etype') == modalPropsObj.props[ar[index]].value[z].value) {
        ac[i].checked = true
      }
    }
  }
}
//获取checkBox值
function getCheckBoxVal() {
  var array = [];
  //接收与解除设备报警 表达式存储
  if (modalPropsObj.expression) {
    var obj1 = modalPropsObj.expression.point;
    var obj2 = modalPropsObj.expression.event;
  }
  //视频联动参数
  if (modalPropsObj.type == 'recLinked') {
    var obj3 = modalPropsObj.props.param2
  }

  var array1 = [];
  var a = 0;
  for (var i = 0; i < $("input[name='checkBoxProp']").length; i++) {
    if ($("input[name='checkBoxProp']")[i].checked) {
      var ac = $("input[name='checkBoxProp']")[i];
      //保存表达式
      if (modalPropsObj.expression) {
        array1.push($(ac).attr('etype'));
        if (($(ac).attr('type1') == 'equip')) {
          obj1.etype = array1;
        } else {
          obj2.event = array1;
        }
      }
      array[a] = {};
      array[a].name = $("input[name='checkBoxProp']")[i].value;
      array[a].value = $(ac).attr('etype');
      array[a].value1 = $(ac).attr('etype');
      array[a].value2 = $(ac).attr('etype');
      a++
    }
  }
  dataRepeat(array, (propsBox))
}
//清空checkBox值
function clearCheckBoxVal() {
  var box = document.getElementsByName('checkBoxProp');
  for (var i in box) {
    box[i].checked = false
  }
}
//全选checkBox值
function allCheckBoxVal() {
  var box = document.getElementsByName('checkBoxProp');
  for (var i in box) {
    box[i].checked = true
  }
}
//树形结构图
function showTrees(data, obj) {
  var zTreeObj,
    setting = {
      view: {
        selectedMulti: true
      },
      check: {
        enable: true
      },
      async: {
        enable: true,
        type: obj.requestType,
        url: obj.url,
        autoParam: ['id=' + obj.parameter[0]],
        dataFilter: ajaxDataFilter
      }
    },
    zTreeNodes = [
      {"name": "设备类型", open: true, children: data}
    ];

  function ajaxDataFilter(treeId, parentNode, responseData) {
    if (responseData) {
      var data1 = [];
      for (var i = 0; i < responseData.data.points.length; i++) {
        data1[i] = {pointno: responseData.data.points[i].pointno, name: responseData.data.points[i].etypename}
      }
    }
    responseData = data1;
    return responseData;
  }

  zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
  $("#myTree").modal();
}
//获取选择的树形的值
function sure() {
  var array = [];
  var zTreeObj = $.fn.zTree.getZTreeObj("tree");
  var a = zTreeObj.getCheckedNodes(true);
  //var obj=modalPropsObj.expression.point;
  //obj.pointno=[];
  var z = 0;
  for (var i = 0; i < a.length; i++) {
    if (!a[i].children) {
      //console.log(a);
      //obj.pointno.push(a[i].pointno);
      array[z] = {};
      array[z].name = a[i].name;
      array[z].value = a[i].pointno;
      z++
    }
  }
  dataRepeat(array, (propsBox))
}