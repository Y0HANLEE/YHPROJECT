<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

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
	<div id="board" class="col-lg-6">
		<div class="panel panel-success">
			<div class="panel-heading HpanelHeading">
				<span><i class="fa fa-edit fa-fw"></i>Board</span><a href="/board/list"><i class="fa fa-compass fa-fw"></i>더보기</a>			
			</div>			
			<div class="panel-body" style="padding:8px; max-height: 244px;">
				<table class="table table-striped table-bordered table-hover tableList">
					<thead>					
						<tr class="Htr">							
							<th class="HBbno">No</th>
							<th class="HBtitle">제목</th>
							<th class="HBwriter">글쓴이</th>														
							<th class="HBregdate">등록일</th>							
						</tr>
					</thead>
					<tbody>						
					<c:forEach items="${boardList}" var="board">						
						<tr class="Htr">							
							<td><c:out value="${board.bno}" /></td>
							<td class="HBtitle" style="padding: 8px 15px;"><a href='/board/get?bno=<c:out value="${board.bno}"/>'><c:out value="${board.title}" /></a></td>	
							<td><c:out value="${board.writer}" /></td>							
							<td><fmt:formatDate pattern="YY.MM.dd" value="${board.regdate}"/></td>						
						</tr>
					</c:forEach>					
					</tbody>
				</table>
			</div>			
		</div>		
	</div>
	<div id="album" class="col-lg-6">
		<div class="panel panel-info">
			<div class="panel-heading HpanelHeading">
				<span><i class="fa fa-camera-retro fa-fw"></i>Photo Album</span>
				<sec:authorize access="isAuthenticated()">
					<a href="/album/list"><i class="fa fa-compass fa-fw"></i>더보기</a>
	   			</sec:authorize>
				<sec:authorize access="isAnonymous()">
	   				<a href="/user/join"><i class="fa fa-user fa-fw"></i>회원가입하러가기</a>
				</sec:authorize>
			</div>			
			<div class="panel-body" style="padding:8px; max-height: 244px;">
				<table class="table table-striped table-bordered table-hover tableList">
						<thead>
							<tr class="Htr">
								<th class="HAano">No</th>							
								<th class="HAthumb">사진</th>
								<th class="HAtitle">제목</th>								
								<th class="HAwriter">글쓴이</th>								
								<th class="HAregdate">등록일</th>
							</tr>
						</thead>
						<tbody>					 
						<c:forEach items="${albumList}" var="album">
							<tr>
								<td class="HAano"><c:out value="${album.ano}"/></td>
								<td class="HAthumb" style="padding:1px" data-ano="${album.ano}"><!-- thumbnail --></td>
								<td class="HAtitle" style="padding: 8px 15px;"><c:out value="${album.title}" /></td>
								<td class="HAwriter"><c:out value="${album.writer}" /></td>								
								<td class="HAregdate"><fmt:formatDate pattern="YY.MM.dd" value="${album.regdate}" /></td>																				
							</tr>
						</c:forEach>					
						</tbody>
					</table>
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
	
	/* 썸네일 출력 */
	$(".HAthumb").each(function() {
	    var ano = $(this).data('ano');
	    var thumbnail = $(this);

	    $.getJSON("/album/getAttachList", {ano: ano}, function(attach) {
	        var str = "";

	        if (attach && attach.length > 0 && attach[0].fileType) { //첨부파일이 있고, 첫번째 첨부파일이 사진인 경우
	            var fileCallPath = encodeURIComponent(attach[0].uploadPath + "/s_" + attach[0].uuid + "_" + attach[0].fileName);
	            str += "<div data-path='"+attach[0].uploadPath+"' data-uuid='"+attach[0].uuid+"' data-filename='"+attach[0].fileName+"' data-type='"+attach[0].fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
	            str += "<img src='/display?fileName=" + fileCallPath + "'></div>"; //있으면 출력
	        } else {
	            str += "<div><img src='/resources/img/Default-Thumbnail.png' width='30px' height='30px'></div>"; //그 외엔 기본썸네일
	            //str += "<div> - </div>"; //그 외엔 기본썸네일
	        }
	        thumbnail.html(str);
	    });
	});
	
	/* 썸네일 클릭시 이벤트 처리 */
	$(".HAthumb").on("click", "div", function(e){
		var divObj = $(this);
		var path = encodeURIComponent(divObj.data("path")+"/"+divObj.data("uuid")+"_"+divObj.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명

		if(divObj.data("type")){
			showImage(path.replace(new RegExp(/\\/g),"/")); // 이미지파일 : showImage함수 실행
		}
	});

	// 원본사진 확대보기 on
	function showImage(fileCallPath){			
		$(".picWrap").css("display","flex").show(); // none > flex 설정 변경
		$(".pic").html("<img src='/display?fileName="+fileCallPath+"'>").animate({width:'100%', height:'100%'}, 0);//pic의 html속성은 controller의 display메서드, animate는 크기변경(배경창 100%*100%) 0.3초 후 실행		
	}
	
	// 원본사진 확대보기 off 
	$(".picWrap").on("click", function(e){
		$(".pic").animate({width:'0%', height:'0%'}, 0); // (0%*0%) 로 0초 후 크기변경
		setTimeout(() => {$(this).hide();}, 0);	// chrome의 ES6화살표함수
		//IE : setTimeout(function(){$('.picWrap').hide();}, 300);
	});
});
</script>

<%@ include file="../includes/footer.jsp"%>