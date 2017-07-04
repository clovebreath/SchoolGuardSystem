<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang=“zh-CN”>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
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
	<% Random random = new Random();
	   int res=random.nextInt(5);
	   String name="lili";
	   String classes="sannianerban";
	   String position="teacher";
	   String stuname="zhangsan";
	   String teacher="lili";
	   String teacherPhone="1786569956";%>
	   
<div class="container">
	<div class="row clearfix">
		<div class="col-md-4 column" style="background:grey;">
			<img alt="140x140" src="v3/default3.jpg" />
		</div>
		<div class="col-md-4 column" style="background:grey;">
			<img alt="140x140" src="v3/default3.jpg" />
		</div>
		<div class="col-md-4 column">
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<p><em>name:</em><%= name %></p>
			<p><em>stuname:</em><%= stuname %></p>
			<p><em>classes:</em><%= classes %></p>
			<p><em>teacherPhone:</em><%= teacherPhone %></p>
		</div>
		<button type="button" class="btn btn-lg btn-primary">允许通过</button>
		<button type="button" class="btn btn-lg btn-primary">禁止通过</button>
	</div>
</div>
	   
</body>
</html>