var $$ = { ajax : jQuery.ajax, submit : jQuery.submit,  click : jQuery.fn.click, that:this };
var mcpGlobal = {
    installCheckFlag : false
    , send : false
    , submitForm :  false
    , commChannel : getParameterByName("channel")
    , commIsLogin : getParameterByName("isLogin")
    , sessionTime : 10
};

(function($){
    $.extend({
        mcpAjax : function(obj){
            var defalut = {
                method		: "POST",		// method type
                url			: null,			// url
                data		: null,			// data
                dataType	: "json",   	// 데이터타입을 JSON형식으로 지정
                //processData : true,
                hasLoadBar  : true,
                async		: true,			// true
                timeout		: 302000,		// 5분(기간계 통신 고려하여 2초 더 추가)
                contentType	: mcpCommon.jsonContentType,
                beforeSend : function(xhr, opts){

                    if(mcpGlobal.send) {
                        alert("데이터를 요청 중입니다. 잠시 후에 다시 요청해주세요.");
                        xhr.abort();
                        return false;
                    }

                    mcpGlobal.send = true;

                    //console.log("headerName: " + E$.csrf.headerName);
                    //console.log("csrftoken: " + E$.csrf.token);

                    if(E$.csrf.headerName && E$.csrf.token) {
                        //console.log("csrftoken setting");
                        xhr.setRequestHeader(E$.csrf.headerName, E$.csrf.token);
                    }

                    var udid_name = $("meta[name='_udid_header']").attr("content");
                    var udid_value = $("meta[name='_udid']").attr("content");

                    if(udid_name && udid_value) {
                        xhr.setRequestHeader(udid_name,udid_value);
                    }

                    if(typeof obj.hasLoadBar  === "undefined" || obj.hasLoadBar === true) {
                        mcpCommon.showLoadingDialog();
                    }

                },
                from : "mcp"
            }

            var settings = $.extend(defalut, obj, true);

            if(typeof obj.data !== "undefined" && obj.data !== null){
                if(obj.data.constructor.name === "FormData") {
                    settings.data = obj.data;
                } else {
                    settings = mcpCommon.setAjaxOptions(settings);
                }
            }

            settings.success = function(result, textStatus, request) {
                //console.log("settings.success");
                mcpGlobal.send = false;

                if(typeof obj.hasLogingBar  === "undefined" || obj.hasLogingBar === true) {
                    mcpCommon.dismissLoadingDialog();
                }


                var error = mcpCommon.outHeaderArrange(result);

                if(typeof error !== "undefined"){

                    if(obj.error){
                        obj.error(error);
                    } else {
                        //if(mcpGlobal.commChannel === "app"){
                            //XXX 에러 처리에 대한 구현 필요
                            alert((typeof error.message !== "undefined" ? error.message: "일시적인 오류가 발생했습니다. 잠시후 다시 시도해 주세요."));
                            //alert("code :: "+ (typeof error.code !== "undefined" ? error.code: "ERRC9999"));
                            //alert("message :: " +(typeof error.message !== "undefined" ? error.message: "시스템오류"));
                            /*
                            if($("#errorPop").length <= 0 ) {
                                var errorPop = $("#ajax-error-template").html();
                                errorPop = errorPop.replace("[[_ERROR_CODE_]]", (typeof error.code !== "undefined" ? error.code: "ERRC9999"));
                                errorPop = errorPop.replace("[[_ERROR_MESSAGE_]]", (typeof error.message !== "undefined" ? error.message: "시스템오류"));
                                $("body").append(errorPop);
                            }
                            $("#errorPop").show();
                            */
                        //} else {
                            //alert(error.message);
                            //XXX 특정 에러에 대한 처리 필요시 구현
                            /*
                            if(error.code === "ERRC9000"){
                                location.href = error.loginPage+"?returnUrl="+error.returnUrl;
                            }
                            */
                        //}
                    }

                    return ;

                }else {
                    var csrf_token = request.getResponseHeader('csrf_token');

                    if(csrf_token !== null){
                        //헤더에 Crsf token 생성
                        $("meta[name='_csrf']").attr("content", csrf_token);
                    }

                    if(typeof obj.success === "function"){
                        obj.success(result);
                    }
                }
            }

            settings.error = function(x, textStatus, e){

                console.log("****************************************************");
                console.log(x);
                console.log(textStatus);
                console.log(e);
                console.log("****************************************************");

                mcpGlobal.send = false;

                if(typeof obj.hasLogingBar  === "undefined" || obj.hasLogingBar === true) {
                    mcpCommon.dismissLoadingDialog();
                }

                if(typeof(obj.error) != 'function'){

                    if(x.status==0){
                        //alert('네트워크 오류');
                        //location.href = "/views/error/error.jsp";
                        E$.errorFn('네트워크 장애가 발생했습니다.<br>잠시후 다시 시도해 주세요');
                    }else if(x.status==403){
                        E$.errorFn('비정상적인 요청입니다.');
                    }else if(x.status==404){
                        //alert('페이지를 찾을수 없습니다.');
                        //location.href = "/error/404";
                        E$.errorFn("요청을 처리하지 못하였습니다.<br>고객센터로 연락주세요.<br>02-2192-6000");
                    }else if(x.status==500){
                        //alert('서버에러');
                        //location.href = "/error/500";
                        //E$.errorFn(x.responseJSON.header.message);
                        E$.errorFn("요청을 처리하지 못하였습니다.<br>고객센터로 연락주세요.<br>02-2192-6000");
                    }else if(e=='parsererror'){
                        //alert('JSON 타싱에러');
                        //location.href = "/views/error/error.jsp";
                        E$.errorFn("요청을 처리하지 못하였습니다.<br>고객센터로 연락주세요.<br>02-2192-6000");
                    }else if(e=='timeout'){
                        //alert('타임아웃');
                        //location.href = "/views/error/error.jsp";
                        //alert("요청을 처리하지 못하였습니다.\n고객센터로 연락주세요.\n1588-9666");
                        E$.errorFn("요청을 처리하지 못하였습니다.<br>고객센터로 연락주세요.<br>02-2192-6000");
                    }else {
                        //alert('Unknow Error.n'+x.responseText);
                        //location.href = "/error/500";
                        E$.errorFn("요청을 처리하지 못하였습니다.<br>고객센터로 연락주세요.<br>02-2192-6000");
                    }

                }else{
                    var error = {};
                    error["type"] = "sysException";
                    error["code"] = "ERRC9999";
                    error["message"] = "시스템 오류가 발생했습니다.";
                    obj.error(error);
                }
            }
            //console.log("before ajax");
            $.ajax(settings);
            //console.log("after ajax");
        }
    })

    $.fn.extend({
        mcpSubmit : function(beforeExcuteFunction) {

            var $this = this;
            var name = $("meta[name='_csrf_header']").attr("content");
            var value = $("meta[name='_csrf']").attr("content");

            if(this.attr("method").toUpperCase() === "POST" && (name && value)) {
                var input = document.createElement("input");
                $(input).attr('type','hidden');
                $(input).attr('name',name);
                $(input).attr('value',value);
                this.append(input);
            }

            var udid_name = $("meta[name='_udid_header']").attr("content");
            var udid_value = $("meta[name='_udid']").attr("content");

            if(this.attr("method").toUpperCase() === "POST" && (udid_name && udid_value)) {
                var udid_input = document.createElement("input");
                $(udid_input).attr('type','hidden');
                $(udid_input).attr('name',udid_name);
                $(udid_input).attr('value',udid_value);
                this.append(udid_input);

            }

            if(!mcpGlobal.submitForm) {

                if(typeof beforeExcuteFunction === "function") {
                    $(this).submit(function(e){
                        beforeExcuteFunction();
                    });
                }else{
                    $(this).submit();
                }

                mcpGlobal.submitForm = true;
                _.delay(function(){
                    mcpGlobal.submitForm = false;
                }, 100);
            }else{
                console.log("요청하신 내용을 이미 실행 중입니다.");
            }
        },

        mcpAjax : function(option) {
            option.data = $(this).serializeObject();
            if(this.hasAttr("method")) option.method = this.attr("method");
            if(this.hasAttr("action")) option.url = this.attr("action");
            $.mcpAjax(option);
        },

        serializeObject : function() {
            var obj = null;
            try {
                if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) {
                    var arr = this.serializeArray();
                    if(arr){
                        obj = {};
                        jQuery.each(arr, function() {
                            obj[this.name] = this.value;
                        });
                    }
                }
            }catch(e) {
                alert(e.message);
                return false;
            }finally {}
            return obj;
        },

        hasAttr: function(name){
            var attr = this.attr(name);
            return (typeof attr !== typeof undefined && attr !== false);
        }
    })
})(jQuery);

