/**
 * Created by Administrator on 2017/7/14.
 */
//接收传输的数据
if (localStorage.data) {
  var editData = localStorage.data;
  editData = JSON.parse(editData);
  editData.svgData = JSON.parse(editData.svgData);
  editData.flowData = JSON.parse(editData.flowData);
  var restoreData = editData.svgData;
  var flowKey = editData.flowKey;
  var isEnable = editData.flowData.isenable;
  $('#flowName').val(editData.flowData.flowName);
  $('#flowDec').val(editData.flowData.flowDec);
}


var propsName;//属性窗口的id
//node的属性容器
var modalPropsObj = {};
var prosObj = {};
var currentNode;//存储被点击的node
var judge = [
  {name: "大于", key: 1},
  {name: "小于", key: 2},
  {name: "大于等于", key: 3},
  {name: "小于等于", key: 4},
  {name: "等于", key: 5},
  {name: "包含", key: 6},
  {name: "在......里面", key: 7}
];
var startCheckClasses = [
  {
    name: "测试类",
    value: "com.greatech.workflow.imp.FlowStartCheckImpl1"
  }, {name: "测试类111111111111111", value: "com.greatech.workflow.imp.FlowStartCheckImpl2"}, {
    name: "测试类",
    value: "com.greatech.workflow.imp.FlowStartCheckImpl3"
  }, {name: "测试类", value: "com.greatech.workflow.imp.FlowStartCheckImpl5"}, {
    name: "测试类",
    value: "com.greatech.workflow.imp.FlowStartCheckImpl"
  }
];//启动条件类
var startFlowObj={
  startCheckClass:"",
  startConditions:[]
};
var flowName={};
//请求地址

var eqtypes = '/flow-page/hlsmecs/Flow/eqtypes';
//单独查询某一设备类型（参数为id=''）
var eqtype = '/flow-page/hlsmecs/Flow/eqtype';
//查找所有设备
var points = '/flow-page/hlsmecs/Flow/points';
//分页查找设备
var pagesUrl = '/flow-page/hlsmecs/Flow/Qpoints/query';
//按设备类型查找
var Qtype = '/flow-page/hlsmecs/Flow/points/Qtype';
//事件类型
var events = '/flow-page/hlsmecs/Flow/events';
//查找所有报警和故障事件
var allEvents = '/flow-page/hlsmecs/Flow/events/alarmEvents';

var updateFlowUrl = '/flow-page/hlsmecs/Flow/flow/update';//修改流程
//丁钰数据接口 列表
var getList = '/uinode/busActivitiesData.do';
//保存数据
var saveUrlD = '/uinode/save.do';
//修改数据
var editUrlD = '/uinode/updateFlow.do';

//获取list列表
var modalData;
$.ajax({
    url: getList,
    type: "get",
    async:false,
    success:function(res){
        modalData=JSON.parse(res);
}
});



function getAttribute() {
  this.attribute = {
    start: {
      type: 'start',
      id: "",
      name: {text: '<<start>>'},
      text: {text: '开始'},
      img: {src: './img/48/start_event_empty.png', width: 48, height: 48},
      attr: {width: 50, heigth: 50},
      category: "0",//组件类别  0/system   1/custome
      props: {
        text: {
          name: 'text', label: '显示', value: '', editor: function () {
            return new myflow.editors.textEditor();
          }, value: '开始'
        }
      }
    },
    end: {
      type: 'end',
      showType: 'image',
      id: "",
      category: "0",//组件类别  0/system   1/custome
      name: {text: '<<end>>'},
      text: {text: '结束'},
      img: {src: './img/48/end_event_terminate.png', width: 48, height: 48},
      attr: {width: 50, heigth: 50},
      props: {
        text: {
          name: 'text', label: '显示', value: '', editor: function () {
            return new myflow.editors.textEditor();
          }, value: '结束'
        }
      }
    },
    fork: {
      type: 'fork',
      showType: 'image',
      id: "",
      name: {text: '<<fork>>'},
      text: {text: '分支'},
      img: {src: './img/48/gateway_parallel.png', width: 48, height: 48},
      attr: {width: 50, heigth: 50},
      category: "0",//组件类别  0/system   1/custome
    }
  };
  return this.attribute
}
var attribute = getAttribute();

