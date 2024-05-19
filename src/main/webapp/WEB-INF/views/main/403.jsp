<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Console.log(YH)_403_ERROR</title>
<%@ include file="../includes/header.jsp"%>

<!-- 페이지 제목 -->
<div class="row">		
	<div class="col-lg-12">
		<h1 class="page-header"><i style="top:6px; color:rgba(255,0,0,0.5);" class="fa fa-exclamation-circle"></i>  403 Error</h1>
	</div>
</div>
<!-- 본문-->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">	접근권한에러 </div>
			<div class="panel-body">			
				<h2><c:out value="${user.userid}"/>회원님께서는 현재 페이지에 접근권한이 없습니다.</h2>				
				<button class="pull-right" onclick="history.back()">이전화면</button>				
			</div>
		</div>
	</div>
</div>	

<%@ include file="../includes/footer.jsp"%>