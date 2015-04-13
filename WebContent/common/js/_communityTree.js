/**
 *
 */
var communityTreeStore = new Ext.data.TreeStore({
	autoLoad: true,

	proxy: {
		type: 'ajax',
		url: common.ctx + 'customer/sketchmap.do?action=getCommunityTreeJson',
		actionMethods: {
			create: "POST",
			read: "POST",
			update: "POST",
			destroy: "POST"
		}
	}
});


var communityTree = Ext.create('Ext.tree.Panel', {
	//title:'小区',
	header: false,
	region: 'west',
	width: 249,
	split: true,
	//collapsible:true,
	root: {
		id: 'root',
		text: '小区列表'
		// expanded:true
	},
	collapsible: true, //可折叠
	rootVisible: false,
	frame: true,
	autoScroll: true,
	store: communityTreeStore,
	tbar: [{
		fieldLabel:'小区',
		labelAlign : 'right',
		labelWidth : 40,
		xtype: 'textfield',
		id: 'KeyWord',
		emptyText: '全部',
		width: 200,
		listeners: {
			'specialkey': function(field, e) {
				if (e.getKey() == e.ENTER) {
					var sName = Ext.getCmp("KeyWord").getValue();
					Ext.apply(communityTreeStore.proxy.extraParams, {
						'cName': sName
					});
					communityTreeStore.load();
				}
			}
		}
	}, '->', {
		//text:'搜索',
		icon: common.ctx + 'images/search_16.png',
		handler: function() {
			var sName = Ext.getCmp("KeyWord").getValue();
			Ext.apply(communityTreeStore.proxy.extraParams, {
				'cName': sName
			});
			communityTreeStore.load();
		}
	}]
});