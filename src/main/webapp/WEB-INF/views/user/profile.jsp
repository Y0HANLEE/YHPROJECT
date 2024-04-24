<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../includes/header.jsp"%>
<title>회원정보페이지</title>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12" style="margin-top:30px">
		<div class="panel panel-default">
			<div class="panel-heading"><i class="fa fa-user fa-fw"></i><strong><c:out value="${user.name}"/></strong><c:choose><c:when test="${auth.auth == '1'}">(관리자)</c:when><c:when test="${auth.auth == '2'}">(운영자)</c:when><c:when test="${auth.auth == '3'}">(일반회원)</c:when></c:choose>님의 회원정보</div>
			<div class="panel-body">
				<div class="col-lg-6">				
					<div class="form-group">						
						<p><label for="userid">아이디</label></p>
						<input class="form-control" type="text" name="userid" id="userid" value="${user.userid}" readonly>							
					</div>			
					<div class="form-group">
						<label for="name">이름</label>
						<input class="form-control" type="text" name="name" value="${user.name}" readonly>						
					</div>
					<div class="form-group">
						<label for="gender">성별</label>
						<input class="form-control" type="text" name="gender" value="${user.gender=='M'?'남자':'여자'}" readonly>
					</div>
					<div class="form-group">
						<label for="phone">전화번호</label>
						<input class="form-control" type="text" name="phone" value="${user.phone}" readonly>
					</div>
					<div class="form-group">
						<label for="email">이메일</label>
						<input class="form-control" type="email" name="email" value="${user.email}" readonly>
					</div>
					<div class="form-group">
						<label for="address">주소</label>
						<input class="form-control" type="text" name="address" value="(${user.zonecode}) ${user.address}. ${user.addressDetail}" readonly>
					</div>
					<div class="form-group">
						<label for="birth">생년월일</label>
						<input class="form-control" type="text" name="birth" value="<fmt:formatDate pattern="yyyy-MM-dd" value='${user.birth}'/>" readonly>
					</div>		
					<div class="form-group" style="margin-top: 30px;">		
						<sec:authentication property="principal" var="principal"/>					
						<sec:authorize access="isAuthenticated()">
							<c:if test="${principal.username eq user.userid}">
								<button class="btn btn-lg btn-info btn-lg" style="width:33%" id="updateBtn">수정하기</button>						
								<button class="btn btn-lg btn-default btn-lg " style="width:33%;" id="deleteBtn" >회원탈퇴</button>
							</c:if>
						</sec:authorize>				
						<button class="btn btn-lg btn-default btn-lg" style="width:32%" id="backBtn" onclick="history.back()">뒤로가기</button>						
					</div>
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
				<div class="modal-title" id="myModalLabel"><!-- modal-title--></div> 
			</div>
			<form id="deleteForm">
				<div class="modal-body">					
					<!-- modal-body -->					
				</div>			
				<div class="modal-footer">
					<!-- button -->
				</div>				
				<input type="hidden" value="${user.userid}" id="Huserid" name="userid">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			</form>
		</div>
	</div>
</div>
 
<script type="text/javascript">
$(document).ready(function(){		
	var result = '<c:out value="${result}"/>';	
	checkModal(result);
	
	/* 뒤로가기 문제해결 */
	history.replaceState({},null,null);		
	
	/* MODAL창 설정 */
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
	
	// 엔터키 이벤트 핸들러를 document에 부착하여 이벤트 위임을 사용합니다.
	$(document).on('keypress', 'input[name="inputPw"]', function(e) {
	    if (e.key === 'Enter') {
	    	e.preventDefault();
	        $("#checkPwBtn").click();	        
	    }
	});
	
	// 비밀번호 재확인용 
	function checkPwModal(){
		var userid =  "${user.userid}";
		$(".modal-title").html('<h4 class="modal-title">본인확인</h4>');			
		$(".modal-body").html('<input class="form-control" type="password" name="inputPw" id="inputPw" placeholder="비밀번호를 입력해주세요">');
		$(".modal-footer").html('<button type="button" class="btn btn-default" id="checkPwBtn">확인</button><button type="button" class="btn btn-default" id="closeBtn">닫기</button>');
		$("#myModal").modal("show");
	}	
	
	// 수정하기버튼 >> 검증 후 update매핑
	$("#updateBtn").on("click", function(e){
		e.preventDefault();
		//비밀번호 재확인 모달창
		checkPwModal();
		//검증
		$("#checkPwBtn").on("click", function(e){
			e.preventDefault();		
			var userid = $("input[name='userid']").val();
			var inputPw = $("#inputPw").val();			
				
			if(inputPw === ""){
				alert("비밀번호를 입력해주세요.");
				$("input[name='inputPw']").focus();
				return;
			} else {		
				$.ajax({
					async:true,
					url:"/user/checkUser",
					type:"POST",
					data:JSON.stringify({"userid":userid, "userpw":inputPw}),
					dataType:"json",
					contentType:"application/json; charset=UTF-8",										
					beforeSend:function(xhr){
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success: function(data){
						if(data === 1){
							$("form").attr("method", "get").attr("action", "/user/update");
							$("input[name='inputPw']").val("");
							location.href="/user/update?userid="+userid;
						} else {
							alert("일치하는 비밀번호가 없습니다.");
							return;
						}
					},
					error:function(request, status, error){
						alert("오류가 발생했습니다. 다시 시도해주십시오.");
					   	return;
					}
				});
			}
		});  
	});
			
	/* 회원탈퇴버튼 >> 검증 후 ajax_delete매핑 */
	$("#deleteBtn").on("click", function(e){
		e.preventDefault();
		//비밀번호 재확인 모달창		
		checkPwModal();
		//검증
		$("#checkPwBtn").on("click", function(e){
			var userid = "${user.userid}";
			var inputPw = $("#inputPw").val();		
	
			if($("#inputPw").val()==""){
				alert("비밀번호를 입력해주세요.");
				$("#inputpw").focus();
				return;
			} else {
				$.ajax({
					async:true,
					url:"/user/checkUser",
					type:"post",
					data:JSON.stringify({"userid":userid, "userpw":inputPw}),
					dataType:"json",
					contentType:"application/json; charset=UTF-8",
					beforeSend:function(xhr){
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success:function(data){				
						if(data === 1){
							console.log(userid+"일치");
							if(confirm("탈퇴하시겠습니까?")){								
								$("#deleteForm").attr("method", "post").attr("action", "/user/delete");
								$("#deleteForm").submit();								
							}						
						}else{
							alert("일치하는 비밀번호가 없습니다.");
							return;
						}
					},
					error:function(request, status, error){
						alert("오류가 발생했습니다. 다시 시도해주십시오.");
					   	return;
					}	
				});
			}
		});
	});
	
	// 모달창 닫기 버튼	
	$("#myModal").on("click", "#closeBtn", function(e) {
	    e.preventDefault();
	    $("input[name='inputPw']").val("");
	    setTimeout(function() { $("#myModal").modal("hide"); }, 100);
	});

	// 모달 외부 클릭 시 모달을 숨기는 이벤트 위임
	$("#myModal").on("click", function(e) {
	    // 클릭된 요소가 모달 내부가 아닌지 확인 후 이벤트 처리
	    if ($(e.target).hasClass("modal")) {
	    	e.preventDefault();
		    $("input[name='inputPw']").val("");		    
	    }
	});
});	
</script>

<%@ include file="../includes/footer.jsp"%>