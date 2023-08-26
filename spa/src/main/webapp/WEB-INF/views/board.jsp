<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/include/head.jsp" %>

<body class="hold-transition skin-blue sidebar-mini layout-boxed">
<div class="">
    <%@ include file="/WEB-INF/views/include/main_header_left.jsp" %>
    
    <div class="content-wrapper">
        <section class="content-header">
            <h1>게시판</h1>
            <ol class="breadcrumb">
                <li><a href="/spa/main"><i class="fa fa-dashboard"></i>HOME</a></li>
            </ol>
        </section>

        <%-- Main content --%>
        <section class="content container-fluid">

            <div class="col-lg-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">게시글 목록</h3>
                    </div>
                    <div class="box-footer" style="padding-bottom:0; padding-right: 25px;">
                        <div class="form-group col-sm-2">
                            <select class="form-control custom_pointer" name="subwayLine" id="subwayLine">
                                <option value="">:::: 호선 ::::</option>
                                
	                           	<c:if test="${not empty subwayLines }">
									<c:forEach var="list" items="${subwayLines }" varStatus="i">
										<option value="${list eq '우이신설선' ? '우이신설' : list }">${list }</option>										
									</c:forEach>
								</c:if>
								
                            </select>
                        </div>
                        <div class="form-group col-sm-3">
                            <select class="form-control custom_pointer" name="subwayST" id="subwayST">
                                <option value="">:::: 지하철역 ::::</option>
                            </select>
                        </div>
					</div>
					<div class="box-footer" style="padding-top: 0px;">
                        <div class="form-group col-sm-2">
                            <select class="form-control custom_pointer" name="searchType" id="searchType">
                                <option value=""			>:::: 선택 ::::</option>
                                <option value="title"		>제목</option>
                                <option value="content"		>내용</option>
                                <option value="writer"		>작성자</option>
                                <option value="storeName"	>상호명</option>
                            </select>
                        </div>
                        <div class="form-group col-sm-5">
                            <div class="input-group">
                                <input type="text" class="form-control" name="keyword" id="searchKeyword" value="" placeholder="검색어">
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-primary btn-flat" id="searchBtn" data-value="1">
                                        <i class="fa fa-search"></i> 검색
                                    </button>
									<button type="button" style="margin-left:5px;" class="btn btn-primary btn-flat" id="resetSearchOption" data-value="1">
		                                <i class="fa fa-undo"></i> 초기화
		                            </button>
                                </span>
                            </div>
                        </div>
                    </div>
                    
                    <div id="board_list" >
	                    <div class="box-body">
	                        <table class="table table-bordered" style="table-layout:fixed">
	                            <tbody>
		                            <tr>
										<th style="width: 40px">#</th>
										<th style="width: 100px">상호명</th>
										<th >제목</th>
										<th style="width: 100px">역이름</th>
										<th style="width: 100px">작성자</th>
										<th style="width: 150px">작성일</th>
										<th style="width: 60px">추천수</th>
										<th style="width: 60px">조회수</th>
		                            </tr>
		                            
		                           	<c:if test="${not empty boardList }">
										<c:forEach var="list" items="${boardList.getList() }" varStatus="i">
											<c:set var="count" value="${boardList.total - (boardList.pageNum - 1) * boardList.pageSize - i.index}"></c:set>
											<tr>
												<td>${count }</td>
												<td class="custom_pointer" >${count }</td>
												<td class="fix_text_length custom_pointer readBtn" data-uidx="${list.brdUserIdx }" data-index="${list.brdIdx }">${list.title }</td>
												<td class="custom_pointer" >${count }</td>
												<td class="fix_text_length custom_pointer" >${list.userName }</td>
												<td>${list.regDate }</td>
												<td>${count }</td>
												<td class="vcount_td"><span class="badge bg-light-blue"><i class="fa fa-eye"></i><span class="view_span">${list.viewCount }</span></td>
											</tr>
										</c:forEach>
									</c:if>
	                            </tbody>
	                        </table>
	                    </div>
	                    
	                    <%--Pagination bar--%>
	                    <div class="box-footer">
	                        <div class="text-center">
	                            <ul class="pagination">
	                                <c:if test="${boardList.isHasPreviousPage() }">
	                                    <li><span id="prevBtn" class="custom_pointer" data-value="${boardList.getPrePage() }" onclick="goBoardList(this);" ><</span></li>
	                                </c:if>
	                                <c:forEach var="list" items="${boardList.getNavigatepageNums() }">
	                                    <li ${list eq boardList.getPageNum() ? 'class=active' += ' id=onPage ' : '' } >
	                                        <span class="custom_pointer" data-value="${list }" ${list ne boardList.getPageNum() ? 'onclick=goBoardList(this)' : '' }>${list}</span>
	                                    </li>
	                                </c:forEach>
	                                <c:if test="${boardList.isHasNextPage() }">
	                                    <li><span id="nextBtn" class="custom_pointer" data-value="${boardList.getNextPage() }" onclick="goBoardList(this);">></span></li>
	                                </c:if>
	                            </ul>
	                        </div>
	                    </div>
                    </div>
                    
                    <div class="box-footer">
                        <div class="pull-right">
                            <button type="button" class="btn btn-success btn-flat" id="writeBtn">
                                <i class="fa fa-pencil"></i> 글쓰기
                            </button>
                        </div>
                    </div>
                    							<div class="swiper-container">
								<div class="swiper-wrapper" id="swiper-wrapper">
								</div>
							</div>
                </div>
			</div>
		</section>
	</div>
	<%@ include file="/WEB-INF/views/include/main_footer.jsp" %>
