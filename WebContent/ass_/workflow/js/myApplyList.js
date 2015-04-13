/**
 * 
 */

Ext.define('myApplyModel', {
	extend: 'Ext.data.Model',
	fields: [
		{ name:'id', type:'string'	}, 
		{ name: 'title', type:'string' }, 
		{ name: 'bussinessId', type:'string' }, 
		{ name: 'bussinessType', type:'string' }, 
		{ name: 'curStep', type:'string' }, 
		{ name: 'participantId', type:'string' }, 
		{ name: 'participantName', type:'string' }, 
		{ name: 'appDate', type:'string' }, 
		{ name: 'applyUserId', type:'string' }, 
		{ name: 'applyUserName', type:'string' }, 
		{ name: 'tOrganizationId', type:'string' }, 
		{ name: 'status', type:'string' }, 
		{ name: 'statusName', type:'string' }, 
		{ name: 'make_time', type:'string' }

	]
});


var myApplyStore =
	Ext.create('Ext.data.Store', {
		pageSize: 50,
		model: 'myApplyModel',
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
					statement: 'workFlow_SpecSql.myApplyList',
					queryAll: 0
				});
				var searchParams = searchPanel.getValues();
				Ext.apply(store.proxy.extraParams, searchParams);
			}
		}
	});

var myApplyGrid =
	Ext.create('Ext.grid.Panel', {
		store: myApplyStore,
		tbar: [
			{
	       		text:'详情',
	       		icon: images.d,
	       		handler:''
	       	},
	       	{
	       		text:'提休假',
	       		icon: images.d,
	       		handler: createVacation
	       	},
	       	{
	       		text:'提出差',
	       		icon: images.d,
	       		handler:''
	       	},
	       	{
	       		text:'作废',
	       		icon: images.d,
	       		handler:''
	       	},
	       	],
		
		selModel: Ext.create('Ext.selection.CheckboxModel'),
		columnLines: true,
		region: 'center',
		columns: [
			 
 			{ text: '名称' ,  dataIndex: 'title',   flex: 1 }, 
			{ text: '当前审批人' ,  dataIndex: 'participantName',   flex: 1 }, 
			{ text: '审批状态' ,  dataIndex: 'statusName',   flex: 1 }, 
			{ text: '申请日期' ,  dataIndex: 'appDate',   flex: 1 }, 
			{ text: '创建日期' ,  dataIndex: 'make_time', flex: 1   }
		],
		bbar: Ext.create('Ext.PagingToolbar', {
			id: 'pageingBar',
			store: myApplyStore,
			displayInfo: true
		})
	});

myApplyGrid.on('celldblclick',function(){


});

//右键
myApplyGrid.addListener('itemcontextmenu', itemcontextmenu);

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


var vacationWin = new Ext.Window({
			title:'休假申请',
			margins: '10 10 10 10',
			layout: 'fit',
			shadow: false,
			closeAction: 'hide',

			//width : 400,
			//height: 250,
			modal:true,//屏蔽后面的网页
			//draggable:false,//拖动
			resizable:false,//是否可以拉伸
			items : [
					
			         {

		                    xtype: 'form',
		                    id : 'form1',
		                    //height: 333,
		                    header: false,
							frame: true,
		                    bodyPadding: 10,
		                    defaults:{
		                    	labelAlign: 'right'
		                    },
		                    title: 'My Form',
		                    layout: {
		                        type: 'table',
		                        columns: 2
		                    },
		                    items: [
		                        {
									xtype: 'combobox',
									labelAlign: 'right',
									fieldLabel: '休假类型',
									colspan: 2,
									forceSelection: true,
									allowBlank: false,
									displayField: 'name',
									valueField: 'id',
									name: 'vacationType',
									store: vacationTypeStore,
									emptyText: '全部',
									listeners: {
										focus: function(view, the, eOpts) {
											view.expand();
										},
										expand: function(view, eOpts) {
											view.reset();
										}
									}
								},
								{
									name: 'startDate',
									xtype: 'datefield',
									fieldLabel: '开始日期',
									allowBlank: false,
									format: 'Y-m-d'
								},
								{
							        xtype: 'timefield',
							        name: 'startTime',
							        fieldLabel: '结束时间',
							        increment: 15,
							        allowBlank: false,
							        format: 'H:i'
							    },
     							{
									name: 'endDate',
									xtype: 'datefield',
									fieldLabel: '结束日期',
									allowBlank: false,
									format: 'Y-m-d'
								},
								{
							        xtype: 'timefield',
							        name: 'endTime',
							        fieldLabel: '开始时间',
							        increment: 15,
							        allowBlank: false,
							        format: 'H:i'
							    },
							    {
		                            xtype: 'numberfield',
		                            fieldLabel: '申请天数',
		                            name : 'appDays',
		                            decimalPrecision:1
		                        },
		                        {
		                            xtype: 'numberfield',
		                            fieldLabel: '申请小时数',
		                            allowBlank: false,
		                            name : 'appHours',
		                            decimalPrecision:1
		                        },
		                        {
		                            xtype: 'textfield',
		                            colspan: 2,
		                            name : 'reason',
		                            fieldLabel: '原因',
		                            width: 450,
		                            labelAlign: 'right'
		                        },
		                        {
								    xtype: 'hiddenfield',
								    fieldLabel: 'Label',
								    name : 'source',
								    value:'01'
								}
		                    ],
		                    buttons: [{ 
						          text: '保存',
						          handler: function(){
						        	  var form1 = this.up('form').getForm();
						        	  if(form1.isValid()){//验证条件
						        		  form1.submit({
						        		  	  submitEmptyText: false,
							        		  url:common.ctx + "vacation.do?action=saveVacation",
							        		  success:function(form, action){
							        			  //console.info(action.result.msg);
							        			  if(action.result.flag=='1'){
							        			  	 common.showMes("提示", action.result.msg);
							                        contMan.contractWin.hide();
									        		contractStore.load();
									        	}else{
									        		common.showMes("提示", action.result.msg);
									        	}
							        			 
							                    },
							                  failure:function(form, action){
							                	  common.showMes('提示','操作失败','warn'); 
							                  }						        		  
							        	  });
						        	  }
						        	  
						          }
						      },{             
						    	  text: '关闭' ,
						    	  handler: function(){
						    		  contMan.contractWin.hide();
						    	  }
						      }] 
		                    
			         }
			         ]
			
});
		


function createVacation(){
	Ext.getCmp('form1').getForm().reset();
	vacationWin.show();
}

























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
			}, {
				name: 'startDate',
				xtype: 'datefield',
				fieldLabel: '创建日期开始',
				format: 'Y-m-d',
				submitFormat: 'U'
			}, {
				name: 'endDate',
				xtype: 'datefield',
				fieldLabel: '结束于',
				format: 'Y-m-d',
				submitFormat: 'U'
			}
		],
		buttons: [{
			text: '查询',
			icon: images.search,
			handler: function() {
				myApplyStore.load();
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
			myApplyGrid
		]
	});
	
	myApplyStore.load();

});




















































