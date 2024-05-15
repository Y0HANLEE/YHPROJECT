<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<title>회원가입</title>
<%@ include file="../includes/header.jsp"%>
<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header">회원가입페이지</h1>
	</div>		
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Form</div>
			<div class="panel-body">			
				<div id="userCol6left" class="col-lg-6" align="center">
					<label for="singleFile">프로필사진</label><br>		
					<div id="join_ProfileFrame" class="uploadProfile">
   						<!-- profile image -->    					
   					</div>						
					<form id="profileForm" action="/uploadSingle" method="post" enctype="multipart/form-data">										
						<div id="profileZone" class="form-group">
							<input id="singleFile" class="form-control" name="singleFile" type="file" > <!-- 파일 선택 -->
							<button id="findImgBtn" class="btn btn-default" style="margin-top:5px" >찾아보기</button>
							<button id="resetImgBtn" class="btn btn-default" style="margin-top:5px" >초기화</button>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
						</div>
					</form>
					<div id="paperZone" class="form-group" align="left">
						<label for="paper">홈페이지 가입약관 및 개인정보이용동의서</label><br>
						<textarea class="form-control" rows="18" cols="60" id="paper" name="paper" readonly>
<%@ include file="agree.jsp"%>
						</textarea>
						<div id="agreeZone">
							<input id="checkbox" type="checkbox" value="N">
							<label id="agree">동의합니다.</label>
						</div>				
					</div>					
				</div>		
					
				<form id="joinForm" method="post" action="/user/join">					
					<div class="col-lg-6">				
						<div class="form-group">						
							<p>
								<label for="userid">아이디</label><span class="essential pull-right">*영문자 6~8자</span> 
								<button id="idCheck" class="btn btn-outline btn-danger btn-xs" value="N">CHECK</button>
							</p>
							<input id="userid" class="form-control" name="userid" placeholder="ID" autofocus>							
						</div>						
						<div class="form-group">
							<label for="userpw">비밀번호</label><span class="essential pull-right">*숫자,영문자,특수문자(!,@,#,$,%,^,&,*) 포함 6~12자</span>
							<input id="userpw" class="form-control" name="userpw" type="password" placeholder="Password">
						</div>
						<div class="form-group">
							<div><label for="pwconfirm">비밀번호확인</label><span class="essential pull-right">*입력하신 비밀번호를 다시 입력해주세요</span> 
							<button type="button" class="btn btn-outline btn-danger btn-xs" id="pwCheck" value="N">CHECK</button></div>
							<input id="pwconfirm" class="form-control" name="pwconfirm" type="password" placeholder="Password Again">
						</div>
						<div class="form-group">
							<label for="name">이름</label>
							<input class="form-control" name="name" placeholder="Name">
						</div>
						<div class="form-group">
							<label for="gender">성별</label>
							<select id="gender" class="form-control" name="gender">
								<option value="" selected disabled>성별을 선택해주세요.</option>
								<option value="M">남자</option>
								<option value="W">여자</option>																
							</select>
						</div>
						<div class="form-group">
							<label for="phone">전화번호</label><span class="essential pull-right">예시) 010-1234-5678</span>
							<input id="phone" class="form-control" name="phone" placeholder="Phone Number">
						</div>
						<div class="form-group">							
							<div><label for="email">이메일</label> <button type="button" class="btn btn-outline btn-danger btn-xs" id="checkModalBtn" value="N">SEND MAIL</button></label>&nbsp;&nbsp;&nbsp;<span class="essential pull-right">예시) admin@console.log</span></div>
							<input id="email" class="form-control" name="email" type="email" placeholder="Email Address">
						</div>
						<div class="form-group">
								<label for="address">주소</label>
							<div id="addressZone">
								<input id="zonecode" class="form-control" name="zonecode" placeholder="우편번호" readonly>&nbsp;&nbsp;
								<button id="searchAddressBtn" class="btn btn-default">주소찾기</button>						
							</div>
							<input id="address_kakao" class="form-control" name="address" placeholder="주소" readonly>
							<input class="form-control" name="addressDetail" placeholder="상세주소">
						</div>
						<div class="form-group">
							<label for="birth">생년월일</label>
							<input id="birth" class="form-control" name="birth" type="date">
						</div>
					</div>						
					<!-- 보안:사이트간 요청 위조방지. spring security에서 post방식을 이용하는 경우 사용.-->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<!-- 프로필사진 정보 -->					
					<input type="hidden" name="profileImg.uploadPath" value="profile">
					<input type="hidden" name="profileImg.uuid" value="ed87212c-4e79-4813-be6c-8c73ac58ac33">
					<input type="hidden" name="profileImg.fileName" value="Default-Profile.png">
					<input type="hidden" name="profileImg.fileType" value="true">
				</form>		
				<div class="pull-right">
					<button id="joinBtn" class="btn btn-lg btn-success btn-block">회원가입</button>
				</div>
			</div>
		</div>
	</div>