</div>
</body>

<%-- 팝업 --%>
<div class="modal" id="write_modal"  role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true" >
	<div class="modal-dialog" style="width:800px;">
		<div id="popup_content" class="modal-content" >
		</div>
	</div>
</div>

<form id="submitForm" class="form-horizontal" role="form" enctype="multipart/form-data" ></form>


<script type="text/javascript">

const swiperContainer = new Swiper('.swiper-container', {
    slidesPerView: 3, //슬라이드 엘리먼트를 몇개나 보여줄 것인지 설정
    spaceBetween: 10, //각 객체들 사이의 패딩 값
    breakpoints: {   //반응형 width에 따라 슬라이드 갯수, 객체들 사이의 패딩 값
	                  1000: { //width가 1000보다 클때
                              slidesPerView: 3,
                              spaceBetween: 10,
				      },
                      1500: { //width가 1500보다 클때
                              slidesPerView: 3,
                              spaceBetween: 10,
                      },
                      1900: { //width가 1900보다 클때
                              slidesPerView: 3,
                              spaceBetween: 10,
                      }
				},
    mousewheelControl: true,
    freeMode: true,//활성화하면 슬라이드에 고정된 위치가 없음 여러개 슬라이드 휙 움직임
    grabCursor: true
});

// const swiperContainer = new Swiper(".swiper-container", {
//     freeMode : false, // 슬라이드 넘길 때 위치 고정 여부
//     autoHeight : true, // true로 설정하면 슬라이더 래퍼가 현재 활성 슬라이드의 높이에 맞게 높이를 조정합니다.
//     a11y : false, // 접근성 매개변수(접근성 관련 대체 텍스트 설정이 가능) - api문서 참고!
//     resistance : false, // 슬라이드 터치에 대한 저항 여부 설정
//     slideToClickedSlide : true, // 해당 슬라이드 클릭시 슬라이드 위치로 이동
//     centeredSlides : true, // true시에 슬라이드가 가운데로 배치
//     allowTouchMove : true, // false시에 스와이핑이 되지 않으며 버튼으로만 슬라이드 조작이 가능
//     watchOverflow : true, // 슬라이드가 1개 일 때 pager, button 숨김 여부 설정
// //     slidesOffsetBefore : number, // 슬라이드 시작 부분 여백
// //     slidesOffsetAfter : number, // 슬라이드 시작 부분 여백
//     pagination : {   // 페이저 버튼 사용자 설정
//         el : '.pagination',  // 페이저 버튼을 담을 태그 설정
//         clickable : true,  // 버튼 클릭 여부
//         type : 'bullets', // 버튼 모양 결정 "bullets", "fraction" 
//         renderBullet : function (index, className) {  // className이 기본값이 들어가게 필수 설정
//             return '<a href="#" class="' + className + '">' + (index + 1) + '</a>'
//         },
//         renderFraction: function (currentClass, totalClass) { // type이 fraction일 때 사용
//             return '<span class="' + currentClass + '"></span>' + '<span class="' + totalClass + '"></span>';
//         }
//     },
// });


