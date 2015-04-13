// 模型
Ext.define('customer', {
	extend: 'Ext.data.Model',
	fields: [
		{name:'id', type: 'int'}, 
		{name:'name', type: 'string'}, 
		{name:'sex', type: 'string'}, 
		{name:'birthday', type: 'string'}, 
		{name:'id_type_name', type: 'string'}, 
		{name:'id_card', type: 'string'}, 
		{name:'e_mail', type: 'string'},
		{name:'mobile',type:'string'},
		{name:'remark', type: 'string'},
		{name:'address', type: 'string'},
		{name:'operator_name', type: 'string'},
		{name:'status_name', type: 'string'}
	]
}); 
// 数据源
var customerStore = Ext.create('Ext.data.Store',{
	pageSize: 50,
	model: 'customer',
	proxy:{
		type: 'ajax',
		actionMethods: {
			read: "POST"
		},
		url: common.ctx + '/common.do?action=list',
		reader: {
			type: 'json',
			root: 'rows',
			totalProperty: 'records'
		},
		simpleSortMode: true
	},
	listeners:{
		beforeload: function(store, options) {
				customerStore.proxy.extraParams = {
					statement: 'Customer_SpecSql.customerList',
					queryAll: 0
				};
//				var searchParams = searchPanel.getValues();
//				Ext.apply(customerStore.proxy.extraParams, searchParams);
			}
	}
});
// 查询区域
var searchPanel = Ext.create('Ext.form.Panel',{
	title: '查询区域',
	// 收缩
	collapsible: true,
	frame: true,
	region: 'north',
	layout: {
			type: 'table',
			columns: 4
	},
	defaults: {
			labelAlign: 'right'
	},
	items:[
			{
                xtype: 'textfield',
                fieldLabel: '姓名',
                emptyText: '请输入...',
                name: 'customerName'
            },
            {
                xtype: 'textfield',
                fieldLabel: '证件号',
                emptyText: '请输入...',
                name: 'idCard'
            },
            {
				xtype: 'combobox',
				labelAlign: 'right',
				fieldLabel: '状态',
				forceSelection: true,
				displayField: 'name',
				valueField: 'id',
				name: 'customerStatus',
				store: customerStatusStore,
				emptyText: '请选择...',
				listeners: {
					focus: function(view, the, eOpts) {
						view.expand();
					},
					expand: function(view, eOpts) {
						view.reset();
					}
				}
			}
	],
	buttons : [
		{
			text: '查询',
			icon: images.search,
			handler: function() {
				customerStore.load();
			}
		},{
			text: '重置',
			icon: images.reset,
			handler: function() {
				searchPanel.getForm().reset();
			}
		}
	]
});
//  表格显示区域
var customerGrid = Ext.create('Ext.grid.Panel',{
	store:customerStore,
	tbar:[
		{text:'增加客户',handler: createCustomer, icon: images.createCustomer},
	    {text:'编辑客户',handler: editCustomer, icon: images.createCustomer},
	    {text:'删除客户',handler: deleteCustomer, icon: images.createCustomer}
	],
	selModel: Ext.create('Ext.selection.CheckboxModel'),
	columnLines: true,
	region: 'center',
	columns: [
			{dataIndex:'id',    text: 'id'  ,hideable: false, hidden: true }, 
			{dataIndex:'name',    text: '客户姓名' ,flex:1 }, 
			{dataIndex:'sex',    text: '性别'  ,flex:1}, 
			{dataIndex:'birthday',    text: '生日' ,flex:1 }, 
			{dataIndex:'id_type_name',    text: '证件类型'  ,flex:1}, 
			{dataIndex:'id_card',    text: '证件号' ,flex:1 }, 
			{dataIndex:'mobile',   text: '手机号' ,flex:1},
			{dataIndex:'e_mail',   text: '邮箱'  ,flex:1 },
			{dataIndex:'address',   text: '地址'  ,flex:1 },
			{dataIndex:'operator_name',   text: '操作人'  ,flex:1 },
			{dataIndex:'status_name', text: '状态'   ,flex:1 },
			{dataIndex:'remark',   text: '备注'  ,flex:1 }
		],
		bbar: Ext.create('Ext.PagingToolbar', {
			id: 'pageingBar',
			store: customerStore,
			displayInfo: true
		})
});
//customerGrid.addListener('itemcontextmenu', itemcontextmenu);

