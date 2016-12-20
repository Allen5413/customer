<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%;">
  <form id="addForm" name="addForm" action="${pageContext.request.contextPath}/addCustomer/add.htm" method="post">
    <div class="pop-tabs">
      <a class="on" href="#">客户基本资料</a>
    </div>
    <ul class="fill_form" id="basicInfo">
      <li>
        <label>学校No：</label><input class="pInput_170" type="text" name="no" />
      </li>
      <li>
        <label>客户名称：</label><input class="pInput_170" type="text" id="name" name="name" />
      </li>
      <li>
        <label>办学规模：</label><input class="pInput_170" type="text" name="scale" />
      </li>
      <li>
        <label>客户代码：</label><input class="pInput_170" type="text" name="code" />
      </li>
      <li>
        <label>所在省份：</label>
        <select class="select-140" name="provinceCode">
          <option value="">--请选择--</option>
          <c:forEach var="province" items="${provinceList}">
            <option value="${province.code}">${province.name}</option>
          </c:forEach>
        </select>
      </li>
      <li>
        <label>所在地址：</label><input class="pInput_280" type="text" name="address" />
      </li>
      <li>
        <label>客户分类：</label>
        <select class="select-140" id="typeId" name="customerTypeId">
          <option value="">--请选择--</option>
          <c:forEach var="type" items="${typeList}">
            <option value="${type.id}">${type.name}</option>
          </c:forEach>
        </select>
      </li>
      <li>
        <label>客户状态：</label>
        <select class="select-140" id="stateId" name="customerStateId">
          <option value="">--请选择--</option>
          <c:forEach var="state" items="${stateList}">
            <option value="${state.id}">${state.name}</option>
          </c:forEach>
        </select>
      </li>
      <li>
        <label>客户经理：</label>
        <c:if test="${isBrowse == 2}">
          <select name="userId">
            <option value="">--请选择--</option>
            <c:forEach var="user" items="${userList}">
              <option value="${user.id}" <c:if test="${user.id == loginId}">selected="selected" </c:if> >${user.name}[${user.zzCode}]</option>
            </c:forEach>
          </select>
        </c:if>
        <c:if test="${isBrowse == 1}">
          <input type="hidden" name="userId" value="${loginId}" />
          ${loginName}
        </c:if>
      </li>
    </ul>
    <br /><br />
    <div class="pop-tabs">
      <a class="on" href="#">客户联系人</a>
    </div>
    <ul class="fill_form bg-li" id="linkmanUL">
      <input type="hidden" id="linkmanInfo" name="linkmanInfo" />
      <li>
        <p><label>联系人姓名：</label><input class="pInput_150" type="text" name="linkmanName" />
          <label>联系电话：</label><input class="pInput_150" type="text" name="linkmanPhone" /></p>
        <p>
          <label>职务：</label><input class="pInput_150" type="text" name="linkmanPost" />
          <label>备注：</label><input class="pInput_150" type="text" name="linkmanRemark" />
        </p>
        <p>
          <label>QQ：</label><input class="pInput_150" type="text" name="linkmanQQ" />
          <label>特点：</label><input class="pInput_150" type="text" name="linkmanTrait" />
        </p>
      </li>
    </ul>
    <a class="btnpop" href="#" onclick="addLinkman()">添加联系人</a>
  </form>
</div>
<script>
  function sub(){
    var name = $("#name").val();
    var typeId = $("#typeId").val();
    var stateId = $("#stateId").val();
    if(name == ""){
      alert("请输入客户名称");
      return false;
    }
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
      $(this).find("[name=linkmanName]").each(function(){
        temp = $(this).val()+"*";
      });
      $(this).find("[name=linkmanPhone]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanPost]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanRemark]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanQQ]").each(function(){
        temp += $(this).val()+"*";
      });
      $(this).find("[name=linkmanTrait]").each(function(){
        temp += $(this).val();
      });
      linkmanInfo.val(linkmanInfo.val()+temp+"|");
    })

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/addCustomer/add.htm",
      data:$('#addForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findCustomerByWhere/find.htm', 1);
        }else{
          alert(data.msg);
          $("#linkmanInfo").val("");
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
    addHtml += "<span class=\"right\"><a href=\"#\" onclick=\"delLinkman(this)\">删除</a></span>";
    addHtml += "<p><label>联系人姓名：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanName\" />";
    addHtml += "<label>联系电话：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanPhone\" /></p>";
    addHtml += "<p><label>职务：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanPost\" />";
    addHtml += "<label>备注：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanRemark\" /></p>";
    addHtml += "<p><label>QQ：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanQQ\" />";
    addHtml += "<label>特点：</label><input class=\"pInput_150\" type=\"text\" name=\"linkmanTrait\" /></p>";
    addHtml += "</li>";
    $("#linkmanUL").html(html+addHtml);
  }

  function delLinkman(obj){
    $(obj).parent().parent().remove();
  }
</script>
