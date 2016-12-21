<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="addForm" name="addForm" action="${pageContext.request.contextPath}/addInterview/add.htm" method="post" enctype="multipart/form-data">
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
      <li>
        <label>当前ip：</label>${ip}
        <input type="hidden" name="ip" value="${ip}" />
      </li>
      <li>
        <label>当前地址：</label>${address}
        <input type="hidden" name="address" value="${address}" />
      </li>
      <li>
        <label>上传图片：</label>
        <input id="img" type="file" multiple name="img" value="选择要上传的照片">
        <button type="button" class="am-btn am-btn-primary" onClick="goOnFile()"><span class="am-icon-plus"></span> 继续上传</button>
        <div id="goOnFileDiv"></div>
      </li>
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

    $("#addForm").ajaxSubmit({
      url:"${pageContext.request.contextPath}/addInterview/add.htm",
      dataType : 'json',
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

  var num = 0;
  function goOnFile() {
    num++;
    var addHtml = "<li><label></label><input id='img" + num + "' name='img' type='file' multiple value='选择要上传的照片' />" +
            "<button type=\"button\" class=\"am-btn am-btn-primary\" onClick=\"delFile(this)\"><span class=\"am-icon-trash-o\"></span> 删除</button></li>";
    $("#goOnFileDiv").append(addHtml);
  }
  function delFile(obj){
    $(obj).parent().remove();
  }
</script>
