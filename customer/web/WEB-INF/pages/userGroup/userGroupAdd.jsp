<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
<form id="addForm" name="addForm" method="post">
  <ul class="fill_form">
    <li>
      <label>职务名称：</label>
      <input type="text" id="name" name="name" />
    </li>
    <li><label class="left">备注：</label><textarea class="pText_280" name="remark" id="remark"></textarea></li>
  </ul>
</form>
</div>
<script>
  function sub(){
    if($("#name").val() == ""){
      alert("请输入名称");
      return false;
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/addUserGroup/userGroupAdd.htm",
      data:$('#addForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          $(obj).click();
          alert("提交成功！");
          searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findUserGroupPage/findUserGroupPageByWhere.htm', 1);
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>