//生成模块的属性对象
function getSideBarObj(array,obj2,node) {
  if(node.elementType=="link"){
    obj2[node.deviceA+node.deviceZ]={};
    obj2[node.deviceA+node.deviceZ].condition = node.condition||[];
    obj2[node.deviceA+node.deviceZ].text = node.text;
  }else {
    if(obj2[node.deviceId])return;
    obj2[node.deviceId] = {};
    obj2[node.deviceId].props = node.props||{};
    obj2[node.deviceId].condition = node.condition||[];
    obj2[node.deviceId].type = parseInt(node.deviceId);
    obj2[node.deviceId].id = '';
    obj2[node.deviceId].text = node.text;
    obj2[node.deviceId].time = node.time||'-1';
    obj2[node.deviceId].category = "1";
    for (var i=0;i<array.length;i++) {
      if (array[i].bussClass == node.bussClass||array[i].bussCaption == node.text) {
        obj2[node.deviceId].bussClass=array[i].bussClass;
        array[i].propertyInfos.forEach(function (item, index) {
          var param = 'param' + index;
          obj2[node.deviceId].props[param] = node.props[param]||{};
          obj2[node.deviceId].props[param].label = item.propertyCaption;
          obj2[node.deviceId].props[param].property = item.property;
          obj2[node.deviceId].props[param].propertyValueType = item.propertyValueType;
          //根据后台数据，给出相应的编辑控件
          switch (item.propertyValueType) {
            case "INPUT":
              obj2[node.deviceId].props[param].value = node.props[param].value||"";
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.inputEditor(item, index);
              };
              break;
            case "RADIO":
              obj2[node.deviceId].props[param].value =  node.props[param].value||"";
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.radioEditor(item, index);
              };
              break;
            case "RADIO_TEXT":
              obj2[node.deviceId].props[param].value =  node.props[param].value||"";
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.radioTextEditor(item, index);
              };
              break;
            case "CHECKBOX":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.checkBoxEditor(item, index);
              };
              break;
            case "CTRL_CHECKBOX":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.ctrlCheckBoxEditor(item, index);
              };
              break;
            case "AJAX_CHECKBOX":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.ajaxCheckBoxEditor(item, index);
              };
              break;
            case "NUM":
              obj2[node.deviceId].props[param].value =  node.props[param].value||"";
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.numEditor(item, index);
              };
              break;
            case "TEL":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.telEditor(item, index);
              };
              break;
            case "TEXTAREA":
              obj2[node.deviceId].props[param].value =  node.props[param].value||"";
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.textareaEditor(item, index);
              };
              break;
            case "SELECT_INPUT":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.selectInputEditor(item, index);
              };
              break;
            case "AJAX_TREE":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.ajaxTreeEditor(item, index);
              };
              break;
            case "AJAX_PAGE":
              obj2[node.deviceId].props[param].value =  node.props[param].value||[];
              obj2[node.deviceId].props[param].Editor = function () {
                return new propsEditor.ajaxPageEditor(item, index);
              };
              break;
          }
        })
      }
    }
  }


}

//生成img

var iconType = [
  {name: 'fire-icon', url: 'icon/fire.png'},
  {name: 'gases-icon', url: 'icon/fire.png'},
  {name: 'receive-icon', url: 'icon/task_recEquipmentAlarm1.png'},
  {name: 'remove-icon', url: 'icon/task_recEquipmentAlarm1.png'},
  {name: 'recEquipmentMessage-icon', url: 'icon/task_recEquipmentMessage1.png'},
  {name: 'client-icon', url: 'icon/task_recEquipmentMessage1.png'},
  {name: 'inOrder-icon', url: 'icon/task_recEquipmentMessage1.png'},
  {name: 'sendNote-icon', url: 'icon/task_sendMessage1.png'},
  {name: 'sendMail-icon', url: 'icon/task_sendMail1.png'},
  {name: 'startVideo-icon', url: 'icon/task_startRec1.png'},
  {name: 'createVideo-icon', url: 'icon/task_startRec1.png'},
  {name: 'recLinked-icon', url: 'icon/task_recLinked1.png'},
  {name: 'conDoor-icon', url: 'icon/task_accessCtr1.png'},
  {name: 'zhou-icon', url: 'icon/task_emergencyCtr1.png'},
  {name: 'alert-icon', url: 'icon/task_emergencyCtr1.png'},
  {name: 'emergency-icon', url: 'icon/task_emergencyCtr1.png'},
  {name: 'conRadio-icon', url: 'icon/task_radioCtr1.png'}
];