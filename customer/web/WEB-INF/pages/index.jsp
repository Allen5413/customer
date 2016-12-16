<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<body id="main_body">
<div id="page">
  <div id="header">
    <div class="logo_admin">
      <div class="logo"><h2>客户拜访记录系统</h2></div>
      <a class="quit_ico" href="${pageContext.request.contextPath}/logoutUser/logou.htm">退出</a>
    </div>
    <div class="barNews_left">
      <div class="barNews_right">
        <label class="right">
          <span class="icoTime">日期：${year}年${month}月${day}日 ${week}</span>
          <em class="hlr">&nbsp;</em>
          <span class="point">&nbsp;</span>
        </label>
        <a class="toHome" href="${pageContext.request.contextPath}/index/main.htm"></a>
      </div>
    </div>
  </div>
  <div id="centerAdmin">
    <div class="admin_leftMenu">
      <dl class="list-menu">
        <c:forEach var="menu" items="${menu}" varStatus="status">
          <dt lang="${status.index}" onclick="clickMenu(${status.index})">
            <a class="tOn" href="#">${menu.key}</a>
          </dt>
          <dd lang="${status.index}">
            <ul id="aa_${status.index}">
              <c:forEach var="resource" items="${menu.value}" varStatus="status2">
                <li <c:if test="${status.index == 0 && status2.index == 0}">class="tab_1 on"</c:if> onclick="clickResource('${pageContext.request.contextPath}${resource.url}', this, '${resource.name}')">
                  <a href="#">${resource.name}</a>
                </li>
              </c:forEach>
            </ul>
          </dd>
        </c:forEach>
      </dl>
    </div>
    <div class="admin_rightContent">
      <div class="content_main">
        <div class="arrow_btn">
        </div>
        <div class="mainContent">
          <div class="mainTab">
            <span class="opt_right">
              <a class="optPrev" href="#" onclick="prevTab()">&nbsp;</a>
              <a class="optNext" href="#" onclick="nextTab()">&nbsp;</a>
              <a class="optClose" href="#" onclick="closeTab()">&nbsp;</a>
            </span>
          </div>
          <div class="contain_blockBg">
            <div class="container" id="contentPage">

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="dialogDiv"></div>
</body>
</html>
<script>
  $(function(){
    $("#aa_0").find("li").first().click();
  });
</script>