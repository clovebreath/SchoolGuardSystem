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
			<div class="col-md-6 column">
				<div class="jumbotron">
					<h2>
						说明：
					</h2>
					<p>
						请正对摄像头，然后点击拍照按钮进行拍照认证。
					</p>
					<p>
						 <a class="btn btn-primary btn-small btn-block" href="index.jsp">返回</a>
					</p>
				</div>
			</div>
			<div class="col-md-6 column">
				<div id="getphoto" style="width:auto;height:250px;background:lightgrey;margin:0px;"></div>
				<button type="button" class="btn btn-default btn-block" onclick="location.href='/getPhotoAndCard.Servlet'">拍照认证</button>
				<script type="text/javascript">
					var json;
					var xmlhttp;
					if (window.XMLHttpRequest){
					    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
					    xmlhttp=new XMLHttpRequest();
					}else{
					    // IE6, IE5 浏览器执行代码
					    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}
					xmlhttp.onreadystatechange=function(){
					  if (xmlhttp.readyState==4 && xmlhttp.status==200)
					    {
					    document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
					    }
					  } 
formedmess22="identity=society&message={\"picture1\": \"base64.\",\"picture2\": \"base64\",\"id\": \"id\",\"name\": \"name\"}";	

					xmlhttp.open("POST","getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(formedmess22);
					
					var result=xmlhttp.responseText;
					
					xmlhttp.open("POST","getResult",true);
					xmlhttp.setRequestHeader("Content-type","application/json");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(result);
					//window.location.href="success.jsp";
					
				</script>
			</div>
		</div>
	</div>	
</body>
</html>