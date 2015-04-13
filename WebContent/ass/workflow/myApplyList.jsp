<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我发起的工作</title>
<%@include file="/common/jsp/taglib.jsp"%>
<%@include file="/ass/workflow/choiceApprover.jsp"%>
<script type='text/javascript' src="${ctx }dwr/interface/workflowDwrService.js"></script>

<script type="text/javascript">

$(function(){
	pageBar();
	
	////刷新下拉菜单 <select>标签id,  action地址, 参数
	common.refreshSelect('approvestatus','code2nameOfProperties','workflowtask_status');
	
	//查询按钮事件
	$("#search").click(function(){
		pageBar();
	});
});



//查询方法  供pageBar()方法调用
function search(page){
		$("#usertbody").html("数据加载中...请稍后...");
		var d = {
				page : page,//当前页码
				limit : '15', //一页显示的记录数
				statement : 'workFlow_SpecSql.myApplyList',
				title : $("#title").val(),//!!
				approvestatus : $("#approvestatus").val(),//!!
				appBeginTime: $("#appBeginTime").val(),
				appEndTime: $("#appEndTime").val()
		};
		var url = common.ctx + "common.do?action=list";
		common.dajax(url, d, function(data){
			var lst = data.rows;
			var innerHtml1 = '';//!!
			for(var i=0; i<lst.length; i++){//!!
				innerHtml1 += "<tr onclick=common.clicktr("+lst[i].id+",'info'); ondblclick=approve.seeDetail("+lst[i].id+",'"+lst[i].title+"',function(){pageBar();}) id="+lst[i].id+">"
				+"<td>"+(i+1)+"</td>"
				// +"<td>"+lst[i].id+"</td>"
				+"<td>"+lst[i].title+"</td>"
				+"<td  class='text-success'>"+lst[i].participantName+"</td>"
				+"<td>"+lst[i].appDate+"</td>"
				+"<td>"+lst[i].make_time+"</td>"
				+"<td class='text-danger'>"+lst[i].statusName+"</td>";
				//00未提交01审批中02退回03已通过04作废    00,02 可以 提交,作废
				if('00' == lst[i].status || '02' == lst[i].status){
					innerHtml1 +="<td><a title='提交审批' href='javascript:void(0)' onclick='submitApprove("+lst[i].id+")'>提交审批</a>&nbsp;&nbsp;"
					+"<a title='作废' href='javascript:void(0)' onclick='deleteWorkflow("+lst[i].id+")'>作废</a>&nbsp;&nbsp;&nbsp;&nbsp;"
					+"<a title='详情' href='javascript:void(0)' onclick=approve.seeDetail("+lst[i].id+",'"+lst[i].title+"',function(){pageBar();}) >详情(双击行)</a>"
					+"</td>";
				}else{
					innerHtml1 +="<td><a title='提交审批' style='color:gray' href='javascript:void(0)' >提交审批</a>&nbsp;&nbsp;"
					+"<a title='作废' href='javascript:void(0)' style='color:gray' >作废</a>&nbsp;&nbsp;&nbsp;&nbsp;"
					+"<a title='详情' href='javascript:void(0)' onclick=approve.seeDetail("+lst[i].id+",'"+lst[i].title+"',function(){pageBar();}) >详情(双击行)</a>"
					+"</td>";
				}
				innerHtml1 +="</tr>";
			}
			//tbody
			$("#applyListtbody").html(innerHtml1);//!!
  });
	
}

//设置分页栏  连带加载表格
function pageBar(){
	//1先获得总的页数
	var d = {
			page : 1,//!!***  当前页码
			limit : '15', //一页显示的记录数
			statement : 'workFlow_SpecSql.myApplyList',
			title : $("#title").val(),//!!
			approvestatus : $("#approvestatus").val(),//!!
			appBeginTime: $("#appBeginTime").val(),
			appEndTime: $("#appEndTime").val()
	};
	var url = common.ctx + "common.do?action=list";
	common.dajax(url, d, function(data){
		if(data.rows.length == 0){
			$("#applyListtbody").html("没有数据");
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

//提交审批
function submitApprove(workflowtaskid){
	approve.show("同意", workflowtaskid, function(){ pageBar(); } );
}

//作废记录
function deleteWorkflow(workflowtaskid){
	common.confirmMes('确定要作废此条记录吗',function(){
		workflowDwrService.invalidWorkflowTask(workflowtaskid,function(data){
			if(data.ok == 'ok'){
				common.showOK();
				pageBar();
			}
		});
	 },'question');
}
</script>

<style type="text/css">
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
    input {
    	margin-left:4px !important;
    }
</style>
</head>
<body>


<div style="margin:10px;">
		<div class="userTable">
			<div class="searchPanel bg-warning">
			<form class="form-inline"  method="post">
				<label for="title">名称:</label>
				<input class="form-control" type="text" placeholder="名称" name="title" id="title" />
				<label for="approvestatus">审批状态:</label>
				<select class="form-control"  name="approvestatus" id="approvestatus"/>
				</select>
				<label for="appBeginTime">申请开始时间:</label>
				<input type="text"  class="calendar calendar-time" id="appBeginTime" />
				<label for="appEndTime">申请结束时间:</label>
				<input type="text"  class="calendar calendar-time" id="appEndTime" />
				
				<input  id="search" type="button" class="btn btn-info btn-sm" value="查询"/>
				<input  id="reset" type="reset" class="btn btn-default btn-sm" value="重置"/>
			</form>
			</div>
		
			<table id="applyListTable" class="table table-hover table-condensed table-bordered ">
				<thead><tr>
					<th>序号</th>
					<!-- <th>id</th> -->
					<th>名称</th>
					<th>当前审批人</th>
					
					<th>申请日期</th>
					<th>创建日期</th>
					<th>审批状态</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody id="applyListtbody">
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
	
	
	






 <script type="text/javascript">
 
 //时间控件
 BUI.use('bui/calendar',function(Calendar){
	    var datepicker = new Calendar.DatePicker({
	      trigger:'.calendar-time',
	      showTime:true,
	      autoRender : true,
	      lockTime : { //可以锁定时间，hour,minute,second
	          //hour : 12,
	          //minute:30,
	          second : 00
	        },
	        dateMask : 'yyyy-mm-dd HH:MM'
	    });
	});
	 

 </script>



</body>
</html>