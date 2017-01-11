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
      <p class="tit" style="text-align:center;">拜访记录筛选</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(0)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul>
          <li>
            <div class="content">
              <div class="col-tg">客户选择：</div>
              <div class="col-txt"><a href="javascript:;" id="customerText" onclick="selectIf(0)">全部客户</a></div>
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
          <li>
            <div class="content">
              <div class="col-tg">业务员：</div>
              <div class="col-txt"><a href="javascript:;" id="userText" onclick="selectIf(3)">所有业务员</a></div>
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
<div id="divCustomer">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择客户</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <li>
            <div class="content">
              <a href="javascript:;" onclick="selectCustomer('', '全部客户')">全部客户</a>
            </div>
          </li>
          <c:if test="${!empty customerList}">
            <c:forEach var="customer" items="${customerList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectCustomer('${customer.id}', '${customer.name}')">${customer.name}</a>
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
<div id="divUser">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择业务员</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <li>
            <div class="content">
              <a href="javascript:;" onclick="selectUser('', '所有业务员')">所有业务员</a>
            </div>
          </li>
          <c:if test="${!empty userList}">
            <c:forEach var="user" items="${userList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectUser(${user.id}, '${user.name}')" >${user.name}</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
<form id="searchForm" name="searchForm" action="${pageContext.request.contextPath}/findInterviewByWhereForApp/openFindByOther.htm">
  <input type="hidden" name="search" value="do" />
  <input type="hidden" id="typeId" name="typeId" />
  <input type="hidden" id="stateId" name="stateId" />
  <input type="hidden" id="customerId" name="customerId" />
  <input type="hidden" id="userId" name="userId" />
  <div id="divResult" style="display: none;">
    <header>
      <div class="header w">
        <p class="tit" style="text-align:center;">拜访记录筛选结果</p>
        <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
      </div>
    </header>
    <c:if test="${param.search eq 'do'}">
      <section>
        <div class="auto w bg-f">
          <div class="adm-visit-list">
            <ul>
              <c:if test="${empty interviewList}">

                  <div class="auto w bg-f">
                    <div class="adm-log-list">
                      <div class="null-tips">
                        <div class="tips-pic"><img src="${pageContext.request.contextPath}/app/images/null-tips.png"></div>
                        <p>对不起，没有找到相关的记录</p>
                      </div>
                    </div>
                  </div>

              </c:if>
              <c:if test="${!empty interviewList}">
                <c:forEach var="interview" items="${interviewList}">
                  <li>
                    <div class="title" onclick="location.href='${pageContext.request.contextPath}/findInterviewByUserIdForApp/find.htm?customerId=${interview.cId}&linkmanId=${interview.clId}'">
                      <a href="javascript:;">${interview.cName}-${interview.clName}</a>
                    </div>
                    <div class="text" onclick="location.href='${pageContext.request.contextPath}/findInterviewByIdForApp/find.htm?id=${interview.id}'">
                      <p>${interview.content}</p>
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
      $("#divCustomer").hide();
      $("#divType").hide();
      $("#divState").hide();
      $("#divUser").hide();
    </c:if>
    <c:if test="${'do' ne param.search}">
      $("#divIf").show();
      $("#divCustomer").hide();
      $("#divType").hide();
      $("#divState").hide();
      $("#divResult").hide();
      $("#divUser").hide();
    </c:if>
  });

  function sub(){
    $("#searchForm").submit();
  }

  function selectIf(flag){
    if(flag == 0){
      $("#divCustomer").show();
      $("#divIf").hide();
      $("#divType").hide();
      $("#divState").hide();
      $("#divUser").hide();
    }
    if(flag == 1){
      $("#divType").show();
      $("#divIf").hide();
      $("#divCustomer").hide();
      $("#divState").hide();
      $("#divUser").hide();
    }
    if(flag == 2){
      $("#divState").show();
      $("#divIf").hide();
      $("#divType").hide();
      $("#divCustomer").hide();
      $("#divUser").hide();
    }
    if(flag == 3){
      $("#divUser").show();
      $("#divIf").hide();
      $("#divType").hide();
      $("#divCustomer").hide();
      $("#divState").hide();
    }
  }

  function returnIf(flag){
    if(flag == 0){
      location.href="${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm";
    }else{
      $("#divIf").show();
      $("#divState").hide();
      $("#divType").hide();
      $("#divCustomer").hide();
      $("#divUser").hide();
      $("#divResult").hide();
    }
  }

  function selectCustomer(id, name){
    $("#customerText").html(name);
    $("#customerId").val(id);
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
  function selectUser(id, name){
    $("#userText").html(name);
    $("#userId").val(id);
    returnIf(1);
  }
</script>
