<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="addForm" name="addForm" action="${pageContext.request.contextPath}/addUser/add.htm" method="post">
    <input type="hidden" id="parentId" name="parentId" value="0"/>
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
        <label>职务：</label>
        <span class="inline-select">
          <input type="hidden" id="userGroupId" name="userGroupId" />
          <select class="select-140" id="ugId" onchange="selectUserGroup()">
            <option value="">--请选择--</option>
            <c:forEach var="userGroup" items="${userGroupList}">
              <option value="${userGroup.id}_${userGroup.level}">${userGroup.name}</option>
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
    <table id="userTreeTable">
      <tr>
        <td width="85px" align="right">上级用户：</td>
        <td><ul id="userTree" class="easyui-tree"></ul></td>
      </tr>
    </table>
  </form>
</div>
<script>
  $('#userTree').tree({
    data:${userTree},
    lines: true,
    animate: true,
    checkbox: true,
    cascadeCheck: false,
    onSelect: function (node) {
      var cknodes = $('#userTree').tree("getChecked");
      for (var i = 0; i < cknodes.length; i++) {
        if (cknodes[i].id != node.id) {
          $('#userTree').tree("uncheck", cknodes[i].target);
        }
      }
      if (node.checked) {
        $('#userTree').tree('uncheck', node.target);

      } else {
        $('#userTree').tree('check', node.target);

      }
    },
    onLoadSuccess: function (node, data) {
      $(this).find('span.tree-checkbox').unbind().click(function () {
        $('#userTree').tree('select', $(this).parent());
        return false;
      });
    }
  });

  function sub(){
    if($("#zzCode").val() == ""){
      alert("请填写ZZ号");
      return false;
    }
    if($("#name").val() == ""){
      alert("请填写姓名");
      return false;
    }
    if($("#userGroupId").val() == ""){
      alert("请选择职务");
      return false;
    }

    var nodes = $('#userTree').tree("getChecked");
    if(nodes.length > 0){
      $("#parentId").val(nodes[0].id);
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/addUser/add.htm",
      data:$('#addForm').serialize(),
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

  function selectUserGroup(){
    var userGroupIdLevel = $("#ugId").val();
    if(userGroupIdLevel == ""){
      $("#userGroupId").val("");
    }else{
      var userGroupId = userGroupIdLevel.split("_")[0];
      var level = userGroupIdLevel.split("_")[1];
      $("#userGroupId").val(userGroupId);

      if(level == 0){
        $("#userTreeTable").hide();
      }else{
        $("#userTreeTable").show();
      }
    }
  }
</script>
