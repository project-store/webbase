/**
 * 
 */
 /**
 * 
 */

Ext.define('myProcessModel', {
	extend: 'Ext.data.Model',
	fields: [
		{ name:'id', type:'string'	}, 
		{ name: 'title', type:'string' }, 
		{ name: 'bussinessId', type:'string' }, 
		{ name: 'bussinessType', type:'string' }, 
		{ name: 'curStep', type:'string' }, 
		{ name: 'participantId', type:'string' }, 
		{ name: 'appDate', type:'string' }, 
		{ name: 'applyUserId', type:'string' }, 
		{ name: 'applyUserName', type:'string' }, 
		{ name: 'organizationId', type:'string' },
		{ name: 'organizationName', type:'string' }, 
		{ name: 'status', type:'string' }, 
		{ name: 'statusName', type:'string' },
		{ name: 'make_time', type:'string' }

	]
});


var myProcessStore =
	Ext.create('Ext.data.Store', {
		pageSize: 50,
		model: 'myProcessModel',
		//autoLoad:true,
		proxy: {
			type: 'ajax',
			actionMethods: {
				read: "POST"
			},
			url: common.ctx + 'common.do?action=list',
			reader: {
				type: 'json',
				root: 'rows',
				totalProperty: 'records'
			}
		},
		listeners: {
			beforeload: function(store, options) {
				Ext.apply(store.proxy.extraParams , {
					statement: 'workFlow_SpecSql.myProcessTaskList',
					queryAll: 0
				});
				var searchParams = searchPanel.getValues();
				Ext.apply(store.proxy.extraParams, searchParams);
				
			}
		}
	});

var myProcessGrid =
	Ext.create('Ext.grid.Panel', {
		store: myProcessStore,
		tbar: [
	       	{
	       		text:'休假',
	       		icon: common.ctx+'',
	       		handler:''
	       	},
	       	{
	       		text:'出差',
	       		icon: common.ctx+'',
	       		handler:''
	       	},
	       	{
	       		text:'作废',
	       		icon: common.ctx+'',
	       		handler:''
	       	},
	       	],
		
		selModel: Ext.create('Ext.selection.CheckboxModel'),
		columnLines: true,
		region: 'center',
		columns: [
			 //名称 申请人 部门 状态 申请日期 

			{ text: '名称', dataIndex: 'title', flex: 1 }, 
			{ text: '申请人', dataIndex: 'applyUserName', flex: 1 }, 
			{ text: '申请部门', dataIndex: 'organizationName', flex: 1 }, 
			{ text: '状态', dataIndex: 'statusName', flex: 1 },
			{ text: '申请日期', dataIndex: 'appDate', flex: 1 }, 
		],
		bbar: Ext.create('Ext.PagingToolbar', {
			id: 'pageingBar',
			store: myProcessStore,
			displayInfo: true
		})
	});


//右键
myProcessGrid.addListener('itemcontextmenu', itemcontextmenu);

function itemcontextmenu(his, record, item, index, e) {

e.preventDefault();  
e.stopEvent();// 取消浏览器默认事件   
var array = [ 	            
	            {
					text: '详情',
					icon: images.forderc,
					handler: ''
				},{
					text: '删除',
					icon: images.forderd,
					handler: ''
				}
            ];  
var nodemenu = new Ext.menu.Menu({  
  items : array  
});  
nodemenu.showAt(e.getXY());// 菜单打开的位置  
};



var searchPanel =
	Ext.create('Ext.form.Panel', {
		title: '查询区域',
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
		items: [
			{
				name: 'title',
				xtype: 'textfield',
				fieldLabel: '名称'
			}, {
				xtype: 'combobox',
				labelAlign: 'right',
				fieldLabel: '申请人',
				forceSelection: true,
				displayField: 'name',
				valueField: 'id',
				name: 'appUserId',
				store: userComboboxStore,
				emptyText: '全部',
				
				listeners: {
					change: function(view, newValue, oldValue, eOpts){
						userComboboxStore.load({
							params: {n: view.getRawValue()}
						});
					},
					focus: function(view, the, eOpts) {
						view.expand();
					},
					expand: function(view, eOpts) {
						view.reset();
					}
				}
			}, {
				name: 'startDate',
				xtype: 'datefield',
				fieldLabel: '申请日期开始',
				format: 'Y-m-d',
				submitFormat: 'U'
			}, {
				name: 'endDate',
				xtype: 'datefield',
				fieldLabel: '结束于',
				format: 'Y-m-d',
				submitFormat: 'U'
			},
			{
				xtype: 'combobox',
				labelAlign: 'right',
				fieldLabel: '审批状态',
				forceSelection: true,
				displayField: 'name',
				valueField: 'id',
				name: 'workflowStatus',
				store: workflowStatusStore,
				emptyText: '全部',
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
		buttons: [{
			text: '查询',
			icon: images.search,
			handler: function() {
				myProcessStore.load();
			}
		}, {
			text: '重置',
			icon: images.reset,
			handler: function() {
				searchPanel.getForm().reset();
			}
		}]
	});


/**
 * ---------------------------------------------------------------------------------------------------------------
 * 页面加载完成后执行
 */
Ext.onReady(function() {

	// 整个页面的布局
	Ext.create('Ext.container.Viewport', {
		layout: "border",
		items: [
			searchPanel, 
			myProcessGrid
		]
	});
	
	myProcessStore.load();

});




















































