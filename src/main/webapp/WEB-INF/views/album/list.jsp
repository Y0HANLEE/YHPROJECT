<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<script type="text/javascript" src="../resources/js/album_reply.js"></script>
<title>Console.log(YH)_사진게시판</title>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i class="fa fa-camera-retro fa-fw"></i>사진게시판</h1>
	</div>
	<p class="mb-4">
		º커뮤니티 회원을 위한 사진게시판이며, 사진과 동영상을 업로드할 수 있습니다.
	</p>
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				사진게시판 리스트
				<button type="button" id="regBtn" class="btn btn-primary btn-xs pull-right">글쓰기</button>
			</div>
			<!-- 본문 및 부가기능 -->
			<div class="panel-body">
				<!-- 본문 -->				
				<table class="table table-striped table-bordered table-hover tableList">
					<thead>
						<tr>
							<th class="Aano">No</th>							
							<th class="Athumb">사진</th>
							<th class="Atitle">제목</th>
							<th class="Aloc">지역</th>
							<th class="Adue">여행일자</th>
							<th class="Awriter">글쓴이</th>
							<th class="Areply">댓글</th>
							<th class="Ahit">조회수</th>
							<th class="Areg">등록일</th>
						</tr>
					</thead>
					<tbody>					 
					<c:forEach items="${list}" var="album">
						<tr style="vertical-align: middle; height:100px;">
							<td class="Aano"><c:out value="${album.ano}"/></td>
							<td class="thumb" data-ano="${album.ano}"><!-- thumbnail --></td>
							<td class="Atitle">
								<a class="move" href='<c:out value="${album.ano}"/>'>
									<c:out value="${album.title}" />
								</a>
							</td>
							<td class="Aloc">								
								<c:choose>
								    <c:when test="${empty album.location}">-</c:when>
								    <c:otherwise><c:out value="${album.location}" /></c:otherwise>
								</c:choose>								
							</td>
							<td class="Adue"><fmt:formatDate value="${album.startDate}" pattern="YY/MM/dd"/> - <fmt:formatDate value="${album.endDate}" pattern="YY/MM/dd"/></td>
							<td class="Awriter"><c:out value="${album.writer}" /></td>
							<td class="Areply"><c:out value="${album.replyCnt}"/></td>
							<td class="Ahit"><c:out value="${album.hit}" /></td>
							<td class="Areg"><fmt:formatDate pattern="YY.MM.dd" value="${album.regdate}" /></td>																				
						</tr>
					</c:forEach>					
					</tbody>
				</table>
				
				<!-- 검색창 -->
				<div class="row">		
					<div class="col-lg-12">	
						<div class="pull-right">
							<form id="searchForm" action="/album/list" method='get' style="display:flex;">	
								<div>						
									<select name="type">
										<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }"/>>--</option>						
										<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : '' }"/>>제목</option>							
										<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }"/>>내용</option>
										<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : '' }"/>>작성자</option>
										<option value="L" <c:out value="${pageMaker.cri.type eq 'L' ? 'selected' : '' }"/>>여행지</option>
										<option value="D" <c:out value="${pageMaker.cri.type eq 'D' ? 'selected' : '' }"/>>여행기간</option>
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
				<form id="actionForm" action="/album/list" method="get">
					<!-- 페이지 유지 -->
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
					<!-- 검색조건 유지 -->
					<input type="hidden" name="type" value='<c:out value="${pageMaker.cri.type}"/>'>
					<input  type="hidden" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"/>'>			
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
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>							
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
			
			if(parseInt(result) > 0) {
				$(".modal-body").html("게시글 "+parseInt(result)+" 번이 등록되었습니다.");
			}
			
			$("#myModal").modal("show");
		} 
		
		/* 등록버튼 기능 설정 : register페이지로 이동 */
		$("#regBtn").on("click", function(){
			self.location = "/album/register";
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
			actionForm.append("<input type='hidden' name='ano' value='"+$(this).attr("href")+"'>");
			actionForm.attr("action", "/album/get");
			actionForm.submit();
		});
		
		/*검색조건 '여행기간'*/
		$("select[name='type']").change(function(){             
            if($(this).val() === 'D') {
                $("#keyword").attr('type', 'date');
            } else {
                $("#keyword").attr('type', 'text');
            }
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
	            window.location.href = "/album/list";
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
	    
	    //검색 후, 여행기간 검색 조건이 선택되었을 때 검색어 입력 폼을 날짜 타입으로 변경
	    $(function(){
	        var type = $('select[name="type"]').val();
	        if (type === 'D') {
	            $('#keyword').attr('type', 'date');
	        } 
	    });

	    //검색 후 입력란 정보 유지
	    $(function(){	        
            var keyword = '${pageMaker.cri.keyword}';
            $('#keyword').val(keyword);
	    });
		
		/* 썸네일 출력 */
		$(".thumb").each(function() {
		    var ano = $(this).data('ano');
		    var thumbnail = $(this);

		    $.getJSON("/album/getAttachList", {ano: ano}, function(attach) {
		        var str = "";

		        if (attach && attach.length > 0 && attach[0].fileType) { //첨부파일이 있고, 첫번째 첨부파일이 사진인 경우
		            var fileCallPath = encodeURIComponent(attach[0].uploadPath + "/s_" + attach[0].uuid + "_" + attach[0].fileName);
		            str += "<div data-path='"+attach[0].uploadPath+"' data-uuid='"+attach[0].uuid+"' data-filename='"+attach[0].fileName+"' data-type='"+attach[0].fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
		            str += "<img src='/display?fileName=" + fileCallPath + "'></div>"; //있으면 출력
		        } else {
		            str += "<div><img src='/resources/img/Default-Thumbnail.png'></div>"; //그 외엔 기본썸네일
		        }
		        thumbnail.html(str);
		    });
		});
		
		/* 썸네일 클릭시 이벤트 처리 */
		$(".thumb").on("click", "div", function(e){
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