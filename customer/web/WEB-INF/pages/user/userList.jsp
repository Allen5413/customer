<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="contain_blockBg">
  <div class="search_title">
    <div class="search_bar">
      <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findUserByWhere/find.htm" method="post">
        <input type="hidden" id="rows" name="rows" />
        <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
        角色名：
        <select id="s_groupId" name="s_groupId">
          <option value="">--请选择--</option>
          <c:forEach var="userGroup" items="${userGroupList}">
            <option value="${userGroup.id}" <c:if test="${userGroup.id == param.s_groupId}">selected="selected" </c:if> >${userGroup.name}</option>
          </c:forEach>
        </select>
        状态：
        <select id="s_state" name="s_state">
          <option value="">--请选择--</option>
          <option value="1" <c:if test="${1 == param.s_state}">selected="selected" </c:if>>启用</option>
          <option value="2" <c:if test="${2 == param.s_state}">selected="selected" </c:if>>停用</option>
        </select>
        等级：
        <select id="s_level" name="s_level">
          <option value="">--请选择--</option>
          <c:if test="${level == 0}">
            <option value="0" <c:if test="${'0' eq param.s_level}">selected="selected" </c:if>>公司</option>
            <option value="1" <c:if test="${'1' eq param.s_level}">selected="selected" </c:if>>区域</option>
            <option value="2" <c:if test="${'2' eq param.s_level}">selected="selected" </c:if>>省级</option>
            <option value="3" <c:if test="${'3' eq param.s_level}">selected="selected" </c:if>>业务</option>
          </c:if>
          <c:if test="${level == 1}">
            <option value="2" <c:if test="${'2' eq param.s_level}">selected="selected" </c:if>>省级</option>
            <option value="3" <c:if test="${'3' eq param.s_level}">selected="selected" </c:if>>业务</option>
          </c:if>
          <c:if test="${level == 2}">
            <option value="3" <c:if test="${'3' eq param.s_level}">selected="selected" </c:if>>业务</option>
          </c:if>
        </select>
        ZZ号：<input type="text" id="s_zzCode" name="s_zzCode" value="${param.s_zzCode}"/>
        姓名：<input type="text" id="s_name" name="s_name" value="${param.s_name}"/>
      </form>
      <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findUserByWhere/find.htm')" />
    </div>
  </div>
  <div class="title-btn">
    <strong>账号管理</strong>
    <a href="#" onclick="add()">新增</a>
    <a href="#" onclick="edit()">编辑</a>
    <a href="#" onclick="del()">删除</a>
  </div>
  <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="5%" >
        <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a>|
        <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
      </th>
      <th width="7%">ZZ号</th>
      <th width="20%">姓名</th>
      <th width="7%">联系电话</th>
      <th width="5%">职务</th>
      <th width="5%">状态</th>
      <th width="5%">等级</th>
      <th width="5%">上级</th>
      <th width="26%">备注</th>
    </tr>
    <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
      <tr>
        <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
      </tr>
    </c:if>
    <c:forEach var="user" items="${pageInfo.pageResults}" varStatus="status">
      <tr onclick="changeTR(this)">
        <td align="center"><input type="checkbox" name="cb" value="${user.id}"></td>
        <td>${user.zzCode}</td>
        <td>${user.name}</td>
        <td>${user.phone}</td>
        <td>${user.ugName}</td>
        <td>${user.state}</td>
        <td>
          <c:if test="${user.level eq '0'}">公司</c:if>
          <c:if test="${user.level eq '1'}">区域</c:if>
          <c:if test="${user.level eq '2'}">省级</c:if>
          <c:if test="${user.level eq '3'}">业务</c:if>
        </td>
        <td>${user.parentName}</td>
        <td>${user.remark}</td>
      </tr>
    </c:forEach>
    <%@ include file="/common/page.jsp"%>
  </table>
</div>
<script>
  function add(){
    openDialog('新增用户', 0.3, 0.5, '${pageContext.request.contextPath}/addUser/open.htm');
  }


  function edit(){
    var num = 0;
    var id = 0;
    $("[name=cb]").each(function (){
      if($(this).is(':checked')){
        num++;
        id = $(this).val();
      }
    });

    if(num == 0){
      alert("请选择要编辑的用户");
      return false;
    }
    if(num > 1){
      alert("编辑用户不能多选");
      return false;
    }
    openDialog('编辑用户', 0.3, 0.5, '${pageContext.request.contextPath}/editUser/open.htm?id='+id);
  }

  function del(){
    var num = 0;
    var ids = "";
    $("[name=cb]").each(function (){
      if($(this).is(':checked')){
        num++;
        ids += $(this).val()+",";
      }
    });

    if(num == 0){
      alert("请选择要删除的用户");
      return false;
    }
    if(confirm("您确定要删除选中的用户？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delUser/del.htm",
        method : 'POST',
        async:false,
        data:{"ids":ids.substr(0, ids.length-1)},
        success:function(data){
          if(data.state == 0){
            alert("删除成功！");
            $("#subBut").click();
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }
</script>
