<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="border:red solid">
	<% Random random = new Random();
	   int res=random.nextInt(3);
	   String name="lili";
	   String classes="sannianerban";
	   String position="teacher";
	   String stuname="zhangsan";
	   String teacher="lili";
	   if(res==0){//黑名单不允许出入    %> 
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;margin:10px;">
		  	<p>很抱歉，您未能通过认证。</p>
		  </div>
		  <button>确定</button>
	<% }else if(res==1){//学生不允许出入 %>
		  <div id="imgnow" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
          <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;margin:10px;">
			  <p>name:<%= name %></p>
			  <p>class:<%= classes %></p>
			  <p>teacher:<%= teacher %></p>
			  <p>抱歉，没有请假不能离开学校！</p>
			  <button>确定</button>
		  </div>
		  
	<% }else if(res==2){//家长不允许出入%>
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;">
		  	<p>name:<%= name %></p>
		  	<p>stuname:<%= stuname %></p>
		  	<p>class:<%= classes %></p>	
		  	<p>teacher:<%= teacher %></p>
		  	<p>position:<%= position %></p>	
		  	<p>联系老师失败，您不能进入。</p>
		  </div>
	<% }%>	
	</div>
</body>
</html>