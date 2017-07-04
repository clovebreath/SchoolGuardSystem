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
	<div style="border:green solid">
	<% Random random = new Random();
	   int res=random.nextInt(3);
	   String name="lili";
	   String classes="sannianerban";
	   String position="teacher";
	   String stuname="zhangsan";
	   String teacher="lili";
	   if(res==0){//教职工允许出入    %> 
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;margin:10px;">
		    <p>name:<%= name %></p>
		  	<p>position:<%= position %></p>	
		  </div>
	<% }else if(res==1){//学生允许出入 %>
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;margin:10px;">
		    <p>name:<%= name %></p>
		  	<p>class:<%= classes %></p>	
		  	<p>teacher:<%= teacher %></p>	
		  </div>
	<% }else if(res==2){//家长允许出入%>
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;margin:10px;">
		  	<p>name:<%= name %></p>
		  	<p>position:<%= position %></p>
		  	<p>stuname:<%= stuname %></p>	
		  	<p>teacher:<%= teacher %></p>	
		  </div>
	<% }%>	
	</div>
</body>
</html>