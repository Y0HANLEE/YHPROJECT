<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>	
<%@ include file="../includes/header.jsp"%>
<title>회원정보수정</title>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px">
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-user fa-fw"></i><strong><c:out value="${user.name}"/></strong><c:choose><c:when test="${auth.auth == '1'}">(관리자)</c:when><c:when test="${auth.auth == '2'}">(운영자)</c:when><c:when test="${auth.auth == '3'}">(일반회원)</c:when></c:choose>님의 회원정보 수정페이지</div>
			<div class="panel-body">				
				<div class="col-lg-6">
				<!-- action:login 은 spring security 기본제공 / name, password도 spring security의 기본설정명 -->
				<form role="form" method="post" action="/user/update" id="updateForm">					
					<div class="form-group">						
						<p><label for="userid">아이디</label></p>
						<input class="form-control" name="userid" id="userid" value="${user.userid}" readonly>							
					</div>							
					<div class="form-group">
						<label for="name">이름</label>
						<input class="form-control" name="name" value="${user.name}" readonly>						
					</div>
					<div class="form-group">
						<label for="gender">성별</label>
						<input class="form-control" name="gender" value="${user.gender=='M'?'남자':'여자'}" readonly>
					</div>
					<div class="form-group">
						<label for="phone">전화번호</label>
						<input class="form-control" name="phone" value="${user.phone}">
					</div>
					<div class="form-group">
						<label for="email">이메일</label>
						<input class="form-control" type="email" name="email" value="${user.email}">
					</div>
					<div class="form-group">
							<label for="address">주소</label>
						<div style="display:flex; margin-bottom: 5px;">
							<input class="form-control" name="zonecode" id="zonecode" placeholder="우편번호" style="width:30%" value="${user.zonecode}" readonly>&nbsp;&nbsp;
							<button id="searchAddressBtn" class="btn btn-default"  style="width:20%; height:34px">주소찾기</button>						
						</div>
						<input style="margin-bottom: 5px;" class="form-control" type="text" id="address_kakao" value="${user.address}" name="address" readonly>
						<input class="form-control" type="text" name="addressDetail" placeholder="상세주소" value="${user.addressDetail}" >
					</div>
					<div class="form-group">
						<label for="birth">생년월일</label>
						<input class="form-control" name="birth" value="<fmt:formatDate pattern="yyyy-MM-dd" value='${user.birth}'/>" readonly>
					</div>					
					<div class="form-group" style="margin-top: 30px">
						<button class="btn btn-lg btn-info btn-lg" style="width:33%" id="updateBtn">수정완료</button>
						<button class="btn btn-lg btn-default btn-lg" style="width:32%" id="passBtn">비밀번호변경</button>						
						<button class="btn btn-lg btn-default btn-lg " style="width:33%; padding-right:0px;" id="backBtn">뒤로가기</button>
					</div>
									
					<!-- 보안:사이트간 요청 위조방지. spring security에서 post방식을 이용하는 경우 사용.-->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
				</form>
				</div>
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
<div class="modal fade" id="passModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header"> 
				<h3 class="modal-title">비밀번호변경</h3> 
			</div>
			<form role="form" id="updatePwForm" method="post" action="/user/updatePw">
			<div class="modal-body">
		    	<div class="form-group">
			        <label for="oldPw">기존 비밀번호</label>
			        <input type="password" class="form-control" id="oldPw" name="oldPw">
		    	</div>        
		    	<div class="form-group">
			        <label for="newPw">새 비밀번호</label>
			        <input type="password" class="form-control" id="newPw" name="newPw">
		        </div>
		        <div class="form-group">
			        <label for="pwconfirm">새 비밀번호 확인</label>
			        <input type="password" class="form-control" id="pwconfirm" name="pwconfirm">
		        </div>			       			        
		    </div>
			<div class="modal-footer" align="center">
				<button type="button" class="btn btn-lg btn-success btn-lg" id="upPwBtn">수정</button>
				<button type="button" class="btn btn-lg btn-default btn-lg" id="closeBtn">취소</button>			       	
			</div>
	        <input type="hidden" id="PwUpuserid" name="userid" value="${user.userid}">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		    </form>
		</div>
	</div>
