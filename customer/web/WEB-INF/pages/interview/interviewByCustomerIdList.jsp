<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <ul class="fill_form" id="basicInfo">
    <li>
      <label style="font-size: 14px; font-weight: bold">客户名称：</label>${customer.name}&nbsp;&nbsp;&nbsp;&nbsp;
      <label style="font-size: 14px; font-weight: bold">学校No：</label>${customer.no}&nbsp;&nbsp;&nbsp;&nbsp;
      <label style="font-size: 14px; font-weight: bold">客户经理：</label>${user.name}
    </li>
  </ul>
  <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="5%" >序号</th>
      <th width="7%">客户经理</th>
      <th width="20%">客户名称</th>
      <th width="7%">交谈对象</th>
      <th width="5%">客户状态</th>
      <th width="5%">客户类型</th>
      <th width="36%">交谈记录</th>
      <th width="15%">最后修改日期</th>
    </tr>
    <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
      <tr>
        <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
      </tr>
    </c:if>
    <c:forEach var="interview" items="${pageInfo.pageResults}" varStatus="status">
      <tr onclick="changeTR(this)">
        <td align="center">${status.index+1}</td>
        <td>${interview.uName}</td>
        <td>${interview.cName}</td>
        <td>${interview.clName}</td>
        <td>${interview.csName}</td>
        <td>${interview.ctName}</td>
        <td>${interview.content}</td>
        <td>${interview.operateTime}</td>
      </tr>
    </c:forEach>
  </table>
</div>
