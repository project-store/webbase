<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@ page import= "org.apache.shiro.subject.Subject"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	/*获取登录信息,此处无须判断是否登录，因为在url访问全县中已经限制*/
	//Subject subject = SecurityUtils.getSubject();		
	//ShiroUser user = (ShiroUser)subject.getPrincipal();
%>
<c:set var="root" value="<%=basePath %>"/>
<c:set var="ctx" value="${pageContext.request.contextPath}/"/>

<!-- 
${root}:   http://localhost:8080/webbase/
${ctx}:    /webbase/ 
-->

<!-- bootstrap 加载顺序不能错  1bootstrap.css  2jquery.js  3bootstrap.js 
	ztree 加载顺序  1 zTreeStyle.css  2 jquery.js  3ztree.js
-->  
<link href="${ctx }common/bootstrap3.3/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<!-- <link rel="stylesheet" href="${ctx }common/ztree3/css/zTreeStyle/zTreeStyle.css" type="text/css"> -->

<!-- bui css -->
<!-- 和ztree有冲突 <link href="http://g.alicdn.com/bui/bui/1.1.9/css/bs3/dpl.css" rel="stylesheet"> 
 <link href="http://g.alicdn.com/bui/bui/1.1.9/css/bs3/bui.css" rel="stylesheet"> -->
<link href="${ctx }common/build-bui/css/bs3/dpl.css" rel="stylesheet">
<link href="${ctx }common/build-bui/css/bs3/bui-min.css" rel="stylesheet">


<script type="text/javascript" src="${ctx }common/js/jquery-1.10.2.min.js"></script>

<!-- bui js 
<script src="http://g.alicdn.com/bui/bui/1.1.9/bui-min.js"></script>-->
<script type="text/javascript" src="${ctx }common/build-bui/bui-min.js"></script>


<!-- <script type="text/javascript" src="${ctx }common/bootstrap3.3/js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="${ctx }common/bootstrap3.3/jquery.twbsPagination.js"></script>
<!--<script type="text/javascript" src="${ctx }common/ztree3/js/jquery.ztree.all-3.5.min.js"></script>-->



<script type="text/javascript">
BUI.use('bui/overlay',function(overlay){
	//common.js 里面的弹出提示,需要引入这个.
});

</script>
<script type="text/javascript" src="${ctx }common/js/common.js"></script>
<script type="text/javascript" src="${ctx }common/js/validator.js"></script>
<link href="${ctx }common/css/index.css" rel="stylesheet">
<script type="text/javascript">
common.ctx = "${ctx}";	
</script>

<!-- dwr 公用 -->
<script type='text/javascript' src="${ctx }dwr/engine.js"></script>
<script type='text/javascript' src="${ctx }dwr/util.js"></script>


