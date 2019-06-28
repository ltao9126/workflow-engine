/**
 * Created by Administrator on 2017/8/29.
 */

//判断input是否显示
function judeRecLinkVal(e) {
  e = e || window.event;
  var index1 = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  var index = $(e.target).parent().parent().parent().data("index");
  var propArray = Object.keys(modalPropsObj.props).sort();
  var input = e.target.parentElement.getElementsByClassName('rec-link-input');
  //判断输入框是否显示
  if (e.target.value != '-1') {
    for (var i = 0; i < input.length; i++) {
      $(input[i]).removeClass('display-none');
      //modalPropsObj.props[propArray[index]].value[index1 - 4] = e.target.value + ' ' + input[0].value + '、' + input[1].value;
    }
  } else {
    for (var a = 0; a < input.length; a++) {
      $(input[a]).addClass('display-none');
      input[a].value = "";
      //modalPropsObj.props[propArray[index]].value.splice([index1 - 4], 1);
    }
  }
  //获取值
}
//获取视频联动的值
function getRecLinkVal(e) {
  e = e || window.event;
  var index1 = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  var index = $(e.target).parent().parent().parent().data("index");
  index1--;
  var propArray = Object.keys(modalPropsObj.props).sort();
  var input = document.getElementsByClassName('rec-link-input');
  modalPropsObj.props[propArray[index]].value[index1 - 4] = {};
  modalPropsObj.props[propArray[index]].value[index1 - 4].name = $(e.target).parent().children().get(0).value;
  modalPropsObj.props[propArray[index]].value[index1 - 4].value1 = input[(index1 - 4) * 2].value;
  modalPropsObj.props[propArray[index]].value[index1 - 4].value2 = input[(index1 - 4) * 2 + 1].value;
  modalPropsObj.props[propArray[index]].value[index1 - 4].value = $(e.target).parent().children().get(0).value + ' ' + input[(index1 - 4) * 2].value + '、' + input[(index1 - 4) * 2 + 1].value;
}
//视屏联动添加功能
function getOrder(e) {
  e = e || window.event;
  //添加输入框
  var index1 = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  var propertyValues = $(e.target).parent().parent().parent().data("propertyValues");
  ($(e.target).parent().parent().parent()).append($('<div class="row props-row">' +
    '<div class="col-xs-4 props-row-text">联动命令:</div> ' +
    '<div  class="col-xs-8"><select class="form-control rec-link-select" onchange="judeRecLinkVal(event)">' +
    '<option value="-1">请选择</option>' +
    '<input class="form-control rec-link-input display-none" name="rec-link-1" type="number" onchange="getRecLinkVal(event)" required="required">' +
    '<input class="form-control rec-link-input display-none"  name="rec-link-2" type="number" onchange="getRecLinkVal(event)" required="required"> ' +
    '</div></div>'));
  var index = $('#' + propsName).children().length;
  for (var i = 0; i < propertyValues.length; i++) {
    $('#' + propsName).find('select').eq(index - 6).append('<option value=' + propertyValues[i].value + '>' + propertyValues[i].name + '</option>');
  }
  //添加原型属性
  //var num= ($(e.target).parent().parent().children()).length;
  //num=num-1;
  //var param='param'+num;
  //myflow.config.tools.states.recLinked.props[param]={name:param, label : '联动命令', value:[],editorType:'Array', editor:function(){return new myflow.editors.textEditor();}}
  //modalData[5].propertyInfos.push(
  //  {"defaultValue":"","nodeType":"TEXT_INPUT","property":"linkageOrder","propertyCaption":"联动命令","propertyDes":"联动命令1","propertyGroup":"a",
  //      "propertyValueType":"TEXT_INPUT","propertyValues":[""]});
  ////myflow.props(window.attribute.recLinked,documentObj);
  //console.log(myflow.config.tools.states);
  //myflow.rect();
  //$.extend(true,myflow.config.tools.states,myflow.config.tools.states);
  //$(eventTarget.target).trigger('click',[eventTarget]);
}
