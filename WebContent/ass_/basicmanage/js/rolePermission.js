
Ext.define('roleModel', {
  extend: 'Ext.data.Model',
  fields: [
	  	{name:'id', type: 'int'}, 
		{name:'name', type: 'string'}, 
		{name:'rolevalue', type: 'string'}

  ]
});


/*
* 页面数据来源
*/
var roleStore = ass.store({
	url: common.ctx+"common.do?action=list",
	autoLoad: false,
	pageSize: 100,
	model:'roleModel'
});
//加载查询
roleStore.on('beforeload', function() {
	//重新设置参数，这些参数会通过url一起传到后台。
	roleStore.proxy.extraParams={ 
		statement : 'userManage_SpecSql.getRoles',
		queryAll : 1,
		sidx : 'id',
		sord : 'asc'
	};
});


// 定义模块列
var cm = [
  		{header : '角色id',dataIndex : 'id',hideable: false, hidden: true },
  		{header : '角色名称',dataIndex : 'name'},
  		{header : '简介',dataIndex : 'rolevalue'}
        ];

/*
 * 定义表格
 */
var roleGrid = ass.grid({
	title:'角色列表',
	region:'west',
	width: 227,
	//forceFit : true,
	sm:Ext.create('Ext.selection.CheckboxModel'),//
	cm:cm,//
	store : roleStore//
	//bbar: true
});

//****单击事件
roleGrid.addListener('itemclick',cellclick); 
//等同于  roleGrid.on('cellclick',cellclick);****
 //获取对应角色含有的权限，把右侧的对应的checkbox权限选中
function cellclick(grid, rowIndex, columnIndex, e) {
	//console.info(rowIndex.raw.htRoleId);
	var url = common.ctx + "rolePermission.do?action=getRolePermissionByRoleId&roleid="+rowIndex.raw.id;
	//console.info(common.ctx);
	common.jajax(url,function(data){
		$("#role").find("input[name='pcCheckbox']").attr("checked", false);
		//$("input:checkbox").attr("checked", false);//等同于上面一句
		var permissionArray = data.roleStr.split(",");
		for (var i = 0; i < permissionArray.length; i++) {
			var t = document.getElementById(permissionArray[i]);
			if(t != null){
				t.checked = true;
			}
		};
	});

} 





//******右键菜单
roleGrid.addListener('itemcontextmenu', itemcontextmenu);
// 调用的函数
function itemcontextmenu(his, record, item, index, e) {
	// itemcontextmenu( Ext.view.View this, Ext.data.Model record,
	// HTMLElement item, Number index, Ext.EventObject e, Object eOpts )
    // 分类代码表的右键菜单 
    e.preventDefault();  
    e.stopEvent();// 取消浏览器默认事件   
    var array = [ {  
                	text : '新建角色',  
                	icon: common.ctx+'images/icon/administrator3_add_16x16.gif',
	                handler : rolePermission.createRolePermission
	            }, 
	            {  
	                	text : '查看编辑角色',  
	                	icon: common.ctx+'images/icon/administrator3_edit_16x16.gif',
		                handler : rolePermission.editRolePermission
	            }, 
	            {  
	                	text : '删除角色',  
	                	icon: common.ctx+'images/icon/administrator3_delete_16x16.gif',
		                handler : rolePermission.delRolePermission
	            }];  
    var nodemenu = new Ext.menu.Menu({  
        items : array  
    });  
    nodemenu.showAt(e.getXY());// 菜单打开的位置  
};








