<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<title>회원관리</title>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px;">
		<div class="panel panel-default">
			<div class="panel-heading">
				User List Page
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
							<th style="text-align:center; width:10%">아이디</th>
							<th style="text-align:center; width:10%">이름</th>
							<th style="text-align:center; width:5%">성별</th>
							<th style="text-align:center; width:5%">생일</th>
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
							<td><fmt:formatDate pattern="YY/MM/dd" value="${user.birth}"/></td>							
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
							<form id="searchForm" action="/manager/list" method='get'>							
								<select name="type">
									<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }"/>>전체</option>						
									<option value="I" <c:out value="${pageMaker.cri.type eq 'I' ? 'selected' : '' }"/>>아이디</option>							
									<option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }"/>>이름</option>
									<option value="G" <c:out value="${pageMaker.cri.type eq 'G' ? 'selected' : '' }"/>>성별</option>
									<option value="P" <c:out value="${pageMaker.cri.type eq 'P' ? 'selected' : '' }"/>>연락처</option>
									<option value="A" <c:out value="${pageMaker.cri.type eq 'A' ? 'selected' : '' }"/>>주소</option>
									<option value="M" <c:out value="${pageMaker.cri.type eq 'M' ? 'selected' : '' }"/>>이메일</option>
									<option value="B" <c:out value="${pageMaker.cri.type eq 'B' ? 'selected' : '' }"/>>생일</option>																		
								</select>	
								<input type="text" name="keyword" placeholder="Search for...">								
								<button class="btn btn-default" type="button">Search</button>														
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
				<form id="actionForm" action="/admin/list" method="get">
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
		
		var userForm = $("#userForm");
		
		/* 등급조정 */
		// select값이 변하면 input_hidden(auth)값으로 전달
		$("select[name='auth']").change(function() { 
            var optionVal = $(this).val();
            $("input[name='auth']").val(optionVal);
        });
		// 버튼 클릭이벤트
		$(".authBtn").on("click", function(){
			var userid = $(this).data("userid");
			var auth = $("input[name='auth']").val();			
			var msg = "";
			
			if (auth == "ROLE_ADMIN"){
				msg = "현재 회원을 [관리자]로 변경하시겠습니까?";				
			} else if(auth=="ROLE_MANAGER") {
				msg = "현재 회원을 [운영자]로 변경하시겠습니까?";
			} else {
				msg = "현재 회원을 [일반회원]으로 변경하시겠습니까?";
			}

			if (confirm(msg)) { // confirm후 취소버튼 클릭시 오류발생.
		        $("#userForm input[name='userid']").val(userid); //userid값을 input_hidden(userid)값으로 전달
		        $("#userForm input[name='auth']").val(auth);     //auth값을 input_hidden(auth)값으로 전달
		        userForm.attr("action", "/admin/auth").submit();
		    }				
		});
		
		$(".deleteBtn").on("click", function(){
		    var userid = $(this).data("userid");		    
		    var msg = "계정을 삭제하시려면 해당 회원의 ID를 입력해주십시오.";		    
		    var userInput = prompt(msg);
			
		    if (userInput === userid) { // 최종체크(아이디 재입력)
		        $("#userForm input[name='userid']").val(userid); //userid값을 input_hidden(userid)값으로 전달			        
			    userForm.attr("action", "/admin/delete").submit();
		    } else {
		        alert("일치하지 않습니다.");
		        return false;
		    }
		});

		
		/* MODAL창 설정 */
		function checkModal(result) {			
			$("#myModal").modal("show");
		} 
		
		/* 버튼 기능 설정 : 추후생각
		$("#regBtn").on("click", function(){
			self.location = "/admin/...";
		});*/
		
		/* 페이징버튼 이벤트 */
		var actionForm = $("#actionForm");
		
		$(".paginate_button a").on("click", function(e){
			e.preventDefault();
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit();
		});
				
		/* 검색버튼 이벤트 처리 */
		var searchForm = $("#searchForm");
		
		$("#searchForm button").on("click", function(e){
			e.preventDefault();
			
			if(!searchForm.find("option:selected").val() && !$("input[name='keyword']").val()){
				window.location.href = "/admin/list";
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
				
			searchForm.find("input[name='pageNum']").val("1"); // 검색시 무조건 1페이지로 이동
		
			searchForm.submit();
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