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
        </li>
      </c:forEach>
    </ul>

</div>
</body>
</html>