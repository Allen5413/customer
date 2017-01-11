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
    <p class="tit" style="text-align:center;">拜访记录</p>
    <span class="next">
      <i class="i-add" onclick="addDiv(0)"></i>
    </span>
    <div id="addDiv" class="t-more-list" style="display: none;">
      <span class="arr"></span>
      <a href="${pageContext.request.contextPath}/addInterviewForApp/open.htm">新增</a>
      <a href="${pageContext.request.contextPath}/findInterviewByWhereForApp/openFindByOther.htm">筛选</a>
    </div>
  </div>
</header>
<section onclick="addDiv(1)">
  <div class="auto w bg-f">
    <div class="adm-visit-list">
      <ul id="contentLi">
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
          <c:forEach var="interview" items="${interviewList}">
            <li>
              <div class="title" onclick="location.href='${pageContext.request.contextPath}/findInterviewByUserIdForApp/find.htm?customerId=${interview.cId}&linkmanId=${interview.clId}'">
                <a href="javascript:;">${interview.cName}-${interview.clName}</a>
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
        </c:if>
      </ul>
    </div>
  </div>
</section>
<footer>
  <div class="footer">
    <ul class="ul w">
      <li class="li">
        <a href="${pageContext.request.contextPath}/findCustomerByWhereForApp/find.htm">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-1.png"></p>
          <p class="p-2">客户管理</p>
        </a>
      </li>
      <li class="li on">
        <a href="javascript:void(0);">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-2-s.png"></p>
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
<form id="pageForm" name="pageForm" method="post" action="${pageContext.request.contextPath}/findInterviewByWhereForApp/findPage.htm">
  <input type="hidden" id="pageNo" name="pageNo" value=""/>
  <input type="hidden" id="pageCount" name="pageCount" value="5"/>
</form>
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

  $(window).scroll(function () {
    loadData();
  });

  var pageNo = 2;
  function loadData() {
    totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
    if ($(document).height() <= totalheight) {
      //下啦加载时运行
      $("#pageNo").val(pageNo);
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/findInterviewByWhereForApp/findPage.htm",
        data:$("#pageForm").serialize(),
        async: false,
        success: function(data) {
          if(data.state == 0){
            if(data.interviewList.length > 0){
              var html = $("#contentLi").html();

              for(var i=0; i<data.interviewList.length; i++){
                var interview = data.interviewList[i];
                html += "<li>";
                html += "<div class='title' onclick='location.href=${pageContext.request.contextPath}/findInterviewByUserIdForApp/find.htm?customerId="+interview.cId+"&linkmanId="+interview.clId+"'>";
                html += "<a href='javascript:;'>"+interview.cName+"-"+interview.clName+"</a>";
                html += "</div>";
                html += "<div class='text' onclick='location.href=${pageContext.request.contextPath}/findInterviewByIdForApp/find.htm?id="+interview.id+"'>";
                html += "<p><a href=‘javascript:;’>"+interview.content+"</a></p>";
                html += "<p class='by'><i class='i-lac'></i>"+interview.uName+" "+interview.operateTime+" "+interview.address+"</p>";
                html += "</div>";
                html += "<div class='opr-tl'>";
                <c:if test="${loginZzCode eq interview.creator && !interview.isPassOneDay}">
                  html += "<a class='btn-edit' href='${pageContext.request.contextPath}/editInterviewForApp/open.htm?id="+interview.id+"'>编辑</a>";
                </c:if>
                html += "</div>";
                html += "</li>";
              }
              $("#contentLi").html(html);
              pageNo++;
            }
          }else{
            alert(data.msg);
          }
        }
      });
    }
  }
</script>
