var	bizSP = (typeof bizSP === 'undefined') ? {}	: console.log('......');

bizSP	= {
	init:function(){
		var	h;
		h =	this;
		h.common.init();
		h.lnb.init();
		h.placeholder.init();
		h.datePicker.init();
		h.menuTree.init();
		h.customScroll.init();
	}
};

bizSP.common	= {
	init:function(){
		var	common	= this;
		common.ui();
	},
	ui:function(){
		//placeholder
		$('[placeholder]').on({
		focus: function() {
			if( $(this).val() == $(this).attr('placeholder') ) {
				$(this).val('');
			}
		},
		blur: function(){
			if( !$(this).val() ) {
				$(this).val( $(this).attr('placeholder') );
			}
		}
		}).each( function() {
			$(this).val( $(this).attr('placeholder') ) ;
		});

	}
};

bizSP.placeholder	= {
	init:function(){
		var	placeholder	= this;
		placeholder.ui();
	},
	ui:function(){
	}
};

bizSP.lnb	= {
	init:function(){
		var	lnb	= this;
		lnb.ui();
	},
	ui:function(){
		
		$(document).on("click", '.leftMenu > li > a',function(e){
			if($(this).next().length > 0){
				var fields = $(this).next();
				if (fields.is(':visible')){
					fields.slideUp(400, 'easeOutQuad')
					$(this).removeClass('active');
					$(this).parent().removeClass('active');
				}else{
					e.preventDefault();
					$('.leftMenu ul').slideUp(400, 'easeOutQuad');
					fields.slideDown(400, 'easeOutQuad');
					$('.leftMenu > li > a').removeClass('active');
					$('.leftMenu li').removeClass('active');
					$(this).addClass('active');
					$(this).parent().addClass('active');
				}
			};
		})
	}
};
bizSP.datePicker	= {
	init:function(){
		var	datePicker	= this;
		datePicker.ui();
	},
	ui:function(){

		$( ".date-picker" ).datepicker({
				inline: true,
				showOtherMonths: true
		}).datepicker('widget').wrap('<div class="ll-skin-melon"/>');
	}
};
bizSP.menuTree	= {
	init:function(){
		var	menuTree = this;
		menuTree.ui();
	},
	ui:function(){
		  $("#browser").treeview({control:"#sidetreecontrol"});
		  /*$(".customSelect").selectbox();*/
	}
};
bizSP.customScroll	= {
	init:function(){
		var	customScroll	= this;
		customScroll.ui();
	},
	ui:function(){
	$('.scrollTable').css('overflow','auto');
	// IE version check
	var ua = window.navigator.userAgent;
	if(ua.indexOf('Trident/4.0') >= 0 || ua.indexOf('MSIE 7.0') >= 0){

	}else{
			$(window).load(function(){
				$(".scrollTable").mCustomScrollbar({
					axis:"x",
					theme:"dark"
				});

			});
			$(window).load(function(){

				$(".layerInner .long .innerBox, .layerInner .long .innerBox02").mCustomScrollbar({
						axis:"yx",
						theme:"dark"
				});
			});

			$(window).load(function(){

				$(".layerInner .long .innerBox03").mCustomScrollbar({
						axis:"y",
						theme:"dark"
				});
			});
	}
	}
};




$(function(){
	bizSP.init();
});


//모달 열기
var dim = 'dim';
function showModal(modalID, targetElementId, flag) {
	
	var popupID = "#" + modalID;
	var popupMarginTop = $(popupID).height() / 2;
	var popupMarginLeft = $(popupID).width() / 2;
	
	$(popupID).hide();
	
	$(popupID + " > input[name=targetId]").val(targetElementId);
	$(popupID + " > input[name=flag]").val(flag);

	$(popupID).css({
		'left': '50%',
		'z-index': 9997,
		'top': '50%',
		'margin-top': -popupMarginTop,
		'margin-left': -popupMarginLeft,
		'display': 'block',
		'position': 'fixed'
	});

	var backgroundSelector = $('<div id="' + dim + '" ></div>');

	backgroundSelector.appendTo('body');

	backgroundSelector.css({
		'width': '100%',
		'height': $(document).height(),
		'display': 'none',
		'background-color': '#000',
		'filter':'alpha(opacity=50)',
		'position': 'absolute',
		'top': 0,
		'left': 0,
		'z-index': 9996
	});

	backgroundSelector.fadeTo('fast', 0.8);
	backgroundSelector.click(function(){
		$("#" + modalID).fadeOut();
		$("#" + dim).remove();
	});

}


