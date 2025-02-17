<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<title>Console.log(YH)_일반게시판</title>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i class="fa fa-edit fa-fw"></i>일반게시판</h1>
	</div>
	<p class="mb-4">
		º게시글 작성, 수정, 댓글서비스는 커뮤니티 회원에게만 제공됩니다.
	</p>
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				일반게시판 리스트
				<button type="button" id="regBtn" class="btn btn-primary btn-xs pull-right">글쓰기</button>
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
							<form id="searchForm" action="/board/list" method='get' style="display:flex;">
								<div>						
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
								</div>
								<div style="margin:0 5px;">
									<input id="keyword" name="keyword" placeholder="검색어를 입력해주세요" style="margin-bottom:15px;">
									<br> <sup class="essential" style="float:right;">'--' + 빈칸 '찾기' → 전체리스트</sup>
								</div>
								<div>
									<button class="btn btn-default" style="height:30px; padding: 0 12px;">찾기</button><br>												
								</div>													
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
				<h4 class="modal-title" id="myModalLabel" style="position: left;">알림</h4>
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
		// 검색 조건이 변경될 때 hidden input 값을 업데이트
	    $("#searchForm select[name='type']").change(function(){	        
	        $("#actionForm input[name='type']").val($(this).val());
	        if($(this).val() === ""){
	        	$("#keyword").val("");
	        }
	    });

	    // 검색 버튼 클릭 시 hidden input 값을 업데이트
	    $("#searchForm button").on("click", function(e){
	        e.preventDefault();
	        
	        var type = $("#searchForm select[name='type']").val();
	        var keyword = $("#searchForm input[name='keyword']").val();
	        
	        $("#actionForm input[name='type']").val(type);
	        $("#actionForm input[name='keyword']").val(keyword);

	        if (!type && !keyword) {
	            window.location.href = "/board/list";
	            return;
	        } // 검색 조건이 없고, 키워드가 비어 있으면 전체 리스트 출력
	        
	        if (!type && keyword) {
	            alert("검색 조건을 선택하세요");
	            return;
	        } // 검색 조건이 없고, 키워드가 입력되었을 때 경고
	        
	        if (!keyword) {
	            alert("검색어를 입력하세요");
	            return false;
	        } // 키워드가 입력되지 않았을 때 경고

	        $("#actionForm input[name='pageNum']").val("1"); // 검색 시 무조건 1페이지로 이동

	        $("#actionForm").submit();
	    });
	    	    		
		//검색 후 입력란 정보 유지
	    $(function(){	        
            var keyword = '${pageMaker.cri.keyword}';
            $('#keyword').val(keyword);
	    });
	});
</script>

<%@ include file="../includes/footer.jsp"%>