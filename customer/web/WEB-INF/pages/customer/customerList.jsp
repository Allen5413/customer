<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="contain_blockBg">
  <div class="search_title">
    <div class="search_bar">
      <form id="pageForm" name="pageForm" action='${pageContext.request.contextPath}/findCustomerByWhere/find.htm' >
        <input type="hidden" id="rows" name="rows" />
        <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
        客户经理：
        <c:if test="${isBrowse == 2}">
          <select name="s_userId">
            <option value="">--请选择--</option>
            <c:forEach var="user" items="${userList}">
              <option value="${user.id}" <c:if test="${user.id == param.s_userId}">selected="selected" </c:if> >${user.name}[${user.zzCode}]</option>
            </c:forEach>
          </select>
        </c:if>
        <c:if test="${isBrowse == 1}">
          ${userName}
        </c:if>
        客户分类：
        <select name="s_typeId">
          <option value="">--请选择--</option>
          <c:forEach var="type" items="${typeList}">
            <option value="${type.id}" <c:if test="${type.id == param.s_typeId}">selected="selected" </c:if> >${type.name}</option>
          </c:forEach>
        </select>
        客户状态：
        <select name="s_stateId">
          <option value="">--请选择--</option>
          <c:forEach var="state" items="${stateList}">
            <option value="${state.id}" <c:if test="${state.id == param.s_stateId}">selected="selected" </c:if> >${state.name}</option>
          </c:forEach>
        </select>
        所在省份：
        <select name="s_provinceCode">
          <option value="">--请选择--</option>
          <c:forEach var="area" items="${areaList}">
            <option value="${area.code}" <c:if test="${area.code == param.s_provinceCode}">selected="selected" </c:if> >${area.name}</option>
          </c:forEach>
        </select>
        客户名称：<input type="text" class="input_100" name="s_name" value="${param.s_name}" />
      </form>
      <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findCustomerByWhere/find.htm')" />
    </div>
  </div>
  <div class="title-btn">
    <strong>客户资料列表</strong>
    <c:if test="${!empty isAdmin}">
      <a href="#" onclick="add()">添加客户</a>
    </c:if>
  </div>
  <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="16%" >操作</th>
      <th width="4%">学校No</th>
      <th width="10%">客户名称</th>
      <th width="4%">客户经理</th>
      <th width="4%">所在省份</th>
      <th width="10%">地址</th>
      <th width="13%">办学规模</th>
      <th width="5%">客户代码</th>
      <th width="5%">客户类型</th>
      <th width="5%">客户状态</th>
      <th width="4%">注册人数</th>
      <th width="4%">联系人姓名</th>
      <th width="5%">联系电话</th>
      <th width="5%">职务</th>
      <th>备注</th>
    </tr>
    <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
      <tr>
        <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
      </tr>
    </c:if>
    <c:forEach var="customer" items="${pageInfo.pageResults}" varStatus="status">
      <tr onclick="changeTR(this)" >
        <td align="center">
          <div class="title-btn">
          <%--如果只能管理自己的客户--%>
            <c:if test="${(1 == isAdmin && customer.userId == userId) || 2 == isAdmin}">
              <a href="#" onclick="edit(${customer.id})">编辑</a>
              <a href="#" onclick="addInterview(${customer.id})">添加访谈记录</a><br />
              <c:if test="${isAssign == 1}">
                <a href="#" onclick="assign(${customer.id})">指派</a>
              </c:if>
              <a href="#" onclick="findLog(${customer.id})">查询信息变更日志</a>
            </c:if>
          </div>
        </td>
        <td>${customer.no}</td>
        <td><a href="#" style="color: #0092DC" onclick="searchInfo(${customer.id})">${customer.cName}</a></td>
        <td>${customer.uName}</td>
        <td>${customer.aName}</td>
        <td>${customer.address}</td>
        <td>${customer.scale}</td>
        <td>${customer.code}</td>
        <td>${customer.ctName}</td>
        <td>${customer.csName}</td>
        <td id="${customer.no}"></td>
        <td><a href="#" style="color: #0092DC" onclick="searchLinkMan(${customer.id})">${customer.lName}</a></td>
        <td>${customer.phone}</td>
        <td>${customer.post}</td>
        <td>${customer.remark}</td>
      </tr>
    </c:forEach>
    <%@ include file="/common/page.jsp"%>
  </table>
</div>
<script>
  $(function(){
    <c:forEach var="customer" items="${pageInfo.pageResults}">
    var no = "${customer.no}";
    if(no != "" && no.length > 0){
      $.ajax({
        url:"${pageContext.request.contextPath}/findCustomerByWhere/findNoCount.htm",
        method : 'POST',
        async:false,
        data:{"no":no},
        success:function(data){
          $("#"+no).html(data);
        }
      });
    }
    </c:forEach>
  });

  function add(){
    openDialog('添加客户', 0.5, 0.8, '${pageContext.request.contextPath}/addCustomer/open.htm');
  }

  function edit(id){
    openDialog('编辑客户', 0.5, 0.8, '${pageContext.request.contextPath}/editCustomer/open.htm?id='+id);
  }

  function assign(id){
    openDialog('客户指派', 0.5, 0.4, '${pageContext.request.contextPath}/assignCustomer/open.htm?id='+id);
  }

  function searchInfo(id){
    openDialog('编辑客户', 0.4, 0.7, '${pageContext.request.contextPath}/findCustomerById/open.htm?id='+id, 0);
  }

  function searchLinkMan(id){
    openDialog('查看联系人', 0.6, 0.7, '${pageContext.request.contextPath}/findLinkmanByCustomerId/open.htm?id='+id, 0);
  }

  function addInterview(id){
    openDialog('添加访谈记录', 0.3, 0.6, '${pageContext.request.contextPath}/addInterview/open.htm?id='+id);
  }

  function findLog(id){
    openDialog('查看客户信息变更日志', 0.9, 0.7, '${pageContext.request.contextPath}/findCustomerLogByCustomerId/find.htm?customerId='+id, 0);
  }
</script>
