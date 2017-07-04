var is_rd = false;
var rd_id = "";
var rd_img = "";

var open_rdcard = function() {
	var result = rdcard.openport();
	if(result === 0) {
		document.getElementById('stat').value += 'open succeed\n';
	}
	else {
		document.getElementById('stat').value += 'open failed: ' + result + '\n';
	}
};
var close_rdcard = function() {
	var result = rdcard.closeport();
	document.getElementById('id_data').value = "";
	if(result === 0) {
		document.getElementById('stat').value += 'close succeed\n';
	}
	else {
		document.getElementById('stat').value += 'close failed: ' + result + '\n';
	}
};
var read_rdcard = function() {
	var result = rdcard.ReadCard2();
	document.getElementById('id_data').value = "";
	if(result === 0) {
		document.getElementById('stat').value += 'read succeed\n';
	}
	else {
		document.getElementById('stat').value += 'read failed: ' + result + '\n';
	}
};
var end_rdcard = function() {
	var result = rdcard.endread();
	if(result === 0) {
		document.getElementById('stat').value += 'end read succeed\n';
	}
	else {
		document.getElementById('stat').value += 'end read failed: ' + result + '\n';
	}
};
var show_id_data = function() {
	document.getElementById('id_data').value = 'Name: ' + rdcard.NameS + '\nSex: ' + rdcard.Sex + '\nFolk: ' + rdcard.Nation + '\nBirth: ' + rdcard.Born + '\nID: ' + rdcard.CardNo + '\nLast: ' + rdcard.Activity + '\nJPG: ' + rdcard.JPGBuffer;
	document.all['id_photo'].src = 'data:image/jpeg;base64,' + rdcard.JPGBuffer;
	rd_id = rdcard.CardNo;
	rd_img = rdcard.JPGBuffer;
	is_rd = true;
	to_json();
};