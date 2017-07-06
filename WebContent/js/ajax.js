				var schoolManButtion=function(newphoto){
					var data="identity=school&message={\"picture\": \""+newphoto+"\"}";
					console.log(data);
					var xmlhttp;
					if (window.XMLHttpRequest){
					    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
					    xmlhttp=new XMLHttpRequest();
					}else{
					    // IE6, IE5 浏览器执行代码
					    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}
					xmlhttp.onreadystatechange=function(){//根据返回数据shi'x
						  if (xmlhttp.readyState==4 && xmlhttp.status==200)
						    {
								var result=xmlhttp.responseText;
								console.log("response",result);
								show.innerHTML+=("--result--"+result); 
								//window.location.href="success1.jsp"; 
						    }else{
						    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
						    }

					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","../getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(data);
				}	
				var societyManButtion=function(rd_id,rd_name,rd_img,rd_sex,dataFromCamera){
					console.log(new Date(),rd_id);
					console.log(new Date(),rd_name);
					console.log(new Date(),rd_img);
					console.log(new Date(),dataFromCamera);
					var data="identity=society&message={\"picture1\": \""+dataFromCamera+"\",\"picture2\":\""+rd_img+"\",\"id\":\""+rd_id+"\",\"name\":\""+rd_name+"\"}";
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
							var result=xmlhttp.responseText;
							console.log("response",result);
							show.innerHTML+=("--result--"+result); 
							//window.location.href="success2.jsp"; 
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","../getPhotoAndCard",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.setRequestHeader("charset","utf-8");
					xmlhttp.send(data);
				}	