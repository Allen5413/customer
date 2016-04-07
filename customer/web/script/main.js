/*用来存储每个url对应的页面html代码，用去切换tab的时候保存之前请求的结果*/
var pageHtmlJSON = {};

function clickMenu(index){
    $("dt").each(function(){
        if($(this).attr("lang") == index){
            if($(this).find("a").attr("class") == "tOn"){
                $(this).find("a").attr("class", "tOff");
            }else{
                $(this).find("a").attr("class", "tOn");
            }
        }
    });
    $("dd").each(function(){
        if($(this).attr("lang") == index){
            if($(this).attr("class") == "" || typeof($(this).attr("class")) == "undefined"){
                $(this).attr("class", "display_none");
            }else{
                $(this).attr("class", "");
            }
        }
    });
}

function clickResource(url, obj, name){
    var oldOpenTab;
    $("li").each(function(){
        $(this).attr("class", "");
    })
    $(obj).attr("class", "on");

    //添加tab
    var isOpen =false;
    $(".mainTab").find("a").each(function () {
        if ($(this).attr("class") == "tab_1 on") {
            $(this).attr("class", "tab_1");
            oldOpenTab = $(this);
        }
    });
    $(".mainTab").find("a").each(function () {
        if ($(this).attr("lang") == url) {
            $(this).attr("class", "tab_1 on");
            isOpen = true;
        }
    });
    openPage(url+"?time="+new Date().getTime());

    if(!isOpen){
        var num = 0;
        $(".mainTab").find("a").each(function () {
            if($(this).attr("class").indexOf("tab_1") > -1){
                num++;
            }
        });
        if(num >= 10){
            oldOpenTab.attr("class", "tab_1 on");
            alert("最多只能打开10个窗口，请关闭其他窗口再打开！");
        }else {
            var mainTabHtml = $(".mainTab").html();
            var addHtml = "<a lang='" + url + "' class='tab_1 on' href='#' onclick='clickTab(this)'>" + name + "</a>";
            $(".mainTab").html(mainTabHtml + addHtml);
        }
    }

}

function closeTab() {
    var obj;
    var isClose = false;
    $(".mainTab").find("a").each(function () {
        if($(this).attr("class") == "tab_1 on"){
            if($(this).html() != "首页") {
                if (typeof($(this).prev().attr("lang")) != "undefined") {
                    obj = $(this).prev();
                } else {
                    obj = $(this).next();
                }
                $(this).remove();
                isClose = true;
            }
        }
    });
    if(isClose) {
        obj.attr("class", "tab_1 on");
        openPage(obj.attr("lang")+"?time="+new Date().getTime());
    }else{
        alert("首页不能关闭！");
    }
}

function clickTab(obj){
    $(".mainTab").find("a").each(function () {
        if($(this).attr("class") == "tab_1 on"){
            $(this).attr("class", "tab_1");
        }
    });
    $(obj).attr("class", "tab_1 on");
    openPage($(obj).attr("lang")+"?time="+new Date().getTime());
}

function prevTab(thisObj){
    $(".mainTab").find("a").each(function () {
        if($(this).attr("class") == "tab_1 on"){
            if (typeof($(this).prev().attr("lang")) != "undefined") {
                $(this).attr("class", "tab_1");
                $(this).prev().attr("class", "tab_1 on");
                openPage($(this).prev().attr("lang")+"?time="+new Date().getTime());
            }
        }
    });
}

function nextTab(){
    var obj;
    $(".mainTab").find("a").each(function () {
        if($(this).attr("class") == "tab_1 on") {
            if (typeof($(this).next().attr("lang")) != "undefined") {
                $(this).attr("class", "tab_1");
                obj = $(this);
            }
        }
    });
    if (typeof(obj) != "undefined") {
        obj.next().attr("class", "tab_1 on");
        openPage(obj.next().attr("lang")+"?time="+new Date().getTime());
    }
}

/**
 * 打开一个tab
 * @param url
 * @param index
 */
function openPage(url){
    var falg = url.substring(0, url.indexOf("?")).replace("/", "").replace("/", "").replace(".htm", "");
    var html = pageHtmlJSON[falg];
    /**
     * 如果该url有之前保留的html代码，就加载之前存的，否则重新请求
     */
    if(typeof (html) == "undefined"){
        $.ajax({
            cache: true,
            type: "POST",
            url: url,
            async: false,
            success: function (data) {
                $("#contentPage").html(data);
                pageHtmlJSON[falg] = data
            }
        });
    }else{
        $("#contentPage").html(html);
    }
}

/**
 * 点击查询按钮
 * @param obj
 * @param url
 * @param flag
 */
function searchFormPage(obj, url, flag){
    $("#rows").val($("#rows_txt").val());
    //这里如果是操作后的重新请求列表页面，就要先关掉窗口
    if (1 == flag) {
        closeDialog();
    }
    if(url != "") {
        var falg = url.replace("/", "").replace("/", "").replace(".htm", "");
        $.ajax({
            cache: true,
            type: "POST",
            url: url,
            async: false,
            data: obj.serialize(),
            success: function (data) {
                $("#contentPage").html(data);
                pageHtmlJSON[falg] = data
            }
        });
    }
}

/**
 * 得到浏览器的高和宽
 * @returns {{x: number, y: number}}
 */
function getWindowSize() {
    var client = {
        x:0,
        y:0
    };

    if(typeof document.compatMode != 'undefined' && document.compatMode == 'CSS1Compat') {
        client.x = document.documentElement.clientWidth;
        client.y = document.documentElement.clientHeight;
    } else if(typeof document.body != 'undefined' && (document.body.scrollLeft || document.body.scrollTop)) {
        client.x = document.body.clientWidth;
        client.y = document.body.clientHeight;
    }
    return client;
}

