<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <!-- Highcharts -->
  <%@ include file="common/taglibsForApp.jsp"%>
  <style>section{padding-top:44px;}</style>
</head>
<body>
<header>
  <div class="header w">
    <p class="tit" style="text-align:center;">数据统计</p>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="data-show-pic">
      <p>
        <span class="ln-m red"><i class="itg"></i>拜访客户总数${json.customerTotal}</span>
        <span class="ln-m blue"><i class="itg"></i>拜访记录总数${json.interviewTotal}</span>
      </p>
      <div class="month-select" style="text-align:center">
        <a href="javascript:;" onclick="changeDate()" id="yearText">${showYear}年拜访记录<i class="arr-bt"></i></a>
        <label id="appDateLabel" for="appDate"></label>
        <input type="text" name="appDate" id="appDate" size="1" style="width: 1px;" />
      </div>
      <div class="mod-curve" id="totalPic" style="height: 300px;"></div>
      <div class="mod-curve" id="totalPic2" style="height: 300px;"></div>
      <div class="mod-curve" id="totalPic3" style="height: 300px;"></div>
    </div>
    <div class="month-select">
      <a class="turn-l" href="javascript:;" onclick="changeTotalUserCount(0)"><i class="arr-l"></i></a>
      <div class="txt"><p id="userCountTxt">${userCountYear}年${month}月拜访记录排行</p></div>
      <a class="turn-r" href="javascript:;" onclick="changeTotalUserCount(1)"><i class="arr-r"></i></a>
    </div>
    <form name="totalForm" id="totalForm" action="${pageContext.request.contextPath}/findInterviewTotalForApp/open.htm" method="get">
      <input type="hidden" id="year" name="year" value="${year}" />
      <input type="hidden" id="userCountYear" name="userCountYear" value="${userCountYear}"/>
      <input type="hidden" id="month" name="month" value="${month}"/>
      <input type="hidden" id="num" name="num" value="${num}"/>
      <input type="hidden" id="flag" name="flag" value="${flag}"/>
    </form>
    <div class="rank-list">
      <ul id="userCountUL">
        <c:if test="${empty useCountList}">
          <div class='null-tips'>
            <p>对不起，没有找到相关的记录</p>
          </div>
        </c:if>
        <c:if test="${!empty useCountList}">
          <c:forEach var="useCount" items="${useCountList}" varStatus="status" >
            <li>
              <div class="content">
                <a href="${pageContext.request.contextPath}/findInterviewByWhereForApp/openFindByUserIdAndMonth.htm?userId=${useCount.id}&year=${year}&month=${month}">
                  <div class="cell-1">
                    <div class="num">${status.index + 1}</div>
                  </div>
                  <div class="cell-2">
                    <div class="text">
                      <p>${useCount.name}</p>
                    </div>
                  </div>
                  <div class="cell-3">
                    <span class="snm">${useCount.count}</span>
                  </div>
                </a>
              </div>
            </li>
          </c:forEach>
        </c:if>
      </ul>
    </div>
    <div class="set-link">
      <ul>
        <li><a href="${pageContext.request.contextPath}/findCustomerForInterviewCountByUserIdForApp/find.htm">拜访记录更新排行</a></li>
        <li><a href="javascript:;" onclick="logout()">退出登录</a></li>
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
      <li class="li">
        <a href="${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-2.png"></p>
          <p class="p-2">拜访记录</p>
        </a>
      </li>
      <li class="li on">
        <a href="javascript:void(0);">
          <p class="p-1"><img src="${pageContext.request.contextPath}/app/images/ico-nav-3-s.png"></p>
          <p class="p-2">数据统计</p>
        </a>
      </li>
    </ul>
  </div>
</footer>
</body>
</html>
<script>
  $(function(){
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
        dateFormat: 'yyyy',
        dateOrder: 'yy',
        onSelect:function(valueText,inst){
          $("#year").val(valueText);
          $("#flag").val("1");
          $("#totalForm").submit();
        }
      };
      $("#appDate").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
    });
    totalPic();
    totalPic2();
  });
  function totalPic(){
    var month12 = [];
    <c:if test="${'0' eq flag}">
      <c:forEach var="month2" items="${month12}">
        month12.push("${fn:substring(month2, 5, fn:length(month2))}月");
      </c:forEach>
    </c:if>
    <c:if test="${'1' eq flag}">
      for(var i = 1; i<=12; i++){
        month12.push(i+"月");
      }
    </c:if>
    $('#totalPic').highcharts({
      hart: {
        type: 'line'
      },
      title: {
        text: ''
      },
      xAxis: {
        categories: month12
      },
      yAxis: {
        title: {
          text: '客户类型统计'
        }
      },
      credits: {
        text: '',
        href: ''
      },
      plotOptions: {
        line: {
          dataLabels: {
            enabled: true
          },
          enableMouseTracking: false
        }
      },
      series: [{
        name: '拜访客户',
        data: [
                <c:forEach items="${json2.customerTotalForMonth}" var="obj" varStatus="status">
                  ${obj}
                  <c:if test="${status.index < 12}">
                    ,
                  </c:if>
                </c:forEach>
              ],
        color: '#f60'
      }, {
        name: '拜访记录',
        data: [
                <c:forEach items="${json2.interviewTotalForMonth}" var="obj" varStatus="status">
                  ${obj}
                  <c:if test="${status.index < 12}">
                    ,
                  </c:if>
                </c:forEach>
              ],
        color: '#09f'
      }]
    });
  }

  function totalPic2(){
    $('#totalPic2').highcharts({
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
      },
      title: {
        text: ''
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f} %</b>'
      },
      credits: {
        text: '',
        href: ''
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: false
          },
          showInLegend: true
        }
      },
      series: [{
        type: 'pie',
        name: '',
        data: [
                <c:set var="csLength" value="${fn:length(csCountList)}" />
                <c:forEach items="${csCountList}" var="csCount" varStatus="status">
                  ['${csCount.name}', ${csCount.countPoint}]
                  <c:if test="${status.index < csLength}">
                    ,
                  </c:if>
                </c:forEach>
              ]
      }]
    });

    $('#totalPic3').highcharts({
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
      },
      title: {
        text: ''
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
      },
      credits: {
        text: '',
        href: ''
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: false
          },
          showInLegend: true
        }
      },
      series: [{
        type: 'pie',
        name: '',
        data: [
                <c:set var="ctLength" value="${fn:length(ctCountList)}" />
                <c:forEach items="${ctCountList}" var="ctCount" varStatus="status">
                  ['${ctCount.name}', ${ctCount.countPoint}]
                  <c:if test="${status.index < ctLength}">
                    ,
                  </c:if>
                </c:forEach>
              ]
      }]
    });
  }

  function changeTotalUserCount(flag){
    var year = $("#userCountYear").val();
    var month = $("#month").val();
    if(flag == 0){
      if(Number(month) == 1){
        $("#userCountYear").val(Number(year)-1);
        $("#month").val(12);
      }else{
        $("#month").val(Number(month)-1);
      }
    }else{
      if(Number(month) == 12){
        $("#userCountYear").val(Number(year)+1);
        $("#month").val(1);
      }else{
        $("#month").val(Number(month)+1);
      }
    }
    $("#totalForm").submit();
  }

  function changeDate(){
    $("#appDateLabel").click();
  }

  function logout(){
    location.href = "${pageContext.request.contextPath}/logoutUser/logouApp.htm";
  }
</script>
