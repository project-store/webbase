<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/jsp/taglib.jsp"%>
<script type='text/javascript' src="${root }dwr/engine.js"></script>
<script type='text/javascript' src="${root }dwr/util.js"></script>
<script type='text/javascript' src="${root }dwr/interface/dwrService.js"></script>

<script type="text/javascript">
	function testStr(){
		console.info("begin test str");
		dwrService.testStr("wangt",function(data){console.info(data)});
	}
	
	function testMap(){
		//*******Map参数写法1 begin
		var data = {
				u : 'username',
				p : 'password'
		}
		//*******Map参数写法1 end
		//*******Map参数写法2 begin
		var d = new Object();
		d.name = 'aa';
		d.sdf='ss';
		//*******Map参数写法2 end
		console.info(d);
		console.info(data);
		dwrService.testMap(d, function(data){
			console.info(data);
			console.info(data.ok);
		})
	}

	
	function testshuzu(){
		var a = new Array();
		a[0] = "1";
		a[1] = "2";
		a[2] = "3";
		dwrService.testShuzu1(a,function(d){//return String[]
			console.info(d);
			console.info(d[0]);
		})
		
		dwrService.testShuzu2(a,function(d){//return List<String>
			console.info(d);
			console.info(d[0]);
		})
		
	}
	
	
	function testList(){
		var lst = [];
		//var a = {name : "aname", password: 'paassword'};
		//var b = {name : "bname", password: 'bpassword'};
		var a = new Object();
		var b = new Object();
		a.name = "aname";
		a.password = "apassword";
		b.name = "bname";
		b.password = "bpassword";
		lst[0] = a;
		lst[1] = b;
		console.info(lst);
		dwrService.testList(lst, function(da){
			console.info(da);
			console.info(da[0]);
			console.info(da[0].ok);
		})
		
	}
	
	function testuser(){
		var u = new Object();
		u.name="uname";
		dwrService.testUser(u,function(d){
			console.info(d);
			console.info(d.name);
		})
		
	}
	
</script>
</head>
<body>
<button onclick="testStr();" >testStr</button><br/>

<button onclick="testMap();" >testMap</button><br/>

<button onclick="testshuzu();" >testshuzu</button><br/>
<button onclick="testList();" >testList</button><br/>
<button onclick="testuser();" >testUser</button><br/>
</body>
</html>