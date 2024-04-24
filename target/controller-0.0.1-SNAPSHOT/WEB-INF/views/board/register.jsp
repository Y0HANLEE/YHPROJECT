<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- 본문 페이지 설명란 -->
<h1 class="h3 mb-2 text-gray-800">Board Register</h1>
<p class="mb-4">
	게시글을 등록하려면 내용을 입력하여주시기 바랍니다.
</p>

<!-- 본문내용(표) 시작 -->
<div class="card shadow mb-4">
	<!-- LIST 제목 시작 -->
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">Board Register</h6>
	</div>
	<!-- LIST 제목 끝 -->
	<!-- LIST 시작 -->
	<div class="card-body">
		<div class="table-responsive">
			<form role="form" action="/board/register" method="post">
				<div class="form-group">
					<label>Title</label>
					<input class="form-control" name="title">
				</div>
				<div class="form-group">
					<label>Text Area</label>
					<textarea class="form-control" rows="10" name="content"></textarea>
				</div>
				<div class="form-group">
					<label>Writer</label>
					<input class="form-control" name="writer">
				</div>
				
				<button type="submit" class="btn btn-default">Submit</button>
				<button type="reset" class="btn btn-default">Reset</button>
				
			</form>				
		</div>
	</div>
	<!-- LIST 끝 -->
</div>
<!-- 본문내용(표) 끝 -->

<%@ include file="../includes/footer.jsp"%>