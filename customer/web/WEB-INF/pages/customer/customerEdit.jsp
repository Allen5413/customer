<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<body>
<div class="pop_content" style="width: 95%">
  <form id="editForm" name="editForm" action="${pageContext.request.contextPath}/editCustomer/editor.htm" method="post">
    <input type="hidden" name="id" value="${customer.id}" />
    <input type="hidden" name="version" value="${customer.version}" />
    <div class="pop-tabs">
      <a class="on" href="#">客户基本资料</a>
    </div>
    <ul class="fill_form" id="basicInfo">
      <li>
        <label>客户名称：</label>${customer.name}
      </li>
      <li>
        <label>办学规模：</label><input class="pInput_170" type="text" name="scale" value="${customer.scale}" />
      </li>
      <li>
        <label>客户代码：</label><input class="pInput_170" type="text" name="code" value="${customer.code}" />
      </li>
      <li>
        <label>所在省份：</label>
        <select class="select-140" name="provinceCode">
          <option value="">--请选择--</option>
          <c:forEach var="province" items="${provinceList}">
            <option value="${province.code}" <c:if test="${customer.provinceCode eq province.code}">selected="selected" </c:if> >${province.name}</option>
          </c:forEach>
        </select>
      </li>
      <li>
        <label>所在地址：</label><input class="pInput_280" type="text" name="address" value="${customer.address}" />
      </li>
      <li>
        <label>客户分类：</label>
        <select class="select-140" id="typeId" name="customerTypeId">
          <option value="">--请选择--</option>
          <c:forEach var="type" items="${typeList}">
            <option value="${type.id}" <c:if test="${customer.customerTypeId eq type.id}">selected="selected" </c:if>>${type.name}</option>
          </c:forEach>
        </select>
      </li>
      <li>
        <label>客户状态：</label>
        <select class="select-140" id="stateId" name="customerStateId">
          <option value="">--请选择--</option>
          <c:forEach var="state" items="${stateList}">
            <option value="${state.id}" <c:if test="${customer.customerStateId eq state.id}">selected="selected" </c:if>>${state.name}</option>
          </c:forEach>
        </select>
      </li>
    </ul>
    <br /><br />
    <div class="pop-tabs">
      <a class="on" href="#">客户联系人</a>
    </div>
    <ul class="fill_form bg-li" id="linkmanUL">
      <input type="hidden" id="linkmanInfo" name="linkmanInfo" />
      <input type="hidden" id="delLinkman" name="delLinkman" />
      <c:forEach var="linkman" items="${linkmanList}" varStatus="status">
        <li>
          <input type="hidden" name="linkmanId" value="${linkman.id}" />
          <c:if test="${status.index > 0}">
            <span class="right"><a href="#" onclick="delLinkman(this)">删除</a></span>
          </c:if>
          <p><label>联系人姓名：</label><input class="pInput_150" type="text" name="linkmanName" value="${linkman.name}" />
            <label>联系电话：</label><input class="pInput_150" type="text" name="linkmanPhone" value="${linkman.phone}" /></p>
          <p>
            <label>职务：</label><input class="pInput_150" type="text" name="linkmanPost" value="${linkman.post}" />
            <label>备注：</label><input class="pInput_150" type="text" name="linkmanRemark" value="${linkman.remark}" />
          </p>
        </li>
      </c:forEach>
    </ul>
    <a class="btnpop" href="#" onclick="addLinkman()">添加联系人</a>
  </form>
</div>
</body>
</html>
<script>
  function sub(obj){
    var typeId = $("#typeId").val();
    var stateId = $("#stateId").val();
    if(typeId == ""){
      alert("请选择客户类型");
      return false;
    }
    if(stateId == ""){
      alert("请选择客户状态");
      return false;
    }

    var linkmanInfo = $("#linkmanInfo");
    $("#linkmanUL").find("li").each(function(){
      var temp = "";
      $(this).find("[name=linkmanId]").each(function(){
        temp = $(this).val()+"*";
      });
      $(this).find("[name=linkmanName]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanPhone]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanPost]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanRemark]").each(function(){
        temp += $(this).val();
      });
      linkmanInfo.val(linkmanInfo.val()+temp+"|");
    })

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/editCustomer/editor.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          $(obj).click();
          alert("提交成功！");
          Dialog.close();
        }else{
          alert(data.msg);
          $("#linkmanInfo").val("");
          $("#delLinkman").val("");
        }
      }
    });
  }

  function addLinkman(){
    //判断已经有几个联系人了，最多他添加3个
    var num = 1;
    $("#linkmanUL").find("li").each(function(){
      num++;
    });
    if(num > 3){
      alert("最多添加3个联系人");
      return false;
    }
    var html = $("#linkmanUL").html();
    var addHtml = "<li>";
    addHtml += "<input type=\"hidden\" name=\"linkmanId\" value=\"new\" />";
    addHtml += "<span class=\"right\"><a href=\"#\" onclick=\"delLinkman(this)\">删除</a></span>";
    addHtml += "<p><label>联系人姓名：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanName\" />";
    addHtml += "<label>联系电话：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanPhone\" /></p>";
    addHtml += "<p><label>职务：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanPost\" />";
    addHtml += "<label>备注：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanRemark\" /></p>";
    addHtml += "</li>";
    $("#linkmanUL").html(html+addHtml);
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
</script>
