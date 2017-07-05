<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">

<HEAD>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <TITLE>stdfcectlTest</TITLE>
    <script type="text/javascript" src="js/operation.js"></script>
    <script type="text/javascript" src="js/cameraLoad.js"></script>
    <script type="text/javascript" src="js/rdcardLoad.js"></script>
</HEAD>

<BODY>
    <div>
        <OBJECT ID="stdfcectl" width="640" height="480" CLASSID="CLSID:41BCE50C-D829-4E8E-A1AD-380EAA7AA02E"></OBJECT>
       <script language="JavaScript" for="stdfcectl" event="CallBackCheckLiveResult(dwResult)">
			getImage(dwResult);
		</script>
        <OBJECT classid="clsid:F1317711-6BDE-4658-ABAA-39E31D3704D3" codebase="SDRdCard.cab#version=1,3,6,4" width=330 height=210 hspace=0 vspace=0 id=idcard name=rdcard style="vertical-align: top;"></OBJECT>
        <script type="text/javascript" for=idcard event="Readed()">
        	show_id_data();
        </script>
        <img width="102" height="120" border="0" name="id_photo" id=jpgfile style="left: 10px;vertical-align: top;">
        <img width="160" height="120" border="0" name="cap_photo" id=jpgfile style="left: 10px;vertical-align: top;">
    </div>
    <br />
    <br />
    <div style="text-align: center;">
        <input type="button" value="open" onclick="javascrypt:openDevice()" />&nbsp;&nbsp;
        <input type="button" value="capture" onclick="javascrypt:capture()" />&nbsp;&nbsp;
        <input type="button" value="close" onclick="javascrypt:closeDevice()" />&nbsp;&nbsp;
        <br />
        <br />
        <input type="button" value="openRdcard" onclick="open_rdcard()" />&nbsp;&nbsp;
        <input type="button" value="closeRdcard" onclick="close_rdcard()" />&nbsp;&nbsp;
        <input type="button" value="readRdcard" onclick="read_rdcard()">&nbsp;&nbsp;
        <input type="button" value="endRdcard" onclick="end_rdcard()">&nbsp;&nbsp;
        <br />
        <br />
        <textarea id="parm" cols="43" rows="12"></textarea>&nbsp;&nbsp;
        <textarea id="data" cols="43" rows="12"></textarea>&nbsp;&nbsp;
        <textarea id="stat" cols="43" rows="12"></textarea>
        <br />
        <br />
        <textarea id="id_data" cols="43" rows="12"></textarea>
        <textarea id="json_data" cols="43" rows="12"></textarea>
    </div>
</BODY>

</HTML>

