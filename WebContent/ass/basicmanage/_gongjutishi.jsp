<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构</title>
<%@include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript">
		   $(function () {
			   $("[data-toggle='tooltip']").tooltip(); 
			   });

		

</script>

</head>

<body>


<h4>工具提示（Tooltip）插件 - 锚</h4>
这是一个 <a href="#" class="tooltip-test" data-toggle="tooltip" 
   title="默认的 Tooltip">
   默认的 Tooltip
</a>.
这是一个 <a href="#" class="tooltip-test" data-toggle="tooltip" 
   data-placement="left" title="左侧的 Tooltip">
   左侧的 Tooltip
</a>.
这是一个 <a href="#" data-toggle="tooltip" data-placement="top" 
   title="顶部的 Tooltip">
   顶部的 Tooltip
</a>.
这是一个 <a href="#" data-toggle="tooltip" data-placement="bottom" 
   title="底部的 Tooltip">
   底部的 Tooltip
</a>.
这是一个 <a href="#" data-toggle="tooltip" data-placement="right" 
   title="右侧的 Tooltip">
   右侧的 Tooltip
</a>

<br>

<h4>工具提示（Tooltip）插件 - 按钮</h4>
<button type="button" class="btn btn-default" data-toggle="tooltip" 
   title="默认的 Tooltip">
   默认的 Tooltip
</button>
<button type="button" class="btn btn-default" data-toggle="tooltip" 
   data-placement="left" title="左侧的 Tooltip">
   左侧的 Tooltip
</button>
<button type="button" class="btn btn-default" data-toggle="tooltip" 
   data-placement="top" title="顶部的 Tooltip">
   顶部的 Tooltip
</button>
<button type="button" class="btn btn-default" data-toggle="tooltip" 
   data-placement="bottom" title="底部的 Tooltip">
   底部的 Tooltip
</button>
<button type="button" class="btn btn-default" data-toggle="tooltip" 
   data-placement="right" title="右侧的 Tooltip">
   右侧的 Tooltip
</button>





		
</body>

</html>