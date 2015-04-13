<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
BUI.use('bui/overlay',function(Overlay){
	//审批框
	approvalD = new Overlay.Dialog({
    title:'审批流程',
    width:550,
    //height:600,
    effect :{
      	effect: 'fade',//进入效果
      	duration: 350
      },
    closeAction : 'hide', 
    //配置DOM容器的编号
    contentId:'approvalDiv_12',
    buttons:[
				{
				    text:'保存',
				    elCls : 'btn btn-primary btn2' ,
				    handler : function(){
				    	if(!isNull($("#comment_1").val())){
	   	    				common.alertMes('请输入意见.',function(){$("#comment_1").focus();},'info');
	   	    				return false;
	   	    			}
				    	if(!isNull($("#participant_1").val())){
	   	    				common.alertMes('请选择下一步审批人.',function(){$("#participant_1").focus();},'info');
	   	    				return false;
	   	    			}
				    	
				    	//提交审批 or 下一步审批. workflow_task, 
				    	//status00:未提交,  当前步骤为提交申请
				    	//status02审批中, 当前步骤为下一步.
				    	
				    	var url = common.ctx + "workflow.do?action=applyOrNext";
				    	var data = {
				    			comment: $("#comment_1").val(),
				    			nextParticipant: $("#participant_1").val(),
				    			workflowTaskId: $("#workflowTaskId_1").val()
				    	}
				    	common.dajax(url, data, function(data){
				    		if(data.ok == "ok"){
				    			common.showOK();//提示成功
				    			approvalD.close();//关闭选择审批人的层
				    			approve.callback();//执行回调函数, 一般为刷新表格. 
				    		}else{
				    			common.alertMes(data.ok,'info');
				    		}
				    	});
				    	
				    }
				},
				{
                   text:'关闭',
                   elCls : 'btn btn-primary btn2',
                   handler : function(){
                     this.close();
                   }
                 }
           ]
  });
	//确认通过框
	agreeDiv = new Overlay.Dialog({
	    title:'确认通过',
	    width:400,
	    //height:600,
	    effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
	    closeAction : 'hide', 
	    //配置DOM容器的编号
	    contentId:'agreeDiv_',
	    buttons:[
					{
					    text:'确认通过',
					    elCls : 'btn btn-primary btn2' ,
					    handler : function(){
					    	workflowDwrService.agree($("#workflow_task_id").val(),$("#agreecomment").val(),function(d){
					    		if(d.ok == 'ok'){
					    			common.showOK();
					    			agreeDiv.close();
					    			approve.callback();
					    		}
					    	});
					    }
					},
					{
	                   text:'关闭',
	                   elCls : 'btn btn-primary btn2',
	                   handler : function(){
	                     this.close();
	                   }
	                 }
	           ]
	  });
	//退回框	
	backDiv = new Overlay.Dialog({
	    title:'退回',
	    //width:550,
	    //height:600,
	    effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
	    closeAction : 'hide', 
	    //配置DOM容器的编号
	    contentId:'backDiv_',
	    buttons:[
					{
					    text:'退回',
					    elCls : 'btn btn-primary btn2' ,
					    handler : function(){
					    	workflowDwrService.back($("#workflow_task_id").val(),$("#backcomment").val(),function(d){
					    		if(d.ok == 'ok'){
					    			common.showOK();
					    			backDiv.close();
					    			approve.callback();
					    		}
					    	});
					    }
					},
					{
	                   text:'关闭',
	                   elCls : 'btn btn-primary btn2',
	                   handler : function(){
	                     this.close();
	                   }
	                 }
	           ]
	  });
	
	//审批详情窗口
	workflowDetailDiv = new Overlay.Dialog({
	    title:'审批详情',
	    width:350,
	    //height:600,
	    effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
	    closeAction : 'hide', 
	    //配置DOM容器的编号
	    contentId:'workflow_detail_div',
	    buttons:[
					{
					    text:'提交',
					    elCls : 'btn btn-primary btn2' ,
					    handler : function(){
					    	approve.show('同意',$("#workflow_task_id").val(),approve.callback);
					    	workflowDetailDiv.close();
					    }
					},
					{
	                   text:'确认通过',
	                   elCls : 'btn btn-primary btn2',
	                   handler : function(){
	                	    approve.agree($("#workflow_task_id").val(),approve.callback);
					    	workflowDetailDiv.close();
	                	   
	                   }
                 	},
                	 {
	                   text:'退回',
	                   elCls : 'btn btn-primary btn2',
	                   handler : function(){
	                	    approve.back($("#workflow_task_id").val(),approve.callback);
					      	workflowDetailDiv.close();
	                	   
	                   }
	                 },
					{
	                   text:'关闭',
	                   elCls : 'btn btn-primary btn2',
	                   handler : function(){
	                	   workflowDetailDiv.close();
	                   }
	                 }
	           ]
	  });
		
});


	$(function(){
		////刷新下拉菜单 <select>标签id,  action地址, 参数
		//common.refreshSelect('participant_1','user');
		
	});
	
	//常用审批人, 单击事件, 设置下拉框的值
	function approvers(uid){
		$("#participant_1").val(uid);
	}
	
	var approve = {
		callback: "",
		//提交审批的 选择下一步审批人页面
		show: function(comment,workflowtaskId,callback){
			
			approve.callback = callback;//保存完后执行. 刷新页面的表格信息 
			
			common.refreshSelect('participant_1','user');
			
			//1设置审批意见的默认值
			$("#comment_1").val(comment);
			//设置任务表id 
			$("#workflowTaskId_1").val(workflowtaskId);
			
			//2 加载当前登录人的常用审批人列表
			var url = common.ctx + "workflow.do?action=getUsualApprover";
			common.jajax(url,function(data){
				$("#approvers").html("正在加载常用审批人...");
				var str = "";
				for(var i=0; i<data.length; i++){
					str += "<div  class='smallperson'><button class='btn btn-success btn-xs' onclick='approvers("+data[i].uid+")' >"+data[i].uname+"</button><button class='btn btn-warning btn-xs' onclick='approve.removeOne(this,"+data[i].uid+")'>X</button></div>";
				}
				$("#approvers").html(str);
			});
			
			approvalD.show();
			return false;
		},
		
		removeOne: function(thi,uid){
			//1 页面隐藏
			$(thi).parent().hide("slow");
			//2 ajax删除
			var url = common.ctx + "workflow.do?action=deleteUsualApprover&approverId="+uid;
			common.jajax(url,function(){});
		},

		//通过
		agree: function(workflowtaskid,callback){
			approve.callback = callback;//保存完后执行. 刷新页面的表格信息 
			$("#workflow_task_id").val(workflowtaskid);
			$("#agreecomment").val("确认通过");
			agreeDiv.show();
		},

		//详情
		seeDetail: function(workflowtaskid,title,callback){
			approve.callback = callback;
			$("#workflow_task_id").val(workflowtaskid);
			$("#titleDetail").html(title);
			var url = common.ctx + "workflow.do?action=taskDetailComments&workflowtaskid="+workflowtaskid;
			common.jajax(url,function(d){
				$("#comments_").html(d.comments);
			})
			
			workflowDetailDiv.show();
		},

		//退回
		back: function(workflowtaskid,callback){
			approve.callback = callback;//保存完后执行. 刷新页面的表格信息 
			$("#workflow_task_id").val(workflowtaskid);
			$("#backcomment").val("");
			backDiv.show();
		}
			
	}
	
