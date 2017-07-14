var currentPage=1;//for all record
$(function() {
    $("#menu nav ul li").click(function() {
        $(this).siblings().removeClass("current");
        $(this).addClass("current");
    })
});
var screen_width = $(window).width();
$(window).resize(function() {
    if($(window).width() <= screen_width * 0.55) {
        $("#photos").width(206);
        $("#info_container").width(408);
    }
    else {
        $("#photos").width($("#info_div").width() * 0.3);
        $("#info_container").width($("#info_div").width() * 0.55);
    }
    $("#cap_photo").height($("#cap_photo").width() * 0.75);

})
var show_realtime_info = function() {
    document.getElementById("data_table").style.display = "none";
    document.getElementById("info_div").style.display="block";
};
var show_all_data = function() {
	currentPage=1;
	$("#previous").removeAttr("onclick");
	$("#next").removeAttr("onclick");
	$("#previous").attr("onclick","getRecord(parseInt(currentPage)-1);");
	$("#next").attr("onclick","getRecord(parseInt(currentPage)+1);");
    document.getElementById("data_table").style.display="block";
    document.getElementById("info_div").style.display = "none";
    document.getElementById("previous").on
    getRecord(currentPage);
};
var show_black_record = function() {
	currentPage=1;
	$("#previous").removeAttr("onclick");
	$("#next").removeAttr("onclick");
	$("#previous").attr("onclick","getBlackRecord(parseInt(currentPage)-1);");
	$("#next").attr("onclick","getBlackRecord(parseInt(currentPage)+1);");
    document.getElementById("data_table").style.display="block";
    document.getElementById("info_div").style.display = "none";
    getBlackRecord(currentPage);
};
var show_unreserved_record = function() {
    document.getElementById("data_table").style.display = "block";
    document.getElementById("info_div").style.display = "none";
};
var show_settings = function() {
    document.getElementById("data_table").style = "display: none";
    document.getElementById("info_div").style = "display: none";
}
var add_data = function(tbid, id, name,identity, name_stu, teacher, status, date) {
    var t_body = document.getElementById(tbid);
    switch (identity){
    case "blacklist":
    	identity="黑名单";
    	break;
    case "student":
    	identity="学生";
    	break;
    case "parent":
    	identity="家长";
    	break;
    default:
    	identity="教职工";
    	break;
    }
    if (t_body === null) {
        alert("TABLE DOES NOT EXIST");
        return;
    }
    if(name_stu==undefined){
    	name_stu="---";
    }
    if(teacher==undefined){
    	teacher="---";
    }
    if(status=="notallowed"){
    	status="拒绝通过";
    }else{
    	status="允许通过";
    }
    var tbody_str = t_body.innerHTML;
    var row_count = t_body.rows.length;
    var new_row = t_body.insertRow(row_count++);
    var img_id = 'show_img_' + row_count;
    new_row.innerHTML = "<td>" + id + "</td>" + "<td class='colsnotindex'>" + name + "</td>" + "<td class='colsnotindex'>" + identity + "</td>" + "<td class='colsnotindex'>" + name_stu + "</td>" + "<td class='colsnotindex'>" + teacher + "</td>" + "<td class='colsnotindex'>" + status + "</td>" + "<td class='colsnotindex'>" + date + "</td>";
};