/**
 * 得到浏览器的宽
 * @returns {{x: number, y: number}}
 */
function getWindowWidthSize() {
    return getWindowSize().x;
}

/**
 * 得到浏览器的高
 * @returns {{x: number, y: number}}
 */
function getWindowHeightSize() {
    return getWindowSize().y;
}


function formatNum(str){
    var newStr = "";
    var count = 0;

    if(str.indexOf(".")==-1){
        for(var i=str.length-1;i>=0;i--){
            if(count % 3 == 0 && count != 0){
                newStr = str.charAt(i) + "," + newStr;
            }else{
                newStr = str.charAt(i) + newStr;
            }
            count++;
        }
        str = newStr + ".00"; //自动补小数点后两位
        console.log(str)
    }
    else
    {
        for(var i = str.indexOf(".")-1;i>=0;i--){
            if(count % 3 == 0 && count != 0){
                newStr = str.charAt(i) + "," + newStr;
            }else{
                newStr = str.charAt(i) + newStr; //逐个字符相接起来
            }
            count++;
        }
        str = newStr + (str + "00").substr((str + "00").indexOf("."),3);
        console.log(str)
    }
}

function setIframeHeight(obj, type) {
    var win=obj;
    if (document.getElementById) {
        if (win && !window.opera) {
            //根据浏览器高度来设置
            if(type == 0){
                win.height = getWindowSize().y-200;
            }
            //根据iframe页面内容的高度来设置
            if(type == 1) {
                if (win.contentDocument && win.contentDocument.body.offsetHeight) {
                    win.height = win.contentDocument.body.offsetHeight + 50;
                }
                else if (win.Document && win.Document.body.scrollHeight) {
                    win.height = win.Document.body.scrollHeight + 50;
                }
            }
        }
    }
}

//全选
function checkAll(boxName, hiddenId){
    var cheArr = document.getElementsByName(boxName);
    var checkValues="";
    for(var i = 0;i < cheArr.length;i ++){
        cheArr[i].checked = true;
        //cheArr[i].parentNode.parentNode.style.backgroundColor = "#aaaaaa";
        checkValues += $(cheArr[i]).val()+",";
    }
    if(typeof(hiddenId) != "undefined") {
        $("#" + hiddenId).val(checkValues.substring(0, checkValues.length - 1));
    }
}
//反选
function checkNall(boxName, hiddenId){
    var cheArr = document.getElementsByName(boxName);
    var checkValues="";
    for(var i = 0;i < cheArr.length;i ++){
        if( cheArr[i].checked){
            cheArr[i].checked = false;
            //cheArr[i].parentNode.parentNode.style.backgroundColor = "";
        }else{
            cheArr[i].checked = true;
            //cheArr[i].parentNode.parentNode.style.backgroundColor = "#aaaaaa";
            checkValues += $(cheArr[i]).val()+",";
        }
    }
    if(typeof(hiddenId) != "undefined") {
        $("#" + hiddenId).val(checkValues.substring(0, checkValues.length - 1));
    }
}
//选中tr高亮
function changeColor(checkOb){
    if(checkOb.checked){
        //checkOb.parentNode.parentNode.style.backgroundColor = "#aaaaaa";
    }else{
        //checkOb.parentNode.parentNode.style.backgroundColor = "";
    }
}
function selectTR(textId){
    var checkValues = "";
    $("[name=cb]").each(function(){
        if($(this).prop("checked")){
            checkValues += $(this).val()+",";
            //$(this).parent().parent().css("backgroundColor", "#aaaaaa");
        }else{
            //$(this).parent().parent().css("backgroundColor", "");
        }
    });
    if(typeof(textId) != "undefined") {
        $("#" + textId).val(checkValues.substring(0, checkValues.length - 1));
    }
}
function isNumber(oNum) {
    if(!oNum){
        return false;
    }
    var strP=/^\d+(\.\d+)?$/;
    if(!strP.test(oNum)){
        return false;
    }
    try{
        if(parseFloat(oNum)!=oNum){
            return false;
        }
    }
    catch(ex)
    {
        return false;
    }
    return true;
}

function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 点击tr行，选中checkbox，加背景色
 * @param obj
 */
function changeTR(obj){
    if(RGBtoHEX($(obj).css("background-color")) == "#000000" || RGBtoHEX($(obj).css("background-color")) == "#FFFFFF" || RGBtoHEX($(obj).css("background-color")) == "transparent"){
        $(obj).css("background-color", "#A9B8BC");
        $(obj).find("input[type=checkbox]").first().prop("checked", true);

    }else{
        $(obj).css("background-color", "#FFFFFF");
        $(obj).find("input[type=checkbox]").first().prop("checked", false);
    }

}


//Helper function to convert a digit to a two column Hex representation
function toHex(N) {
    if (N==null) return "00";
    N=parseInt(N); if (N==0 || isNaN(N)) return "00";
    N=Math.max(0,N); N=Math.min(N,255); N=Math.round(N);
    return "0123456789ABCDEF".charAt((N-N%16)/16) + "0123456789ABCDEF".charAt(N%16);
}

//Function to convert rgb() format values into normal hex values
function RGBtoHEX(str)
{
    if (str.substring(0, 3) == 'rgb') {
        var arr = str.split(",");
        var r = arr[0].replace('rgb(','').trim(), g = arr[1].trim(), b = arr[2].replace(')','').trim();
        var hex = [
            toHex(r),
            toHex(g),
            toHex(b)
        ];
        return "#" + hex.join('');
    }
    else{
        return str;
    }
}