</script>
<style type="text/css">
	
	#approvers{
		overflow: auto;
		/*max-height: 150px;*/
	}
	
	
	.smallperson{
		width: 33%;
		float:left;
		height:23px;
		margin:4px 4px;
		font-size: 14px;
		
	}

</style>

<div style="display:none;" id="approvalDiv_12">
<input type="hidden" id="workflowTaskId_1" />
		<div>
		    <label for="comment_1" ><span style='color:red;'>*</span>意见:</label>
		    <div >
		    	<input type="text" class="form-control " style="width:80%" id="comment_1" name="comment_1" >
		    </div>
	  	</div>
	  	
	  	<div >
		    <label class="control-label " ><span style='color:red;'>*</span>下一步审批人:</label>
		    <div >
			    <select  class="form-control" style="width:80%" id="participant_1" name="participant_1" >
				</select>
			</div>
	  	</div>
	  	<div  class="row" style="margin-left:10px">
	  	常用审批人:
	  	</div>
	  	<div id="approvers" class="row" style="margin-left:5px;">
	  	<!-- 
	  		<div  class="smallperson">
	  			<button class="btn btn-success btn-xs" >dsfsf</button><button class="btn btn-warning btn-xs" onclick="approve.removeOne(this)" >X</button>
	  		</div>
	  		 -->
	  	</div>

</div>



<input type="hidden" id="workflow_task_id" />
<!-- 确认通过窗口 -->
<div id="agreeDiv_" style="display:none;">
<label>意见:</label>
	<input type="text" class="controls" style="width: 280px;" id="agreecomment" />
</div>
<!-- 退回窗口 -->
<div id="backDiv_" style="display:none;">
<label>意见:</label>
	<input type="text" class="controls" style="width: 280px;" id="backcomment" />
</div>

<!-- 详情窗口 -->

<div id="workflow_detail_div" style="display:none">
<h3 id="titleDetail" class="bg-warning">某某的休假申请</h3>
	<div id="comments_" style="height:300px;overflow:auto;">
	 </div>
</div>









