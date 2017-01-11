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
<div id="divEdit">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">编辑拜访记录</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(0)"></a>
    </div>
  </header>
  <section>
    <form id="editForm" name="editForm" action="${pageContext.request.contextPath}/editInterviewForApp/editor.htm" method="post">
      <input type="hidden" name="id" value="${interview.id}"/>
      <input type="hidden" name="ip" value="${ip}" />
      <input type="hidden" id="address" name="address" />
      <input type="hidden" id="linkmanId" name="linkmanId" value="${linkman.id}" />
      <div class="auto w bg-f">
        <div class="adm-select-list">
          <ul>
            <li>
              <div class="content">
                <div class="col-tg">交谈对象：</div>
                <div class="col-txt"><a href="javascript:;" id="linkmanText" onclick="selectIf(0)">${linkman.name} <i class="i-arr f-r"></i></a></div>
              </div>
            </li>
            <li>
              <div class="content">
                <div class="col-tg">交流记录：</div>
                <div class="col-txt">
                  <div class="text-input">
                    <textarea placeholder="请输入交流记录" id="content" name="content">${interview.content}</textarea>
                  </div>
                </div>
              </div>
            </li>
          </ul>
          <c:if test="${!empty interviewFileList}">
            <ul class="annex-list">
              <c:forEach var="interviewFile" items="${interviewFileList}">
                <li>
                  <div class="content">
                    <img src="${pageContext.request.contextPath}${interviewFile.url}">
                  </div>
                </li>
              </c:forEach>
            </ul>
          </c:if>
        </div>
      </div>
      <div class="ft-fixed-opr">
        <a class="btn-opr-com" href="javascript:;" onclick="sub()">确定</a>
      </div>
    </form>
  </section>
</div>

<div id="divLinkman" style="display: none;">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择对象</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set">
          <c:if test="${!empty linkmanList}">
            <c:forEach var="linkman" items="${linkmanList}">
              <li>
                <div class="content">
                  <a href="javascript:;" onclick="selectLinkman(${linkman.id}, '${linkman.name}')">${linkman.name}(${linkman.post})</a>
                </div>
              </li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </div>
  </section>
</div>
</body>
</html>
<script>
  callMap();
  function callMapReturn(x,y,address){
    $("#address").val(address);
  }

  function selectIf(flag) {
    if (flag == 0) {
      $("#divLinkman").show();
      $("#divEdit").hide();
    }
  }

  function returnIf(flag){
    if(flag == 0){
      location.href = "${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm";
    }else{
      $("#divEdit").show();
      $("#divLinkman").hide();
    }
  }

  function selectLinkman(id, name){
    $("#linkmanText").html(name);
    $("#linkmanId").val(id);
    returnIf(1);
  }

  function sub(){
    var linkmanId = $("#linkmanId").val();
    var content = $("#content").val();
    if(linkmanId == ""){
      alert("请选择交谈对象");
      return false;
    }
    if(content == ""){
      alert("请填写交谈内容");
      return false;
    }
    if(content.length > 500){
      alert("交谈内容不能超过500字");
      return false;
    }

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/editInterviewForApp/editor.htm",
      data:$('#editForm').serialize(),
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          location.href = "${pageContext.request.contextPath}/findInterviewByIdForApp/find.htm?id=${interview.id}";
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>