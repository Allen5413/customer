<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="contain_blockBg">
  <div class="search_title">
    <div class="search_bar">
      <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findInterviewByWhere/find.htm" method="post">
        <input type="hidden" id="rows" name="rows" />
        <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
        客户名称：
        <select id="s_customerId" name="s_customerId">
          <option value="">--请选择--</option>
          <c:forEach var="customer" items="${customerList}">
            <option value="${customer.id}" <c:if test="${customer.id == param.s_customerId}">selected="selected" </c:if> >${customer.name}</option>
          </c:forEach>
        </select>
        客户经理：
        <select id="s_userId" name="s_userId">
          <option value="">--请选择--</option>
          <c:forEach var="user" items="${userList}">
            <option value="${user.id}" <c:if test="${user.id == param.s_userId}">selected="selected" </c:if> >${user.name}[${user.zzCode}]</option>
          </c:forEach>
        </select>
        客户分类：
        <select id="s_typeId" name="s_typeId">
          <option value="">--请选择--</option>
          <c:forEach var="type" items="${typeList}">
            <option value="${type.id}" <c:if test="${type.id == param.s_typeId}">selected="selected" </c:if> >${type.name}</option>
          </c:forEach>
        </select>
        客户状态：
        <select id="s_stateId" name="s_stateId">
          <option value="">--请选择--</option>
          <c:forEach var="state" items="${stateList}">
            <option value="${state.id}" <c:if test="${state.id == param.s_stateId}">selected="selected" </c:if> >${state.name}</option>
          </c:forEach>
        </select>
        开始日期：<input type="text" id="s_beginDate" name="s_beginDate" class="Wdate" style="width: 110px;" onfocus="WdatePicker({readOnly:true})" value="${param.s_beginDate}"/>
        结束日期：<input type="text" id="s_endDate" name="s_endDate" class="Wdate" style="width: 110px;" onfocus="WdatePicker({readOnly:true})" value="${param.s_endDate}"/>
      </form>
      <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findInterviewByWhere/find.htm');" />
    </div>
  </div>
  <div class="title-btn">
    <strong>客户访谈记录</strong>
  </div>
  <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="15%" >操作</th>
      <th width="5%">客户经理</th>
      <th width="7%">客户名称</th>
      <th width="5%">交谈对象</th>
      <th width="5%">客户状态</th>
      <th width="5%">客户类型</th>
      <th width="35%">交谈记录</th>
      <th width="8%">最后修改日期</th>
      <th width="5%">ip</th>
      <th width="15%">操作地址</th>
    </tr>
    <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
      <tr>
        <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
      </tr>
    </c:if>
    <c:forEach var="interview" items="${pageInfo.pageResults}" varStatus="status">
      <tr onclick="changeTR(this)">
        <td>
          <div class="title-btn">
            <c:if test="${loginZzCode eq interview.creator && !interview.isPassOneDay}">
              <a href="#" onclick="edit(${interview.id})">编辑</a>
            </c:if>
            <c:if test="${'100005' eq loginZzCode || '177126' eq loginZzCode}">
              <a href="#" onclick="del(${interview.id})">删除</a>
            </c:if>
            <a href="#" onclick="searchFile(${interview.id})">附件</a>
          </div>
        </td>
        <td>${interview.uName}</td>
        <td>${interview.cName}</td>
        <td>${interview.clName}</td>
        <td>${interview.csName}</td>
        <td>${interview.ctName}</td>
        <td>${interview.content}</td>
        <td>${interview.operateTime}</td>
        <td>${interview.ip}</td>
        <td>${interview.address}</td>
      </tr>
    </c:forEach>
    <%@ include file="/common/page.jsp"%>
  </table>
</div>
<script>
  function edit(id){
    openDialog('编辑访谈记录', 0.3, 0.4, '${pageContext.request.contextPath}/editInterview/open.htm?id='+id);
  }

  function del(id){
    if(confirm("您确定要删除选中的记录？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delInterview/interviewDel.htm",
        method : 'POST',
        async:false,
        data:{"ids":id},
        success:function(data){
          if(data.state == 0){
            alert("删除成功！");
            searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findInterviewByWhere/find.htm');
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }

  function searchFile(id){
    openDialog('查看访谈记录附件', 0.8, 0.8, '${pageContext.request.contextPath}/findInterviewFileByInterviewId/open.htm?interviewId='+id);
  }
</script>
