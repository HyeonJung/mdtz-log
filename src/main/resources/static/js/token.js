var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
// 3자리 수 콤마.
function comma(str) {
	if(!$.isNumeric(str))
		return 0;
	else {
		str = String(str);
		return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}
}

// 콤마제거.
function uncomma(str) {
	str = String(str);
	return str.replace(/[^\d]+/g, '');
}

$(function() {
	$(".subTopMenu").on("click", function(e) {
		e.preventDefault();
		$(".menuArea").addClass("on");
		$("body").addClass("screenOut")
	})
	
	$(".menuArea .btnClose .slideClose").on("click", function(e) {
		e.preventDefault();
		$("body").addClass("screenOut")
		$(".menuArea").removeClass("on");
	})
});

// 로딩
(function($) {
	
	$.ajaxSetup({
		beforeSend: function(xhr) {
			xhr.setRequestHeader("AJAX", "true");
		},
		error: function(xhr, status, err) {
			if (xhr.status == 403) {
				location.href = contextPath + "/login?redirect=true";
			}
			else {
				console.log("error: " + xhr.status);
			}
		}
	});
	
	$(document).ajaxStart(function() {
		$(".loadingWrap").show();
		
	}).ajaxStop(function() {
		$(".loadingWrap").hide();
	});

})(jQuery);

/******************************************************************************
 * 토큰 관련.
 ******************************************************************************/
function getColorList() {
	var $tokenSearchForm = $("#tokenSearchForm");
	var rows = $tokenSearchForm.find("input[name=rows]").val();
	var type = $tokenSearchForm.find("input[name=type]").val();
	var keyword = $("input[name=keyword]").val();
	$tokenSearchForm.find("input[name=keyword]").val(keyword);
	var gradeList = new Array();
	$("input[name=grade]:checked").each(function() {
		var grade = $(this).data('grade');	
		gradeList.push(grade);
	});
	
	var values = new Array();
	// 색
	$("input[name=color]:checked").each(function() {
		var color = $(this).data('color');	
		values.push(color);
	});
	
	// 특성
	$("input[name=attribute]:checked").each(function() {
		var attribute = $(this).data('attribute');	
		values.push(attribute);
	});
	
	$(".item.filter.color").html("");
	$.ajax({
		url : contextPath + "/token/colorList",
		type: "GET",
		dataType: "html",
		data: {  type : type, gradeList: gradeList, keyword: keyword},
		success: function(html) {
			$(".item.filter.color").html(html);
			$(".item.filter.color").find("input[type=radio]").each(function() {
				var color = $(this).data("color");
				if (values.indexOf(color) >= 0) {
					$(this).prop("checked", true);
				}
			});
		}, 
		error: function() {
		}
	});
	
}

function getAttributes() {
	var type = "MTDZ"
	$.ajax({
		url : contextPath + "/token/attributes",
		type: "GET",
		dataType: "html",
		data: {  type : type},
		success: function(html) {
			$(".attributeWrap").html(html);
		}, 
		error: function() {
		}
	});
}