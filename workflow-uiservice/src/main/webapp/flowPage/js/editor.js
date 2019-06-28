
function propertyPanel(mainControlDiv) {
  //网络属性面板操作
  this.control = $(mainControlDiv);
  //帮助窗口
  this.helpWin = $("#help");
  //JSON数据压缩
  this.jsonCode = {
    x: "0",
    y: "1",
    width: "2",
    height: "3",
    visible: "4",
    alpha: "5",
    rotate: "6",
    scaleX: "7",
    scaleY: "8",
    strokeColor: "9",
    fillColor: "a",
    shadow: "b",
    shadowColor: "c",
    shadowOffsetX: "d",
    shadowOffsetY: "e",
    transformAble: "f",
    zIndex: "g",
    dragable: "h",
    selected: "i",
    showSelected: "j",
    isMouseOver: "k",
    deviceId: "l",
    dataType: "m",
    nodeImage: "n",
    text: "z",
    font: "o",
    fontColor: "p",
    textPosition: "q",
    textOffsetX: "r",
    textOffsetY: "s",
    borderRadius: "t",
    deviceA: "u",
    deviceZ: "v",
    lineType: "w",
    bundleOffset: "x",
    arrowsRadius: "y",
    lineWidth: "C",
    lineJoin: "D",
    elementType: "E",
    backgroundColor: "F",
    mode: "G",
    paintAll: "H",
    areaSelect: "I",
    translate: "J",
    translateX: "K",
    translateY: "L",
    lastTranslatedX: "M",
    lastTranslatedY: "N",
    visible: "P",
    version: "S",
    frames: "T",
    wheelZoom: "U",
    childs: "V",
    offsetGap: "O",
    borderColor: "A",
    direction: "B",
    templateId: "T",
    totalLevel: "Y",
    topoLevel: "Z",
    parentLevel: "X",
    nextLevel: "R"
  };
}
//修改插件，实现虚线滑动
CanvasRenderingContext2D.prototype.JTopoDashedLineTo = function (a, b, c, d, e) {
  var animespeed = (new Date()) / 100;//滑动速度
  "undefined" == typeof e && (e = 12);
  var f, g, i, h, k,j;
  //if(c>=a){
    f = c - a;//x轴差
  //}else {
  //  f=a-c;
  //}
  //if(d>=b){
    g = d - b;//y轴差
  //}else {
  //  g = b - d;
  //}
    h = Math.floor(Math.sqrt(f * f + g * g)),//勾股定理,直线长度
    i = 0 >= e ? h : h / e,//虚线段数
    j = g / h * e,//连接点坐标
    k = f / h * e;//连接点坐标
  this.beginPath();
  animespeed = animespeed % (e * 2);
  var txs = -f / h * animespeed;
  var tys = -g / h * animespeed;
  for (var l = 0; i > l; l++) {
    l % 2 ? this.lineTo(a + l * k - txs, b + l * j - tys) : this.moveTo((a + l * k - txs) > (a + i * k) ? (a + l * k - txs) : (a + l * k - txs), (b + l * j - tys) > (b + i * j) ? (b + l * j - tys) : (b + l * j - tys))
  }
  this.stroke()
};
CanvasRenderingContext2D.prototype.JtopoDrawPointPath = function (a, b, c, d, e, f) {
  var animespeed = (new Date()) / 10;
  var xs = c - a,
    xy = d - b,
    l = Math.floor(Math.sqrt(xs * xs + xy * xy)),
    colorlength = 50,
    j = l;
  xl = xs / l,
    yl = xy / l;
  var colorpoint = animespeed % (l + colorlength) - colorlength;
  for (var i = 0; i < j; i++) {
    if (((i) > colorpoint) && ((i) < (colorpoint + colorlength))) {
      this.beginPath();
      this.strokeStyle = e;
      this.moveTo(a + (i - 1) * xl, b + (i - 1) * yl);
      this.lineTo(a + i * xl, b + i * yl);
      this.stroke();
    } else {
      this.beginPath();
      this.strokeStyle = f;
      this.moveTo(a + (i - 1) * xl, b + (i - 1) * yl);
      this.lineTo(a + i * xl, b + i * yl)
      this.stroke();
    }
  }
};

/**
 * @param templateid 节点的模板ID
 * @param moduleId
 * @param dataType
 */

/**
 * 保存序列化的JSON数据到服务器,为减少请求参数长度,进行了字符串的替换
 */
propertyPanel.prototype.saveToplogy = function (showAlert) {
  editor.mainMenu.hide();
  var self = this;
  //先删除标尺线
  editor.utils.clearRuleLines();
  //保存container状态
  var containers = editor.utils.getContainers();
  for (var c = 0; c < containers.length; c++) {
    var temp = [];
    var nodes = containers[c].childs;
    for (var n = 0; n < nodes.length; n++) {
      if (nodes[n] instanceof JTopo.Node) {
        temp.push(nodes[n].deviceId);
      }
    }
    containers[c].childNodes = temp.join(",");
  }

  //转换JSON数据
  var svgData = editor.stage.toJson();
  svgData = eval("(" + svgData + ")");
  console.log(typeof (svgData));
  console.log(svgData);
  //生成流程数据
  if (svgData.childs[0].childs.length != 0) {
    var flowData={};
    flowData = getFlowData(modalData, svgData, judge, flowData);
    if (flowData==false)return;
    var data={
      svgData:svgData,
      flowData:flowData
    };
    if(localStorage.data){
      $.ajax({
        url: editUrlD,
        type: "POST",
        async:false,
        data:JSON.stringify(data),
        success:function(res){
          res=JSON.parse(res);
          if (res.status.success=="OK"){
            alert("保存成功");
            window.location.href='index.html'
          }else {
            alert(res.status.errorMsg);
          }
        }
      });
    }else {
      $.ajax({
        url: saveUrlD,
        type: "POST",
        async:false,
        data:JSON.stringify(data),
        success:function(res){
          res=JSON.parse(res);
          if (res.status.success=="OK"){
            alert("保存成功");
            window.location.href='index.html'
          }else {
            alert(res.status.errorMsg);
          }
        }
      });
    }

  }
};