var userWin = new Ext.Window({
	margins: '20 10 10 10',
	layout: 'fit',
	shadow: false,
	closeAction: 'hide',
	modal:true,//屏蔽后面的网页
	constrain: true,//限制在窗口内部
	//draggable:false,//拖动
	resizable:false,//是否可以拉伸
	title: '添加客户信息',
	items:[
		{
			xtype: 'form',
			id: 'formid',
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
			items:[
				{
					xtype: 'textfield',
				    anchor: '100%',
				    id : 'name_',
				    name : 'name',
				    fieldLabel: '姓名',
				    allowBlank: false,
				    labelAlign: 'right'
				},{
					xtype: 'radiogroup',
					fieldLabel: '性别',
					anchor: '100%',
					labelAlign: 'right',
               		items: [{
		                   	name: 'sex',
		                   	inputValue: '1',
		                   	boxLabel: '男',
		                   	checked: true
		               }, {
		                   	name: 'sex',
		                  	inputValue: '0',
		                    boxLabel: '女'
		              }]
				},{
					xtype: 'datefield',
				    anchor: '100%',
				    id : 'birthday_',
				    name : 'birthday',
				    fieldLabel: '生日',
				    allowBlank: false,
				    labelAlign: 'right',
				    format:'Y-m-d',
				},{
					xtype: 'combobox',
				    anchor: '100%',
				    id : 'idType_',
				    name : 'idType',
				    fieldLabel: '证件类型',
				    displayField: 'name',
				    valueField: 'id',
				    allowBlank: false,
				    labelAlign: 'right',
				    emptyText: '请选择...',
				    store: customerIdTypeStore,
				    listeners: {
						focus: function(view, the, eOpts) {
							view.expand();
						},
						expand: function(view, eOpts) {
							view.reset();
						}
					}
				},{
					xtype: 'textfield',
				    anchor: '100%',
				    id : 'idCard_',
				    name : 'idCard',
				    fieldLabel: '证件号',
				    forceSelection: true,
				    displayField: 'name',
				    valueField: 'id',
				    allowBlank: false,
				    labelAlign: 'right'
				},{
					xtype: 'textfield',
				    anchor: '100%',
				    id : 'mobile_',
				    name : 'mobile',
				    fieldLabel: '手机号',
				    allowBlank: false,
				    labelAlign: 'right',
				    regex: Validator.Mobile,
				    regexText: '手机号格式不正确'
				},{
					xtype: 'textfield',
				    anchor: '100%',
				    id : 'email_',
				    name : 'email',
				    fieldLabel: '邮箱',
				    allowBlank: false,
				    labelAlign: 'right',
				    regex: Validator.Email,
				    regexText: '邮箱格式不正确'
				},{
					xtype: 'textfield',
				    anchor: '100%',
				    id : 'address_',
				    name : 'address',
				    fieldLabel: '地址',
				    allowBlank: false,
				    labelAlign: 'right',
				    maxLength:300
				},{
					xtype: 'combobox',
				    anchor: '100%',
				    id : 'status_',
				    name : 'status',
				    fieldLabel: '状态',
				    forceSelection: true,
				    allowBlank: false,
				    displayField: 'name',
				    valueField: 'id',
				    labelAlign: 'right',
				    emptyText: '请选择...',
				    store: customerStatusStore,
				    listeners: {
					focus: function(view, the, eOpts) {
						view.expand();
					},
					expand: function(view, eOpts) {
						view.reset();
					}
				}
				},{
					xtype: 'textarea',
				    anchor: '100%',
				    id : 'remark_',
				    name : 'remark',
				    fieldLabel: '备注',
				    allowBlank: false,
				    labelAlign: 'right',
				    labelSeparator:':' // 分隔符
				}
			]
		}
	],
	buttons:[
		{
			text:"保存",
			handler:function(){
				var formSub = Ext.getCmp("formid").getForm();
				if(formSub.isValid()){
					formSub.submit({
						url : common.ctx + "customer.do?action=saveOrupdate",
            		 	// params : param,
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
			text:"取消",
			handler:function(){
				userWin.hide();
			}
		}
	]
});


// 增加函数
function createCustomer(){
	userWin.setTitle("创建客户");
	userWin.show();
}

// 删除函数
function deleteCustomer(){

}

// 编辑函数
function editCustomer(){
	userWin.setTitle("编辑客户");
	userWin.show();
}
Ext.onReady(function(){
	// 整个页面的布局
	Ext.create('Ext.container.Viewport',{
		layout: "border",
		items:[
			searchPanel,
			customerGrid
		]
	});
	customerStore.load();
});