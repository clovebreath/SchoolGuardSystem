<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang=“zh-CN”>
	<head>
		<meta charset="UTF-8">
	    <script type="text/javascript" src="../js/ajax.js"></script>
	    <script type="text/javascript" src="../js/driver.js"></script>
	    <script type="text/javascript" src="../js/operation.js"></script>
		<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="../css/style.css"/>
		<title>visitor</title>
	</head>
	<body>
		<div>
			<p  id="staff-bg-letter">VISITOR</p>
		</div>
		<div id="staff-main">
			<object id="stdfcectl" CLASSID="CLSID:41BCE50C-D829-4E8E-A1AD-380EAA7AA02E"></object>
			<OBJECT classid="clsid:F1317711-6BDE-4658-ABAA-39E31D3704D3" id='idcard' name='rdcard' style="display:none;"></OBJECT>
		</div>
		<div id="staff-right">
			<div id="staff-message">
				<div id="staff-imgs">
					<img  id="vi_log_pic"   src="../image/000.png"/>
					<img  id="vi_idcard_pic"   src="../image/000.png"/>
					<img  id="vi_new_pic" src="../image/888.png"/>
				</div>
				<table id="people_data" class="table table-bordered">
					  <tbody style="width: 100%;" >
					    <tr><td class="tb-front">身份证号</td><td id="people_id" class="tb-back">---</td></tr>
					    <tr><td class="tb-front">姓名</td><td id="people_name"  class="tb-back">---</td></tr>
					    <tr><td class="tb-front">身份</td><td id="people_identity"  class="tb-back">---</td></tr>
					    <tr><td class="tb-front">能否通过</td><td id="people_canleave"   class="tb-back">---</td></tr>
					  </tbody>
				</table>
			</div>
			<div id="staff-tips">
				<p id="staff-tips-message">请刷身份证</p>
			</div>
			<div id="staff-btnarea">
				<button id="capture" onclick="capture('vi_new_pic');changeButton('capture');changeState('visitor');" disabled="true">拍照</button>
				<button id="back" onclick="window.history.back(-1);">返回</button>
			</div>
		</div>		
		<div id="footer">
			<p id="footer-bg-letter"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>EYECOOL</p>
			<p id="footer-front-text">「眼神科技」月蚀</p>
		</div>
        <script type="text/javascript" for='idcard' event="Readed()">
        	getMessage();
			document.getElementById("staff-tips-message").innerHTML="身份证信息读取成功，请点击拍照进行认证......";
        	document.getElementById("vi_idcard_pic").src=rd_img;
        	document.getElementById('capture').disabled= false ;
			document.getElementById("people_id").innerHTML=rd_id;
			document.getElementById("people_name").innerHTML=rd_name;
        </script>
	    <script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
			// 拍照成功回调
			changeButton('capture');
			changeButton('back',false);
			if(0==dwResult){
				document.getElementById("staff-tips-message").innerHTML="拍照成功，正在匹配数据，请稍后......";
				getImage(dwResult);
				document.getElementById("vi_new_pic").src=dataFromCamera;
				societyManButtion(rd_id,rd_name,rd_base64,rd_sex,cap_base64);
			}else{
				document.getElementById("staff-tips-message").innerHTML="点击拍照进行认证";
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
        	//	loadParam(document.getElementById("staff-main").style.width, document.getElementById("staff-main").style.height, 0, 0, 0, 1, 3000, 10, 0, 0, 18, 18, 18, 18, 1, 256, 15);
        		loadParam(600, 450, 0, 0, 0, 1, 3000, 10, 0, 0, 18, 18, 18, 18, 1, 256, 15);
        		openDevice();
        		read_rdcard();
        	} else {
        		setTimeout(arguments.callee, 10);
        	}
        })();
        window.onbeforeunload=function(){
        	closeDevice();
        	end_rdcard();
        }
    </script>
	</body>
</html>