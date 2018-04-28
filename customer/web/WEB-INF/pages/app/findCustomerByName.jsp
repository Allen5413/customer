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
    <p class="tit" style="text-align:center;">客户查询</p>
    <a class="back icon" href="${pageContext.request.contextPath}/findCustomerByWhereForApp/find.htm"></a>
  </div>
</header>
<section>
  <form id="form" name="form" action="${pageContext.request.contextPath}/findCustomerByWhereForApp/findByName.htm" method="get">
    <input type="hidden" name="search" value="do" />
    <div class="auto w bg-f">
      <div class="search">
        <div class="ss-text">
          <div class="ico-cl"><i class="i-ss"></i></div>
          <div class="text-cl"><input type="text" placeholder="输入关键字" name="name" value="${param.name}"></div>
        </div>
      </div>
      <c:if test="${param.search eq 'do'}">
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
                <li onclick="location.href='${pageContext.request.contextPath}/findCustomerByIdForApp/open.htm?id=${customer.id}'">
                  <div class="content">
                    <div class="col-txt">${customer.cName}</div>
                    <div class="col-r"><a href="javascript:;">${customer.csName} <i class="i-arr"></i></a></div>
                  </div>
                </li>
              </c:forEach>
            </c:if>
          </ul>
        </div>
      </c:if>
    </div>
  </form>
</section>
</body>
</html>
<script type="text/javascript">
  $(function(){
    //回车事件
    document.onkeydown = function(e){
      var ev = document.all ? window.event : e;
      if(ev.keyCode==13) {
        $("#name").val(encodeURI($("#name").val()));
        $("#form").submit();
      }
    }
  });
</script>