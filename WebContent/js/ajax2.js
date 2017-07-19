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
							  	changeButton("cap_btn",false);
							  	changeButton("exit_btn",false);
								result=xmlhttp.responseText;
								var people=JSON.parse(result);
								
								if ("photo_error" == people.result) {								
									document.getElementById("status_tips_message").innerHTML="照片中无法识别人脸，请重新拍照";
									document.getElementById("cap_pic").src="";

								}else if ("not_match"==people.result){							
									document.getElementById("status_tips_message").innerHTML="无匹配数据，请重新拍照";
									
								}else{
									document.getElementById("people_identity").innerHTML=people.details.identity;
									document.getElementById("people_id").innerHTML=people.details.id;
									document.getElementById("people_name").innerHTML=people.details.name;
									document.getElementById("people_teacher").innerHTML=people.details.teacherName;
									document.getElementById("log_pic").src= 'data:image/png;base64,'+people.details.imglog;
									console.log(document.getElementById("teacher_index").style.display);
									if(people.details.identity!="student"){
										document.getElementById("teacher_index").style.display="none";
									}else{
										document.getElementById("teacher_index").style.display="";
									}
									
									if(people.result=="allowed"){
										document.getElementById("people_leavestat").innerHTML="已请假";
										console.log(people.result);
										document.getElementById("people_canleave").innerHTML="允许通过";
										document.getElementById("status_tips_message").innerHTML="验证通过，可以出入。";										
									}else{
										document.getElementById("people_leavestat").innerHTML="未请假";
										console.log(people.result);
										document.getElementById("people_canleave").innerHTML="不许通过";
										document.getElementById("status_tips_message").innerHTML="对不起，您没有请假，无法离开学校。";										
									}

								
									delete people.details.imglog;
									delete people.details.imgnow;
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
						  	changeButton("cap_btn",false);
						  	changeButton("exit_btn",false);
						  	result=xmlhttp.responseText;
							var people=JSON.parse(result);
							
							if("photo_error"==people.result){
								
								console.log(new Date(),people.result);
								document.getElementById("status_tips_message").innerHTML="照片中无法识别人脸，请重新拍照";
								
							}else if ("not_match"==people.result){

								document.getElementById("status_tips_message").innerHTML="照片与身份证不匹配，请重试！";
								
							}else if("noresult"==people.identity){
								
								document.getElementById("people_identity").innerHTML="无登记记录";
								document.getElementById("people_id").innerHTML=people.message.id;
								document.getElementById("people_name").innerHTML=people.message.name;
								document.getElementById("people_canleave").innerHTML='不许通过';
								document.getElementById("status_tips_message").innerHTML="请联系门卫进行登记注册";
								changeButton("cap_btn",true);
							}else if("parent"==people.details.identity){
								document.getElementById("people_identity").innerHTML=people.details.identity;
								document.getElementById("people_id").innerHTML=people.details.id;
								document.getElementById("people_name").innerHTML=people.details.name;
								document.getElementById("people_canleave").innerHTML=people.result;
								document.getElementById("people_teacher").innerHTML=people.details.teacherName;
								document.getElementById("people_stu_name").innerHTML=people.details.stuName;
								changeButton("cap_btn",true);
								document.getElementById("log_pic").src='data:image/jpeg;base64,'+people.details.imglog;
								if(people.result=="allowed"){
									
									document.getElementById("people_canleave").innerHTML="允许通过";
									document.getElementById("status_tips_message").innerHTML="验证通过，可以出入。";	
								}else{
									document.getElementById("people_canleave").innerHTML="不许通过";
									document.getElementById("status_tips_message").innerHTML="对不起，您没有预约，请等待门卫通知老师。";		
								}
								delete people.details.imgnow;
								delete people.details.imglog;
								delete people.details.imgstu;
								
								console.log(JSON.stringify(people));
								send(JSON.stringify(people));
								
							}else  if("blacklist"==people.details.identity){
								document.getElementById("log_pic").src='data:image/jpeg;base64,'+people.details.imglog;
								document.getElementById("people_identity").innerHTML=people.details.identity;
								document.getElementById("people_id").innerHTML=people.details.id;
								document.getElementById("people_name").innerHTML=people.details.name;
								changeButton("cap_btn",true);

									document.getElementById("people_canleave").innerHTML="不许通过";
									document.getElementById("status_tips_message").innerHTML="对不起，您身份有问题，无法进入学校。";	
								delete people.details.imgnow;
								delete people.details.imglog;
								console.log(JSON.stringify(people));
								send(JSON.stringify(people));
								
							}else{
								changeButton("cap_btn",true);
								document.getElementById("people_identity").innerHTML="somethingwrong";
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
				var getPicture=function(id){
					var data="id="+id;
					console.log(data);
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
						  console.log("response",xmlhttp.responseText);
						  var pictures=JSON.parse(result);
						  document.getElementById("db_photo").src='data:image/jpeg;base64,'+pictures.logPic;
						  document.getElementById("cap_photo").src='data:image/jpeg;base64,'+pictures.newPic;
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","../getPicture",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);
				}	
			    
				
				var getRecord=function(currPage){
					var data="currpage="+currPage;
					console.log(data);
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
						  var result=xmlhttp.responseText;
						  console.log("response",xmlhttp.responseText);
						  var record=JSON.parse(result);
						  document.getElementById("table_body").innerHTML="";
						  record.records.forEach(setRecordTable, this);
						  document.getElementById("page_info").innerHTML="本页为第 "+record.currpage +" 页，一共 "+record.pagecount +" 页。";
						  
						  currentPage=record.currpage;
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","../getRecord",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);	
				}

				var getBlackRecord=function(currPage){
					var data="currpage="+currPage;
					console.log(data);
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
						  var result=xmlhttp.responseText;
						  console.log("response",xmlhttp.responseText);
						  var record=JSON.parse(result);
						  document.getElementById("table_body").innerHTML="";
						  record.records.forEach(setRecordTable, this);
						  document.getElementById("page_info").innerHTML="本页为第 "+record.currpage +" 页，一共 "+record.pagecount +" 页。";
						  currentPage=record.currpage;
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","../getBlackRecord",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);	
				}
				
				var setRecordTable=function(currentValue){//tbid, id, name,identity, name_stu, teacher, status, date
					add_data('table_body',currentValue.id,currentValue.name,currentValue.identity, currentValue.sname, currentValue.wname,currentValue.status,currentValue.time)
				}
				
				
				var setNotAllowedParent=function(id){
					var data="id="+id;
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
						  var result=xmlhttp.responseText;
						  console.log("response",xmlhttp.responseText);
						  document.getElementById("people_canleave_school").innerHTML='允许通过';
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					xmlhttp.open("POST","../setNotAllowedParent",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);
			    }
			    
				
				