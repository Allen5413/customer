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
  <form id="addForm" name="addForm" action="${pageContext.request.contextPath}/addUser/add.htm" method="post">
    <input type="hidden" name="customerId" value="${customer.id}"/>
    <ul class="fill_form">
      <li>
        <label>ZZ号：</label><input type="text" id="zzCode" name="zzCode" />
      </li>
      <li>
        <label>姓名：</label><input type="text" id="name" name="name" />
      </li>
      <li>
        <label>联系电话：</label><input type="text" id="phone" name="phone" />
      </li>
      <li>
        <label>用户角色：</label>
        <span class="inline-select">
          <select class="select-140" id="userGroupId" name="userGroupId">
            <option value="">--请选择--</option>
            <c:forEach var="userGroup" items="${userGroupList}">
              <option value="${userGroup.id}">${userGroup.name}</option>
            </c:forEach>
          </select>
        </span>
      </li>
      <li>
        <label>状态：</label>
        <span class="inline-select">
          <select class="select-140" id="state" name="state">
            <option value="1">启用</option>
            <option value="2">停用</option>
          </select>
        </span>
      </li>
      <li><label class="left">备注：</label><textarea class="pText_280" name="remark" id="remark"></textarea></li>
    </ul>
  </form>
</div>
</body>
</html>
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
      url:"${pageContext.request.contextPath}/addUser/add.htm",
      data:$('#addForm').serialize(),
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
