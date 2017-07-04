var all_data = {
	"id": "",
	"id_img": "",
	"cap_img": ""
};

function getXmlhttp() {
	var xmlhttp;
	if(window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	}
	else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
};

var query_str = function() {
	var id = rdcard.CardNo;
	var id_img = all_data["id_img"];
	var cap_img = all_data["cap_img"];
	var str = "id=" + id + "&id_img=" + id_img + "&cap_img=" + cap_img;
	return str;
};

var to_json = function() {
    if (is_rd && is_cmr) {
        all_data["id"] = rdcard.CardNo;
        all_data["id_img"] = rdcard.JPGBuffer;
        all_data["cap_img"] = dataFromCamera;
        var str_json = JSON.stringify(all_data);
        document.getElementById("json_data").value = str_json;
        is_cmr = false;
        is_rd = false;
        
        var xmlhttp = getXmlhttp();
        var url = "./AjaxServerlet?" + "time_stamp=" + new Date().getTime();
        var query_string = query_str();
        xmlhttp.open("POST", url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send(query_string);
    }
};
