<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="4%" >序号</th>
      <th width="4%">学校No</th>
      <th width="10%">客户名称</th>
      <th width="10%">所在省份</th>
      <th width="10%">地址</th>
      <th width="8%">办学规模</th>
      <th width="5%">客户代码</th>
      <th width="5%">客户类型</th>
      <th width="5%">客户状态</th>
      <th width="20%">备注</th>
      <th width="5%">操作人</th>
      <th width="14%">操作时间</th>
    </tr>
    <c:forEach var="customerLog" items="${customerLogList}" varStatus="status">
      <tr>
        <td>${status.index+1}</td>
        <td>${customerLog.no}</td>
        <td>${customerLog.name}</td>
        <td>${customerLog.aName}</td>
        <td>${customerLog.address}</td>
        <td>${customerLog.scale}</td>
        <td>${customerLog.code}</td>
        <td>${customerLog.tName}</td>
        <td>${customerLog.sName}</td>
        <td>${customerLog.remark}</td>
        <td>${customerLog.operatorName}</td>
        <td>${customerLog.operateTime}</td>
      </tr>
    </c:forEach>
  </table>
</div>