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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/css/common.css"  />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/app/css/style.css"  />
  <style>section{padding-top:44px;}</style>
</head>
<body>
<header>
  <div class="header w">
    <p class="tit" style="text-align:center;">登录</p>
  </div>
</header>
<section>
  <div class="auto w bg-f">
    <div class="login-mod">
      <ul>
        <li><input class="input-nickname" placeholder="手机号/ZZ号" name="loginName" id="loginName"></li>
        <li><input class="input-pwd" placeholder="请输入密码" type="password" name="pwd" id="pwd"></li>
        <li><button class="btn-logon" onclick="sub()">登 录</button></li>
      </ul>
    </div>
  </div>
</section>
</body>
</html>
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
            location.href = "${pageContext.request.contextPath}/findCustomerByWhereForApp/find.htm";
          }else {
            alert(data.msg);
          }
        }
      });
    }
  }
</script>
