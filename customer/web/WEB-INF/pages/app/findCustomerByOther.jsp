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
<div id="divIf">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">客户管理</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(0)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul>
          <li>
            <div class="content">
              <div class="col-tg">区域选择：</div>
              <div class="col-txt"><a href="javascript:;" id="areaText" onclick="selectIf(0)">全国</a></div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">客户类型：</div>
              <div class="col-txt"><a href="javascript:;" id="typeText" onclick="selectIf(1)">不限类型</a></div>
            </div>
          </li>
          <li>
            <div class="content">
              <div class="col-tg">客户状态：</div>
              <div class="col-txt"><a href="javascript:;" id="stateText" onclick="selectIf(2)">不限状态</a></div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="ft-fixed-opr">
      <a class="btn-opr-com" href="javascript:;" onclick="sub()">确定</a>
    </div>
  </section>
</div>
<div id="divArea">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择区域</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <li>
            <div class="content">
              <a href="javascript:;" onclick="selectArea('', '全国')">全国</a>
            </div>
          </li>
          <c:if test="${!empty areaList}">
            <c:forEach var="area" items="${areaList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectArea('${area.code}', '${area.name}')">${area.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<div id="divType">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择客户类型</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <li>
            <div class="content">
              <a href="javascript:;" onclick="selectType('', '不限类型')">不限类型</a>
            </div>
          </li>
          <c:if test="${!empty typeList}">
            <c:forEach var="type" items="${typeList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectType(${type.id}, '${type.name}')">${type.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<div id="divState">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择客户状态</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <li>
            <div class="content">
              <a href="javascript:;" onclick="selectState('', '不限状态')">不限状态</a>
            </div>
          </li>
          <c:if test="${!empty stateList}">
            <c:forEach var="state" items="${stateList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectState(${state.id}, '${state.name}')" >${state.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<form id="searchForm" name="searchForm" action="${pageContext.request.contextPath}/findCustomerByWhereForApp/openFindByOther.htm">
  <input type="hidden" name="search" value="do" />
  <input type="hidden" id="typeId" name="typeId" />
  <input type="hidden" id="stateId" name="stateId" />
  <input type="hidden" id="provinceCode" name="provinceCode" />
  <div id="divResult" style="display: none;">
    <header>
      <div class="header w">
        <p class="tit" style="text-align:center;">客户筛选结果</p>
        <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
      </div>
    </header>
    <c:if test="${param.search eq 'do'}">
      <div class="adm-log-list">
        <ul>
          <c:if test="${empty customerList}">
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
          <c:if test="${!empty customerList}">
            <c:forEach var="customer" items="${customerList}">
              <li onclick="location.href = '${pageContext.request.contextPath}/findCustomerByIdForApp/open.htm?id=${customer.id}'">
                <div class="content">
                  <div class="col-txt">${customer.cName}</div>
                  <div class="col-r"><a href="javascript:;">${customer.csName} <i class="i-arr"></i></a></div>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </c:if>
  </div>
</form>
</body>
</html>
<script>
  $(function(){
    //回车事件
    document.onkeydown = function(e){
      var ev = document.all ? window.event : e;
      if(ev.keyCode==13) {
        sub();
      }
    }

    <c:if test="${'do' eq param.search}">
      $("#divResult").show();
      $("#divIf").hide();
      $("#divArea").hide();
      $("#divType").hide();
      $("#divState").hide();
    </c:if>
    <c:if test="${'do' ne param.search}">
      $("#divIf").show();
      $("#divArea").hide();
      $("#divType").hide();
      $("#divState").hide();
      $("#divResult").hide();
    </c:if>
  });

  function sub(){
    $("#searchForm").submit();
  }

  function selectIf(flag){
    if(flag == 0){
      $("#divArea").show();
      $("#divIf").hide();
      $("#divType").hide();
      $("#divState").hide();
    }
    if(flag == 1){
      $("#divType").show();
      $("#divIf").hide();
      $("#divArea").hide();
      $("#divState").hide();
    }
    if(flag == 2){
      $("#divState").show();
      $("#divIf").hide();
      $("#divType").hide();
      $("#divArea").hide();
    }
  }

  function returnIf(flag){
    if(flag == 0){
      location.href="${pageContext.request.contextPath}/findCustomerByWhereForApp/find.htm";
    }else{
      $("#divIf").show();
      $("#divState").hide();
      $("#divType").hide();
      $("#divArea").hide();
      $("#divResult").hide();
    }
  }

  function selectArea(code, name){
    $("#areaText").html(name);
    $("#provinceCode").val(code);
    returnIf(1);
  }
  function selectType(id, name){
    $("#typeText").html(name);
    $("#typeId").val(id);
    returnIf(1);
  }
  function selectState(id, name){
    $("#stateText").html(name);
    $("#stateId").val(id);
    returnIf(1);
  }
</script>
