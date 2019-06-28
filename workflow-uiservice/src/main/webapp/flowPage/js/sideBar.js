/**
 * Created by Administrator on 2017/8/28.
 */
//左侧菜单栏相关事件

//左侧菜单的切换显示
$('#basicGraph').hide();
$('#key_node').hide();
$('#module_node').hide();
//切换左侧菜单栏宽度
function showLeftContent(event) {
  var a=0;
  for (var i=0;i<$('#leftContent').find('.key').length;i++){
    if( $('#leftContent').find('.key')[i].style.display=="none"){
      a++
    }
  }
  if(a==3){
    $('#leftContent').stop().animate({width:'241px'},function(){
      $(event).fadeToggle()
    })
  }else {
    $(event).stop().fadeToggle(function(){
      $('#leftContent').animate({width:'120px'})
    })
  }
}
//线条显示
function showLine() {
  $('#key_node').hide();
  $('#module_node').hide();
  showLeftContent($('#basicGraph'));
}
//起始节点显示
function showKey_node() {
  $('#basicGraph').hide();
  $('#module_node').hide();
  showLeftContent($('#key_node'));
}
//模块显示
function showModule_node() {
  $('#key_node').hide();
  $('#basicGraph').hide();
  showLeftContent($('#module_node'));
}

//生成侧边栏列表图标
function getIcon(icon, iconType) {
  var src;
  iconType.forEach(function (item) {
    if (icon == item.name) {
      src = item.url
    }
  });
  var name = 'name';
  var iconArray = getObjArray(iconArray, iconType, name);
  if (iconArray.indexOf(icon) == -1) {
    src = 'icon/fire.png'
  }
  return src;
}
//动态生成模块
function createModule(modalData) {
  $('#createModule').empty();
  for (var i = 0; i < modalData.length; i++) {
    var src = getIcon(modalData[i].showIcon, iconType);
    $('#createModule').append('<tr style="width: 100%"><td width="100%" align="center"  class="text-center border-bottom">' +
      '<div divType="mode" class="drag-modal" draggable="true" bussClass=' + modalData[i].bussClass + '><div class="title">&nbsp;</div>' +
      '<img name="backGroundImg" src=' + src + ' class="nodeStyle">' +
      '<br><span>' + modalData[i].bussCaption + '</span></div></td></tr>')
  }
}
createModule(modalData);


//切换框选模式
function changeSceneModal(e, boolean) {
  e = e || window.event;
  e.target = e.target || e.srcElement;
  function a(){
    if (!e.target.checked && boolean == true) {
      editor.stage.mode = 'edit';
      $('#editMode')[0].checked = true;
    } else
    if (!e.target.checked && boolean == false) {
      editor.stage.mode = 'select';
      $('#selectMode')[0].checked = true;
    }
  }
  function b(){
    if (e.target.checked && boolean == true) {
      editor.stage.mode = 'select';
      $('#editMode')[0].checked = false;
    } else if (e.target.checked && boolean == false){
      editor.stage.mode = 'edit';
      $('#selectMode')[0].checked = false;
    }
  }
  a();
  b()

}