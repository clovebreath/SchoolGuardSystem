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
    <script type="text/javascript" src="js/cameraLoad.js"></script>
    <script type="text/javascript" src="js/operation.js"></script>
    <script type="text/javascript" src="js/rdcardLoad.js"></script>
	
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
    <div>
        <OBJECT ID="stdfcectl" width="640" height="480" CLASSID="CLSID:41BCE50C-D829-4E8E-A1AD-380EAA7AA02E"></OBJECT>
        <script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
	        getImage(dwResult);
        </script> 
        <OBJECT classid="clsid:F1317711-6BDE-4658-ABAA-39E31D3704D3" codebase="SDRdCard.cab#version=1,3,6,4" width=330 height=210 hspace=0 vspace=0 id=idcard name=rdcard style="vertical-align: top; display:none"></OBJECT>    
        <img width="160" height="120" border="0" name="cap_photo" id="jpgfile" style="left: 10px;vertical-align: top;">
    </div>
    <script type="text/javascript">openDevice();</script>
    <button type="button" class="btn btn-default btn-block" onclick="end_rdcard();capture();societyManButtion(getdata());">拍照认证</button>
    
    
    <script type = "text/javascript">
        window.onload = function()
        {
        	openDevice();
        	open_rdcard();
        	read_rdcard();
        }
        window.onbeforeunload=function(){
        	closeDevice();
        	
        }
    </script>
</body>
</html>