<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@include file="/common/jsp/taglib.jsp"%>
</head>
<body>
这里是首页
<br/>
用户管理http://localhost:8080/webbase/ass/basicmanage/userManager.jsp<br/>
组织机构http://localhost:8080/webbase/ass/basicmanage/organizationManager.jsp<br/>
角色权限http://localhost:8080/webbase/rolePermission.do?action=initRolePermission<br/>
日志管理http://localhost:8080/webbase/ass/basicmanage/log.jsp<br/>
<br/>
<shiro:guest>  
访客未认证
</shiro:guest>
<br/><br/>

<shiro:user>  
欢迎[<shiro:principal/>]登录
</shiro:user>
<shiro:principal/>
<br/><br/>
<shiro:authenticated>  
    用户[<shiro:principal/>]已身份验证通过  
</shiro:authenticated>   
<br/><br/>

<shiro:hasRole name="super">  
    用户[<shiro:principal/>]拥有角色super<br/>  
</shiro:hasRole>  
<br/><br/>

<shiro:hasRole name="charge">  
    用户[<shiro:principal/>]拥有角色charge<br/>  
</shiro:hasRole>  
<br/><br/>
<br/><br/>
<shiro:lacksRole name="super">  
    用户[<shiro:principal/>]没有角色super<br/>  
</shiro:lacksRole>

<shiro:lacksRole name="charge">  
    用户[<shiro:principal/>]没有角色charge<br/>  
</shiro:lacksRole>

<br/><br/>
<shiro:hasPermission name="charge:user">  
    用户[<shiro:principal/>]拥有权限charge:user<br/>  
</shiro:hasPermission>  


<shiro:hasRole name="user11http://localhost:8080/ass/ass/basicmanage/userManager.jsp">  
    用户[<shiro:principal/>]拥有角色user<br/>  
</shiro:hasRole>  
<br/><br/>
<shiro:hasPermission name="super:add1">  
    用户[<shiro:principal/>]拥有权限super:add1<br/>  
</shiro:hasPermission>   
<br/><br/>
<shiro:hasPermission name="user:add2">  
    用户[<shiro:principal/>]拥有权限user:add2<br/>  
</shiro:hasPermission>   
<br/><br/>
<shiro:hasPermission name="user:add3">  
    用户[<shiro:principal/>]拥有权限user:add3<br/>  
</shiro:hasPermission>   
<br/><br/>
<shiro:hasPermission name="charge:add4">  
    用户[<shiro:principal/>]拥有权限charge:add4<br/>  
</shiro:hasPermission>   
<br/><br/>
<shiro:hasPermission name="charge:add5">  
    用户[<shiro:principal/>]拥有权限charge:add5<br/>  
</shiro:hasPermission>  


























</body>
</html>