/**
 * 加载环境模板ID对应的拓扑图JSON数据结构
 * @param backImg 拓扑图的背景图片
 * @param templateId 环境模板ID
 * @param topologyId 拓扑 表记录ID
 */
propertyPanel.prototype.loadTopology = function (backImg, templateId, topologyId) {
  var self = this;
  //editor.init(backImg, templateId, topologyId,"-1","");
  if (!templateId) {
    templateId = editor.templateId;
  }
  if (localStorage.data) {
    var editData = localStorage.data;
    editData = JSON.parse(editData);
    editData.svgData = JSON.parse(editData.svgData);
    editData.flowData = JSON.parse(editData.flowData);
    var restoreData = editData.svgData;
    editor.init(backImg, templateId, topologyId, restoreData, "");
  }else {
    editor.init(backImg, templateId, topologyId,"-1","");
  }

};

/**
 * 清空所有节点
 * @param templateId
 */
propertyPanel.prototype.deleteAllNodes = function (templateId) {
  if (!templateId) {
    return;
  }
  var self = this;
  $('#confirm_content').empty();
  $('#confirm_content').append('<h3>确定要清空拓扑图吗?</h3>');
  $('#flow_confirm').modal({backdrop: false});
  $('#flow_confirm').on('hidden.bs.modal', function (e) {
    if ($('#flow_confirm').data('confirm')==1) {
      editor.stage.childs.forEach(function (s) {
        s.clear();
      });
      //连线重置
      editor.beginNode = null;
      editor.link = null;
      //关闭属性面板
      $('.right-content').fadeOut();
      $('#flow_confirm').off('hidden.bs.modal')
    }
    $('#flow_confirm').data('confirm',"2")
  });

};
//画图参数初始化
function networkTopologyEditor(mainControl) {
  //绘图参数
  this.config = {
    stageFrames: 500,
    //新建节点默认尺寸
    defaultWidth: 32,
    defaultHeight: 32,
    //滚轮缩放比例
    defaultScal: 0.95,
    ////是否显示鹰眼对象
    eagleEyeVsibleDefault: false,
    //连线颜色
    strokeColor: "0,200,255",
    //连线宽度
    lineWidth: 1,
    //二次折线尾端长度
    offsetGap: 40,
    //线条箭头半径
    arrowsRadius: 5,
    //折线的方向
    direction: "horizontal",
    //节点文字颜色
    nodeFontColor: "black",
    //连线文字颜色
    lineFontColor: "0,200,255",
    //是否显示连线阴影
    showLineShadow: true,
    //节点旋转幅度
    rotateValue: 0.5,
    //节点缩放幅度
    nodeScale: 0.2,
    alpha: 1,
    containerAlpha: 0.5,
    nodeStrokeColor: "22,124,255",
    lineStrokeColor: "0,200,255",
    fillColor: "22,124,255",
    containerFillColor: "10,100,80",
    shadow: false,
    shadowColor: "rgba(0,0,0,0.5)",
    font: "12px Consolas",
    fontColor: "black",
    lineJoin: "lineJoin",
    borderColor: "10,10,100",
    borderRadius: 30,
    shadowOffsetX: 3,
    shadowOffsetY: 6
  };
  //布局参数
  this.layout = {};
  //纪录节点最后一次移动的幅度
  this.lastMovedX = 0;
  this.lastMovedY = 0;
  //绘图区属性
  this.stage = null;
  this.scene = null;

  //连线类型
  this.lineType = "line";

  //业务数据
  this.currDeviceId = null;
  this.currDataType = null;
  this.modeIdIndex = 1;
  this.templateId = null;

  //当前选择的节点对象
  this.currentNode = null;
  //节点右键菜单DOM对象
  this.nodeMainMenu = $("#nodeMainMenu");
  //连线右键菜单DOM
  this.lineMenu = $("#lineMenu");
  //全局菜单
  this.mainMenu = $("#mainMenu");
  //节点文字修改菜单
  this.nodeTextMenu = $("#nodeTextMenu");
  //节点文字方向
  this.nodeTextPosMenu = $("#nodeTextPosMenu");
  //节点分组菜单
  this.groupMangeMenu = $("#groupMangeMenu");
  //节点对齐菜单
  this.groupAlignMenu = $("#groupAlignMenu");
  this.alignGroup = $("#alignGroup");
  //容器管理菜单
  this.containerMangeMenu = $("#containerMangeMenu");

  //是否显示参考线
  this.showRuleLine = true;
  //标尺线数组
  this.ruleLines = [];
  //调用构造函数
  propertyPanel.call(this, document.getElementById("mainControl"));
}

//原型继承
networkTopologyEditor.prototype = new propertyPanel();

/**
 * 菜单初始化
 */
