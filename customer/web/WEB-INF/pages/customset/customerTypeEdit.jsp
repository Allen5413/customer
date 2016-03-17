<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<body>
<div class="pop_content" style="width: 95%">
  <form id="editForm" name="editForm" method="post">
    <input type="hidden" name="id" value="${customerType.id}" />
    <ul class="fill_form">
      <li>
        <label>类型名称：</label>
        <input type="text" id="name" name="name" value="${customerType.name}" />
      </li>
      <li><label class="left">备注：</label><textarea class="pText_280" name="remark" id="remark">${customerType.remark}</textarea></li>
    </ul>
  </form>
</div>
</body>
</html>
<script>
  function sub(obj){
    if($("#name").val() == ""){
      alert("请输入名称");
      return false;
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/editCustomerType/editor.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          $(obj).click();
          alert("提交成功！");
          Dialog.close();
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>