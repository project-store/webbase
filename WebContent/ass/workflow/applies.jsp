<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提申请</title>
<%@include file="/common/jsp/taglib.jsp"%>
<%@include file="/ass/workflow/choiceApprover.jsp"%>
<script type='text/javascript' src="${ctx }ass/workflow/js/applies.js"></script>

<style type='text/css'>
	.module{
		width:200px;
		float:left;
		margin: 10px 10px;
		cursor: pointer;
	}
</style>
<script type="text/javascript">

//时间控件 js 放到最后


//页面初始化
$(document).ready(function(){
		//1 初始化 下拉框
		////刷新下拉菜单 <select>标签id,  action地址, 参数
		common.refreshSelect('vacationType','code2nameOfProperties','vacation_type');
});


	function approval(type){
		//1 休假 , 2外出 , 3报销
		if(type == 1){
			vacationD.show();
			//document.forms["vacationForm"].reset();  
			//document.vacationForm.reset();  
			 $("#vacationForm")[0].reset();
		}else if(type == 2){
			travelD.show();
			$("#travelForm")[0].reset();
		}else if(type == 3){
			
		}else{
			//do nothing
		}
		
	}
	
</script>
</head>
<body>

<div class="panel panel-success module" onclick="approval(1);">
<div class="panel-heading">
    <h3 class="panel-title">休假申请</h3>
  </div>
  <div class="panel-body">
    提出事假, 病假等申请
  </div>
</div>

<div class="panel panel-success module" onclick="approval(2);">
<div class="panel-heading">
    <h3 class="panel-title">外出申请</h3>
  </div>
  <div class="panel-body">
    外出登记
  </div>
</div>

<div class="panel panel-success module" onclick="approval(3);">
<div class="panel-heading">
    <h3 class="panel-title">报销申请</h3>
  </div>
  <div class="panel-body">
    费用报销申请
  </div>
</div>
















<!-- 提休假 -->
<div class="vacationDiv" id='vacationDiv_'  style="display:none;" >
    <div class="row">
      <div class="span16 doc-content">
         <form class="form-horizontal well" id="vacationForm" style="background-color: #fff !important;">
         <input type="hidden" value="01" name="source" />
            <div class="row">
              <div class="control-group span8">
                <label class="control-label">休假类型：</label>
                <div class="controls">
                  <select name="vacationType" class="input-normal"  id="vacationType"  >
				  </select>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="control-group span8">
                <label class="control-label">开始时间：</label>
                <div class="controls">
                  <input type="text"  class="calendar calendar-time" name="beginTime" />
                </div>
              </div>
              <div class="control-group span8">
                <label class="control-label">结束时间：</label>
                <div class="controls">
                  <input type="text" class="calendar calendar-time" name="endTime" />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="control-group span8">
                <label class="control-label">申请小时：</label>
                <div class="controls">
                  <input type="text"  name='appHours' id="appHours" />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="control-group span16">
                <label class="control-label">原因：</label>
                <div class="controls">
                  <input type="text" name='reason' id="reason" style="width:200%;" />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="form-actions offset3">
                <button type="button" class="button button-primary" id="saveVacationBtn">保存</button>
                <button type="button" class="button button-primary" id="saveVacationSubmitBtn">保存并提交</button>
                <button type="reset" class="button">重置</button>
              </div>
            </div>
          </form>
      </div>
    </div>
 </div>


<!-- 提出差 -->
<div class="travelDiv" id='travelDiv_'  style="display:none;" >
    <div class="row">
      <div class="span16 doc-content">
         <form class="form-horizontal well" id="travelForm" style="background-color: #fff !important;">
         <input type="hidden" value="01" name="source" />
         <!-- 
            <div class="row">
              <div class="control-group span8">
                <label class="control-label">外出类型：</label>
                <div class="controls">
                  <select name="vacationType" class="input-normal"  id="vacationType"  >
				  </select>
                </div>
              </div>
            </div>
             -->
            <div class="row">
              <div class="control-group span8">
                <label class="control-label">开始时间：</label>
                <div class="controls">
                  <input type="text"  class="calendar calendar-time" name="beginTime" />
                </div>
              </div>
              <div class="control-group span8">
                <label class="control-label">结束时间：</label>
                <div class="controls">
                  <input type="text" class="calendar calendar-time" name="endTime" />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="control-group span8">
                <label class="control-label">申请小时：</label>
                <div class="controls">
                  <input type="text"  name='appHours' id="appHours" />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="control-group span16">
                <label class="control-label">原因：</label>
                <div class="controls">
                  <input type="text" name='reason' id="reasonT" style="width:200%;" />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="form-actions offset3">
                <button type="button" class="button button-primary" id="saveTravelBtn">保存</button>
                <button type="button" class="button button-primary" id="saveTravelSubmitBtn">保存并提交</button>
                <button type="reset" class="button">重置</button>
              </div>
            </div>
          </form>
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