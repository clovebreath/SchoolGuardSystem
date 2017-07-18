$("#cap_main").height($("#cap_main").width() * 0.75);
$("#guard_right").height($("#cap_main").height());
$(".small_pic").height($(".small_pic").width() * 1.2);
$("#cap_pic").height($(".small_pic").height());
$("#cap_pic").width($("#cap_pic").height() * 4 / 3);
$("#btnarea").height($("#guard_right").height() * 0.871 - $("#message").height() - $("#status_tips").height());
$(".guard_btn").height($("#btnarea").height());
$(window).resize(function() {
	$("#cap_main").height($("#cap_main").width() * 0.75);
	$("#guard_right").height($("#cap_main").height());
	$(".small_pic").height($(".small_pic").width() * 1.2);
	$("#cap_pic").height($(".small_pic").height());
	$("#cap_pic").width($("#cap_pic").height() * 4 / 3);
	$("#btnarea").height($("#guard_right").height() * 0.871 - $("#message").height() - $("#status_tips").height());
	$(".guard_btn").height($("#btnarea").height());
});
