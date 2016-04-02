<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="contain_blockBg">
  <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findUserGroupResource/find.htm" method="post">
    <div class="search_title">
      <div class="search_bar">
        <strong>角色使用权限</strong>
        角色名：
        <select name="s_groupId" id="s_groupId" onchange="searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findUserGroupResource/find.htm')">
          <c:forEach var="userGroup" items="${userGroupList}">
            <option value="${userGroup.id}" <c:if test="${userGroup.id == param.s_groupId}">selected="selected"</c:if> >${userGroup.name}</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
      <tr>
        <th width="20%" style="text-align: center">角色名</th>
        <th width="20%">页面</th>
        <th width="60%">权限设置</th>
      </tr>
      <c:if test="${empty resourceList}">
        <tr>
          <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
        </tr>
      </c:if>
      <c:forEach var="resource" items="${resourceList}" varStatus="status">
        <tr>
          <td align="center">${userGroup.name}</td>
          <td>${resource.name}</td>
          <td>
            <c:if test="${resource.rId == 1}">
              允许浏览：
              <select id="isBrowse" name="isBrowse">
                <option value="">--请选择--</option>
                <option value="1" <c:if test="${resource.isBrowse == 1}">selected="selected"</c:if> >自身客户</option>
                <option value="2" <c:if test="${resource.isBrowse == 2}">selected="selected"</c:if> >所有客户</option>
              </select>&nbsp;&nbsp;
              允许管理：
              <select id="isAdmin" name="isAdmin">
                <option value="">--请选择--</option>
                <option value="1" <c:if test="${resource.isAdmin == 1}">selected="selected"</c:if> >自身客户</option>
                <option value="2" <c:if test="${resource.isAdmin == 2}">selected="selected"</c:if> >所有客户</option>
              </select>&nbsp;&nbsp;
              <input type="checkbox" id="isAssign" name="isAssign" <c:if test="${resource.isAssign == 1}">checked="checked"</c:if> />指派：
            </c:if>
            <c:if test="${resource.rId > 1}">
              <input type="checkbox" id="rIds" name="rIds" value="${resource.rId}" <c:if test="${!empty resource.gId}">checked="checked"</c:if> />管理
            </c:if>
          </td>
        </tr>
      </c:forEach>
      <tr>
        <td class="tbg" style="height: 15px;" colspan="20"></td>
      </tr>
    </table>
    <br /><br />
    <a class="btnpop" href="#" onclick="sub()">保存</a>
  </form>
</div>
<script>
  function sub(){
    if(confirm("您确定要为该角色保存此权限？")){
      var groupId = "${param.s_groupId}" == "" ? $("#s_groupId").val() : "${param.s_groupId}";
      var isBrowse = $("#isBrowse").val();
      var isAdmin = $("#isAdmin").val();
      var isAssign = $("#isAssign").is(":checked") ? 1 : 0;
      var rIds = "";
      $("[name=rIds]").each(function(){
        if($(this).is(":checked")){
          rIds += $(this).val()+",";
        }
      });
      if(rIds.length > 0) {
        rIds = rIds.substr(0, rIds.length-1);
      }
      $.ajax({
        url:"${pageContext.request.contextPath}/addUserGroupResource/add.htm",
        method : 'POST',
        async:false,
        data:{"groupId":groupId,
              "isBrowse":isBrowse,
              "isAdmin":isAdmin,
              "isAssign":isAssign,
              "rIds":rIds},
        success:function(data){
          if(data.state == 0){
            alert("保存成功！");
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }

</script>
