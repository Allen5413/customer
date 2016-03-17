<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="editForm" method="post">
  <input type="hidden" name="id" value="${userGroup.id}" />
  <input type="hidden" name="version" value="${userGroup.version}" />
  <input type="hidden" name="resourceIds" id="resourceIds" />
  <div class="main-content">
    <ul class="create_info_list">
      <li>
        <label class="lab_80">用户组名称：</label>
        <input type="text" class="input_240" name="name" value="${userGroup.name}" maxlength="50" />
      </li>
      <li>
        <label class="lab_80 left">说明：</label>
        <textarea class="textArea left" name="remark" maxlength="500" style="width: 245px;">${userGroup.remark}</textarea>
        <div class="clear"></div>
      </li>
      <li>
        <label class="lab_80 left">关联资源：</label>
      </li>
    </ul>
  </div>
  <div class="easyui-layout" data-options="fit:true, border:false" style="padding-left: 50px; overflow-y:scroll; height: 280px;">
    <ul id="menuTree" checkbox="true" ></ul>
  </div>
  <br /><br />
  <div class="main-content" style="padding-left: 50px;">
    <ul>
      <li>
        <label class="lab_80 left">
          <a class="com_btn_b" href="#" onclick="sub()"><span>提&nbsp;交</span></a>
        </label>
      </li>
    </ul>
  </div>
</form>
<script>
  function sub(){
    $.ajax({
      cache: true,
      type: "POST",
      url:"/editUserGroup/userGroupEdit.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          $('#index_iframe').contents().find("#subBut").click();
          closeDialog();
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>