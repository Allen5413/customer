<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<body>
<div id="page">
  <div class="contain_blockBg">
    <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findCustomerByWhere/find.htm" method="post">
      <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
      <div class="search_title">
        <div class="search_bar">
          客户经理：
          <c:if test="${isBrowse == 2}">
            <select id="userId" name="userId">
              <option value="">--请选择--</option>
              <c:forEach var="user" items="${userList}">
                <option value="${user.id}" <c:if test="${user.id == param.userId}">selected="selected" </c:if> >${user.name}[${user.zzCode}]</option>
              </c:forEach>
            </select>
          </c:if>
          <c:if test="${isBrowse == 1}">
            ${userName}
          </c:if>
          客户分类：
          <select id="typeId" name="typeId">
            <option value="">--请选择--</option>
            <c:forEach var="type" items="${typeList}">
              <option value="${type.id}" <c:if test="${type.id == param.typeId}">selected="selected" </c:if> >${type.name}</option>
            </c:forEach>
          </select>
          客户状态：
          <select id="stateId" name="stateId">
            <option value="">--请选择--</option>
            <c:forEach var="state" items="${stateList}">
              <option value="${state.id}" <c:if test="${state.id == param.stateId}">selected="selected" </c:if> >${state.name}</option>
            </c:forEach>
          </select>
          所在省份：
          <select id="provinceCode" name="provinceCode">
            <option value="">--请选择--</option>
            <c:forEach var="area" items="${areaList}">
              <option value="${area.code}" <c:if test="${area.code == param.provinceCode}">selected="selected" </c:if> >${area.name}</option>
            </c:forEach>
          </select>
          客户名称：<input type="text" class="input_100" name="name" value="${param.name}" />
          <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="pageForm.submit();" />
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
          <th width="6%">学校No</th>
          <th width="15%">客户名称</th>
          <th width="5%">客户经理</th>
          <th width="5%">所在省份</th>
          <th width="10%">地址</th>
          <th width="5%">办学规模</th>
          <th width="5%">客户代码</th>
          <th width="5%">客户类型</th>
          <th width="5%">客户状态</th>
          <th width="5%">注册人数</th>
          <th width="5%">联系人姓名</th>
          <th width="8%">联系电话</th>
          <th width="5%">职务</th>
          <th width="11%">备注</th>
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
    </form>
  </div>
</div>
</body>
</html>
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
    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.5;
    dialog.Height = getWindowHeightSize() * 1;
    dialog.Title = "添加客户";
    dialog.URL = "${pageContext.request.contextPath}/addCustomer/open.htm";
    dialog.show();
    dialog.okButton.value=" 提 交 ";
    dialog.cancelButton.value=" 取 消 ";
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

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.5;
    dialog.Height = getWindowHeightSize() * 1;
    dialog.Title = "编辑客户";
    dialog.URL = "${pageContext.request.contextPath}/editCustomer/open.htm?id="+id;
    dialog.show();
    dialog.okButton.value=" 提 交 ";
    dialog.cancelButton.value=" 取 消 ";
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

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.5;
    dialog.Height = getWindowHeightSize() * 0.4;
    dialog.Title = "客户指派";
    dialog.URL = "${pageContext.request.contextPath}/assignCustomer/open.htm?id="+id;
    dialog.show();
    dialog.okButton.value=" 提 交 ";
    dialog.cancelButton.value=" 取 消 ";
  }

  function searchInfo(id){
    var dialog = new Dialog();
    dialog.Width = getWindowWidthSize() * 0.4;
    dialog.Height = getWindowHeightSize() * 0.7;
    dialog.Title = "查看客户详情";
    dialog.URL = "${pageContext.request.contextPath}/findCustomerById/open.htm?id="+id;
    dialog.show();
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

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.3;
    dialog.Title = "添加访谈记录";
    dialog.URL = "${pageContext.request.contextPath}/addInterview/open.htm?id="+id;
    dialog.show();
    dialog.okButton.value=" 提 交 ";
    dialog.cancelButton.value=" 取 消 ";
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
      alert("请选择要删除的客户");
      return false;
    }
    if(confirm("该操作会把客户相关信息和访谈记录一并删除，您确定要删除选中的记录？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delCustomer/del.htm",
        method : 'POST',
        async:false,
        data:{"ids":ids.substr(0, ids.length-1)},
        success:function(data){
          if(data.state == 0){
            alert("删除成功！");
            pageForm.submit();
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }
</script>
