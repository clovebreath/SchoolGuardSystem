				var schoolManButtion=function(newphoto){
					var json="{\"identity\": \"school\",\"message\":{\"picture\": \""+newphoto+"\"}}";
					var xmlhttp;
					if (window.XMLHttpRequest){
					    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
					    xmlhttp=new XMLHttpRequest();
					}else{
					    // IE6, IE5 浏览器执行代码
					    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}
					xmlhttp.onreadystatechange=function(){//图片数据预处理之后，将图片数据传递给getResult
					  if (xmlhttp.readyState==4 && xmlhttp.status==200)
					    {
							var xmlhttpRes;
							if (window.XMLHttpRequest){
							    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
							    xmlhttpRes=new XMLHttpRequest();
							}else{
							    // IE6, IE5 浏览器执行代码
							    xmlhttpRes=new ActiveXObject("Microsoft.XMLHTTP");
							}
							var result=xmlhttp.responseText;
							
							xmlhttpRes.open("POST","getResult",true);
							xmlhttpRes.setRequestHeader("Content-type","application/json");
							xmlhttpRes.setRequestHeader("charset","utf-8");
							xmlhttpRes.send(result);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(json);
				}	
				var societyManButtion=function(){
					var json=arguments[0];
					var xmlhttp;
					if (window.XMLHttpRequest){
					    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
					    xmlhttp=new XMLHttpRequest();
					}else{
					    // IE6, IE5 浏览器执行代码
					    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}
					xmlhttp.onreadystatechange=function(){//图片数据预处理之后，将图片数据传递给getResult
					  if (xmlhttp.readyState==4 && xmlhttp.status==200)
					    {
							var xmlhttpRes;
							if (window.XMLHttpRequest){
							    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
							    xmlhttpRes=new XMLHttpRequest();
							}else{
							    // IE6, IE5 浏览器执行代码
							    xmlhttpRes=new ActiveXObject("Microsoft.XMLHTTP");
							}
							var result=xmlhttp.responseText;
							
							xmlhttpRes.open("POST","getResult",true);
							xmlhttpRes.setRequestHeader("Content-type","application/json");
							xmlhttpRes.setRequestHeader("charset","utf-8");
							xmlhttpRes.send(result);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(json);
				}	