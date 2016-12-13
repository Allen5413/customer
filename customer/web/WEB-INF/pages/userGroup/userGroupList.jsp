<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <div class="contain_blockBg">
    <div class="search_title">
      <div class="search_bar">
        <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findUserGroupPage/findUserGroupPageByWhere.htm" method="post">
          <input type="hidden" id="rows" name="rows" />
          <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
          关键字：<input type="text" class="input_100" name="s_name" value="${param.s_name}" />
        </form>
        <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findUserGroupPage/findUserGroupPageByWhere.htm')" />
      </div>
    </div>
    <div class="title-btn">
      <strong>职务设置</strong>
      <a href="#" onclick="add()">新增</a>
      <a href="#" onclick="del()">删除</a>
    </div>
    <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
      <tr>
        <th width="10%" style="text-align: center">
          <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a> |
          <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
        </th>
        <th width="20%">职务名称</th>
        <th width="70%">备注</th>
      </tr>
      <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
        <tr>
          <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
        </tr>
      </c:if>
      <c:forEach var="userGroup" items="${pageInfo.pageResults}" varStatus="status">
        <tr onclick="changeTR(this)">
          <td align="center"><input type="checkbox" name="cb" value="${userGroup.id}"></td>
          <td>${userGroup.name}</td>
          <td>${userGroup.remark}</td>
        </tr>
      </c:forEach>
      <%@ include file="/common/page.jsp"%>
    </table>
  </div>
<script>
  function add(){
    openDialog('添加职务', 0.3, 0.3, '${pageContext.request.contextPath}/addUserGroup/openAddUserGroupPage.htm');
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
      alert("请选择要删除的职务");
      return false;
    }
    if(confirm("您确定要删除选中的职务？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delUserGroup/userGroupDel.htm",
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