var dim02 = 'dim02';
function showModal02(modalID02) {
	var popupID = "#" + modalID02;
	var popupMarginTop = $(popupID).height() / 2;
	var popupMarginLeft = $(popupID).width() / 2;

	$(popupID).hide();

	$(popupID).css({
		'left': '50%',
		'z-index': 9999,
		'top': '50%',
		'margin-top': -popupMarginTop,
		'margin-left': -popupMarginLeft,
		'display': 'block',
		'position': 'fixed'
	});

	var backgroundSelector = $('<div id="' + dim02 + '" ></div>');

	backgroundSelector.appendTo('body');

	backgroundSelector.css({
		'width': '100%',
		'height': $(document).height(),
		'display': 'none',
		'background-color': '#000',
		'filter':'alpha(opacity=50)',
		'position': 'absolute',
		'top': 0,
		'left': 0,
		'z-index': 9998
	});

	backgroundSelector.fadeTo('fast', 0.3);
	backgroundSelector.click(function(){
		$("#" + modalID02).fadeOut();
		$("#" + dim02).remove();
	});

}
//모달 닫기
function hideModal02(modalID02) {
	$("#" + modalID02).fadeOut();
	$("#" + dim02).remove();
}

var dim03 = 'dim03';
function showModal03(modalID03) {
	var popupID = "#" + modalID03;
	var popupMarginTop = $(popupID).height() / 2;
	var popupMarginLeft = $(popupID).width() / 2;

	$(popupID).hide();

	$(popupID).css({
		'left': '50%',
		'z-index': 800,
		'top': '50%',
		'margin-top': -popupMarginTop,
		'margin-left': -popupMarginLeft,
		'display': 'block',
		'position': 'fixed'
	});

	var backgroundSelector = $('<div id="' + dim03 + '" ></div>');

	backgroundSelector.appendTo('body');

	backgroundSelector.css({
		'width': '100%',
		'height': $(document).height(),
		'display': 'none',
		'background-color': '#000',
		'filter':'alpha(opacity=50)',
		'position': 'absolute',
		'top': 0,
		'left': 0,
		'z-index': 300
	});

	backgroundSelector.fadeTo('fast', 0.3);
	backgroundSelector.click(function(){
		$("#" + modalID03).fadeOut();
		$("#" + dim03).remove();
	});

}
//모달 닫기
function hideModal03(modalID03) {
	$("#" + modalID03).fadeOut();
	$("#" + dim03).remove();
}


var DEPT_NAME_SEARCH = "/rest/deptApi/deptNameSearch/";
var USER_NAME_SEARCH = "/rest/userApi/userNameSearch/";


