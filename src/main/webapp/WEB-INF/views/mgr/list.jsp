<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<title>회원관리</title>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i class="fa fa-user fa-fw"></i>회원목록</h1>
	</div>
	<p class="mb-4">
		<sec:authentication property="principal" var="id"/>
		º안녕하세요 <b>@<c:out value="${id.username}"/></b> 운영자님, Console.log(YH) 커뮤니티 회원목록입니다.
	</p>
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px;">
		<div class="panel panel-default">
			<div class="panel-heading">
				Console.log(YH) 회원목록
				<!-- <button type="button" id="regBtn" class="btn btn-primary btn-xs pull-right">register</button> -->
			</div>
			<!-- 본문 및 부가기능 -->
			<div class="panel-body">
				<!-- 본문 -->
				<!-- <table width="100%"	class="table table-striped table-bordered table-hover" id="dataTables-example"> sort기능 -->
				<table class="table table-striped table-bordered table-hover" style="text-align:center; justify-content: center;">
					<thead>					
						<tr>							
							<th style="text-align:center; width:5%">No</th>							
							<th style="text-align:center; width:8%">아이디</th>
							<th style="text-align:center; width:7%">이름</th>
							<th style="text-align:center; width:5%">성별</th>
							<th style="text-align:center; width:10%">생일</th>
							<th style="text-align:center; width:15%">연락처</th>
							<th style="text-align:center; width:35%">주소</th>							
							<th style="text-align:center; width:10%">가입일</th>							
						</tr>
					</thead>
					<tbody>					 
					<c:forEach items="${list}" var="user">
						<tr style="vertical-align: middle; height:20px;">
							<td><c:out value="${user.uno}"/></td>
							<td><c:out value="${user.userid}" /></td>							
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.gender == 'M'?'M':'F'}" /></td>
							<td><fmt:formatDate pattern="yyyy.MM.dd" value="${user.birth}"/></td>							
							<td><c:out value="${user.phone}"/></td>
							<td>(<c:out value="${user.zonecode}"/>)<c:out value="${user.address}"/><c:out value="${user.addressDetail}"/></td>							
							<td><fmt:formatDate pattern="YY/MM/dd" value="${user.regdate}" /></td>
						</tr>
					</c:forEach>					
					</tbody>
				</table>
				
				<form id="userForm" method="post">
					<input type="hidden" name="userid" value="${user.userid}">
					<input type="hidden" name="auth" value="${user.authList[0].auth}">					
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				</form>
				
				<!-- 검색창 -->
				<div class="row">		
					<div class="col-lg-12">	
						<div class="pull-right">
							<form id="searchForm" method='get' action="/mgr/list" style="display:flex;">
								<div>
									<select name="type">
										<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }"/>>--</option>						
										<option value="I" <c:out value="${pageMaker.cri.type eq 'I' ? 'selected' : '' }"/>>아이디</option>							
										<option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }"/>>이름</option>
										<option value="G" <c:out value="${pageMaker.cri.type eq 'G' ? 'selected' : '' }"/>>성별</option>
										<option value="B" <c:out value="${pageMaker.cri.type eq 'B' ? 'selected' : '' }"/>>생일</option>
										<option value="P" <c:out value="${pageMaker.cri.type eq 'P' ? 'selected' : '' }"/>>연락처</option>
										<option value="A" <c:out value="${pageMaker.cri.type eq 'A' ? 'selected' : '' }"/>>주소</option>
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
			
				<!-- 페이징 -->
				<div class="row">					
					<div class="col-lg-12" align="center" style="height:60px">						
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
				<form id="actionForm" action="/mgr/list" method="get">
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
		resultModal(result);
		
		function resultModal(result){
			//뒤로가기 문제
			if (result === '' || history.state) {
				return;
			}
			
			if(result != null){				
				$(".modal-body").html(result);
				$("#myModal").modal("show");
			} else {
				$(".modal-body").html("작업에 실패하였습니다. 다시 시도해주십시오");
				$("#myModal").modal("show");
			}
		}		
		
		/* MODAL창 설정 */
		function checkModal(result) {			
			$("#myModal").modal("show");
		} 
			
		/* 페이징버튼 이벤트 */
		var actionForm = $("#actionForm");
		
		$(".paginate_button a").on("click", function(e){
			e.preventDefault();
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
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
	            window.location.href = "/mgr/list";
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