networkTopologyEditor.prototype.initMenus = function () {
  var self = this;
  self.lineMenu.blur(function () {
    if (self.currentNode)
      self.currentNode.text = self.deviceEditText.hide().val();
    else
      self.deviceEditText.hide();
  });

  //右键菜单事件处理
  self.nodeMainMenu.on("click", function (e) {
    //菜单文字
    var text = $.trim($(e.target).text());
    if (text == "删除节点(Delete)") {
      editor.utils.deleteSelectedNodes();
    } else if (text == "复制节点(Shift+C)") {
      self.utils.cloneSelectedNodes();
    } else if (text == "撤销(Shift+Z)") {
      self.utils.cancleNodeAction();
    } else if (text == "重做(Shift+R)") {
      self.utils.reMakeNodeAction();
    } else {
      editor.utils.saveNodeInitState();
    }

    switch (text) {
      case "放大(Shift+)":
        self.utils.scalingBig();
        self.utils.saveNodeNewState();
        break;
      case "缩小(Shift-)":
        self.utils.scalingSmall();
        self.utils.saveNodeNewState();
        break;
      case "顺时针旋转(Shift+U)":
        self.utils.rotateAdd();
        self.utils.saveNodeNewState();
        break;
      case "逆时针旋转(Shift+I)":
        self.utils.rotateSub();
        self.utils.saveNodeNewState();
        break;
      case "节点文字":
        return;
      default :

    }

    //关闭菜单
    $(this).hide();
  });

  self.nodeMainMenu.on("mouseover", function (e) {
    //隐藏第三级菜单
    self.nodeTextPosMenu.hide();
    //菜单文字
    var text = $.trim($(e.target).text());
    var menuX = parseInt(this.style.left) + $(document.getElementById('changeNodeText')).width();
    //边界判断
    if (menuX + self.nodeTextMenu.width() * 2 >= self.stage.width) {
      menuX -= (self.nodeTextMenu.width() + self.nodeMainMenu.width());
    }
    if ("节点文字" == text) {
      self.nodeTextMenu.css({
        top: parseInt(this.style.top),
        left: menuX
      }).show();
    } else {
      self.nodeTextMenu.hide();
    }
  });

  self.nodeTextMenu.on("click", function (e) {
    //菜单文字
    var text = $.trim($(e.target).text());
    if ("修改节点文字" == text) {
      editor.utils.saveNodeInitState();
      //隐藏菜单显示文字输入框
      self.nodeTextMenu.hide();
      self.nodeMainMenu.hide();
      self.deviceEditText.css({
        top: self.yInCanvas,
        left: self.xInCanvas
      }).show();
      self.deviceEditText.attr('value', self.currentNode.text);
      self.deviceEditText.focus();
      self.deviceEditText.select();
    }
  });

  //节点右键二级菜单
  self.nodeTextMenu.on("mouseover", function (e) {
    //菜单文字
    var text = $.trim($(e.target).text());
    if ("调整节点文字位置" == text) {
      //处于边界时三级菜单位置调整
      var menuX = parseInt(this.style.left) + $(document.getElementById('justfyNodeText')).width();
      if (parseInt(this.style.left) < parseInt(document.getElementById('nodeMainMenu').style.left)) {
        menuX = parseInt(this.style.left) - $(document.getElementById('justfyNodeText')).width();
      }
      self.nodeTextPosMenu.css({
        top: parseInt(this.style.top),
        left: menuX
      }).show();
    } else {
      self.nodeTextPosMenu.hide();
    }
  });

  //修改节点文字位置菜单
  self.nodeTextPosMenu.on("click", function (e) {
    //菜单文字
    var text = $.trim($(e.target).text());
    if (self.currentNode && self.currentNode instanceof JTopo.Node) {
      self.utils.saveNodeInitState();
      switch (text) {
        case "顶部居左":
          self.currentNode.textPosition = "Top_Left";
          self.utils.saveNodeNewState();
          break;
        case "顶部居中":
          self.currentNode.textPosition = "Top_Center";
          self.utils.saveNodeNewState();
          break;
        case "顶部居右":
          self.currentNode.textPosition = "Top_Right";
          self.utils.saveNodeNewState();
          break;
        case "中间居左":
          self.currentNode.textPosition = "Middle_Left";
          self.utils.saveNodeNewState();
          break;
        case "居中":
          self.currentNode.textPosition = "Middle_Center";
          self.utils.saveNodeNewState();
          break;
        case "中间居右":
          self.currentNode.textPosition = "Middle_Right";
          self.utils.saveNodeNewState();
          break;
        case "底部居左":
          self.currentNode.textPosition = "Bottom_Left";
          self.utils.saveNodeNewState();
          break;
        case "底部居中":
          self.currentNode.textPosition = "Bottom_Center";
          self.utils.saveNodeNewState();
          break;
        case "底部居右":
          self.currentNode.textPosition = "Bottom_Right";
          self.utils.saveNodeNewState();
          break;
        default :
      }
      $('#mainMenu').hide();
    }
  });
  //连线菜单
  self.lineMenu.on("click", function (e) {
    //关闭菜单
    $(this).hide();
    var text = $.trim($(e.target).text());
    switch (text) {
      case "添加描述":
        editor.utils.addNodeText(this.style.left, this.style.top);
        break;
      case "删除连线":
        editor.utils.deleteLine()
        break;
      default :
    }
  });

  //系统设置菜单
  self.mainMenu.on("click", function (e) {
    //关闭菜单
    $(this).hide();
    var text = $.trim($(e.target).text());
    if (text.indexOf("参考线") != -1) {
      if (editor.showRuleLine) {
        editor.showRuleLine = false;
        $("#ruleLineSpan").text("显示参考线");
      }
      else {
        editor.showRuleLine = true;
        $("#ruleLineSpan").text("隐藏参考线");
      }
    }
  });

  //节点分组菜单
  self.groupMangeMenu.on("click", function (e) {
    $(this).hide();
    var text = $.trim($(e.target).text());
    if (text == "新建分组") {
      self.utils.toMerge();
    }
  });
  //对齐
  self.groupAlignMenu.on("click", function (e) {
    var currNode = self.currentNode;
    var selectedNodes = self.utils.getSelectedNodes();
    if (!currNode || !selectedNodes || selectedNodes.length == 0) return;
    $(this).hide();
    var text = $.trim($(e.target).text());
    selectedNodes.forEach(function (n) {
      if (n.deviceId == currNode.deviceId) return true;
      if (text == "水平对齐") {
        n.y = currNode.y;
      } else if (text == "垂直对齐") {
        n.x = currNode.x;
      } else {

      }
    });
  });
  self.groupMangeMenu.on("mouseover", function (e) {
    var text = $.trim($(e.target).text());
    if (text == "对齐方式") {
      //节点对齐
      var menuX = parseInt(this.style.left) + $(document.getElementById('alignGroup')).width();
      if (menuX + self.alignGroup.width() * 2 >= self.stage.width) {
        menuX -= (self.alignGroup.width() + self.groupMangeMenu.width());
      }
      self.groupAlignMenu.css({
        top: parseInt(this.style.top) + $(document.getElementById('alignGroup')).height(),
        left: menuX
      }).show();
    } else {
      self.groupAlignMenu.hide();
    }
  });
  //容器管理菜单
  self.containerMangeMenu.on("click", function (e) {
    var cNode = editor.currentNode;
    if(!cNode instanceof JTopo.Container) return;
    $(this).hide();
    var text = $.trim($(e.target).text());
    if(text == "拆分"){
      self.utils.toSplit();
      self.utils.deleteNode(cNode)
    }
  });
};
/**
 * 编辑器初始化方法,根据请求返回结果加载空白的或者指定结构的拓扑编辑器
 * @param backImg     背景图片
 * @param templateId  环境模板ID
 * @param topologyId  拓扑记录ID
 * @param stageJson    拓扑JSON结构
 */
