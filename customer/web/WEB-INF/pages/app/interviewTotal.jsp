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
  <!-- Highcharts -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/script/Highcharts-4.2.5/js/highcharts.js" charset="utf-8"></script>
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
        <a href="javascript:;" onclick="changeDate()" id="yearText">${year}年拜访记录<i class="arr-bt"></i></a>
        <label id="appDateLabel" for="appDate"></label>
        <input type="text" name="appDate" id="appDate" size="1" style="width: 1px;" />
      </div>
      <div class="mod-curve" id="totalPic" style="height: 300px;"></div>
    </div>
    <div class="month-select">
      <a class="turn-l" href="javascript:;" onclick="changeTotalUserCount(0)"><i class="arr-l"></i></a>
      <div class="txt"><p id="userCountTxt">${year}年${month}月拜访记录排行</p></div>
      <a class="turn-r" href="javascript:;" onclick="changeTotalUserCount(1)"><i class="arr-r"></i></a>
      <input type="hidden" id="userCountYear" value="${year}"/>
      <input type="hidden" id="userCountMonth" value="${month}"/>
    </div>
    <div class="rank-list">
      <ul id="userCountUL">
      </ul>
    </div>
    <div class="set-link">
      <ul>
        <li><a href="${pageContext.request.contextPath}/findCustomerForInterviewCountByUserIdForApp/find.htm">拜访记录更新排行</a></li>
        <li><a href="${pageContext.request.contextPath}/logoutUser/logouApp.htm">退出登录</a></li>
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
          $("#yearText").html(valueText+"年拜访记录");
          totalPic(valueText);
        }
      };
      $("#appDate").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
    });
    totalPic(${year});
    totalUserCount();
  });
  function totalPic(year){
    var data = [];
    var data2 = [];
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/findInterviewTotalForApp/findYear.htm",
      data:{"year":year},
      async: false,
      success: function(result) {
        if(result.state == 0){
          for(var i=0; i<result.json.customerTotalForMonth.length; i++){
            data.push(result.json.customerTotalForMonth[i]);
            data2.push(result.json.interviewTotalForMonth[i]);
          }
        }
      }
    });
    $('#totalPic').highcharts({
      hart: {
        type: 'line'
      },
      title: {
        text: ''
      },
      xAxis: {
        categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
      },
      yAxis: {
        title: {
          text: ''
        }
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
        data: data,
        color: '#f60'
      }, {
        name: '拜访记录',
        data: data2,
        color: '#09f'
      }]
    });
  }

  function changeTotalUserCount(flag){
    var year = $("#userCountYear").val();
    var month = $("#userCountMonth").val();
    if(flag == 0){
      if(Number(month) == 1){
        $("#userCountYear").val(Number(year)-1);
        $("#userCountMonth").val(12);
      }else{
        $("#userCountMonth").val(Number(month)-1);
      }
    }else{
      if(Number(month) == 12){
        $("#userCountYear").val(Number(year)+1);
        $("#userCountMonth").val(1);
      }else{
        $("#userCountMonth").val(Number(month)+1);
      }
    }
    totalUserCount();
  }

  function totalUserCount(){
    var year = $("#userCountYear").val();
    var month = $("#userCountMonth").val();
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/findInterviewTotalForApp/findUserCount.htm",
      data:{"year":year, "month":month},
      async: false,
      success: function(data) {
        if(data.state == 0){
          var html = "";
          if(data.list.length > 0) {
            for (var i = 0; i < data.list.length; i++) {
              var id = data.list[i].id;
              var name = data.list[i].name;
              var count = data.list[i].count;
              html += "<li>" +
              "<div class=\"content\">" +
              "<a href=\"${pageContext.request.contextPath}/findInterviewByWhereForApp/openFindByUserIdAndMonth.htm?userId="+id+"&year="+year+"&month="+month+"\">" +
              "<div class=\"cell-1\">" +
              "<div class=\"num\">" + (i + 1) + "</div>" +
              "</div> " +
              "<div class=\"cell-2\"> " +
              "<div class=\"text\"> " +
              "<p>" + name + "</p> " +
              "</div> " +
              "</div> " +
              "<div class=\"cell-3\"> " +
              "<span class=\"snm\">" + count + "</span> " +
              "</div> " +
              "</a> " +
              "</div> " +
              "</li>";
            }
          }else{
            html += "<div class='null-tips'>";
            html += "<p>对不起，没有找到相关的记录</p>";
            html += "</div>";
          }
          $("#userCountUL").html(html);
          $("#userCountTxt").html(year+"年"+month+"月拜访记录排行");
        }
      }
    });
  }

  function changeDate(){
    $("#appDateLabel").click();
  }
</script>