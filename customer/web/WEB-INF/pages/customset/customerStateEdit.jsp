<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="editForm" name="editForm" method="post">
    <input type="hidden" name="id" value="${customerState.id}" />
    <ul class="fill_form">
      <li>
        <label>状态名称：</label>
        <input type="text" id="name" name="name" value="${customerState.name}" />
      </li>
      <li>
        <label>状态：</label>
        <select id="state" name="state">
          <option value="0" <c:if test="${customerState.state == 0}">selected="selected"</c:if> >启用</option>
          <option value="1" <c:if test="${customerState.state == 1}">selected="selected"</c:if> >停用</option>
        </select>
      </li>
      <li><label class="left">备注：</label><textarea class="pText_280" name="remark" id="remark">${customerState.remark}</textarea></li>
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
      url:"${pageContext.request.contextPath}/editCustomerState/editor.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findCustomset/find.htm', 1);
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>