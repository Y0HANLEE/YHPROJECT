<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<title>Console.log(YH)_회원관리</title>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i class="fa fa-user fa-fw"></i>회원목록</h1>
	</div>
	<p class="mb-4">
		<sec:authentication property="principal" var="id"/>
		º안녕하세요 <b>@<c:out value="${id.username}"/></b> 관리자님, Console.log(YH) 커뮤니티 회원목록입니다.
	</p>
</div>
<!-- 본문-->
<div class="row" style="margin-top: 0px">
	<div id="userCol12" class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				Console.log(YH) 회원목록			
			</div>
			<!-- 본문 및 부가기능 -->
			<div class="panel-body">
				<!-- 본문 -->				
				<table class="table table-striped table-bordered table-hover tableList">
					<thead>					
						<tr>
							<th class="adminListTh" style="width:5%">No</th>							
							<th class="adminListTh" style="width:10%">아이디</th>
							<th class="adminListTh" style="width:10%">이름</th>
							<th class="adminListTh" style="width:5%">성별</th>
							<th class="adminListTh" style="width:10%">생일</th>
							<th class="adminListTh" style="width:20%">연락처</th>							
							<th class="adminListTh" style="width:15%">등급</th>							
							<th class="adminListTh" style="width:15%">가입일</th>
							<th class="adminListTh" style="width:10%">수정</th>
						</tr>
					</thead>
					<tbody>					 
					<c:forEach items="${list}" var="user">
						<tr class="adminListTr">
							<td><c:out value="${user.uno}"/></td>
							<td>
								<a id="userid" href='/user/profile?userid=<c:out value="${user.userid}"/>'><c:out value="${user.userid}" /></a>
							</td>							
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.gender == 'M'?'M':'F'}" /></td>	
							<td><fmt:formatDate pattern="yyyy.MM.dd" value="${user.birth}" /></td>						
							<td><c:out value="${user.phone}"/></td>
							<td>
								<c:choose>
									<c:when test="${user.userid ne 'admin'}">
									<select name="auth">									
										<option value="ROLE_USER" <c:out value="${user.authList[0].auth == 'ROLE_USER'?'selected':''}"/>>일반회원</option>
										<option value="ROLE_MANAGER" <c:out value="${user.authList[0].auth == 'ROLE_MANAGER'?'selected':''}"/>>운영자</option>
										<option value="ROLE_ADMIN" <c:out value="${user.authList[0].auth == 'ROLE_ADMIN'?'selected':''}"/>>관리자</option>
									</select>
									</c:when>
									<c:otherwise>
										<c:out value="${user.authList[0].auth == 'ROLE_ADMIN' ? '관리자':''}"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td><fmt:formatDate pattern="YY/MM/dd" value="${user.regdate}" /></td>																				
							<td style="height: 63.5px">
								<c:if test="${user.userid ne 'admin'}">
									<!-- data-userid : c:forEach로 반복시 고유한 기능을 잃게되므로 각각 고유한 버튼으로 만들어줘야함. -->
									<button class="authBtn btn btn-default btn-xs" data-userid="${user.userid}" style="margin-bottom: 2px;">등급조정</button>
									<button class="deleteBtn btn btn-defualt btn-xs" data-userid="${user.userid}">회원삭제</button>
								</c:if>
							</td>						
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
							<form id="searchForm" method='get' action="/admin/list" style="display:flex;">
								<div>
									<select name="type">
										<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }"/>>--</option>						
										<option value="I" <c:out value="${pageMaker.cri.type eq 'I' ? 'selected' : '' }"/>>아이디</option>							
										<option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }"/>>이름</option>
										<option value="G" <c:out value="${pageMaker.cri.type eq 'G' ? 'selected' : '' }"/>>성별</option>
										<option value="P" <c:out value="${pageMaker.cri.type eq 'P' ? 'selected' : '' }"/>>연락처</option>
										<option value="A" <c:out value="${pageMaker.cri.type eq 'A' ? 'selected' : '' }"/>>주소</option>
										<option value="M" <c:out value="${pageMaker.cri.type eq 'M' ? 'selected' : '' }"/>>이메일</option>
										<option value="B" <c:out value="${pageMaker.cri.type eq 'B' ? 'selected' : '' }"/>>생일</option>
										<%-- <option value="U" <c:out value="${pageMaker.cri.type eq 'U' ? 'selected' : '' }"/>>권한</option> --%>									
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
								<li id="dataTable_next" class="paginate_button next">
									<a href="${pageMaker.endPage + 1}" class="page-link">▶</a>
								</li>
							</c:if>
						</ul>						
					</div>
				</div>
			
				<!-- page이동을 위한 pageMaker정보 가져오기 -->
				<form id="actionForm" method="get" action="/admin/list">
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
<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 id="myModalLabel" class="modal-title" style="position: left;">알림</h4>
				<button class="close" data-dismiss="modal" aria-hidden="true" style="position: right;">×</button>
			</div>
			<div class="modal-body">처리가 완료되었습니다.</div>
			<div class="modal-footer">
				<button class="btn btn-default" data-dismiss="modal">Close</button>							
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
		    } else {
		    	return false;
		    }				
		});
		
		$(".deleteBtn").on("click", function(){
		    var userid = $(this).data("userid");		    
		    var msg = "계정을 삭제하시려면 해당 회원의 ID를 입력해주십시오.";		    
		    var userInput = prompt(msg);
					    
		    if (userInput === null) { //취소
		        return; 
		    } else if (userInput === userid) { //최종체크(아이디 재입력)
		        $("#userForm input[name='userid']").val(userid); //userid값을 input_hidden(userid)값으로 전달			        
		        userForm.attr("action", "/admin/delete").submit();
		    } else if (userInput === ""){
		        alert("아이디를 입력해주세요");
		    } else {
		        alert("일치하지 않습니다.");
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
	            window.location.href = "/admin/list";
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
	    	    		
		//검색 후 입력란 정보 유지
	    $(function(){	        
            var keyword = '${pageMaker.cri.keyword}';
            $('#keyword').val(keyword);
	    });		
	});
</script>

<%@ include file="../includes/footer.jsp"%>