<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% pageContext.setAttribute("space", "\n"); %>

<section class="content-header">
    <button id="close_modal" type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <h1>게시글 조회</h1>
</section>

<section class="content container-fluid" >
    <div class="col-lg-12">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">글제목 : ${boardVo.title }</h3>
                <ul class="list-inline pull-right">
                    <li id="view_cnt" data-count="${boardVo.viewCount }"><i class="fa fa-eye"></i>조회수 (${boardVo.viewCount })</li>
                </ul>
            </div>
			<section class="custom_scrollbar" >
	            <div class="box-body" >
					${boardVo.content }
	<%-- 				${fn:replace(fn:replace(fn:escapeXml(boardVo.content), space, "<br/>") , " ", "&nbsp;")} --%>
	<%-- 				${fn:replace(fn:replace(boardVo.content, space, "<br/>") , " ", "&nbsp;")} --%>
	            </div>
	
	            <%--업로드 파일 정보 영역--%>
	            <div class="box-footer uploadFiles">
	                <ul class="mailbox-attachments clearfix uploadedList"></ul>
	            </div>
	
	            <%--작성자 정보 영역--%>
	            <div class="box-footer">
	                <div class="user-block">
	                    <img class="img-circle img-bordered-sm" src="/adminLTE/dist/img/default-user-image.jpg" alt="user image">
	                    <span class="username">
	                        <a href="#">${boardVo.userName}</a>
	                    </span>
	                    <span class="description"><fmt:formatDate pattern="yyyy-MM-dd a HH:mm" value="${boardVo.regDate}"/></span>
	                </div>
	            </div>
	
	            <%--페이지 이동 버튼 영역--%>
	            <div class="box-footer">
	                <c:if test="${memberVo.userIdx eq boardVo.brdUserIdx }">
	                    <div class="pull-right">
	                        <button type="button" id="modBtn" data-idx="${boardVo.brdIdx }" data-uidx="${boardVo.brdUserIdx }" class="btn btn-warning modBtn"><i class="fa fa-edit"></i> 수정</button>
	                        <button type="button" id="delBtn" data-idx="${boardVo.brdIdx }" data-uidx="${boardVo.brdUserIdx }" class="btn btn-danger delBtn"><i class="fa fa-trash"></i> 삭제</button>
	                    </div>
	                </c:if>
	            </div>
	
		        <%--댓글 입력 영역--%>
		        <div class="box box-warning">
		            <div class="box-header with-border">
		                <a class="link-black text-lg"><i class="fa fa-pencil"></i> 댓글작성</a>
		            </div>
		            <div class="box-body">
		                <form class="form-horizontal">
		                    <div class="form-group margin-bottom-none">
		                        <div class="col-sm-10">
		                            <textarea class="form-control" id="newReplyText" rows="3" placeholder="댓글을 입력해주세요..." style="resize: none"></textarea>
		                        </div>
		                        <div class="col-sm-2">
		                            <input class="form-control" id="newReplyWriter" type="text" value="${memberVo.userName}" readonly="readonly">
		                        </div>
		                        <hr/>
		                        <div class="col-sm-2">
		                            <button type="button" class="btn btn-primary btn-block replyAddBtn"><i class="fa fa-save"></i> 저장</button>
		                        </div>
		                    </div>
		                </form>
		            </div>
		        </div>
		
		        <%--댓글 목록 영역--%>
		        <div class="box box-success collapsed-box">
		            <%--댓글 유무 / 댓글 갯수 / 댓글 펼치기, 접기--%>
		            <div class="box-header with-border">
		                <a href="" class="link-black text-lg"><i class="fa fa-comments-o margin-r-5 replyCount"></i> </a>
		                <div class="box-tools">
		                    <button type="button" class="btn btn-box-tool" data-widget="collapse">
		                        <i class="fa fa-plus"></i>
		                    </button>
		                </div>
		            </div>
		            <%--댓글 목록--%>
		            <div class="box-body repliesDiv">
		
		            </div>
		            <%--댓글 페이징--%>
		            <div class="box-footer">
		                <div class="text-center">
		                    <ul class="pagination pagination-sm no-margin">
		
		                    </ul>
		                </div>
		            </div>
		        </div>
		        
		        <%--댓글 수정 modal 영역--%>
		        <div class="modal fade" id="modModal">
		            <div class="modal-dialog">
		                <div class="modal-content">
		                    <div class="modal-header">
		                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                            <span aria-hidden="true">&times;</span></button>
		                        <h4 class="modal-title">댓글수정</h4>
		                    </div>
		                    <div class="modal-body" data-rno>
		                        <input type="hidden" class="rno"/>
		                        <%--<input type="text" id="replytext" class="form-control"/>--%>
		                        <textarea class="form-control" id="replytext" rows="3" style="resize: none"></textarea>
		                    </div>
		                    <div class="modal-footer">
		                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">닫기</button>
		                        <button type="button" class="btn btn-primary modalModBtn">수정</button>
		                    </div>
		                </div>
		            </div>
		        </div>
		
		        <%--댓글 삭제 modal 영역--%>
		        <div class="modal fade" id="delModal">
		            <div class="modal-dialog">
		                <div class="modal-content">
		                    <div class="modal-header">
		                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                            <span aria-hidden="true">&times;</span></button>
		                        <h4 class="modal-title">댓글 삭제</h4>
		                        <input type="hidden" class="rno"/>
		                    </div>
		                    <div class="modal-body" data-rno>
		                        <p>댓글을 삭제하시겠습니까?</p>
		                        <input type="hidden" class="rno"/>
		                    </div>
		                    <div class="modal-footer">
		                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">아니요.</button>
		                        <button type="button" class="btn btn-primary modalDelBtn">네. 삭제합니다.</button>
		                    </div>
		                </div>
		            </div>
		        </div>
			</section>
        </div>
    </div>

</section>