<%--검색 유효성 검사 --%>
$('#searchBtn').on('click', function(){
	let type = $('#searchType').val();
	let keyword = $('#searchKeyword').val().trim(); 
	
	if(type == '') {
		alert('검색 타입을 선택해주세요.');
	} else if(keyword == '') {
		alert('검색어를 입력해 주세요.');
	} else {
		goBoardList(this);
	}
});

<%-- 게시판 글쓰기 팝업 --%>
$('#writeBtn, #writeBtn2').on('click', function(){
	$post('/board/write')
	.done(function(data){
		$('#popup_content').html(data);
		CKEDITOR.replace('content', {height: '400px'});
		$('#write_modal').show();
	});
});

<%-- 게시글 조회 --%>
$(document).on('click', '.readBtn', function(obj){
	let idx 	= $(this).data('index');
	let uidx 	= $(this).data('uidx');
	let viewCnt = $(this).siblings('.vcount_td').find('.view_span');
	
	if(idx != undefined && idx != '' && idx != null) {
		let boardInfo = {'brdIdx' : idx, "brdUserIdx" : uidx};
		
		$post('/board/read', boardInfo)
		.done(function(data){
			$('#popup_content').html(data);
			viewCnt.html($('#view_cnt').data('count'));
			$('#write_modal').show();
		});
	}
});

<%--게시물 수정버튼 클릭시 수정화면--%>
$(document).on('click', '#modBtn', function(obj){
	let boardIdx 	= $(this).data('idx');
	let brdUserIdx 	= $(this).data('uidx');
	
	let boardInfo = {"brdIdx" : boardIdx, "brdUserIdx" : brdUserIdx};
	
	$post('/board/updContent', boardInfo)
	.done(function(data){
		$('#popup_content').html(data);
		CKEDITOR.replace('content', {height: '400px'});
		CKEDITOR.instances.content.setData($('#mod_content').val());
 		$('#mod_content').remove();
	});
});

<%--게시물 리셋버튼 클릭시--%>
$(document).on('click', '#reset', function(e){
	e.preventDefault();
	
	$('#title').val('');
	CKEDITOR.instances.content.setData("");
});

<%--작성게시물 저장버튼 클릭시 --%>
$(document).on('click', '#submitContent', function(){
	insertBoard2('#board_write_form');
});

<%--작성게시물 저장버튼 클릭시 --%>
$(document).on('click', '#modifyContent', function(){
	updateContent('#board_write_form');
});

<%--게시물 삭제버튼 클릭시 --%>
$(document).on('click', '#delBtn', function(){
	let msg = '정말로 삭제하시겠습니까?';
	
	if(confirm(msg)) {
		deleteContent(this);
	}
});

<%--지하철 노선 선택 시--%>
$('#subwayLine').on('change', function(){
	let selectedLine 	= $('#subwayLine').val();	<%--선택한 노섬--%>
	let displayId 		= 'subwayST';				<%--검색 후 목록 보여줄곳--%>
	
<%-- 	let displayId 		= 'swiper-wrapper';			검색 후 목록 보여줄곳 --%>
	getStations(selectedLine, displayId);				
});

<%--검색 초기화 버튼--%>
$('#resetSearchOption').on('click', function(){
	$('#searchType, #searchKeyword, #subwayLine').val('');
	getStations('', 'subwayST')
});

