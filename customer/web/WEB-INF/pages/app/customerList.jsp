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
  <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-1.9.1.js" charset="utf-8"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/css/common.css"  />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/css/style.css"  />
  <style>section{padding-top:44px;}</style>
</head>
<body>
<header>
  <div class="header w">
    <p class="tit" style="text-align:center;">客户管理</p>
      <span class="next">
        <a href="${pageContext.request.contextPath}/findCustomerByWhereForApp/findByName.htm"><i class="i-ss"></i></a>&nbsp;
        <i class="i-add" onclick="addDiv(0)"></i>
      </span>
      <div id="addDiv" class="t-more-list" style="display: none;">
        <span class="arr"></span>
        <a href="${pageContext.request.contextPath}/addCustomerForApp/open.htm">新增</a>
        <a href="${pageContext.request.contextPath}/findCustomerByWhereForApp/openFindByOther.htm">筛选</a>
      </div>
  </div>
</header>
<section onclick="addDiv(1)">
  <div class="auto w bg-f">
    <div class="adm-log-list">
      <ul>
        <c:if test="${empty customerList}">
          <section>
            <div class="auto w bg-f">
              <div class="adm-log-list">
                <div class="null-tips">
                  <div class="tips-pic"><img src="${pageContext.request.contextPath}/app/images/null-tips.png"></div>
                  <p>对不起，没有找到相关的记录</p>
                </div>
              </div>
            </div>
          </section>
        </c:if>
        <c:if test="${!empty customerList}">
          <c:forEach var="customer" items="${customerList}">
            <li>
              <div class="content">
                <div class="col-txt">${customer.cName}</div>
                <div class="col-r"><a href="${pageContext.request.contextPath}/findCustomerByIdForApp/open.htm?id=${customer.id}">${customer.csName} <i class="i-arr"></i></a></div>
              </div>
            </li>
          </c:forEach>
        </c:if>
      </ul>
    </div>
  </div>
</section>
<footer>
  <div class="footer">
    <ul class="ul w">
      <li class="li on">
        <a href="javascript:;">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-1-s.png"></p>
          <p class="p-2">客户管理</p>
        </a>
      </li>
      <li class="li">
        <a href="${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-2.png"></p>
          <p class="p-2">拜访记录</p>
        </a>
      </li>
      <li class="li">
        <a href="${pageContext.request.contextPath}/findInterviewTotalForApp/open.htm">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-3.png"></p>
          <p class="p-2">数据统计</p>
        </a>
      </li>
    </ul>
  </div>
</footer>
</body>
</html>
<script>
  function addDiv(flag){
    if(flag == 0) {
      $("#addDiv").show();
    }else{
      $("#addDiv").hide();
    }
  }
</script>
