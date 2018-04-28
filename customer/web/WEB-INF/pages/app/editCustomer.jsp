<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <title>客户拜访</title>
  <%@ include file="common/taglibsForApp.jsp"%>
  <style>section{padding-top:44px;}</style>
</head>
<body>
<div id="divAdd">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">编辑客户</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(0)"></a>
    </div>
  </header>
  <section>
    <form id="editForm" name="editForm" action="${pageContext.request.contextPath}/editCustomerForApp/editor.htm" method="get">
      <input type="hidden" name="id" value="${customer.id}" />
      <input type="hidden" name="version" value="${customer.version}" />
      <input type="hidden" id="linkmanInfo" name="linkmanInfo" />
      <input type="hidden" id="delLinkman" name="delLinkman" />
      <input type="hidden" id="ip_address" name="ip_address" />
      <input type="hidden" id="random" name="random" />
      <div class="auto w bg-f">
        <div class="adm-select-list">
          <div class="title-bgr">基本资料</div>
          <ul>
            <li>
              <div class="content">
                <div class="col-tg">学校No：</div>
                <div class="col-txt"><input type="text" class="input-txt" name="no" placeholder="请输入学校No" value="${customer.no}"></div>
              </div>
            </li>
            <li>
              <div class="content">
                <div class="col-tg">学校名称：</div>
                <div class="col-txt"><input type="text" class="input-txt" id="name" name="name" placeholder="请输入学校名称（必填）" value="${customer.name}"></div>
              </div>
            </li>
            <li>
              <div class="content">
                <div class="col-tg">客户代码：</div>
                <div class="col-txt"><input type="text" class="input-txt" name="code" placeholder="请输入客户代码" value="${customer.code}"></div>
              </div>
            </li>
            <li>
              <div class="content">
                <div class="col-tg">办学规模：</div>
                <div class="col-txt"><input type="text" class="input-txt" id="scale" name="scale" placeholder="请输入办学规模（必填）" value="${customer.scale}"></div>
              </div>
            </li>
            <li>
              <div class="content">
                <input type="hidden" id="provinceCode" name="provinceCode" value="${customer.provinceCode}">
                <div class="col-tg">所在省份：</div>
                <div class="col-txt"><a href="javascript:;" id="areaText" onclick="selectIf(0)">${area.name} <i class="i-arr f-r"></i></a></div>
              </div>
            </li>
            <li>
              <div class="content">
                <div class="col-tg">详细地址：</div>
                <div class="col-txt"><textarea class="input-addr" id="address" name="address" placeholder="请输入详细地址（必填）">${customer.address}</textarea></div>
              </div>
            </li>
            <li>
              <div class="content">
                <input type="hidden" id="customerTypeId" name="customerTypeId" value="${customer.customerTypeId}">
                <div class="col-tg">客户类型：</div>
                <div class="col-txt"><a href="javascript:;" id="typeText" onclick="selectIf(1)">${customerType.name} <i class="i-arr f-r"></i></a></div>
              </div>
            </li>
            <li>
              <div class="content">
                <input type="hidden" id="customerStateId" name="customerStateId" value="${customer.customerStateId}">
                <div class="col-tg">客户状态：</div>
                <div class="col-txt"><a href="javascript:;" id="stateText" onclick="selectIf(2)">${customerState.name} <i class="i-arr f-r"></i></a></div>
              </div>
            </li>
          </ul>
          <div class="title-bgr">联系人信息</div>
          <div id="linkmanContent">
            <c:set var="temp" value="${fn:length(linkmanList)}" />
            <c:forEach var="linkman" items="${linkmanList}" varStatus="status">
              <ul class="adm-visit-list">
                <li>
                  <div class="title">
                    <input type="hidden" name="linkmanId" value="${linkman.id}" />
                    <a id='linkmanName_${status.index}' href="javascript:;" name='linkmanName'>${linkman.name}</a>
                  </div>
                  <div class="info-list">
                    <p>职务：<span id='linkmanPost_${status.index}' name='linkmanPost'>${linkman.post}</span></p>
                    <p>手机：<span id='linkmanPhone_${status.index}' name='linkmanPhone'>${linkman.phone}</span></p>
                    <p>QQ：<span id='linkmanQQ_${status.index}' name='linkmanQQ'>${linkman.qq}</span></p>
                    <p>特点：<span id='linkmanTrait_${status.index}' name='linkmanTrait'>${linkman.trait}</span></p>
                  </div>
                  <div class="opr-tl">
                    <a class="btn-edit" href="javascript:;" onclick="editLinkman(${status.index})">编辑</a> &nbsp;
                    <a class="btn-delete" href="javascript:;" onclick='delLinkman(this)'>删除</a>
                  </div>
                </li>
              </ul>
            </c:forEach>
          </div>
          <div class="btn-bar">
            <a class="upload-btn" href="javascript:;" onclick="selectIf(3)">添加联系人</a>
          </div>
        </div>
      </div>
      <div class="ft-fixed-opr">
        <a class="btn-opr-com" href="javascript:;" onclick="sub();">保存</a>
      </div>
    </form>
  </section>
