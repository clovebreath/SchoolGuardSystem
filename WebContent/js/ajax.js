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
							  	changeButton('capture',false);
								result=xmlhttp.responseText;
								var people=JSON.parse(result);
								
								if ("photo_error" == people.result) {								
									document.getElementById("staff-tips-message").innerHTML="照片中无法识别人脸，请重新拍照";
									document.getElementById("new_pic").src="";

								}else if ("not_match"==people.result){							
									document.getElementById("staff-tips-message").innerHTML="无匹配数据，请重新拍照";
									
								}else{
									document.getElementById("people_identity").innerHTML=people.details.identity;
									document.getElementById("people_id").innerHTML=people.details.id;
									document.getElementById("people_name").innerHTML=people.details.name;
									document.getElementById("log_pic").style.display="inline";
									document.getElementById("log_pic").src= 'data:image/png;base64,'+people.details.imglog;
									if(people.result=="allowed"){
										console.log(people.result);
										console.log('1');
										document.getElementById("people_canleave").innerHTML="允许通过";
										document.getElementById("staff-tips-message").innerHTML="验证通过，可以出入。";										
									}else{
										console.log('2');
										console.log(people.result);
										document.getElementById("people_canleave").innerHTML="不许通过";
										document.getElementById("staff-tips-message").innerHTML="对不起，您没有请假，无法离开学校。";										
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
							result=xmlhttp.responseText;
							var people=JSON.parse(result);
							
							if("photo_error"==people.result){
								
								console.log(new Date(),people.result);
								document.getElementById("staff-tips-message").innerHTML="照片中无法识别人脸，请重新拍照";
								
							}else if ("not_match"==people.result){

								document.getElementById("staff-tips-message").innerHTML="照片与身份证不匹配，请重试！";
								document.getElementById('capture').disabled= false ;
								
							}else if("noresult"==people.identity){
								
								document.getElementById("people_identity").innerHTML="无登记记录";
								document.getElementById("people_id").innerHTML=people.message.id;
								document.getElementById("people_name").innerHTML=people.message.name;
								document.getElementById("people_canleave").innerHTML='N';
								document.getElementById("staff-tips-message").innerHTML="请联系门卫进行登记注册";
								document.getElementById('capture').disabled= true ;

							}else if("parent"==people.details.identity){
								document.getElementById("people_identity").innerHTML=people.details.identity;
								document.getElementById("people_id").innerHTML=people.details.id;
								document.getElementById("people_name").innerHTML=people.details.name;
								document.getElementById("people_canleave").innerHTML=people.result;
								document.getElementById('capture').disabled= true ;
								document.getElementById("vi_log_pic").src='data:image/jpeg;base64,'+people.details.imglog;
								if(people.result=="allowed"){
									
									document.getElementById("people_canleave").innerHTML="允许通过";
									document.getElementById("staff-tips-message").innerHTML="验证通过，可以出入。";	
								}else{
									document.getElementById("people_canleave").innerHTML="不许通过";
									document.getElementById("staff-tips-message").innerHTML="对不起，您没有预约，无法进入学校。";		
								}
								delete people.details.imgnow;
								delete people.details.imglog;
								delete people.details.imgstu;
								
								console.log(JSON.stringify(people));
								send(JSON.stringify(people));
								
							}else  if("blacklist"==people.details.identity){
								document.getElementById("vi_log_pic").src='data:image/jpeg;base64,'+people.details.imglog;
								document.getElementById("people_identity").innerHTML=people.details.identity;
								document.getElementById("people_id").innerHTML=people.details.id;
								document.getElementById("people_name").innerHTML=people.details.name;
								document.getElementById('capture').disabled= true ;

									document.getElementById("people_canleave").innerHTML="不许通过";
									document.getElementById("staff-tips-message").innerHTML="对不起，您身份有问题，无法进入学校。";	
								delete people.details.imgnow;
								delete people.details.imglog;
								console.log(JSON.stringify(people));
								send(JSON.stringify(people));
								
							}else{
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
						  document.getElementById("first").onclick = function() {
							  getRecord(1);
						  }
						  document.getElementById("last").onclick = function() {
							  getRecord(record.pagecount);
						  }
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
						  document.getElementById("first").onclick = function() {
							  getBlackRecord(1);
						  }
						  document.getElementById("last").onclick = function() {
							  getBlackRecord(record.pagecount);
						  }
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
				var getParentRecord=function(currPage){
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
						  document.getElementById("first").onclick = function() {
							  getParentRecord(1);
						  }
						  document.getElementById("last").onclick = function() {
							  getParentRecord(record.pagecount);
						  }
						  currentPage=record.currpage;
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					//将图片数据传递给getPhotoAndCard
					xmlhttp.open("POST","../getParentRecord",true);
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
						  document.getElementById("result").innerHTML='允许通过';
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					xmlhttp.open("POST","../setNotAllowedParent",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);
			    }
			    
				
				var changeCanLeave=function(id,status){
					var data="id="+id+"&status="+status;
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
						  alert("操作成功！");
						  window.location.reload(true);
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					xmlhttp.open("POST","../changeCanLeave",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);
				}
				var changeOrderStatus=function(id,order){
					var data="id="+id+"&order="+order;
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
						  alert("操作成功！");
						  window.location.reload(true);
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					xmlhttp.open("POST","../setOrderStatus",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);
				}
				var makeBlacklist=function(id){
					var data="id="+id;
					console.log("ajax",data);
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
						  alert("拉黑成功！");
						  window.location.reload(true);
					    }else{
					    	console.log("response","error"+xmlhttp.readyState+ xmlhttp.status);
					    }
					  } 
					xmlhttp.open("POST","../makeBlacklist",true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send(data);
				}	