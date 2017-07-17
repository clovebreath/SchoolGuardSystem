<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
<%=request.getParameter("id") %>

            <div id="info_div" class="each_ctt">
                <div id="photos">
                    <img src="" width="400" height="300" id="cap_photo">
                    <div id="s_photo">
                        <img src="" width="100" height="120" id="db_photo">
                        <img src="" width="100" height="120" id="id_photo">
                        <div id="expr">身份：<lable id="people_identity"></lable></div>
                    </div>
                </div>
                <div id="info_container">
                    <div id="text_info">
                        <div id="name">姓名：<label id="people_name"></label></div>
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
                  </div>
                

	              <div id="status">
	                	<label id="result" >结果</label>
	              </div>     	
            </div>
            
            <script type="text/javascript">
            
            function setMessage(data) {

		    	var people=JSON.parse(data);
		    	getPicture(people.details.recordid);
		    	switch (people.details.identity)
		    	{
		    	    case "parent":
		    	    	$(".student").hide();
		    	    	$(".worker").hide();
		    	    	$(".blacklist").hide();
		    	    	$(".parent").show();
		    	    	$("#people_identity").text(people.details.identity);
		    	    	$("#people_id_society").text(people.details.id);
		    	    	$("#people_name").text(people.details.name);
		    	    	$("#people_canleave_society").text(people.result);
		    	    	$("#child_name").text(people.details.stuName);
		    	    	$("#order_teacher").text(people.details.teacherName);
		    	    	$("#order_teacher_con").text(people.details.teacherPhone);
		    	    	if("notallowed"==people.result){
		    	    		$("#result").text("不许通过");
		    	    		$("#people_canleave_society").text("未预约");
		    	    		$("#notorderedval").show();
		    	    		$("#notorderedbtn").show();
		    	    		$("#notorderedval").val(people.details.recordid);
		    	    	}else{
		    	    		$("#result").text("允许通过");
		    	    		$("#people_canleave_society").text("已预约");
		    	    		$(".notallowed").hide();	
		    	    	}
		    	    break;
		    	    case "blacklist":
		    	    	$(".student").hide();
		    	    	$(".worker").hide();
		    	    	$(".parent").hide();
		    	    	$(".blacklist").show();
		    	    	$("#people_identity").text(people.details.identity);
		    	    	$("#people_id_society").text(people.details.id);
		    	    	$("#people_name").text(people.details.name);
		    	    	$("#contact_name").text(people.details.contactName);
		    	    	$("#contact_phone").text(people.details.contactPhone);
		    	    	$("#note").text(people.details.note);
		    	    	if("notallowed"==people.result){
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
		    	    	$("#people_identity").text(people.details.identity);
		    	    	$("#people_id_school").text(people.details.id);
		    	    	$("#people_name").text(people.details.name);
		    	    	$("#people_canleave_school").text(people.result);
		    	    	$("#order_teacher").text(people.details.teacherName);
		    	    	$("#order_teacher_con").text(people.details.teacherPhone);
		    	    	if("notallowed"==people.result){
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
		    	    	$("#people_identity").text(people.details.identity);
		    	    	$("#people_position").text(people.details.position);
		    	    	$("#people_phone").text(people.details.phone);
		    	    	$("#people_id_school").text(people.details.id);
		    	    	$("#people_name").text(people.details.name);
		    	    	if("notallowed"==people.result){
		    	    		$("#result").text("不许通过");
		    	    	}else{
		    	    		$("#result").text("允许通过");
		    	    	}
		    	}
            }
            </script>
</body>
</html>