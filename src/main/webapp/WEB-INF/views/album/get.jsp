<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../includes/header.jsp"%>
<script type="text/javascript" src="../resources/js/album_reply.js"></script>
<title>Console.log(YH)_<c:out value="${album.title}"/></title>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px">
		<div class="panel panel-default">
			<!-- 본문제목 -->
			<div class="panel-heading">사진게시판 - 게시글 조회</div>	
					
			<!-- 본문내용 -->	
			<div class="panel-body">	
				<div class="form-group">					
					<h3><i class="fa fa-folder-open fa-fw"></i><c:out value="${album.title}"/></h3>
				</div>
				<hr>							
				<div class="form-group" style="display: flex; justify-content: space-between;">					
					<div class="col-lg-6" ><i class="fa fa-user fa-fw"></i> @<c:out value="${album.writer}"/></div>
					<div class="col-lg-6" ><i class="fa fa-arrow-circle-o-up fa-fw"></i> Hit:<c:out value="${album.hit}"/></div>
				</div>
				<div class="form-group" style="display: flex; justify-content: space-between;">
					<div class="col-lg-6" ><i class="fa fa-map-marker fa-fw"></i>여행지: <c:out value="${album.location}"/></div>					
					<div class="col-lg-6" ><i class="fa fa-calendar fa-fw"></i> <fmt:formatDate pattern="yyyy-MM-dd" value='${album.startDate}'/> - <fmt:formatDate pattern="yyyy-MM-dd" value='${album.endDate}'/></div>
				</div>		
				<hr>			
				<div class="form-group">
					<i class="fa fa-tags fa-fw"></i> <label>첨부파일</label>
					<div class="uploadResult" style="margin-bottom: 0px">
						<ul><!-- $.getJSON("/Album/getAttachList",ano,function) --></ul>					
					</div>
				</div>
				<p class="attachInfo">[첨부파일] <i class="fa fa-image fa-fw"></i>, <i class='fa fa-film'></i>를 클릭하시면 전체화면 미리보기, 파일명을 클릭하시면 다운로드가 진행됩니다.</p>
				<hr>
				<i class="fa fa-pencil fa-fw"></i> <label>내용</label>
				<div class="textArea"> <!-- 입력한 그대로 출력: 엔터/띄어쓰기 적용, 대신 공백 -->					
<c:out value="${album.content}"/>
					<div class="mediaContents">
						<ul><!-- 사진목록 --></ul>
					</div>
				</div>				
				<div style="justify-content: flex-end; display: flex; width: 100%;">
					<!-- 작성자 = 로그인된 사용자인 경우에만 Modify버튼 활성화 -->
					<sec:authentication property="principal" var="info"/>						
					<sec:authorize access="isAuthenticated()">
						<c:if test="${info.username eq album.writer}">
							<button data-oper="modify" class="btn btn-info" style="width:100px">수정하기</button>
						</c:if>
					</sec:authorize>				
					<button data-oper="list" class="btn btn-default" style="width:100px; margin: 0 5px;">목록으로</button>
					<button id="backBtn" class="btn btn-default"  style="width:100px">뒤로가기</button>
				</div>				
											
				<!-- Modify로 ano정보를 넘김으로써 수정/삭제시 modal창을 통해 게시글의 수정/삭제여부를 확인할수있게함. -->
				<form id="operForm" action="/album/modify" method="get">
					<input type="hidden" id="ano" name="ano" value='<c:out value="${album.ano}"/>'>
					<!-- albumController get()_get의 @ModelAttribute("cri")로 인해 수집된 정보 -->
					<input type="hidden" id="pageNum" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
					<input type="hidden" id="amount" name="amount" value='<c:out value="${cri.amount}"/>'>
					<!-- 검색조건 유지 -->
					<input type="hidden" name="type" value='<c:out value="${cri.type}"/>'>
					<input type="hidden" name="keyword" value='<c:out value="${cri.keyword}"/>'>
				</form>					
			</div>
		</div>			
	</div>
</div>

<!-- 첨부파일 파트 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			
		</div>
	</div>
</div>
			
