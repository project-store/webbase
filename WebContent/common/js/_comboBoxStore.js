/**
 * 通用的model
 */
Ext.define('model',{
	extend: 'Ext.data.Model',
	fields: [	          
	         {name: 'id', type: 'string'}, 			//  valueField      
	         {name: 'name', type: 'string'} 		//displayField    
	         
	         /**
	          * 可以附加其他属性
	          *	{name: 'name', type: 'string'}  
	          *	{name: 'name', type: 'string'}    
	          *	{name: 'name', type: 'string'}
	          */
	]
	//,
	//idProperty: 'id'
});

/**
 * 
 */
var comboBox = {
		getStore : function getStore(url, autoload){
			return Ext.create('Ext.data.Store', {	
				autoLoad: autoload == null ? false : autoload,	
			    model: 'model',
			    proxy: {
			        type: 'ajax',
			        url: url,									          
			        reader: {
			            root: 'list'
			        },
				        actionMethods:{
	               		create: "POST", 
	               		read: "POST", 
	               		update: "POST", 
	               		destroy: "POST"
         			}          
			    },
			    sorters: [{
			    	 property: 'id',
			         direction: 'ASC'
			    }]
			});
		}
};

//用户下拉框
var userComboboxStore = comboBox.getStore(common.ctx+'combox.do?action=user',false);

//组织机构下拉框
var organBoxStore = comboBox.getStore(common.ctx+'combox.do?action=organization',false);
//日志类型
var logTypeStore = comboBox.getStore(common.ctx+'combox.do?action=code2nameOfProperties&codetype=log_type',false);

//客户状态
var customerStatusStore = comboBox.getStore(common.ctx+'combox.do?action=code2nameOfProperties&codetype=customer_status',false);
//工作审批状态
var workflowStatusStore = comboBox.getStore(common.ctx+'combox.do?action=code2nameOfProperties&codetype=workflowtask_status',false);
//休假类型
var vacationTypeStore = comboBox.getStore(common.ctx+'combox.do?action=code2nameOfProperties&codetype=vacation_type',false);














// 证件类型
var customerIdTypeStore = comboBox.getStore(common.ctx+'combox.do?action=code2nameOfProperties&codetype=id_type',false);

/////////////下拉框写法例子******************
/*
{
    xtype: 'combobox',
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
}
/////////////下拉框写法例子**********************/


/////////////////////////以下未使用
/**
 * 是否为黑名单
 */
var isBlackModel = new Ext.data.ArrayStore({
    fields: ['id','name'],
    data: [[1, '是'], [0, '否']]
});

/**
 * 是否有卡
 */
var isHaveCardModel = new Ext.data.ArrayStore({
    fields: ['id','name'],
    data: [[1, '是'], [0, '否']]
});

/**
 * 开票方式
 */
var kaipiaoModel = new Ext.data.ArrayStore({
    fields: ['id','name'],
    data: [[0,'机打发票'],[1, '手开发票'],[2,'机打收据'],[3, '手开收据'], [4, '不开']]
});

