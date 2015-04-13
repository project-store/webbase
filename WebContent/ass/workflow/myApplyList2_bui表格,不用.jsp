<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我发起的工作</title>
<%@include file="/common/jsp/taglib.jsp"%>
<%@include file="/ass/workflow/choiceApprover.jsp"%>
<script type='text/javascript' src="${ctx }ass/workflow/js/myApplyList.js"></script>

<style type='text/css'>
</style>
<script type="text/javascript">
BUI.use(['bui/grid','bui/data','bui/toolbar','bui/select'],function(Grid,Data,Toolbar){
    var Grid = Grid,
  Store = Data.Store,
  columns = [{
      title : '名称',
      dataIndex :'title',
      width:100
    },{
      title : '当前审批人',
      dataIndex :'participantName',
      width:200
    },{
      title : '审批状态',
      dataIndex : 'statusName',
      width:300
  },{
      title : '申请日期',
      dataIndex : 'appDate',
      width:300
  }
  ,{
      title : '创建日期',
      dataIndex : 'make_time',
      width:300
  }];

var store = new Store({
    url: common.ctx + "common.do?action=list&statement=workFlow_SpecSql.myApplyList",
    autoLoad:true,
    pageSize: 4
  }),
  grid = new Grid.Grid({
    render:'#grid',
    columns : columns,
    stripeRows: false, // 默认为true
    // 顶部工具栏
    tbar:{
        // items:工具栏的项， 可以是按钮(bar-item-button)、 文本(bar-item-text)、 默认(bar-item)、 分隔符(bar-item-separator)以及自定义项 
        items:[{
            //xclass:'bar-item-button',默认的是按钮
            btnCls : 'button button-small',
            text:'命令一'
        }, {
            xclass:'bar-item-text',
            text:'<a href="#">链接</a>'
        },{
          xclass : 'select',
          elTagName : 'li',
          items : [{text : '1',value : '1'},{text : '2',value : '2'}],
          listeners : {
            change : function(ev){
              alert(ev.value);
              //ev.item,ev.text
            }
          }
        }]
        // pagingBar:表明包含分页栏
        // pagingBar:true
    },
    // 底部工具栏
    bbar:{
        // pagingBar:表明包含分页栏
        pagingBar:true
    },
    store : store
  });

grid.render();
var tbar = grid.get('tbar'),
  searchBar = new Toolbar.Bar({
    elCls : 'pull-right',
    items:[{
        //xclass:'bar-item-button',默认的是按钮
        content : '<input name="name" id="id"/>'
    }, {
        xclass:'bar-item-button',
        btnCls : 'button button-small button-primary',
        text:'搜索'
    }]
  });
tbar.addChild(searchBar);
});
</script>
</head>
<body>

<div id="grid">
</div>

	
	
	










</body>
</html>