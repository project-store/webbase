/**
 * 
 */

//页面初始化
$(document).ready(function(){
		//2初始化右侧页面
		loadRightPage(1);
		
		
		//5 初始化 下拉框
		common.refreshSelect('inchargeUser','user');
		
		
		
		
		//6保存 用户按钮事件
		$("#saveUserBtn").click(function(){
			if(!stringCheck($("#uname").val())){
				common.alertMes('姓名输入有误',function(){$("#uname").focus();},'info');
				return false;
			}
			if(!isNull($("#uloginname").val())){
				common.alertMes('员工号输入有误',function(){$("#uloginname").focus();},'info');
				return false;
			}
			//获得表格中所有被选中的角色
			var ids = common.getIds('roletable','success');
			var data1 = $("#uForm").serialize();
			data1 = data1 +"&roleids="+ids;
			var url = common.ctx+"basic.do?action=saveUser";
			common.dajax(url,data1,function(data){
				if(data.success){
					common.showOK();
					userDiv.hide();
					pageBar();//重新加载数据就行
					//loadRightPage(data.id);//刷新右侧
					
				}else{
					common.alertMes(data.msg,'warning');
				}
			});
			return false;
		});
		
		
		
		
		
});//end of document.ready***********








//根据机构id  加载右侧 机构和员工信息
function loadRightPage(id){
	var url = common.ctx +"basic.do?action=userManage&id="+id;
		$.ajax({
			type: "POST",
			url: url + '&random=' + Math.random(),
			dataType: "html",
			success: function(html){
				$("#contentright").html(html);
			}
		});
		
	}
	

//角色表格点击之后,
function clicktr2(id, type){
	common.clicktr(id,type);//1改变样式
	changePermissionTree();//2刷新权限树
}

//bui begin************************************************

//定义organizationDiv 的显示框
BUI.use('bui/overlay',function(Overlay){
		organizationDiv = new Overlay.Dialog({
        title:'机构信息',
        width:340,
        height:350,
        effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
        closeAction : 'hide', 
        //配置DOM容器的编号
        contentId:'organizationDiv_',
        buttons:[
                 {
                   text:'保存',
                   elCls : 'btn btn-primary',
                   handler : function(){

   	    			if(!stringCheck($("#organizationName").val())){
   	    				common.alertMes('机构名称不合法',function(){$("#organizationName").focus();},'info');
   	    				
   	    				return false;
   	    			}
   	    			if(!isNull($("#organizationCode").val())){
   	    				
   	    				common.alertMes('机构编码有误',function(){$("#organizationCode").focus();},'info');
   	    				return false;
   	    			}
   	    			
   	    			var data = $("#oForm").serialize();
   	    			var url = common.ctx+"basic.do?action=saveOrEditOrganization";
   	    			var closeDiv;
   	    			common.dajaxTB(url,data,function(data){
   	    				if(data.code =='ok'){
   	    					common.showOK();
   	    					//刷新树
   	    					store.load();
   	    					
   	    					if(data.flag == 'edit'){//编辑的话，重新刷新信息
   	    						loadRightPage(data.id);
   	    					}
   	    					closeDiv = 1;
   	    				}else{
   	    					common.alertMes(data.code,'info');
   	    					closeDiv = 0;
   	    				}
   	    				
   	    			});
   	    			if(closeDiv == 1){
   	    				this.close();
   	    			}
   	    			
                   }
                 },{
                   text:'关闭',
                   elCls : 'btn btn-primary',
                   handler : function(){
                     this.close();
                   }
                 }
               ]
      });
		

		//定义organizationDiv 的显示框
	  userDiv = new Overlay.Dialog({
      title:'用户信息',
      width:720,
      effect :{
    	      	effect: 'fade',//进入效果
    	      	duration: 350
    	      },
      height:386,
      closeAction : 'hide', 
      //配置DOM容器的编号
      contentId:'userInfoDiv_',
      buttons:[/*
               {
                 text:'保存',
                 elCls : 'btn btn-primary',
                 handler : function(){
                 }
               },{
                 text:'关闭',
                 elCls : 'btn btn-primary',
                 handler : function(){
                   this.close();
                 }
               }*/
             ]
    });
		
		
});


BUI.use('bui/form',function(Form){
	new Form.Form({
	srcNode : '#oForm'
	}).render();
	}); 

BUI.use(['bui/tree','bui/data','bui/menu'],function (Tree,Data,Menu) {
	//数据缓冲类
	store = new Data.TreeStore({
//	root : {
//	id : '0',
//	text : '0',
//	checked : false
//	},
//	pidField : 'pid',
	url : common.ctx + "basic.do?action=organizationTree",
	autoLoad : true
	});
	var otree = new Tree.TreeList({
		render : '#treeDemo',
		showLine : true,
		store : store,
		checkType : 'none',
		showRoot : false,
		dirCls : 'tree_dir',
		leafCls : 'tree_leaf',
	});
	
	
	//点击事件
	otree.on('itemclick',function(ev){
		var item = ev.item;
//		console.info(item.id);
//		console.info(item.text);
		loadRightPage(item.id);
	});
	
	 function showOrganizationDiv(treeid){
		organizationDiv.show();
		//重置表单
		$("#oForm")[0].reset();
		$("#OrganId").val(treeid);
		$("#flag").val("add");
		$("#organizationName").focus();
		omenu.hide();
		return false;
	};
	
	//右键
 	otree.on('itemcontextmenu',function(ev){
		 var item = ev.item;
		 otree.setSelected(item);
		 omenu = new Menu.ContextMenu({
			 children : [
			 {
				 iconCls:' icon-plus',
				 text : '新建',
				 listeners:{
					 'click':function(){
						 showOrganizationDiv(item.id);
					 }
				 }
			 },
			 {xclass:'menu-item-sparator'},
			 {
				 iconCls:'icon-remove',
				 text: '删除',
				 listeners:{
					 'click':function(){
						 common.confirmMes('确定要删除该节点吗?',function(){
							 basicDwrService.deleteOrganization(item.id, function(d){
									if(d.isok == '1'){
										//刷新树
										//console.info(1);
										store.load();
										common.showOK();
									}else{
										//console.info(2);
										common.alertMes(d.msg,'info');
									}
								});
						 },'question');
					 }
				 }
			 }
			 ]
			 });
		 
		 omenu.set('xy',[ev.pageX,ev.pageY]);
		 omenu.show();
		 
		 
		
			
		 return false; //prevent the default menu
	 });
 	otree.render();
	
 	
 	
 	
 	//权限树显示
 	storePermission = new Data.TreeStore({
// 		root : {
// 		id : '0',
// 		text : '0',
// 		checked : false
// 		},
// 		pidField : 'pid',
 		url : common.ctx + "basic.do?action=permissionTree",
 		autoLoad : true
 		});
 		 ptree = new Tree.TreeList({
 			render : '#permissionTree',
 			showLine : true,
 			store : storePermission,
 			checkType : 'none',
 			showRoot : false
 		});
 		ptree.render();
});

//bui end ******************************************************

function changePermissionTree(){
	var ids = common.getIds('roletable','success');
	storePermission.load({'ids':ids});
	
};

		
		
