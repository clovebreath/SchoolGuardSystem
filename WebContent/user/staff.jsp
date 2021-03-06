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
		<meta charset="UTF-8">
		<script type="text/javascript" src="../js/jquery-3.2.1.min.js"></script>
	    <script type="text/javascript" src="../js/driver.js"></script>
	    <script type="text/javascript" src="../js/ajax.js"></script>
	    <script type="text/javascript" src="../js/operation.js"></script>
		<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="../css/style2.css"/>
		<title>staff</title>
	</head>
	<body>
	<div id="staff_wrapper">
		<div id="cap_main">
			<object id="stdfcectl" CLASSID="CLSID:41BCE50C-D829-4E8E-A1AD-380EAA7AA02E"></object>
		</div>
		<div id="guard_right">
			<div id="message">
				<div id="all_imgs">
					<img id="log_pic" class="small_pic">
					<img id="stu_pic" class="small_pic">
					<img id="cap_pic">
				</div>
				<table id="people_data">
					  <tbody style="width: 100%;" >
					    <tr><td class="tb-front">学工号</td><td id="people_id" class="tb-back">---</td></tr>
					    <tr><td class="tb-front">姓名</td><td id="people_name" class="tb-back">---</td></tr>
					    <tr><td class="tb-front">班级</td><td id="people_identity" class="tb-back">---</td></tr>
					    <tr><td class="tb-front">请假状态</td><td id="people_canleave" class="tb-back">---</td></tr>
					    <tr><td class="tb-front">班主任</td><td id="people_teacher" class="tb-back">---</td></tr>
					    <tr><td class="tb-front">班主任联系方式</td><td id="people_t_contact" class="tb-back"><a href="#" onclick="alert('网络错误，拨号失败！')">---</a></td></tr>
					  </tbody>
				</table>
			</div>
			<div id="status_tips">
				<p id="status_tips_message">请点击拍照进行认证</p>
			</div>
			<div id="btnarea">
				<div id="cap_btn" class="guard_btn"></div>
				<div id="exit_btn" class="guard_btn"></div>
			</div>
		</div>		
		<script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
			// 采集成功回调
			changeButton('exit_btn',false);
			if(0==dwResult){
				changeButton('cap_btn',true);
				getImage(dwResult);
				document.getElementById("cap_pic").src=dataFromCamera;
				document.getElementById("status_tips_message").innerHTML="拍照成功，正在匹配数据，请稍后......";
				schoolManButtion(cap_base64);
			}else{		
				changeButton('cap_btn');
				document.getElementById("status_tips_message").innerHTML="点击拍照进行认证";
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
	   //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function () {
	        closeWebSocket();
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
        (function(){
        	if (document.readyState && document.readyState == 'complete') {
        		// doing
        		//loadParam(document.getElementById("cap_main").style.width, document.getElementById("cap_main").style.height, 0, 0, 0, 1, 3000, 10, 0, 0, 18, 18, 18, 18, 1, 256, 15);

        		loadParam(600, 450, 0, 0, 0, 1, 3000, 10, 0, 0, 18, 18, 18, 18, 1, 256, 15);
        		openDevice();
        	} else {
        		setTimeout(arguments.callee, 10);
        	}
        })();
        window.onbeforeunload=function(){
        	closeDevice();
        }
    </script>
    <script type="text/javascript" src="../js/divAdapt.js"></script>
	</div>
	</body>
</html>