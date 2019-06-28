/**
 * Created by Administrator on 2017/7/18.
 */

//每条属性的点击函数
function chooseOptions(e) {
  e = e || window.event;
  var index;
  //拿到需要渲染的数据
  var num = getModalDataIndex(modalPropsObj, modalData);
  if (e.target.tagName == "BUTTON") {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  } else {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement.parentElement);
  }

  var array = modalData[num].propertyInfos[index].propertyValues;

  //渲染数据
  var data = [];
  for (var i = 0; i < array.length; i++) {
    data[i] = {value: array[i], name: array[i]};
  }
  var array1 = Object.keys(modalPropsObj.props).sort();
  for (var a in modalPropsObj.props) {
    if (a == array1[index]) {
      createCheckBox(data, $('#POPUP_BOXLabel'), index);
    }
  }
  //propsBox为显示选中数据的div
  (e.target.previousSibling) ? propsBox = e.target.previousElementSibling : propsBox = e.target.parentElement.previousElementSibling;
  $('#POPUP_BOX').modal(
    {backdrop: false}
  )
}
function chooseAjaxOptions(e) {
  e = e || window.event;
  var index;
  //拿到需要渲染的数据
  var num = getModalDataIndex(modalPropsObj, modalData);
  if (e.target.tagName == "BUTTON") {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  } else {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement.parentElement);
  }
  var obj = modalData[num].propertyInfos[index].propertyValuesUrl;

  $.get(obj[0].url, function (res) {
    var data = [];
    for (var i = 0; i < res.data.types.length; i++) {
      data[i] = {value: res.data.types[i].tname, name: res.data.types[i].id};
    }
    (e.target.previousSibling) ? propsBox = e.target.previousElementSibling : propsBox = e.target.parentElement.previousElementSibling;
    var array1 = Object.keys(modalPropsObj.props).sort();
    for (var a in modalPropsObj.props) {
      if (a == array1[index]) {
        createCheckBox(data, $('#POPUP_BOXLabel'), index);
      }
    }
  });
  $('#POPUP_BOX').modal(
    {backdrop: false}
  )
}
function chooseAjaxTrees(e) {
  e = e || window.event;
  var index;
  var num = getModalDataIndex(modalPropsObj, modalData);
  if (e.target.tagName == "BUTTON") {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  } else {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement.parentElement);
  }
  var obj = modalData[num].propertyInfos[index].propertyValuesUrl;
  $.get(obj[0].url, function (res) {
    var data = [];
    for (var i = 0; i < res.data.types.length; i++) {
      data[i] = {id: res.data.types[i].id, name: res.data.types[i].tname, children: [], isParent: true};
    }
    (e.target.previousSibling) ? propsBox = e.target.previousElementSibling : propsBox = e.target.parentElement.previousElementSibling;
    var array1 = Object.keys(modalPropsObj.props).sort();
    for (var a in modalPropsObj.props) {
      if (a == array1[index]) {
        createCheckBox(data, $('#POPUP_BOXLabel'), index);
      }
    }
    showTrees(data, obj[1])
  });
}
function choosePages(e) {
  e = e || window.event;
  var index;
  var num = getModalDataIndex(modalPropsObj, modalData);
  if (e.target.tagName == "BUTTON") {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  } else {
    index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement.parentElement);
  }
  (e.target.previousSibling) ? propsBox = e.target.previousElementSibling : propsBox = event.target.parentElement.previousElementSibling;
  pages.obj = modalData[num].propertyInfos[index].propertyValuesUrl;
  pages.index = index;
  getPages(pages.obj);
  $('#POPUP_Page').modal(
  )

}
//获取当前模块数据在modalData中的index
function getModalDataIndex(obj1, array) {
  for (var k in array) {
    if (obj1.bussClass == array[k].bussClass) {
      return k
    }
  }
}

//获取对象下标或属性值的数组
function getObjArray(array, obj, attribute) {
  array = [];
  for (var a=0;a<obj.length;a++) {
    if (!attribute) {
      array.push(a)
    } else {
      array.push(obj[a][attribute])
    }

  }
  return array
}
//数组去重
function unique(arr) {
  var res = [];
  var json = {};
  for (var i = 0; i < arr.length; i++) {
    if (!json[arr[i]]) {
      res.push(arr[i]);
      json[arr[i]] = 1;
    }
  }
  return res;
}
//获取数组元素的下标
function getArrayIndex(array, string) {
  var i;
  for (i = 0; i < array.length; i++) {
    if (string == array[i]) {
      return i
    }
  }
}
//动态渲染数组数据
function dataRepeat(array, parent) {
  var source = [];
  $(parent).empty();
  for (var i = 0; i < array.length; i++) {
    source[i] = $('<div class="options">' +
      '<span class="input-group-btn" >' +
      '<button class="btn btn-default options-btn" type="button">' + array[i].name +
      '<button class="btn btn-default options-btn" type="button" onclick="delEvent(event)">' +
      'x ' +
      '</button> ' +
      '</span> ' +
      '</div>');
    $(parent).append(source[i])
  }
  //获取checkBox值存入modalPropsObj
  var index = getArrayIndex($('#' + propsName).children(), parent.parentElement.parentElement);
  var array1 = Object.keys(modalPropsObj.props).sort();
  if (modalPropsObj.type == 'recLinked') {
    var index1 = index - 2;
    modalPropsObj.props[array1[index]].value[index1] = [];
    for (var z in array) {
      modalPropsObj.props[array1[index]].value[index1][z] = array[z].value
    }
  } else {
    for (var a in modalPropsObj.props) {
      if (a == array1[index]) {
        for (var c=0;c<array.length;c++) {
          modalPropsObj.props[a].value[c] = array[c]
        }
      }
    }
  }


}

