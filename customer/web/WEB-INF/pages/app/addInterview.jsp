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
<div id="divAdd">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">新增拜访记录</p>
      <a class="back icon" href="${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm"></a>
    </div>
  </header>
  <section>
      <div class="auto w bg-f">
        <div class="adm-select-list">
          <ul>
            <form id="addForm" name="addForm" action="${pageContext.request.contextPath}/addInterviewForApp/add.htm" method="get">
              <input type="hidden" id="customerId" name="customerId" value="${customer.id}"/>
              <input type="hidden" id="customerLankmanId" name="customerLankmanId" value="${customerLankman.id}"/>
              <input type="hidden" name="ip" value="${ip}" />
              <input type="hidden" id="address" name="address" />
              <input type="hidden" id="filePaths" name="filePaths" value="" />
              <input type="hidden" name="token" value="${token}" />
              <li>
                <div class="content">
                  <div class="col-tg">客户名称：</div>
                  <div class="col-txt">
                    <c:if test="${empty customer}">
                      <a href="javascript:;" id="customerText" onclick="selectIf(0)">请选择（必填）</a>
                    </c:if>
                    <c:if test="${!empty customer}">${customer.name}</c:if>
                  </div>
                </div>
              </li>
              <li>
                <div class="content">
                  <div class="col-tg">交谈对象：</div>
                  <div class="col-txt">
                    <c:if test="${empty customerLankman}">
                      <a href="javascript:;" id="linkmanText" onclick="selectIf(1)">请选择（必填）</a>
                    </c:if>
                    <c:if test="${!empty customerLankman}">${customerLankman.name}</c:if>
                  </div>
                </div>
              </li>
              <li>
                <div class="content">
                  <div class="col-tg">交流记录：</div>
                  <div class="col-txt">
                    <div class="text-input">
                      <textarea placeholder="请输入交流记录" name="content" id="content"></textarea>
                    </div>
                  </div>
                </div>
              </li>
            </form>
            <li>
              <div class="add-pics-list">
                <form id="fileForm" name="fileForm" enctype="multipart/form-data" action="${pageContext.request.contextPath}/uploadTempFile/upload.htm?random=${random}" method="post">
                  <ul id="fileUl">
                    <li><a class="add-btn" href="javascript:;"><i class="i-plus"></i><input type="file" class="uploadFile" id="img" name="img" onchange="changeFile()"></a></li>
                  </ul>
                </form>
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
<div id="divLinkman">
  <header>
    <div class="header w">
      <p class="tit" style="text-align:center;">选择交谈对象</p>
      <a class="back icon" href="javascript:;" onclick="returnIf(1)"></a>
    </div>
  </header>
  <section>
    <div class="auto w bg-f">
      <div class="adm-select-list">
        <ul class="item-set" id="linkmanLi">
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

  $("#divAdd").show();
  $("#divCustomer").hide();
  $("#divLinkman").hide();

  function selectIf(flag){
    if(flag == 0){
      $("#divCustomer").show();
      $("#divAdd").hide();
      $("#divLinkman").hide();
    }
    if(flag == 1){
      var cusotmerId = $("#customerId").val();
      if(cusotmerId == ""){
        alert("请选择拜访客户");
        return false;
      }
      $("#divLinkman").show();
      $("#divAdd").hide();
      $("#divCustomer").hide();
    }
  }

  function returnIf(flag){
    if(flag == 0){
      location.href="${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm";
    }else{
      $("#divAdd").show();
      $("#divCustomer").hide();
      $("#divLinkman").hide();
    }
  }

  function selectCustomer(id, name){
    $("#customerText").html(name);
    $("#customerId").val(id);
    returnIf(1);
    $.ajax({
      cache: true,
      type: "get",
      url:"${pageContext.request.contextPath}/findLinkmanByCustomerId/find.htm",
      data:{"customerId": id, "random":Math.round(Math.random()*100000)},
      async: false,
      success: function(data) {
        if(data.state == 0){
          if(data.linkmanList.length < 1){
            var html = "<section>";
            html += "<div class=\"auto w bg-f\">";
            html += "<div class=\"adm-log-list\">";
            html += "<div class=\"null-tips\">";
            html += "<div class=\"tips-pic\"><img src=\"${pageContext.request.contextPath}/app/images/null-tips.png\"></div>";
            html += "<p>对不起，没有找到相关的记录</p>";
            html += "</div>";
            html += "</div>";
            html += "</div>";
            html += "</section>";
            $("#linkmanLi").html(html);
          }else{
            if(1 == data.linkmanList.length){
              $("#linkmanText").html(data.linkmanList[0].name);
              $("#customerLankmanId").val(data.linkmanList[0].id);
            }else{
              $("#linkmanText").html("请选择（必填）");
              $("#customerLankmanId").val("");
            }
            var html = "";
            for(var i=0; i<data.linkmanList.length; i++){
              var linkman = data.linkmanList[i];
              html += "<li>";
              html += "<div class=\"content\">";
              html += "<a href=\"javascript:;\" onclick=\"selectLinkman("+linkman.id+", '"+linkman.name+"')\">"+linkman.name+"</a>";
              html += "</div>";
              html += "</li>";
            }
            $("#linkmanLi").html(html);
          }
        }else{
          alert(data.msg);
        }
      }
    });
  }

  function selectLinkman(id, name){
    $("#linkmanText").html(name);
    $("#customerLankmanId").val(id);
    returnIf(1);
  }

  function sub(){
    var customerId = $("#customerId").val();
    var customerLankmanId = $("#customerLankmanId").val();
    var content = $("#content").val();
    if(customerId == ""){
      alert("请选择学校");
      return false;
    }
    if(customerLankmanId == ""){
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
    $("#addForm").ajaxSubmit({
      url:"${pageContext.request.contextPath}/addInterviewForApp/add.htm",
      dataType : 'json',
      success: function(data) {
        if(data.state == 0){
          alert("提交成功！");
          location.href = "${pageContext.request.contextPath}/findInterviewByWhereForApp/find.htm";
        }else{
          alert(data.msg);
        }
      }
    });
  }

  function changeFile(){

    $("#fileForm").ajaxSubmit({
      url:"${pageContext.request.contextPath}/uploadTempFile/upload.htm",
      dataType : 'json',
      success: function(data) {
        if(data.state == 0){
          var fileUrl = data.filePath;
          var html = "<li><img src='${pageContext.request.contextPath}"+fileUrl+"'><a class='x-del' href='javascript:;' onclick=\"delFile('"+fileUrl+"', this);\">×</a></li>";
          html += $("#fileUl").html();
          $("#fileUl").html(html);
          $("#img").val("");
          var filePaths = $("#filePaths").val();
          if(filePaths == ""){
            $("#filePaths").val(fileUrl);
          }else{
            $("#filePaths").val(filePaths+","+fileUrl);
          }
        }else{
          alert(data.msg);
        }
      }
    });
  }

  function delFile(fileUrl, obj){
    $.ajax({
      url:"${pageContext.request.contextPath}/uploadTempFile/delFile.htm",
      method : 'get',
      async:false,
      data:{"filePath":fileUrl},
      success:function(data){
        if(data.state == 0){
          $(obj).parent().remove();
          var filePaths = $("#filePaths").val();
          $("#filePaths").val(filePaths.replace(fileUrl, ""));
        }else{
          alert(data.msg);
        }
      }
    });
  }

</script>