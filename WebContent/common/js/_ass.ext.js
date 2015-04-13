var  ass = ass || {};
	/**
	 * config={ 
	 * 		url:url,
	 * 		totalProperty:total,
	 * 		root:rows,
	 * 		fields: [ 
	 * 			{name:'firstName', type: 'string'}, 
	 * 			{name: 'lastName', type: 'string'} 
	 * 		],
	 		baseParams:
	 * }
	 */

	ass.store = function(config) {
		var store = Ext.create('Ext.data.Store',{
			autoLoad : config.autoLoad == null ? false : config.autoLoad,
			pageSize: config.pageSize == null ? 50 : config.pageSize,
			model:config.model,
			proxy:{
				//method:"POST",
	         	type: 'ajax',
	         	url : config.url == null ? common.ctx+"common.do?action=list" : config.url,
	         	//访问后台使用post 或者 get
	         	actionMethods:{
               		create: "POST", 
               		read: "POST", 
               		update: "POST", 
               		destroy: "POST"
         		},
	         	reader: {
	             	type: 'json',
	             	root : config.root == null ? 'rows' : config.root,
	             	totalProperty: config.totalProperty == null ? 'records' : config.totalProperty
	         	}
	         	
	        }
		});
		return store;
	};




			ass.grid = function(config) {
			
			//两种bbar的情况
			var twobbar = {
				xtype: 'form',
				header: false,
				frame:true,
				items: [{
						xtype :'label',
					    //fieldStyle:'background: none; border: none;border-bottom:#b5b8c8 1px solid; color:red;',
					    html:'<span id="count_info_" ></span>'
						 },
							 {
				     	xtype: 'pagingtoolbar',
				     	store: config.store,   // same store GridPanel is using
				     	displayInfo: true
			
						 }]
			};
			//1中bbar的情况
			var onebbar = {
				xtype: 'pagingtoolbar',
				store: config.store,   // same store GridPanel is using
				displayInfo: true,
			
				 };
			
			var grid = Ext.create('Ext.grid.Panel',{
					//var grid = Ext.grid.Panel({
					title : config.title,
					region : config.region,
					
					height:config.height,
					width:config.width,
					
					//singleSelect:config.singleSelect==null?false:config.singleSelect,
					autoScroll:config.autoScroll == null ? true : false,
					selModel : config.sm,
					columns : config.cm,
					//split : config.split == null ? true : false,
					store : config.store,
					//renderTo:config.renderTo,
					
					columnLines:true,                                         //显示行列线。
					viewConfig :{
						forceFit:true
					},
					tbar : config.tbar,
					/*
						bbar 不传 或 null ，             没有
						bbar 不为null （为true，1，2，3……） twobbar 不传或 null 。  为单bbar
						bbar 不为null （为true，1，2，3……）twobbar 不为null （为true，1，2，3……） 2为双bbar
		
					*/
					bbar : config.bbar==null?null:(config.twobbar==null?onebbar:twobbar),
					
			   		plugins:config.plugins == null ? '' : config.plugins,
			   		listeners:config.listeners == null ? '' : config.listeners
						
			});
					
					
				if(config.cm!=null){
					//获取选中的行数	
					grid.selectRows = function(){
						return grid.getSelectionModel().getSelection();
					},
					//获取选中行数的长度
					grid.selectLength =function(){
						return grid.selectRows().length;
					},
					//返回选中的第一行
					grid.FirstRow = function(){
						if(grid.selectRows().length==0){
							return null;
						}else{
							return grid.selectRows()[0];
						}
					},
					//返回选中的最后一行
					grid.LastRow  = function(){
						if(grid.selectRows().length==0){
							return null;
						}else{
							return grid.selectRows()[grid.selectLength-1];
						}
					}
				}
					
					return grid;
				};

	/**
	 * bbar config={ pageSize:页大小 displayMsg:数据描述 store: }
	 */
	ass.bbar = function(config) {
		var bbar = new Ext.PagingToolbar({
			id : config.id,
			pageSize : config.pageSize == null ? 50 : config.pageSize,
			store : config.store,
			displayInfo : config.displayInfo == null ? true : false,
			loadMask : {
				msg : config.loadMask == null ? '数据加载中...' : config.loadMask,
			},
			displayMsg : (config.displayMsg==null?'数据':config.displayMsg) + ' {0} - {1} of {2}',
			emptyMsg : "没有查到符合条件的数据!",
			beforePageText : "第",
			afterPageText : "/ {0}页",
			firstText : "首页",
			prevText : "上一页",
			nextText : "下一页",
			lastText : "尾页",
			refreshText : "刷新",
			
			doLoad : function(start) {
				this.store.load({
					params : {
						start : start,
						limit : this.pageSize,
						page : start / this.pageSize + 1,
						pageSize : this.pageSize
					}
				});
			}
		});
		return bbar;
	};

	ass.tree = function(config) {
		var tree = new Ext.tree.TreePanel({
			
			title:config.title,
			region:config.region,
			split :config.split==null?true:config.split,
			width : 280,
			tbar : config.tbar,
			minSize : 175,
			maxSize : 500,
			collapsible : true,
			autoScroll : true,
			shadow : true,
			frame : true,
			checkModel : 'cascade', // 对树的级联多选
			onlyLeafCheckable : false,// 对树所有结点都可选
			enableAllCheck : true,
			enableDD : false,
			baseAttrs : {
				uiProvider : Ext.tree.TreeCheckNodeUI
			}, // 添加 uiProvider 属性

			animate : true,
			rootVisible : false,
			lines : true,
			singleExpand : false,
			collapseMode : 'mini',

			root : config.root!=null?config.root:new Ext.tree.AsyncTreeNode({
													text : 'workspace',
													draggable : false,
													id : 'workspace-tree',
													expanded : true
												})
		});
		return tree;
	};

	/**
	 * Win config={ 
	 *		title:'title',
	 * 		html:$("#"+id).html(),
	 * 		buttons:[
			   {text:"确定",handler:function(){}},
			   {text:"取消",handler:function(){win.hide();}}
			]
	 * }
	 */
	ass.win=function(config){
		var win=new Ext.Window({
			title:config.title,
			margins:'10 10 10 10',
			layout: 'fit',
			shadow : false,
			autoLoad:config.url,
			closeAction: 'hide',
			html:config.html,//$("#"+item.itemId).html(),
			buttons:config.buttons
			/**
			buttons:[
			   {text:"确定",handler:function(){$("#"+item.itemId).submit();}},
			   {text:"取消",handler:function(){mapwin.hide();}}
			],//footer部
			buttonAlign:"center",
			*/
		});
		return win;
	};



