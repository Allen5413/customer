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
    <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findCustomset/find.htm" method="post">
      <div class="search_title">
        <div class="search_bar">
          选择类别：
          <select name="type" onchange="pageForm.submit();">
            <option value="1">客户类型</option>
            <option value="2" selected="selected">客户状态</option>
          </select>
        </div>
      </div>
      <div class="title-btn">
        <a href="#" onclick="add()">新增</a>
        <a href="#" onclick="edit()">编辑</a>
        <a href="#" onclick="del()">删除</a>
      </div>
      <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <th width="10%" style="text-align: center">
            <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a> |
            <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
          </th>
          <th width="20%">客户类型</th>
          <th width="70%">备注</th>
        </tr>
        <c:if test="${empty stateList}">
          <tr>
            <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
          </tr>
        </c:if>
        <c:forEach var="state" items="${stateList}" varStatus="status">
          <tr>
            <td align="center"><input type="checkbox" name="cb" value="${state.id}"></td>
            <td>${state.name}</td>
            <td>${state.remark}</td>
          </tr>
        </c:forEach>
        <tr>
          <td class="tbg" style="height: 15px;" colspan="20"></td>
        </tr>
      </table>
    </form>
  </div>
</div>
</body>
</html>
<script>
  function add(){
    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#pageForm"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.2;
    dialog.Title = "添加客户状态";
    dialog.URL = "${pageContext.request.contextPath}/addCustomerState/open.htm";
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
      alert("请选择要编辑的客户状态");
      return false;
    }
    if(num > 1){
      alert("编辑客户状态不能多选");
      return false;
    }

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#pageForm"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.4;
    dialog.Title = "编辑客户状态";
    dialog.URL = "${pageContext.request.contextPath}/editCustomerState/open.htm?id="+id;
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
      alert("请选择要删除的客户状态");
      return false;
    }
    if(confirm("您确定要删除选中的客户状态？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delCustomerState/del.htm",
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
