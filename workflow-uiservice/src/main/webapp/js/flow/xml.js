/**
 * Created by Administrator on 2017/8/2.
 */
var app=angular.module('app',[]);

app.controller('Index',function(constant){
  var vm=this;
  vm.constant=constant;
  vm.data={};
  vm.data.propertyInfos=[];
  vm.data.propertyInfo={};
  vm.data.propertyInfo.propertyValues=[];
  vm.data.propertyInfo.propertyValuesUrl=[];
  vm.data.Urls={};
  vm.data.Urls.parameter=[];

//默认执行一次
  function defaultAdd(){
    vm.data.Urls.parameter.push(vm.data.parameter);
    vm.data.propertyInfo.propertyValuesUrl.push(vm.data.Urls);
    vm.data.propertyInfo.propertyValues.push(vm.data.propertyValue);
    vm.data.propertyInfos.push(vm.data.propertyInfo);
  }
  defaultAdd();


  vm.addPropertyValue=function(){
    vm.data.propertyInfo.propertyValues.push(vm.data.propertyValue);
    vm.data.propertyValue=""
  };
  vm.propertyValueUrl=function(){
    vm.data.Urls={};
    vm.data.Urls.parameter=[];
    vm.data.propertyInfo.propertyValuesUrl.push(vm.data.Urls);
  };
  vm.addParameter=function(){
    vm.data.Urls.parameter.push(vm.data.parameter);
    vm.data.parameter=""
  };
  vm.addPropertyInfos=function(){
    vm.data.propertyInfo={};
    vm.data.propertyInfo.propertyValues=[];
    vm.data.propertyInfo.propertyValuesUrl=[];
    vm.data.Urls={};
    vm.data.Urls.parameter=[];
    vm.data.parameter="";
    defaultAdd();
    //vm.data.propertyInfos.push(vm.data.propertyInfo);
  };

  vm.save=function(){
    var data={};
    data=JSON.stringify(vm.data);
    data=JSON.parse(data);
    delete data.parameter;
    delete data.Urls;
    delete data.propertyInfo;
    data=JSON.stringify(data);
    $.post("busActivitySave.do",
      data,function(e){
        alert(e)
      })

  }

});

app.constant('constant',{
  "propertyValueType":["INPUT", "RADIO", "RADIO_TEXT", "CHECKBOX",
    "CTRL_CHECKBOX", "AJAX_CHECKBOX", "NUM",
    "TEXTAREA", "SELECT_INPUT", "TREE",
    "AJAX_TREE", "AJAX_PAGE"],
  "requestType":["GET","POST"]
});