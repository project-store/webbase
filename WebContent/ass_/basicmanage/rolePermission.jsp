<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色权限</title>
<%@include file="/common/jsp/taglib.jsp"%>
<script type="text/javascript" src="${root }ass_/basicmanage/js/rolePermission.js"></script>
</head>
<script type="text/javascript">
$(function(){
	roleStore.load();
	
});



</script>


<body >


<div id="authDiv">
<form id="role" >
<input type="hidden" id="roleid" name="roleid" value="" />
<c:forEach items="${ allPermissionLst}" var="aPermissionLst" varStatus="ind" >
<div>
	<input type="checkbox" onclick="rolePermission.checkSibings(this);" name="pcCheckbox" id="${aPermissionLst.id }" value="${aPermissionLst.id }" />
	<label style="color:blue;font-size:16px;" for="${aPermissionLst.id }">${aPermissionLst.name }</label>
	
	<br/>
	<c:forEach items="${ aPermissionLst.children}" var="aPermission" varStatus="ind2" >
		<input type="checkbox" onclick="rolePermission.checkParent(this);" name="pcCheckbox" id="${aPermission.id }" value="${aPermission.id }" value2="${aPermission.pid }" />
		<label style="font-size: 14px;" for="${aPermission.id }">${ aPermission.name}</label>
	</c:forEach>
	<br/>
</div>
</c:forEach>

</form>
</div>

<!-- 新增角色 -->
<!-- 
<div id="roleInfo">
<input type="hidden" value="" id="roleInfoId" />
	<table>
	<tr>
		
		<td>
			角色名称：<input type="text" id="roleInfoName" />
		</td>
	</tr>
	<tr>
	<td>
			角色介绍：<textarea id="roleInfoNote"></textarea>
		</td>
	</tr>

	</table>
	
	

</div>
 -->

	
	
</body>

</html>