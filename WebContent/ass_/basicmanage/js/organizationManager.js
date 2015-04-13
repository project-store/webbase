
































var organizationStore = new Ext.data.TreeStore({
 	autoLoad:true,
	proxy:{
		type:'ajax',							
		url:common.ctx+"basic.do?action=organizationTree",
		actionMethods:{
               		create: "POST", 
               		read: "POST", 
               		update: "POST", 
               		destroy: "POST"
         		}
	}
 });




var organizationTree = Ext.create('Ext.tree.Panel', {
	title:'组织机构',
	region:'west',
	width : 249,
	collapsible : true,//可折叠
	rootVisible: false,
    frame: true,	
    autoScroll:true,
	store: organizationStore
});

//创建树的点击事件
organizationTree.on("itemClick",function(view, record, index, e){
	var organizationId = record.raw.id;

	//var name = record.raw.text;
	//ajax获取此节点相关的信息，在页面右上方显示出来
	var url = common.ctx + "basic.do?action=getOrganizationDetail";
	var data = {tOrganizationId : organizationId};
		$("#name_").html("");
		$("#code_").html("");
		$("#incharge_user").html("");
		$("#tel_").html("");
		$("#phone_").html("");
		$("#introduce_").html("");
	common.dajax(url, data, function(d){
		//console.info(d);
		$("#name_").html(d.name);
		$("#code_").html(d.code);
		$("#incharge_user").html(d.uname);
		$("#tel_").html(d.tel);
		$("#phone_").html(d.phone);
		$("#introduce_").html(d.introduce);

	});
	
	
});


//右键菜单
organizationTree.on("itemcontextmenu",function( view, record, item, index, e){

	//console.info(record.raw.leaf);
	var	array ;
	//console.info(record.raw.pid);
	if(record.raw.pid == 0){
		array = [
					{text: "查看编辑信息", handler: function(){ openWin('edit',  record.raw.id); }, icon: common.ctx+'images/icon/edit-purple.gif'},
					{text: "新建机构",   handler: function(){ openWin('new' ,  record.raw.id); }, icon: common.ctx+'images/icon/sign_add.png'},
		        ];

	}else{
		array = [
					{text: "查看编辑信息", handler: function(){ openWin('edit',  record.raw.id); }, icon: common.ctx+'images/icon/edit-purple.gif'},
					{text: "新建机构",   handler: function(){ openWin('new' ,  record.raw.id); }, icon: common.ctx+'images/icon/sign_add.png'},
					{text: "删除机构",     handler: function(){ deleteOrganization(record.raw.id,record.raw.leaf); }, icon: common.ctx+'images/icon/sign_remove.png'}
		        ];
	}
	
	//先屏蔽浏览器原有的事件
	e.preventDefault();  
	e.stopEvent();
	var id = record.raw.id;
	
	var nodemenu = new Ext.menu.Menu({
		items:array
	});

	nodemenu.showAt(e.getXY());
	
},this);


