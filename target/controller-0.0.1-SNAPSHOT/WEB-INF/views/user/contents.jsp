<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="../resources/js/album_reply.js"></script>
<title>내가 작성한 게시글</title>
<%@ include file="../includes/header.jsp"%>
<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i class="fa fa-th-list fa-fw"></i> My Contents</h1>
	</div>	
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div id="myContent" class="panel-heading">	
				<div>					
					<button id="boardBtn" class="btn btn-info">일반게시판</button>
					<button id="albumBtn" class="btn btn-default">사진게시판</button>
				</div>			
				<!-- 검색창 
				<div class="row">		
					<div class="col-lg-12">	
						<div class="pull-right">
							<form id="searchForm" action="/user/contents?boardType=1" method='get'>							
								<select name="type">
								    <option value=""  ${boardPage.cri.type == null ? 'selected' : ''}>--</option>
								    <option value="T" ${boardPage.cri.type eq 'T' ? 'selected' : ''}>제목</option>
								    <option value="C" ${boardPage.cri.type eq 'C' ? 'selected' : ''}>내용</option>
								</select>
								<input name="keyword" placeholder="Search for...">
								<input type="hidden" name="userid" value="${userid}">
								<input type="hidden" name="boardType" value="1">							
								<button id="searchBtn" class="btn btn-default">Search</button>																				
							</form>
						</div>								
					</div>
				</div>	 -->		
			</div>
			<!-- 본문 및 부가기능 -->
			<div class="panel-body">
				<!-- 본문 -->
				<table id="myConTable" class="table table-striped table-bordered table-hover tableList">
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
					<c:forEach items="${board}" var="board">
					    <tr class="listTr">
					        <td>일반<br>게시판</td>
					        <td><c:out value="${board.bno}"/></td>                            
					        <td class="myContents">
					            <div class="contents">
					                <div class="a">
					                    <div class="a-1">	
					                        <h3><a class="getBoard" href="/board/get?bno=${board.bno}"><i class="fa fa-folder fa-fw"></i><c:out value="${board.title}" /></a></h3>					                        							
					                    </div>                               
					                </div>
					                <div class="b"> 
					                    <div class="b-1"><i class="fa fa-user fa-fw"></i>@<c:out value="${board.writer}" /></div>
					                    <div class="b-2"><i class="fa fa-comments fa-fw"></i><c:out value="${board.replyCnt}"/></div>
					                    <div class="b-3"><i class="fa fa-arrow-circle-o-up fa-fw"></i><c:out value="${board.hit}" /><br></div>
					                </div>
					            </div>
					        </td>                            
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${board.regdate}"/><br><fmt:formatDate pattern="hh:mm" value="${board.regdate}" /></td>                                                                                
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${board.updateDate}"/><br><fmt:formatDate pattern="hh:mm" value="${board.updateDate}" /></td>
					    </tr>
					</c:forEach>															
					</tbody>	
					<tbody id="albumList">
					<c:forEach items="${album}" var="album">
					    <tr class="listTr">
					        <td>사진<br>게시판</td>
					        <td><c:out value="${album.ano}"/></td>                            
					        <td class="myContents">
					            <div class="contents">
					                <div class="a album">
					                    <div class="a-1">	
					                        <h3><a class="getAlbum" href="/album/get?ano=${album.ano}"><i class="fa fa-folder fa-fw"></i><c:out value="${album.title}" /></a></h3>
					                    </div>
					                    <div class="a-2">
					                    	<div class="a-2-1">
					                    		<c:choose>
												    <c:when test="${empty album.location}"><i class="fa fa-map-marker fa-fw"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</c:when>
												    <c:otherwise><i class="fa fa-map-marker fa-fw"></i><c:out value="${album.location}" /></c:otherwise>
												</c:choose>
					                    	</div>
					                    	<div class="a-2-2">
					                    		<i class="fa fa-calendar fa-fw"></i><fmt:formatDate value="${album.startDate}" pattern="YY/MM/dd"/> - <fmt:formatDate value="${album.endDate}" pattern="YY/MM/dd"/>
					                    	</div>
					                    </div>                               
					                </div>
					                <div class="b"> 
					                    <div class="b-1"><i class="fa fa-user fa-fw"></i>@<c:out value="${album.writer}" /></div>
					                    <div class="b-2"><i class="fa fa-comments fa-fw"></i><c:out value="${album.replyCnt}"/></div>
					                    <div class="b-3"><i class="fa fa-arrow-circle-o-up fa-fw"></i><c:out value="${album.hit}" /><br></div>
					                </div>
					            </div>
					        </td>                            
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${album.regdate}"/><br><fmt:formatDate pattern="hh:mm" value="${album.regdate}" /></td>                                                                                
					        <td><fmt:formatDate pattern="YY/MM/dd" value="${album.updatedate}"/><br><fmt:formatDate pattern="hh:mm" value="${album.updatedate}" /></td>
					    </tr>
					</c:forEach>															
					</tbody>				
				</table>			
			</div>
			<form id="actionForm" action="/user/contents" method="get">
				<input type="hidden" name="type" value="${boardPage.cri.type}">
				<input type="hidden" name="keyword" value="${boardPage.cri.keyword}">
				<input type="hidden" name="userid" value="${boardPage.cri.userid}">
				<input type="hidden" name="boardType" value="${boardPage.cri.boardType}">				
			</form>
		</div>
	</div>
