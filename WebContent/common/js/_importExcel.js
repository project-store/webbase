/**

这是一个通用的上传excel的组件
传入上传的地址和window上面的显示的信息就行了。

*/
function importExcel(url, title) {

	var errStore = Ext.create('Ext.data.Store', { //创建一个数据存储
		fields: ['headText', 'rowIndex', 'errorMsg'], //
		data: {
			'items': []
		},
		proxy: {
			type: 'memory',
			reader: {
				type: 'json',
				root: 'items'
			}
		}
	});
	Ext.create("Ext.window.Window", {
		height: 550,
		width: 850,
		title: title,
		modal: true,
		layout: 'border',
		//添加frame的方式使得只有在激活标签的时候才会加载页面。
		items: [{
			xtype: 'form',
			frame: true,
			layout: 'column',
			region: 'north',
			items: [{
				xtype: 'filefield',
				width: 300,
				margin: '0 10 0 0',
				name: 'uploadFile',
				fieldLabel: '文件名',
				buttonText: '浏览',
				allowBlank:false,
				regex: /(xls)$/i
			}, {
				xtype: 'button',
				text: '上传',
				handler: function() {

					if(!this.up('form').isValid()){

						return;
					}


					this.up('form').submit({
						url: url,
						type: 'ajax',
						waitMsg: '正在上传文件...',
						success: function(form, action) {

							var errList = action.result.errList;
							if (errList.length > 0) {
								Ext.Msg.alert('失败', "存在"+errList.length+"条错误，请先更正全部错误！");
								errStore.loadData(errList);

							} else {
								Ext.MessageBox.hide();
								form.reset();
								Ext.Msg.alert('成功', "保存成功！");
							}
						},
						failure: function(){
							Ext.Msg.alert('失败', "系统错误，请联系管理员！");
						}
					});
				}
			}]
		}, {
			xtype: 'grid',
			title: '错误信息',
			store: errStore,
			region: 'center',
			columnLines: true,
			columns: [{
				text: '表头',
				dataIndex: 'headText',
				width:200,
			}, {
				text: '列索引',
				dataIndex: 'cellindex',
				hidden: true
			}, {
				text: '行索引',
				dataIndex: 'rowIndex',
				width:200,
				renderer: function(value) {
					return value + 2;
				}
			}, {
				text: '错误信息',
				dataIndex: 'errorMsg',
				flex: 1
			}]
		}]

	}).show();
}