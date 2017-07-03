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
	<% Random random = new Random();
	   int res=random.nextInt(5);
	   String name="lili";
	   String classes="sannianerban";
	   String position="teacher";
	   String stuname="zhangsan";
	   String teacher="lili";
	   if(res==0){//教职工允许出入    %> 
		  <div id="imgnow" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <p>name:<%= name %></p>
		  <p>position:<%= position %></p>
	<% }else if(res==1){//学生允许出入 %>
		  <div id="imgnow" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <p>name:<%= name %></p>
		  <p>class:<%= classes %></p>
	<% }else if(res==2){//家长允许出入%>
		  <div id="imgnow" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="imgstu" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <p>name:<%= name %></p>
		  <p>stuname:<%= stuname %></p>	
		  <p>teacher:<%= teacher %></p>	
	<% }else{//家长不允许出入%>		
		  <div id="imgnow" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="imgIdentity" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <p>name:<%= name %></p>	  		
	<% }%>
	
</body>
</html>