<%--카카오맵 위치값 --%>
const getCoordinate = function() {
	let cooMakLat = marker.getPosition().La;	<%--마커 위도--%>
	let cooMakLon = marker.getPosition().Ma;	<%--마커 경도--%>
	let cooMapLvl = map.getLevel();				<%--맵 레벨--%>
	let cooMapLat = map.getCenter().La;			<%--맵 위도--%>
	let cooMapLon = map.getCenter().Ma;			<%--맵 경도--%>
	
	let obj = {
			"cooMakLat" : cooMakLat,
			"cooMakLon" : cooMakLon,
			"cooMapLvl" : cooMapLvl,
			"cooMapLat" : cooMapLat,
			"cooMapLon" : cooMapLon,
	};
	
	return obj; 
};

<%--작성게시물 저장--%>
const insertBoard = function(obj){
	$('#mod_content').remove();
	$('#content').val(CKEDITOR.instances.content.getData());		//에디터 작성글 textarea에 넣기
	let inputData = $(obj).find('input, select, textarea');
	let coordiData = JSON.stringify(getCoordinate());
	let storeVo = {};
	let valid = true;
	
	inputData.each(function(){
		if(($(this).val() == '' || $(this).val() == undefined)) {
			alert($(this).attr('placeholder'));
			valid = false;
		}
		
		if(valid){
			<%--storeVo 값 설정--%>
			if($(this).data('store') == 'info') {
				let key = $(this).attr('name');
				let val = $(this).val();
				storeVo[key] = val;
			} else {
				$('#submitForm').append($('<input/>', {type: 'hidden', name: $(this).attr('name'), value:$(this).val() }));
			}
		} else {
			return false;
		}
	});
	
	if(coordiData != undefined && coordiData != null) {
		$('#submitForm').append($('<input/>', {type: 'hidden', name: 'cooVo', value: coordiData }));
	}
	
	if(isNotEmptyObj(storeVo)) {
		let jsonData = JSON.stringify(storeVo);
		$('#submitForm').append($('<input/>', {type: 'hidden', name: 'storeVo', value: jsonData }));
	}
	
	if(valid){
		let formData = $('#submitForm').serialize();
		
		$post('/board/insert', formData)
		.done(function(data){
			alert('저장되었습니다.');
			$('#board_list').html(data)
			$('#write_modal').hide();
		});
	}
};

<%--작성게시물 저장--%>
const insertBoard2 = function(obj){
	$('#mod_content').remove();
	$('#content').val(CKEDITOR.instances.content.getData());		//에디터 작성글 textarea에 넣기
	let inputData = $(obj).find('input, select, textarea');
	let coordiData = JSON.stringify(getCoordinate());
	let storeVo = {};
	let valid = true;
	
	inputData.each(function(){
		if(($(this).val() == '' || $(this).val() == undefined)) {
			alert($(this).attr('placeholder'));
			valid = false;
		}
		
		if(valid){
			<%--storeVo 값 설정--%>
			if($(this).data('store') == 'info') {
				let key = $(this).attr('name');
				let val = $(this).val();
				storeVo[key] = val;
			} else {
				$('#submitForm').append($('<input/>', {type: 'hidden', name: $(this).attr('name'), value:$(this).val() }));
			}
		} else {
			return false;
		}
	});
	
	if(coordiData != undefined && coordiData != null) {
		$('#submitForm').append($('<input/>', {type: 'hidden', name: 'cooVo', value: coordiData }));
	}
	
	if(isNotEmptyObj(storeVo)) {
		let jsonData = JSON.stringify(storeVo);
		$('#submitForm').append($('<input/>', {type: 'hidden', name: 'storeVo', value: jsonData }));
	}
	
	if(valid){
// 		let jsonData = $('#submitForm').serialize();
// 		formData.append("jsonData", jsonData);

	let form = $('#submitForm')[0];
	let formData = new FormData(form);
		
		$postWithFile('/board/insert', formData)
		.done(function(data){
			alert('저장되었습니다.');
			$('#board_list').html(data)
			$('#write_modal').hide();
		});
	}
};


