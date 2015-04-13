<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">






BUI.use('bui/tooltip',function (Tooltip) {	
var tipbt = new Tooltip.Tip({
	trigger : '#biaotiTitle',
	alignType : 'right',
	offset : 10,
	title : '点击修改机构信息.',
	elCls : 'tips tips-success',
	titleTpl : '<span class="x-icon x-icon-small x-icon-success"><i class="icon icon-white icon-info"></i></span><div class="tips-content">{title}</div>'
	});
tipbt.render();
});

//////表格 和 分页栏  begin******
$(function(){
	
	//查询按钮事件定义
	$("#search").click(function(){
		pageBar();
	});
	
	//页面初始化分页栏 连带加载表格数据
	pageBar();
	 
	
});



//查询方法  供pageBar()方法调用
function search(page){
		$("#usertbody").html("数据加载中...请稍后...");
		var d = {
				page : page,//当前页码
				limit : '12', //一页显示的记录数
				statement : 'userManage_SpecSql.getUserRelated',
				//sidx : 'id',  //选传
				//sord : 'asc', //选传
				username : $("#name_").val(),//!!
				loginname : $("#loginname_").val(),//!!
				tOrganizationId : '${organization.id}'
		};
		var url = common.ctx + "common.do?action=list";
		common.dajax(url, d, function(data){
			var lst = data.rows;
			var innerHtml1 = '';//!!
			for(var i=0; i<lst.length; i++){//!!
				innerHtml1 += "<tr onclick=common.clicktr("+lst[i].id+",'info'); id="+lst[i].id+">"
				+"<td>"+(i+1)+"</td>"
				// +"<td>"+lst[i].id+"</td>"
				+"<td>"+lst[i].name+"("+lst[i].loginName+")</td>"
				+"<td>"+lst[i].mobile+"</td>"
				+"<td>"+lst[i].email+"</td>"
				+"<td>"+lst[i].dbrolenames+"</td>"
				+"<td>"+lst[i].organizationName+"</td>"
				+"<td><a title='修改信息 ' href='javascript:void(0)' onclick='editUser("+lst[i].id+")'>修改</a>&nbsp;&nbsp;"
				+"<a title='删除用户 ' href='javascript:void(0)' onclick='deleteUser("+lst[i].id+")'>删除</a></td>"
				+"</tr>";
			}
			//tbody
			$("#usertbody").html(innerHtml1);//!!
    });
	
}

//设置分页栏  连带加载表格
function pageBar(){
	//1先获得总的页数
	var d = {
			page : 1,//!!***  当前页码
			limit : '12', //一页显示的记录数
			statement : 'userManage_SpecSql.getUserRelated',
			//sidx : 'id',  //选传
			//sord : 'asc', //选传
			username : $("#name_").val(),//!!
			loginname : $("#loginname_").val(),//!!
			tOrganizationId : '${organization.id}'
	};
	var url = common.ctx + "common.do?action=list";
	common.dajax(url, d, function(data){
		if(data.rows.length == 0){
			$("#usertbody").html("没有数据");
			return false;
		}
		//不重新弄一下html，页码不刷新
		$("#pageBarDiv").html("<ul id='pagination' class='pagination-sm'></ul>");
		$('#pagination').twbsPagination({
			//上面查询后台就是为了获取 data.total!!!!!!!
	        totalPages: data.total,
	        visiblePages: data.total < 8 ? data.total : 8 ,//!!
	        onPageClick: function (event, page) {
	        		search(page);//默认会执行一次查询数据
	        	}
			});
	
});
	
}
//////表格 和 分页栏  end******	

//弹出增加用户的窗口
function addNewUserf(organizationId){
	$("#uForm")[0].reset();//重置表单
	$("#belongOid").val(organizationId);//设置 机构id
	$("#uid").val(0);//0代表新增, 其他值代表编 辑
	common.clearTrClass("roletable");//清楚表格被选中状态, 重置表格
	userDiv.show();
}

//修改机构
function editOrganization(oId){
	organizationDiv.show();
	//重置表单
	$("#oForm")[0].reset();
	$("#OrganId").val(oId);
	$("#flag").val("edit");
	
	$("#organizationName").val('${organization.name }');
	
	$("#organizationCode").val('${organization.code }');
	
	$("#organizationTel").val('${organization.tel }');
	
	return false;


}



