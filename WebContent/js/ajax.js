				var xmlhttp;
				var result;				
				var schoolManButtion=function(newphoto){
					var data="identity=school&message={\"picture\": \""+newphoto+"\"}";
					console.log(data);
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
								result=xmlhttp.responseText;
								show.innerHTML+=("--result--"+result); 
								var people=JSON.parse(result);
								
								if ("photo_error" == people.result) {
//									document.getElementById("people_identity").innerHTML='';
//									document.getElementById("people_id").innerHTML='';
//									document.getElementById("people_name").innerHTML='';
//									document.getElementById("people_canleave").innerHTML='';
									document.getElementById("log_pic").src='../image/no28.png';
									document.getElementById("log_pic").style.display="inline";
									
									alert("照片中无法识别人脸，请重新拍照");
									
								}else if ("not_match"==people.result){
//									document.getElementById("people_identity").innerHTML='';
//									document.getElementById("people_id").innerHTML='';
//									document.getElementById("people_name").innerHTML='';
//									document.getElementById("people_canleave").innerHTML='';
									document.getElementById("log_pic").src='../image/no28.png';
									document.getElementById("log_pic").style.display="inline";
									
									alert("无匹配数据，请重新拍照");
									
								}else{
									document.getElementById("people_identity").innerHTML=people.identity;
									document.getElementById("people_id").innerHTML=people.message.id;
									document.getElementById("people_name").innerHTML=people.message.name;
									document.getElementById("people_canleave").innerHTML=people.message.canleave;
									document.getElementById("log_pic").style.display="inline";
									document.getElementById("log_pic").src= 'data:image/png;base64,'+people.message.pic;
									
									delete people.message.pic;
									console.log(JSON.stringify(people));
									send(JSON.stringify(people));
								}


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
							result=xmlhttp.responseText;
							show.innerHTML+=("--result--"+result); 
							var people=JSON.parse(result);
							
							if("photo_error"==people.result){
								
								console.log(new Date(),people.result);
								alert("照片中无法识别人脸，请重新拍照");
								document.getElementById("new_pic").src="";
								
							}else if ("not_match"==people.result){
								
								
								alert("照片与身份证不匹配，请重试！");
								document.getElementById("new_pic").src="";
								document.getElementById('captureBtn').disabled= true ;
								
							}else if("noresult"==people.identity){
								
								document.getElementById("people_identity").innerHTML="无登记记录";
								document.getElementById("people_id").innerHTML=people.message.id;
								document.getElementById("people_name").innerHTML=people.message.name;
								document.getElementById("people_canleave").innerHTML='N';
								alert("请联系门卫进行登记注册");
//								document.getElementById("log_pic").style.display="inline";
//								document.getElementById("log_pic").src= 'data:image/png;base64,'+people.message.pic;
								//window.location.href="success2.jsp"; 
							}else if("parent"==people.identity){
								document.getElementById("people_identity").innerHTML=people.identity;
								document.getElementById("people_id").innerHTML=people.message.id;
								document.getElementById("people_name").innerHTML=people.message.name;
								document.getElementById("people_canleave").innerHTML=people.message.canleave;
								
							}else  if("blacklist"==people.identity){
								
								document.getElementById("people_identity").innerHTML=people.identity;
								document.getElementById("people_id").innerHTML=people.message.id;
								document.getElementById("people_name").innerHTML=people.message.name;
								document.getElementById("people_canleave").innerHTML='N';
								
							}else{
								
							}
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