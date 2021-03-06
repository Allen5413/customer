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
    <p class="tit" style="text-align:center;">拜访详情</p>
    <a class="back icon" href="${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm"></a>
    <span class="next">
      <c:if test="${loginZzCode eq interview.creator && !isPassOneDay}">
        <a href="${pageContext.request.contextPath}/editInterviewForApp/open.htm?id=${interview.id}"><i class="i-edit"></i></a>
      </c:if>
    </span>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="visit-info-mod" onclick="location.href='${pageContext.request.contextPath}/findInterviewByUserIdForApp/find.htm?customerId=${customer.id}&linkmanId=${customerLankman.id}'">
      <div class="mod-info">
        <div class="title">
          <a href="javascript:;">${customer.name}-${customerLankman.name}</a>
        </div>
        <div class="text">
          <p>${interview.content}</p>
          <p class="by"><i class="i-lac"></i>${user.name} ${interview.operateTime} ${interview.address}</p>
        </div>
      </div>
      <c:if test="${!empty interviewFileList}">
        <div class="title">附件</div>
          <ul class="annex-list">
            <c:forEach var="interviewFile" items="${interviewFileList}">
            <li>
              <div class="content">
                <img src="${pageContext.request.contextPath}${interviewFile.url}">
              </div>
            </li>
            </c:forEach>
          </ul>
        </div>
      </c:if>
    </div>
  </div>
</section>
</body>
</html>