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