networkTopologyEditor.prototype.init = function (backImg, templateId, topologyId, stageJson, templateName) {
  if (!stageJson) {
    jAlert("加载拓扑编辑器失败!");
    return;
  }
  this.templateId = templateId;
  this.topologyId = topologyId;
  //创建JTOP舞台屏幕对象
  var canvas = document.getElementById('drawCanvas');
  canvas.width = $("#contextBody").width();
  canvas.height = $("#contextBody").height();
  //加载空白的编辑器
  if (stageJson == "-1") {
    this.stage = new JTopo.Stage(canvas);
    this.modeIdIndex = 1;
    this.scene = new JTopo.Scene(this.stage);
  } else {
    this.stage = JTopo.createStageFromJson(stageJson, canvas);
    this.scene = this.stage.childs[0];
  }
  $("#parentLevel").val(this.stage.parentLevel);
  //拓扑层次切换
  var options = "";
  for(var i = 1; i <= this.scene.totalLevel ;i++){
    options += '<option value="' + i +'" ';
    if( i == this.stage.topoLevel){
      options += 'selected="selected" ';
    }
    options += '>编辑第' + i + '层</option>';
  }
  $("#selectLevel").append(options);

  //滚轮缩放
  this.stage.frames = this.config.stageFrames;
  this.stage.wheelZoom = this.config.defaultScal;
  this.stage.eagleEye.visible = this.config.eagleEyeVsibleDefault;
  this.scene.mode = "edit";
  //背景由样式指定
  //this.scene.background = backImg;

  //用来连线的两个节点
  this.tempNodeA = new JTopo.Node('tempA');
  this.tempNodeA.setSize(1, 1);
  this.tempNodeZ = new JTopo.Node('tempZ');
  this.tempNodeZ.setSize(1, 1);
  this.beginNode = null;
  this.link = null;
  var self = this;

  //初始化菜单
  this.initMenus();
  //双击编辑文字
  this.scene.dbclick(function (e) {
    if (e.target)
      self.currentNode = e.target;
    else
      return;
    currentNode = self.currentNode;
    getSideBarObj(modalData, attribute, currentNode);
    showProps(e.target);
  });
  //清除起始节点不完整的拖放线
  this.scene.mousedown(function (e) {
    if (self.link && !self.isSelectedMode && (e.target == null || e.target === self.beginNode || e.target === self.link)) {
      this.remove(self.link);
    }
  });

  //处理右键菜单，左键连线
  this.scene.mouseup(function (e) {
    if (e.target)
      self.currentNode = e.target;
    if (e.target && e.target instanceof JTopo.Node && e.target.layout && e.target.layout.on && e.target.layout.type && e.target.layout.type != "auto")
      JTopo.layout.layoutNode(this, e.target, true, e);
    if (e.button == 2) {//右键菜单
      $('#Menu').hide();
      var menuY = e.layerY ? e.layerY : e.offsetY;
      var menuX = e.layerX ? e.layerX : e.offsetX;
      //记录鼠标触发位置在canvas中的相对位置
      self.xInCanvas = menuX;
      self.yInCanvas = menuY;
      if (e.target) {
        //处理节点右键菜单事件
        if (e.target instanceof JTopo.Node) {
          var selectedNodes = self.utils.getSelectedNodes();
          //如果是节点多选状态弹出分组菜单
          if (selectedNodes && selectedNodes.length > 1) {
            //判断边界出是否能完整显示弹出菜单
            if (menuX + self.groupMangeMenu.width() >= self.stage.width) {
              menuX -= self.groupMangeMenu.width();
            }
            if (menuY + self.groupMangeMenu.height() >= self.stage.height) {
              menuY -= self.groupMangeMenu.height();
            }
            self.groupMangeMenu.css({
              top: menuY,
              left: menuX
            }).show();
          } else {
            //判断边界出是否能完整显示弹出菜单
            if (menuX + self.nodeMainMenu.width() >= self.stage.width) {
              menuX -= self.nodeMainMenu.width();
            }
            if (menuY + self.nodeMainMenu.height() >= self.stage.height) {
              menuY -= self.nodeMainMenu.height();
            }
            self.nodeMainMenu.css({
              top: menuY,
              left: menuX
            }).show();
          }
        } else if (e.target instanceof JTopo.Link) {//连线右键菜单
          if (e.target.lineType == "rule") {
            editor.utils.hideRuleLines();//删除标尺线
          } else {
            self.lineMenu.css({
              top: e.layerY ? e.layerY : e.offsetY,
              left: e.layerX ? e.layerX : e.offsetX
            }).show();
          }
        } else if (e.target instanceof JTopo.Container) {//容器右键菜单
          self.containerMangeMenu.css({
            top: e.layerY ? e.layerY : e.offsetY,
            left: e.layerX ? e.layerX : e.offsetX
          }).show();
        }
      } else {
        //判断边界出是否能完整显示弹出菜单
        if (menuX + self.mainMenu.width() >= self.stage.width) {
          menuX -= self.mainMenu.width();
        }
        if (menuY + self.mainMenu.height() >= self.stage.height) {
          menuY -= self.mainMenu.height();
        }
        self.mainMenu.css({
          top: menuY,
          left: menuX
        }).show();
      }

    } else if (e.button == 1) {//中键

    } else if (e.button == 0) {//左键
      if (!$('#drawCanvas').data('line'))
      return false;
        if (e.target != null && e.target instanceof JTopo.Node && !self.isSelectedMode) {
          if (self.beginNode == null) {
            self.beginNode = e.target;
            if (self.lineType == "line") {
              self.link = new JTopo.Link(self.tempNodeA, self.tempNodeZ);
              self.link.lineType = "line";
            } else if (self.lineType == "foldLine") {
              self.link = new JTopo.FoldLink(self.tempNodeA, self.tempNodeZ);
              self.link.lineType = "foldLine";
              self.link.direction = self.config.direction;
            } else if (self.lineType == "dashLine") {
              self.link = new JTopo.Link(self.tempNodeA, self.tempNodeZ);
              self.link.lineType = "line";
              self.link.direction = self.config.direction;
            } else if (self.lineType == "flexLine") {
              self.link = new JTopo.FlexionalLink(self.tempNodeA, self.tempNodeZ);
              self.link.direction = self.config.direction;
              self.link.lineType = "flexLine";
            } else if (self.lineType == "curveLine") {
              self.link = new JTopo.CurveLink(self.tempNodeA, self.tempNodeZ);
              self.link.lineType = "curveLine";
            }
            self.link.dashedPattern = 5;
            self.link.lineWidth = self.config.lineWidth;
            self.link.shadow = self.config.showLineShadow;
            self.link.strokeColor = JTopo.util.randomColor();
            this.add(self.link);
            self.tempNodeA.setLocation(e.x, e.y);
            self.tempNodeZ.setLocation(e.x, e.y);
          } else if (e.target && e.target instanceof JTopo.Node && self.beginNode !== e.target) {//结束连线
            var endNode = e.target;
            //判断两个节点是否有循环引用
            for (var el = 0; el < endNode.outLinks.length; el++) {
              //存在循环引用，线条变红
              if (endNode.outLinks[el].nodeZ == self.beginNode) {
                if (self.link)
                  this.remove(self.link);
                self.beginNode = null;
                return;
              }
            }
            //节点间是否有重复连线,即起点到终点有两条以上连线
            for (var el2 = 0; el2 < self.beginNode.outLinks.length; el2++) {
              //起始节点已经有一条线指向目标节点
              if (self.beginNode.outLinks[el2].nodeZ == endNode) {
                if (self.link)
                  this.remove(self.link);
                self.beginNode = null;
                return;
              }
            }
            var l;
            if (self.lineType == "line") {
              l = new JTopo.Link(self.beginNode, endNode);
              l.lineType = "line";
            } else if (self.lineType == "dashLine") {
              l = new JTopo.Link(self.beginNode, endNode);
              l.lineType = "line";
              l.dashedPattern = 5;
            } else if (self.lineType == "foldLine") {
              l = new JTopo.FoldLink(self.beginNode, endNode);
              l.direction = self.config.direction;
              l.bundleOffset = self.config.offsetGap;//折线拐角处的长度
              l.lineType = "foldLine";
            } else if (self.lineType == "flexLine") {
              l = new JTopo.FlexionalLink(self.beginNode, endNode);
              l.direction = self.config.direction;
              l.lineType = "flexLine";
              l.offsetGap = self.config.offsetGap;
            } else if (self.lineType == "curveLine") {
              l = new JTopo.CurveLink(self.beginNode, endNode);
              l.lineType = "curveLine";
            }

            //保存线条所连接的两个节点ID
            l.deviceA = self.beginNode.deviceId;
            l.deviceZ = endNode.deviceId;
            if (self.lineType != "curveLine")
              l.arrowsRadius = self.config.arrowsRadius;
            l.strokeColor = self.config.strokeColor;
            l.lineWidth = self.config.lineWidth;
            l.condition=[];
            l.text="";
            this.add(l);
            self.beginNode = null;
            this.remove(self.link);
            self.link = null;
          } else {
            self.beginNode = null;
          }
        } else {
          if (self.link)
            this.remove(self.link);
          self.beginNode = null;
        }
      }
  });

  //动态更新连线坐标
  this.scene.mousemove(function (e) {
    if (!self.isSelectedMode && self.beginNode)
      self.tempNodeZ.setLocation(e.x, e.y);
  });

  this.scene.mousedrag(function (e) {
    if (!self.isSelectedMode && self.beginNode)
      self.tempNodeZ.setLocation(e.x, e.y);
  });

  //单击编辑器隐藏菜单
  this.stage.click(function (event) {
    editor.utils.hideRuleLines();
    if (event.button == 0) {
      // 关闭弹出菜单（div）
      $('#mainMenu').hide();
      $('#groupMangeMenu').hide();
      $('#groupAlignMenu').hide();
      $('#nodeMainMenu').hide();
      $('#lineMenu').hide();
      $('#nodeTextMenu').hide();
      $('#nodeTextPosMenu').hide();


    }
  });

  this.stage.mouseout(function (e) {
    //清空标尺线
    editor.utils.hideRuleLines();
    //删掉节点带出来的连线
    if (self.link && !self.isSelectedMode && (e.target == null || e.target === self.beginNode || e.target === self.link)) {
      self.scene.remove(self.link);
    }
  });

  //画布尺寸自适应
  this.stage.mouseover(function (e) {
    if (editor.stage) {
      var w = $("#contextBody").width(), wc = editor.stage.canvas.width,
        h = $("#contextBody").height(), hc = editor.stage.canvas.height;
      if (w > wc) {
        editor.stage.canvas.width = $("#contextBody").width();
      }
      if (h > hc) {
        editor.stage.canvas.height = $("#contextBody").height();
      }
      editor.stage.paint();
    }
  });

  //按下ctrl进入多选模式，此时选择节点不能画线
  $(document).keydown(function (e) {
    if (e.shiftKey) {//组合键模式
      switch (e.which) {
        //放大 ctrl+=
        case  187:
        case  61:
          //单个节点可以撤销操作
          if (editor.currentNode instanceof JTopo.Node) {
            //保存初始状态
            editor.utils.saveNodeInitState();
            editor.utils.scalingBig();
            editor.utils.saveNodeNewState();
          } else {
            editor.utils.scalingBig();
          }
          //return false;
          break;
        //缩小 ctrl+-
        case 189:
        case  173:
          if (editor.currentNode instanceof JTopo.Node) {
            //保存初始状态
            editor.utils.saveNodeInitState();
            editor.utils.scalingSmall();
            editor.utils.saveNodeNewState();
          } else {
            editor.utils.scalingSmall();
          }
          //return false;
          break;
        case  70:
          //ctrl+f 全屏显示
          editor.utils.showInFullScreen(editor.stage.canvas, 'RequestFullScreen');
          //return false;
          break;
        case  72:
          //h 帮助
          editor.showHelpWindow();
          //return false;
          break;
        case  71:
          //ctrl+g 居中显示
          editor.utils.showInCenter();
          //return false;
          break;
        case  73:
          //shif+I 逆时针旋转
          if (editor.currentNode instanceof JTopo.Node) {
            editor.utils.saveNodeInitState();
            editor.utils.rotateSub();
            editor.utils.saveNodeNewState();
          }
          //return false;
          break;
        case  76:
          //shift+L 参考线
          editor.showRuleLine = !editor.showRuleLine;
          //return false;
          break;
        case  67:
          editor.utils.cloneSelectedNodes();
          //return false;
          break;
        case  80:
          //ctrl + p
          editor.utils.showPic();
          //return false;
          break;
        case  82:
          //单个节点重做
          if (editor.currentNode instanceof JTopo.Node) {
            editor.utils.reMakeNodeAction();
          }
          //return false;
          break;
        case  83:
          //ctrl+s 保存
          editor.saveToplogy(true);
          //return false;
          break;
        case  85:
          //shif+U 顺时针旋转
          if (editor.currentNode instanceof JTopo.Node) {
            editor.utils.saveNodeInitState();
            editor.utils.rotateAdd();
            editor.utils.saveNodeNewState();
          }
          //return false;
          break;
        case  87:
          jAlert("ctrl + w 另存为");
          //return false;
          break;
        case  89:
          //ctrl+y
          editor.utils.clear();
          //return false;
          break;
        case  90:
          //单个节点撤销
          if (editor.currentNode instanceof JTopo.Node) {
            editor.utils.cancleNodeAction();
          }
          //return false;
          break;
      }
    } else if (e.which == 46) {//单独按下delete
      editor.utils.deleteSelectedNodes();
      //return false;
    } else if (e.which == 17) {//单独按下ctrl
      self.isSelectedMode = true;
      //return false;
    }
  });
  $(document).keyup(function (e) {
    if (e.which == 17) {
      self.isSelectedMode = false;
      return false;
    }
  });
  //第一次进入拓扑编辑器,生成stage和scene对象
  if (stageJson == "-1") {
    this.saveToplogy(false);
  }
};
/**
 * 图元拖放功能实现
 * @param modeDiv
 * @param drawArea
 */