</div>
<!-- 이동용 화살표 -->
<a href="#" class="btn-nav-arrow up" style="position:fixed; top:45%; right:1%;" title="25%↑"><i class="fa fa-arrow-up"></i></a>
<a href="#" class="btn-nav-arrow down" style="position:fixed; top:55%; right:1%;" title="25%↓"><i class="fa fa-arrow-down"></i></a>

<script>
$(document).ready(function(){
	var actionForm = $("#actionForm");
	var board = $("#boardList");		
	var album = $("#albumList");
	
	/* board버튼 - 일반게시판 조회 */
	$("#boardBtn").on("click", function(e){
		e.preventDefault();
		var userid = "<c:out value='${userid}'/>";
		var type = $("select[name='type']").val();
		var keyword = $("input[name='keyword']").val();
	    $("#actionForm").html(
			'<input type="hidden" name="type" value='+type+'>'+
			'<input type="hidden" name="keyword" value='+keyword+'>'+
			'<input type="hidden" name="userid" value='+userid+'>'+
    		'<input type="hidden" name="boardType" value="1">'
	    ).submit();
	    $(this).attr("class", "btn btn-info");		    
	    $("#albumBtn").attr("class", "btn btn-default");		   
	    album.hide();
		board.show();
		/*
		$("#searchForm").attr("action", "/user/contents?boardType=1");
	    $("select[name='type']").html(		    		
    		'<option value=""  ${boardPage.cri.type == null ? "selected" : ""}>--</option>' +						
	        '<option value="T" ${boardPage.cri.type eq "T" ? "selected" : ""}>제목</option>' +						
	        '<option value="C" ${boardPage.cri.type eq "C" ? "selected" : ""}>내용</option>' 			        
	    );	  
	   
	    $("#searchForm").find("input[name='boardType']").val("1");
	    */
	});

	/* album버튼 - 사진게시판 조회 */
	$("#albumBtn").on("click", function(e){
		e.preventDefault();
		var userid = "<c:out value='${userid}'/>";
		var type = $("select[name='type']").val();
		var keyword = $("input[name='keyword']").val();
	    $("#actionForm").html(
			'<input type="hidden" name="type" value='+type+'>'+
			'<input type="hidden" name="keyword" value='+keyword+'>'+
			'<input type="hidden" name="userid" value='+userid+'>'+
    		'<input type="hidden" name="boardType" value="2">'
	    ).submit();
	    
	    $(this).attr("class", "btn btn-info");		    
	    $("#boardBtn").attr("class", "btn btn-default");			
	    board.hide();		    
		album.show();
		/*			
		$("#searchForm").attr("action", "/user/contents?boardType=2");
	    $("select[name='type']").html(
	        '<option value=""  ${albumPage.cri.type == null ? "selected" : ""}>--</option>' +						
	        '<option value="T" ${albumPage.cri.type eq "T" ? "selected" : ""}>제목</option>' +						
	        '<option value="C" ${albumPage.cri.type eq "C" ? "selected" : ""}>내용</option>' +
	        '<option value="L" ${albumPage.cri.type eq "L" ? "selected" : ""}>여행지</option>'
	    );
	    $("#searchForm").find("input[name='boardType']").val("2");
	    */
	});

	// 화살표 클릭시 스크롤 이벤트 처리
	$('.btn-nav-arrow.up').click(function(e) {
		e.preventDefault();
		scroll(-25);
	});
	
	$('.btn-nav-arrow.down').click(function(e) {
		e.preventDefault();
		scroll(25);
	});

	function scroll(percentage) {		
		var windowHeight = $(window).height(); //페이지높이		
		var scrollAmount = (windowHeight * percentage) / 100; //스크롤시 내릴 퍼센티지		
		var currentScroll = $(window).scrollTop(); //현재스크롤 위치
		$('html, body').animate({ scrollTop: currentScroll + scrollAmount}, 500);// 스크롤 이동
	}
	
	/* 검색버튼 이벤트 처리 
	var searchForm = $("#searchForm");
	var userid = "<c:out value='${userid}'/>";
	var boardType = $("input[name='boardType']").val();
	
	$("#searchBtn").on("click", function(e){
		e.preventDefault();
		alert("click");
		console.log("click");
		
		if(!searchForm.find("option:selected").val() && !$("input[name='keyword']").val()){
			window.location.href = "/user/contents?userid="+userid+"&boardType="+boardType;
			return;
		}//검색조건x + 빈칸 : 전체목록
		
		if(searchForm.find("select[name='type']").val() == "" && $("input[name='keyword']").val() == ""){
		    alert("검색조건을 선택하세요");		
		    return;
		}
		
		if($("input[name='keyword']").val() == ""){
		    alert("검색어를 입력하세요");
		    return false;
		}
		
		searchForm.submit();
	});*/
});
</script>

<%@ include file="../includes/footer.jsp"%>
