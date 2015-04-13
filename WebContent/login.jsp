<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}/"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="${ctx }common/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
function formsubmit(){
	var psd1 = $("#username").val();
	if(psd1 == "" || psd1 == undefined){
		document.getElementById("msg").innerHTML = "请输入员工号";
		return false;
	}
	
	var psd = $("#password").val();
	if(psd == "" || psd == undefined){
		document.getElementById("msg").innerHTML = "请输入密码";
		return false;
	}
	
	var f = document.getElementById("loginForm");
	f.submit();
}

function enderForformsubmit(event){
	if(event.keyCode == 13 ){
		formsubmit();
	}
}
</script>
</head>
<body>
<div class="login">
<!--start-loginform-->
		<form name="login-form" class="login-form" id="loginForm" action="login.do" method="post">
			<span class="header-top"><img src="images/topimg.png"/></span>
		    <div class="header">
		    <h1>登录</h1>
		   	<span id="msg">
		   	<%
			String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if(error != null){
				//if(error.contains("DisabledAccountException")){
					//out.print("用户已被屏蔽,请登录其他用户.");
				//}
				//else{
					out.print("登录失败，请重试.");
				//}
			}
			%>
		   	</span>
		    </div>
		    <div class="content">
			<input type="text" class="input username" name="username" id="username" placeholder="员工号" >
		    <input type="password"   class="input password" name="password" id="password" onkeydown="enderForformsubmit(event);" placeholder="密码" >
		    </div>
		    <div class="login_button">
		    <input  class="button" type="button" value="登录" onclick="formsubmit();" />
		    </div>
		</form>
<!--end login form-->
<!--side-icons-->
    <div class="user-icon"> </div>
    <div class="pass-icon"> </div>
    <!--END side-icons-->
    <!--Side-icons-->
	<script type="text/javascript">
	$(document).ready(function() {
		$(".username").focus(function() {
			$(".user-icon").css("left","-69px");
		});
		$(".username").blur(function() {
			$(".user-icon").css("left","0px");
		});
		
		$(".password").focus(function() {
			$(".pass-icon").css("left","-69px");
		});
		$(".password").blur(function() {
			$(".pass-icon").css("left","0px");
		});
	});
	</script>
	<p class="copy_right">&#169; 2015 wangt </p>

</div>
</body>
</html>