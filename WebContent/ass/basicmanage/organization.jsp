<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构</title>
<%@include file="/common/jsp/taglib.jsp"%>
<script type='text/javascript' src="${ctx }dwr/interface/basicDwrService.js"></script>
<script type='text/javascript' src="${ctx }ass/basicmanage/js/organization.js"></script>

<script type="text/javascript">

		
	</script>
	
	<style type="text/css">
		.ztreeleft {
			margin: 15px 0px 20px 30px;
			width: 15%;
			text-align: left;
			float: left;
			border-right: 1px solid #cdcfcf;
			border-top: 1px solid #cdcfcf;
		}
		.contentright{
			margin: 15px 0px 20px 0px;
			width:74%;
			/*height: 800px;*/
			float: left;
			border-left: 1px solid #cdcfcf;
			border-top: 1px solid #cdcfcf;
		}
		.addPng{
			background-image: url("${ctx }common/ztree3/css/zTreeStyle/img/diy/add.png");
		}
		.deletePng{
			background-image: url("${ctx }common/ztree3/css/zTreeStyle/img/diy/delete.png");
		}
		#organizationDiv_ dl{
			margin: 5px 25px;
		
		}
		#userInfoDiv_ dl{
			margin: 5px 25px;
			width:700;
		}
		.uinfo{
			height: 300px;
			width: 240px;
			float: left;
		}
		.urole{
			border-left: 1px solid #cdcfcf;
			border-right: 1px solid #cdcfcf;
			height: 300px;
			float: left;
			width: 240px;
		}
		
		.upermission{
			height: 300px;
			width: 200px;
			float: left;
			overflow: auto;
		}
	</style>
</head>

<body>
	<div >
		<div class="ztreeleft">
			<ul id="treeDemo"  ></ul>
		</div>
	
		<div class="contentright" id="contentright" >
		</div>
	
	</div>
		
		
		
		
<div id="organizationDiv_" style="display:none;" >
<form   id="oForm" class="form-horizontal" >
	<input type="hidden" id="OrganId" name="OrganId" />
	<input type="hidden" id="flag" name="flag" />
		<div class="control-group span10">
			<label class="control-label" style="width:80px;"><s>*</s>机构名称:</label>
			<div class="controls" >
				<input type="text"  class="input-normal" data-rules="{required : true}" placeholder="机构名称(必填)" name="organizationName" id="organizationName"/>
			</div>
		</div>
		<div class="control-group span10">
			<label class="control-label " style="width:80px;"><s>*</s>机构代码:</label>
			<div class="controls" >
				<input   type="text"  class="input-normal" data-rules="{required : true}" placeholder="机构代码(必填)" name="organizationCode" id="organizationCode"/>
			</div>
		</div>
		
		<div class="control-group span10">
			<label class="control-label " style="width:80px;">机构电话:</label>
			<div class="controls" >
				<input   type="text" class="input-normal" placeholder="机构电话" name="organizationTel" id="organizationTel"/>
			</div>
		</div>
		
		<div class="control-group span10">
			<label class="control-label " style="width:80px;">负责人:</label>
			<div class="controls" >
				<select name="inchargeUser" class="input-normal"  id="inchargeUser"  >
			<!-- <option value="1">a1a</option> -->
		</select>
			</div>
		</div>
		
		
		
</form>		
</div>



<div id="userInfoDiv_" style="display:none">
<!-- 用户信息 -->
<div class='uinfo' >
<form id="uForm">
	<input type="hidden" name="belongOid" id="belongOid" />
	<input type="hidden" name="uid" id="uid" />
	<dl>
		<dt>员工姓名:</dt>
		<dd><input type="text" placeholder="姓名(必填)" name="uname" id="uname"/></dd>
		
		<dt>员工号:</dt>
		<dd><input   type="text" placeholder="员工号(必填)" name="uloginname" id="uloginname"/><dd>
	
		<dt>手机号码:</dt>
		<dd><input   type="text" placeholder="手机号码" name="umobile" id="umobile"/><dd>
		
		<dt>邮箱:</dt>
		<dd>
		<dd><input   type="text" placeholder="邮箱" name="uemail" id="uemail"/><dd>
		</dd>
		
	</dl>

</form>
</div>
<!-- 角色 -->
<div class='urole'>
<span id="rolelist" class="label label-info" style="font-size:12px;" >选择用户包含角色</span>
	<div style="height:230px;width:230px; overflow-height: auto;margin-top:6px;">
		<table id="roletable" class="table table-bordered table-hover table-condensed" >
		<tbody>
		<c:forEach items="${roleLst }" var="u" varStatus="ind" >
							<tr onclick="clicktr2('${u.id }role','success');" id="${u.id }role">
								<td>${ind.index+1 }</td>
								<td>${u.name }</td>
								<!-- <td>${u.rolevalue }</td> -->
							</tr>
						</c:forEach>
		</tbody>
		</table>
	</div>
     <input type="button" id="saveUserBtn" class="btn btn-primary" style="margin-left:20px;" value="保存用户信息和所有角色" />
</div>

<!-- 权限 -->
<div class='upermission' >
	<ul id="permissionTree" ></ul>
</div>


</div>
		
</body>

</html>