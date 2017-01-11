<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <p class="tit" style="text-align:center;">拜访记录更新排名</p>
    <a href="${pageContext.request.contextPath}/findInterviewTotalForApp/open.htm" class="back icon"></a>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="tabs-select">
      <ul>
        <form id="searchForm" name="searchForm" action="${pageContext.request.contextPath}/findCustomerForInterviewCountByUserIdForApp/find.htm" method="post">
          <c:if test="${loginLevel == 0}">
            <li><a href="javascript:;" onclick="changeUserLevel(1)"><c:if test="${empty param.areaName}">区域代理</c:if><c:if test="${!empty param.areaName}">${param.areaName}</c:if><i class="arr-bt"></i></a></li>
            <li><a href="javascript:;" onclick="changeUserLevel(2)"><c:if test="${empty param.provName}">省级代理</c:if><c:if test="${!empty param.provName}">${param.provName}</c:if><i class="arr-bt"></i></a></li>
            <li><a href="javascript:;" onclick="changeUserLevel(3)"><c:if test="${empty param.businessName}">所有业务员</c:if><c:if test="${!empty param.businessName}">${param.businessName}</c:if><i class="arr-bt"></i></a></li>
          </c:if>
          <c:if test="${loginLevel == 1}">
            <li><a href="javascript:;" onclick="changeUserLevel(2)"><c:if test="${empty param.provName}">省级代理</c:if><c:if test="${!empty param.provName}">${param.provName}</c:if><i class="arr-bt"></i></a></li>
            <li><a href="javascript:;" onclick="changeUserLevel(3)"><c:if test="${empty param.businessName}">所有业务员</c:if><c:if test="${!empty param.businessName}">${param.businessName}</c:if><i class="arr-bt"></i></a></li>
          </c:if>
          <c:if test="${loginLevel == 2}">
            <li><a href="javascript:;" onclick="changeUserLevel(3)"><c:if test="${empty param.businessName}">所有业务员</c:if><c:if test="${!empty param.businessName}">${param.businessName}</c:if><i class="arr-bt"></i></a></li>
          </c:if>
          <input type="hidden" id="userId" name="userId" />
          <input type="hidden" id="flag" name="flag" />
          <input type="hidden" id="areaId" name="areaId" value="${param.areaId}"/>
          <input type="hidden" id="provId" name="provId" value="${param.provId}"/>
          <input type="hidden" id="businessId" name="businessId" value="${param.businessId}"/>
          <input type="hidden" id="areaName" name="areaName" value="${param.areaName}"/>
          <input type="hidden" id="provName" name="provName" value="${param.provName}"/>
          <input type="hidden" id="businessName" name="businessName" value="${param.businessName}"/>
        </form>
      </ul>
    </div>
    <div class="rank-list b0">
      <ul>
        <c:if test="${empty list}">
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
        <c:if test="${!empty list}">
          <c:forEach items="${list}" var="json" >
            <li>
              <div class="content">
                <a href="${pageContext.request.contextPath}/findInterviewByWhereForApp/openFindByOther.htm?customerId=${json.id}&search=do">
                  <div class="cell-m-1">
                    <span class="time">
                      ${fn:substring(json.createTime, 0, 10)}
                    </span>
                  </div>
                  <div class="cell-m-2">
                    <div class="text">
                      <p>${json.name}</p>
                    </div>
                  </div>
                  <div class="cell-m-3">
                    <span class="snm">${json.count}</span>
                  </div>
                </a>
              </div>
            </li>
          </c:forEach>
        </c:if>
      </ul>
    </div>
  </div>
  <div id="changeUser" class="layer-trans">
    <div class="pop-content">
      <div class="pop-list">
        <ul id="userText">
        </ul>
      </div>
    </div>
  </div>
</section>
</body>
</html>
<script>
  $(function(){
    $("#changeUser").hide();
  });

  function changeUserLevel(flag){
    var html = "";
    html += "<li>";
    html += "<a href=\"javascript:;\" onclick=\"changeUser('', '')\">";
    html += "全部";
    html += "</a>";
    html += "</li>";
    if(flag == 1){
      <c:forEach var="user" items="${areaUserList}">
        html += "<li>";
        html += "<a href=\"javascript:;\" onclick=\"changeUser(${user.id}, '${user.name}')\">";
        html += "${user.name}";
        html += "</a>";
        html += "</li>";
      </c:forEach>
      $("#userText").html(html);
    }
    if(flag == 2){
      <c:forEach var="user" items="${provUserList}">
        html += "<li>";
        html += "<a href=\"javascript:;\" onclick=\"changeUser(${user.id}, '${user.name}')\">";
        html += "${user.name}";
        html += "</a>";
        html += "</li>";
      </c:forEach>
      $("#userText").html(html);
    }
    if(flag == 3){
      <c:forEach var="user" items="${businessUserList}">
        html += "<li>";
        html += "<a href=\"javascript:;\" onclick=\"changeUser(${user.id}, '${user.name}')\">";
        html += "${user.name}";
        html += "</a>";
        html += "</li>";
      </c:forEach>
      $("#userText").html(html);
    }
    $("#changeUser").show();
    $("#flag").val(flag);
  }

  function changeUser(id, name){

    if($("#flag").val() == 1){
      $("#areaId").val(id);
      $("#provId").val("");
      $("#businessId").val("");
      $("#areaName").val(name);
      $("#provName").val("");
      $("#businessName").val("");
    }
    if($("#flag").val() == 2){
      $("#provId").val(id);
      $("#businessId").val("");
      $("#provName").val(name);
      $("#businessName").val("");
    }
    if($("#flag").val() == 3){
      $("#businessId").val(id);
      $("#businessName").val(name);
    }
    if($("#areaId").val() == "" && $("#provId").val() == "" && $("#businessId").val() == ""){
      $("#userId").val(id);
    }else{
      if($("#businessId").val() != ""){
        $("#userId").val($("#businessId").val());
      }else{
        if($("#provId").val() != ""){
          $("#userId").val($("#provId").val());
        }else{
          $("#userId").val($("#areaId").val());
        }
      }
    }
    $("#searchForm").submit();
  }
</script>
