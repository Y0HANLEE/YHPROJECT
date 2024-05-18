<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../includes/header.jsp"%>
<title>Console.log(YH)_게시글등록</title>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px">
		<div class="panel panel-default">
			<!-- 본문제목 -->
			<div class="panel-heading">일반게시판 - 새 게시글 등록</div>			
			<!-- 본문내용 -->	
			<div class="panel-body">			
				<form role="form" action="/board/register" method="post">				
					<div class="form-group">
						<i class="fa fa-folder fa-fw"></i><label>제목</label>
						<input class="form-control" name="title">
					</div>				
					<hr>
					<div class="form-group" style="display: flex;justify-content: space-between;">
						<div><i class="fa fa-user fa-fw"></i> @<sec:authentication property='principal.username'/></div>												 
						<div><i class="fa fa-check fa-fw"></i><label>등록일</label> <fmt:formatDate pattern="YY/MM/dd hh:mm:ss" value="${now}"/></div>						
					</div>	
					<hr>
					<i class="fa fa-tags fa-fw"></i><label>첨부파일</label>
					<div class="form-group uploadDiv">
						<input type="file" name="uploadFile" multiple>
					</div>
					<!-- 결과 -->
					<div class="uploadResult">
						<ul>
							<!-- function showUploadResult(uploadResultArr) -->
						</ul>
					</div>
					<hr>
					<div class="form-group">
						<i class="fa fa-pencil fa-fw"></i><label>내용</label>
						<textarea class="form-control" rows="10" name="content"></textarea>
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-info">작성하기</button>
						<button type="reset" class="btn btn-warning">초기화</button>
						<button type="button" class="btn btn-default" onclick="history.back()">뒤로가기</button>						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />		
						<input type="hidden" class="form-control" name="writer" value="<sec:authentication property='principal.username'/>" readonly>
					</div>
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
				<h4 class="modal-title">알림</h4> 
			</div>
			<div class="modal-body">
				<!--checkBlank()-->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>
				