var mcpCommon = {
    setAjaxOptions : function(o){
        var data = {}
        data = o.data;
        if(o.contentType === mcpCommon.jsonContentType) {
            var obj = mcpCommon.formToJsonObj(data);
            data = JSON.stringify(obj);
        }
        o.data = data;
        return o;
    },

    formToJsonObj : function(data) {
        if(typeof data !== "undefined") {
            if(typeof data === "string") {
                var obj = {};
                data.replace(/\+/g, "%20").replace(/([^=&]+)=([^&]*)/g, function(m, key, value){
                    obj[decodeURIComponent(key)] = decodeURIComponent(value);
                })
                data = obj;
            }

            if(typeof data !== "object"){
                alert("[Ajax Data Error]!!")
            }
        } else {
            data = {};
        }
        return data;
    },

    outHeaderArrange : function(data) {
        var error;
        if(typeof data === "string") {
            try{
                data = JSON.parse(data);
            }catch(e){
                console.log("Error not Json String!");
            }
        }
        if(data.header) {
            if(data.header.code !== '0000') {
                error = {};
                error["type"] = "bizException";
                error["code"] = data.header.code;
                error["message"] = data.header.message;
                if(data.header.loginPage){
                    error["loginPage"] = data.header.loginPage;
                }
                if(data.header.returnUrl) {
                    error["returnUrl"] = data.header.returnUrl;
                }
            }
        }
        return error;
    },

    jsonContentType : "application/json; charset=utf-8",
    formContentType : "application/x-www-form-urlencoded; charset=UTF-8",
    showLoadingDialog : function() {
        $('#econtract_footer_lading').show();
    },

    dismissLoadingDialog : function() {
        $('#econtract_footer_lading').hide();
    }
};