function openWin(type, id){
	if('edit' == type){//编辑机构
		var form1 = Ext.getCmp('organization_form').getForm();
		form1.reset();
		//ajax 获得organizationId 为id 的，组织机构的信息，set到form表单里面
		var url = common.ctx + 'basic.do?action=getOrganizationDetail&tOrganizationId='+id;
		common.jajax(url, function(data){
			userComboboxStore.load({
				callback: function(){
					form1.setValues({
						organization_id_i:id,//当前编辑的机构id
						name_i: data.name,
						code_i: data.code,
						incharge_user_i: data.uid+"",//变成 字符串类型
						tel_i: data.tel,
						phone_i: data.phone,
						introduce_i: data.introduce,
						addoredit_i: 'edit'
					});

				}
			});
		});
		infoWin.setTitle('查看编辑机构信息');
		infoWin.show();
	}else if('new' == type){//新建机构
		var form1 = Ext.getCmp('organization_form').getForm();
		form1.reset();
		form1.setValues({
			'addoredit_i':'add', 
			organization_id_i:id //新建机构的父机构id
		});
		infoWin.setTitle('新建机构');
		infoWin.show();
	}
};
//用户下拉框
var infoWin = new Ext.Window({
	margins: '10 10 10 10',
	layout: 'fit',
	shadow: false,
	height: 220,
	width: 550,
	closeAction: 'hide',
	modal:true,//屏蔽后面的网页
	constrain: true,//限制在窗口内部
	//draggable:false,//拖动
	resizable:false,//是否可以拉伸
	items: [
		{
                    xtype: 'form',
                    height: 349,
                    width: 647,
                    bodyPadding: 10,
                    id: 'organization_form',
                    header: false,
                    frame: true,
                    layout: {
                        type: 'table',
                        columns: 2
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            id: 'name_i',
                            fieldLabel: '机构名称',
                            labelAlign: 'right',
                            allowBlank: false,
                            name: 'name_n'
                        },
                        {
                            xtype: 'combobox',
                            labelAlign: 'right',
                            fieldLabel: '负责人',
                            forceSelection: true,
                            displayField: 'name',
                            valueField: 'id',
                            name: 'incharge_user_n',
                            id: 'incharge_user_i',
                            store: userComboboxStore,
                            listeners:{
                            	change: function(view, newValue, oldValue, eOpts){
									userComboboxStore.load({
										params: {n: view.getRawValue()}
									});
								},
                            	focus: function(view, the,eOpts){
                            		view.expand();
                            	},
                            	expand: function(view, eOpts){
                            		view.reset();
                            	}
                            	
                            }

                        },
                        {
                            xtype: 'textfield',
                            
                            labelAlign: 'right',
                            fieldLabel: '机构代码',
                            allowBlank: false,
                            name: 'code_n',
                            id: 'code_i'

                        },
                        {
                            xtype: 'textfield',
                            
                            labelAlign: 'right',
                            fieldLabel: '电话',
                            name: 'tel_n',
                            id: 'tel_i'

                        },
                        {
                            xtype: 'textfield',
                            id: 'phone_i',
                            labelAlign: 'right',
                            fieldLabel: '负责人手机',
                            name: 'phone_n'

                        },
                        {
                            xtype: 'textfield',
                            labelAlign: 'right',
                            fieldLabel: '说明',
                            name: 'introduce_n',
                            id: 'introduce_i'

                        },
                        {
                            xtype: 'hiddenfield',
                            
                            id: 'organization_id_i',
                            name: 'organization_id_n'
                        },
                        {
                            xtype: 'hiddenfield',
                            
                            id: 'addoredit_i',
                            name: 'addoredit_n'
                        }
                       
                    ],
                	buttons: [{ 
                         text: '保存',
                         handler: function(){
                		 	 var form1 = this.up('form').getForm();

                        	 if(form1.isValid()){
                        		 form1.submit({
                        			 	waitMsg: '保存中,请稍候...',
                	        			url : common.ctx + "basic.do?action=saveOrEditOrganization",
                	            		success:function(form, action){
                	            			if(action.result.code != 'ok'){
                	            				common.showMes("提示", action.result.code);
                	            				return ;
                	            			}
                	                        common.showMes("提示", action.result.msg); 
                	                        infoWin.hide();
                	                        //console.info(action);
                	                        if('add' == action.result.flag){
                	                        	//新增
                	                        	var node = {
                	                        		id: action.result.id,
                	                        		pid: action.result.pid,
                	                        		text: action.result.text,
                	                        		leaf: true
                	                        	};
                	                        	var nod = organizationStore.getNodeById(action.result.pid);
                	                        	if(nod.isLeaf){
                	                        		//不加这句，原来是子菜单的展开不了
                	                        		nod.set('leaf', false);
                	                        	}
                	                        	nod.appendChild(node);
                	                        }else{
                	                        	//编辑
                	                        	var nod = organizationStore.getNodeById(action.result.id);
                	                        	nod.set('text',action.result.text);
                	                        }
                	                    
                	                    },
                	                    failure:function(form, action){
//                	                 	   Ext.Msg.alert('提示',"操作失败"); 
                	                    	common.showMes('提示','操作失败','warn');
                	                    }	
                        		 });
                        		 
                        	 }
                         }
                     },{             
                   	  text: '关闭' ,
                   	  handler: function(){
                   		infoWin.hide();
                   	  }
                     }]
                }
	]
});



function deleteOrganization(id, isleaf,tt){
	
	if(isleaf == true){
		Ext.Msg.confirm("警告", "删除机构会级联删除其下所有的员工信息，确定删除吗？", function(button,text){
			    	if(button=='yes'){
			    		
			        	var url = common.ctx+"basic.do?action=deleteOrganization&id="+id;
			        	common.jajax(url,function(data){
			        		if(data.success == true){
			        			//Ext.Msg.alert('提示', '删除成功');
			        			common.showMes("提示", '删除成功','success');
			        			//organizationStore.load();
			        			//organizationTree.remove(tt);
			        			organizationStore.getNodeById(id).remove();
			        		}else{
			        			common.showMes("提示","删除失败!","danger");
			        		}
			        	});
			    	}
			    	
			    });
		
	}else{
		common.showMes("提示","只能删除叶子节点!","warn");
	}

};







var detailPanel =Ext.create('Ext.panel.Panel',{
	title:'组织机构信息',
	region : 'center',
	frame:true,
	height: 100,
	contentEl : 'detailPanel'
});





Ext.onReady(function(){
	Ext.create('Ext.container.Viewport', {
		layout : "border",
		frame : true,
		items : [
		         
		        detailPanel,
		        
		         organizationTree
		]
	});
});