//属性编辑框中radio绑定事件
$(':radio[name=radio]').bind('click', function (e) {
  e = e || window.event;
  var index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement.parentElement);
  var array1 = Object.keys(modalPropsObj.props).sort();
  for (var a in modalPropsObj.props) {
    if (a == array1[index]) {
      modalPropsObj.props[a].value = e.target.value
    }
  }
});
//属性编辑框中checkBox绑定事件
$('input:checkbox[name="holeFac"]').bind('click', function (e) {
  e = e || window.event;
  if (e.target.checked) {
    $('#' + propsName + ' button.prop-btn').attr('disabled', 'disabled');
  } else {
    $('#' + propsName + ' button.prop-btn').removeAttr('disabled')
  }
  var index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement);
  var array1 = Object.keys(modalPropsObj.props).sort();
  for (var a in modalPropsObj.props) {
    if (a == array1[index]) {
      modalPropsObj.props[a] = e.target.value
    }
  }
});
//电话号码输入框的事件处理
function getNum(e) {
  e = e || window.event;
  if ($(e.target).val().length == 12) {
    alert('号码只能为11位');
    e.target.value = e.target.value.substring(0, $(e.target).val().length - 1)
  }
}
function getVal(e) {
  e = e || window.event;
  if (e.target.type == 'number' && e.target.value.length < 11) {
    alert('号码少于11位');
  } else {
    //获取属性值存入modalPropsObj
    if (e.target.type == 'number') {
      (e.target.previousElementSibling) ? propsBox = e.target.previousElementSibling : propsBox = e.target.parentElement.previousElementSibling;
    }
    var index = $(e.target.parentElement.parentElement.parentElement.parentElement).data("index");
    var array1 = Object.keys(modalPropsObj.props).sort();
    for (var a in modalPropsObj.props) {
      if (a == array1[index]) {
        if (e.target.type == 'number') {
          modalPropsObj.props[a].value.push({name: e.target.value, value: e.target.value});
          var array = [];
          for (var i in modalPropsObj.props[a].value) {
            array[i] = {};
            array[i].name = modalPropsObj.props[a].value[i].name;
            array[i].value = modalPropsObj.props[a].value[i].value
          }
          dataRepeat(array, propsBox);
          e.target.value = '';
        } else {
          modalPropsObj.props[a].value = e.target.value;
        }
      }
    }

  }
}
//删除动态渲染的值
function delEvent(e) {
  e = e || window.event;
  var children = $(e.target).parent().parent().parent().children();
  var parent = e.target.parentElement.parentElement;
  //获取该值在数组的索引
  var i = getArrayIndex(children, e.target.parentElement.parentElement);
  //获取该属性再属性对象的索引
  var index = getArrayIndex($('#' + propsName).children(), parent.parentElement.parentElement.parentElement);
  var array1 = Object.keys(modalPropsObj.props).sort();
  for (var a in modalPropsObj.props) {
    if (array1[index] == a) {
      modalPropsObj.props[a].value.splice(i, 1)
    }
  }
  $(e.target.parentElement.parentElement).remove();

}
//选择等待时间
function changeTime(e) {
  e = e || window.event;
  var parent = $(e.target).parent().parent();
  var children = $(parent).children();
  var index = getArrayIndex($('#' + modalPropsObj.type).children(), $(e.target).parent().parent().parent()[0]);
  var propArray = Object.keys(modalPropsObj.props).sort();
  if (e.target.checked) {
    if (e.target.value == "1") {
      $(children[3]).removeClass('display-none');
      modalPropsObj.time = e.target.value;
      modalPropsObj.props[propArray[index]].value = e.target.value;
    } else {
      $(children[3]).addClass('display-none');
      modalPropsObj.time = e.target.value;
      modalPropsObj.props[propArray[index]].value = e.target.value;
    }
  }
  if (parseInt(e.target.value) > 1) {
    modalPropsObj.time = e.target.value;
    modalPropsObj.props[propArray[index]].value = e.target.value;
  }
}
function getCondi(e) {
  e = e || window.event;
  modalPropsObj.condition = e.target.value
}
//转化等待时间
function transWatingTime(obj) {
  var time = parseInt(obj.time);
  return time

}

//打开属性框时获取等待时间的值
function getRadioVal(obj, name) {
  var time = document.getElementsByName(name);
  for (var i in time) {
    if (obj.time === time[i].value) {
      time[i].checked = true;
    }
    if (parseInt(obj.time) > 1) {
      $(time[3]).parent().removeClass('display-none');
      time[2].checked = true;
      $(time[3]).val(obj.time)
    }
  }
  if (parseInt(obj.time) <= 1) {
    $(time[3]).parent().addClass('display-none');
    time[3].value = "";
  }
}

//存储新属性框的值
function saveInputVal(e) {
  e = e || window.event;
  var index = getArrayIndex($('#' + modalPropsObj.type).children(), e.target.parentElement.parentElement.parentElement);
  var propArray = Object.keys(modalPropsObj.props).sort();
  modalPropsObj.props[propArray[index]].value = e.target.value

}
function savePropsTitle(e) {
  e = e || window.event;
  var propArray = Object.keys(modalPropsObj.props).sort();
  modalPropsObj.props[propArray[0]].value = e.target.value;
  modalPropsObj.text = e.target.value;
}

$('#drawCanvas').data('line',true);
//出口条件名称
function saveConText(e){
  e=e||window.event;
  modalPropsObj.text= e.target.value;
}