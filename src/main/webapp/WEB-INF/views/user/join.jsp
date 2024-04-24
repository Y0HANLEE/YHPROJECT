<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="../includes/header.jsp"%>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header">Join us!!</h1>
	</div>
	<p class="mb-4" style="padding-left: 20px">
		회원가입을 하시면 더 많은 컨텐츠를 즐길 수 있습니다.
	</p>	
</div>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Form</div>
			<div class="panel-body">				
				<form role="form" method="post" action="/user/join">					
				<div class="col-lg-6">				
					<div class="form-group">						
						<p><label for="userid">아이디</label> <button type="button" class="btn btn-outline btn-danger btn-xs" id="idCheck" value="N">CHECK</button></p>
						<input class="form-control" type="text" name="userid" id="userid" placeholder="ID" autofocus>							
					</div>						
					<div class="form-group">
						<label for="userpw">비밀번호</label>
						<input class="form-control" type="password" name="userpw" id="userpw" placeholder="Password">
					</div>
					<div class="form-group">
						<div><label for="pwconfirm">비밀번호확인</label> <button type="button" class="btn btn-outline btn-danger btn-xs" id="pwCheck" value="N">CHECK</button></div>
						<input class="form-control" type="password" name="pwconfirm" id="pwconfirm" placeholder="Password Again">
					</div>
					<div class="form-group">
						<label for="name">이름</label>
						<input class="form-control" type="text" name="name" placeholder="Name">
					</div>
					<div class="form-group">
						<label for="gender">성별</label>
						<select class="form-control" id="gender" name="gender">
							<option value="" selected disabled>성별을 선택해주세요.</option>
							<option value="M">남자</option>
							<option value="W">여자</option>																
						</select>
					</div>
					<div class="form-group">
						<label for="phone">전화번호</label>
						<input class="form-control" type="text" id="phone" name="phone" placeholder="Phone Number">
					</div>
					<div class="form-group">
						<label for="email">이메일</label>
						<input class="form-control" type="email" id="email" name="email" placeholder="Email Address">
					</div>
					<div class="form-group">
							<label for="address">주소</label>
						<div style="display:flex; margin-bottom: 5px;">
							<input class="form-control" name="zonecode" id="zonecode" placeholder="우편번호" style="width:30%" readonly>&nbsp;&nbsp;
							<button id="searchAddressBtn" class="btn btn-default" style="width:20%; height:34px">주소찾기</button>						
						</div>
						<input style="margin-bottom: 5px;" class="form-control" type="text" id="address_kakao" name="address" placeholder="주소" readonly>
						<input class="form-control" type="text" name="addressDetail" placeholder="상세주소">
					</div>
					<div class="form-group">
						<label for="birth">생년월일</label>
						<input class="form-control" type="date" id="birth" name="birth">
					</div>
				</div>
				
				<div class="col-lg-6" align="center" style="margin-top: 20px">
					<div class="form-group" style="height:273.266px">
						<label for="profileImage">프로필사진</label><br>
    					<img id="profileImagePreview" src="/resources/img/Default-Profile.png" alt="프로필 이미지" style="max-width:150px; max-height:200px;"><br> <!-- 기본 프로필이미지 -->						
						<input class="form-control" type="file" id="profileImage" name="profileImage" style="display: none;"> <!-- 파일 선택 -->
						<button class="btn btn-default" id="findImage" style="margin-top:5px" >찾아보기</button>
					</div>					
					<div class="form-group" align="left" style="margin-top: 25px">
						<label for="paper">홈페이지 가입약관 및 개인정보이용동의서</label><br>
						<textarea class="form-control" rows="14" cols="60" id="paper" name="paper" readonly>
<%@ include file="agree.jsp"%>
						</textarea>
						<div>
							<input type="checkbox" style="vertical-align: middle;" id="checkbox" value="N">
							<label style="vertical-align: middle; margin-top:0px">동의합니다.</label>
						</div>				
					</div>
					<!-- <img src=''> -->
					
				</div>		
				<div align="center">
					<button class="btn btn-lg btn-success btn-block" style="width:300px; align-content: center;" id="join">회원가입</button>
				</div>									
				<!-- 보안:사이트간 요청 위조방지. spring security에서 post방식을 이용하는 경우 사용.-->
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
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
window.onload = function(){
    document.getElementById("searchAddressBtn").addEventListener("click", function(e){
    	e.preventDefault();
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
            	document.getElementById("zonecode").value = data.zonecode; // 우편번호 넣기
                document.getElementById("address_kakao").value = data.address; // 주소 넣기                
                //document.querySelector("input[name=address_detail]").focus(); //상세입력 포커싱
                $("input[name='addressDetail']").focus(); //상세입력 포커싱
            }        
        }).open();
    
        new daum.Postcode({
            onclose: function(state) {
                //state는 우편번호 찾기 화면이 어떻게 닫혔는지에 대한 상태 변수 이며, 상세 설명은 아래 목록에서 확인하실 수 있습니다.
                if(state === 'FORCE_CLOSE'){ //x버튼

                } else if(state === 'COMPLETE_CLOSE'){ // 검색결과를 선택 - oncomplete 콜백 함수가 실행 완료된 후에 실행됩니다.
                	
                	
                }
            }
        });
    });
}
</script>

