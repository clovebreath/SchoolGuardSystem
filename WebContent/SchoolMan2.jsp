<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang=“zh-CN”>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <TITLE>stdfcectlTest</TITLE>
    <script type="text/javascript" src="js/ajax.js"></script>
    <script type="text/javascript" src="js/driver.js"></script>
    <script type="text/javascript" src="js/operation.js"></script>
	
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
    <div style="margin:0 auto;">
        <OBJECT ID="stdfcectl" width="600" height="450" CLASSID="CLSID:41BCE50C-D829-4E8E-A1AD-380EAA7AA02E" ></OBJECT>
        <img width="160" height="120" border="1" name="my_pic" id="new_pic" style="margin: 10px;vertical-align: top;">
    </div>    
    <button type="button" class="btn btn-default btn-block" onclick="capture();">拍照认证</button>
  	<div id="showing" style="width:450px;height:600px;border:red solid;display:none;"></div>
  	
    <script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
	// 采集成功回调
	getImage(dwResult);
	document.getElementById("new_pic").src=dataFromCamera;
	schoolManButtion(dataFromCamera);
	</script>
	
    <script type = "text/javascript">
    	show=document.getElementById("showing");
        (function(){
        	if (document.readyState && document.readyState == 'complete') {
        		// doing
        		loadParam(600, 450, 0, 0, 0, 1, 3000, 15, 0, 0, 18, 18, 18, 18, 1, 256, 15);
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