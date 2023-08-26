<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
						<td class="custom_pointer">${count }</td>
						<td class="fix_text_length custom_pointer readBtn" data-uidx="${list.brdUserIdx }" data-index="${list.brdIdx }">${list.title }</td>
						<td class="custom_pointer">${count }</td>
						<td class="custom_pointer">${list.userName }</td>
						<td>${list.regDate }</td>
						<td>${count }</td>
						<td class="vcount_td"><span class="badge bg-light-blue"><i class="fa fa-eye"></i><span class="view_span">${list.viewCount }</span></td>
					</tr>
				</c:forEach>
			</c:if>
        </tbody>
    </table>
</div>
	                    
<div class="box-footer">
    <div class="text-center">
        <ul class="pagination">
            <c:if test="${boardList.isHasPreviousPage() }">
                <li><span id="prevBtn" class="custom_pointer"class="custom_pointer" data-value="${boardList.getPrePage() }" onclick="goBoardList(this);"><</span></li>
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

<!-- <form role="form" method="post"> -->
<%--     <input type="hidden" name="page" 		value="${boardList.getPageNum() }"	> --%>
<%--     <input type="hidden" name="searchType" 	value="${boardVo.searchType }"		> --%>
<%--     <input type="hidden" name="keyword" 	value="${boardVo.keyword }"			> --%>
<!-- </form> -->