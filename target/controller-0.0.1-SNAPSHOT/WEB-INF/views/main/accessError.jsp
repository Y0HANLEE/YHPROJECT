<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp"%>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header">Error</h1>
	</div>
	<p class="mb-4" style="margin-left: 20px">
		403 Forbidden_접근권한 에러입니다.
	</p>
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">	<c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}"/> </div>
			<div class="panel-body">			
				<h2><c:out value="${msg}"/></h2>				
			</div>
		</div>
	</div>
</div>	

<%@ include file="../includes/footer.jsp"%>