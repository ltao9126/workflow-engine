/**
 * Created by Administrator on 2017/8/29.
 */
//分页操作
function getPages() {
  var parent = $('#page-list');
  var ul = $('#page-ul');
  $.get(
    pages.obj[0].url + '?pageIndex=' + pages.currentPage + '&pageSize=' + pages.size + '&' + pages.obj[0].parameter[0] + '=' + pages.pointno + '&PointName=' + pages.pointname,
    function (res) {
      //处理数据
      parent.empty();
      ul.empty();
      if (!res.data.list) {
        res.data.list = [];
      }
      var data = res.data.list;
      var array = [];
      for (var z = 0; z < data.length; z++) {
        array[z] = {value: data[z].etypename, name: data[z].pointno, type: 'equip'};
      }
      //渲染页面
      //for (var i=0;i<data.length;i++){
      //    $('<div class="checkbox-modal col-xs-2">' +
      //        '<label><input type="checkbox" value="' +
      //        array[i].value + '" name="checkBoxProp" etype="' + array[i].name + '" ' +
      //        'type1="' + array[i].type + '">' + array[i].value + '</label></div>').appendTo(parent)
      //}
      createCheckBox(array, parent, pages.index);
      //处理分页
      //获取页码
      pages.num = [];
      pages.total = res.data.pageNumber;
      //for (var k = 1; k <= res.data.pageNumber; k++) {
      //  pages.num.push(k)
      //}
      pages.num.splice(0, 0, "<<", "<");
      pages.num.splice(pages.num.length, 0, ">", ">>");
      pages.currentPage = res.data.pageIndex;
      var page = pages.currentPage + 1;
      for (var p = 0; p < pages.num.length; p++) {
        if (pages.num[p] == page) {
          $(ul).append('<li class="active"><a onclick="turnPages(event)">' + pages.num[p] + '</a></li>');
        } else {
          $(ul).append('<li onclick="turnPages(event)"><a>' + pages.num[p] + '</a></li>');
        }
      }
      $('#page_operation').empty();
      $('#page_operation').append('<span>共</span>' + pages.total + '<span>页</span>');
      //每页显示多少条
      $('#page_size').val(pages.size);
      $('#page_turn').val(page)
    }
  )
}

function turnPages(e) {
  e = e || window.event;
  var num = e.target.text;
  switch (num) {
    case '<<':
      num = 0;
      break;
    case '<':
      num = pages.currentPage - 1;
      if (num < 0) {
        return false
      }
      break;
    case '>':
      num = pages.currentPage + 1;
      if (num == pages.total) {
        return false
      }
      break;
    case '>>':
      num = pages.total - 1;
      break;
    default:
      num = parseInt(num) - 1;
  }
  pages.currentPage = num;
  getPages()
}

//每页显示多少条，跳转具体页面
function changePageSize(e) {
  e = e || window.event;
  pages.size = e.target.value;
}
function changePage(e) {
  e = e || window.event;
  if (parseInt(e.target.value) > pages.total) {
    e.target.value = pages.total
  }
  pages.currentPage = parseInt(e.target.value) - 1;
}
//关键词搜索
function changepointno(e) {
  e = e || window.event;
  pages.pointno = e.target.value
}
function changepointname(e) {
  e = e || window.event;
  pages.pointname = e.target.value
}
//清空
function clearInput() {
  var input = document.getElementsByName('keywords');
  input[0].value = "";
  input[1].value = "";
  pages.size = 12;
  pages.currentPage = "";
  pages.pointno = "";
  pages.pointname = "";
  getPages()
}