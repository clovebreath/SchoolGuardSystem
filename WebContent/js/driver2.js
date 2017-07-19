var dataFromCamera = "";
var cap_base64="";
var szParam ="";
var is_cmr = false;
var newphoto="";
var show;
var loadParam = function(width, height, rotate, flip, capidx, audio, step, time, eye, mouth, headup, headdown, headleft, headright, pupilmin, pupilmax, clarity) {
    szParam = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<param>\n    <device>\n        <width>" + width + "</width>\n        <height>" + height + "</height>\n        <rotate>" + rotate + "</rotate>\n        <flip>" + flip + "</flip>\n        <capidx>" + capidx + "</capidx>\n        <audio>" + audio + "</audio>\n        <step>" + step + "</step>\n        <time>" + time + "</time>\n    </device>\n    <image>\n        <eye>" + eye + "</eye>\n        <mouth>" + mouth + "</mouth>\n        <headup>" + headup + "</headup>\n        <headdown>" + headdown + "</headdown>\n        <headleft>" + headleft + "</headleft>\n        <headright>" + headright + "</headright>\n        <pupilmin>" + pupilmin + "</pupilmin>\n        <pupilmax>" + pupilmax + "</pupilmax>\n        <clarity>" + clarity + "</clarity>\n    </image>\n</param>";
};

var openDevice = function() {
    stdfcectl.InitParam(szParam);
    stdfcectl.OpenCapture();
};
var capture = function(id) {
	dataFromCamera='';
	cap_base64='';
    stdfcectl.GetFace();
    document.getElementById(id).style = "background-color: #a0a0a0;";
};
var showImage = function() {
    stdfcectl.ShowPicture(dataFromCamera);
};
var closeDevice = function() {
    stdfcectl.CloseCapture();
};

var getImage = function(dwResult) {
    if (0 == dwResult) {
        cap_base64 = stdfcectl.GetImageData();
        dataFromCamera = 'data:image/jpeg;base64,'+cap_base64;
        is_cmr = true;
       //setTimeout("showImage()", 3000);
    } else {
        if (-15 == dwResult) {
            alert("DISCONNECTED");
        }
    }
};



//id_card模组
var is_rd = false;
var rd_id = "";
var rd_img = "";
var rd_name = "";
var rd_base64="";
var id_card_data="";
var rd_sex="";
var open_rdcard = function() {
	var result = rdcard.openport();
	if(result === 0) {
	}
	else {
		alert("身份证读取设备连接出错，请重试");
	}
};
var close_rdcard = function() {
	var result = rdcard.closeport();
	if(result === 0) {
	}
	else {
	}
};
var read_rdcard = function() {
	var result = rdcard.ReadCard2();
	if(result === 0) {
	}
	else {
	}
};
var end_rdcard = function() {
	var result = rdcard.endread();
	if(result === 0) {
	}
	else {
	}
};
var getMessage = function() {
	rd_id = rdcard.CardNo;
	rd_img = 'data:image/jpeg;base64,'+rdcard.JPGBuffer;
	rd_base64=rdcard.JPGBuffer
	rd_name=rdcard.NameS;
	rd_sex=rdcard.Sex;
	is_rd = true;
};

var get_data = function() {
	return "{\"identity\": \"society\",\"message\":{\"picture1\": \""+dataFromCamera+"\"+\"picture2\":\""+rd_img+"\",\"id\":\""+rd_id+"\",\"name\":\""+rd_name+"\"}}";
};


//button_control
var changeButton=function(){
	if(arguments.length==1){
		document.getElementById(arguments[0]).disabled=!document.getElementById(arguments[0]).disabled;		
	}else{
		document.getElementById(arguments[0]).disabled=arguments[1];			
	}
}
//log_image_control
var changeLogpic=function(){
	document.getElementById("log_pic").style = "background-color: #a0a0a0;";
	document.getElementById("cap_pic").style = "background-color: #a0a0a0;";
}

var changeState=function(){
	document.getElementById("status_tips_message").innerHTML="正在拍照，请直视摄像头......";
	if("staff"==arguments[0]){
		document.getElementById("people_identity").innerHTML="---";
		document.getElementById("people_id").innerHTML="---";
		document.getElementById("people_name").innerHTML="---";
		document.getElementById("people_canleave").innerHTML="---";
		document.getElementById("people_teacher").innerHTML="---";
		document.getElementById("people_leavestat").innerHTML = "---";
	}else{
		
	}
}
var defaultSet=function(){


	document.getElementById("log_pic").src = "";
	document.getElementById("idcard_pic").src = "";
	document.getElementById("cap_pic").src = "";
	document.getElementById("people_identity").innerHTML="---";
	document.getElementById("people_canleave").innerHTML="---";
	document.getElementById("people_stu_name").innerHTML="---";
	document.getElementById("people_teacher").innerHTML="---";
}