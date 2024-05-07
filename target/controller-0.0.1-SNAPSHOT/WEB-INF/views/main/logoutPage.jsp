<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<%@ include file="../includes/header.jsp"%>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header">Logout Page</h1>
	</div>
	<p class="mb-4">
		로그아웃페이지입니다.
	</p>
</div>

<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Logout</div>
			<div class="panel-body">								
				<form method="post" action="/main/logoutPage">					
					<button>로그아웃</button>
					<!-- 보안:사이트간 요청 위조방지. spring security에서 post방식을 이용하는 경우 사용.-->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
				</form>
			</div>
		</div>
	</div>
</div>	

<%@ include file="../includes/footer.jsp"%>