<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<title>게시판</title>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header">Board List</h1>
	</div>
	<p class="mb-4">
		자유롭게 게시글을 작성하는 게시판입니다.
	</p>
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				Board List Page
				<button type="button" id="regBtn" class="btn btn-primary btn-xs pull-right">register</button>
			</div>
			<!-- 본문 및 부가기능 -->
			<div class="panel-body">
				<!-- 본문 -->
				<!-- <table width="100%"	class="table table-striped table-bordered table-hover" id="dataTables-example"> sort기능 -->
				<table class="table table-striped table-bordered table-hover tableList">
					<thead>
						<tr>
							<th class="Bbno">No</th>
							<th class="Btitle">제목</th>
							<th class="Bwriter">글쓴이</th>
							<th class="Breply">댓글</th>
							<th class="Bhit">조회수</th>							
							<th class="Bregdate">등록일</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${list}" var="board">
						<tr>
							<td><c:out value="${board.bno}" /></td>
							<td class="Btitle">
								<a class="move" href='<c:out value="${board.bno}"/>'>
									<c:out value="${board.title}" />
								</a>
							</td>	
							<td><c:out value="${board.writer}" /></td>							
							<td><c:out value="${board.replyCnt}" /></td>						
							<td><c:out value="${board.hit}" /></td>							
							<td><fmt:formatDate pattern="YY.MM.dd" value="${board.regdate}" /></td>						
						</tr>
					</c:forEach>					
					</tbody>
				</table>
				
				<!-- 검색창 -->
				<div class="row">		
					<div class="col-lg-12">	
						<div class="pull-right">
							<form id="searchForm" action="/board/list" method='get'>							
								<select name="type">
									<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }"/>>--</option>						
									<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : '' }"/>>제목</option>							
									<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }"/>>내용</option>
									<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : '' }"/>>작성자</option>
									<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : '' }"/>>제목+내용</option>
									<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : '' }"/>>제목+작성자</option>
									<option value="CW" <c:out value="${pageMaker.cri.type eq 'CW' ? 'selected' : '' }"/>>내용+작성자</option>
									<option value="TWC" <c:out value="${pageMaker.cri.type eq 'TCW' ? 'selected' : '' }"/>>제목+내용+작성자</option>
								</select>
	
								<input type="text" name="keyword" placeholder="Search for...">								
								<button class="btn btn-default" type="button">Search</button>														
							</form>
						</div>								
					</div>
				</div>
			
				<!-- 페이징-->
				<div class="row">		
					<div class="cols-lg-12" align="center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">		
								<li class="paginate_button previous" id="dataTable_previous">
									<a href="${pageMaker.startPage - 1}" class="page-link">◀</a>
								</li>
							</c:if>
							<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
								<li class='paginate_button ${pageMaker.cri.pageNum == num ? "active":""}'>
									<a href="${num}">${num}</a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next}">
								<li class="paginate_button next" id="dataTable_next">
									<a href="${pageMaker.endPage + 1}" class="page-link">▶</a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			
				<!-- page이동을 위한 pageMaker정보 가져오기 -->
				<form id="actionForm" action="/board/list" method="get">
					<!-- 페이지 유지 -->
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
					<!-- 검색조건 유지 -->
					<input type="hidden" name="type" value='<c:out value="${pageMaker.cri.type}"/>'>
					<input type="hidden" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"/>'>			
				</form>						
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel" style="position: left;">Modal Title</h4>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: right;">×</button>
			</div>
			<div class="modal-body">처리가 완료되었습니다.</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>							
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		var result = '<c:out value="${result}"/>';
				
		checkModal(result);
		
		/* 뒤로가기 문제해결 */
		history.replaceState({},null,null);		
		
		/* MODAL창 설정 */
		function checkModal(result) {
			if(result === "" || history.state){	return;	}			
			if(parseInt(result) > 0) {	$(".modal-body").html("게시글 "+parseInt(result)+" 번이 등록되었습니다.");  }			
			$("#myModal").modal("show");
		} 
		
		/* 등록버튼 기능 설정 : register페이지로 이동 */
		$("#regBtn").on("click", function(){
			self.location = "/board/register";
		});
		
		/* 페이징버튼 이벤트 */
		var actionForm = $("#actionForm");
		
		$(".paginate_button a").on("click", function(e){
			e.preventDefault();
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit();
		});
		
		/* 조회페이지로 이동 : 조회된 게시글의 url에 pageNum, amout정보를 입력*/
		$(".move").on("click", function(e){
			e.preventDefault();
			actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
			actionForm.attr("action", "/board/get");
			actionForm.submit();
		});
		
		/* 검색버튼 이벤트 처리 */
		var searchForm = $("#searchForm");
		
		$("#searchForm button").on("click", function(e){
			e.preventDefault();
			
			if(!searchForm.find("option:selected").val() && !$("input[name='keyword']").val()){
				window.location.href = "/board/list";
				return;
			} // 검색조건x + 검색어 미입력 > 전체 리스트 출력

			if(!searchForm.find("option:selected").val() && $("input[name='keyword']").val()){
				alert("검색조건을 선택하세요");		
				return;
			} // 검색조건x + 검색어 입력 > 경고
		
			if(!searchForm.find("input[name='keyword']").val()){
				alert("검색어를 입력하세요");
				return false;
			} // 검색어 미입력 경고
				
			searchForm.find("input[name='pageNum']").val("1"); // 검색시 무조건 1페이지로 이동			
			
			searchForm.submit();
		});
	});
</script>

<%@ include file="../includes/footer.jsp"%>