var dataFromCamera = "";
var szParam = "";
var is_cmr = false;
var newphoto="";
var loadParam = function(width, height, rotate, flip, capidx, audio, step, time, eye, mouth, headup, headdown, headleft, headright, pupilmin, pupilmax, clarity) {
    szParam = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<param>\n    <device>\n        <width>" + width + "</width>\n        <height>" + height + "</height>\n        <rotate>" + rotate + "</rotate>\n        <flip>" + flip + "</flip>\n        <capidx>" + capidx + "</capidx>\n        <audio>" + audio + "</audio>\n        <step>" + step + "</step>\n        <time>" + time + "</time>\n    </device>\n    <image>\n        <eye>" + eye + "</eye>\n        <mouth>" + mouth + "</mouth>\n        <headup>" + headup + "</headup>\n        <headdown>" + headdown + "</headdown>\n        <headleft>" + headleft + "</headleft>\n        <headright>" + headright + "</headright>\n        <pupilmin>" + pupilmin + "</pupilmin>\n        <pupilmax>" + pupilmax + "</pupilmax>\n        <clarity>" + clarity + "</clarity>\n    </image>\n</param>";
    document.getElementById('parm').value = szParam;
};
window.onload = function() {
    document.getElementById('data').value = "";
    document.getElementById('stat').value = "";
    document.getElementById('parm').value = "";
    document.getElementById('id_data').value = "";
    document.getElementById('json_data').value = "";
    loadParam(640, 480, 0, 0, 0, 1, 3000, 15, 0, 0, 18, 18, 18, 18, 1, 256, 15);
};

var openDevice = function() {
    document.getElementById("data").value = "";
    var szParam = document.getElementById("parm").value;
    stdfcectl.InitParam(szParam);
    stdfcectl.OpenCapture();
};
var capture = function() {
    document.getElementById("data").value = "";
    stdfcectl.GetFace();
};
var showImage = function() {
    var idata = document.getElementById("data").value;
    stdfcectl.ShowPicture(idata);
};
var closeDevice = function() {
    stdfcectl.CloseCapture();
};
var getImage = function(dwResult) {
    if (0 == dwResult) {
        var s = document.getElementById('stat').value;
        s += "succeed\n";
        document.getElementById('stat').value = s;
        var str = stdfcectl.GetImageData();
        document.all['cap_photo'].src = 'data:image/jpeg;base64,' + str;
        document.getElementById('data').value = str;
        dataFromCamera = str;
        is_cmr = true;
        to_json();

        setTimeout("showImage()", 3000);
    } else {
        if (-15 == dwResult) {
            alert("DISCONNECTED");
        }
        var s = document.getElementById('stat').value;
        s = s + "\n" + 'failed:(' + dwResult + ')';
        document.getElementById('stat').value = s;
    }
};

