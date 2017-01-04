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
  <script src="${pageContext.request.contextPath}/app/mobiscroll/js/mobiscroll.core-2.5.2.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/app/mobiscroll/js/mobiscroll.core-2.5.2-zh.js" type="text/javascript"></script>
  <link href="${pageContext.request.contextPath}/app/mobiscroll/css/mobiscroll.core-2.5.2.css" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/app/mobiscroll/css/mobiscroll.animation-2.5.2.css" rel="stylesheet" type="text/css" />
  <script src="${pageContext.request.contextPath}/app/mobiscroll/js/mobiscroll.datetime-2.5.1.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/app/mobiscroll/js/mobiscroll.datetime-2.5.1-zh.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/app/mobiscroll/js/mobiscroll.android-ics-2.5.2.js" type="text/javascript"></script>
  <link href="${pageContext.request.contextPath}/app/mobiscroll/css/mobiscroll.android-ics-2.5.2.css" rel="stylesheet" type="text/css" />
  <style>
    section{padding-top:44px;}
  </style>
</head>
<body>
<header>
  <div class="header w">
    <p class="tit" style="text-align:center;">${user.name}学校拜访记录</p>
    <a href="${pageContext.request.contextPath}/findInterviewTotalForApp/open.htm" class="back icon"></a>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="month-select" style="text-align:center">
      <a href="javascript:;" onclick="changeDate()">${param.year}年${param.month}月拜访记录排行<i class="arr-bt"></i></a>
      <label id="appDateLabel" for="appDate"></label>
      <input type="text" name="appDate" id="appDate" size="1" style="width: 1px;" />
    </div>
    <div class="adm-visit-list">
      <ul>
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
              <div class="title">
                <a href="javascript:;">${interview.cName}-${interview.clName}</a>
              </div>
              <div class="text">
                <p>${interview.content}</p>
                  <p class="by"><i class="i-lac"></i>${interview.uName} ${interview.operateTime} ${interview.address}</p>
              </div>
              <div class="opr-tl"><a class="btn-edit" href="${pageContext.request.contextPath}/editInterviewForApp/open.htm?id=${interview.id}">编辑</a></div>
            </li>
          </c:forEach>
        </c:if>
      </ul>
    </div>
  </div>
</section>
</body>
</html>
<script>
  $(function() {
    var currYear = (new Date()).getFullYear();
    var opt = {};
    opt.date = {
      preset: 'date'
    };
    opt.datetime = {
      preset: 'datetime'
    };
    opt.time = {
      preset: 'time'
    };
    opt.default = {
      theme: 'android-ics light', //皮肤样式
      display: 'bottom', //显示方式
      mode: 'scroller', //日期选择模式
      lang: 'zh',
      startYear: currYear - 10, //开始年份
      endYear: currYear + 10, //结束年份
      dateFormat: 'yyyymm',
      dateOrder: 'yymm',
      onSelect:function(valueText,inst){
        var year = valueText.substring(0, 4);
        var month = valueText.substring(4, 6);
        location.href = "${pageContext.request.contextPath}/findInterviewByWhereForApp/openFindByUserIdAndMonth.htm?userId=${param.userId}&year="+year+"&month="+month;
      }
    };
    $("#appDate").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
  });
  function changeDate(){
    $("#appDateLabel").click();
  }
</script>