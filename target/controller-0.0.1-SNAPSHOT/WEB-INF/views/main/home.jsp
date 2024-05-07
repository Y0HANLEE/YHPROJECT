<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="../includes/header.jsp"%>
<title>메인페이지</title>

	<!-- 페이지 설명 -->
	<div class="row">
		<div class="col-lg-12">
			<div>
				<h1 class="page-header">${home.title_title}</h1>
			</div>
		    <p class="pull-right"><a href="#board"><i class="fa fa-edit fa-fw"></i>게시판</a>  <a href="#map" style="margin-left: 20px"><i class="fa fa-map-marker fa-fw"></i>지도</a></p>    			
			<p class="mb-4">${home.title_intro}</p>
		</div>
	</div>	
	<!-- 사진 슬라이드 -->
	<div class="row">
	    <div class="col-lg-12">
	        <!-- 사진 슬라이드 -->	
			<div class="slider" style="margin: 50px 0 0 50px; width:88.3%">
				<div class="slides">		
					<!-- upload photo -->
				</div>
				<button id="prevBtn" class="button prev"><i class="fa fa-chevron-left fa-fw"></i></button>
				<button id="nextBtn" class="button next"><i class="fa fa-chevron-right fa-fw"></i></button>
			</div>
	    </div>
	</div>
	<!-- 게시판 -->
	<h3 style="margin: 70px 0 10px 0;">최근 게시글</h3>	
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
									<td class="HBtitle" style="padding: 8px 15px;"><c:out value="${board.title}" /></td>	
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
	<!-- 댓글 -->
	<h3 style="margin:30px 0 10px 0;">최근 댓글</h3>	
	<div class="row">	
		<div id="boardReply" class="col-lg-6">
			<div class="panel panel-warning">
				<div class="panel-heading HpanelHeading">
					<span><i class="fa fa-wechat fa-fw"></i>Board Reply (No:게시글번호)</span><a href="/board/list"><i class="fa fa-compass fa-fw"></i>더보기</a>			
				</div>			
				<div class="panel-body" style="padding:8px; max-height: 244px;">
					<table class="table table-striped table-bordered table-hover tableList">
						<thead>					
							<tr class="Htr">							
								<th class="HBbno">No</th>
								<th class="HBtitle">댓글</th>
								<th class="HBwriter">글쓴이</th>														
								<th class="HBregdate">등록일</th>							
							</tr>
						</thead>
						<tbody>						
							<c:forEach items="${boardReplyList}" var="boardReply">						
								<tr class="Htr">							
									<td><c:out value="${boardReply.bno}" /></td>									
									<td class="HBtitle" style="padding: 8px 15px;"><c:out value="${boardReply.reply}" /></td>	
									<td><c:out value="${boardReply.replyer}" /></td>							
									<td><fmt:formatDate pattern="YY.MM.dd" value="${boardReply.replyDate}"/></td>						
								</tr>
							</c:forEach>					
						</tbody>
					</table>
				</div>			
			</div>		
		</div>
		<div id="albumReply" class="col-lg-6">
			<div class="panel panel-danger">
				<div class="panel-heading HpanelHeading">
					<span><i class="fa fa-wechat fa-fw"></i>Photo Album Reply (No:게시글번호)</span>
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
								<th class="HBbno">No</th>
								<th class="HBtitle">댓글</th>
								<th class="HBwriter">글쓴이</th>														
								<th class="HBregdate">등록일</th>							
							</tr>
						</thead>
						<tbody>						
							<c:forEach items="${albumReplyList}" var="albumReply">						
								<tr class="Htr">							
									<td><c:out value="${albumReply.ano}"/></td>
									<td class="HBtitle" style="padding: 8px 15px;"><c:out value="${albumReply.reply}" /></td>	
									<td><c:out value="${albumReply.replyer}" /></td>							
									<td><fmt:formatDate pattern="YY.MM.dd" value="${albumReply.replyDate}"/></td>						
								</tr>
							</c:forEach>					
						</tbody>
					</table>
				</div>			
			</div>		
		</div>
	</div>
	<!-- 지도 -->
	<div class="col-lg-12" style="padding: 0">	
		<div class="form-group" style="margin: 80px 0 5px 0; width: 100%; display: flex; align-items: center;">
			<label><i class="fa fa-bookmark fa-fw"></i><c:out value="${home.map_title}"></c:out>&nbsp;_&nbsp;<i class="fa fa-map-marker fa-fw"></i>address: <c:out value="${home.map_address}"/>. <c:out value="${home.map_addressdetail}"/></label>					 
		</div>	
		<div id="map" style="border-radius:5px;  width:100%; height:350px; margin-bottom: 10px;"></div>
		- <c:out value="${home.map_intro}"/>
	</div>
	<!-- 소개 -->
	<div class="row">
		<div class="col-lg-12" style="margin-top: 100px;">
			<div class="panel panel-default">
				<div class="panel-heading">누구냐고 물으신다면</div>
				<div class="panel-body">			    
	    			<a href="/main/intro">대답</a>해드리는게 인지상정!
				</div>
			</div>
		</div>
		<div class="col-lg-12" align="center" style="margin: 50px 0 30px 0; color: gray; font-size: 14px;">
			${home.intro}
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

