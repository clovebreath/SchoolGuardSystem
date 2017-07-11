<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="java.util.* " %>
<%@ page import="java.io.* " %>
<%@ page import="faceRecognition.*" %>
<!DOCTYPE html>
<html lang=“zh-CN”>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <TITLE>stdfcectlTest</TITLE>
    <script type="text/javascript" src="../js/ajax.js"></script>
    <script type="text/javascript" src="../js/driver.js"></script>
    <script type="text/javascript" src="../js/operation.js"></script>
	
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
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
    <div style="text-align:center;">
        <OBJECT ID="stdfcectl"  CLASSID="CLSID:41BCE50C-D829-4E8E-A1AD-380EAA7AA02E"  style="width:320px;height:240px;"></OBJECT>
        <img width="320px" height="240px" border="1" name="my_pic" id="new_pic" style="margin: 0;vertical-align: top;">
        <img border="1" name="log_pic" id="log_pic" style="margin: 0;height:240px;width:auto;vertical-align: top;display:none;"> 
    </div>     
  	
    <button type="button"  id="captureBtn" class="btn btn-default btn-block" onclick="capture();changeButton('captureBtn');changeLogpic();">拍照认证</button>
    <a href="../index.jsp"><button id="returnBtn" type="button" class="btn btn-default btn-block" >返回</button></a>
    
<table class="table">
  <thead>
    <tr><th>名称</th><th>数据</th></tr>
  </thead>
  <tbody id="people_data">
    <tr><td>身份</td><td id="people_identity"></td></tr>
    <tr><td>学工号</td><td id="people_id"></td></tr>
    <tr><td>姓名</td><td id="people_name"></td></tr>
    <tr><td>能否通过</td><td id="people_canleave" ></td></tr>
  </tbody>
</table>
  	<div id="showing" style="width:100%;height:150px;border:red solid;display:block;"></div>
  	<div id="newMessage" style="width:100%;height:150px;border:grey solid;"></div>
  	<textarea id="text" style="width:100%;height:100px;border:grey solid;"></textarea>
  	
    <script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
	// 采集成功回调
	changeButton('captureBtn');

	if(0==dwResult){
		show.innerHTML="";
		getImage(dwResult);
		document.getElementById("new_pic").src=dataFromCamera;
		schoolManButtion(cap_base64);
	}else{
		
		alert("请调整姿势重新拍照！");
		
	}

	</script>

	<script type="text/javascript" >
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
	        setMessageInnerHTML("WebSocket连接成功");
	    }

	    //接收到消息的回调方法
	    websocket.onmessage = function (event) {
	        setMessageInnerHTML(event.data);
	    }

	    //连接关闭的回调方法
	    websocket.onclose = function () {
	        setMessageInnerHTML("WebSocket连接关闭");
	    }

	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function () {
	        closeWebSocket();
	    }

	    //将消息显示在网页上
	    function setMessageInnerHTML(innerHTML) {
	        document.getElementById('newMessage').innerHTML += innerHTML + '<br/>';
	    }

	    //关闭WebSocket连接
	    function closeWebSocket() {
	        websocket.close();
	    }

	    //发送消息
	    function send(message) {
	        websocket.send(message);
	    }

	</script>

    <script type = "text/javascript">
    	show=document.getElementById("showing");
    		

        (function(){
        	if (document.readyState && document.readyState == 'complete') {
        		// doing
        		loadParam(640, 480, 0, 0, 0, 1, 3000, 15, 0, 0, 18, 18, 18, 18, 1, 256, 15);
        		openDevice();
        	} else {
        		setTimeout(arguments.callee, 10);
        	}
        })();
        window.onbeforeunload=function(){
        	closeDevice();
        }
    </script>
</body>
</html>