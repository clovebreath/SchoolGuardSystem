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
    show.innerHTML+="openDevice***";
};
var capture = function() {
    stdfcectl.GetFace();
    show.innerHTML+="capture***";   
};
var showImage = function() {
    stdfcectl.ShowPicture(dataFromCamera);
    show.innerHTML+="showImage***";    
};
var closeDevice = function() {
    stdfcectl.CloseCapture();
};

var getImage = function(dwResult) {
	show.innerHTML+="getImage***";    
    if (0 == dwResult) {
        cap_base64 = stdfcectl.GetImageData();
        dataFromCamera = 'data:image/jpeg;base64,'+cap_base64;
        show.innerHTML+=dataFromCamera;    
        is_cmr = true;
       //setTimeout("showImage()", 3000);
       show.innerHTML+="getImage success***";    
    } else {
    	show.innerHTML+="getImage fail error"+dwResult+"***";  
        if (-15 == dwResult) {
            alert("DISCONNECTED");
        }
    }
};





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
		show.innerHTML += 'open succeed\n';
	}
	else {
		show.innerHTML += 'open failed: ' + result + '\n';
	}
};
var close_rdcard = function() {
	var result = rdcard.closeport();
	if(result === 0) {
		show.innerHTML += 'close succeed\n';
	}
	else {
		show.innerHTML += 'close failed: ' + result + '\n';
	}
};
var read_rdcard = function() {
	var result = rdcard.ReadCard2();
	if(result === 0) {
		show.innerHTML += 'read succeed\n';
	}
	else {
		show.innerHTML += 'read failed: ' + result + '\n';
	}
};
var end_rdcard = function() {
	var result = rdcard.endread();
	if(result === 0) {
		show.innerHTML += 'end read succeed\n';
	}
	else {
		show.innerHTML += 'end read failed: ' + result + '\n';
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