<script>
$(document).ready(function(e){
	/* 게시글 등록버튼 클릭시 수행할 기능 */
	var formObj = $("form[role='form']");
	
	$("button[type='submit']").on("click", function(e){
		e.preventDefault();
		console.log("submit clicked!!!!!!");
		
		// DB에 등록시키기 위해 BoardVO.attachList에 정보를 전송
		var str = "";
					
		$(".uploadResult ul li").each(function(i, obj){
			var jobj = $(obj);
			
			console.dir(jobj); // 체의 속성을 나열하여 출력
			
			str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
		});
		formObj.append(str);			
		checkBlank(); 
	});
	
	/* 빈칸 경고창 */			
	function checkBlank() {
		var title = $("input[name='title']").val();
		var content = $("textarea[name='content']").val();			

		if (title === "") {
			$(".modal-body").html("<p>제목을 작성해주세요</p>");
			$("#myModal").modal("show");
		    $("input[name='title']").focus();
		    return false; 
		}

		if (content === "") {				
			$(".modal-body").html("<p>내용을 작성해주세요</p>");
			$("#myModal").modal("show");
			$("input[name='content']").focus();
		    return false;				
		}				
		
		formObj.submit();
	}
	
	/* 업로드 상세처리(확장자, 크기 등) */
	var regex = new RegExp("(.*?)\.(exe|sh|alz|bat)"); // 업로드 불가 확장자
	var maxSize = 31457280; // 30MB		
	
	function checkFile(fileName, fileSize){
		// 파일사이즈 검토
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과 : 파일당 최대 30MB까지 업로드 가능합니다.");
			return false;
		}
		
		// 파일이름(확장자) 검토
		if(regex.test(fileName)){
			alert("exe,sh,alz,bat 확장자는 업로드 할 수 없습니다.");
			return false;
		}
		
		return true; // 성공시
	}
		
	/* 등록버튼 없이 변화가 감지되면 처리할 기능 */
	$("input[type='file']").change(function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");//첨부된 파일
		var files = inputFile[0].files;
		
		for(i=0; i<files.length; i++){
			if(!checkFile(files[i].name, files[i].size)){				
				return false;
			}
			formData.append("uploadFile", files[i]);
		}
		
		$.ajax({
			type:'post',
			url:'/uploadAjaxAction',
			processData:false,
			contentType:false,
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
			data:formData,
			dataType:'json',
			success:function(result){					
				showUploadResult(result);				
			}
		});
	});
	
	/* 업로드 결과 보이기 */
	function showUploadResult(uploadResultArr){
		if(!uploadResultArr || uploadResultArr.length == 0){ return; };
		
		var uploadUL = $(".uploadResult ul");		
		var str="";				
		
		$(uploadResultArr).each(
			function(i,obj){
				if(!obj.fileType){
					var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);						
					
					str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uuid, uploadpath, filename, type(img)을 추가한다.
					str += "<img src='/resources/img/attach.png'>";	// 첨부파일 이미지(attach.png)
					str += "<span>"+' '+obj.fileName+' '+"</span>"; // 파일명
					str += "<button type='button' class='btn btn-danger btn-circle btn-xs' data-file='"+fileCallPath+"' data-type='file'><i class='fa fa-times'></i></button> </li>"; // x버튼 data-file:삭제할 경로, data-type:삭제할 파일의 타입 >> file:그냥삭제
				} else {				
					var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);													
					
					str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, type(img)을 추가한다.
					str += "<img src='/display?fileName="+fileCallPath+"'>"; // 첨부파일 이미지(썸네일)						
					str += "<span>"+' '+obj.fileName+' '+"</span>"; // 파일명 
					str += "<button type='button' class='btn btn-danger btn-circle btn-xs' data-file=\'"+fileCallPath+"\' data-type='image'><i class='fa fa-times'></i></button> </li>"; // x버튼 data-file:삭제할 경로, data-type:삭제할 파일의 타입 >> image:원본+썸네일 삭제
				}
			});
		uploadUL.append(str);
	}
	
	/* 업로드 취소 기능 구현*/
	$(".uploadResult").on("click", ".btn-danger", function(e){
		var target = $(this).data("file");
		var type = $(this).data("type");
		var targetLi = $(this).closest("li"); // target(삭제 파일)이 속한 li태그
				
		$.ajax({
			type:'POST',
			url:'/deleteFile', // 서버(폴더)에서만 삭제
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
			data:{fileName:target, type:type},
			dataType:'text',
			success: function(result){				
				targetLi.remove(); // 화면에서도 삭제
			}
		});		
	});		
	
	/* 첨부파일 클릭시 이벤트 처리 */
	$(".uploadResult").on("click", "li", function(e){
		var element = $(e.target);
		var liObj = $(this);
		var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명
		
		//span이나 img일경우만 이벤트 >> x버튼은 삭제만 처리
		if(element.is("span") || element.is("img")){
			if(liObj.data("type")){
				showImage(path.replace(new RegExp(/\\/g),"/")); // 이미지파일 : showImage함수 실행
			}
		} 
	});
	
	// 원본사진 확대보기 on
	function showImage(fileCallPath){			
		$(".picWrap").css("display","flex").show(); // none > flex 설정 변경
		$(".pic").html("<img src='/display?fileName="+fileCallPath+"'>").animate({width:'100%', height:'100%'}, 0);//pic의 html속성은 controller의 display메서드, animate는 크기변경(배경창 100%*100%) 0.3초 후 실행 
	}
	
	// 원본사진 확대보기 off 
	$(".picWrap").on("click", function(e){
		$(".pic").animate({width:'0%', height:'0%'}, 0); // (0%*0%) 로 0.3초 후 크기변경
		setTimeout(() => {$(this).hide();}, 0);	// chrome의 ES6화살표함수
		//IE : setTimeout(function(){$('.picWrap').hide();}, 300);
	});
	
});
</script>
<%@ include file="../includes/footer.jsp"%>