<!-- 댓글 파트 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> 댓글
				<!-- 로그인된 사용자만 댓글달기 버튼이 활성화됨. -->
				<sec:authorize access="isAuthenticated()">
					<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">댓글쓰기</button>
				</sec:authorize>
			</div>			
			<!-- 댓글 목록 -->
			<div class="panel-body">
				<ul class="chat">
					<!-- function showList(page) -->
				</ul>
			</div>	
			<!-- 댓글 페이징 -->
			<div class="panel-footer">
				<!-- function showReplyPage(replyCnt) -->
			</div>							
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">댓글창</h4>
            </div>
            <div class="modal-body">
            	<div class="form-group">
					<label>내용</label>
					<textarea class="form-control" name='reply'></textarea>
				</div>	
                <div class="form-group">
					<label>작성자</label>
					<input class="form-control" name='replyer' value='Replyer'>
				</div>
				<div class="form-group">
					<label>작성일</label>
					<input class="form-control" name='replyDate' value=''>
				</div>
            </div>
            <div class="modal-footer">
                <button type="button" id="modalModBtn" class="btn btn-warning">수정</button>
                <button type="button" id="modalRemoveBtn" class="btn btn-danger">삭제</button>
                <button type="button" id="modalRegisterBtn" class="btn btn-default">등록</button>
                <button type="button" id="modalCloseBtn" class="btn btn-default" data-dismiss="modal">닫기</button>
            </div>
        </div>      
    </div>
