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
    <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findInterviewByWhere/find.htm" method="post">
      <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
      <div class="search_title">
        <div class="search_bar">
          客户名称：
          <select id="customerId" name="customerId">
            <option value="">--请选择--</option>
            <c:forEach var="customer" items="${customerList}">
              <option value="${customer.id}" <c:if test="${customer.id == param.customerId}">selected="selected" </c:if> >${customer.name}</option>
            </c:forEach>
          </select>
          客户经理：
          <select id="userId" name="userId">
            <option value="">--请选择--</option>
            <c:forEach var="user" items="${userList}">
              <option value="${user.id}" <c:if test="${user.id == param.userId}">selected="selected" </c:if> >${user.name}[${user.zzCode}]</option>
            </c:forEach>
          </select>
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
          开始日期：<input type="text" id="beginDate" name="beginDate" class="Wdate" style="width: 110px;" onfocus="WdatePicker({readOnly:true})" value="${param.beginDate}"/>
          结束日期：<input type="text" id="endDate" name="endDate" class="Wdate" style="width: 110px;" onfocus="WdatePicker({readOnly:true})" value="${param.endDate}"/>
          <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="pageForm.submit();" />
        </div>
      </div>
      <div class="title-btn">
        <strong>客户访谈记录</strong>
        <a href="#" onclick="edit()">编辑</a>
        <a href="#" onclick="del()">删除</a>
      </div>
      <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <th width="5%" >
            <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a>|
            <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
          </th>
          <th width="7%">客户经理</th>
          <th width="20%">客户名称</th>
          <th width="7%">交谈对象</th>
          <th width="5%">客户状态</th>
          <th width="5%">客户类型</th>
          <th width="36%">交谈记录</th>
          <th width="15%">最后修改日期</th>
        </tr>
        <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
          <tr>
            <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
          </tr>
        </c:if>
        <c:forEach var="interview" items="${pageInfo.pageResults}" varStatus="status">
          <tr onclick="changeTR(this)">
            <td align="center">
              <c:if test="${nowDate eq fn:substring(interview.operateTime, 0, 10)}">
                <input type="checkbox" name="cb" value="${interview.id}">
              </c:if>
            </td>
            <td>${interview.uName}</td>
            <td>${interview.cName}</td>
            <td>${interview.clName}</td>
            <td>${interview.csName}</td>
            <td>${interview.ctName}</td>
            <td>${interview.content}</td>
            <td>${interview.operateTime}</td>
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
      alert("请选择要编辑的记录");
      return false;
    }
    if(num > 1){
      alert("编辑记录不能多选");
      return false;
    }

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.4;
    dialog.Title = "编辑访谈记录";
    dialog.URL = "${pageContext.request.contextPath}/editInterview/open.htm?id="+id;
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
      alert("请选择要删除的记录");
      return false;
    }
    if(confirm("您确定要删除选中的记录？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delInterview/interviewDel.htm",
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
