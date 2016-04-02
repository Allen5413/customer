<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="editForm" name="editForm" action="${pageContext.request.contextPath}/editUser/editor.htm" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    <ul class="fill_form">
      <li>
        <label>ZZ号：</label><input type="text" id="zzCode" name="zzCode" value="${user.zzCode}" />
      </li>
      <li>
        <label>姓名：</label><input type="text" id="name" name="name" value="${user.name}" />
      </li>
      <li>
        <label>联系电话：</label><input type="text" id="phone" name="phone" value="${user.phone}" />
      </li>
      <li>
        <label>用户角色：</label>
        <span class="inline-select">
          <select class="select-140" id="userGroupId" name="userGroupId">
            <option value="">--请选择--</option>
            <c:forEach var="userGroup" items="${userGroupList}">
              <option value="${userGroup.id}" <c:if test="${userGroupId == userGroup.id}">selected="selected"</c:if> >${userGroup.name}</option>
            </c:forEach>
          </select>
        </span>
      </li>
      <li>
        <label>状态：</label>
        <span class="inline-select">
          <select class="select-140" id="state" name="state">
            <option value="1" <c:if test="${1 == user.state}">selected="selected"</c:if>>启用</option>
            <option value="2" <c:if test="${2 == user.state}">selected="selected"</c:if>>停用</option>
          </select>
        </span>
      </li>
      <li><label class="left">备注：</label><textarea class="pText_280" name="remark" id="remark">${user.remark}</textarea></li>
    </ul>
  </form>
</div>
<script>
  function sub(obj){
    if($("#zzCode").val() == ""){
      alert("请填写ZZ号");
      return false;
    }
    if($("#name").val() == ""){
      alert("请填写姓名");
      return false;
    }
    if($("#userGroupId").val() == ""){
      alert("请选择角色");
      return false;
    }

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/editUser/editor.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findUserByWhere/find.htm', 1);
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>
