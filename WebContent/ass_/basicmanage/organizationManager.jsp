<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织机构</title>
<%@include file="/common/jsp/taglib.jsp"%>
<script type="text/javascript" src="${root }ass_/basicmanage/js/organizationManager.js"></script>
<style type="text/css">
.detail2{
	/*width : 250px;*/
}
.detail1{
	color: blue;
}
#detailPanel{
	font-size: medium;
}
.checkboxgray{
	disabled:true;
}
#detailPanel table tr{
	height:40px;
}
</style>
</head>

<body>
	
		<div style="display: none;">
			<div id="detailPanel" >
				<table width="60%" align="center" >
					<tr>
						<td class="detail2">机构名称：</td><td align="left" class="detail1" id="name_"></td>
						<td class="detail2">负责人：</td><td align="left" class="detail1" id="incharge_user"></td>
					</tr>
					<tr>
						<td class="detail2">机构代码：</td><td align="left" class="detail1" id="code_"></td>
						<td class="detail2">电话：</td><td align="left" class="detail1" id="tel_"></td>
						
					</tr>
					<tr>
						<td class="detail2">负责人手机：</td><td align="left" class="detail1" id="phone_"></td>
						<td class="detail2">说明：</td><td align="left" class="detail1" id="introduce_"></td>
					</tr>
					
				</table>
			</div>
		</div>
		
		
		
</body>

</html>