</div>

<div id="divArea" style="display: none;">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择区域</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <c:if test="${!empty provinceList}">
            <c:forEach var="area" items="${provinceList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectArea('${area.code}', '${area.name}')">${area.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<div id="divType" style="display: none;">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择客户类型</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <c:if test="${!empty typeList}">
            <c:forEach var="type" items="${typeList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectType(${type.id}, '${type.name}')">${type.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<div id="divState" style="display: none;">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择客户状态</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <c:if test="${!empty stateList}">
            <c:forEach var="state" items="${stateList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectState(${state.id}, '${state.name}')" >${state.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<div id="divLinkMan" style="display: none;">
  <input type="hidden" id="linkmanIndex" />
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">新增/编辑联系人</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul>
          <li>
            <div class="content">
              <div class="col-tg">姓名：</div>
              <div class="col-txt"><input type="text" class="input-txt" id="add_linkmanName" placeholder="请输入姓名（必填）"></div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">职务：</div>
              <div class="col-txt"><input type="text" class="input-txt" id="add_linkmanPost" placeholder="请输入职务（必填）"></div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">手机：</div>
              <div class="col-txt"><input type="text" class="input-txt" id="add_linkmanPhone" placeholder="请输入手机（必填）"></div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">QQ：</div>
              <div class="col-txt"><input type="text" class="input-txt" id="add_linkmanQQ" placeholder="请输入QQ（非必填）"></div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">特点：</div>
              <div class="col-txt"><input type="text" class="input-txt" id="add_linkmanTrait" placeholder="请输入特点（非必填）"></div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="ft-fixed-opr">
      <a class="btn-opr-com" href="javascript:;" onclick="addLinkman()">确定</a>
    </div>
  </section>
</div>
</body>
</html>
<script>

  callMap();
  function callMapReturn(x,y,address){
    $("#ip_address").val(address);
  }

  function sub(){
    var linkmanInfo = $("#linkmanInfo");
    var name = $("#name").val();
    var scale = $("#scale").val();
    var provinceCode = $("#provinceCode").val();
    var customerTypeId = $("#customerTypeId").val();
    var customerStateId = $("#customerStateId").val();
    var address = $("#address").val();
    if(name == ""){
      alert("请输入客户名称");
      linkmanInfo.val("");
      return false;
    }
    if(scale == ""){
      alert("请输入办学规模");
      linkmanInfo.val("");
      return false;
    }
    if(provinceCode == ""){
      alert("请选择所在省份");
      linkmanInfo.val("");
      return false;
    }
    if(address == ""){
      alert("请输入详细地址");
      linkmanInfo.val("");
      return false;
    }
    if(customerTypeId == ""){
      alert("请选择客户类型");
      linkmanInfo.val("");
      return false;
    }
    if(customerStateId == ""){
      alert("请选择客户状态");
      linkmanInfo.val("");
      return false;
    }

    $("#linkmanContent").find("li").each(function(){
      var temp = "";
      $(this).find("[name=linkmanId]").each(function(){
        temp = $(this).val()+"*";
      });
      $(this).find("[name=linkmanName]").each(function(){
        temp += $(this).html()+"*";
      });
      $(this).find("[name=linkmanPhone]").each(function(){
        temp += $(this).html()+"*";
      });
      $(this).find("[name=linkmanPost]").each(function(){
        temp += $(this).html()+"*";
      });
      //$(this).find("[name=linkmanRemark]").each(function(){
        temp += "*";
      //});
      $(this).find("[name=linkmanQQ]").each(function(){
        temp += $(this).html()+"*";
      });
      $(this).find("[name=linkmanTrait]").each(function(){
        temp += $(this).html();
      });
      linkmanInfo.val(linkmanInfo.val()+temp+"|");
    });
    $("#random").val(Math.round(Math.random()*100000));
    linkmanInfo.val(encodeURI(linkmanInfo.val()));
    $.ajax({
      cache: true,
      type: "get",
      url:"${pageContext.request.contextPath}/editCustomerForApp/editor.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          location.href="${pageContext.request.contextPath}/findCustomerByIdForApp/open.htm?id=${customer.id}";
        }else{
          alert(data.msg);
        }
      }
    });
  }

  function selectIf(flag){
    if(flag == 0){
      $("#divArea").show();
      $("#divAdd").hide();
      $("#divType").hide();
      $("#divState").hide();
      $("#divLinkMan").hide();
    }
    if(flag == 1){
      $("#divType").show();
      $("#divAdd").hide();
      $("#divArea").hide();
      $("#divState").hide();
      $("#divLinkMan").hide();
    }
    if(flag == 2){
      $("#divState").show();
      $("#divAdd").hide();
      $("#divType").hide();
      $("#divArea").hide();
      $("#divLinkMan").hide();
    }
    if(flag == 3){
      $("#divLinkMan").show();
      $("#divAdd").hide();
      $("#divType").hide();
      $("#divArea").hide();
      $("#divState").hide();

      $("#add_linkmanName").val("");
      $("#add_linkmanPost").val("");
      $("#add_linkmanPhone").val("");
      $("#add_linkmanQQ").val("");
      $("#add_linkmanTrait").val("");
      $("#linkmanIndex").val(-1);
    }
  }

  function returnIf(flag){
    if(flag == 0){
      location.href="${pageContext.request.contextPath}/findCustomerByWhereForApp/find.htm";
    }else{
      $("#divAdd").show();
      $("#divState").hide();
      $("#divType").hide();
      $("#divArea").hide();
      $("#divLinkMan").hide();
    }
  }

  function selectArea(code, name){
    $("#areaText").html(name);
    $("#provinceCode").val(code);
    returnIf(1);
  }
  function selectType(id, name){
    $("#typeText").html(name);
    $("#customerTypeId").val(id);
    returnIf(1);
  }
  function selectState(id, name){
    $("#stateText").html(name);
    $("#customerStateId").val(id);
    returnIf(1);
  }

  var temp = "${temp}";
  function addLinkman(){
    var linkmanName = $("#add_linkmanName").val().trim();
    var linkmanPost = $("#add_linkmanPost").val().trim();
    var linkmanPhone = $("#add_linkmanPhone").val().trim();
    var linkmanQQ = $("#add_linkmanQQ").val().trim();
    var linkmanTrait = $("#add_linkmanTrait").val().trim();
    var linkmanIndex = $("#linkmanIndex").val();
    if(linkmanName == "") {
      alert("请输入联系人名称");
      return false;
    }
    if(linkmanPost == "") {
      alert("请输入联系人职务");
      return false;
    }
    if(linkmanPhone == "") {
      alert("请输入联系人手机");
      return false;
    }
    if(linkmanIndex == -1) {
      //新增一个联系人
      var html = $("#linkmanContent").html();
      html += "<ul class='adm-visit-list'>";
      html += "<li>";
      html += "<div class='title'>";
      html += "<input type='hidden' name='linkmanId' value='new' />";
      html += "<a id='linkmanName_" + temp + "' href='javascript:;' name='linkmanName'>" + linkmanName + "</a>";
      html += "</div>";
      html += "<div class='info-list'>";
      html += "<p>职务：<span id='linkmanPost_" + temp + "' name='linkmanPost'>" + linkmanPost + "</span></p>";
      html += "<p>手机：<span id='linkmanPhone_" + temp + "' name='linkmanPhone'>" + linkmanPhone + "</span></p>";
      html += "<p>QQ：<span id='linkmanQQ_" + temp + "' name='linkmanQQ'>" + linkmanQQ + "</span></p>";
      html += "<p>特点：<span id='linkmanTrait_" + temp + "' name='linkmanTrait'>" + linkmanTrait + "</span></p>";
      html += "</div>";
      html += "<div class='opr-tl'><a class='btn-edit' href='javascript:;' onclick='editLinkman(" + temp + ")'>编辑</a> &nbsp; <a class='btn-delete' href='javascript:;' onclick='delLinkman(this)'>删除</a></div>";
      html += "</li>";
      html += "</ul>";
      $("#linkmanContent").html(html);
      returnIf(1);
      temp++;
    }else{
      //编辑联系人
      $("#linkmanName_"+linkmanIndex).html(linkmanName);
      $("#linkmanPost_"+linkmanIndex).html(linkmanPost);
      $("#linkmanPhone_"+linkmanIndex).html(linkmanPhone);
      $("#linkmanQQ_"+linkmanIndex).html(linkmanQQ);
      $("#linkmanTrait_"+linkmanIndex).html(linkmanTrait);
      returnIf(1);
    }
  }

  function delLinkman(obj){
    var delLinkman = $("#delLinkman").val();
    $(obj).parent().parent().find("[name=linkmanId]").each(function(){
      if(delLinkman.length < 1){
        $("#delLinkman").val($(this).val());
      }else{
        $("#delLinkman").val(delLinkman + "," + $(this).val());
      }
    });
    $(obj).parent().parent().remove();
  }

  function editLinkman(index){
    var linkmanName = $("#linkmanName_"+index).html().trim();
    var linkmanPost = $("#linkmanPost_"+index).html().trim();
    var linkmanPhone = $("#linkmanPhone_"+index).html().trim();
    var linkmanQQ = $("#linkmanQQ_"+index).html().trim();
    var linkmanTrait = $("#linkmanTrait_"+index).html().trim();
    selectIf(3);
    $("#add_linkmanName").val(linkmanName);
    $("#add_linkmanPost").val(linkmanPost);
    $("#add_linkmanPhone").val(linkmanPhone);
    $("#add_linkmanQQ").val(linkmanQQ);
    $("#add_linkmanTrait").val(linkmanTrait);

    $("#linkmanIndex").val(index);
  }
</script>