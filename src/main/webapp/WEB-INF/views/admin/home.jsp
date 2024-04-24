<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<title>메인페이지</title>

<!-- 페이지 설명 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header">HomePage</h1>
	</div>
	<p class="mb-4">2024 YH 홈페이지제작 개인프로젝트 홈화면입니다.</p>
    <p class="mb-4">현재 시각 : ${serverTime}</p>    			
</div>

<!-- 게시판 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">게시판이 궁금하시다면</div>			
			<div class="panel-body">		    
    			<a href="/board/list">Click here</a> to go to Board page.
			</div>
		</div>
	</div>
</div>

<!-- 앨범 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">사진게시판이 궁금하시다면</div>
			<div class="panel-body">			    
				<sec:authorize access="isAnonymous()">
    				<a href="/user/join">Click here</a> to go to Join our Homepage.
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">							    
    				<a href="/album/list">Click here</a> to go to Album page.
    			</sec:authorize>
			</div>
		</div>			
	</div>
</div>

<!-- 회원가입 -->
<sec:authorize access="isAnonymous()">
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">회원가입이 하고싶으시다면</div>
			<div class="panel-body">			    
    			<a href="/user/join">Click here</a> to go to Join page.
			</div>
		</div>
	</div>
</div>
</sec:authorize>

<!-- 소개 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">누구냐고 물으신다면</div>
			<div class="panel-body">			    
    			<a href="/main/intro">대답</a>해드리는게 인지상정!
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">알림</h4>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: right;">×</button>
			</div>
			<div class="modal-body"><!-- msg --></div>
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
		if(result === "" || history.state){ 
			return;
		}
		$(".modal-body").html(result);		
		$("#myModal").modal("show");
		setTimeout(function() { $("#myModal").modal("hide"); }, 1000);
	} 
});
</script>

<%@ include file="../includes/footer.jsp"%>