<%@page import="dbTools.dbTools"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="width:100%;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=11" />
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
     <script type="text/javascript" src="../js/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body style="width:100%;">
<% dbTools dbTool=new dbTools(); 
	String id=request.getParameter("id");
	System.out.println(id);
	String result=dbTool.getRecordDetailById(Integer.parseInt(id));
	System.out.println(result);
%>

            <div id="info_div" class="each_ctt" style="width:100%;" >
                <div id="photos" style="width:100%;text-align:center;">
                    <img src="" width="300" height="200" id="cap_photo">
                    <img src="" width="150" height="200"  id="db_photo">
                    <img src="" width="150" height="200" style="display:none;"  id="id_photo">
                </div>
                <div id="info_container" style="width:100%;text-align:center;">
                
<table class="table" style="width:80%;margin:10px auto;">
  <caption>详细信息</caption>
  <tbody>
    <tr id="name"><td>姓名</td><td id="people_name"></td></tr>
    <tr id="expr"><td>身份</td><td id="people_identity"></td></tr>
    <tr class="worker student" style="display: none"><td>学工号</td><td id="people_id_school"></td></tr>
    <tr class="parent blacklist" style="display: none"><td>身份证号</td><td id="people_id_society"></td></tr>
    <tr class="student" style="display: none"><td>是否请假</td><td id="people_canleave_school"></td></tr>
    <tr class="worker" style="display: none"><td>职位</td><td id="people_position"></td></tr>
    <tr class="worker" style="display: none"><td>联系方式</td><td id="people_phone"></td></tr>
    <tr class="parent" style="display: none"><td>是否预约</td><td id="people_canleave_society"></td></tr>
    <tr id="name_stu" class="parent" style="display: none"><td>学生姓名</td><td id="child_name"></td></tr>
  <tr id="teacher"  class="parent student"  style="display: none"><td>班主任</td><td id="order_teacher"></td></tr>
  <tr id="t_contact" class="parent student"  style="display: none"><td>班主任联系方式</td><td id="order_teacher_con"></td></tr>
  <tr class="blacklist" style="display: none"><td>负责人</td><td id="contact_name"></td></tr>
  <tr class="blacklist" style="display: none"><td>负责人联系方式</td><td id="contact_phone"></td></tr>
  <tr class="blacklist" style="display: none"><td>备注</td><td id="note" ></td></tr>
	              <tr id="status" style="width:100%;">
	                	<td >结果</td><td id="result" ></td>
	              </tr>     	
  </tbody>
</table>
<!-- 
                    <div id="text_info">
                        <div id="name">姓名：<label id="people_name"></label></div>
                        <div id="expr">身份：<lable id="people_identity"></lable></div>
                        <div class="worker student" style="display: none">学工号：<label  id="people_id_school"></label></div>
                        <div class="parent blacklist" style="display: none">身份证号：<label  id="people_id_society"></label></div>
                        <div  class="student" style="display: none" >是否请假：<label id="people_canleave_school" ></label></div>
                        <div class="worker" style="display: none">职位：<label  id="people_position"></label></div>
                        <div class="worker" style="display: none">联系方式：<label  id="people_phone"></label></div>
                        <div class="parent" style="display: none" >是否预约：<label id="people_canleave_society" ></label></div>
                        <div id="name_stu" class="parent" style="display: none">学生姓名：<label id="child_name" ></label></div>
                        <div id="teacher"  class="parent student"  style="display: none">班主任：<label id="order_teacher"></label></div>
                        <div id="t_contact" class="parent student"  style="display: none">班主任联系方式：<lable  id="order_teacher_con"></lable></div>
                        <div class="blacklist" style="display: none">负责人：<label  id="contact_name"></label></div>
                        <div class="blacklist" style="display: none">负责人联系方式：<label  id="contact_phone"></label></div>
                        <div class="blacklist" style="display: none">备注：<label id="note" ></label></div>
                    </div>
                    
                     -->
                  </div>
                

            </div>
            
            <script type="text/javascript">
            var data = '<%=result  %>';
            
            function setMessage(data) {

		    	var people=JSON.parse(data);
            	$("#db_photo").attr("src",'data:image/jpeg;base64,'+people.logpic);
            	$("#cap_photo").attr("src",'data:image/jpeg;base64,'+people.newpic);
		    	//getPicture(people.details.recordid);
		    	switch (people.identity)
		    	{
		    	    case "parent":
		    	    	$(".student").hide();
		    	    	$(".worker").hide();
		    	    	$(".blacklist").hide();
		    	    	$(".parent").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_id_society").text(people.pid);
		    	    	$("#people_name").text(people.name);
		    	    	$("#people_canleave_society").text(people.order);
		    	    	$("#child_name").text(people.sname);
		    	    	$("#order_teacher").text(people.tname);
		    	    	$("#order_teacher_con").text(people.phone);
		    	    	if("notallowed"==people.status){
		    	    		$("#result").text("不许通过");
		    	    	}else{
		    	    		$("#result").text("允许通过");
		    	    	}
		    	    	if("Y"==people.order){
		    	    		$("#people_canleave_society").text("已预约");
		    	    	}else{
		    	    		$("#people_canleave_society").text("未预约");
		    	    	}
		    	    break;
		    	    case "blacklist":
		    	    	$(".student").hide();
		    	    	$(".worker").hide();
		    	    	$(".parent").hide();
		    	    	$(".blacklist").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_id_society").text(people.pid);
		    	    	$("#people_name").text(people.name);
		    	    	$("#contact_name").text(people.contactname);
		    	    	$("#contact_phone").text(people.phone);
		    	    	$("#note").text(people.note);
		    	    	if("notallowed"==people.status){
		    	    		$("#result").text("不许通过");
		    	    	}else{
		    	    		$("#result").text("允许通过");
		    	    	}
		    	    break;
		    	    case "student":
		    	    	$(".worker").hide();
		    	    	$(".parent").hide();
		    	    	$(".blacklist").hide();
		    	    	$(".student").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_id_school").text(people.pid);
		    	    	$("#people_name").text(people.name);
		    	    	$("#people_canleave_school").text(people.status);
		    	    	$("#order_teacher").text(people.tname);
		    	    	$("#order_teacher_con").text(people.phone);
		    	    	if("notallowed"==people.status){
		    	    		$("#result").text("不许通过");
		    	    		$("#people_canleave_school").text("未请假");
		    	    	}else{
		    	    		$("#people_canleave_school").text("已请假");
		    	    		$("#result").text("允许通过");
		    	    	}
			    	break;
		    	    default:
		    	    //worker
		    	    	$(".parent").hide();
	    	    		$(".blacklist").hide();
	    	    		$(".student").hide();
	    	    		$(".worker").show();
		    	    	$("#people_identity").text(people.identity);
		    	    	$("#people_position").text(people.position);
		    	    	$("#people_phone").text(people.phone);
		    	    	$("#people_id_school").text(people.pid);
		    	    	$("#people_name").text(people.name);
		    	    	if("notallowed"==people.status){
		    	    		$("#result").text("不许通过");
		    	    	}else{
		    	    		$("#result").text("允许通过");
		    	    	}
		    	}
            }
            
            setMessage(data);
            </script>
</body>
</html>