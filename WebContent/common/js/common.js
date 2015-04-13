var common = {
		
	//根路径
	ctx : "",
		
	//form 表单提交
	formSubmit : function(formId, url) {
		url = encodeURI(url);
		$("#" + formId).attr("action",url);
		$("#" + formId).submit();
	},
		
	jajax : function(url,callback){
		var url1 = encodeURI(encodeURI(url))+'&wt_=' + Math.random();
				$.ajax({
					type:"post",
					contentType:"application/json",
					async:true,///////////////////
					url: url1,
					datatype:"json",
					data:"",
					success: function(data){
							//var data = eval('('+data+')');
							callback(data);
					}
				});
	},

	jajaxTB : function(url,callback){
		var url1 = encodeURI(encodeURI(url))+'&wt_=' + Math.random();
				$.ajax({
					type:"post",
					contentType:"application/json",
					async:false,////////////////////
					url: url1,
					datatype:"json",
					data:"",
					success: function(data){
							//var data = eval('('+data+')');
							callback(data);
					}
				});
	},

	dajax : function(url,data1,callback){
		var url1 = encodeURI(encodeURI(url))+'&wt_=' + Math.random();
				$.ajax({
					type:"post",
					contentType:"application/x-www-form-urlencoded",
					async:true,
					url: url1,
					datatype:"json",
					data: data1 == null ? '' : data1,
					success: function(data){
							//var data = eval('('+data+')');
							callback(data);
					}
				});
	},
	dajaxTB : function(url,data1,callback){
		var url1 = encodeURI(encodeURI(url))+'&wt_=' + Math.random();
				$.ajax({
					type:"post",
					contentType:"application/x-www-form-urlencoded",
					async:false,////////////////////
					url: url1,
					datatype:"json",
					data: data1 == null ? '' : data1,
					success: function(data){
							//var data = eval('('+data+')');
							callback(data);
					}
				});
	},

	encode: function(url){
		return encodeURI(encodeURI(url));
	},

	//按钮样式:btn btn-default  btn-primary btn-success btn-info btn-warning btn-danger btn-link
	
	
	/*
	 * 点击表格的行，改变颜色。
	 * id： tr的id
	 * type： 颜色样式 success info 等。
	 */
	clicktr: function(id,type){
		$("#"+ id).toggleClass(type);
	},
	//取消所有的选中状态
	clearTrClass: function(tableId){
		$("#"+tableId +" tbody tr").removeClass();
	},
	
	/*
	 * 通过tableid 获取所有被选中的tr的id， 逗号隔开。  1,2,3
	 * tableId: 表格id
	 * type： 表格样式 success， info 等。
	 */
	getIds: function(tableId, type){
		//console.info(type);
		var trids = '';
		$.each($("#"+tableId+" tbody tr"), function(i , n){
			//console.info(i);//序号
			//console.info(n);//元素
			//通过class判断被选中的记录
			//console.info(n.id);
			//console.info($("#"+n.id).attr('class'));
			
			if($("#"+n.id).attr('class') == type ){
				trids += n.id + ',';
			}
			
		});
		var ids = trids.substring(0,trids.length-1);
		return ids;
	},
	
	
	//刷新下拉菜单 <select>标签id,  action地址, 参数
	refreshSelect: function(selectId,action,param){
		var p = {'index' : param};
		var url = common.ctx+"selectOption.do?action="+action;
		common.dajax(url,p,function(data){
			var str = "<option value=''>请选择</option>";
			for(var i=0; i<data.length; i++){
				str += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
			}
			$("#"+selectId).html(str);
		});
	},
	
	//需要点一下确认的信息提示 info,error,success,question,warning
	alertMes: function(msg, callback, type){
		BUI.Message.Alert(msg,callback,type);
	},
	
	//提示是否继续操作
	confirmMes: function(msg, callback, type){
		BUI.Message.Confirm(msg,callback,type);
		
	},
	
	//操作成功 1.2秒消失
	showOK: function() {
        BUI.Message.Show({
          msg : '操作成功!',
          icon : 'success',
          buttons:[],
          autoHide : true,
          autoHideDelay : 1200
        });  		
	},
 
//	"alert alert-success"--success
//	"alert alert-info"   --info
//	"alert alert-warning"--warning
//	"alert alert-danger" --danger
	/* bootstrap 弹出提示层。
	 * 1 类型 上面四种
	 * 2 提示内容
	 * 3 是否可关闭 true false
	 * 4 自动关闭时间 负数为不关闭     ********只有在设置自动关闭时间时候，遮盖层才会自动关闭， 不自动关闭遮盖层不会关闭*********
	 * 5 是否开启 遮盖效果，  true false */
	 
	showMes: function(type,str,canClose,autoclosetime,showCover){
		if(showCover == true){
			common.showCover();
		}
		
		$("#imformdiv").remove();
		$("body").append("<div id='imformdiv' class='alert alert-"+type+"' >"
				+str+"</div>");
		if(canClose==true){
		$("#imformdiv").append("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>");
		}
	    //设置遮盖层的样式
	    $("#imformdiv").css({ "display": "none", "left": "32%", "top": "22%", "position": "fixed", "z-index": "7" });
	    $("#imformdiv").show();
	    if(autoclosetime > 0){
	    	setTimeout(function(){
				$("#imformdiv").hide();
				if(showCover == true){
					$("#coverdiv").hide();
				}
			},autoclosetime);
	    }
	    
	},
	
	showCover: function(){
		//上传阴影， 比较暗
		$("#coverdiv").remove();
	    $("body").append("<div id='coverdiv'></div>");
	    $("#coverdiv").css({ "display": "none", "left": "0px", "top": "0px", "position": "absolute", "z-index": "7", "background-color": "#777", "width": "100%", "height": "100%", "filter": "alpha(opacity=30)","-moz-opacity": "0.3","opacity": ".30" });
	    $("#coverdiv").show();
	}


		
};