networkTopologyEditor.prototype.drag = function (modeDiv, drawArea, text, modalData) {
  if (!text) text = "";
  var self = this;
  //拖拽开始,携带必要的参数
  modeDiv.ondragstart = function (e) {
    e = e || window.event;
    var dragSrc = this;
    var backImg = $(dragSrc).find("img").eq(0).attr("src");
    backImg = backImg.substring(backImg.lastIndexOf('/') + 1);
    var datatype = $(this).attr("datatype");
    var bussClass=$(this).attr("bussClass");
    try {
      //IE只允许KEY为text和URL
      e.dataTransfer.setData('text', backImg + ";" + text + ";" + datatype + ";" + bussClass);
    } catch (ex) {
      console.log(ex);
    }
  };
  //阻止默认事件
  drawArea.ondragover = function (e) {
    e.preventDefault();
    return false;
  };
  //创建节点
  drawArea.ondrop = function (e) {
    e = e || window.event;
    var data = e.dataTransfer.getData("text");
    var img, text, datatype,bussClass;
    if (data) {
      var datas = data.split(";");
      if (datas && datas.length == 4) {
        img = datas[0];
        text = datas[1];
        datatype = datas[2];
        bussClass=datas[3];
        var node = new JTopo.Node();
        node.fontColor = self.config.nodeFontColor;
        node.setBound((e.layerX ? e.layerX : e.offsetX) - self.scene.translateX - self.config.defaultWidth / 2, (e.layerY ? e.layerY : e.offsetY) - self.scene.translateY - self.config.defaultHeight / 2, self.config.defaultWidth, self.config.defaultHeight);
        //设备图片
        node.setImage('./icon/' + img);
        //var cuurId = "device" + (++self.modeIdIndex);
        node.deviceId = "" + new Date().getTime() * Math.random();
        node.dataType = datatype;
        node.nodeImage = img;
        node.props = {};
        node.time="";
        node.condition = [];
        ++self.modeIdIndex;
        node.bussClass = bussClass;
        node.text = text;
        node.layout = self.layout;

        //每个节点创建单独的属性对象
        getSideBarObj(modalData, attribute, node);

        self.scene.add(node);

        self.currentNode = node;
      }
    }
    if (e.preventDefault()) {
      e.preventDefault();
    }
    if (e.stopPropagation()) {
      e.stopPropagation();
    }
  }
};

