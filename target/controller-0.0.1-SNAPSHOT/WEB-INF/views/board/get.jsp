<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- 본문 페이지 설명란 -->
<h1 class="h3 mb-2 text-gray-800">Board Register</h1>
<p class="mb-4">
	현재 화면은 <c:out value="${board.bno}"/>번 게시글의 내용입니다.
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
			<div class="form-group">
				<label>No</label>
				<input class="form-control" name="bno" value="<c:out value="${board.bno}"/>" readonly="readonly">
			</div>			
			<div class="form-group">
				<label>Title</label>
				<input class="form-control" name="title" value="<c:out value="${board.title}"/>" readonly="readonly">
			</div>
			<div class="form-group">
				<label>Text Area</label>
				<textarea class="form-control" rows="10" name="content" readonly="readonly"><c:out value="${board.content}"/></textarea>
			</div>
			<div class="form-group">
				<label>Writer</label>
				<input class="form-control" name="writer" value="<c:out value="${board.writer}"/>" readonly="readonly">
			</div>
			
			<button data-oper="modify" class="btn btn-info2" style="border-color:#6b6d7d">Modify</button>
			<button data-oper="list" class="btn btn-info">List</button>
			
			<!-- Modify로 bno정보를 넘김으로써 수정/삭제시 modal창을 통해 게시글의 수정/삭제여부를 확인할수있게함. -->
			<form id="operForm" action="/board/modify" method="get">
				<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}"/>'>
				<!-- BoardController get()_get의 @ModelAttribute("cri")로 인해 수집된 정보 -->
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

<!-- reply.js -->
<script type="text/javascript" src="../resources/js/reply.js"></script>

<script type="text/javascript">
	$(document).ready(function(){		
		console.log("---------");
		console.log("-JS TEST-");
		
		var bnoVal = '<c:out value="${board.bno}"/>';
		
		console.log("현재 페이지 :"+bnoVal);
		/* add test
		replyService.add(
				{reply:"aaa", replyer:"replyer1", bno:bnoVal},
				
				function(result) {
					alert("RESULT : " + result);
				}
		); 
		*/
		
		/* get List */
		replyService.getList({bno:bnoVal, page:1}, 
			function(list){
				for(var i=0, len=list.length||0; i<len; i++){
					console.log(list[i]);
				}
			}
		);
	});	
</script>

<script>
	$(document).ready(function(){
				
		var operForm = $("#operForm");
		
		/* modify버튼 기능 구현*/
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		
		/* list버튼 기능 구현 */
		$("button[data-oper='list']").on("click", function(e){
			operForm.find("#bno").remove(); // 수정없이 list로 이동시 bno정보가 필요없으므로 삭제
			operForm.attr("action", "/board/list");
			operForm.submit();
		});
	});	
</script>

<%@ include file="../includes/footer.jsp"%>


	