</div>	
<!-- Modal -->
<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header"> 
				<h4 class="modal-title">알림</h4> 
			</div>
			<div id="myModalBody" class="modal-body">
				<!--checkBlank()-->
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="checkModal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
	<!-- <div class="modal-content"> -->
		<div class="modal-body" style="position:absolute; top: 200px; padding: 30px; background-color: white; border-radius: 4px;">
			인증번호 : <input id="checkStr" placeholder="인증번호를 입력해주세요." style="width: 250px; margin: 0 10px;">
			<button id="checkMailSend" class="btn btn-info">메일전송</button>
			<button id="checkBtn" class="btn btn-default">확인</button>
			<button id="closeBtn" class="btn btn-default">닫기</button>
		</div>
	<!-- </div>		 -->	
	</div>
</div>
<!-- KAKAO 주소검색 -->
<script>
window.onload = function(){
    document.getElementById("searchAddressBtn").addEventListener("click", function(e){
    	e.preventDefault();
        new daum.Postcode({
            oncomplete: function(data) {
            	document.getElementById("zonecode").value = data.zonecode;
                document.getElementById("address_kakao").value = data.address;
                $("input[name='addressDetail']").focus(); 
            }        
        }).open();
    
        new daum.Postcode({onclose: function(state) {if(state === 'FORCE_CLOSE'){} else if(state === 'COMPLETE_CLOSE'){}}});
    });
}
</script>
<!-- 본문 -->
<script type="text/javascript">
$(document).ready(function(){	
	/* form 제출 */
	$("#joinBtn").on("click", function(e){
		e.preventDefault();
		checkInfo();			
	});
	
	/* 체크박스 : 약관, 동의서 동의여부 체크 */
	$("#checkbox").on("change", function(){        
		if (checkbox.checked) {
			checkbox.value = "Y"; 	        
		} else {
			checkbox.value = "N";
		} 
	});
	
	//"동의합니다" 클릭이벤트
	$("#agree").on("mousedown", function(e){
		e.preventDefault();
		var checkbox = $("#checkbox");
		
		if(checkbox.prop("checked")){ //prop:property(속성) checked가 true/false를 변화시킴
			checkbox.prop("checked", false); //만일 체크되어있으면 해제
			checkbox.val("N");
		} else {
			checkbox.prop("checked", true);  //아니면 체크
			checkbox.val("Y");
		}
	});

	/* 프로필사진 업로드 */
	//업로드 상세처리(확장자, 크기 등) 
	var regex = new RegExp("(.*?)\.(jpg|jpeg|png|gif)$"); //업로드 가능 확장자
	var maxSize = 5242880; //5MB
	
	function checkFile(fileName, fileSize){
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과 : 최대 5MB까지 업로드 가능합니다.");
			return false;
		}
		if(!regex.test(fileName)){
			alert("jpg,jpeg,png,gif 확장자만 업로드 가능합니다.");
			return false;
		}		
		return true; 
	}
			
	//찾아보기 버튼 클릭 이벤트 처리 
	$("#findImgBtn").click(function(e){
		e.preventDefault();	    
	    $("input[type='file']").click();//파일 선택 input 클릭	    
	});
	
	//파일업로드 : 상태가 변하면 전송 
	$("input[type='file']").change(function(){
        var formData = new FormData();
        var inputFile = $("input[name='singleFile']");
        var file = inputFile[0].files[0];
        var fileName = file.name;
        var fileSize = file.size;
        formData.append('singleFile', file);
                
        //파일 검증
        if(!checkFile(fileName, fileSize)) {
            //uploadProfile.html("<img id='profile_small' src='/display?fileName="+prevFile+"'>");	
            return false;
        }
       
     	//파일을 서버로 전송
        $.ajax({
            url: '/uploadSingle',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
            success: function(result) {        
            	console.log(result);
	            //XML을 jQuery 객체로 변환
                var Img = $(result);
                //XML에서 필요한 데이터를 가져옴
                var uploadPath = Img.find('uploadPath').text();
                var uuid = Img.find('uuid').text();
                var fileName = Img.find('fileName').text();
                var fileType = (Img.find('fileType').text() === "true") ? true : false;
                //화면출력
                var reader = new FileReader();                                
                reader.onload = function(){
					var fileCallPath = encodeURIComponent(uploadPath+"/"+uuid+"_"+fileName);
					var DeleteFilePath = encodeURIComponent(uploadPath+"/s_"+uuid+"_"+fileName); //기존의 /deleteFile는 썸네일을 지우는 용도, 파일명 쪼개서 "s_"를 붙이는것보단 지우는게 쉽다.
					//var DeleteFilePath = uploadPath+"/s_"+uuid+"_"+fileName; //기존의 /deleteFile는 썸네일을 지우는 용도, 파일명 쪼개서 "s_"를 붙이는것보단 지우는게 쉽다.
					var ImgTag = fileType === true ? '<img id="profile" src="/display?fileName='+fileCallPath+'">' : '<img id="profile" src="/resources/img/Default-Profile.png">'; //그럴리없겠지만 이미지타입이 아닌 경우, 기본프로필사진
					console.log(ImgTag);
					console.log(fileType);
                    $(".uploadProfile").attr({"data-path":uploadPath, "data-uuid":uuid, "data-filename":fileName, "data-type":fileType, "data-file":fileCallPath});
                    $(".uploadProfile").html(ImgTag);
                    $("#resetImgBtn").attr({"data-file": DeleteFilePath, "data-type": fileType});//초기화버튼으로 정보전달
                    
            		var profileData = $(".uploadProfile");//업로드된 이미지 정보 가져오기        		
            		//hidden input에 이미지 정보 설정
            		$("input[name='profileImg.uploadPath']").val(profileData.data("path"));
            		$("input[name='profileImg.uuid']").val(profileData.data("uuid"));
            		$("input[name='profileImg.fileName']").val(profileData.data("filename"));            		           		
                }
                reader.readAsDataURL(file);
             
                $(".uploadProfile").css('background-image', 'none');//기본프로필사진 화면에서 삭제

            },
            error: function(xhr, status, error) {
                console.error('프로필 사진 업로드 실패:', error);
            }
        });
    });	
	
	//업로드 취소-업로드파일삭제
	$("#resetImgBtn").on("click", function(e){
	    e.preventDefault();
	    	    
	    var defaultPath = "profile", 
	    	defaultUuid = "ed87212c-4e79-4813-be6c-8c73ac58ac33",
	    	defaultName = "Default-Profile.png",
	    	defaultType = "image",
	    	defaultCallPath = encodeURIComponent(defaultPath+"/"+defaultUuid+"_"+defaultName);
	    
		//프로필 이미지 초기화
        $(".uploadProfile").attr({"data-path":defaultPath, "data-uuid":defaultUuid, "data-filename":defaultName, "data-type":defaultType, "data-file":defaultCallPath});
        $(".uploadProfile").html('<img id="profile" src="/display?fileName='+defaultCallPath+'" style="width:200px; height:200px">');
        
	    //서버로 파일 삭제 요청 보내기
	    var fileName = $(this).data("file"); //삭제할 파일 이름 가져오기, 사진은 원본이지만 이름은 썸네일
	    console.log("-----------------삭제:"+fileName);
	    
	    //설정된 파일이 없다면 리턴
	    if(fileName == null){
	    	console.log("실패");
	    	return false;
	    }
	    
	    $.ajax({
	        type: 'POST',
	        url: '/deleteFile',
	        data: {fileName: fileName, type: "image"}, 
	        beforeSend:function(xhr){
	            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
	        },
	        success: function(result){
	            console.log('파일 삭제 성공');
	            $("input[name='profileImg.uploadPath']").val(defaultPath);	            
	            $("input[name='profileImg.uuid']").val(defaultUuid);
	            $("input[name='profileImg.fileName']").val(defaultName);	            
	        },
	        error: function(xhr, status, error) {
	            console.error('파일 삭제 실패:', error);
	        }
	    });
	});	
	
	//프로필사진 클릭시 이벤트 처리 
	$(".uploadProfile").on("click", function(e){				
		var img = $(this);
		console.log(img);
		var path = encodeURIComponent(img.data("path")+"/"+img.data("uuid")+"_"+img.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명
		showImage(path.replace(new RegExp(/\\/g),"/")); //이미지파일 : showImage함수 실행
	});
	
	//원본사진 확대보기 on
	function showImage(fileCallPath){			
		$(".picWrap").css("display","flex").show(); //none > flex 설정 변경
		$(".pic").html("<img src='/display?fileName="+fileCallPath+"'>").animate({width:'100%', height:'100%'}, 0);//pic의 html속성은 controller의 display메서드, animate는 크기변경(배경창 100%*100%) 0.3초 후 실행 
	}
	
	//원본사진 확대보기 off 
	$(".picWrap").on("click", function(e){
		$(".pic").animate({width:'0%', height:'0%'}, 0); //(0%*0%) 로 0.3초 후 크기변경
		setTimeout(() => {$(this).hide();}, 0);	//chrome의 ES6화살표함수
		//IE : setTimeout(function(){$('.picWrap').hide();}, 300);
	});
	/*
	//프로필사진 오프셋 조정
	var profile = $('#profile');
    var offsetX = 0;
    var offsetY = 0;
    var isDragging = false;

    //마우스를 눌렀을 때 이벤트 처리
    profile.mousedown(function(e) {
    	console.log("push");
        isDragging = true;
        offsetX = e.offsetX;
        offsetY = e.offsetY;        
    });

    //마우스를 뗐을 때 이벤트 처리
    $(document).mouseup(function() {
    	console.log("up");
        isDragging = false;
    });

    //마우스를 움직였을 때 이벤트 처리
    $(document).mousemove(function(e) {
        if (isDragging) {
        	console.log("drag");
            var x = e.clientX - offsetX;
            var y = e.clientY - offsetY;
            $profileImage.css({ left: x + 'px', top: y + 'px' });
        }
    });
	*/     
	/* modal창 설정 */
	function modal(element){
		$("#myModal").modal("show");
		setTimeout(function() { $("#myModal").modal("hide"); }, 800);
		setTimeout(function() { $(element).focus(); }, 900);
	}
	
	/* 유효성 검사 후 제출 */			
	function checkInfo() {		
		//입력칸 
		var userid = $("input[name='userid']").val();
		var userpw = $("input[name='userpw']").val();
		var pwconfirm = $("input[name='pwconfirm']").val();
		var name = $("input[name='name']").val();
		var gender = $("select[name='gender']").val();
		var phone = $("input[name='phone']").val();
		var email = $("input[name='email']").val();
		var address = $("input[name='address']").val();
		var birth = $("input[name='birth']").val();
				
		//입력규칙
	    var reg_id = /^[a-zA-Z][0-9a-zA-Z].{5,8}$/;
	    var reg_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]+$/;
	    var reg_phone = /^\d{2,3}-\d{3,4}-\d{4}$/;
	    var reg_pw = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*?[!@#$%^&*]).{6,12}/;
	    
		//아이디
		if (userid === "") {
			$(".modal-body").html("<p>아이디를 입력해주세요</p>");			
			modal("input[name='userid']");
		    return; 
		} else if(!reg_id.test(userid)) {	    	
	    	$("#myModalBody").html("<p>아이디는 영문자 6~8자리로 작성해주세요</p>");
	    	modal("input[name='userid']");     	
	        return;
	    } 
		
		//아이디 중복 여부 확인
		if($("#idCheck").val() == "N"){
			$("#myModalBody").html("<p>아이디 중복여부를 확인해주세요</p>");
			modal("#idCheck");
		    return;					
		}

		//비밀번호
		if(userpw === ""){
	    	$("#myModalBody").html("<p>비밀번호를 입력해주세요</p>");		
			modal("input[name='userpw']");
		    return;
    	} else if(!reg_pw.test(userpw)){    		
	    	$("#myModalBody").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='userpw']");  	    
	        return;
    	}   	
		
	    //비밀번호 확인
	    if (pwconfirm === "") {
    		$("#myModalBody").html("<p>비밀번호 확인란을 입력해주세요</p>");		
			modal("input[name='pwconfirm']");
		    return;	    		
	    } else if(!reg_pw.test(pwconfirm)){    		
	    	$("#myModalBody").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='pwconfirm']");  	    
	        return;
    	}		
	    
		//비밀번호 일치여부 체크
		if($("#pwCheck").val() == "N"){
			$("#myModalBody").html("<p>비밀번호 일치여부를 확인해주세요</p>");
			modal("#pwCheck"); 
		    return;					
		}		
				
		//이름
		if (name === "") {				
			$("#myModalBody").html("<p>이름을 입력해주세요</p>");			
			modal("input[name='name']");
		    return;				
		}
		
		//성별
		if (gender === null || gender === "") {				
			$("#myModalBody").html("<p>성별을 체크해주세요</p>");
			modal("select[name='gender']");
		    return;				
		}
		
		//연락처
		if (phone === "") {				
			$("#myModalBody").html("<p>전화번호를 입력해주세요</p>");
			modal("input[name='phone']");
		    return;				
		} else if(!reg_phone.test(phone)){
			$("#myModalBody").html("<div align='center' style='font-size:16px; margin:15px'>전화번호 형식에 맞게 입력해주세요<br>ex)010-1234-5678</div>");
			modal("input[name='phone']"); 	        
		   	return;
		}
				
		//이메일
		if (email === "") {				
			$("#myModalBody").html("<p>이메일을 입력해주세요</p>");
			modal("input[name='email']"); 
		    return;				
		} else if(!reg_email.test(email)){
			$("#myModalBody").html("<p>이메일 형식에 맞게 입력해주세요</p>");
			modal("input[name='email']"); 	        
		   	return;
		}
		
		//이메일 본인인증 여부 확인
		if($("#checkModalBtn").val() == "N"){
			$("#myModalBody").html("<p>이메일 본인인증을 진행해주세요</p>");
			modal("#checkModalBtn");
		    return;					
		}
		
		//주소
		if (address === "") {				
			$("#myModalBody").html("<p>주소를 입력해주세요</p>");
			modal("input[name='address']"); 
		    return;				
		}
		
		//생년월일
		if (birth === "") {				
			$("#myModalBody").html("<p>생년월일을 입력해주세요</p>");
			modal("input[name='birth']"); 
		    return;				
		}
		
		//생년월일이 오늘 이후인지 확인
	    var birthDay = new Date(birth);
	    var today = new Date();
	    if (birthDay > today) {	    	
	    	$("#myModalBody").html("<p>오늘 이전의 날짜를 선택해주십시오.</p>");
	    	modal("input[name='birth']"); 	
	        return;
	    }
	    
	 	//약관 및 동의서 체크여부
		if(checkbox.value == "N"){
			$("#myModalBody").html("<p>가입약관 및 개인정보이용동의서를 확인해주세요</p>");
			modal("#checkbox"); 
		    return;					
		}	 	
	    
		$("form").submit(); //제출
		$("form")[0].reset();
	}		
	
	/* 아이디 중복체크 */
	$("#idCheck").on("click", function(e) {
		e.preventDefault();
		var userid = $("input[name='userid']").val();		
		var reg_id = /^[a-zA-Z][0-9a-zA-Z].{5,8}$/;		
		
		//아이디 빈칸 & 형식 체크
	   	if(userid === ""){	   		
	    	$("#myModalBody").html("<p>아이디를 작성해주세요</p>");
	    	modal("input[name='userid']"); 
	     	return;
   		} else if(!reg_id.test(userid)) {	      	
    		$("#myModalBody").html("<p>6~8자의 영문자로 시작하는 아이디를 입력하세요</p>");
    		modal("input[name='userid']"); 
	      	return;
	   	}
	   	
		//아이디 중복 확인
	   	$.ajax({
			url: "/user/checkId",
			type: "post",
			dataType: "json",
			data: {"userid": userid},
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
			success: 
				function(result){
				if(result === 1) {
		    		$("#myModalBody").html("<p>이미 사용중인 아이디입니다.</p>");					
		    		modal("input[name='userid']");
				} else if(result === 0) {					
		    		$("#myModalBody").html("<p>사용가능한 아이디입니다.</p>");
		    		modal("input[name='userid']");
		    		$("#idCheck").attr("value", "Y");
				}
			},
			error: function(){				
	    		$("#myModalBody").html("<p>서버오류가 발생했습니다.</p>");
				modal("input[name='userid']");
			}
		});		
	});
	
	/* 비밀번호 확인(유효성 및 일치여부 확인) */
	$("#pwCheck").on("click", function(e) {
		e.preventDefault();
	    var userpw = $("input[name='userpw']").val();
	    var pwconfirm = $("input[name='pwconfirm']").val();
	    var reg_pw = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*?[!@#$%^&*]).{6,12}/; 
	    
	    //비밀번호 빈칸 & 형식
	    if(userpw === ""){
	    	$("#myModalBody").html("<p>비밀번호를 입력해주세요</p>");		
			modal("input[name='userpw']");
		    return;
    	} else if(!reg_pw.test(userpw)){    		
	    	$("#myModalBody").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='userpw']");  	    
	        return;
    	}   	
	    
	 	//비밀번호 확인 빈칸 & 형식
	    if (pwconfirm === "") {
    		$("#myModalBody").html("<p>비밀번호 확인란을 입력해주세요</p>");		
			modal("input[name='pwconfirm']");
		    return;	    		
	    } else if(!reg_pw.test(pwconfirm)){    		
	    	$("#myModalBody").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='pwconfirm']");  	    
	        return;
    	}	    
	    
		//비밀번호 일치여부 확인
    	if (userpw === pwconfirm) {
    		$("#myModalBody").html("<p>비밀번호가 일치합니다</p>");
    		modal("input[name='pwconfirm']");
			$("#pwCheck").attr("value", "Y");
	    } else {
    		$("#myModalBody").html("<p>비밀번호가 일치하지 않습니다. 올바르게 입력해주세요</p>");
	        $("input[name='pwconfirm']").val("");			 	        
    		modal("input[name='pwconfirm']");
	    }
	});
	
	$("#checkModalBtn").on("click", function(e){
		e.preventDefault();
		
		var reg_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]+$/;
		var email = $("input[name='email']");
		
		if (email.val() === "") {				
			$("#myModalBody").html("<p>이메일을 입력해주세요</p>");
			modal(email); 
		    return;				
		} else if(!reg_email.test(email.val())){
			$("#myModalBody").html("<p>이메일 형식에 맞게 입력해주세요</p>");
			modal(email); 	        
		   	return;
		}
		
		$("#checkModal").modal("show");
	})
	
	$("#checkModal").on("click", "#checkMailSend", function(){	
		var email = $("input[name='email']").val();
		
		$.ajax({
			url:"/user/checkMailSend",
			type:"post",
			data:{"email":email},										
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success: function(result){
				if(result === "success"){					
					alert("사용자님의 이메일("+email+")로 임시비밀번호가 발송되었습니다.");
				} 				
			},
			error:function(request, status, error){
				alert("오류로 인해 메일전송에 실패하였습니다. 다시시도해주십시오");
				$("#checkStr").val("");
				setTimeout(function() { $("#checkModal").modal("hide"); }, 200);
				return false;
			} 
		});	   
	});
	
	// 이벤트위임
    $("#checkModal").on("click", "#checkBtn", function() {
    	var ranStr = $("#checkStr").val();    	
	    
		if(ranStr == ""){
			alert("인증번호를 입력해주세요");
			$("#checkStr").focus();
			return;
		}
		
		//아이디 중복 확인
	   	$.ajax({
			url: "/user/checkMailUser",
			type: "post",
			dataType: "json",
			data: {"ranStr": ranStr},
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
			success: 
				function(result){
				if(result === 1) {					
					alert("인증번호 일치");
					$("#checkModalBtn").attr("value", "Y");
					$("#checkStr").val("");
		    		$("#checkModal").modal("hide");
				} else if(result === 0) {					
					alert("인증번호 불일치");
					$("#checkStr").val("");
		    		return false;
				}
			},
			error: function(){
	    		alert("서버오류가 발생했습니다. 다시 시도해주십시오.");	
	    		$("#checkStr").val("");
	    		return false;
			}
		});		
    });
	
 // 이벤트위임
    $("#checkModal").on("click", "#closeBtn", function(e) {
    	e.preventDefault();
    	$("#checkStr").val("");
    	setTimeout(function() { $("#checkModal").modal("hide"); }, 100);
    });    
});	
</script>

<%@ include file="../includes/footer.jsp"%>