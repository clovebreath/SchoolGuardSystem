$(function() {
    $("#menu nav ul li").click(function() {
        $(this).siblings().removeClass("current");
        $(this).addClass("current");
    })
});
var show_realtime_info = function() {
    document.getElementById("data_table").style = "display: none";
    document.getElementById("info_div").style = "display: block";
};
var show_all_data = function() {
    document.getElementById("data_table").style = "display: block";
    document.getElementById("info_div").style = "display: none";
};
var show_black_record = function() {
    document.getElementById("data_table").style = "display: block";
    document.getElementById("info_div").style = "display: none";
};
var show_unreserved_record = function() {
    document.getElementById("data_table").style = "display: block";
    document.getElementById("info_div").style = "display: none";
};
var show_settings = function() {
    document.getElementById("data_table").style = "display: none";
    document.getElementById("info_div").style = "display: none";
}
var add_data = function(tbid, name, id_num, name_stu, cls, teacher, t_contact, cap_img, status) {
    var t_body = document.getElementById(tbid);
    if (t_body === null) {
        alert("TABLE DOES NOT EXIST");
        return;
    }
    var tbody_str = t_body.innerHTML;
    var row_count = t_body.rows.length;
    var new_row = t_body.insertRow(row_count++);
    var img_id = 'show_img_' + row_count;
    new_row.innerHTML = "<td>" + row_count + "</td>" + "<td class='colsnotindex'>" + name + "</td>" + "<td class='colsnotindex'>" + id_num + "</td>" + "<td class='colsnotindex'>" + name_stu + "</td>" + "<td class='colsnotindex'>" + cls + "</td>" + "<td class='colsnotindex'>" + teacher + "</td>" + "<td class='colsnotindex'>" + t_contact + "</td>" + "<td class='colsnotindex' colspan='3'>" + "<img width='160' height='120' id=" + img_id + ">" + "</td>" + "<td class='colsnotindex'>" + status + "</td>";
    document.all[img_id].src = 'data:image/jpeg;base64,' + cap_img;
};
