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
		$('.leftMenu > li > a').click(function(e){
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
		});	
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
		var	menuTree	= this;
		menuTree.ui();
	},
	ui:function(){
		  $("#browser").treeview({control:"#sidetreecontrol"});$(".customSelect").selectbox();
	}
};
bizSP.customScroll	= {
	init:function(){
		var	customScroll	= this;
		customScroll.ui();
	},
	ui:function(){
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
};
$(function(){
	bizSP.init();
});


//모달 열기
var dim = 'dim';
function showModal(modalID) {
	var popupID = "#" + modalID;
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
		'z-index': 9990
	});

	backgroundSelector.fadeTo('fast', 0.8);
	backgroundSelector.click(function(){
		$("#" + modalID).fadeOut();
		$("#" + dim).remove();
	});

}
//모달 닫기
function hideModal(modalID) {
	$("#" + modalID).fadeOut();
	$("#" + dim).remove();
}