<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="addForm" name="addForm" action="${pageContext.request.contextPath}/addInterview/add.htm" method="post">
    <input type="hidden" name="customerId" value="${customer.id}"/>
    <ul class="fill_form">
      <li>
        <label>客户名称：</label>${customer.name}
      </li>
      <li>
        <label>交谈对象：</label>
        <span class="inline-select">
          <select class="select-140" id="customerLankmanId" name="customerLankmanId">
            <option value="">--请选择--</option>
            <c:forEach var="linkman" items="${linkmanList}" varStatus="status">
              <option value="${linkman.id}">${linkman.name}</option>
            </c:forEach>
          </select>
        </span>
      </li>
      <li><label class="left">交流记录：</label><textarea class="pText_280" name="content" id="content"></textarea></li>
    </ul>
  </form>
</div>
<script>
  function sub(){
    var customerLankmanId = $("#customerLankmanId").val();
    var content = $("#content").val();
    if(customerLankmanId == ""){
      alert("请选择交谈对象");
      return false;
    }
    if(content == ""){
      alert("请填写交谈内容");
      return false;
    }
    if(content.length > 500){
      alert("交谈内容不能超过500字");
      return false;
    }

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/addInterview/add.htm",
      data:$('#addForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          searchFormPage($('#pageForm'), '', 1);
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>