function getParameterByName(name) {
    var scripts = document.getElementsByTagName('script');
    var myScript = scripts[ scripts.length - 1 ];

    var queryString = myScript.src.replace(/^[^\?]+\??/,'');

    var params = parseQuery( queryString );

    function parseQuery ( query ) {
      var Params = new Object ();
      if ( ! query ) return Params; // return empty object
      var Pairs = query.split(/[;&]/);
      for ( var i = 0; i < Pairs.length; i++ ) {
        var KeyVal = Pairs[i].split('=');
        if ( ! KeyVal || KeyVal.length != 2 ) continue;
        var key = unescape( KeyVal[0] );
        var val = unescape( KeyVal[1] );
        val = val.replace(/\+/g, ' ');
        Params[key] = val;
      }
      return Params;
    }

    return params[name];
};

// 새로고침
function refresh() {
    window.location.reload();
};

// 클래스에 공백 문자 제거 기능 추가 함수
$(document).on("propertychange change keyup paste input", ".removeWhiteSpace", function(event) {
    $(this).val($(this).val().replace(/\s/g, ""));
});


//이메일 유효성 

var emailValid = function(obj){
//    var domainRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}/;
//    var mailNameRegExp = /^[0-9a-zA-Z-\.+\-*\/\%\"\'\(\)\[\]\{\}\~\!\?\=\:\:\,\;\|#\$\&\^\_\?]+$/;
    var typeEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    var notKor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    var spaceChk = /\s/g;
    var id = obj.val();

    var name = id.trim().toLowerCase();

    if(name == "" || notKor.test(name) || !typeEmail.test(name)) {
		alert("올바른 이메일을 입력해 주세요.");
		return false;
    } else {
		return true;
	}
};

//json object 타입변환
const formToJsonObj = function(data) {
    if(typeof data !== "undefined") {
        if(typeof data === "string") {
            var obj = {};
            data.replace(/\+/g, "%20").replace(/([^=&]+)=([^&]*)/g, function(m, key, value){
                obj[decodeURIComponent(key)] = decodeURIComponent(value);
            })
            data = obj;
        }

        if(typeof data !== "object"){
            alert("[Ajax Data Error]!!")
        }
    } else {
        data = {};
    }
    return data;
};

const $post = function(url, param) {
	return $.ajax({
		type		: 'post',
//		dataType	: 'json',
//		dataType	: 'html',
		contentType	: "application/json; charset=utf-8",
		url			: url,
		data		: JSON.stringify(formToJsonObj(param)),
	})
	.always(() => {
		mapOnLoad = false;	// 카카오맵 초기화
	})
	.fail(function(data, textStatus, xhr){
	    if(data.status == 401) {
			location.href="/main/login";    // 인증 풀렸을경우 로그인 화면으로.
		} else if(data.status == 499) {
			alert('유효하지 않은 요청입니다.');
		} else if(data.status == 498) {
			alert('저장에 실패하였습니다.');
	    } else {
			alert("오류 발생");
			if(data.status != null) console.log(data.status);
		}
	});
};

const $postWithFile = function(url, formData) {
	return $.ajax({
		type		: 'post',
		url			: url,
		data		: formData,
        processData	: false, 
        contentType	: false, 
        cache		: false,
	})
	.always(() => {
		mapOnLoad = false;	// 카카오맵 초기화
	})
	.fail(function(data, textStatus, xhr){
	    if(data.status == 401) {
			location.href="/main/login";    // 인증 풀렸을경우 로그인 화면으로.
		} else if(data.status == 499) {
			alert('유효하지 않은 요청입니다.');
		} else if(data.status == 498) {
			alert('저장에 실패하였습니다.');
	    } else {
			alert("오류 발생");
			if(data.status != null) console.log(data.status);
		}
	});
};

