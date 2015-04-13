<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构</title>
<%@include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript">

		

</script>

</head>
<body>
  <div class="demo-content">
    <div>
      <button id="btnShow" class="button button-primary">显示</button>
    </div>
    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content" class="hide">
      <form class="form-horizontal">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label">供应商编码：</label>
            <div class="controls">
              <input type="text" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">供应商类型：</label>
            <div class="controls">
              <select class="input-normal"> 
                <option>淘宝卖家</option>
                <option>大厂直供</option>
              </select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15">
            <label class="control-label">起始日期：</label>
            <div class="controls">
              <input class="input-small control-text" type="text"><label>&nbsp;-&nbsp;</label><input class="input-small control-text" type="text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15">
            <label class="control-label">备注：</label>
            <div class="controls control-row4">
              <textarea class="input-large" type="text"></textarea>
            </div>
          </div>
        </div>
      </form>
    </div>
    
    <script type="text/javascript">
        BUI.use('bui/overlay',function(Overlay){
        	var dialog = new Overlay.Dialog({
	            title:'配置DOM',
	            width:500,
	            height:320,
	            closeAction : 'hide', //每次关闭dialog释放
	            //配置DOM容器的编号
	            contentId:'content',
	            success:function () {
	              alert('确认');
	              this.close();
	            }
	          });
            $('#btnShow').on('click',function () {
	          
	          dialog.show();
        });
      });
    </script>
<!-- script end -->
  </div>
  
   <div class="demo-content">
<div class="row">
<div class="span8 offset3">
<div id="t1">
</div>
<h2>点击的节点</h2>
<div class="log well"></div>
</div>
</div>
 
<script src="http://g.tbcdn.cn/fi/bui/jquery-1.8.1.min.js"></script>
<script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.9/config.js"></script>
<script type="text/javascript">
BUI.use('bui/tree',function (Tree) {
//树节点数据，
//text : 文本，
//id : 节点的id,
//leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
//expanded ： 是否默认展开
var data = [
{text : '1',id : '1',children: [{text : '11',id : '11'}]},
{text : '2',id : '2',expanded : true,children : [
{text : '21',id : '21',children : [{text : '211',id : '211'},{text : '212',id : '212'}]},
{text : '22',id : '22'}
]},
{text : '3',id : '3'},
{text : '4',id : '4'}
];
//由于这个树，不显示根节点，所以可以不指定根节点
var tree = new Tree.TreeList({
render : '#t1',
nodes : data
});
tree.render();
 
tree.on('itemclick',function(ev){
var item = ev.item;
$('.log').text(item.text);
});
});
</script>
<!-- script end -->
</div>
</body>

</html>