//*****
Ext.onReady(function(){

	//整个页面的布局
	Ext.create('Ext.container.Viewport', {
		layout : "border",
		frame : true,
		
		items : [
			{  
			    region: 'center',  
			    xtype: 'panel',  
			    layout : "border",
			    frame: true,
			    tbar:[
			  	    {
			  	    	text:'新建角色',
			  	    	icon: common.ctx+'images/icon/administrator3_add_16x16.gif',
			  	    	handler:rolePermission.createRolePermission
			  	    },
			  	    '-',
			  	    {
			  	    	text:'查看编辑角色',
			  	    	icon: common.ctx+'images/icon/administrator3_edit_16x16.gif',
			  	    	handler:rolePermission.editRolePermission
			  	    },
			  	    '-',
			  	    {
			  	    	text:'删除角色',
			  	    	icon: common.ctx+'images/icon/administrator3_delete_16x16.gif',
			  	    	handler:rolePermission.delRolePermission
			  	    },
			  	    '->',
			  	    '-',
			  		{
			  	    	text:'保存角色',
			  	    	icon: common.ctx+'images/icon/administrator3_16x16.gif',
			  	    	handler:rolePermission.saveRolePermission
			  	    },
			  	    '-'
			  		],
			    items : [
					roleGrid,
					
					{  
					    region: 'center',  
					    title: '权限',  
					    xtype: 'panel',  
					    frame: true,
					    autoScroll : true,
					    contentEl: "authDiv"
					}
			             ]
			}
		]
	});
});


/////////////////////////////////////////////////////


