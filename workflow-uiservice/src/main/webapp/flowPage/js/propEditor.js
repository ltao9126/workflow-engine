/**
 * Created by Administrator on 2017/8/29.
 */
//动态生成属性窗口--编辑器
var propsEditor = {
  row: '<div class="row props-row"></div>',
  inputEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    if (obj.propertyCaption == "显示") {
      $('<div class="col-xs-10"><label>' +
        '<input  class="props-row-input form-control" type="text" name="input' + index + '" onchange="savePropsTitle(event)" value=' + modalPropsObj.text + '></label>' +
        '</div>').appendTo($(parent).children()[index]);
    } else {
      $('<div class="col-xs-10"><label>' +
        '<input  class="props-row-input form-control" type="text" name="input' + index + '" onchange="saveInputVal(event)" value=' + modalPropsObj.props[propArray[index]].value + '></label>' +
        '</div>').appendTo($(parent).children()[index]);
    }
    if (obj.checked) {
      switch (obj.checked.checkType) {
        case "range":
          for (var i in obj.checked) {
            $(parent).find('[name=' + "input" + index + ']').attr(i, obj.checked[i]);
          }
          $(parent).find('[name=' + "input" + index + ']').attr("onchange", "checkRange(event)");
          $(parent).find('[name=' + "input" + index + ']').attr("type", "number");
          break;
        case "port":
          $(parent).find('[name=' + "input" + index + ']').attr("type", "text");
          $(parent).find('[name=' + "input" + index + ']').attr("onchange", "checkIpPatten(event)");
      }
    }
    return row;
  },
  radioEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    var div = $('<div class="col-xs-10">' +
      '</div>').appendTo($(parent).children()[index]);
    for (var i = 0; i < obj.propertyValues.length; i++) {
      div.append('<label class="radio-inline"><input name="radio' + index + '" type="radio" value=' + i + '>' + obj.propertyValues[i] + '</label>');
      if (i === parseInt(modalPropsObj.props[propArray[index]].value)) {
        var inputs = $(':radio[name="radio' + index + '"]');
        inputs[i].checked = true
      }
    }
    $(':radio[name="radio' + index + '"]').bind('click', function (e) {
      e = e || window.event;
      for (var a in modalPropsObj.props) {
        if (a === propArray[index]) {
          modalPropsObj.props[a].value = parseInt(e.target.value)
        }
      }
    });
    return row;
  },
  radioTextEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    $(parent).append(row);
    $(row).attr('id', 'props_time');
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10" onchange="changeTime(event)">' +
      '<label class="radio-inline"><input type="radio" name="time' + index + '" value=' + obj.propertyValues[0].value + '>' + obj.propertyValues[0].name + '</label>' +
      '<label class="radio-inline"><input type="radio" name="time' + index + '" value=' + obj.propertyValues[1].value + '>' + obj.propertyValues[1].name + '</label>' +
      '<label class="radio-inline"><input type="radio" name="time' + index + '" class="specificTime" value=' + obj.propertyValues[2].value + '>' + obj.propertyValues[2].name + '</label>' +
      '<label class="display-none">：<input type="text" name="time' + index + '" class="specific-time" value=""></label>' +
      '</div>').appendTo($(parent).children()[index]);
    var name = "time" + index;
    getRadioVal(modalPropsObj, name);
    return row;
  },
  checkBoxEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text "> ' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10"><div class="prop-type"></div>' +
      '<button class="btn btn-default prop-btn" onclick="chooseOptions(event)">' +
      '<span class="glyphicon glyphicon-th"></span>' +
      '</button>' +
      '</div></div>').appendTo($(parent).children()[index]);
    var warp = $(parent).children()[index];
    warp = $(warp).find('.prop-type')[0];
    dataRepeat(modalPropsObj.props[propArray[index]].value, warp);
    return row;
  },
  ctrlCheckBoxEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10">' +
      '<label><input type="checkbox" name="holeFac" value="全厂">全厂</label>' +
      '</div>').appendTo($(parent).children()[index]);
    return row;
  },
  ajaxCheckBoxEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10"><div class="prop-type"></div>' +
      '<button class="btn btn-default prop-btn" onclick="chooseAjaxOptions(event)">' +
      '<span class="glyphicon glyphicon-th"></span>' +
      '</button>' +
      '</div></div>').appendTo($(parent).children()[index]);
    var warp = $(parent).children()[index];
    warp = $(warp).find('.prop-type')[0];
    dataRepeat(modalPropsObj.props[propArray[index]].value, warp);
    return row;
  },
  numEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    $(parent).data("index", index);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10">' +
      '<div></div>' +
      '<label><input class="form-control props-row-input input-num" name="input' + index + '"  value="' + modalPropsObj.props[propArray[index]].value + '" type="number"></label>' +
      '</div>').appendTo($(parent).children()[index]);
    if (obj.checked) {
      switch (obj.checked.checkType) {
        case "range":
          for (var i in obj.checked) {
            $(parent).find('[name=' + "input" + index + ']').attr(i, obj.checked[i]);
          }
          $(parent).find('[name=' + "input" + index + ']').attr("onchange", "checkRange(event)");
          $(parent).find('[name=' + "input" + index + ']').attr("type", "number");
          break;
        case "port":
          $(parent).find('[name=' + "input" + index + ']').attr("type", "text");
          $(parent).find('[name=' + "input" + index + ']').attr("onchange", "checkIpPatten(event)");
      }
    }
    return row;
  },
  telEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    $(parent).data("index", index);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10">' +
      '<div></div>' +
      '<label><input class="form-control props-row-input input-num" name="tel' + index + '" oninput="getNum(event)" onchange="getVal(event)" type="number"></label>' +
      '</div>').appendTo($(parent).children()[index]);

    var warp = $('input[name="tel' + index + '"]').eq(0).parent().prev()[0];
    dataRepeat(modalPropsObj.props[propArray[index]].value, warp);

    return row;
  },
  textareaEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10">' +
      '<label><textarea placeholder="输入框大小可在右下角拖动" class="form-control input-textarea" rows="3" onchange="saveInputVal(event)"></textarea></label> ' +
      '</div>').appendTo($(parent).children()[index]);
    $('textarea').val(modalPropsObj.props[propArray[index]].value);
    return row;
  },
  selectInputEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    var propertyValues = $(parent).data("propertyValues", obj.propertyValues);
    $(parent).data("index", index);
    $(parent).append(row);
    $(parent.children().eq(index)).append('<div class="col-xs-2 props-row-text">添加命令:</div>' +
      '<div  class="col-xs-10"><button type="button" class="col-xs-offset-2 btn btn-primary glyphicon glyphicon-plus" ' +
      'onclick="getOrder(event)"></button></div>');
    //根据临时保存值重新生成dom
    if (modalPropsObj.props[propArray[index]].value.length != 0) {
      for (var k = 0; k < modalPropsObj.props[propArray[index]].value.length; k++) {
        $(parent).append(row);
        $('<div class="col-xs-2 props-row-text">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index + k + 1]);
        $('<div  class="col-xs-10"><select class="form-control rec-link-select" onchange="judeRecLinkVal(event)">' +
          '<option value="-1">请选择</option>' +
          '<input class="form-control rec-link-input" name="rec-link-1" type="number" value=' + modalPropsObj.props[propArray[index]].value[k].value1 + ' onchange="getRecLinkVal(event)" required="required">' +
          '<input class="form-control rec-link-input"  name="rec-link-2" type="number" value=' + modalPropsObj.props[propArray[index]].value[k].value2 + '  onchange="getRecLinkVal(event)" required="required"> ' +
          '</div></div>').appendTo($(parent).children()[index + k + 1]);
        for (var i = 0; i < obj.propertyValues.length; i++) {
          $(parent).find('select').eq(k).append('<option value=' + obj.propertyValues[i].value + '>' + obj.propertyValues[i].name + '</option>');
        }
        $(parent).find('select').eq(k).val(modalPropsObj.props[propArray[index]].value[k].name)
      }
    } else {
      $(parent).append(row);
      $('<div class="col-xs-2 props-row-text">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index + 1]);
      $('<div  class="col-xs-10"><select class="form-control rec-link-select" onchange="judeRecLinkVal(event)">' +
        '<option value="-1">请选择</option>' +
        '<input class="form-control rec-link-input display-none" name="rec-link-1" type="number" onchange="getRecLinkVal(event)" required="required">' +
        '<input class="form-control rec-link-input display-none"  name="rec-link-2" type="number" onchange="getRecLinkVal(event)" required="required"> ' +
        '</div></div>').appendTo($(parent).children()[index + 1]);
      for (var i = 0; i < obj.propertyValues.length; i++) {
        $(parent).find('select').append('<option value=' + obj.propertyValues[i].value + '>' + obj.propertyValues[i].name + '</option>');
        //$(parent).find('select').after(  '<input class="form-control rec-link-input display-none" name="rec-link-1" type="number" onchange="getRecLinkVal(event)" required="required">')
      }
    }
    return row;
  },
  ajaxTreeEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10"><div class="prop-type"></div>' +
      '<button class="btn btn-default prop-btn" onclick="chooseAjaxTrees(event)">' +
      '<span class="glyphicon glyphicon-th"></span>' +
      '</button>' +
      '</div></div>').appendTo($(parent).children()[index]);
    var warp = $(parent).children()[index];
    warp = $(warp).find('.prop-type')[0];
    dataRepeat(modalPropsObj.props[propArray[index]].value, warp);
    return row;
  },
  ajaxPageEditor: function (obj, index) {
    var row = propsEditor.row;
    var parent = $('#' + propsName);
    var propArray = Object.keys(modalPropsObj.props);
    propArray=propArray.sort();
    $(parent).append(row);
    $('<div class="col-xs-2 props-row-text ">' + obj.propertyCaption + '</div>').appendTo($(parent).children()[index]);
    $('<div class="col-xs-10"><div class="prop-type"></div>' +
      '<button class="btn btn-default prop-btn" onclick="choosePages(event)">' +
      '<span class="glyphicon glyphicon-th"></span>' +
      '</button>' +
      '</div></div>').appendTo($(parent).children()[index]);
    var warp = $(parent).children()[index];
    warp = $(warp).find('.prop-type')[0];
    dataRepeat(modalPropsObj.props[propArray[index]].value, warp);
    return row;
  }
};
//根据属性对象，生成属性dom
function showProps(node) {
  //初始化属性暂存容器,清空dom，显示属性框
  $('.right-content').fadeIn();
  //$('#contextBody').css('margin-right','450px');
  if (node.elementType=="link"){
    $('#props_condition').empty();
    $('#props_body').hide();
    $('#props_body_title').hide();
    $('#props_condition').show();

    $('#props_condition_title').show();
    modalPropsObj = attribute[node.deviceA+node.deviceZ];
    showCondition(false,node)
  }else {
    modalPropsObj = attribute[node.deviceId];
    if (node.text=="开始"||node.text=="结束"||node.text=="分支"){
      $('.right-content').hide();
      modalPropsObj.props={a:"1"};
      return;
    }
    $('#props_body').empty();
    $('#props_body').show();
    $('#props_condition').hide();
    $('#props_body_title').show();
    $('#props_condition_title').hide();

    //根据属性中的编辑器生成dom
    propsName = parseInt(node.deviceId);//生成dom的id
    var propArray = Object.keys(modalPropsObj.props).sort();
    propArray=propArray.sort();
    var warp = $('<div id=' + propsName + ' class="props-body"></div>');
    $('#props_body').append(warp);
    for (var k = 0; k < propArray.length; k++) {
      var row = modalPropsObj.props[propArray[k]].Editor();
      $(warp).append(row)
    }
  }



}
//保存模块属性框的值
function saveProps() {
  //模块名称及属性
  if(currentNode.elementType=="link"){
    currentNode.text = modalPropsObj.text;
    currentNode.props = modalPropsObj.props;
    //模块出口条件
    modalPropsObj.condition = [];
    var row = $('#props_condition_content').find('.props-row');
    for (var i = 0; i < $(row).length; i++) {
      var a = i + 1;
      var inputs = $(row).eq(i).find('.form-control');
      modalPropsObj.condition.push({
        "value": $(inputs).eq(0).val(),
        "operaterType": $(inputs).eq(1).val(),
        "property": $(inputs).eq(2).val()
      })
    }
  }else {
    currentNode.text = modalPropsObj.text;
    currentNode.props = modalPropsObj.props;
    currentNode.time = modalPropsObj.time;
  }


//隐藏属性窗口
  currentNode.condition = modalPropsObj.condition;
  $('.right-content').fadeOut();
}
//取消保存属性框
function cancelProps() {
  $('.right-content').fadeOut();
}