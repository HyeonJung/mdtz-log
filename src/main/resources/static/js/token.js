//3자리 수 콤마.
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

/******************************************************************************
 * 토큰 관련.
 ******************************************************************************/
function getTokenInfoList(page) {
	var $tokenSearchForm = $("#tokenSearchForm");
	var rows = $tokenSearchForm.find("input[name=rows]").val();
	var type = $tokenSearchForm.find("input[name=type]").val();
	
	$.ajax({
		url : contextPath + "/token/subList",
		type: "GET",
		dataType: "html",
		data: { rows : rows, page : page, type : type},
		success: function(html) {

		}, 
		error: function() {
		}
	});
}