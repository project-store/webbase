

var fileStore_ = Ext.create('Ext.data.Store', {	
		fields: [
		{name: 'id'},
	    {name: 'fileName'},
	    {name: 'filePath'},
	    {name: 'bussType'},
	    {name: 'bussId'},
	    {name: 'operatorId'},
	    {name: 'operatorName'},
	    {name: 'createTime'},
	    {name: 'remark'},
	    {name: 'fileSuffix'}
	    ],
		proxy: {	           
		  type: 'ajax',actionMethods:{read: "POST"},
		  url: common.ctx+'common.do?action=list',									          
		  reader: {type: 'json',root: 'rows',totalProperty: 'records' }
		},
		listeners:{
			// beforeload:function(){
			// 		Ext.apply(fileStore.proxy.extraParams,{ 
			// 		statement : 'Attachment_Sepc_sql.query',
			// 		queryAll : 1,
			// 		bussType : 'staff'
			//     });
			// }
		}
	});






		//附件列表
var attachmentGrid_ = Ext.create('Ext.grid.Panel',{
        		region:'center',

				store:fileStore_,
				// height:200,
				// width:650,
				
				selModel:Ext.create('Ext.selection.CheckboxModel'),
				columnLines:true,
				region:'center',		  
				columns:[  	
					{dataIndex : 'id', text: 'id' ,hideable: false, hidden: true },
				    {dataIndex : 'fileName', text: '文件名称' ,flex: 1 },
				    {dataIndex : 'filePath', text: '文件路径' ,hideable: false, hidden: true},
				    {dataIndex : 'fileSuffix', text: '后缀' ,flex: 1  },
				    {dataIndex : 'bussType', text: '类型' ,hideable: false, hidden: true },
				    {dataIndex : 'bussId', text: '关联id' ,hideable: false, hidden: true },
				    {dataIndex : 'operatorId', text: '上传人id' ,hideable: false, hidden: true },
				    {dataIndex : 'operatorName', text: '上传人' ,flex: 1 },
				    {dataIndex : 'createTime', text: '创建时间' ,flex: 1 },
				    {dataIndex : 'remark', text: '备注' ,flex: 1  },
				    {header : '操作',renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
						var s = "<a onclick=uploadFile.downloadFile('"+record.get('id')+"') href='javascript:void(0);'  >下载</a>"
								+"&nbsp;&nbsp; <a onclick=uploadFile.deleteFile('"+record.get('id')+"') href='javascript:void(0);'  >删除</a>";
						return s;
					},flex:1
					}
				   
				]
			});


var uploadUrl_  = "";

var uploadWin_ = Ext.create('Ext.window.Window', {
			        title:'附件管理',
			        height:350,
			        width: 700,
			        modal:true,
			        closeAction: 'hide',
			        layout:'border',
			        defaults:{
			        	labelAlign:'right'
			        },

			        items:[
				        {
				        	xtype:'form',
				        	title:'上传区域',
				        	frame:true,
				        	region:'north',
				        	id: 'fileForm_1',
				        	layout: {
						          type: 'table',
						          columns: 2
						      },
				        	items:[
				        		{
									name:'filename',
									fieldLabel:'文件名称',
									colspan: 2,
									xtype:'textfield',
									allowBlank: false,
									width:250
								},{
									name:'uploadFile',
									width:350,
									colspan: 2,
									allowBlank: false,
								    xtype: 'filefield',
								    fieldLabel: '附件',	
								    buttonText: '浏览'
								},{
									name:'remark',
									fieldLabel:'备注',
									colspan: 1,
									xtype:'textfield',
									width:350
								},
								{
									text: '上传',
									colspan: 1,
									style: {
										'margin-left': '20px',
										'margin-bottom': '3px'
									},
									xtype: 'button',
									handler:function(){
										var form1 = this.up('form').getForm();
			        				form1.submit({
			        					waitMsg: '保存中,请稍候...',
			        					url: uploadUrl_,
			        					success:function(form,action){
			        						if(action.result.flag== "ok"){
			        							common.showMes("成功","上传成功!","success");
												fileStore_.load();
												//重置form
												Ext.getCmp('fileForm_1').getForm().reset();
												return ;
			        						}else{
			        							common.showMes("警告",action.result.msg,"warn");
			        							return ;
			        						}
			        					}
			        				});
			        			}}
				        	]
				        },
			        	attachmentGrid_
			        ]
			    });


//文件上传入口
var uploadFile={


		showFileWin: function(bussId, bussType){
			//1设置store的参数
			Ext.apply(fileStore_.proxy.extraParams,{ 
						statement : 'Attachment_Sepc_sql.query',
						queryAll : 1,
						bussType : bussType,
						bussId: bussId
				    });
			fileStore_.loadPage(1);

			//设置提交路径 
			uploadUrl_ = common.ctx+'attachment.do?action=upload&bussType='+bussType+'&bussId='+bussId;

			//重置form
			Ext.getCmp('fileForm_1').getForm().reset();
			//2 显示附件win
			uploadWin_.show();


		},



		//删除附件
		deleteFile : function(id){

			Ext.Msg.confirm("警告", "确定要删除所选附件吗？删除后不能恢复!", function(button,text){
			if(button=='yes'){
		    	var url = common.ctx + 'attachment.do?action=delete&id='+id;
		    	common.jajax(url,function(data){
		    		if(data.success == true){
		    			//Ext.Msg.alert('提示', '删除成功');
		    			common.showMes("提示", "删除成功",'success');
		    			fileStore_.load();
		    		}else{
		//        			Ext.Msg.alert('提示', '删除失败');
		    			common.showMes('提示','删除失败','warn');
		    		}
		    	});
			}
			
		});
		},

		//删除附件
		downloadFile : function(id){

			var url = common.ctx + 'attachment.do?action=download&id='+id;
		    window.location = url;
			}

		
		};

//附件控件end*************************************************************



