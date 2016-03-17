<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<body style="background:#fff;">
<div class="pop_content">
    <ul class="fill_form border-bg">
      <li>
        <label>客户名称：</label>${customer.name}
      </li>
      <li>
        <label>办学规模：</label>${customer.scale}
      </li>
      <li>
        <label>客户代码：</label>${customer.code}
      </li>
      <li>
        <label>所在省份：</label>${area.name}
      </li>
      <li>
        <label>所在地址：</label>${customer.address}
      </li>
      <li>
        <label>客户分类：</label>${customerType.name}
      </li>
      <li>
        <label>客户状态：</label>${customerState.name}
      </li>
    </ul>
    <ul class="fill_form">
      <li>
        <label>指派给：</label>
        <span class="inline-select">
          <select class="select-140" id="userId">
            <option value="">--请选择--</option>
            <c:forEach var="user" items="${userList}">
              <option value="${user.id}">${user.name}[${user.zzCode}]</option>
            </c:forEach>
          </select>
        </span>
      </li>
    </ul>
</div>
</body>
</html>
<script>
  function sub(obj){
    var userId = $("#userId").val();
    if(userId == ""){
      alert("请选择客户经理");
      return false;
    }

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/assignCustomer/assign.htm",
      data:{id:"${customer.id}",userId:userId},
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
