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
      <a href="#" onclick="edit()">编辑</a>
    </c:if>
    <c:if test="${isAssign == 1}">
      <a href="#" onclick="assign()">指派</a>
    </c:if>
    <c:if test="${!empty isAdmin}">
      <a href="#" onclick="addInterview()">添加访谈记录</a>
    </c:if>
  </div>
  <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="5%" >
        <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a>|
        <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
      </th>
      <th width="4%">学校No</th>
      <th width="12%">客户名称</th>
      <th width="5%">客户经理</th>
      <th width="5%">所在省份</th>
      <th width="10%">地址</th>
      <th width="13%">办学规模</th>
      <th width="5%">客户代码</th>
      <th width="5%">客户类型</th>
      <th width="5%">客户状态</th>
      <th width="5%">注册人数</th>
      <th width="5%">联系人姓名</th>
      <th width="6%">联系电话</th>
      <th width="5%">职务</th>
      <th width="10%">备注</th>
    </tr>
    <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
      <tr>
        <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
      </tr>
    </c:if>
    <c:forEach var="customer" items="${pageInfo.pageResults}" varStatus="status">
      <tr onclick="changeTR(this)" >
        <td align="center">
            <%--如果只能管理自己的客户--%>
          <c:if test="${1 == isAdmin && customer.userId == userId}">
            <input type="checkbox" name="cb" value="${customer.id}">
          </c:if>
          <c:if test="${2 == isAdmin}">
            <input type="checkbox" name="cb" value="${customer.id}">
          </c:if>
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
        <td>${customer.lName}</td>
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
      alert("请选择要编辑的客户");
      return false;
    }
    if(num > 1){
      alert("编辑客户不能多选");
      return false;
    }
    openDialog('编辑客户', 0.5, 0.8, '${pageContext.request.contextPath}/editCustomer/open.htm?id='+id);
  }

  function assign(){
    var num = 0;
    var id = 0;
    $("[name=cb]").each(function (){
      if($(this).is(':checked')){
        num++;
        id = $(this).val();
      }
    });

    if(num == 0){
      alert("请选择要指派的客户");
      return false;
    }
    if(num > 1){
      alert("指派客户不能多选");
      return false;
    }
    openDialog('客户指派', 0.5, 0.4, '${pageContext.request.contextPath}/assignCustomer/open.htm?id='+id);
  }

  function searchInfo(id){
    openDialog('编辑客户', 0.4, 0.7, '${pageContext.request.contextPath}/findCustomerById/open.htm?id='+id, 0);
  }

  function addInterview(){
    var num = 0;
    var id = 0;
    $("[name=cb]").each(function (){
      if($(this).is(':checked')){
        num++;
        id = $(this).val();
      }
    });

    if(num == 0){
      alert("请选择客户");
      return false;
    }
    if(num > 1){
      alert("客户不能多选");
      return false;
    }
    openDialog('编辑客户', 0.3, 0.3, '${pageContext.request.contextPath}/addInterview/open.htm?id='+id);
  }
</script>
