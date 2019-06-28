/**
 * Created by Administrator on 2017/8/29.
 */
  //提示工具激活
$(function () { $("[data-toggle='tooltip']").tooltip(); });
//默认关闭连线
$('#drawCanvas').data('line',false);
jQuery(document).ready(function () {
  //jAlert($.i18n.prop('editor.ui.selectLanguage'));
  $(document).on("contextmenu", function (e) {
    return false;
  });

  $("#simpleLine").click(function () {
    $(this).css("background-color", "darkgray");
    $("#foldLineH").css("background-color", "white");
    $("#foldLineV").css("background-color", "white");
    $("#flexLineH").css("background-color", "white");
    $("#flexLineV").css("background-color", "white");
  });
  $("#dashLine").click(function () {
    $(this).css("background-color", "darkgray");
    $("#foldLineH").css("background-color", "white");
    $("#simpleLine").css("background-color", "white");
    $("#foldLineV").css("background-color", "white");
    $("#flexLineH").css("background-color", "white");
    $("#flexLineV").css("background-color", "white");
  });
  $("#foldLineH").click(function () {
    $(this).css("background-color", "darkgray");
    $("#simpleLine").css("background-color", "white");
    $("#foldLineV").css("background-color", "white");
    $("#flexLineH").css("background-color", "white");
    $("#flexLineV").css("background-color", "white");
  });
  $("#foldLineV").click(function () {
    $(this).css("background-color", "darkgray");
    $("#simpleLine").css("background-color", "white");
    $("#foldLineH").css("background-color", "white");
    $("#flexLineH").css("background-color", "white");
    $("#flexLineV").css("background-color", "white");
  });
  $("#flexLineH").click(function () {
    $(this).css("background-color", "darkgray");
    $("#simpleLine").css("background-color", "white");
    $("#foldLineH").css("background-color", "white");
    $("#foldLineV").css("background-color", "white");
    $("#flexLineV").css("background-color", "white");
  });
  $("#flexLineV").click(function () {
    $(this).css("background-color", "darkgray");
    $("#simpleLine").css("background-color", "white");
    $("#foldLineH").css("background-color", "white");
    $("#foldLineV").css("background-color", "white");
    $("#flexLineH").css("background-color", "white");
  });



  var modes = jQuery("[divType='mode']");
  var modeLength = modes.length;

  for (var i = 0; i < modeLength; i++) {
    modes[i].gtype = modes[i].getAttribute("gtype");
    modes[i].datatype = modes[i].getAttribute("datatype");
    var text = $(modes[i]).find("span").eq(0).text();
    editor.drag(modes[i], document.getElementById('drawCanvas'), text, modalData);
  }
  //加载网络拓扑图
  editor.loadTopology("images/backimg.png",{}, {}, "");
});