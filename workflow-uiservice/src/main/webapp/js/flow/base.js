var str = localStorage.data;
alert(str)

function JsonPropetity() {
    var flag;//1-字符串，2-对象，3-数组
    var name;//属性名
    var str;//放字符串
    var obj;//放对象
    var arr;//放数组
    //添加字符串类型
    this.addStr = function (nameArg, strArg) {
        flag = 1;
        name = nameArg;
        str = strArg;
    }
    //添加对象类型
    this.addObj = function (nameArg, objArg) {
        flag = 2;
        name = nameArg;
        obj = objArg;
    }
    //添加数组类型
    this.addArr = function (nameArg, arrArg) {
        flag = 3;
        name = nameArg;
        arr = arrArg;
    }
    //拿到属性名
    this.getName = function () {
        return name;
    }
    //拿到标志位
    this.getFlag = function () {
        return flag;
    }
    //拿到值
    this.getValue = function () {
        if (flag == 1) {
            return str;
        } else if (flag == 2) {
            return obj;
        } else {
            return arr;
        }
    }
}
/*
 *对象
 */
function JsonObject() {
    var arr = new Array();
    //添加一个键值对
    this.addPro = function (jp) {
        arr.push(jp);
    }
    //返回键值对数组
    this.getArr = function () {
        return arr;
    }
}
/*
 *数组
 */
function JsonArray() {
    var arr = new Array();
    //添加一个对象
    this.addObj = function (jo) {
        arr.push(jo);
    }
    //返回对象数组
    this.getArr = function () {
        return arr;
    }
}
/*
 *转换工具
 */
