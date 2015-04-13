<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}/"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>hr+crm System</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link href="${ctx }common/build-bui/css/bs3/dpl.css" rel="stylesheet" type="text/css" />
<link href="${ctx }common/build-bui/css/bs3/bui.css" rel="stylesheet" type="text/css" />
<link href="${ctx }common/build-bui/css/main-min-Manager.css" rel="stylesheet" type="text/css" />

   <script type="text/javascript" src="${ctx }common/js/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" src="${ctx }common/build-bui/common/bui-min-Manger.js"></script>
  <script type="text/javascript" src="${ctx }common/build-bui/common/config-Manager.js"></script>
  <style type="text/css">
	body{
		line-height:21px !important;
		}
  </style>
 </head>
 <body>

  <div class="header">
    
      <div class="dl-title">
        <a href="http://www.builive.com" title="文档库地址" target="_blank"><!-- 仅仅为了提供文档的快速入口，项目中请删除链接 -->
          <span class="lp-title-port">BUI</span><span class="dl-title-text">前端框架</span>
        </a>
      </div>

    <div class="dl-log">欢迎您，<span class="dl-log-user"><shiro:principal/></span><a href="${ctx }logout.do" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      <div class="dl-inform"><div class="dl-inform-title">贴心小秘书<s class="dl-inform-icon dl-up"></s></div></div>
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">首页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-order">基础设置</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-inventory">搜索页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-supplier">详情页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-marketing">图表</div></li>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-content">

    </ul>
   </div>


  <script>
    BUI.use('common/main',function(){
      var config = [{
          id:'menu', 
          homePage : 'code',
          menu:[{
              text:'首页内容',
              items:[
                {id:'code',text:'首页代码*',href:'${ctx}NewFile.jsp',closeable : false},
                {id:'main-menu',text:'顶部导航*',href:'http://www.baidu.com'},
                {id:'second-menu',text:'右边菜单*',href:'main/second-menu.html'},
                {id:'dyna-menu',text:'动态菜单*',href:'main/dyna-menu.html'}
              ]
            },{
              text:'工作流程',
              items:[
                {id:'applies',text:'流程申请',href:'${ctx}ass/workflow/applies.jsp'},
                <shiro:hasPermission name="admin:aaa">  </shiro:hasPermission>
                <shiro:hasPermission name="admin:aaa">  </shiro:hasPermission>
                <shiro:hasPermission name="admin:aaa">  </shiro:hasPermission>
                {id:'myApplyList',text:'我发起的工作',href:'${ctx}ass/workflow/myApplyList.jsp'},
                
                {id:'myTaskList',text:'我的待处理工作',href:'${ctx}ass/workflow/myTaskList.jsp'},
                {id:'myProcessTaskList',text:'我经办的工作',href:'${ctx}ass/workflow/myProcessTaskList.jsp'},
                {id:'myFinishTaskList',text:'我结束的工作',href:'${ctx}ass/workflow/myFinishTaskList.jsp'},
              ]
            },{
              text:'申请汇总',
              items:[
                {id:'totalvacation',text:'休假信息汇总',href:'${ctx}ass/workflow/totalapplies/totalvacation.jsp'},
                {id:'totaltravel',text:'出差信息汇总',href:'${ctx}ass/workflow/totalapplies/totaltravel.jsp'}  
              ]
            }]
          },{
            id:'form',
            menu:[{
                text:'机构权限',
                items:[
                  {id:'organization',text:'组织机构',href:'${ctx}basic.do?action=showOrganization'},
                  {id:'rolePermission',text:'角色管理',href:'${ctx}rolePermission.do?action=initRolePermission'},
                  {id:'introduce',text:'表单简介*',href:'form/introduce.html'},
                  {id:'valid',text:'表单基本验证*',href:'form/basicValid.html'},
                  {id:'advalid',text:'表单复杂验证*',href:'form/advalid.html'},
                  {id:'remote',text:'远程调用*',href:'form/remote.html'},
                  {id:'group',text:'表单分组*',href:'form/group.html'},
                  {id:'depends',text:'表单联动*',href:'form/depends.html'}
                ]
              },{
                text:'成功失败页面',
                items:[
                  {id:'success',text:'成功页面*',href:'form/success.html'},
                  {id:'fail',text:'失败页面*',href:'form/fail.html'}
                
                ]
              },{
                text:'可编辑表格',
                items:[
                  {id:'grid',text:'可编辑表格*',href:'form/grid.html'},
                  {id:'form-grid',text:'表单中的可编辑表格*',href:'form/form-grid.html'},
                  {id:'dialog-grid',text:'使用弹出框*',href:'form/dialog-grid.html'},
                  {id:'form-dialog-grid',text:'表单中使用弹出框*',href:'form/form-dialog-grid.html'}
                ]
              }]
          },{
            id:'search',
            menu:[{
                text:'搜索页面',
                items:[
                  {id:'code',text:'搜索页面代码*',href:'search/code.html'},
                  {id:'example',text:'搜索页面示例*',href:'search/example.html'},
                  {id:'example-dialog',text:'搜索页面编辑示例*',href:'search/example-dialog.html'},
                  {id:'introduce',text:'搜索页面简介*',href:'search/introduce.html'}, 
                  {id:'config',text:'搜索配置*',href:'search/config.html'}
                ]
              },{
                text : '更多示例',
                items : [
                  {id : 'tab',text : '使用tab过滤*',href : 'search/tab.html'}
                ]
              }]
          },{
            id:'detail',
            menu:[{
                text:'详情页面',
                items:[
                  {id:'code',text:'详情页面代码*',href:'detail/code.html'},
                  {id:'example',text:'详情页面示例*',href:'detail/example.html'},
                  {id:'introduce',text:'详情页面简介*',href:'detail/introduce.html'}
                ]
              }]
          },{
            id : 'chart',
            menu : [{
              text : '图表',
              items:[
                  {id:'code',text:'引入代码*',href:'chart/code.html'},
                  {id:'line',text:'折线图*',href:'chart/line.html'},
                  {id:'area',text:'区域图*',href:'chart/area.html'},
                  {id:'column',text:'柱状图*',href:'chart/column.html'},
                  {id:'pie',text:'饼图*',href:'chart/pie.html'}, 
                  {id:'radar',text:'雷达图*',href:'chart/radar.html'}
              ]
            }]
          }];
      new PageUtil.MainPage({
        modulesConfig : config
      });
    });
  </script>
 </body>
</html>
