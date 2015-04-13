
/**
 * ---------------------------------------------------------------------------------------------------------
 * 用于页面上搜索的panel
 */
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
				name: 'log_type',
				xtype: 'combobox',
				fieldLabel: '业务名称',
				emptyText: "全部",
				store: logTypeStore,
				valueField: "id",
				displayField: "name",
				listeners: {
					focus: function(view, the, eOpts) {
						view.expand();
					},
					expand: function(view, eOpts) {
						view.reset();
					}
				}
			}, {
				xtype: 'combobox',
				labelAlign: 'right',
				fieldLabel: '操作人',
				forceSelection: true,
				displayField: 'name',
				valueField: 'id',
				name: 'operatorId',
				store: userComboboxStore,
				emptyText: '请选择...',
				listeners: {
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
				fieldLabel: '起始时间',
				format: 'Y-m-d',
				submitFormat: 'U'
			}, {
				name: 'endDate',
				xtype: 'datefield',
				fieldLabel: '结束时间',
				format: 'Y-m-d',
				submitFormat: 'U'
			}
		],
		buttons: [{
			text: '查询',
			icon: images.search,
			handler: function() {
				store.load();
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
 * -----------------------------------------------------------------------------------------------
 * 定义了一个名字叫customer的类模型（可以理解成就是一个类）
 * fields里面相当于属性名
 */


Ext.define('log', {
	extend: 'Ext.data.Model',
	fields: [{
			name:'id'
		}, {
			name: 'comment'
		}, {
			name: 'createTime'
		},  {
			name: 'loginName'
		}, {
			name: 'name'
		}, {
			name: 'businessName'
		}
	]
});

/**
 * ----------------------------------------------------------------------------------------------------------------
 * 定义一个当前页面的主表格的store。
 */
var store =
	Ext.create('Ext.data.Store', {
		pageSize: 100,
		model: 'log',
		//autoLoad:true,
		proxy: {
			type: 'ajax',
			actionMethods: {
				read: "POST"
			},
			url: common.ctx + '/common.do?action=list',
			reader: {
				type: 'json',
				root: 'rows',
				totalProperty: 'records'
			}
		},
		listeners: {
			beforeload: function(store, options) {
				store.proxy.extraParams = {
					statement: 'log_sql.queryLogs',
					queryAll: 0,
					sidx: 'id',
					sord: 'desc'
				};
				var searchParams = searchPanel.getValues();
				Ext.apply(store.proxy.extraParams, searchParams);
			}
		}
	});







/**
 * ----------------------------------------------------------------------------------------------------------------
 * 主表格
 */
var grid =
	Ext.create('Ext.grid.Panel', {
		store: store,
		dockedItems: {
			xtype: 'toolbar',
			dock: 'top',
			items: [{
				text: '详情',
				icon: images.forderc,
				handler: logDetail
			},{
				text: '删除',
				icon: images.forderd,
				handler: deleteLog
			}]
		},
		selModel: Ext.create('Ext.selection.CheckboxModel'),
		columnLines: true,
		region: 'center',
		columns: [
			{
				text: '帐号',
				dataIndex: 'loginName',
			}, {
				text: '操作员',
				dataIndex: 'name',
			}, {
				text: '操作时间',
				dataIndex: 'createTime',
				renderer:common.transUnixToDateTime,
				width:200,
			},{
				text: '业务名称',
				dataIndex: 'businessName',
				width:150,
			},  {
				text: '操作信息内容',
				dataIndex: 'comment',
				flex:1
			}
		],
		bbar: Ext.create('Ext.PagingToolbar', {
			id: 'pageingBar',
			store: store,
			displayInfo: true
		})
	});
grid.addListener('itemcontextmenu', itemcontextmenu);
//调用的函数
function itemcontextmenu(his, record, item, index, e) {
	// itemcontextmenu( Ext.view.View this, Ext.data.Model record,
	// HTMLElement item, Number index, Ext.EventObject e, Object eOpts )
//分类代码表的右键菜单 
e.preventDefault();  
e.stopEvent();// 取消浏览器默认事件   
var array = [ 	            
	            {
					text: '详情',
					icon: images.forderc,
					handler: logDetail
				},{
					text: '删除',
					icon: images.forderd,
					handler: deleteLog
				}
            ];  
var nodemenu = new Ext.menu.Menu({  
  items : array  
});  
nodemenu.showAt(e.getXY());// 菜单打开的位置  
};

/**
 * ---------------------------------------------------------------------------------------------------------------
 * 页面加载完成后执行
 */
Ext.onReady(function() {

	// 整个页面的布局
	Ext.create('Ext.container.Viewport', {
		layout: "border",
		items: [
			searchPanel, grid
		]
	});
	
	store.load();

});


function deleteLog(){
	var rows = grid.getSelectionModel().getSelection();
	if (rows.length != 1) {
		common.showMes("提示", "请至少选择一条数据！");
		return;
	}

	var tLogIds = rows[0].get('id');
	for(var i=1;i<rows.length;i++){
		tLogIds+=','+rows[i].get('id');
	}
	Ext.Msg.confirm("警告", "确定要删除吗？", function(button,text){
        if(button=='yes'){
        	basicDwrService.deleteLogs(tLogIds,function(){
        		common.showMes("提示","删除成功","success");
        		store.load();
        	});
        }
        
    });
	
	
	

}


//显示日志详情
function logDetail(){

	var rows = grid.getSelectionModel().getSelection();
	if (rows.length != 1) {
		common.showMes("提示", "请选择一条数据！");
		return;
	}

	Ext.apply(logDetailStore.proxy.extraParams, {
		tLogId: rows[0].get('id')
	});

	logDetailStore.load();


	Ext.create('Ext.window.Window', {
		title: '详情',
		height: 250,
		width: 650,
		closeAction: 'hide',
		modal: true,
		layout: 'fit',
		items: logDetailGrid
	}).show();
}


Ext.define('logDetailModel', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'tableName'
		}, {
			name: 'recordId'
		}, {
			name: 'fieldName'
		}, {
			name: 'fieldOldValue'
		}, {
			name: 'fieldNewValue'
		}]
	});

var logDetailStore =
	Ext.create('Ext.data.Store', {
		pageSize: 100,
		model: 'logDetailModel',
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
				Ext.apply(store.proxy.extraParams ,{
					statement: 'log_sql.queryLogDetails',
					queryAll: 0
				});
			}
		}
	});

var logDetailGrid =
	Ext.create('Ext.grid.Panel', {
		store: logDetailStore,
		columnLines: true,
		columns: [{
			text: '表名',
			dataIndex: 'tableName',
			flex:1
		}, {
			text: '主键',
			dataIndex: 'recordId',
			flex:1
		}, {
			text: '字段名',
			dataIndex: 'fieldName',
			flex:1
		}, {
			text: '旧值',
			dataIndex: 'fieldOldValue',
			flex:1
		},
		{
			text: '新值',
			dataIndex: 'fieldNewValue',
			flex:1
		}]
	});