function ChangeTool() {
    //去头尾的空格
    var removeSpace = function (str) {
        var begin = 0;
        var end = str.length;

        while (str.charAt(begin) == ' ') {
            begin++;
        }
        while (str.charAt(end - 1) == ' ') {
            end--;
        }

        return str.substring(begin, end);
    }
    //拿到逗号分隔的字符串数组
    var splitString = function (str) {
        var arr = new Array();

        //1 遍历
        var begin = 0;//开始下标
        var doubleFlag = 1;//前面的双引号成对
        var bigFlag = 0;//前面的大括号成对
        var middleFlag = 0;//前面的中括号成对
        var i = 0;
        while (i < str.length) {
            var c = str.charAt(i);
            //2 转义字符，跳到后面第二位循环
            if (c == '\\') {
                i += 2;
                continue;
                //3 普通字符
            } else {
                //3.1 双引号
                if (c == '"') {
                    doubleFlag = 0 - doubleFlag;
                    //3.2 大括号开始
                } else if (c == '{') {
                    bigFlag--;
                    //3.3 大括号结束
                } else if (c == '}') {
                    bigFlag++;
                    //3.4 中括号开始
                } else if (c == '[') {
                    middleFlag--;
                    //3.5 中括号结束
                } else if (c == ']') {
                    middleFlag++;
                    //3.6 逗号
                } else if (c == ',') {
                    //最外层的逗号
                    if (doubleFlag == 1 && bigFlag == 0 && middleFlag == 0) {
                        arr.push(str.substring(begin, i));
                        begin = i + 1;
                    }
                }
                i++;
            }
        }
        if (str.length > 2) {
            arr.push(str.substring(begin));
        }

        return arr;
    }
    //字符串到键值对
    this.str2jp = function (str) {
        //1 找到第一个冒号的下标
        var n = str.indexOf(":");
        //2 key、value去头尾空格
        var key = removeSpace(str.substring(0, n));
        var value = removeSpace(str.substring(n + 1));
        //3 判断value的类型,并生成相应的键值对
        var firstChar = value.charAt(0);
        //3.1 数组
        if (firstChar == '[') {
            var ja = this.str2arr(value);
            var jp = new JsonPropetity();
            jp.addArr(key, ja);
            return jp;
            //3.2 对象
        } else if (firstChar == '{') {
            var jo = this.str2jo(value);
            var jp = new JsonPropetity();
            jp.addObj(key, jo);
            return jp;
            //3.3 字符串
        } else {
            var jp = new JsonPropetity();
            jp.addStr(key, value);
            return jp;
        }
    }
    //字符串到对象
    this.str2jo = function (str) {
        var jo = new JsonObject();

        //1 去头尾空格
        str = removeSpace(str);
        //2 去头尾的大括号
        str = str.substring(1, str.length - 1);
        //3 拿到逗号分隔的字符串数组
        var arr = splitString(str);
        //4 遍历字符串数组，生成一个个键值对并添加进对象里去
        for (var i = 0; i < arr.length; i++) {
            var jp = this.str2jp(arr[i]);
            jo.addPro(jp);
        }

        return jo;
    }
    //字符串到数组
    this.str2arr = function (str) {
        var ja = new JsonArray();

        //1 去头尾的中括号
        str = str.substring(1, str.length - 1);
        //2 拿到逗号分隔的字符串数组
        var arr = splitString(str);
        //3 遍历字符串数组，生成一个个对象并添加到数组里去
        for (var i = 0; i < arr.length; i++) {
            var jo = this.str2jo(arr[i]);
            ja.addObj(jo);
        }

        return ja;
    }
    //打印键值对
    this.showPro = function (fatherId, num, jp) {
        var id = fatherId + "_" + num;
        //1 打印n个间隔
        var n = id.split("_").length - 1;
        while (n-- > 0) {
            document.write("<img src='images//line.gif'>");
        }
        var flag = jp.getFlag();
        var name = jp.getName();
        var value = jp.getValue();
        //2 打印字符串类型
        if (flag == 1) {
            document.write("<img src='images//joinbottom.gif'>" + name + " : " + value + "<br><br>");
            //3 打印对象类型
        } else if (flag == 2) {
            document.write("<img src='images//plus.gif' id='img_" + id + "' onclick=changeStyle('" + id + "')>" + name + "<br><br>");
            this.showObj(id, 1, value);
            //4 打印数组类型
        } else {
            document.write("<img src='images//plus.gif' id='img_" + id + "' onclick=changeStyle('" + id + "')>" + name + "<br><br>");
            this.showArr(id, 1, value);
        }
    }
    //打印对象
    this.showObj = function (fatherId, num, jo) {
        //1 根
        if (fatherId == '1') {
            document.write("<img src='images//plus.gif' id='img_" + fatherId + "' onclick=changeStyle('" + fatherId + "')>结果<br><br>");
        }
        document.write("<div id='div_" + fatherId + "' style='display:none'>");
        //2 遍历属性列表，打印各个键值对
        var arr = jo.getArr();
        for (var i = 0; i < arr.length; i++) {
            var jp = arr[i];
            this.showPro(fatherId, (i + 1), jp);
        }
        document.write("</div>");
    }
    //打印数组
    this.showArr = function (fatherId, num, ja) {
        var n = fatherId.split("_").length;
        document.write("<div id='div_" + fatherId + "' style='display:none'>");
        //1 遍历对象数组，打印各个对象
        var arr = ja.getArr();
        if (arr.length == 0) {
            //2 打印n个空格
            var m = n;
            while (m-- > 0) {
                document.write("<img src='images//line.gif'>");
            }
            document.write("<img src='images//joinbottom.gif'>[]<br><br>");
        } else {
            for (var i = 0; i < arr.length; i++) {
                //2 打印n个空格
                var m = n;
                while (m-- > 0) {
                    document.write("<img src='images//line.gif'>");
                }
                var id = fatherId + "_" + (i + 1);
                document.write("<img src='images//plus.gif' id='img_" + id + "' onclick=changeStyle('" + id + "')>[ " + (i) + "]" + "<br><br>");
                var jo = arr[i];
                this.showObj(id, (i + 1), jo);
            }
        }
        document.write("</div>");
    }
    //对外总方法
    this.toChange = function (str) {
        var jo = this.str2jo(str);
        this.showObj("1", 1, jo);
    }
}

//修改样式
function changeStyle(id) {
    var openUrl = "minus.gif";
    var closeUrl = "plus.gif";
    var imgId = "img_" + id;
    var img = document.getElementById(imgId);
    var imgUrl = img.src;
    var sonDivId = "div_" + id;
    var sonDiv = document.getElementById(sonDivId);
    //打开的
    if (imgUrl.indexOf(openUrl) != -1) {
        img.src = imgUrl.replace(openUrl, closeUrl);
        sonDiv.style.display = "none";
        //关闭的
    } else {
        img.src = imgUrl.replace(closeUrl, openUrl);
        sonDiv.style.display = "block";
    }
}
//执行js命令
var ct = new ChangeTool();
ct.toChange(str);