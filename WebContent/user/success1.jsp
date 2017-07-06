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

<%! String name="小张";
	String classes="三年二班";
	String 班主任="小王";
	String contactPositon="园艺工";
	String contactPhone="13256758888";
%>
<div class="container">
	<div class="row clearfix" style="border:green solid">
		<div class="col-md-12 column" style="text-align:center;font-size:3em">
			Hello
		</div>

	</div>
	<div class="row clearfix" style="border:green solid">
		<div class="col-md-2 column" style="text-align:center">
			
		</div>
		<div class="col-md-8 column" style="margin-top:60px;">
			<div class="row clearfix">
				<div class="col-md-4 column">
					<img style="width:150px;height:100%" src="imgs/1.jpeg" class="img-rounded" />
				</div>
				<div class="col-md-8 column">
					<ul class="list-unstyled">
						<li>
							姓名：<%= name %>
						</li>
						<li>
							班级：<%= classes %>
						</li>
						<li>
							班主任：<%= name %>
						</li>
						<li>
							认证结果：<strong>允许外出！</strong>
						</li>
					</ul>
					<button class="btn btn-primary btn-block">确定</button>
				</div>
			</div>
		</div>
		<div class="col-md-2 column">
		</div>
	</div>
</div>
</body>
</html>