<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
    <ul class="fill_form">
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
    <ul class="fill_form border-top">
      <c:forEach var="linkman" items="${linkmanList}" varStatus="status">
        <li>
          <p>
            <span class="w-240"><label>联系人姓名：</label>${linkman.name}</span>
            <span class="w-240"><label>联系电话：</label>${linkman.phone}</span>
          </p>
          <p>
            <span class="w-240"><label>职务：</label>${linkman.post}</span>
            <span class="w-240"> <label>备注：</label>${linkman.remark}</span>
          </p>
          <p>
            <span class="w-240"><label>QQ：</label>${linkman.qq}</span>
            <span class="w-240"> <label>特点：</label>${linkman.trait}</span>
          </p>
        </li>
      </c:forEach>
    </ul>
    <table class="table_slist" cellpadding="0" cellspacing="0" width="100%">
      <tr>
        <th width="10%">客户经理</th>
        <th width="10%">交谈对象</th>
        <th width="65%">交谈记录</th>
        <th width="15%">最后修改日期</th>
      </tr>
      <c:if test="${empty interviewList}">
        <tr>
          <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
        </tr>
      </c:if>
      <c:forEach var="interview" items="${interviewList}" varStatus="status">
        <tr onclick="changeTR(this)">
          <td>${interview.uName}</td>
          <td>${interview.clName}</td>
          <td>${interview.content}</td>
          <td>${interview.operateTime}</td>
        </tr>
      </c:forEach>
    </table>
</div>