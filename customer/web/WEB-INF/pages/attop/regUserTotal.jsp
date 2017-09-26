<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <title>倒计时</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/attop/css/common.css"  />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/attop/css/style.css"  />
  <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-1.9.1.js" charset="utf-8"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/script/Highcharts-4.2.5/js/highcharts.js" charset="utf-8"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/script/Highcharts-4.2.5/js/highcharts-more.js" charset="utf-8"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/script/Highcharts-4.2.5/js/modules/solid-gauge.js" charset="utf-8"></script>
</head>
<body onload="getDays(), getRegUser(), getRegSchool()">
<header>
  <div class="header w">
    <p class="tit">数据统计</p>
    <a href="#" class="back icon"></a>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="timer-bar">
      距离<span class="red">5月31日</span>还有 <span id="days"><c:forEach var="days" items="${days}"><span class="day">${days}</span></c:forEach></span> 天
    </div>
    <div class="ind-data-img">
      <p class="tit-num">已注册用户数<span class="red" id="regUser">10000</span>&nbsp;&nbsp;&nbsp;&nbsp;已注册学校数<span class="red" id="regSchool">100</span></p>
      <div class="data-tb">
        <div style="height: 300px;">
          <div id="container" style="width: 100%; height: 300px;"></div>
        </div>
      </div>
    </div>
    <div class="img-data-tb">
      <div id="container2" style="width: 100%; height: 300px; margin: 0 auto"></div>
    </div>
    <div class="img-data-tb">
      <div id="container3" style="width: 100%; height: 300px; margin: 0 auto"></div>
    </div>
  </div>
</section>
</body>
</html>
<script>
  function getDays(){
    setInterval(function(){
      $.ajax({
        url:"${pageContext.request.contextPath}/attopRegUserTotal/findDays.htm",
        method : 'get',
        async:false,
        success:function(data){
          var html = "";
          for(var i=0; i<data.days.length; i++){
            html += "<span class='day'>"+data.days[i]+"</span>";
          }
          $("#days").html(html);
        }
      });
    }, 10000);
  }

  function getRegUser(){
    var num = 0;
    setInterval(function(){
      $("#regUser").html(num);
      num++;
      if(num == 10001){
        num = 0;
      }
    }, 100);
  }

  function getRegSchool(){
    var num = 0;
    setInterval(function(){
      $("#regSchool").html(num);
      num++;
      if(num == 2001){
        num = 0;
      }
    }, 100);
  }

  var gaugeOptions = {
    chart: {
      type: 'solidgauge'
    },
    title: null,
    pane: {
      center: ['50%', '85%'],
      size: '100%',
      startAngle: -90,
      endAngle: 90,
      background: {
        backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
        innerRadius: '60%',
        outerRadius: '100%',
        shape: 'arc'
      }
    },
    tooltip: {
      enabled: false
    },
    // the value axis
    yAxis: {
      stops: [
        [0.1, '#08b9fb'], // green
        [0.5, '#08b9fb'], // yellow
        [0.9, '#08b9fb'] // red
      ],
      lineWidth: 0,
      minorTickInterval: null,
      tickPixelInterval: 800,
      tickWidth: 0,
      title: {
        y: 70
      },
      labels: {
        y: 160
      }
    },
    plotOptions: {
      solidgauge: {
        dataLabels: {
          y: 5,
          borderWidth: 0,
          useHTML: true
        }
      }
    }
  };
  // The speed gauge
  $('#container').highcharts(Highcharts.merge(gaugeOptions, {
    yAxis: {
      min: 0,
      max: 3000000,
      title: {
        text: ''
      }
    },
    credits: {
      enabled: false
    },
    series: [{
      name: '',
      data: [1600000],
      dataLabels: {
        format: '<div style="text-align:center"><span style="font-size:16px;color:' +
        ((Highcharts.theme && Highcharts.theme.contrastTextColor) || '#08b9fb') + '">{y}</span><br/>' +
        '<span style="font-size:12px;color:#f75a5c">8.5%</span></div>'
      },
      tooltip: {
        valueSuffix: ''
      }
    }]
  }));

  var chart = new Highcharts.Chart('container2', {
    title: {
      text: '',
      x: -20
    },
    subtitle: {
      text: '',
      x: -20
    },
    xAxis: {
      categories: ['18日', '19日', '20日', '21日', '22日', '23日', '24日']
    },
    yAxis: {
      title: {
        text: ''
      },
      plotLines: [{
        value: 0,
        width: 1,
        color: '#808080'
      }]
    },

    credits: {
      text: '',
      href: ''
    },
    series: [{
      name: '人数',
      data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2],
      color: '#f75a5c'
    }]
  });

  $('#container3').highcharts({
    chart: {
      type: 'bar'
    },
    title: {
      text: ''
    },
    subtitle: {
      text: ''
    },
    xAxis: {
      categories: ['重庆', '四川', '云南', '江苏', '浙江'],
      title: {
        text: null
      }
    },
    yAxis: {
      min: 0,
      title: {
        text: '',
        align: 'high'
      },
      labels: {
        overflow: 'justify'
      }
    },
    tooltip: {
      valueSuffix: ''
    },
    plotOptions: {
      bar: {
        dataLabels: {
          enabled: true
        }
      }
    },

    credits: {
      enabled: false
    },
    series: [{
      name: '注册人数',
      data: [133, 156, 947, 408, 6],
      color: '#fdcb4c'
    }]
  });
</script>

