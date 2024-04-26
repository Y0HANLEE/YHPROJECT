<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../includes/header.jsp"%>
<script type="text/javascript" src="../resources/js/board_reply.js"></script>
<title>게시글조회 no.<c:out value="${board.bno}"/></title>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px">
		<div class="panel panel-default">
			<!-- 본문제목 -->
			<div class="panel-heading">	Board Read Page</div>	
					
			<!-- 본문내용 -->	
			<div class="panel-body">				
				<div class="form-group">					
					<h3><i class="fa fa-folder-open fa-fw"></i> [<c:out value="${board.bno}"/>] <c:out value="${board.title}"/></h3>
				</div>
				<hr>
				<div class="pull-right">
					<i class="fa fa-user fa-fw"></i> @<c:out value="${board.writer}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<i class="fa fa-arrow-circle-o-up fa-fw"></i> Hit:<c:out value="${board.hit}"/> 
				</div>
				<i class="fa fa-tags fa-fw"></i> <label>File</label>
				<div class="uploadResult" style="margin-bottom: 5px">
					<ul>
						<!-- function showUploadResult(uploadResultArr) -->
					</ul>
				</div>
				<p class="attachInfo">[사진파일] 썸네일을 클릭하시면 이미지 확대, 파일명을 클릭하시면 다운로드가 진행됩니다.</p>
				<i class="fa fa-pencil fa-fw"></i> <label>Content</label>
				<div class="textArea">	<!-- 입력한 그대로 출력: 엔터/띄어쓰기 적용, 대신 공백 -->				
<c:out value="${board.content}"/>
				</div>				
				<div class="pull-right">
					<!-- 작성자 = 로그인된 사용자인 경우에만 Modify버튼 활성화 -->
					<sec:authentication property="principal" var="info"/>						
					<sec:authorize access="isAuthenticated()">
						<c:if test="${info.username eq board.writer}">
							<button data-oper="modify" class="btn btn-info">수정하기</button>
						</c:if>
					</sec:authorize>				
					<button data-oper="list" class="btn btn-default">목록으로</button>
					<button type="button" class="btn btn-default" id="backBtn">뒤로가기</button>
				</div>	
											
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
	</div>
</div>
			
<!-- 댓글 파트 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Reply
				<!-- 로그인된 사용자만 댓글달기 버튼이 활성화됨. -->
				<sec:authorize access="isAuthenticated()">
					<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">New</button>
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
                <h4 class="modal-title" id="myModalLabel">Reply Modal</h4>
            </div>
            <div class="modal-body">
            	<div class="form-group">
					<label>Reply</label>
					<textarea class="form-control" name='reply'></textarea>
				</div>	
                <div class="form-group">
					<label>Replyer</label>
					<input class="form-control" name='replyer' value='Replyer'>
				</div>
				<div class="form-group">
					<label>Reply Date</label>
					<input class="form-control" name='replyDate' value=''>
				</div>
            </div>
            <div class="modal-footer">
                <button type="button" id="modalModBtn" class="btn btn-warning">Modify</button>
                <button type="button" id="modalRemoveBtn" class="btn btn-danger">Remove</button>
                <button type="button" id="modalRegisterBtn" class="btn btn-default">Register</button>
                <button type="button" id="modalCloseBtn" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>      
    </div>
</div>
<!-- 이동용 화살표 -->
<a href="#top" class="btn-nav-arrow up"><i class="fa fa-arrow-up"></i></a>
<a href="#bottom" class="btn-nav-arrow down"><i class="fa fa-arrow-down"></i></a>
<a class="btn-nav-arrow back"><i class="fa fa-arrow-left"></i></a>

<!-- 기본화면 -->
<script>
$(document).ready(function(){			
	var operForm = $("#operForm");
	
	/* modify버튼 기능 구현*/
	$("button[data-oper='modify']").on("click", function(e){
		operForm.attr("action", "/board/modify").submit();
	});
	
	/* list 이동버튼 기능 구현 */
	// list버튼
	$("button[data-oper='list']").on("click", function(e){
		operForm.find("#bno").remove(); // 수정없이 list로 이동시 bno정보가 필요없으므로 삭제
		operForm.attr("action", "/board/list");
		operForm.submit();
	});
	// 하단 nav arrow
	$(".btn-nav-arrow.back").on("click", function(e){
		operForm.find("#bno").remove(); // 수정없이 list로 이동시 bno정보가 필요없으므로 삭제
		operForm.attr("action", "/board/list");
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
	
	


});
</script>

<!-- 댓글 -->
<script>
$(document).ready(function(){
	/* 댓글 이벤트처리 */
	var bnoValue = '<c:out value="${board.bno}"/>';
	var replyUl = $(".chat");
	
	showList(1);
	
	/* 댓글 목록 조회 */
	function showList(page) {
		replyService.getList({bno:bnoValue, page:page||1}, 
			function(replyCnt, list){
				console.log("replyCnt:"+replyCnt);
				console.log("list:"+list);
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
					str += "<li class='left clearfix' data-rno="+list[i].rno+"><div class='header'>";
					str += "<strong class='primary-font'>"+list[i].replyer+"</strong>";
					str += "<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					str += "<p class='pre-wrap'>"+list[i].reply+"</p></div></li>";					
				}						
				replyUl.html(str);
				
				showReplyPage(replyCnt);
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
				bno:bnoValue
		};
		
		replyService.add(reply, function(result){
			alert(result); // 결과 알림창
			modal.find("textarea").val(""); // reply내용 비우고,
			modal.modal("hide");         // modal창 숨기기.
			
			showList(-1); // 새 댓글은 마지막에 추가됨.
		});
	});
	
	/* 댓글 조회(특정 댓글 클릭 이벤트) */
	$(".chat").on("click", "li", function(e){
		var rno = $(this).data("rno");
		
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
		var originReplyer = modalInputReplyer.val(); //댓글작성자		
		var reply = {rno:modal.data("rno"),	reply:modalInputReply.val(), replyer:originReplyer};
		
		// 로그인x
		if(!replyer){
			alert("로그인 후 수정이 가능합니다.");
			modal.modal("hide");
			return;
		}		
		
		//로그인≠댓글작성자
		if(replyer != originReplyer){
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
			showList(targetPageNum);
		}
	);		
});
</script>

<!-- 첨부파일 -->
<script>
$(document).ready(function(){
	/* 첨부파일 조회화면 : 즉시실행함수*/
	(function(){
		var bno = '<c:out value="${board.bno}"/>';
		
		$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
			console.log(arr)
			
			var str="";
			
			$(arr).each(function(i, attach){					
				if(attach.fileType){					
					var fileCallPath = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);						
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.					 
					str += "<div><img src='/display?fileName="+fileCallPath+"'><a><span>"+" "+attach.fileName+"</span></a></div> </li>"; // 첨부파일 이미지(썸네일)
				} else {						
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
					str += "<div><img src='/resources/img/attach.png'><a><span>"+" "+attach.fileName+"</span></a></div> </li>"; // 첨부파일 이미지
				}
			});
			$(".uploadResult ul").html(str);
		});
	})();
	
	/* 첨부파일 클릭시 이벤트 처리 */
	$(".uploadResult").on("click", "li", function(e){
		var element = $(e.target);
		var liObj = $(this);
		var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명

		if(liObj.data("type") && element.is("img")){ //type이 이미지, 요소가 img일경우만 이벤트				
			showImage(path.replace(new RegExp(/\\/g),"/")); // 이미지파일 : showImage함수 실행				
		} else {
			self.location="/download?fileName="+path; // 기타파일 : 현재창에서 다운로드
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


	