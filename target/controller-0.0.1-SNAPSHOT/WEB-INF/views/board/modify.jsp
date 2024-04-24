<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- 본문 페이지 설명란 -->
<h1 class="h3 mb-2 text-gray-800">Board Modify</h1>
<p class="mb-4">
	현재 화면은 <c:out value="${board.bno}"/>번 게시글의 수정페이지입니다.
</p>

<!-- 본문내용(표) 시작 -->
<div class="card shadow mb-4">
	<!-- LIST 제목 시작 -->
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">Board Read</h6>
	</div>
	<!-- LIST 제목 끝 -->
	<!-- LIST 시작 -->
	<div class="card-body">
		<div class="table-responsive">
			<form role="form" action="/board/modify" method="post">
				<div class="form-group">
					<label>No</label>
					<input class="form-control" name="bno" value="<c:out value="${board.bno}"/>" readonly="readonly">
				</div>			
				<div class="form-group">
					<label>Title</label>
					<input class="form-control" name="title" value="<c:out value="${board.title}"/>">
				</div>
				<div class="form-group">
					<label>Text Area</label>
					<textarea class="form-control" rows="10" name="content"><c:out value="${board.content}"/></textarea>
				</div>
				<div class="form-group">
					<label>Writer</label>
					<input class="form-control" name="writer" value="<c:out value="${board.writer}"/>" readonly="readonly">
				</div>
				<div class="form-group">
					<label>RegDate</label>
					<input class="form-control" name="regdate" value='<fmt:formatDate pattern="YY/MM/dd" value="${board.regdate}"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>UpdateDate</label>
					<input class="form-control" name="updateDate" value='<fmt:formatDate pattern="YY/MM/dd" value="${board.updateDate}"/>' readonly="readonly">
				</div>
				
				<button type="submit" data-oper="modify" class="btn btn-info2" style="border-color:#6b6d7d">Modify</button>
				<button type="submit" data-oper="remove" class="btn btn-info2" style="border-color:#6b6d7d">Remove</button>
				<button type="submit" data-oper="list" class="btn btn-info">List</button>
				
				<!-- BoardController get()_modify의 @ModelAttribute("cri")로 인해 수집된 정보 -->
				<input type="hidden" id="pageNum" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
				<input type="hidden" id="amount" name="amount" value='<c:out value="${cri.amount}"/>'>				
				<!-- 검색조건 유지 -->
				<input type="hidden" name="type" value='<c:out value="${cri.type}"/>'>
				<input type="hidden" name="keyword" value='<c:out value="${cri.keyword}"/>'>
			</form>
		</div>
	</div>
	<!-- LIST 끝 -->
</div>
<!-- 본문내용(표) 끝 -->

<script>
	var formObj = $("form");
	
	<!-- form 내부의 버튼클릭이벤트 data-oper="modify/remove/list" -->
	$('button').on('click', function(e){
		e.preventDefault(); // 기본기능 막기
		
		var oper = $(this).data("oper"); // 현재 동작상태 : button의 data-oper속성
		
		if(oper === 'remove'){
			formObj.attr("action", "/board/remove");
		} else if(oper === 'list'){
			formObj.attr("action", "/board/list").attr("method", "get");
			/* 기존의 페이지+검색조건으로 이동하기 위한 세팅 */
			var pageNumTag = $("input[name='pageNum']").clone();
			var amountTag = $("input[name='amount']").clone();
			var typeTag = $("input[name='type']").clone();
			var keywordTag = $("input[name='keyword']").clone();
						
			formObj.empty();
			
			/* 기존의 페이지로 이동 */
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(typeTag);
			formObj.append(keywordTag);
		}
		formObj.submit();
	});
</script>

<%@ include file="../includes/footer.jsp"%>