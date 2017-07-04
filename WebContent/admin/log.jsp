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
	<div style="border:yellow solid">
	<% String name="lili";
	   String classes="sannianerban";
	   String position="teacher";
	   String stuname="zhangsan";
	   String teacher="lili";%> 
	   
		  <div id="imglog" style="width:150px;height:200px;background:lightgrey;display:inline-block;margin:10px;"></div>
		  <div id="messages" style="width:450px;height:200px;background:lightgrey;display:inline-block;">
		  	<p>name:<%= name %></p>
		  	<p>stuname:<%= stuname %></p>
		  	<p>class:<%= classes %></p>	
		  	<p>teacher:<%= teacher %></p>
		  	<p>position:<%= position %></p>	
		  	<p>您尚未预约，正在联系老师中。。。。</p>
		  </div>
		  <button>返回</button>
	</div>
</body>
</html>