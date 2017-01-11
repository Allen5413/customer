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
    <p class="tit" style="text-align:center;">${customer.name}-${customerLankman.name}</p>
    <a class="back icon" href="${pageContext.request.contextPath}/findCustomerByIdForApp/open.htm?id=${customer.id}"></a>
    <span class="next"><a href="${pageContext.request.contextPath}/addInterviewForApp/open.htm?customerId=${customer.id}&linkmanId=${customerLankman.id}"><i class="i-add"></i></a></span>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="adm-select-list">
      <ul class="detail-info">
        <li>
          <div class="content">
            <div class="col-tg">学校名称：</div>
            <div class="col-txt">${customer.name}</div>
          </div>
        </li>
        <li>
          <div class="content">
            <div class="col-tg">职务：</div>
            <div class="col-txt">${customerLankman.post}</div>
          </div>
        </li>
        <li>
          <div class="content">
            <div class="col-tg">手机：</div>
            <div class="col-txt">${customerLankman.phone}</div>
          </div>
        </li>
        <li>
          <div class="content">
            <div class="col-tg">QQ：</div>
            <div class="col-txt">${customerLankman.qq}</div>
          </div>
        </li>
        <li>
          <div class="content">
            <div class="col-tg">特点：</div>
            <div class="col-txt">${customerLankman.trait}</div>
          </div>
        </li>
      </ul>
      <c:if test="${empty interviewList}">
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
      <c:if test="${!empty interviewList}">
        <ul class="adm-visit-list">
          <c:forEach var="interview" items="${interviewList}">
            <li>
              <div class="title">
                <a href="javascript:;">${customerLankman.name}</a>
              </div>
              <div class="text" onclick="location.href='${pageContext.request.contextPath}/findInterviewByIdForApp/find.htm?id=${interview.id}'">
                <p><a href="javascript:;">${interview.content}</a></p>
                <p class="by"><i class="i-lac"></i>${interview.uName} ${interview.operateTime} ${interview.address}</p>
              </div>
              <div class="opr-tl">
                <c:if test="${loginZzCode eq interview.creator && !interview.isPassOneDay}">
                  <a class="btn-edit" href="${pageContext.request.contextPath}/editInterviewForApp/open.htm?id=${interview.id}">编辑</a>
                </c:if>
              </div>
            </li>
          </c:forEach>
        </ul>
      </c:if>
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
