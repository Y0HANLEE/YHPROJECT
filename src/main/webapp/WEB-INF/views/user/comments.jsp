<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<script type="text/javascript" src="../resources/js/album_reply.js"></script>
<title>내가 작성한 댓글</title>
<style>

</style>
<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i class="fa fa-comments fa-fw"></i> My Comments</h1>
	</div>	
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading"  style="display: flex; justify-content: space-between;">		
				<div>
					<button type="button" id="boardBtn" class="btn btn-info" value="Y">일반게시판</button>				
					<button type="button" id="albumBtn" class="btn btn-default" value="N">사진게시판</button>
				</div>		
				<!-- 검색창 --> 
				<div class="row">		
					<div class="col-lg-12">	
						<div class="pull-right">
							<form id="searchForm" action="/user/contents?boardType=1.1" method='get'>							
								<select name="type">
								    <option value=""  ${boardReplyPage.cri.type == null ? 'selected' : ''}>전체</option>
								    <option value="R" ${boardReplyPage.cri.type eq 'R' ? 'selected' : ''}>내용</option>							    
								</select>
								<input type="text" name="keyword" placeholder="Search for...">
								<input type="hidden" name="userid" value="${userid}">
								<input type="hidden" name="boardType" value="1.1">							
								<button class="btn btn-default" type="button" id="searchBtn">Search</button>																				
							</form>
						</div>								
					</div>
				</div>
			</div>
				
			<!-- 본문 및 부가기능 -->
			<div class="panel-body">
				<!-- 본문 -->
				<table class="table table-striped table-bordered table-hover tableList" id="myConTable">
					<thead>
						<tr>
							<th class="con-type">구분</th>
							<th class="con-num">글번호</th>							
							<th class="con-content">내용</th>							
							<th class="con-reg">등록일</th>
							<th class="con-update">수정일</th>
						</tr>
					</thead>
					
					<tbody id="boardList">
					<c:forEach items="${boardReply}" var="board">
					    <tr class="listTr">
					        <td>일반<br>게시판</td>
					        <td><a href='/board/get?bno=<c:out value="${board.bno}"/>'><c:out value="${board.bno}"/></a></td>                            
					        <td class="myComments">					            
				                <div style="height:90%"><h5><i class="fa fa-wechat fa-fw"></i><c:out value="${board.reply}" /></h5></div>
				                <hr style="margin:0px">				                
				                <div style="height:10%"><i class="fa fa-user fa-fw"></i>@<c:out value="${board.replyer}" /></div>					                
					        </td>                            
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${board.replyDate}"/><br><fmt:formatDate pattern="hh:mm" value="${board.replyDate}" /></td>                                                                                
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${board.updateDate}"/><br><fmt:formatDate pattern="hh:mm" value="${board.updateDate}" /></td>
					    </tr>
					</c:forEach>															
					</tbody>
					
					<tbody id="albumList">
					<c:forEach items="${albumReply}" var="album">
					    <tr class="listTr">
					        <td>사진<br>게시판</td>
					        <td><a href='/album/get?ano=<c:out value="${album.ano}"/>'><c:out value="${album.ano}"/></a></td>                            
					        <td class="myComments">					            
				                <div style="height:90%"><h5><i class="fa fa-wechat fa-fw"></i><c:out value="${album.reply}" /></h5></div>
				                <hr style="margin:0px">				                
				                <div style="height:10%"><i class="fa fa-user fa-fw"></i>@<c:out value="${album.replyer}" /></div>					                
					        </td>                            
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${album.replyDate}"/><br><fmt:formatDate pattern="hh:mm" value="${album.replyDate}" /></td>                                                                                
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${album.updateDate}"/><br><fmt:formatDate pattern="hh:mm" value="${album.updateDate}" /></td>
					    </tr>					
					</c:forEach>															
					</tbody>
				</table>		
			</div>
		
			<!-- page이동을 위한 ContentsPage정보 가져오기-->
			<form id="actionForm" action="/user/comments" method="get">					
				<input type="hidden" name="pageNum" value="${boardReplyPage.cri.pageNum}">
				<input type="hidden" name="amount" value="${boardReplyPage.cri.amount}">					
				<input type="hidden" name="type" value='<c:out value="${boardReplyPage.cri.type}"/>'>
				<input type="hidden" name="keyword" value='<c:out value="${boardReplyPage.cri.keyword}"/>'>
			</form>						
		</div>		
	</div>
</div>

<script>
	$(document).ready(function(){
		var board = $("#boardList");
		var album = $("#albumList");
		
		//album.hide();
		
		/* board버튼 */
		$("#boardBtn").on("click", function(e){		    		
			e.preventDefault();
			var userid = "<c:out value='${userid}'/>";
			var type = $("select[name='type']").val();
			var keyword = $("input[name='keyword']").val();
		    
			$("#actionForm").html(
				'<input type="hidden" name="type" value='+type+'>'+
				'<input type="hidden" name="keyword" value='+keyword+'>'+
				'<input type="hidden" name="userid" value='+userid+'>'+
	    		'<input type="hidden" name="boardType" value="1.1">'
		    ).submit();
		    
		    $(this).attr("class", "btn btn-info");		    
		    $("#albumBtn").attr("class", "btn btn-default");		   
		    album.hide();
			board.show();
			
			$("#searchForm").attr("action", "/user/contents?boardType=1.1");
		    $("#searchForm").find("input[name='boardType']").val("1.1");
		});

		/* album버튼 */
		$("#albumBtn").on("click", function(e){
			e.preventDefault();
			
			var userid = "<c:out value='${userid}'/>";
			var type = $("select[name='type']").val();
			var keyword = $("input[name='keyword']").val();
		    
			$("#actionForm").html(
				'<input type="hidden" name="type" value='+type+'>'+
				'<input type="hidden" name="keyword" value='+keyword+'>'+
				'<input type="hidden" name="userid" value='+userid+'>'+
	    		'<input type="hidden" name="boardType" value="2.1">'
		    ).submit();
		    
		    $(this).attr("class", "btn btn-info");		    
		    $("#boardBtn").attr("class", "btn btn-default");			
		    board.hide();		    
			album.show();
						
			$("#searchForm").attr("action", "/user/contents?boardType=2.1");		    
		    $("#searchForm").find("input[name='boardType']").val("2.1"); 
		});
		
		/* 검색버튼 이벤트 처리 */
		var searchForm = $("#searchForm");
		var userid = "<c:out value='${userid}'/>";
		var boardType = $("input[name='boardType']").val();
		
		$("#searchForm button").on("click", function(e){
			e.preventDefault();
			
			if(!searchForm.find("option:selected").val() && !$("input[name='keyword']").val()){
				window.location.href = "/user/contents?userid="+userid+"boardType="+boardType;
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
		
			searchForm.submit();
		});


	});
</script>

<%@ include file="../includes/footer.jsp"%>