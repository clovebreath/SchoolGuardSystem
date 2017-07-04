				var schoolManButtion=function(newphoto){
					console.log(newphoto);
					var data="identity=school&message={\"picture\": \""+newphoto+"\"}";
					console.log(data);
					var te="/9j/4AAQSkZJRgABAQEAAAAAAAD/2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh7/wAARCAHgAoADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4 Tl5ufo6erx8vP09fb3 Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVV";
					var testdata="identity=school&message={\"picture\": \""+te+"\"}";
					var xmlhttp;
					if (window.XMLHttpRequest){
					    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
					    xmlhttp=new XMLHttpRequest();
					}else{
					    // IE6, IE5 浏览器执行代码
					    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}
					xmlhttp.onreadystatechange=function(){//根据返回数据shi'x
						var result=xmlhttp.responseText;
						
						window.location.href="success1.jsp"; 
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(data);
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
							xmlhttpRes.send(testdata);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(json);
				}	