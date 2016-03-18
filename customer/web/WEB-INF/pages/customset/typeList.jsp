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
      <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
      <div class="search_title">
        <div class="search_bar">
          选择类别：
          <select name="type" onchange="pageForm.submit();">
            <option value="1">客户类型</option>
            <option value="2">客户状态</option>
          </select>
        </div>
      </div>
      <div class="title-btn">
        <a href="#" onclick="add()">新增</a>
        <a href="#" onclick="edit()">编辑</a>
      </div>
      <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <th width="10%" style="text-align: center">
            <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a> |
            <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
          </th>
          <th width="20%">客户类型</th>
          <th width="10%">状态</th>
          <th width="60%">备注</th>
        </tr>
        <c:if test="${empty typeList}">
          <tr>
            <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
          </tr>
        </c:if>
        <c:forEach var="type" items="${typeList}" varStatus="status">
          <tr onclick="changeTR(this)">
            <td align="center"><input type="checkbox" name="cb" value="${type.id}"></td>
            <td>${type.name}</td>
            <td>${type.state == 0 ? "启用" : "停用"}</td>
            <td>${type.remark}</td>
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
    dialog.Height = getWindowHeightSize() * 0.3;
    dialog.Title = "添加类型";
    dialog.URL = "${pageContext.request.contextPath}/addCustomerType/open.htm";
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
      alert("请选择要编辑的客户类型");
      return false;
    }
    if(num > 1){
      alert("编辑客户类型不能多选");
      return false;
    }

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#pageForm"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.3;
    dialog.Title = "编辑客户类型";
    dialog.URL = "${pageContext.request.contextPath}/editCustomerType/open.htm?id="+id;
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
      alert("请选择要删除的类型");
      return false;
    }
    if(confirm("您确定要删除选中的类型？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delCustomerType/del.htm",
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
