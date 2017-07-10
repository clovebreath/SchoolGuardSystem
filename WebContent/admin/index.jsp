<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=“zh-CN”>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	 <script type="text/javascript" src="../js/ajax.js"></script>
	
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
	<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  
  
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    
</head>
<body>

<div class="container">
   <div class="row">
      <div class="col-md-4">
      	<img alt="注册照片"  id="log_pic" src="" style="width:100px;height:auto">
      	<img alt="现场照片"  id="new_pic" src="" style="width:100px;height:auto">
      </div>
      <div class="col-md-8">
      	<table class="table">
		  <thead>
		    <tr><th>名称</th><th>数据</th></tr>
		  </thead>
		  <tbody id="people_data">
		    <tr><td>身份</td><td id="people_identity"></td></tr>
			<tr class="school" style="display: none"><td >学工号</td><td id="people_id_school"></td></tr>
			<tr class="parent blacklist" style="display: none"><td>身份证号</td><td id="people_id_society"></td></tr>  
		    <tr><td>姓名</td><td id="people_name"></td></tr>
		    <tr><td>能否通过</td><td id="people_canleave_school" ></td></tr>
		    <tr class="parent" style="display: none"><td>是否预约</td><td id="people_canleave_society" ></td></tr>
		     <tr class="parent" style="display: none"><td>孩子姓名</td><td id="child_name" ></td></tr>
		     <tr class="parent" style="display: none"><td>联系老师</td><td id="order_teacher" ></td></tr>
		  
		     <tr class="blacklist" style="display: none"><td>负责人</td><td id="contact_name" ></td></tr>
		     <tr class="blacklist" style="display: none"><td>备注</td><td id="note" ></td></tr>
		    
		  </tbody>
		</table>
      </div>      
   </div>
   <div class="row">...</div>
</div>


	<button type="button" class="btn btn-default btn-block" onclick="location.href='list.jsp'">查看记录</button>				
	<button type="button" class="btn btn-default btn-block" onclick="location.href='setting.jsp'">设置</button>			
	<!--
	<div id="newMessage" style="width:100%;height:100px;border:grey solid;"></div>
	<textarea id="text" style="width:100%;height:100px;border:grey solid;"></textarea>
	<button type="button" class="btn btn-default btn-block" onclick="javascript:send();">发送</button>			
	  -->
	
	
	<script type="text/javascript">
		
		   var websocket = null;
		    //判断当前浏览器是否支持WebSocket
		    if ('WebSocket' in window) {
		        websocket = new WebSocket("ws://localhost:8080/SchoolGuardSystem/websocket");
		    }
		    else {
		        alert('当前浏览器 Not support websocket')
		    }

		    //连接发生错误的回调方法
		    websocket.onerror = function () {
		        setMessageInnerHTML("WebSocket连接发生错误");
		    };

		    //连接成功建立的回调方法
		    websocket.onopen = function () {
		        //setMessageInnerHTML("WebSocket连接成功");
		    }

		    //接收到消息的回调方法
		    websocket.onmessage = function (event) {
		        setMessage(event.data);
		    }

		    //连接关闭的回调方法
		    websocket.onclose = function () {
		       // setMessageInnerHTML("WebSocket连接关闭");
		    }

		    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		    window.onbeforeunload = function () {
		        closeWebSocket();
		    }
		    
		    //将消息显示在网页上
			function setMessageInnerHTML(data){
			    document.getElementById('newMessage').innerHTML += data + '<br/>';   
			}
		    
		    function setMessage(data) {

		    	var people=JSON.parse(data);
		    	getPicture(people.message.id);
		    	switch (people.identity)
		    	{
		    	    case "parent":
		    	    	$(".school").hide();
		    	    	$(".blacklist").hide();
		    	    	$(".parent").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_id_society").text(people.message.id);
		    	    	$("#people_name").text(people.message.name);
		    	    	$("#people_canleave_school").text(people.message.isordered);
		    	    	$("#people_canleave_society").text(people.message.isordered);
		    	    	$("#child_name").text(people.message.student);
		    	    	$("#order_teacher").text(people.message.teacher);
		    	    break;
		    	    case "blacklist":
		    	    	$(".school").hide();
		    	    	$(".parent").hide();
		    	    	$(".blacklist").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_id_society").text(people.message.id);
		    	    	$("#people_canleave_school").text("N");
		    	    	$("#people_name").text(people.message.name);
		    	    	$("#contact_name").text(people.message.personincharge+"cell: "+people.message.personinchargephone);
		    	    	$("#note").text(people.message.note);
		    	    break;
		    	    case "student":
		    	    	$(".parent").hide();
		    	    	$(".blacklist").hide();
		    	    	$(".school").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_id_school").text(people.message.id);
		    	    	$("#people_name").text(people.message.name);
		    	    	$("#people_canleave_school").text(people.message.canleave);
			    	break;
		    	    default:
		    	    //worker
		    	    	$(".parent").hide();
	    	    		$(".blacklist").hide();
	    	    		$(".school").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_canleave_school").text("Y");
		    	    	$("#people_id_school").text(people.message.id);
		    	    	$("#people_name").text(people.message.name);
		    	    	$("#people_canleave_school").text(people.message.canleave);
		    	}
		    	
		    	
		    }

		    //关闭WebSocket连接
		    function closeWebSocket() {
		        websocket.close();
		    }

		    //发送消息
		    function send() {
		        var message = document.getElementById('text').value;
		        websocket.send(message);
		    }
	</script>
</body>
</html>