/**
 * Created by Administrator on 2017/8/29.
 */
//表单验证(验证端口与ip)
function checkIpPatten(e) {
  e = e || window.event;
  if (!ipPatten.test(e.target.value)) {
    alert("请输入正确的地址格式。示例：xxx.xxx.xxx:xxxx")
  } else {
    //存值
    var index = getArrayIndex($('#' + modalPropsObj.type).children(), e.target.parentElement.parentElement.parentElement);
    var propArray = Object.keys(modalPropsObj.props).sort();
    modalPropsObj.props[propArray[index]].value = e.target.value
  }
}
//表单验证（验证最大值最小值）
function checkRange(e) {
  e = e || window.event;
  var n = true;
  //转换为数字再比较
  if (parseInt(e.target.value) > parseInt(e.target.max)) {
    n = false;
    alert("允许的最大值为：" + e.target.max);
  }
  if (parseInt(e.target.value) < parseInt(e.target.min)) {
    n = false;
    alert("允许的最小值为：" + e.target.min);
  }

  //存值\
  if (n) {
    var index = getArrayIndex($('#' + propsName).children(), e.target.parentElement.parentElement.parentElement);
    var propArray = Object.keys(modalPropsObj.props).sort();
    modalPropsObj.props[propArray[index]].value = e.target.value
  }

}
