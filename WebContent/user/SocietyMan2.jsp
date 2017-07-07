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
        <img  border="1" name="my_pic" id="new_pic" style="height:240px;width:auto;margin: 0;vertical-align: top;">
        <img  border="1" name="log_pic" id="log_pic" style="height:240px;width:auto;margin: 0;vertical-align: top;display:none;">  <br/>
        <OBJECT classid="clsid:F1317711-6BDE-4658-ABAA-39E31D3704D3" codebase="SDRdCard.cab#version=1,3,6,4" width=330 height=210 hspace=0 vspace=0 id=idcard name=rdcard style="vertical-align: top;"></OBJECT>
        <img  border="1" name="id_pic" id="idcard_pic" style="height:210px;width:auto;margin: 0;vertical-align: top;"> 
        <script type="text/javascript" for=idcard event="Readed()">
        	getMessage();
        	show.innerHTML+=get_data();
        	document.getElementById("idcard_pic").src=rd_img;
        	document.getElementById('captureBtn').disabled= false ;
			document.getElementById("people_id").innerHTML=rd_id;
			document.getElementById("people_name").innerHTML=rd_name;
        </script>
    </div>    
    <button id="captureBtn" type="button" disabled="true" class="btn btn-default btn-block" onclick="capture();changeButton('captureBtn');">拍照认证</button>
        <table class="table table-hover">
          <thead>
		    <tr><th>名称</th><th>数据</th></tr>
		  </thead>
		  <tbody id="people_data">
		    <tr><td>姓名</td><td id="people_name"></td></tr>
		    <tr><td>身份证号 </td><td id="people_id"></td></tr>
		    <tr><td>身份</td><td id="people_identity"></td></tr>
		    <tr><td>能否通过</td><td id="people_canleave" ></td></tr>
		  </tbody>
	  </table>
  	<div id="showing" style="width:100%;height:300px;border:red solid;display:block;"></div>
  	
    <script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
	// 拍照成功回调
	show.innerHTML="";
	changeButton('captureBtn');
	if(0==dwResult){
		getImage(dwResult);
		document.getElementById("new_pic").src=dataFromCamera;
		societyManButtion(rd_id,rd_name,rd_base64,rd_sex,cap_base64);
	}else{
		alert("请调整姿势重新拍照！");
	}
	</script>
	
    <script type = "text/javascript">
    //display:none;
    	show=document.getElementById("showing");
        (function(){
        	if (document.readyState && document.readyState == 'complete') {
        		// doing
        		loadParam(600, 450, 0, 0, 0, 1, 3000, 15, 0, 0, 18, 18, 18, 18, 1, 256, 15);
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