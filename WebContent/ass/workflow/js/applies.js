
//休假***************************************begin
BUI.use('bui/overlay',function(Overlay){
		vacationD = new Overlay.Dialog({
        title:'休假申请',
        //width:340,
        //height:350,
        effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
        closeAction : 'hide', 
        //配置DOM容器的编号
        contentId:'vacationDiv_',
        buttons:[
               ]
      });
		
});

//保存休假
function save(flag){
	//校验
	if(!isNull($("#vacationType").val())){
		common.alertMes('请选择休假类型.',function(){$("#vacationType").focus();},'info');
		return false;
	}
//	console.info($("#beginTime").val());
//	if(!isNull($("#beginTime").val())){
//		common.alertMes('请输入开始时间.',function(){$("#beginTime").focus();},'info');
//		return false;
//	}
//	if(!isNull($("#endTime").val())){
//		common.alertMes('请输入结束时间.',function(){$("#endTime").focus();},'info');
//		return false;
//	}
	if(!isNumber($("#appHours").val())){
		common.alertMes('申请小时输入有误.',function(){$("#appHours").focus();},'info');
		return false;
	}
	
	var data = $("#vacationForm").serialize();
	var url = common.ctx + "vacation.do?action=saveVacation";
	var result;
	common.dajaxTB(url,data,function(d){
		if(d.ok == 'ok'){
			if(flag == 1){
				common.showOK();
			}
			result = d.workflowtaskid;
			vacationD.hide();
			
			
		}else{
			result = 0;
			common.alertMes(d.ok,'info');
		}
	});
	return result;
}
//休假***************************************end



//出差***************************************begin
BUI.use('bui/overlay',function(Overlay){
		travelD = new Overlay.Dialog({
        title:'外出申请',
        //width:340,
        //height:350,
        effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
        closeAction : 'hide', 
        //配置DOM容器的编号
        contentId:'travelDiv_',
        buttons:[
               ]
      });
		
});

//保存外出申请
function saveTravel(flag){
//	console.info($("#beginTime").val());
//	if(!isNull($("#beginTime").val())){
//		common.alertMes('请输入开始时间.',function(){$("#beginTime").focus();},'info');
//		return false;
//	}
//	if(!isNull($("#endTime").val())){
//		common.alertMes('请输入结束时间.',function(){$("#endTime").focus();},'info');
//		return false;
//	}
	if(!isNumber($("#appHours").val())){
		common.alertMes('申请小时输入有误.',function(){$("#appHours").focus();},'info');
		return false;
	}
	
	var data = $("#travelForm").serialize();
	var url = common.ctx + "travel.do?action=saveTravel";
	var result;
	common.dajaxTB(url,data,function(d){
		if(d.ok == 'ok'){
			if(flag == 1){
				common.showOK();
			}
			result = d.workflowtaskid;
			travelD.hide();
			
			
		}else{
			result = 0;
			common.alertMes(d.ok,'info');
		}
	});
	return result;
}
//出差***************************************end




$(function(){
	//保存休假
	$("#saveVacationBtn").click(function(){
		save(1);
	});
	
	$("#saveVacationSubmitBtn").click(function(){
		var workflowtaskId = save(2);
		//$("#reason").val();
		//console.info(workflowtaskId);
		if(workflowtaskId != 0){
			approve.show($("#reason").val(), workflowtaskId, function(){} );
		}
	});
	
	
	//保存外出
	$("#saveTravelBtn").click(function(){
		saveTravel(1);
	});
	
	$("#saveTravelSubmitBtn").click(function(){
		var workflowtaskId = saveTravel(2);
		//$("#reason").val();
		//console.info(workflowtaskId);
		if(workflowtaskId != 0){
			approve.show($("#reasonT").val(), workflowtaskId, function(){} );
		}
	});
	
	
});