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
