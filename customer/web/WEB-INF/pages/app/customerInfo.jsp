<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <title>客户拜访</title>
  <%@ include file="common/taglibsForApp.jsp"%>
  <style>section{padding-top:44px;}</style>
</head>
<body>
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">客户详情</p>
      <a class="back icon" href="${pageContext.request.contextPath}/findCustomerByWhereForApp/find.htm"></a>
      <span class="next">
        <a href="${pageContext.request.contextPath}/editCustomerForApp/open.htm?id=${customer.id}"><i class="i-edit"></i></a>
      </span>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <div class="title-bgr">基本资料</div>
        <ul class="detail-info">
          <li>
            <div class="content">
              <div class="col-tg">学校名称：</div>
              <div class="col-txt">${customer.name}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">客户代码：</div>
              <div class="col-txt">${customer.code}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">办学规模：</div>
              <div class="col-txt">${customer.scale}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">所在省份：</div>
              <div class="col-txt">${area.name}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">详细地址：</div>
              <div class="col-txt">${customer.address}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">客户类型：</div>
              <div class="col-txt">${customerType.name}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">客户状态：</div>
              <div class="col-txt">${customerState.name}</div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">注册人数：</div>
              <div class="col-txt" id="${customer.no}"></div>
            </div>
          </li>
        </ul>
        <div class="title-bgr">联系人信息</div>
        <ul class="adm-visit-list">
          <c:forEach var="linkman" items="${linkmanList}" varStatus="status">
            <li>
              <div class="title">
                <a href="javascript:;">${linkman.name}</a>
              </div>
              <div class="info-list">
                <p>职务：${linkman.post}</p>
                <p>手机：${linkman.phone}</p>
                <p>QQ：${linkman.qq}</p>
                <p>特点：${linkman.trait}</p>
              </div>
              <div class="opr-tl">
                <a class="btn-edit" href="${pageContext.request.contextPath}/findInterviewByUserIdForApp/find.htm?customerId=${customer.id}&linkmanId=${linkman.id}">查看记录</a> &nbsp;
                <a class="btn-edit" href="${pageContext.request.contextPath}/addInterviewForApp/open.htm?customerId=${customer.id}&linkmanId=${linkman.id}">新增记录</a>
              </div>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </section>
</body>
</html>
<script>
  $(function(){
    var no = "${customer.no}";
    if(no != "" && no.length > 0){
      $.ajax({
        url:"${pageContext.request.contextPath}/findCustomerByWhere/findNoCount.htm",
        method : 'POST',
        async:false,
        data:{"no":no},
        success:function(data){
          $("#"+no).html(data);
        }
      });
    }
  });
</script>
