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
    <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findUserByWhere/find.htm" method="post">
      <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
      <div class="search_title">
        <div class="search_bar">
          角色名：
          <select id="groupId" name="groupId">
            <option value="">--请选择--</option>
            <c:forEach var="userGroup" items="${userGroupList}">
              <option value="${userGroup.id}" <c:if test="${userGroup.id == param.groupId}">selected="selected" </c:if> >${userGroup.name}</option>
            </c:forEach>
          </select>
          状态：
          <select id="state" name="state">
            <option value="">--请选择--</option>
            <option value="1" <c:if test="${1 == param.state}">selected="selected" </c:if>>启用</option>
            <option value="2" <c:if test="${2 == param.state}">selected="selected" </c:if>>停用</option>
          </select>
          ZZ号：<input type="text" id="zzCode" name="zzCode" value="${param.zzCode}"/>
          姓名：<input type="text" id="name" name="name" value="${param.name}"/>
          <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="pageForm.submit();" />
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
          <th width="5%">用户角色</th>
          <th width="5%">状态</th>
          <th width="36%">备注</th>
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
            <td>${user.remark}</td>
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
  function add(){
    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.4;
    dialog.Title = "新增用户";
    dialog.URL = "${pageContext.request.contextPath}/addUser/open.htm";
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
      alert("请选择要编辑的用户");
      return false;
    }
    if(num > 1){
      alert("编辑用户不能多选");
      return false;
    }

    var dialog = new Dialog();
    dialog.ShowButtonRow=true;
    dialog.OKEvent = function(){
      dialog.innerFrame.contentWindow.sub($("#subBut"));
    };
    dialog.Width = getWindowWidthSize() * 0.3;
    dialog.Height = getWindowHeightSize() * 0.4;
    dialog.Title = "编辑用户";
    dialog.URL = "${pageContext.request.contextPath}/editUser/open.htm?id="+id;
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
            pageForm.submit();
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }
</script>
