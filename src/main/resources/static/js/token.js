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
		$("body").addClass("screenOut");
	})
	
	$(".menuArea .btnClose .slideClose").on("click", function(e) {
		e.preventDefault();
		$("body").removeClass("screenOut")
		$(".menuArea").removeClass("on");
	})
	
	$(".btnTop").on("click", function(e) {
		e.preventDefault();
		$('html, body').animate({scrollTop: '0'}, 1000);
	});
	
	toastPop = function(tsMsg,tsNm,tsUrl,tsOpt,tsTg) {
        var msg = tsMsg ? tsMsg : "메시지를 입력해주세요", 
            nm = tsNm ? tsNm : "", 
            url = tsUrl ? tsUrl : "javascript:;", 
            opt = tsOpt ? tsOpt : "", //화살표 right or top or check
            tg = tsTg ? tsTg : "_self", 
            html = '<div class="popToast"><a href="'+url+'" rel="opener" target="'+tg+'" class="toastText '+opt+'"><span class="left">'+msg+'</span><span class="right">'+nm+'</span></a></div>';
        $('body').append(html);
        setTimeout(function(){
            $('body').find('.popToast').remove();
        }, 2600);
    }
	
	$(".tokenItem").on("click", function(e) {
		e.preventDefault();
		var tokenId = $(this).data("token-id");
		$(".tokenDetailPopArea").addClass("on");
		$("body").addClass("screenOut")
		$.ajax({
			url : contextPath + "/token/" + tokenId + ".inc",
			type: "GET",
			dataType: "html",
			success: function(html) {
				$(".tokenDetailPopArea .tokenDetailWrap").html(html);
			}, 
			error: function() {
			}
		});
	});
	
	$(".tokenDetailPopArea .tokenDetailWrap").on("click", ".btnClose .slideClose", function(e)  {
		e.preventDefault();
		$("body").removeClass("screenOut")
		$(".tokenDetailPopArea").removeClass("on");
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
	
	var attributes = new Array();
	// 특성
	$("input[name=attribute]:checked").each(function() {
		var attribute = $(this).data('attribute');	
		attributes.push(attribute);
	});
	
	$(".item.filter.color").html("");
	$.ajax({
		url : contextPath + "/token/colorList",
		type: "GET",
		dataType: "html",
		data: {  type : type, gradeList: gradeList, keyword: keyword, attributes: attributes},
		success: function(html) {
			$(".item.filter.color").html(html);
			$(".item.filter.color").find("input[type=checkbox]").each(function() {
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

// 지갑 로그인
function walletLogin(walletAddress) {
	console.log(walletAddress);
	var $signUpForm = $("#signUpForm");		
	$signUpForm.attr("action", "/walletLogin");
	$signUpForm.html("<input type='hidden' name='walletAddress' value='" + walletAddress + "'/>");
	$signUpForm.submit();
}

function walletConnect(walletAddress) {
	$.ajax({
		url : contextPath + '/users/connectKaikas',
		type : "POST",
		data : {
			walletAddress: walletAddress
		},
		success : function(data) {
			toastPop('지갑이 연결되었습니다.')
			setTimeout(() => { window.location.reload(true) }, 500);
		}, error : function(e) {
			console.log(e.responseText)
			if (e.responseText != null && e.responseText != '') {
				toastPop(e.responseText);
			} else {
				toastPop('지갑 연결 중 오류가 발생했습니다.');
			}
		}
	})
}