//<%-- 게시글 조회 ajax--%>
const goBoardList = function(obj){
	let pageNum = $(obj).data('value');
	let type = $(obj).data('type') == 'main' ? true : false;
	let searchType;
	let searchKeyword;
	
	if(!type){
		searchType = $('#searchType').val();
		searchKeyword = $('#searchKeyword').val(); 
	} else {
		$('#searchType, #searchKeyword').val('');
	}
	
	let data = 
		{
			"page" 			: pageNum,
			"searchType"	: searchType,
			"searchKeyword"	: searchKeyword
		};
	
	$post('/board/list', data)
	
	.done(function(data){
		$('#board_list').html(data);
	});
};

//모달 외부 클릭시 닫힘
//$(document).mouseup(function(e){
//	var container = $('#write_modal');
//	var editor = $('.cke_dialog_body');
//
//	if( container.has(e.target).length === 0){
//		if(editor != null && editor.has(e.target).length ===0) {
//			container.hide();
//		}
//	}
//});

//글쓰기 모달 닫기
$(document).on('click', '#close_modal', function(){
	$('#write_modal').hide();
});

//입력값 체크
const inputValid = function(obj){
	let result = true;
	
	$(obj).each(function(){
		let target = $('#' + this);
		
		if(target.val().trim() == '') {
			let msg = target.data('error') + ' 입력해 주세요.';
			alert(msg);
			result = false;
			target.val('');
			target.focus();
			return false;
		}
	});
	
	return result;
};

//<%--지하철역 조회--%>
const getStations = function(line, target){
	let param 		= {'lineNum' : line };	
	let appendId 	= $('#' + target);		
	
	if(line != '') {
		$post('/board/getStations', param)
		.done(function(data){
			if(data != null) {
				appendId.empty();
				appendId.append('<option value="">:::: 지하철역 ::::</option>');
// 				swiperContainer.removeAllSlides();
				for(let i=0; i < data.length; i++) {
					appendId.append(new Option(data[i].station, data[i].station));
// 					appendId.append('<div class="swiper-slide info-box bg-success"><div class="info-box-content"><span class="info-box-text">' + data[i].station + '<span></div></div>');
				}
		       
// 				swiperContainer.update();
			} else {
				alert('지하철역 목록을 불러오는데 실패하였습니다.');
			}
		});
	} else {
		appendId.empty();
		appendId.append('<option value="">:::: 지하철역 ::::</option>');
	}
};

//<%--키워드 검색 완료 시 호출되는 콜백함수 입니다--%>
const placesSearchCB  = function(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        let bounds = new kakao.maps.LatLngBounds();
        
        displayMarker(data[0]);    
        bounds.extend(new kakao.maps.LatLng(data[0].y, data[0].x));

        //검색대로 마커
//         for (let i=0; i<data.length; i++) {
//             displayMarker(data[i]);    
//             bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
//         }       

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
};

//<%--지도에 마커를 표시하는 함수입니다--%>
const displayMarker = function(place) {
    // 마커를 생성하고 지도에 표시합니다
    marker = new kakao.maps.Marker({
        map		: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });
    
//    <%--마커 드래그이동 가능--%>
    marker.setDraggable(true);
    
//    if(!isMarkerOn) {
//	    infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
//	    infowindow.open(map, marker);
//	    isMarkerOn = true;
//    }

//    <%--마커 클릭이벤트 등록--%>
//     kakao.maps.event.addListener(marker, 'click', function(mouseEvent) {
//		<%--최초 카카오맵이 열릴때 지하철역 마킹--%>    	
// 		if(!isMarkerOn) {
// 	        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
// 	        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
// 	        infowindow.open(map, marker);
        
//<%--         마커 이동 후 위치정보 재검색 --%>
// 		} else {
// 	        // 클릭한 위도, 경도 정보를 가져옵니다 
// // 	        let latlng = mouseEvent.latLng; 
// 	        // 마커 위치를 클릭한 위치로 옮깁니다
// 	        marker.setPosition(mouseEvent.latLng);
// 		}
//     });
    
//    kakao.maps.event.addListener(marker, 'dragstart', function() {
//    	infowindow.close();
//    });
    
// 	kakao.maps.event.addListener(marker, 'dragend', function() {
//         marker.setPosition(mouseEvent.latLng);
// 		map.setCenter(marker.getPosition());	//맵 중심위치 변경      
// 		infowindow.open(map, marker);
// 		infowindow.close();
        // 마커 위치를 클릭한 위치로 옮깁니다
// 	});
};

//대상이 빈객체인가 아닌지 확인
const isNotEmptyObj = function(obj) {
	if(obj.constructor === Object && Object.keys(obj).length !== 0)  {
		return true;
	} else {
		return false;
	}
};