//编辑器实例
var editor = new networkTopologyEditor('mainControl');

//工具方法
editor.utils = {
  //获取所有选择的节点实例
  getSelectedNodes: function () {
    var selectedNodes = [];
    var nodes = editor.scene.selectedElements;
    return nodes.forEach(function (n) {
      if (n.elementType === "node")
        selectedNodes.push(n);
    }), selectedNodes;
  },
  //获取标尺线对象
  getRuleLines: function () {
    var ruleLines = [];
    editor.stage.childs.forEach(function (s) {
      s.childs.forEach(function (n) {
        if (n.elementType === "link" && n.lineType == "rule")
          ruleLines.push(n);
      });
    });
    return ruleLines;
  },
  //删除标尺线
  clearRuleLines: function () {
    for (var i = 0; i < editor.ruleLines.length; i++) {
      editor.scene.remove(editor.ruleLines[i]);
    }
    editor.ruleLines = [];
    return this;
  },
  //重新创建标尺线对象
  reCreateRuleLines: function () {
    if (editor.ruleLines && editor.ruleLines.length == 2) {
      editor.scene.add(editor.ruleLines[0]);
      editor.scene.add(editor.ruleLines[1]);
    }
    return this;
  },
  //显示标尺线
  showRuleLines: function (x, y) {
    if (editor.ruleLines && editor.ruleLines.length == 2) {
      editor.ruleLines[0].visible = true;
      editor.ruleLines[1].visible = true;
      /* editor.ruleLines[0].nodeA.setLocation(0 - editor.scene.translateX, y );
       editor.ruleLines[0].nodeZ.setLocation(JTopo.stage.width - editor.scene.translateX,y);
       editor.ruleLines[1].nodeA.setLocation(x,0 - editor.scene.translateY);
       editor.ruleLines[1].nodeZ.setLocation(x,JTopo.stage.height - editor.scene.translateY);*/
      editor.ruleLines[0].nodeA.y = y;
      editor.ruleLines[0].nodeZ.y = y;
      editor.ruleLines[1].nodeA.x = x;
      editor.ruleLines[1].nodeZ.x = x;
    }
    return this;
  },
  //隐藏标尺线
  hideRuleLines: function () {
    if (editor.ruleLines && editor.ruleLines.length == 2) {
      editor.ruleLines[0].visible = false;
      editor.ruleLines[1].visible = false;
    }
    return this;
  },
  //节点分组合并
  toMerge: function () {
    var selectedNodes = this.getSelectedNodes();
    // 不指定布局的时候，容器的布局为自动(容器边界随元素变化）
    var container = new JTopo.Container();
    container.textPosition = 'Top_Center';
    container.fontColor = editor.config.fontColor;
    container.borderColor = editor.config.borderColor;
    container.borderRadius = editor.config.borderRadius;
    editor.scene.add(container);
    selectedNodes.forEach(function (n) {
      container.add(n);
    });
  },
  //分组拆除
  toSplit: function () {
    if (editor.currentNode instanceof JTopo.Container) {
      editor.currentNode.removeAll();
      editor.scene.remove(editor.currentNode);
    }
  },
  //删除连线
  deleteLine: function () {
    if (editor.currentNode instanceof JTopo.Link) {
      editor.scene.remove(editor.currentNode);
      editor.currentNode = null;
      editor.lineMenu.hide();
    }
  },
  //删除节点
  deleteNode: function (n) {
    editor.scene.remove(n);
    editor.currentNode = null;
    //连线重置
    editor.beginNode = null;
    if (editor.link)
      editor.scene.remove(editor.link);
    editor.link = null;
  },
  //删除选择的节点
  deleteSelectedNodes: function () {
    var self = this;
    var nodes = editor.scene.selectedElements;
    var i=-1;
    //用递归逐一删除被选择模块
    function delConfirm(){
      i++;
     if (i== nodes.length){
       $('#flow_confirm').off('hidden.bs.modal');
       return false;
     }

      if (nodes[i].elementType == "link") {
        self.deleteNode(nodes[i]);
      }else {
        $('#confirm_content').empty();
        $('#confirm_content').append('<h3>'+"确认删除模块：“"+nodes[i].text+"”？"+'</h3>');
        $('#flow_confirm').modal({backdrop: false});
        $('#flow_confirm').on('hidden.bs.modal',function (e) {
          if ($('#flow_confirm').data('confirm')==1) {
            if(nodes[i].text==modalPropsObj.text)
              $('.right-content').fadeOut();
            self.deleteNode(nodes[i]);
            $('#flow_confirm').data('confirm','2');
            delConfirm()
          }
          if ($('#flow_confirm').data('confirm')==0){
            $('#flow_confirm').data('confirm','2');
            delConfirm()
          }
      });
      }

    }
    delConfirm();
  },
  //放大
  scalingBig: function () {
    if (editor.currentNode instanceof JTopo.Node) {
      editor.currentNode.scaleX += editor.config.nodeScale;
      editor.currentNode.scaleY += editor.config.nodeScale;
    } else {
      editor.stage.zoomOut(editor.stage.wheelZoom);
    }
  },
  //缩小
  scalingSmall: function () {
    if (editor.currentNode instanceof JTopo.Node) {
      editor.currentNode.scaleX -= editor.config.nodeScale;
      editor.currentNode.scaleY -= editor.config.nodeScale;
    } else {
      editor.stage.zoomIn(editor.stage.wheelZoom);
    }
  },
  //顺时针旋转
  rotateAdd: function () {
    if (editor.currentNode instanceof JTopo.Node) {
      editor.currentNode.rotate += editor.config.rotateValue;
    }
  },
  //逆时针旋转
  rotateSub: function () {
    if (editor.currentNode instanceof JTopo.Node) {
      editor.currentNode.rotate -= editor.config.rotateValue;
    }
  },
  //清空编辑器
  clear: function () {
    //删除节点表对应的节点记录
    editor.deleteAllNodes(editor.templateId);
  },
  //拓扑图预览
  showPic: function () {
    if (editor.ruleLines && editor.ruleLines.length > 0) {
      this.clearRuleLines();
    }
    editor.stage.saveImageInfo();
  },
  //复制节点
  cloneNode: function (n) {
    if (n instanceof JTopo.Node) {
      var newNode = new JTopo.Node();
      n.serializedProperties.forEach(function (i) {
        //只复制虚拟机的模板属性
        if (i == "templateId" && n.dataType != "VM") return true;
        newNode[i] = n[i];
      });
      newNode.id = "";
      newNode.alpha = editor.config.alpha;
      newNode.strokeColor = editor.config.nodeStrokeColor;
      newNode.fillColor = editor.config.fillColor;
      newNode.shadow = editor.config.shadow;
      newNode.shadowColor = editor.config.shadowColor;
      newNode.font = editor.config.font;
      newNode.fontColor = editor.config.nodeFontColor;
      newNode.borderRadius = null;
      newNode.shadowOffsetX = editor.config.shadowOffsetX;
      newNode.shadowOffsetY = editor.config.shadowOffsetY;
      newNode.layout = n.layout;
      newNode.selected = false;
      newNode.deviceId = "" + new Date().getTime() * Math.random();
      newNode.setLocation(n.x + n.width, n.y + n.height);
      newNode.text = n.text;
      newNode.setImage(n.image);
      attribute[newNode.deviceId] = attribute[n.deviceId];
      editor.scene.add(newNode);
    }
  },
  //复制选择的节点
  cloneSelectedNodes: function () {
    var self = this;
    var nodes = editor.scene.selectedElements;
    nodes.forEach(function (n) {
      if (n.elementType === "node")
        self.cloneNode(n);
    })
  },
  //全屏显示
  showInFullScreen: function (element, method) {
    var usablePrefixMethod;
    ["webkit", "moz", "ms", "o", ""].forEach(function (prefix) {
        if (usablePrefixMethod) return;
        if (prefix === "") {
          // 无前缀，方法首字母小写
          method = method.slice(0, 1).toLowerCase() + method.slice(1);
        }
        var typePrefixMethod = typeof element[prefix + method];
        if (typePrefixMethod + "" !== "undefined") {
          if (typePrefixMethod === "function") {
            usablePrefixMethod = element[prefix + method]();
          } else {
            usablePrefixMethod = element[prefix + method];
          }
        }
      }
    );
    return usablePrefixMethod;
  },
  //切换连线
  switchLining:function(e){
    e=e||window.event;
    if ($('#drawCanvas').data('line')){
      $('#drawCanvas').data('line',false);
      $(e.target).text("开始连线");
    }else {
      $('#drawCanvas').data('line',true);
      $(e.target).text("停止连线");
    }

  },
  //居中显示
  showInCenter: function () {
    editor.stage.centerAndZoom();
  },
  //添加节点描述文字
  addNodeText: function (x, y) {
    var a = editor.currentNode.nodeA, z = editor.currentNode.nodeZ;
    editor.deviceEditText.css({
      top: y,
      left: x,
      display: "block"
    });
    editor.deviceEditText.attr('value', editor.currentNode.text);
    editor.deviceEditText.focus();
    editor.deviceEditText.select();
    editor.currentNode.text = "";
  },
  //创建标尺线
  createRuleLines: function (x, y) {
    if (editor.showRuleLine) {
      //新建两条定点连线
      if (editor.ruleLines.length == 0) {
        var nodeHA = new JTopo.Node(), nodeHZ = new JTopo.Node();
        /* nodeHA.setLocation(0 - editor.scene.translateX, y );
         nodeHZ.setLocation(JTopo.stage.width - editor.scene.translateX,y);*/
        nodeHA.setLocation(JTopo.stage.width * -2, y);
        nodeHZ.setLocation(JTopo.stage.width * 2, y);
        nodeHA.setSize(1, 1);
        nodeHZ.setSize(1, 1);
        var nodeVA = new JTopo.Node(), nodeVZ = new JTopo.Node();
        /*  nodeVA.setLocation(x,0 - editor.scene.translateY);
         nodeVZ.setLocation(x,JTopo.stage.height - editor.scene.translateY); */
        nodeVA.setLocation(x, JTopo.stage.height * -2);
        nodeVZ.setLocation(x, JTopo.stage.width * 2);
        nodeVA.setSize(1, 1);
        nodeVZ.setSize(1, 1);
        var linkH = new JTopo.Link(nodeHA, nodeHZ);
        var linkV = new JTopo.Link(nodeVA, nodeVZ);
        linkH.lineType = "rule";
        linkV.lineType = "rule";
        linkH.lineWidth = 1; // 线宽
        linkH.dashedPattern = 2; // 虚线
        linkV.lineWidth = 1; // 线宽
        linkV.dashedPattern = 2; // 虚线
        linkH.strokeColor = "255,0,0";
        linkV.strokeColor = "0,255,0";
        //保存标尺线
        editor.ruleLines.push(linkH);
        editor.ruleLines.push(linkV);
        editor.scene.add(linkH);
        editor.scene.add(linkV);
      } else {
        editor.utils.showRuleLines(x, y);
      }
    }
  },
  //获取所有的容器对象
  getContainers: function () {
    var containers = [];
    editor.stage.childs.forEach(function (s) {
      s.childs.forEach(function (n) {
        if (n.elementType === "container")
          containers.push(n);
      });
    });
    return containers;
  },
  //根据指定的key返回节点实例
  getNodeByKey: function (key, value) {
    var node = null;
    editor.stage.childs.forEach(function (s) {
      s.childs.forEach(function (n) {
        if (n.elementType === "node" && n[key] == value) {
          node = n;
          return node;
        }
      });
    });
    return node;
  },
  //撤销对节点的操作
  cancleNodeAction: function () {
    if (editor.currentNode.currStep <= 0)
      return;
    --editor.currentNode.currStep;
    for (var p in editor.currentNode) {
      if (p != "currStep")
        editor.currentNode[p] = (editor.currentNode.historyStack[editor.currentNode.currStep])[p];
    }
  },
  //重做节点操作
  reMakeNodeAction: function () {
    if (editor.currentNode.currStep >= editor.currentNode.maxHistoryStep ||
      editor.currentNode.currStep >= editor.currentNode.historyStack.length - 1)
      return;
    editor.currentNode.currStep++;
    for (var q in editor.currentNode) {
      if (q != "currStep")
        editor.currentNode[q] = (editor.currentNode.historyStack[editor.currentNode.currStep])[q];
    }
  },
  //保存节点新的状态
  saveNodeNewState: function () {
    //如果历史栈超过最大可记录历史长度，丢弃第一个元素
    if (editor.currentNode.historyStack.length >= editor.currentNode.maxHistoryStep + 1) {
      editor.currentNode.historyStack.shift();
    }
    editor.currentNode.historyStack.push(JTopo.util.clone(editor.currentNode));
    editor.currentNode.currStep = editor.currentNode.historyStack.length - 1;
  },
  //保存节点初始状态,便于回退
  saveNodeInitState: function () {
    if (!editor.currentNode.hasInitStateSaved) {
      editor.currentNode.historyStack.push(JTopo.util.clone(editor.currentNode));
      editor.currentNode.hasInitStateSaved = true;
    }
  }
};
