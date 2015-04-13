
BUI.use('bui/overlay',function(Overlay){
		editRoleDiv = new Overlay.Dialog({
        title:'角色信息',
        width:340,
        height:200,
        effect :{
	      	effect: 'fade',//进入效果
	      	duration: 350
	      },
        closeAction : 'hide', 
        //配置DOM容器的编号
        contentId:'editRole_',
        buttons:[
                 {
                   text:'保存',
                   elCls : 'btn btn-primary btn2',
                   handler : function(){

   	    			if(!stringCheck($("#edit_rolename").val())){
   	    				common.alertMes('角色名称不合法',function(){$("#edit_rolename").focus();},'info');
   	    				
   	    				return false;
   	    			}
   	    			var data =  {
   	    					name: $("#edit_rolename").val(),
   	    					roleid : $("#edit_roleid").val()
   	    			};
   	    			var url = common.ctx+"rolePermission.do?action=saveOrEditRole";
   	    			var closeDiv;
   	    			common.dajaxTB(url,data,function(data){
   	    				if(data.ok == 'ok'){
   	    					common.showOK();
   	    					//刷新页面
   	    					location.reload();
   	    				}else{
   	    					common.alertMes(data.ok,'info');
   	    				}
   	    				
   	    			});
   	    			
                   }
                 },{
                   text:'关闭',
                   elCls : 'btn btn-primary btn2',
                   handler : function(){
                     this.close();
                   }
                 }
               ]
      });
		
	});
		








//点击角色表格,先取消选中其他的,再选中当前的.
function clickrole(trid,type,id){
	//console.info($('#'+trid).siblings());
	$('#'+trid).siblings().removeClass(type);
	common.clicktr(trid,type);
	
  	var url = common.ctx + "rolePermission.do?action=getRolePermissionByRoleId&roleid="+id;
  	common.jajax(url,function(data){
  		$("#role").find("input[name='pcCheckbox']").attr("checked", false);
  		//$("input:checkbox").attr("checked", false);//等同于上面一句
  		var permissionArray = data.roleStr.split(",");
  		for (var i = 0; i < permissionArray.length; i++) {
  			var t = document.getElementById(permissionArray[i]);
  			if(t != null){
  				t.checked = true;
  			}
  		};
  	});
	
}











//等同于  roleGrid.on('cellclick',cellclick);****
 //获取对应角色含有的权限，把右侧的对应的checkbox权限选中
function cellclick(grid, rowIndex, columnIndex, e) {
	//console.info(rowIndex.raw.htRoleId);
	var url = common.ctx + "rolePermission.do?action=getRolePermissionByRoleId&roleid="+rowIndex.raw.id;
	//console.info(common.ctx);
	common.jajax(url,function(data){
		$("#role").find("input[name='pcCheckbox']").attr("checked", false);
		//$("input:checkbox").attr("checked", false);//等同于上面一句
		var permissionArray = data.roleStr.split(",");
		for (var i = 0; i < permissionArray.length; i++) {
			var t = document.getElementById(permissionArray[i]);
			if(t != null){
				t.checked = true;
			}
		};
	});

} 








//***用到的方法
var rolePermission = {
		checkParent : function(thi){
			var p = document.getElementById(thi.id);
			if(p.checked == true){
//				$("#" + $("#"+thi.id).attr("value2") ).attr("checked",true);//
				var par = document.getElementById($("#"+thi.id).attr("value2"));
				par.checked = true;
			}
			
		},

		checkSibings: function(thi){
			if(thi.checked){//$("#"+thi.id).attr("checked")
				//$("#"+thi.id).siblings("input").attr("checked","checked");
				var childs = $("#"+thi.id).siblings("input");
				for(var i =0; i<childs.length; i++){
					childs[i].checked = true;//childs[i].checked = 'checked';
				}
			}else{
				$("#"+thi.id).siblings("input").removeAttr("checked");
			}
		},
		saveRolePermission: function(){
			var id = common.getIds('roletable_','success');
			if(id == ''){
				common.alertMes('请选中一条记录!','info');
				return;
			}
			//console.info(id);
			var htstr = id.replace('role_', '');
			//console.info(htstr);
        	$("#roleid").val(htstr);
        	
		    var url = common.ctx+ "rolePermission.do?action=saveRolePermission";
        	common.dajax(url,$('#role').serialize(),function(d){
        		if(d.ok == 'ok'){
        			common.showOK();
        		}else{
        			common.alertMes(d.ok,'info');
        		}
        	});
        	
		},
		
		/**新建*/
		createRolePermission : function(){
			$("#edit_roleid").val(0);
			$("#edit_rolename").val('');
			editRoleDiv.show();
		},
		
		
		/**编辑*/
		editRolePermission : function(rId){
			
			var url = common.ctx+ "rolePermission.do?action=getRoleInfo&roleid="+rId;
			common.jajax(url, function(d){
				$("#edit_roleid").val(d.id);
				$("#edit_rolename").val(d.name);
				editRoleDiv.show();
			});
			
			
			
		},
		/**删除*/
		delRolePermission : function(rId){
			common.confirmMes('用户将失去这个角色，确定要删除吗？',function(){
    				//删除角色操作开始----------------
		        	var url = common.ctx + 'rolePermission.do?action=deleteRole&roleIds='+rId;
		        	common.jajax(url,function(data){
		        		//console.info(data);
		        		if(data.success == true){
		        			//Ext.Msg.alert('提示', '删除成功');
		        			common.showOK();
   	    					//刷新页面
   	    					location.reload();
		        		}else{
		        			common.alertMes('info','删除失败');
		        		}
		        	});
			},'question');
			
		}
		


		
};
