<!-- 이동용 화살표 -->
<a href="#top" class="btn-nav-arrow up" style="position:fixed; bottom:80px; right:10px;"><i class="fa fa-arrow-up"></i></a>
<a href="#bottom" class="btn-nav-arrow down" style="position:fixed; bottom:30px; right:10px;"><i class="fa fa-arrow-down"></i></a>

<!-- KAKAO MAP -->
<script>	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
		mapCenter = new kakao.maps.LatLng(37.5034138, 126.7660309),//(위도, 경도)
    	mapOption = { center: mapCenter, level: 3 }; // 위치, 확대(1(대)~14(소))  
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 객체	
	var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
	
	
	var inputAddress = $('#address_kakao').val();
	var inputPlace = $("input[name='map_caption']").val();
	var mapCaption = '<c:out value="${intro.map_caption}"/>',
		mapAddress = '<c:out value="${intro.map_address}"/>';			

	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(mapAddress, function(result, status) {	     
		if (status === kakao.maps.services.Status.OK) { // 정상적으로 검색이 완료됐으면				
			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);			
			var marker = new kakao.maps.Marker({ map: map, position: coords });// 결과값으로 받은 위치를 마커로 표시합니다		
			if(mapCaption != null){
				var infowindow = new kakao.maps.InfoWindow({ content: '<div style="width:150px;text-align:center;padding:6px 0;">'+mapCaption+'</div>'});// 인포윈도우로 장소에 대한 설명을 표시합니다
				infowindow.open(map, marker);
			}
			map.setCenter(coords);// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	    } 
	});	
</script>
<!-- 본문 -->
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
	$("td.HAthumb").each(function() {
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
	
	/* 사진 슬라이드 */	
	//슬라이드 함수
	function initializeSlide() {
	    var slideIndex = 0;
	    var totalSlides = $('.slide').length;
	
	    function showSlide(index) {
	        if (index < 0) {
	            slideIndex = totalSlides - 1;
	        } else if (index >= totalSlides) {
	            slideIndex = 0;
	        } else {
	            slideIndex = index;
	        }
	        var offset = -slideIndex * 100;
	        $('.slides').css('transform', 'translateX(' + offset + '%)');
	    }
	
	    $("#prevBtn").click(function() {
	        console.log("<" + slideIndex);
	        showSlide(slideIndex - 1);
	    });
	
	    $("#nextBtn").click(function() {
	        console.log(slideIndex + ">");
	        showSlide(slideIndex + 1);
	    });
	
	    // 자동 슬라이드 기능 추가
	    // setInterval(() => { showSlide(slideIndex + 1); }, 10000); // 10초마다 슬라이드 변경
	}
	
	//이미지 추가 함수
	function addImageToSlide(imageUrl) {
	    var newSlide = $('<div class="slide"><img src="' + imageUrl + '"></div>');
	    $('.slides').append(newSlide);
	}
	
	//이미지 불러오기
	var boardtype = '<c:out value="${home.boardtype}"/>';

    $.getJSON("/main/getAttachList", { boardtype: boardtype }, function(arr) {
        var str = "";

        $(arr).each(function(i, attach) {
            var fileCallPath = encodeURIComponent(attach.uploadPath + "/" + attach.uuid + "_" + attach.fileName);
            addImageToSlide("/display?fileName=" + fileCallPath);
        });

        initializeSlide(); // 슬라이드 초기화
    });	
});
</script>
<script type="text/javascript">
/* 첨부파일 조회화면 : 즉시실행함수*/
(function(){
	var ano = '<c:out value="${album.ano}"/>';
	
	$.getJSON("/album/getAttachList", {ano:ano}, function(arr){						
		var uStr="";
		var mStr="";
		
		
		$(arr).each(function(i, attach){					
			
			if(attach.fileType){					
				var fileCallPath = encodeURIComponent(attach.uploadPath+"/"+attach.uuid+"_"+attach.fileName);						
				uStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.					 
				uStr += "<div><i class='fa fa-image'></i>"+" <a>"+attach.fileName+"</a></div> </li>"; // 첨부파일 이미지(썸네일)
				
				mStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.					 
				mStr += "<img src='/display?fileName="+fileCallPath+"'> </li>"; // 첨부파일 이미지(썸네일)
			} else {
				var fileCallPath = encodeURIComponent(attach.uploadPath+"/"+attach.uuid+"_"+attach.fileName);
				uStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
				uStr += "<div><i class='fa fa-film'></i><a><span>"+" "+attach.fileName+"</span></a></div> </li>"; // 첨부파일 이미지
				
				mStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
				mStr += "<img src='/resources/img/attach.png'> </li>"; // 첨부파일 이미지		
			}
			
			$(".uploadResult ul").html(uStr);	
			$(".mediaContents ul").html(mStr);
		});			
	});
})();

</script>

<%@ include file="../includes/footer.jsp"%>