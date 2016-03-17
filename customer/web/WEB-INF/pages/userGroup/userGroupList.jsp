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
    <form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findUserGroupPage/findUserGroupPageByWhere.htm" method="post">
      <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
      <div class="search_title">
        <div class="search_bar">
          关键字：<input type="text" class="input_100" name="name" value="${param.name}" />
          <input id="subBut" class="btnImg" type="image" src="${pageContext.request.contextPath}/images/btn_search.png" onclick="pageForm.submit();" />
        </div>
      </div>
      <div class="title-btn">
        <strong>登录角色设置</strong>
        <a href="#" onclick="add()">新增</a>
        <a href="#" onclick="del()">删除</a>
      </div>
      <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <th width="10%" style="text-align: center">
            <a href="#" onclick="checkAll('cb')" style="color: #0092DC">全选</a> |
            <a href="#" onclick="checkNall('cb')" style="color: #0092DC">反选</a>
          </th>
          <th width="20%">角色名称</th>
          <th width="70%">备注</th>
        </tr>
        <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
          <tr>
            <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
          </tr>
        </c:if>
        <c:forEach var="userGroup" items="${pageInfo.pageResults}" varStatus="status">
          <tr>
            <td align="center"><input type="checkbox" name="cb" value="${userGroup.id}"></td>
            <td>${userGroup.name}</td>
            <td>${userGroup.remark}</td>
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
    dialog.Height = getWindowHeightSize() * 0.2;
    dialog.Title = "添加角色";
    dialog.URL = "${pageContext.request.contextPath}/addUserGroup/openAddUserGroupPage.htm";
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
      alert("请选择要删除的角色");
      return false;
    }
    if(confirm("您确定要删除选中的角色？")){
      $.ajax({
        url:"${pageContext.request.contextPath}/delUserGroup/userGroupDel.htm",
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
