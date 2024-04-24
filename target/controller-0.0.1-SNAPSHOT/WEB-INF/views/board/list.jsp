<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- 본문 페이지 설명란 -->
<h1 class="h3 mb-2 text-gray-800">Board List</h1>
<p class="mb-4">
	DataTables is a third party plugin that is used to generate the demo
	table below. For more information about DataTables, please visit the <a
		target="_blank" href="https://datatables.net">official DataTables
		documentation</a>.
</p>

<!-- 본문내용(표) 시작 -->
<div class="card shadow mb-4">
	<!-- LIST 제목 시작 -->
	<div class="card-header py-3 d-flex justify-content-between align-items-center">
		<h4 class="m-0 font-weight-bold text-primary" style="position: left;">DataTables Example</h4>
		<button type="button" id="regBtn" class="btn btn-primary" style="position: right;">register</button>		
	</div>
	<!-- LIST 제목 끝 -->
	<!-- LIST 시작 -->
	<div class="card-body">
		<div class="table-responsive">
			<!-- list -->
			<div class="row">
			<table class="table table-bordered" id="dataTable" width="100%"	cellspacing="0">
				<thead>
					<tr>
						<th>No</th>
						<th>제목</th>
						<th>내용</th>
						<th>글쓴이</th>
						<th>등록일</th>
						
					</tr>
				</thead>
				<c:forEach items="${list}" var="board">
					<tr>
						<td><c:out value="${board.bno}" /></td>
						<td><a class="move" href='<c:out value="${board.bno}"/>'><c:out value="${board.title}" /></a></td>
						<td><c:out value="${board.content}" /></td>
						<td><c:out value="${board.writer}" /></td>
						<td><fmt:formatDate pattern="YY/MM/dd" value="${board.regdate}" /></td>						
					</tr>
				</c:forEach>
			</table>	
			</div>		
			<!-- 검색창 -->
			<div class="row">				
				<form id="searchForm" action="/board/list" method="get" class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
					<div class="input-group">
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
						<input type="text" name="keyword" class="form-control bg-light border-0 small"	placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
						<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum}"/>' />
						<input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount}"/>' />
						<div class="input-group-append">
							<button class="btn btn-primary" type="button"> <i class="fas fa-search fa-sm"></i> </button>
						</div>
					</div>
				</form>
			</div>
			<!-- 페이징 -->
			<div class="row">
				<div class="col-sm-12 col-md-5">
					<!-- 나중에 처리 -->
					<div class="dataTables_info" id="dataTable_info" role="status" aria-live="polite">
						Showing <c:out value='${pageMaker.startPage}'/> to  <c:out value='${pageMaker.endPage}'/> of  <c:out value='${pageMaker.cri.amount}'/> entries
					</div>
				</div>
				<div class="col-sm-12 col-md-7">
					<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">		
								<li class="paginate_button page-item previous disabled" id="dataTable_previous">
									<a href="${pageMaker.startPage - 1}" class="page-link">◀</a>
								</li>
							</c:if>
							<c:forEach var="page" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
								<li class='paginate_button page-item ${pageMake.cri.pageNum == page ? "active":""}'>
									<a href="${page}" class="page-link">${page}</a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next}">
								<li class="paginate_button page-item next disabled" id="dataTable_next">
									<a href="${pageMaker.endPage + 1}" class="page-link">▶</a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
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
		
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel" style="position: left;">Modal Title</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: right;">&times</button>
					</div>
					<div class="modal-body">처리가 완료되었습니다.</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary">Save Changes</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- LIST 끝 -->
</div>
<!-- 본문내용(표) 끝 -->

<script>
	$(document).ready(function(){
		var result = '<c:out value="${result}" />';
		
		/* 뒤로가기 문제해결 */
		history.replaceState({},null,null);

		checkModal(result);
				
		/* MODAL창 설정 */
		function checkModal(result) {
			if(result === ''){ // || history.state : modal창이 뜨질 않는 문제발생
				return;
			}
			
			if(parseInt(result) > 0) {
				$(".modal-body").html("게시글 "+parseInt(result)+" 번이 등록되었습니다.");
			}
			
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
			
			if(!searchForm.find("option:selected").val()){
				alert("검색종류를 선택하세요");
				return false;
			} // 검색종류 미선택 경고
			
			if(!searchForm.find("input[name='keyword']").val()){
				alert("검색어를 입력하세요");
				return false;
			} // 검색어 미입력 경고
				
			searchForm.find("input[name='pageNum']").val("1"); // 검색시 무조건 1페이지로 이동
			e.preventDefault();
			
			searchForm.submit();
		});
	});
</script>


<%@ include file="../includes/footer.jsp"%>