function autoCompleteSearch(searchInputId){
	$("#" + searchInputId).autocomplete({
		source : function(request, response) {
            $.ajax({
                url : DEPT_NAME_SEARCH + encodeURIComponent($("#" + searchInputId).val().replace(/\//, '`')),
                type : "GET",
                dataType : "json",
                success : function(data) {
                    var result = data;
                    response(result);
                },
                error : function(data) {
                	bootbox.alert("에러가 발생하였습니다.")
                }
            });
        }
    });
} 

//모달 닫기
function hideModal(modalID) {
	$("#" + modalID).fadeOut();
	$("#" + dim).remove();
}

// 마크업 어태치 & 페이징 처리
function setData(html, totalRow) {
	$("#pagenationSection").pagination({
		items: totalRow,
		itemsOnPage: 10,
		cssStyle: 'light-theme',
		hrefTextPrefix: "javascript:getList(",
		hrefTextSuffix: ");"
	});
	$("#totalRow").html(totalRow);
	$("#dataArea").html(html);
}

// YEAR 셀렉트박스 마크업 생성 및 어태치
function makeYear (elem) {
	var html = '<option value="">전체</option>';
	var year = new Date().getFullYear();
	for (var i = 2015; i <= year; i++) {
		if (year == i) {
			html += '<option value="' + i + '" selected="selected">' + i + '년</option>';
		} else {
			html += '<option value="' + i + '" >' + i + '년</option>';
		}
	}
	$("#"+elem).html(html);
}

// MONTH 셀렉트박스 마크업 생성 및 어태치
function makeMonth (elem) {
	var html = '<option value="">전체</option>';
	var month = new Date().getMonth();

	for (var i = 1; i <= 12; i++) {
		var selected = "";

		if ((month + 1) == i) {
			selected = 'selected="selected"';
		}

		if (i < 10) {
			html += '<option value="' + '0' + i + '" ' + selected + '>' + i + '월</option>';
		} else {
			html += '<option value="' + i + '" ' + selected + '>' + i + '월</option>';
		}
	}
	$("#"+elem).html(html);
}

// YEAR, MONTH 마크업 생성
function makeYearMonth(year, month, flag) {
	makeYear(year);
	makeMonth(month);
	if (flag == 'Y') {
		$(".customSelect").selectbox();
	}
}

// COM_CD_GRP_ID 조회 + 셀렉트박스 마크업 어태치
function getComCdGrpId(code, elem, flag, codeYn){
	$.ajax({
		type : 'GET',
		url : '/rest/codeApi/commCodeList/' + code + '/0',
		dataType : "json",
		success : function(data) {
			var html = '<option value="all">전체</option>';
			$.each(data.codeList, function(key, obj) {
				if(codeYn=='Y'){
					html += '<option value="' + obj.code + '">' + '(' + obj.code + ') ' + obj.codeName + '</option>';
				}else{
					html += '<option value="' + obj.code + '">'+ obj.codeName + '</option>';
				}
			});
			$("#"+elem).html(html);
			if (flag == 'Y') {
				$(".customSelect").selectbox();
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

// 정산서 팝업 열기
function openCalculateForm(val, seq, flag) {
	var popupMarginTop = $("#calculateForm").height() / 2;
	var popupMarginLeft = $("#calculateForm").width() / 2;

	$("#calculateForm").hide();

	$("#calculateForm").css({
		'left': '50%',
		'z-index': 2000,
		'top': '50%',
		'margin-top': -popupMarginTop,
		'margin-left': -popupMarginLeft,
		'display': 'block',
		'position': 'fixed'
	});

	getCalculateData(val, seq, flag);

	var backgroundSelector = $('<div id="' + dim02 + '" ></div>');

	backgroundSelector.appendTo('body');

	backgroundSelector.css({
		'width': '100%',
		'height': $(document).height(),
		'display': 'none',
		'background-color': '#000',
		'filter':'alpha(opacity=50)',
		'position': 'absolute',
		'top': 0,
		'left': 0,
		'z-index': 1000
	});

	backgroundSelector.fadeTo('fast', 0.8);
	backgroundSelector.click(function(){
		$("#calculateForm").fadeOut();
		$("#"+dim02).remove();
	});
}

// 정산서 팝업 닫기
function closeCalculateForm() {
	$("#calculateForm").fadeOut();
	$("#"+dim02).remove();
}

// 품의서 정보 조회
function getApprovalData(val) {
	var result = null;

	$.ajax({
		type : 'GET',
		url : '/rest/approvalApi/approvalData/' + val,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.code == 'S') {
				result = data.approvalVo;
			} else {
				bootbox.alert(data.msg, function () {
					closeCalculateForm();
				});
			}
		},
		error : function(e) {
			console.log(e);
		}
	});

	return result;
}

// 정산서 데이터 조회
function getCalculateData(val, seq, flag) {
	var obj = getApprovalData(val);

	$.ajax({
		type : 'GET',
		url : '/rest/approvalApi/calculateData/' + val + '/' + flag,
		dataType : "json",
		success : function(data) {
			if (data.code == 'S') {
				$("#lineArea").html(data.lineList);
				setToolTipEvent();
				$("#itemArea").html(data.itemList);

				// 헤더 스타일 초기화
				$("#titleArea tr th:nth-child(11)").removeAttr('style');
				$("#titleArea tr th:nth-child(12)").removeAttr('style');
				$("#titleArea tr th:nth-child(7)").removeAttr('style');
				$("#titleArea tr th:nth-child(16)").removeAttr('style');
				$("#titleArea tr th:nth-child(8)").removeAttr('style');
				$("#titleArea tr th:nth-child(9)").removeAttr('style');
				// 필드 스타일 초기화
				$("#itemArea tr td:nth-child(11)").removeAttr('style');
				$("#itemArea tr td:nth-child(12)").removeAttr('style');
				$("#itemArea tr td:nth-child(7)").removeAttr('style');
				$("#itemArea tr td:nth-child(16)").removeAttr('style');
				$("#itemArea tr td:nth-child(8)").removeAttr('style');
				$("#itemArea tr td:nth-child(9)").removeAttr('style');
				// 예산별도계정명 초기화
				$("#accountSp1Cd").hide();
				$("#accountSp2Cd").hide();

				if (obj.accountCd == '3' || obj.accountCd == '4' || obj.accountCd == '5') {
					// ACCOUNT_CD IN (1, 2) 만 결과보고서, 공정경쟁규약 노출
					$("#titleArea tr th:nth-child(11)").css('display', 'none');
					$("#titleArea tr th:nth-child(12)").css('display', 'none');
					$("#itemArea tr td:nth-child(11)").css('display', 'none');
					$("#itemArea tr td:nth-child(12)").css('display', 'none');
				}

				if (obj.accountCd == '1' || obj.accountCd == '2' || obj.accountCd == '5') {
					// ACCOUNT_CD IN (3, 4) 만 예산별도계정명 노출
					$("#titleArea tr th:nth-child(7)").css('display', 'none');
					$("#itemArea tr td:nth-child(7)").css('display', 'none');
				}

				if (obj.accountCd == '1' || obj.accountCd == '2' || obj.accountCd == '3' || obj.accountCd == '4') {
					// ACCOUNT_CD = '5' 만 회의록 노출
					$("#titleArea tr th:nth-child(16)").css('display', 'none');
					$("#itemArea tr td:nth-child(16)").css('display', 'none');
				}

				if (obj.accountCd == '5') {
					// ACCOUNT_CD IN (1, 2, 3, 4) 만 제품군/명, 거래처 노출
					$("#titleArea tr th:nth-child(8)").css('display', 'none');
					$("#itemArea tr td:nth-child(8)").css('display', 'none');
					$("#titleArea tr th:nth-child(9)").css('display', 'none');
					$("#itemArea tr td:nth-child(9)").css('display', 'none');
				}

				$("#approvalIdArea").html(obj.approvalId);
				$("#accountCdArea").html(obj.accountCd);
				$("#accountNmArea").html(obj.accountNm);
				$("#budgetDeptNmArea").html(obj.budgetDeptNm);

				if (flag == 'approval') {
					if (data.btnFlag == 'Y') {
						$("#btn03").show();
						$("#btn04").show();
						if (data.accountSpCdFlag == 'Y') {
							if (data.accountCd == '3') {
								$("#accountSp1Cd").show();
							} else if (data.accountCd == '4') {
								$("#accountSp2Cd").show();
							}
							$("#btn06").val(val + '/' + seq + '/' + data.accountCd);
						} else {
							$("#btn06").val(val + '/' + seq + '/' + '0');
						}
						$("#btn07").val(val + '/' + seq);
					}
					if (data.firstFlag == 'Y') {
						$("#itemArea tr td:nth-child(6)").each(function (idx) {
							$(this).append("&nbsp;<a href='#' class='btnView'><i class='icon iconView' name='account2Nm'>돋보기</i></a>");
						});
					}
					if (data.adminFlag == 'Y') {
						$("#btn04").show();
						$("#btn07").val(val + '/' + seq);
					}
				} else if (flag == 'approvalComplete') {
					if (data.btnFlag == 'Y') {
						$("#btn05").show();
						$("#btn05").val(val);
					}					
				} else if (flag == 'progress') {
					if (data.btnFlag == 'Y') {
						$("#btn01").val(val);
						$("#btn01").show();
					}
				} else if (flag == 'restore') {
					if (data.btnFlag == 'Y') {
						$("#btn02").val(val);
						$("#btn02").show();
					}
				} else if (flag == 'report') {
					// 사후 처리 용도
				}
			} else {
				bootbox.alert(data.msg, function () {
					closeCalculateForm();
				});
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

// 결재의견 툴팁 처리
function setToolTipEvent() {
	$('.toolTip > .toolTipBtns').hover(function() {
		$(this).parent().find('.toolTipTxt').stop().fadeIn('fast');
	}, function() {
		$(this).parent().find('.toolTipTxt').stop().hide();
	});
}

function numberPad (str, max) {
	str = str.toString();
	return str.length < max ? numberPad("0" + str, max) : str;
}

function dateFormat(date){
	if(date==null) return '';
	var year 	= date.substr(0, 4);
	var month 	= date.substr(4, 2);
	var day		= date.substr(6, 2);
	
	return year + "-" + month + "-" + day;
}

function timeFormat(time){
	if(time==null) return '';
	var hh 	= time.substr(0, 2);
	var mm 	= time.substr(2, 2);
	var ss	= time.substr(4, 2);
	
	return hh + ":" + mm + ":" + ss;
}

//////숫자 콤마 찍어주는 함수
function setDecimalFormat(str){
	if(str==null) return '';
	str +="";
	var size = str.length;
	var cal = size % 3;
	if(size>3){
		if(cal != 0){
			return str.substring(0, cal) + "," + setDecimalFormat(str.substring(cal));
		}else{
			return str.substring(0, 3) + "," + setDecimalFormat(str.substring(3));
		}
	}else{
		return str;
	}
}

function checkNull(str){
	if(str==null) return '';
	return str;
}

//심포지움 일때 일괄 적용
function setAllFtrCode(val, type){
	if(type == "ftrCode"){
		$("#approvalItemList").find("tr").each(function(){
			$(this).find("input[name=ruleDocumentInfo]").val(val);
			$(this).attr("ftrCode", val)
		})
	} else if(type == "account2Info"){
		$("#approvalItemList").find("tr").each(function(){
			var arrayVal = val.split("|");
			$(this).find("input[name=account2Info]").val(arrayVal[1]);
			$(this).attr("account2Code", arrayVal[0]);
			$(this).attr("account2Name", arrayVal[1]);
		})
	}
}

var console = console || {
    log:function(){},
    warn:function(){},
    error:function(){}
};
