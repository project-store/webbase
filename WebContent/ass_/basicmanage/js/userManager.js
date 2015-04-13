


//***************************用户
//角色表格 
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


//定义模块列
var cm = [
		{header : '角色id',dataIndex : 'id',hideable: false, hidden: true },
		{header : '角色名称',dataIndex : 'name'},
		{header : '简介',dataIndex : 'rolevalue'}
      ];

var roleSM = Ext.create('Ext.selection.CheckboxModel');

/*
* 定义表格
*/
var roleGrid = ass.grid({
	title:'角色列表',
	width: 230,
	height: 350,
	autoScroll:true,
	//forceFit : true,
	sm: roleSM,//
	cm:cm,//
	store : roleStore//
	//bbar: true
});




//权限树
var permissionStore= new Ext.data.TreeStore({
 	autoLoad:true,		
	proxy:{
		type:'ajax',							
		url: common.ctx+"basic.do?action=permissionTree",
		actionMethods:{
               		create: "POST", 
               		read: "POST", 
               		update: "POST", 
               		destroy: "POST"
         		}
	}
 });

var permissionTree = Ext.create('Ext.tree.Panel', {
	title:'包含权限',
	//collapsible : true,//右上方 的箭头， 可以折叠面板
	rootVisible: false,
    frame: true,	
    width: 230,
	height: 350,
	checkModel:'single',
	useArrows:true,//箭头
	lines:true,//点之间连接线
    autoScroll:true,
    store:permissionStore,
    viewConfig : {   //checkbox联动!!!!!!
        onCheckboxChange : function(e, t) {
        	 var item = e.getTarget(this.getItemSelector(), this.getTargetEl()), record;
	         if (item){
		          record = this.getRecord(item);
		          var check = !record.get('checked');
		          record.set('checked', check);
		          if (check) {
			           record.bubble(function(parentNode) {
				            parentNode.set('checked', true);
				            parentNode.expand();
			           });
			           record.cascadeBy(function(node) {
				            node.set('checked', true);
				            node.expand();
			           });
		          } else {
			           record.cascadeBy(function(node) {
			            node.set('checked', false);
			           });
		          }
	         }
	       }
	     }
	
});
//permissionTree.expandAll();

roleSM.addListener('selectionchange', checkPermissionTree);


//角色列表点击事件

function checkPermissionTree(grid, rowIndex, columnIndex, e) {

	permissionStore.load({
		callback: function(){
			
			//设置节点的  icon
//			 var rootNode = permissionStore.getRootNode();
//			rootNode.cascadeBy(function(d){
//				d.set("icon","");
//			});
			
				//  权限树上显示对应的权限
				 var rows = roleGrid.selectRows();
				 if(rows.length < 1){
					 return false;
				 }
				 var roleIds="";
				 
				 for(var i=0;i<rows.length;i++){
					 roleIds += rows[i].get('id');
					 if(i != (rows.length-1)){
						 roleIds += ",";
			 		}
			 	}
				 //通过rows 获取其对应的所有的权限
				 var url = common.ctx+"basic.do?action=getAllPermissionsByRoleIds";
				 var param = {
						 ids : roleIds
				 };
			//	 var authArr = [];
			//	 var selectArr = [];
//				 console.info(param);
				 common.dajax(url, param, function(d){
					 //console.info(d);
					 if(d.str != 'none'){
						 //console.info(d.str);
						 var a = d.str.split(',');
						 //console.info(a);
					 		for(var i = 0; i < a.length; i++){
					 			var authNode = permissionStore.getNodeById(a[i]);
					 			//authNode.set('checked',true);//选中自己
					 			if(authNode != undefined){
					 				authNode.set('icon', common.ctx+"images/tree/tick.png");
						 			if(!authNode.isLeaf() && !authNode.isExpanded()){
						 				//console.info(authNode.isLeaf()+"--"+authNode.isExpanded());
						 				authNode.expand();//;//展开节点
						 			}
					 			}
					 			
					 		}
					 		
				 		}else{
				 			console.info("null");
				 		}
				 });

		}
		});
	
	 
		 
} 