//弹出编辑用户窗口
function editUser(id){
	//获得用户的id,  姓名, 员工号 手机号码 邮箱 包含角色
	basicDwrService.getUserInfo(id,function(data){
		$("#belongOid").val(data.tOrganizationId);//机构
		$("#uid").val(data.id);//id
		//表单
		$("#uname").val(data.name);
		$("#uloginname").val(data.loginName);
		$("#umobile").val(data.mobile);
		$("#uemail").val(data.email);
		common.clearTrClass("roletable");//清楚表格被选中状态, 重置表格
		var roleArr = data.roleIds.split(",");
		for(var i=0; i<roleArr.length; i++){
			//console.info(roleArr[i]);
			common.clicktr(roleArr[i]+"role","success");//tr的id, 选中拥有的id行
			
		}
		userDiv.show();
		
		changePermissionTree();
	});//end of basicDwrService.getUserInfo
	
	
}

//删除用户
function deleteUser(id){
	//console.info(id);
	common.confirmMes('确定要删除此员工吗?',function(){
		basicDwrService.deleteUser(id, function(d){
			if(d == 'ok'){
				common.showOK();
				pageBar();//刷新表格
			}
		});
	},'question');
	
}//end deleteUser
</script>
	
	<style type="text/css">
	.topInfo{
		margin: 10px;
		padding: 4px;
		border-bottom: 1px solid #cdcfcf;
	}
	.userTable{
		margin: 0px 10px 0px 10px;
		/*border: 1px solid red;*/
	}
	.orgTitle{
		padding-left:7px;
		color: rgb(0, 176, 240);
	    font-size: 18px;
	    font-weight: bold;
    }
    .personName{
    	padding-left:7px;
    	font-size: 14px;
    	font-weight: bold;
    	text-overflow: ellipsis;
    	white-space: nowrap;
    }
    .smallName{
    	padding-left:30px;
    	color: #7e7e7e;
    	font-size: 12px;
    }
    .searchPanel{
    	padding-top:10px;
    	padding-bottom:10px;
    	text-align: left;
    	margin: 10px 0px 10px 0px;
    	/*border-bottom: 1px solid #ccc;*/
    }
    .searchPanel form input{
    	margin-left: 40px;
    	
    }
    .searchPanel form button{
    	margin-left: 40px;
    }
	</style>
	<div>
		<div class="topInfo">
			<div class="margin:5px;">
				<div class="orgTitle">
				<h3 style="height:30px;"><span id="biaotiTitle" style="cursor:pointer;font-size: 20px;" onclick="editOrganization(${organization.id});" class="label label-info">${organization.name }</span></h3>
				
				 <span class="label label-success">code:${organization.code }</span>
				<span class="label label-default">tel:${organization.tel }</span>
				<span class="personName">&nbsp;&nbsp;&nbsp;
				<c:if test="${oUser.name != null}">负责人：${oUser.name}(${oUser.loginName})&nbsp;&nbsp;&nbsp;&nbsp;  ${oUser.mobile }
				</c:if>
				</span>
				</div>
			</div>
		</div>
		<div class="userTable">
			<div class="searchPanel bg-warning">
			<form class="form-inline" action="${ctx }basic.do?action=userManage" method="post">
				<input type="hidden" value="${organization.id }" name="curOId" />
				<label for="name_">姓名:</label>
				<input class="form-control" type="text" placeholder="姓名" name="name" id="name_" />
				<label for="loginname_">工号:</label>
				<input class="form-control" type="text" placeholder="员工号" name="loginname" id="loginname_"/>
				<input id="search" type="button" class="btn btn-info btn-sm" value="查询"/>
				<input id="addNewUser" type="button" onclick="addNewUserf(${organization.id});" class="btn btn-warning btn-sm" value="新增"/>
			</form>
			</div>
		
			<table id="userTable" class="table table-hover table-condensed table-bordered ">
				<thead><tr>
					<th>序号</th>
					<!-- <th>id</th> -->
					<th>姓名</th>
					<th>手机</th>
					<th>邮箱</th>
					<th>拥有角色</th>
					<th>部门</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody id="usertbody">
				<!-- 
				<c:forEach items="${uList }" var="u" varStatus="ind" >
					<tr onclick="common.clicktr(${u.id },'info');" id="${u.id }">
						<td>${ind.index }</td>
						<td>${u.id }</td>
						<td>${u.name }(${u.loginName })</td>
						<td>${u.mobile }</td>
						<td>${u.email }</td>
						<td>${u.dbrolename }</td>
					</tr>
				</c:forEach>
				 -->
				</tbody>
			</table>
			<div id="pageBarDiv">
               
            </div>
		</div>
	</div>
	
		
		
