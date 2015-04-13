



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
	Ext.apply(roleStore.proxy.extraParams,{ 
		statement : 'userManage_SpecSql.getRoles',
		queryAll : 1,
		sidx : 'id',
		sord : 'asc'
	});
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
        },
        '-',
        {xtype:'label',text:'请输入用户名称：'},
		{xtype:'textfield',id:'KeyWord', emptyText: '全部',
					listeners: {
                      'specialkey': function(field, e){
                      		if (e.getKey() == e.ENTER) {
                              var sName = Ext.getCmp("KeyWord").getValue();
								Ext.apply(userStore.proxy.extraParams, { 'username' : sName});
								userStore.load();
							}
                          }
                      }
		},
		{	text:'搜索',
			icon: common.ctx+'images/search_16.png',
			handler:function(){
						var sName = Ext.getCmp("KeyWord").getValue();
						Ext.apply(userStore.proxy.extraParams, { 'username' : sName});
						userStore.load();
					}	
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
	//console.info(organizationId);
	organBoxStore.load({
		callback: function(){
			Ext.getCmp("organization_").setValue(String(organizationId));
		}
	});
	
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




























//**************************************************************************************
//**************************************************************************************
//**************************************************************************************




var organizationId = 1;//全局，当前组织机构

//*****************************组织机构数begin

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
	organizationId = record.raw.id;

	//var name = record.raw.text;
	//ajax获取此节点相关的信息，在页面右上方显示出来
	var url = common.ctx + "basic.do?action=getOrganizationDetail";
	var data = {tOrganizationId : organizationId};
		$("#name_jsp").html("");
		$("#code_").html("");
		$("#incharge_user").html("");
		$("#tel_").html("");
		$("#phone_").html("");
		$("#introduce_").html("");
	common.dajax(url, data, function(d){
		//console.info(d);
		$("#name_jsp").html(d.name);
		$("#code_").html(d.code);
		$("#incharge_user").html(d.uname);
		$("#tel_").html(d.tel);
		$("#phone_").html(d.phone);
		$("#introduce_").html(d.introduce);

	});
	//	清除原有的查询条件！
		userStore.proxy.extraParams = {};
		Ext.apply(userStore.proxy.extraParams,{'tOrganizationId' : organizationId});
		userStore.load();
	
	
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
//*****************************组织机构数end



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



//******************************机构节点 编辑框
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
//******************************机构节点 编辑框


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
	region : 'north',
	frame:true,
	height: 110,
	contentEl : 'detailPanel'
});





Ext.onReady(function(){
	Ext.create('Ext.container.Viewport', {
		layout : "border",
		frame : true,
		items : [
		        
		         organizationTree,
		         {
		        	 xtype:'panel',
		        	 layout :"border",
		        	 region:'center',
		        	 items:[
		        	        detailPanel,
		        	        userGrid
		        	 ]
		         },
		         
		]
	});
});