</div>
<!-- KAKAO주소 -->
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
<!-- web -->
<script type="text/javascript">
$(document).ready(function(){
	
	var result = '<c:out value="${result}"/>';
	
	checkModal(result);
	
	/* 뒤로가기 문제해결 */
	history.replaceState({},null,null);	
	
	// 회원정보 수정완료 알림
	function checkModal(result) {
		if(result === "" || history.state){ 
			return;
		}				
		$(".modal-title").html("Success");
		$(".modal-body").html(result);
		$(".modal-footer").html('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
		$("#myModal").modal("show");
		setTimeout(function() { $("#myModal").modal("hide"); }, 800);		
	} 	
	
	/* 수정완료버튼 */
	$("#updateBtn").on("click", function(e){
		e.preventDefault();
		checkInfo();// 수정 정보 유효성 검사 실행
	});	
	
	/* 뒤로가기버튼 */
	$("#backBtn").on("click", function(e){
		e.preventDefault();
		window.history.go(-1); 		
	});
		
	/* 비밀번호 변경 버튼 */
	$("#passBtn").on("click", function(e){
		e.preventDefault();
		$("#passModal").modal("show");
	});
		
	// 비밀번호 변경 모달 - 변경 버튼
	$("#passModal").on("click", "#upPwBtn", function(e){
    	e.preventDefault();
		console.log("-------------------");			
		
		var userid = $("#PwUpuserid").val();	
		
		var oldPw = $("#oldPw").val();
		var newPw = $("#newPw").val();		
		var pwconfirm = $("#pwconfirm").val();
		var reg_pw = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*?[!@#$%^&*]).{6,12}/;	
		
		if(oldPw === ""){
			alert("비밀번호를 입력해주세요.");
			$("input[name='oldPw']").focus();
			return
		} // 비밀번호 초기화시 특수문자 포함x >> 정규표현식 검증x
		
		if(newPw === ""){
			alert("새 비밀번호를 입력해주세요.");
			$("input[name='newPw']").focus();
			return
		}else if(!reg_pw.test(newPw)){
			alert("비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)를 포함한 6~12자로 작성해주세요");
			$("input[name='newPw']").focus();
	        return;
    	}
		
		if(pwconfirm === "") {
			alert("새 비밀번호 확인란을 입력해주세요.");
			$("input[name='pwconfirm']").focus();
			return;
		} else if(!reg_pw.test(pwconfirm)){
			alert("비밀번호는 숫자,영문자,특수문자(!,@,#,$,%,^,&,*)를 포함한 6~12자로 작성해주세요");
			$("input[name='pwconfirm']").focus();
	        return;
    	}
		
		$.ajax({
			url:"/user/checkUser",
			type:"POST",
			data:JSON.stringify({"userid":userid, "userpw":oldPw}),
			dataType:"json",
			contentType:"application/json; charset=UTF-8",										
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success: function(data){
				if(data === 1){										
					if (newPw === pwconfirm) {	
						alert("success");
						$("#updatePwForm").submit();						
				    } else {
				    	alert("비밀번호 불일치");			    		
				        $("input[name='pwconfirm']").val("");		 	        
				        $("input[name='pwconfirm']").focus();
				        return;
			    	}	
				} else {
					alert("본인의 비밀번호를 올바르게 입력해주세요.");
					$("input[name='oldPw']").val("");
					$("input[name='oldPw']").focus();
					return;
				}				
			},
			error:function(request, status, error){
				alert("오류가 발생했습니다. 다시 시도해주십시오.");
			   	return;
			}
		});		
	});					
	
	// 비밀번호 변경 모달 - 취소 버튼	
	$("#passModal").on("click", "#closeBtn", function(e) {
	    e.preventDefault();
	    $("input[name='oldPw']").val("");
	    $("input[name='newPw']").val("");
	    $("input[name='pwconfirm']").val("");
	    setTimeout(function() { $("#passModal").modal("hide"); }, 100);
	});
	
	/* 모달창 보이기 */
	function modal(element){
		$("#myModal").modal("show");
		setTimeout(function() { $("#myModal").modal("hide"); }, 800);
		setTimeout(function() { $(element).focus(); }, 900);
	}
	
	/* 빈칸 및 형식 체크 후 제출 */			
	function checkInfo() {		
		//입력칸
		var phone = $("input[name='phone']").val();
		var email = $("input[name='email']").val();
		var address = $("input[name='address']").val();	
		
		// 입력규칙	
		var reg_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]+$/;	
		
		//전화번호
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
				
		//주소
		if (address === "") {				
			$(".modal-body").html("<p>주소를 입력해주세요</p>");
			modal("input[name='address']"); 
		    return;				
		}		
	
		$("#updateForm").submit();
	}		
});	
</script>

<%@ include file="../includes/footer.jsp"%><%@ include file="../includes/footer.jsp"%>