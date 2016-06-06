<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="content">

</div>
<script>
  var title = [], value = [], num = 0;
  <c:forEach var="map" items="${list}">
    title.push("${map.name}<br>${fn:substring(map.date, 0, 10) }");
    value.push({y:${map.count}, id: ${map.id}});
    num++;
  </c:forEach>

  $("#content").height(num*60+"px");

  $('#content').highcharts({
    chart: {
      type: 'bar'
    },
    title: {
      text: '客户访谈记录统计图'
    },

    xAxis: {
      categories: title,
      title: {
        text: null
      }
    },
    yAxis: {
      min: 0,
      title: {
        text: '次数',
        align: 'high'
      },
      labels: {
        overflow: 'justify'
      }
    },
    tooltip: {
      valueSuffix: ' 次'
    },
    plotOptions: {
      bar: {
        dataLabels: {
          enabled: true
        }
      },
      series: {
        cursor: 'pointer',
        point: {
          events: {
            click: function() {
              openDetail(this.options.id);
            }
          }
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
      backgroundColor: '#FFFFFF',
      shadow: true
    },
    credits: {
      enabled: false
    },
    series: [{
      name: '客户访谈记录数',
      data: value
    }]
  });

  function openDetail(id){
    openDialog('客户访谈记录详情', 0.9, 0.8, '${pageContext.request.contextPath}/findInterviewByCustomerId/find.htm?customerId='+id);
  }
</script>