//***用到的方法
var rolePermission = {
		checkParent : function(thi){
			var p = document.getElementById(thi.id);
			if(p.checked == true){
//				$("#" + $("#"+thi.id).attr("value2") ).attr("checked",true);//
				var par = document.getElementById($("#"+thi.id).attr("value2"));
				par.checked = true;
			}
			
		},

		checkSibings: function(thi){
			if(thi.checked){//$("#"+thi.id).attr("checked")
				//$("#"+thi.id).siblings("input").attr("checked","checked");
				var childs = $("#"+thi.id).siblings("input");
				for(var i =0; i<childs.length; i++){
					childs[i].checked = true;//childs[i].checked = 'checked';
				}
			}else{
				$("#"+thi.id).siblings("input").removeAttr("checked");
			}
		},
		saveRolePermission: function(){
			var rows = roleGrid.selectRows();
			if(rows.length < 1 ){
				common.showMes('提示', '最少选择一个角色','warn');
				return false;
			}
    		var htstr = "";
        	for(var i=0;i<rows.length;i++){
        		htstr += rows[i].get('id')+",";
        	}
        	$("#roleid").val(htstr);
        	
		    
		    $.ajax({
                type: "POST",
                url:common.ctx+ "rolePermission.do?action=saveRolePermission",
                data:$('#role').serialize(),// 你的formid
                async: true,
                error: rolePermission.showResponse2,
                success: rolePermission.showResponse
            });
		    
		    return false;
		},
		showResponse : function(data) {
			common.showMes("提示", "保存成功!");
		},
		showResponse2 : function(data){
			common.showMes("提示", "保存失败!");
		},

		roleInfoWin : new Ext.Window({
			
			margins: '10 10 10 10',
			layout: 'fit',
			shadow: false,
			closeAction: 'hide',
			width : 400,
			height: 180,
			modal:true,//屏蔽后面的网页
			constrain: true,//限制在窗口内部
			//draggable:false,//拖动
			resizable:false,//是否可以拉伸
			//contentEl: 'roleInfo',

			items : [
						{
							id : 'form1',
						    xtype: 'form',
						    height: 154,
						    width: 398,
						    bodyPadding: 10,
						    header: false,
						    title: 'My Form',
							frame : true,
						    autoScroll : true,
						    //url在form.submit(){
						    		//url: common.ctx+"basemanage/rolePermission.do?action=saveOrEditROle"//在这儿定义
								//}
						    //url : "basemanage/rolePermission.do?action=saveOrEditRole",//http://localhost:8080/zzyh/basemanage/basemanage/roleAuth.do?action=saveOrEditRole
						    //url : "/basemanage/roleAuth.do?action=saveOrEditRole",//http://localhost:8080//basemanage/roleAuth.do?action=saveOrEditRole
						    //url : common.ctx + "basemanage/roleAuth.do?action=saveOrEditRole",//common.ctx 加载不出来
						    items: [
						        {
						            xtype: 'textfield',
						            anchor: '100%',
						            id: 'roleInfoName',
						            fieldLabel: '角色名称',
						            allowBlank: false,
						            name : 'name'
						        },
						        {
						            xtype: 'textfield',
						            anchor: '100%',
						            id: 'roleInfoNote',
						            width: 150,
						            fieldLabel: '角色简介',
						            name : 'note'
						        },
						        {
						            xtype: 'hiddenfield',
						            anchor: '100%',
						            id: 'roleInfoId',
						            width: 150,
						            fieldLabel: 'Label',
						            name : 'roleid'
						        }
						    ],
						    buttons: [{ 
						          text: '保存',
						          handler: function(){
						        	  /*
						        	  var data = {
						        			  'roleid': $("#roleInfoId").val(),
						        			  'name': $("#roleInfoName").val(),
						        			  'note': $("#roleInfoNote").val()
						        	  };*/
						        	  //console.info(data);

						        	  var form1 = this.up('form').getForm();
						        	  
						        	  if(form1.isValid()){//验证条件
						        		  form1.submit({
							        		  url:common.ctx + "rolePermission.do?action=saveOrEditRole",
							        		  success:function(form, action){
								        			//  console.info(form);
								        			//  console.info(action.result.msg);
							        			  	//var da = eval('('+d+')');
							                        //Ext.Msg.alert('提示',"操作成功"); 
							                        common.showMes("提示", action.result.msg); 
							                        rolePermission.roleInfoWin.hide();
									        		roleStore.load();
							                    },
							                  failure:function(form, action){
							                	  common.showMes('提示',"操作失败",'danger'); 
							                  }						        		  
							        	  });
						        	  }
						        	  
						          }
						      },{             
						    	  text: '关闭' ,
						    	  handler: function(){
						    		  rolePermission.roleInfoWin.hide();
						    	  }
						      }] 
						}
			         ]
		}),
		
		/**新建*/
		createRolePermission : function(){
			var form1 = Ext.getCmp("form1").getForm();
			form1.setValues({roleInfoId: '0', roleInfoName:'', note:''});
			rolePermission.roleInfoWin.setTitle('新增角色');
			rolePermission.roleInfoWin.show();
			
		},
		
		
		/**编辑*/
		editRolePermission : function(){
			
			var rows = roleGrid.selectRows();
			if(rows.length != 1 ){
				common.showMes('提示', '请选择单条记录','warn');
				return false;
			}
			var url = common.ctx+ "rolePermission.do?action=getRoleInfo&roleid="+rows[0].get('id');
			common.jajax(url, function(d){
				var form1 = Ext.getCmp("form1").getForm();
				form1.setValues({roleInfoId: d.id, roleInfoName:d.name, note:d.roleValue});
				rolePermission.roleInfoWin.setTitle('查看角色');
				roleAuth.roleInfoWin.show();
			});
			
			
			
		},
		/**删除*/
		delRolePermission : function(){
			var rows = roleGrid.selectRows();
			if(rows.length<1){
				common.showMes('提示', '至少选择一条记录','warn');
				return false;
			}
			
		    Ext.Msg.confirm("警告", "拥有这个角色的用户将会失去这个角色，确定要删除吗？", function(button,text){
		    	if(button=='yes'){
		    		var htRoleIds=[];
		        	for(var i=0;i<rows.length;i++){
		        		htRoleIds.push(rows[i].get('id'));
		        	}
	    				//删除角色操作开始----------------
			        	var url = common.ctx + 'rolePermission.do?action=deleteRole&roleIds='+htRoleIds;
			        	common.jajax(url,function(data){
			        		//console.info(data);
			        		if(data.success == true){
			        			//Ext.Msg.alert('提示', '删除成功');
			        			common.showMes("提示", "删除成功",'success');
			        			roleStore.load();
			        		}else{
			        			common.showMes('提示', '删除失败','danger');
			        		}
			        	});
				       //删除角色操作结束----------------
		    	}
		    	
		    });
		}
		


		
};
















