<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
    <title>사용자 권한</title>
</head>
<body>
	<p>현재 로그인 사용자: ${id}</p>
    <p>현재 사용자의 권한: ${auth}</p>
    <p>principal:<sec:authentication property="principal"/></p>				
	<p>principal.username:<sec:authentication property="principal.username"/></p>
	<p>principal.user:<sec:authentication property="principal.user"/></p>
	<p>userid:<sec:authentication property="principal.user.userid"/></p>
</body>
</html>
