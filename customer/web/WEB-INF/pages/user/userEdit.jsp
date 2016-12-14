<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="editForm" name="editForm" action="${pageContext.request.contextPath}/editUser/editor.htm" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    <input type="hidden" id="parentId" name="parentId" value="0"/>
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
      <input type="hidden" id="userGroupId" name="userGroupId" value="${userGroupId}" />
      <c:if test="${level eq '0' || !isEditMe}">
        <li>
          <label>职务：</label>
          <span class="inline-select">
            <select class="select-140" id="ugId" onchange="selectUserGroup()">
              <option value="">--请选择--</option>
              <c:forEach var="userGroup" items="${userGroupList}">
                  <option value="${userGroup.id}_${userGroup.level}" <c:if test="${userGroupId == userGroup.id}">selected="selected"</c:if>>${userGroup.name}</option>
              </c:forEach>
            </select>
          </span>
        </li>
      </c:if>
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
<c:if test="${!empty userTree}">
  <table id="userTreeTable">
    <tr>
      <td width="85px" align="right">上级用户：</td>
      <td><ul id="userTree" class="easyui-tree"></ul></td>
    </tr>
  </table>
</c:if>
<script>
  <c:if test="${!empty userTree}">
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
  </c:if>

  <c:if test="${user.level eq '0'}">
    $("#userTreeTable").hide();
  </c:if>

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
    <c:if test="${user.level > 0 && !empty userTree}">
      var nodes = $('#userTree').tree("getChecked");
      if(nodes.length > 0){
        $("#parentId").val(nodes[0].id);
      }
    </c:if>

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
