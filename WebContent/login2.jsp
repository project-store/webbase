<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<script type="text/javascript">
function formsubmit(){
	var psd = document.getElementById("username").value;
	if(psd == ""){
		document.getElementById("msg").innerHTML = "请输入员工号";
		return false;
	}
	
	
	var psd = document.getElementById("password").value;
	if(psd == ""){
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
登陆页面

<form id="loginForm" action="login.do" method="post">
	<div id="msg" >
	<%
			String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if(error != null){
				if(error.contains("DisabledAccountException")){
					out.print("用户已被屏蔽,请登录其他用户.");
				}
				else{
					out.print("登录失败，请重试.");
				}
			}
		%>
	</div>

	<div id="username_input">
		<span class="span1"> 
 	     <span class="span2"><div style="float: left;padding-top: 10px;padding-left: 10px;"><img class="smallPic" src="./images/login/uname.png"></div><b class="textname">员工号:</b></span>

 	     <input class="inputtext" name="username" id="username" type="text" onblur="if (this.value == '') {this.value = '请输入员工号';}" onfocus="this.value=''"  >
 	    </span>
	</div>
	<div id="password_input">
		<span class="span1"> 
 	     <span class="span2"><div style="float: left;padding-top: 10px;padding-left: 10px;"><img class="smallPic" alt="" src="./images/login/psd.png"></div><b class="textname">&nbsp;&nbsp;密码:</b></span>
 	     <input class="inputtext" onkeydown="enderForformsubmit(event);" name="password" id="password" type="password"  >
 	    </span>
	</div>
	<div class="loginbutton2">
		<button  onclick="formsubmit();">提交</button>
	</div>
	</form>
</body>
</html>