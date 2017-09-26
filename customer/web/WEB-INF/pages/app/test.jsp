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
  <style>
    section{padding-top:44px;}
  </style>
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
        <span class="ln-m red"><i class="itg"></i>注册用户数：100000</span>
        <span class="ln-m blue"><i class="itg"></i>已加入学校：1000</span>
      </p>
      <table width="100%">
        <tr>
          <td width="60%">
            <div style="width: 100%; height: 200px; margin: 0 auto">
              <div id="container-speed" style="width: 100%; height: 200px; float: left"></div>
            </div>
          </td>
          <td width="40%">
            倒计时图
          </td>
        </tr>
        <tr>
          <td colspan="2"><div id="container2" style="width: 100%; height: 300px; margin: 0 auto"></div></td>
        </tr>
        <tr>
          <td colspan="2"><div id="container3" style="width: 100%; height: 300px; margin: 0 auto"></div></td>
        </tr>
      </table>
    </div>
  </div>
</section>
</body>
</html>
<script>
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
        [0.1, '#55BF3B'], // green
        [0.5, '#DDDF0D'], // yellow
        [0.9, '#DF5353'] // red
      ],
      lineWidth: 0,
      minorTickInterval: null,
      tickPixelInterval: 400,
      tickWidth: 0,
      title: {
        y: -70
      },
      labels: {
        y: 16
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
  $('#container-speed').highcharts(Highcharts.merge(gaugeOptions, {
    yAxis: {
      min: 0,
      max: 5000000,
      title: {
        text: ''
      }
    },
    credits: {
      enabled: false
    },
    series: [{
      name: '',
      data: [300000],
      dataLabels: {
        format: '<div style="text-align:center"><span style="font-size:16px;color:' +
        ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
        '<span style="font-size:12px;color:red">8.5%</span></div>'
      },
      tooltip: {
        valueSuffix: ''
      }
    }]
  }));




  var chart = new Highcharts.Chart('container2', {
    title: {
      text: '每日用户注册数',
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
      data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2]
    }]
  });


  $('#container3').highcharts({
    chart: {
      type: 'bar'
    },
    title: {
      text: '各省用户注册数'
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
    legend: {
      layout: 'vertical',
      align: 'right',
      verticalAlign: 'top',
      x: -40,
      y: 100,
      floating: true,
      borderWidth: 1,
      backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
      shadow: true
    },
    credits: {
      enabled: false
    },
    series: [{
      name: '注册人数',
      data: [133, 156, 947, 408, 6]
    }]
  });
</script>