var organizationcombobox = Ext.create('Ext.form.ComboBox', {
        id : 'organization_',
        name : 'tOrganizationId',
        fieldLabel: '所属机构',
        allowBlank: false,
        forceSelection: true,//强制只能从下拉框里面选
        matchFieldWidth: true,//过长的名字会变成2行甚至更多
        displayField: 'name',
        valueField: 'id',
        labelAlign : 'right',
        store : organBoxStore,
        listeners:{
        	change: function(view, newValue, oldValue, eOpts){
				organBoxStore.load({
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
    
});




var userWin = new Ext.Window({
	margins: '10 10 10 10',
	layout: 'fit',
	shadow: false,
	closeAction: 'hide',
	modal:true,//屏蔽后面的网页
	constrain: true,//限制在窗口内部
	//draggable:false,//拖动
	resizable:false,//是否可以拉伸
	items:[
				 { //左侧员工信息 中间角色列表， 右侧权限树
				     xtype: 'form',
				     frame: true,
				     bodyPadding: 10,
				     header : false,
				     title: 'My Form',
				     layout: {
				         type: 'table',
				         columns: 'left'
				     },
				     items: [
				         {
				             xtype: 'form',
				             id: 'form1',
				             flex: 1,
				             width: 300,
				             frame: true,
				             bodyPadding: 10,
				             style: 'border: none',
				             header: false,
				             layout: {
				                 type: 'table',
				                 columns: 1
				             },
				             items: [
				                     organizationcombobox,
				                 {
				                     xtype: 'textfield',
				                     anchor: '100%',
				                     id : 'name_',
				                     name : 'name',
				                     fieldLabel: '用户姓名',
				                     allowBlank: false,
				                     labelAlign: 'right'
				                 },
				                 {
				                     xtype: 'textfield',
				                     anchor: '100%',
				                     id : 'loginName_',
				                     name : 'loginName',
				                     fieldLabel: '登录名',
				                     allowBlank: false,
				                     labelAlign: 'right'
				                 },
				                 {
				                     xtype: 'textfield',
				                     anchor: '100%',
				                     id : 'mobile_',
				                     name : 'mobile',
				                     fieldLabel: '手机号',
				                     labelAlign: 'right',
				                     regex: Validator.Mobile,
				                     regexText: '手机号格式不正确'
				                 },
				                 {
				                     xtype: 'textfield',
				                     anchor: '100%',
				                     id : 'email_',
				                     name : 'email',
				                     fieldLabel: '邮箱',
				                     labelAlign: 'right',
				                     regex: Validator.Email,
				                     regexText: '邮箱格式不正确'
				                 },
				                 {
				                     xtype: 'textfield',
				                     anchor: '100%',
				                     id : 'password_',
				                     name : 'password',
				                     fieldLabel: '初始密码',
				                     //value: '000000',
				                     allowBlank: false,
				                     labelAlign: 'right'
				                 },
				                 {
							            xtype: 'hiddenfield',
							            anchor: '100%',
							            id: 'id_',
							            width: 150,
							            fieldLabel: 'Label',
							            name : 'id'
							        },
							        {
							            xtype: 'textfield',
							            fieldLabel: '',
							            width: 230,
							            readOnly: true,
							            fieldStyle:'background: none; border: 1; border-bottom:#b5b8c8 1px solid; color:red;',
							            value: '编辑用户时,密码框为空默认不修改密码.'
							        }
				             ]
				         },
				         roleGrid,
				         permissionTree
	      ]
	 }],
	 buttons: [{ 
         text: '保存',
         handler: function(){
        	 var rows = roleGrid.selectRows();
        	 //构造  1,2,3  类型的权限id 字符串
        	 var roleids = "";
	 		 if(rows.length > 0){
	 			for(var i=0;i<rows.length;i++){
	        		roleids += rows[i].get('id');
	        		if(i != (rows.length-1)){
	        			roleids += ",";
	        		}
	        	}
	 		 }
        	 //除了表单内的 需要传到后天的其他参数 1组织结构id   2权限id  
        	 var param = {
        			 htRoleIds : roleids
        	 };
        	 var form1 = Ext.getCmp("form1").getForm();
        	 if(form1.isValid()){
        		 form1.submit({
        			 url : common.ctx + "basic.do?action=saveUser",
            		 params : param,
            		 success:function(form, action){
                         common.showMes("提示", action.result.msg,'success'); 
                         userWin.hide();
                         userStore.load();
                     },
                   failure:function(form, action){
                   	common.showMes("警告", action.result.msg,'warn'); 
                   }	
        			 
        		 });
        		 
        	 }
        	 
        	 
         }
     },{             
   	  text: '关闭' ,
   	  handler: function(){
   		userWin.hide();
   	  }
     }

     ]
});









var northpanel= Ext.create('Ext.form.Panel', {
	  	//xtype: 'form',
	  	id: 'north',
	  	defaults: {
	          labelAlign: 'right',
	          submitEmptyText:false,
	      },
        bodyPadding: 10,
        title: '查询区域',
        region:'north',
        frame: true,
        height: 100,
        header: true,
        layout: {
            type: 'table',
            columns: 3
        },
        items: [
            {
                xtype: 'textfield',
                fieldLabel: '姓名',
                emptyText: '全部',
                name: 'username'

            },
            {
                xtype: 'textfield',
                fieldLabel: '登录名',
                emptyText: '全部',
                name: 'loginname'
            },
            {
                
	            xtype: 'combobox',
	            name : 'tOrganizationId',
	            fieldLabel: '所属机构',
	            emptyText: '全部',
	            displayField: 'name',
	            valueField: 'id',
	            labelAlign : 'right',
	            store : organBoxStore,
	            listeners:{
                	change: function(view, newValue, oldValue, eOpts){
						organBoxStore.load({
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


            }
        ],
        buttons:[
        	{
        		text: '查询',
        		icon: common.ctx+'images/search_16.png',
        		handler: function(){
        			
				    userStore.loadPage(1);
        		}
        	},{
        		text: '重置',
        		icon: common.ctx+'images/reset_16.png',
        		handler: function(){
        			var form1 = this.up('form').getForm();
        			form1.reset();
        		}
        	}
        ]

		
});




Ext.define('userModel', {
     extend: 'Ext.data.Model',
     fields: [
		{name:'id', type: 'int'}, 
		{name:'loginName', type: 'string'}, 
		{name:'name', type: 'string'}, 
		{name:'mobile', type: 'string'}, 
		{name:'email', type: 'string'}, 
		{name:'dbrolenames', type: 'string'},
		{name:'tOrganizationId', type: 'string'},
		{name:'organizationName', type: 'string'},

     ]
 });

var userStore =ass.store({
	pageSize: '50',
	url: common.ctx+"common.do?action=list",
	model:'userModel'
});
userStore.on('beforeload', function() {
	Ext.apply(userStore.proxy.extraParams,{ 
		statement : 'userManage_SpecSql.getUserRelated',
		queryAll : 0,
		sidx : 'id',
		sord : 'asc'
    });
	var vs =northpanel.getValues();
    Ext.apply(userStore.proxy.extraParams,vs);
});

// 定义模块列
var cm = [//,hideable: false, hidden: true 
	{header : '用户ID',dataIndex : 'id', flex: 1, hideable: false, hidden: true},
	{header : '所在机构id',dataIndex : 'tOrganizationId', flex: 1, hideable: false, hidden: true},
	{header : '用户姓名',dataIndex : 'name', flex: 1},
	{header : '登录名',dataIndex : 'loginName', flex: 1 },
	{header : '手机号',dataIndex : 'mobile', flex: 1 },
	{header : '邮箱',dataIndex : 'email', flex: 1 },
	{header : '所在机构',dataIndex : 'organizationName', flex: 1 },
	{header : '包含角色',dataIndex : 'dbrolenames', flex: 1}
	];

var userGrid = ass.grid({
	tbar:[
	    {
	    	text:'新建用户',
	    	icon: images.addUser,
	    	handler:createUser
	    	
	    },
	    '-',
	    {
	    	text:'修改用户',
	    	icon: images.editUser,
	    	handler:editUser
	    	
	    },
	    '-',
	    {
	    	text:'删除用户',
	    	icon: images.deleteUser,
	    	handler:deleteUsers
	    	
	    },
	    '-',
	    {  
                text : '员工附件管理', 
                icon: images.attachment,
                handler : attachmentManage
        }
		],
	//title : '用户列表',
	header :false,
	region:'center',
	forceFit: true,
	sm:Ext.create('Ext.selection.CheckboxModel'),
	cm:cm,
	store : userStore,
	bbar: true
});

//双击事件
userGrid.on('celldblclick',function(){
	editUser();
});

//******用户表格右键菜单
userGrid.addListener('itemcontextmenu', itemcontextmenu);
//调用的函数
function itemcontextmenu(his, record, item, index, e) {
	// itemcontextmenu( Ext.view.View this, Ext.data.Model record,
	// HTMLElement item, Number index, Ext.EventObject e, Object eOpts )
  // 分类代码表的右键菜单 
  e.preventDefault();  
  e.stopEvent();// 取消浏览器默认事件   
  var array = [ {  
		              text : '新建用户',  
		              icon: images.addUser,
		              handler : createUser
	            }, {  
		                text : '查看编辑用户',  
		                icon: images.editUser,
		                handler : editUser
	            }, {  
		                text : '删除用户', 
		                icon: images.deleteUser,
		                handler : deleteUsers
	            },{  
		                text : '员工附件管理', 
		                icon: images.attachment,
		                handler : attachmentManage
	            }];  
  var nodemenu = new Ext.menu.Menu({  
      items : array  
  });  
  nodemenu.showAt(e.getXY());// 菜单打开的位置  
};



function editUser(){
	userWin.setTitle("编辑用户");
	var rows = userGrid.selectRows();
	if(rows.length != 1 ){
		common.showMes('提示', '请选择单条记录');
		return false;
	}
	
	var form1 = Ext.getCmp("form1").getForm();//待提交的表单
	form1.reset();//重置表单
	organBoxStore.load({
		callback: function(){
			
			roleStore.load({//刷新 角色
				callback : function(){
					//设置 对应的角色
					var model = roleGrid.getSelectionModel();
					 var url = common.ctx+"basic.do?action=getRolesByUserId";
					 common.dajax(url,{userId:rows[0].get('id')},function(d){
						 if(d.id != 'none'){
							 var roleids = d.id;
							 var ids = roleids.split(',');
							 var arr = [];
							 //console.info(roleids);
							 if(roleids != null && roleids != ''){
								 roleStore.each(function(record){
									 //console.info(record.data.htRoleId);
										 for(var i =0; i<ids.length; i++){
											 if(parseInt(ids[i]) == record.data.id){
												 arr.push(record);
												 break;
											 }
										 }
									});
							 }
//							 console.info(arr);
							 model.select(arr);//选中角色
							
							 
						 }
					 });
					
				}
			});
			
			//设置 其他选项的默认值   组织结构下拉菜单以及默认值
			form1.loadRecord(rows[0]);
			form1.setValues({id_ : rows[0].get('id')});
			Ext.getCmp("organization_").setValue(rows[0].get('tOrganizationId'));
			//rows[0].get('tOrganizationId') 的类型要是跟combobox定义的value列类型一致！
			//int or string， 当前为string！
			var p = Ext.getCmp("password_");//密码框的必填要求去掉
			p.allowBlank = true;
			
			
			userWin.show();
			
			
		}
		
	});
	
	
};

function createUser(){
	userWin.setTitle("创建用户");
	var form1 = Ext.getCmp("form1").getForm();//待提交的表单
	form1.reset();
	form1.setValues({id_ : '0'});//设置id的值为0， 代表新增
	
	var p = Ext.getCmp("password_");
	p.allowBlank = false;
	
	roleStore.load();
	userWin.show();
};

function deleteUsers(){
	var rows = userGrid.selectRows();
	if(rows.length<1){
		common.showMes('提示', '至少选择一条记录');
		return false;
	}
	
    Ext.Msg.confirm("警告", "确定要删除吗？", function(button,text){
    	if(button=='yes'){
    		var htUserIds=[];
        	for(var i=0;i<rows.length;i++){
        		htUserIds.push(rows[i].get('id'));
        	}
        	var url = common.ctx + 'basic.do?action=deleteUsers&ids='+htUserIds;
        	common.jajax(url,function(data){
        		//console.info(data);
        		if(data == true){
        			//Ext.Msg.alert('提示', '删除成功');
        			common.showMes("提示", "删除成功",'success');
        			userStore.load();
        		}else{
//        			Ext.Msg.alert('提示', '删除失败');
        			common.showMes('提示','删除失败','warn');
        		}
        	});
    	}
    	
    });
	
};

//附件管理按钮
function attachmentManage(){

		var rows = userGrid.selectRows();
		if(rows.length != 1 ){
			common.showMes('提示', '请选择单条记录');
			return false;
		}
		uploadFile.showFileWin(rows[0].get('id'),'staff');
		//rows[0].get('id')
		//传递查询参数
		// fileStore.load({parmas:{bussId:rows[0].get('id')}});
		// var form1 = Ext.getCmp('fileform').getForm();
		// form1.setValues({'bussId_':rows[0].get('id')});
		// fileWin.show();

}


















Ext.onReady(function(){
	Ext.create('Ext.container.Viewport', {
		layout : "border",
		frame : true,
		items : [
		        
		        	northpanel,
		        	userGrid
		        	 
		]
	});
	userStore.loadPage(1);
});




