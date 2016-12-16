<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<script type="text/javascript">
  $(function(){
    //回车事件
    document.onkeydown = function(e){
      var ev = document.all ? window.event : e;
      if(ev.keyCode==13) {
        sub();
      }
    }
  });

  function sub(){
    var loginName = $.trim($("#loginName").val());
    var pwd = $.trim($("#pwd").val());
    if(loginName == ""){
      alert("请输入用户名！");
    }
    else if(pwd == ""){
      alert("请输入密码！");
    }else{
      var params = {
        "zzCode":loginName,
        "pwd":pwd
      };
      $.ajax({
        url:"${pageContext.request.contextPath}/loginUser/login.htm",
        method : 'POST',
        async:false,
        data:params,
        success:function(data){
          if(data.msg == "success"){
            location.href = "${pageContext.request.contextPath}/index/main.htm";
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }
</script>
<body id="main_body">
  <div id="page">
    <div id="header">
      <div class="logo_admin">
        <div class="logo"><h2>客户拜访记录系统</h2></div>
      </div>
    </div>
    <div id="centerAdmin">
      <div class="login-box">
        <h2>欢迎登录客户拜访记录系统App端</h2>
        <ul class="login-info-list">
          <li><label>账 号：</label><input class="input-login-1" type="text" name="loginName" id="loginName" /></li>
          <li><label>密 码：</label><input class="input-login-1" type="password" name="pwd" id="pwd" /></li>
          <li>
            <a class="btn-login" href="#" onclick="sub()">登 录</a>
          </li>
        </ul>
      </div>
    </div>
    <div id="footAdmin">
      <p><span class="right">Copyright 2009 至善在线教育网 版权所有 渝ICP备06004276号 </span></p>
    </div>
  </div>
</body>
</html>