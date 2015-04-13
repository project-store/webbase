<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色权限</title>
<%@include file="/common/jsp/taglib.jsp"%>
<script type='text/javascript' src="${ctx }dwr/interface/basicDwrService.js"></script>
<script type='text/javascript' src="${ctx }ass/basicmanage/js/rolePermission.js"></script>
</head>
<script type="text/javascript">


</script>
<style type="text/css">
		.roleTable {
			margin: 15px 0px 20px 30px;
			width: 250px;
			text-align: left;
			float: left;
			border-right: 1px solid #cdcfcf;
			border-top: 1px solid #cdcfcf;
		}
		.contentright{
			margin: 15px 0px 20px 0px;
			float: left;
			padding-left: 10px;
			border-left: 1px solid #cdcfcf;
			border-top: 1px solid #cdcfcf;
		}
</style>

<body >
<div >
<button onclick="rolePermission.createRolePermission();" type="button" class="btn btn-info" style="margin:10px 30px 0px 30px;">新建角色</button>
<button onclick="rolePermission.saveRolePermission();" type="button" class="btn btn-warning" style="margin:10px 30px 0px 10px;">保存当前角色的权限</button>
</div>
<div >
		<div class="roleTable">
			<div id="roleGrid">
				<table id="roletable_" class="table table-bordered table-hover table-condensed" >
					<thead>
					<tr>
					<th>序号</th>
					<th>名称</th>
					<th>操作</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach items="${roleLst }" var="u" varStatus="ind" >
							<tr onclick="clickrole('${u.id }role_','success',${u.id });" id="${u.id }role_">
								<td>${ind.index+1 }</td>
								<td>${u.name }</td>
								<td> <a href="javascript:void(0)" onclick="rolePermission.editRolePermission(${u.id})" >编辑</a> &nbsp;&nbsp;
									<a href="javascript:void(0)" onclick="rolePermission.delRolePermission(${u.id})" >删除</a>
								 </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	
	
		<div class="contentright" id="contentright" >
		
			<div id="authDiv" >
				<form id="role" >
				<input type="hidden" id="roleid" name="roleid" value="" />
				<c:forEach items="${ allPermissionLst}" var="aPermissionLst" varStatus="ind" >
				<div>
				
					<input type="checkbox"  onclick="rolePermission.checkSibings(this);" name="pcCheckbox" id="${aPermissionLst.id }" value="${aPermissionLst.id }" />
					<label style="font-size: 16px;font-weight: normal;" class="text-danger" for="${aPermissionLst.id }">${aPermissionLst.text }</label>
					
					<br/>
					<c:forEach items="${ aPermissionLst.children}" var="aPermission" varStatus="ind2" >
						<input type="checkbox" onclick="rolePermission.checkParent(this);" name="pcCheckbox" id="${aPermission.id }" value="${aPermission.id }" value2="${aPermission.pid }" />
						<label style="font-size: 14px;font-weight: normal;"  class="text-info "  for="${aPermission.id }">${ aPermission.text}</label>&nbsp;&nbsp;
					</c:forEach>
					<br/>
				</div>
				</c:forEach>
				
				</form>
				</div>
		
			
		</div>
	
</div>
	
	
	
	
<div id='editRole_' style="display:none" >
<label class="control-label " style="width:80px;">角色名称:</label>
<input type="text" class='input-normal' name='edit_rolename' id='edit_rolename' />
<input type='hidden' id="edit_roleid" />

</div>



	
	
</body>

</html>