</div>
<!-- 이동용 화살표 -->
<a href="#top" class="btn-nav-arrow up"><i class="fa fa-arrow-up"></i></a>
<a href="#bottom" class="btn-nav-arrow down"><i class="fa fa-arrow-down"></i></a>
<a class="btn-nav-arrow back"><i class="fa fa-arrow-left"></i></a>
<script>
$(document).ready(function(){
			
	var operForm = $("#operForm");
	
	/* modify버튼 기능 구현*/
	$("button[data-oper='modify']").on("click", function(e){
		operForm.attr("action", "/album/modify").submit();
	});
	
	/* list 이동버튼 기능 구현 */
	// list버튼
	$("button[data-oper='list']").on("click", function(e){
		operForm.find("#ano").remove(); // 수정없이 list로 이동시 ano정보가 필요없으므로 삭제
		operForm.attr("action", "/album/list");
		operForm.submit();
	});
	// 하단 nav arrow
	$(".btn-nav-arrow.back").on("click", function(e){
		operForm.find("#ano").remove(); // 수정없이 list로 이동시 ano정보가 필요없으므로 삭제
		operForm.attr("action", "/album/list");
		operForm.submit();
	});
	
	// nav버튼 클릭횟수 : 페이지 내부 이동시 history에 기록이 남아 이전페이지로 이동이 불가
	var navBtnCnt=0;	
	$("a.btn-nav-arrow.up, a.btn-nav-arrow.down").on("click", function(e){ navBtnCnt++; });
	
	/* 뒤로가기버튼 : nav버튼 횟수보다 -1을 해야 이전페이지로 이동  */
	$("#backBtn").on("click", function(e){
		e.preventDefault();				
		window.history.back(-navBtnCnt-1);
	});
	
	/* 댓글 이벤트처리 */
	var anoValue = '<c:out value="${album.ano}"/>';
	var replyUl = $(".chat");
	
	showList(1);
	
	/* 댓글 목록 조회 */
	function showList(page) {
		replyService.getList({ano:anoValue, page:page||1}, 
			function(replyCnt, list){
				if(page == -1){
					pageNum = Math.ceil(replyCnt/10.0);
					showList(pageNum);
					return;
				}
				
				var str = "";
			
				// 댓글목록이 없을 경우
				if(list == null || list.length == 0){
					replyUl.html("");
					return;
				} 
				
				// 댓글이 있을 경우 순차적으로 replyUl에 추가
				for(var i=0; i<list.length||0; i++){
					str += "<li class='left clearfix' data-rno="+list[i].rno+"><div id='replyZone' class='header'>";
					str += "<div id='replyA'><div class='reply_ProfileFrame' data-userid="+list[i].replyer+"></div></div>";
					str += "<div id='replyB'><strong class='primary-font'>"+list[i].replyer+"</strong><p class='pre-wrap'>"+list[i].reply+"</p></div>";
					str += "<div id='replyC' style='width:15%'><small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					str += "</div></li>";		
					
					(function(){		
						var replyer = list[i].replyer;
						$.getJSON("/user/getProfileImg", {userid:replyer}, function(result){
							var fileCallPath = encodeURIComponent(result.uploadPath+"/"+result.uuid+"_"+result.fileName);
					      	$(".reply_ProfileFrame[data-userid='"+replyer+"']").attr({ "data-path": result.uploadPath, "data-uuid": result.uuid, "data-filename": result.fileName, "data-type": result.fileType, "data-userid": result.userid });
				            $(".reply_ProfileFrame[data-userid='"+replyer+"']").html("<img id='profile_small' src='/display?fileName=" + fileCallPath + "'>");
						});
					})();
				}
				
				replyUl.html(str);
				
				showReplyPage(replyCnt);			
				
				// 프로필사진 클릭시 이벤트 처리 
				$(".reply_ProfileFrame").on("click", function(e){				
					var img = $(this);
					var path = encodeURIComponent(img.data("path")+"/"+img.data("uuid")+"_"+img.data("filename"));					
					console.log("------"+img.data("path"));
					showImage(path.replace(new RegExp(/\\/g),"/"), "img");
				});	
			}
		);	
	}
	
	/* 댓글 작성 */
	var modal = $(".modal");
	var modalInputReply = modal.find("textarea[name='reply']");
	var modalInputReplyer = modal.find("input[name='replyer']");
	var modalInputReplyDate = modal.find("input[name='replyDate']");
	
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	var modalRegisterBtn = $("#modalRegisterBtn");
	
	var replyer = null;
	
	/* replyer = 인증된 사용자 */
	<sec:authorize access="isAuthenticated()">
		replyer = '<sec:authentication property="principal.username"/>';
	</sec:authorize>
			
	/* Ajax Spring security Header info : 기본 설정으로 ajax에서 각각 beforeSend 호출없이 csrf를 적용. */
	$(document).ajaxSend(function(e, xhr, options){
		xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	});
	
	// 댓글 추가버튼>>modal
	$("#addReplyBtn").on("click", function(e){
		modal.find("textarea").val("");
		modal.find("input[name='replyer']").val(replyer);
		modalInputReplyDate.closest("div").hide();
		modal.find("button[id!='modalCloseBtn']").hide();
		modalRegisterBtn.show();
		$(".modal").modal("show");
	});
	
	// 댓글 추가처리
	modalRegisterBtn.on("click",function(e){
		var reply = {
				reply:modalInputReply.val(),
				replyer:modalInputReplyer.val(),
				ano:anoValue
		};
		
		replyService.add(reply, function(result){
			alert(result); // 결과 알림창
			modal.find("textarea").val(""); // input내용 비우고,
			modal.modal("hide");         // modal창 숨기기.
			
			showList(1); // 새 댓글은 마지막에 추가됨.
		});
	});
	
	/* 댓글 조회(특정 댓글 클릭 이벤트) */
	$(".chat").on("click", "li div[id='replyB']", function(e){
		var parentLi = $(this).closest('li');
	    var rno = parentLi.data('rno');
		//var rno = $(this).data("rno");
		
		replyService.get(rno, 
			function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer).attr("readonly", "readonly");
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
				modal.data("rno", reply.rno);
				
				modal.find("button[id!='modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				
				$(".modal").modal("show");
		});			
	});
	
	/* 댓글 수정 */
	modalModBtn.on("click",function(e){
		var origin = modalInputReplyer.val(); // 댓글 작성자
		var reply = {rno:modal.data("rno"),	reply:modalInputReply.val(), replyer:origin};
		
		//로그인이 되어있지 않다면,
		if(!replyer){
			alert("로그인 후 수정이 가능합니다.");
			modal.modal("hide");
			return;
		}
		
		//로그인≠댓글작성자
		if(replyer != origin){
			alert("본인이 작성한 댓글만 수정할 수 있습니다.");
			modal.modal("hide");
			return;	
		}			
		
		replyService.update(reply, function(result){
			alert(result); // 결과 알림창				
			modal.modal("hide");  // modal창 숨기기.				
			showList(pageNum); // 수정된 댓글이 위치했던 곳으로 이동
		});
	});
	
	/* 댓글삭제 */
	modalRemoveBtn.on("click",function(e){
		var rno = modal.data("rno"); 
		
		//로그인이 되어있지 않다면,
		if(!replyer){
			alert("로그인 후 삭제가 가능합니다.");
			modal.modal("hide");
			return;
		}
		
		var origin = modalInputReplyer.val(); // 댓글 작성자
		
		//로그인≠댓글작성자
		if(replyer != origin){
			alert("본인이 작성한 댓글만 삭제할 수 있습니다.");
			modal.modal("hide");
			return;	
		}			
		
		replyService.remove(rno, origin, function(result){
			alert(result); // 결과 알림창				
			modal.modal("hide"); // modal창 숨기기.				
			showList(pageNum); //삭제된 댓글이 위치했던 곳으로 이동
		});
	});
	
	/* 댓글 페이징 처리 */
	var pageNum = 1;
	var replyPageFooter = $(".panel-footer");
	
	function showReplyPage(replyCnt){
		var endNum = Math.ceil(pageNum/10.0)*10;
		var startNum = endNum - 9; // 리스트에 10개씩 출력하는 조건
		
		var prev = startNum != 1;
		var next = false;
		
		// 마지막 넘버 재계산
		if(endNum*10 >= replyCnt){
			endNum = Math.ceil(replyCnt/10.0);
		} 
		
		// next버튼 활성화
		if(endNum*10 < replyCnt){
			next = true;
		} 
		
		var str = "<ul class='pagination pull-right'>";
		
		//prev구현
		if(prev){
			str += "<li class='page-item'><a class='page-link' href='"+(startNum -1 )+"'>◀</a></li>";
		}
		
		//번호칸 구현
		for(var i=startNum; i<=endNum; i++){
			var active = pageNum == i ? "active" : "";	
			str += "<li class='page-item "+active+"'><a class='page-link' href='"+i+"'>"+i+"</a></li>";					
		}
		
		//next구현
		if(next){
			str += "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>▶</a></li>";
		}
					
		str+="</ul></div>"

		replyPageFooter.html(str);			
	}		
	
	//페이지 이동 기능 구현
	replyPageFooter.on("click","li a", function(e){
			e.preventDefault();
			var targetPageNum = $(this).attr("href"); // panel-footer(prev/num/next) > href속성
			pageNum = targetPageNum;
			showList(targetPageNum);
		}
	);		
	
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
					uStr += "<div><i id='img' class='fa fa-image'></i> <a>"+attach.fileName+"</a></div> </li>"; // 첨부파일 이미지(썸네일)
					
					mStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.					 
					mStr += "<img src='/display?fileName="+fileCallPath+"'> </li>"; // 첨부파일 이미지(썸네일)
				} else {
					var fileCallPath = encodeURIComponent(attach.uploadPath+"/"+attach.uuid+"_"+attach.fileName);
					uStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
					uStr += "<div><i id='film' class='fa fa-film'></i> <a>"+attach.fileName+"</a></div> </li>"; // 첨부파일 이미지
					
					mStr += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.					
					mStr += "<video controls><source src='/display?fileName="+fileCallPath+"' type='video/mp4'></video>";
				}
				
				$(".uploadResult ul").html(uStr);	
				$(".mediaContents ul").html(mStr);
			});			
		});
	})();
	
	/* 첨부파일 클릭시 이벤트 처리 */
	$(".uploadResult").on("click", "li", function(e){
		var element = $(e.target);
		var liObj = $(this);
		var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명

		if(element.is("i#img")){ 				
			showImage(path.replace(new RegExp(/\\/g),"/"), "img"); // 이미지파일 : showImage함수 실행				
		} else if(element.is("i#film")){
			showImage(path.replace(new RegExp(/\\/g),"/"), "video"); // 이미지파일 : showImage함수 실행
		} else {
			self.location="/download?fileName="+path; // 기타파일 : 현재창에서 다운로드
		}
	});
	
	
	$(".mediaContents").on("click", "li", function(e){
		var liObj = $(this);
		var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명
		
		if(element.is("i#img")){
			showImage(path.replace(new RegExp(/\\/g),"/"), "img");			
		} else if (element.is("i#film")) {
			showImage(path.replace(new RegExp(/\\/g),"/"), "video");
		}
	});
	
	// 원본사진 확대보기 on
	function showImage(fileCallPath, type){			
		$(".picWrap").css("display","flex").show(); // none > flex 설정 변경		
		if(type === "img"){
			console.log(type);
			$(".pic").html("<img src='/display?fileName="+fileCallPath+"'>").animate({width:'100%', height:'100%'}, 0);//pic의 html속성은 controller의 display메서드, animate는 크기변경(배경창 100%*100%) 0.3초 후 실행
		} else if(type === "video"){
			console.log(type);
			$(".pic").html("<video controls autoplay><source src='/display?fileName="+fileCallPath+"' type='video/mp4'></video>").animate({width:'100%', height:'100%'}, 0);			
		}
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


	