<script type="text/javascript">
$(document).ready(function(){	
	/* form 제출 */
	$("#join").on("click", function(e){
		e.preventDefault();		
		var code = $("input[name=zonecode]")
		alert()
		checkInfo();			
	});
	
	
	var regex = new RegExp("/(\.jpg|\.jpeg|\.png)$/i"); // 업로드 가능 확장자
	var maxSize = 2097152; // 최대크기 2MB
	var cloneObj = $(".uploadDiv").clone(); // 클론
	
	function checkFile(fileName, fileSize){
		// 파일사이즈 검토
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과 : 2MB 이하의 사진을 업로드해주세요");
			return false;
		}
		
		// 파일이름(확장자) 검토
		if(!regex.test(fileName)){
			alert("해당 확장자는 업로드 할 수 없습니다.");
			return false;
		}
		
		return true; // 성공시
	}
	
	/*프로필 이미지 찾기*/
	$("#findImage").on("click", function(e){
		e.preventDefault();
		var inputFile = $("input[id='profileImage']");//첨부된 파일
		var file = inputFile.files;
		if(!checkFile(file.name, file.size)){
			//$(".uploadDiv").html(cloneObj.html()); // 제한에 걸릴 시, 초기상태("선택 파일 없음")로 보여지기 위해 초기화면을 clone으로 복사하여 붙여넣기함.
			return false;
		}
		$("#profileImage").click(); // 파일 선택 input을 클릭하여 파일 선택 창 열기
    });
	
    // 파일 선택 input의 change 이벤트를 감지하여 선택된 파일의 미리보기
	$("#profileImage").on("change", function(){
		var input = this;
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function (e) {
				$('#profileImagePreview').attr('src', e.target.result); // 파일의 미리보기 이미지를 설정
			}
			reader.readAsDataURL(input.files[0]);
		}
	});   
	
    /* 체크박스 : 약관, 동의서 동의여부 체크 */
	$("#checkbox").on("change", function(){        
		if (checkbox.checked) {
			checkbox.value = "Y"; 	        
		} else {
			checkbox.value = "N";
		} 
	});
    
	/* modal창 설정 */
	function modal(element){
		$("#myModal").modal("show");
		setTimeout(function() { $("#myModal").modal("hide"); }, 800);
		setTimeout(function() { $(element).focus(); }, 900);
	}
	
	/* 빈칸 및 형식 체크 후 제출 */			
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
				
		// 입력규칙
	    var reg_id = /^[a-zA-Z][0-9a-zA-Z].{5,8}$/;
	    var reg_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]+$/;
	    var reg_pw = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*?[!@#$%^&*]).{6,12}/;
	    
		// 아이디
		if (userid === "") {
			$(".modal-body").html("<p>아이디를 입력해주세요</p>");			
			modal("input[name='userid']");
		    return; 
		} else if(!reg_id.test(userid)) {
	    	$(".modal-title").html("<p>아이디 작성규칙 오류</p>");
	    	$(".modal-body").html("<p>아이디는 영문자 6~8자리로 작성해주세요</p>");
	    	modal("input[name='userid']");     	
	        return;
	    } 
		
		// 아이디 중복 여부 확인
		if($("#idCheck").val() == "N"){
			$(".modal-body").html("<p>아이디 중복여부를 확인해주세요</p>");
			modal("#idCheck");
		    return;					
		}

		//비밀번호
		if(userpw === ""){
	    	$(".modal-body").html("<p>비밀번호를 입력해주세요</p>");		
			modal("input[name='userpw']");
		    return;
    	} else if(!reg_pw.test(userpw)){
    		$(".modal-title").html("<p>비밀번호 작성규칙 오류</p>");
	    	$(".modal-body").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='userpw']");  	    
	        return;
    	}   	
		
	    //비밀번호 확인
	    if (pwconfirm === "") {
    		$(".modal-body").html("<p>비밀번호 확인란을 입력해주세요</p>");		
			modal("input[name='pwconfirm']");
		    return;	    		
	    } else if(!reg_pw.test(pwconfirm)){
    		$(".modal-title").html("<p>비밀번호 작성규칙 오류</p>");
	    	$(".modal-body").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='pwconfirm']");  	    
	        return;
    	}		
	    
		// 비밀번호 일치여부 체크
		if($("#pwCheck").val() == "N"){
			$(".modal-body").html("<p>비밀번호 일치여부를 확인해주세요</p>");
			modal("#pwCheck"); 
		    return;					
		}		
		
		if (name === "") {				
			$(".modal-body").html("<p>이름을 입력해주세요</p>");			
			modal("input[name='name']");
		    return;				
		}
		
		if (gender === null || gender === "") {				
			$(".modal-body").html("<p>성별을 체크해주세요</p>");
			modal("select[name='gender']");
		    return;				
		}
		
		if (phone === "") {				
			$(".modal-body").html("<p>전화번호를 입력해주세요</p>");
			modal("input[name='phone']");
		    return;				
		}
		
		//이메일
		if (email === "") {				
			$(".modal-body").html("<p>이메일을 입력해주세요</p>");
			modal("input[name='email']"); 
		    return;				
		} else if(!reg_email.test(email)){
			$(".modal-body").html("<p>이메일 형식에 맞게 입력해주세요</p>");
			modal("input[name='email']"); 	        
		   	return;
		}
		
		if (address === "") {				
			$(".modal-body").html("<p>주소를 입력해주세요</p>");
			modal("input[name='address']"); 
		    return;				
		}
		
		if (birth === "") {				
			$(".modal-body").html("<p>생년월일을 입력해주세요</p>");
			modal("input[name='birth']"); 
		    return;				
		}	   
	    
		// 생년월일이 오늘 이후인지 확인
	    var birthDay = new Date(birth);
	    var today = new Date();
	    if (birthDay > today) {
	    	$(".modal-title").html("<p>생년월일 입력 오류</p>");
	    	$(".modal-body").html("<p>오늘 이전의 날짜를 선택해주십시오.</p>");
	    	modal("input[name='birth']"); 	
	        return;
	    }
	    
	 	// 약관 및 동의서 체크여부
		if(checkbox.value == "N"){
			$(".modal-body").html("<p>가입약관 및 개인정보이용동의서를 확인해주세요</p>");
			modal("#checkbox"); 
		    return;					
		}
	    
		$("form").submit(); // 제출
	}		
	
	/* 아이디 중복체크 */
	$("#idCheck").on("click", function() {		
		var userid = $("input[name='userid']").val();		
		var reg_id = /^[a-zA-Z][0-9a-zA-Z].{5,8}$/;		
		
		//아이디 빈칸 & 형식 체크
	   	if(userid === ""){	   		
	    	$(".modal-body").html("<p>아이디를 작성해주세요</p>");
	    	modal("input[name='userid']"); 
	     	return;
   		} else if(!reg_id.test(userid)) { 
	      	$(".modal-title").html("<p>아이디 작성규칙 오류</p>");
    		$(".modal-body").html("<p>6~8자의 영문자로 시작하는 아이디를 입력하세요</p>");
    		modal("input[name='userid']"); 
	      	return;
	   	}
	   	
		// 아이디 중복 확인
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
					$(".modal-title").html("<p>아이디 중복 오류</p>");
		    		$(".modal-body").html("<p>이미 사용중인 아이디입니다.</p>");					
		    		modal("input[name='userid']");
				} else if(result === 0) {					
		    		$(".modal-body").html("<p>사용가능한 아이디입니다.</p>");
		    		modal("input[name='userid']");
		    		$("#idCheck").attr("value", "Y");
				}
			},
			error: function(){
				$(".modal-title").html("<p>Server Error</p>");
	    		$(".modal-body").html("<p>서버오류가 발생했습니다.</p>");
				modal("input[name='userid']");
			}
		});		
	});
	
	/* 비밀번호 확인(형식 체크 및 일치 체크) */
	$("#pwCheck").on("click", function() {		    
	    var userpw = $("input[name='userpw']").val();
	    var pwconfirm = $("input[name='pwconfirm']").val();
	    var reg_pw = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*?[!@#$%^&*]).{6,12}/; 
	    
	    // 비밀번호 빈칸 & 형식체크
	    if(userpw === ""){
	    	$(".modal-body").html("<p>비밀번호를 입력해주세요</p>");		
			modal("input[name='userpw']");
		    return;
    	} else if(!reg_pw.test(userpw)){
    		$(".modal-title").html("<p>비밀번호 작성규칙 오류</p>");
	    	$(".modal-body").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='userpw']");  	    
	        return;
    	}   	
	    
	 	// 비밀번호 확인 빈칸 & 형식체크
	    if (pwconfirm === "") {
    		$(".modal-body").html("<p>비밀번호 확인란을 입력해주세요</p>");		
			modal("input[name='pwconfirm']");
		    return;	    		
	    } else if(!reg_pw.test(pwconfirm)){
    		$(".modal-title").html("<p>비밀번호 작성규칙 오류</p>");
	    	$(".modal-body").html("<p>비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)<br>포함 6~12자로 작성해주세요</p>");
	    	modal("input[name='pwconfirm']");  	    
	        return;
    	}	    
	    
	 // 비밀번호 일치여부 확인
    	if (userpw === pwconfirm) {
    		$(".modal-body").html("<p>비밀번호가 일치합니다</p>");
    		modal("input[name='pwconfirm']");
			$("#pwCheck").attr("value", "Y");
	    } else {	        
	    	$(".modal-title").html("<p>비밀번호 불일치</p>");
    		$(".modal-body").html("<p>비밀번호를 올바르게 입력해주세요</p>");
	        $("input[name='pwconfirm']").val("");			 	        
    		modal("input[name='pwconfirm']");
	    }
	});
});	
</script>

<%@ include file="../includes/footer.jsp"%>