<%--게시판 컨텐츠 수정 --%>
const updateContent = function(obj){
	$('#content').val(CKEDITOR.instances.content.getData());
	let title 		= $(obj).find('#title').val();
	let userName 	= $(obj).find('#writer').val();
	let content 	= $(obj).find('#content').val();
	let brdIdx 		= $(obj).find('#modifyContent').data('idx');
	let brdUserIdx 	= $(obj).find('#modifyContent').data('uidx');
	
	let data = 
		{
			"title" 		: title,
			"userName" 		: userName,
			"content" 		: content,
			"brdIdx" 		: brdIdx,
			"brdUserIdx" 	: brdUserIdx,
			"pageVo" 		: page_info()
		};
	
	$post('/board/update', data)
	.done(function(data){
		alert('수정 되었습니다.');
		$('#board_list').html(data);
		$('#write_modal').hide();
	});
}

<%--게시물 삭제--%>
const deleteContent = function(obj){
	let boardIdx   	= $(obj).data('idx');
	let brdUserIdx 	= $(obj).data('uidx');
	
	let data = 
		{
			"brdIdx" 	 	: boardIdx,
			"brdUserIdx" 	: brdUserIdx,
			"pageVo"		: page_info()

		};
	
	$post('/board/delete', data)
	.done(function(data){
		alert('삭제되었습니다.');
		$('#board_list').html(data);
		$('#write_modal').hide();
	});
};

<%-- 페이지 정보 --%>
const page_info = function(){
	let searchType 	= $('#searchType option:selected').val();
	let searchWord 	= $('#searchKeyword').val().trim();
	let page	   	= $('#onPage').find('span').data('value');
	let pageData =  
		{
			"page"			: page,
			"searchType" 	: searchType,
			"searchKeyword" : searchWord
		};
	
	return pageData;
};

<%-- kakao map global variables --%>
let map;
let marker;
let mapOnLoad 	= false;	//kakao map onload 구분값
// let isMarkerOn 	= false;	//marker onload 구분값	
let infowindow 	= new kakao.maps.InfoWindow({zIndex:1}); 

<%-- 지하철역 선택 시 kakao map open --%>
$(document).on('change', '#station', function(){
	let station = $(this).val() + '역';			//지하철역 이름
	let line 	= $('.subwayLine').val() == '우이신설' ? '우이신설선' : $('.subwayLine').val();	//호선 (지하철역정보api 호환을 위해 우이신설선 변경)
	
	if(!mapOnLoad) {
		$('#kakaoGuideText').removeClass('hide');
		$('#kakaoMap').attr('style', 'width:600px; height:400px;');		//맵 크기 세팅
		let mapContainer = 
			$('#kakaoMap')[0], // 지도를 표시할 div 
		    mapOption = {
		        center: new kakao.maps.LatLng(37.566826, 126.9786567), 	//지도의 중심좌표
		        level: 4 // 지도의 확대 레벨
		    };  
	
		// 지도를 생성합니다    
		map = new kakao.maps.Map(mapContainer, mapOption);
		mapOnLoad = true;
	}

	let ps = new kakao.maps.services.Places(); 				//장소 검색 객체 생성
	//키워드로 장소 검색
	ps.keywordSearch(station + line, placesSearchCB, {				
		category_group_code : 'SW8'	// 카테고리: 지하철역		
	});
// 	ps.categorySearch("SW8", placesSearchCB);				//카테고리로 장소 검색

	<%--맵 클릭시 마커 이동 이벤트--%>
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		let latLng = mouseEvent.latLng;    	

		infowindow.close();
    	marker.setPosition(latLng);
    });
    
});

<%--모달창 닫기버튼 클릭 시 카카오맵 초기화--%>
$(document).on('click', '#close_modal', function(){
	mapOnLoad = false;
});

</script>
</html>