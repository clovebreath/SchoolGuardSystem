<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-4 column">
				<div class="jumbotron">
					<h2>
						说明：
					</h2>
					<p>
						请先刷身份证，待身份证消息读取成功后，点击拍照按钮进行拍照认证。
					</p>
					<p>
						 <a class="btn btn-primary btn-block" href="#">返回</a>
					</p>
				</div>
			</div>
			<div class="col-md-4 column">
				<div id="getphoto" style="width:auto;height:250px;background:lightgrey;margin:0px;"></div>
				<button type="button" class="btn btn-default btn-block" onclick="location.href='waiting.jsp'">拍照认证</button>
			</div>
			<div class="col-md-4 column">
				<div id="getidcard" style="width:auto;height:250px;background:lightgrey;margin:0px;"></div>
				<button type="button" class="btn btn-default btn-block" onclick="location.href='waiting.jsp'">读取信息</button>
			</div>		
		</div>
	</div>	
</body>
</html>