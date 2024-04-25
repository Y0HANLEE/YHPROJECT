<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">   

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">
    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="/resources/dist/css/board.css" rel="stylesheet">
    <!-- <link href="/resources/dist/css/sb-admin-2.min.css" rel="stylesheet"> -->
    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>   
    <!-- KAKAO Map : services(장소검색,주소/좌표변환)와 clusterer(Marker), drawing(Marker,graphics 그리기모드지원) 라이브러리 불러오기 -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3b70d3d0a8a684def18821cba38c489e&libraries=services,clusterer,drawing"></script>
	<!-- KAKAO 우편번호찾기 -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<!-- 사진보기 -->
<div class="picWrap" style="z-index: 10000;">	
	<strong class="msg" style="margin-top:5px; color:red;">화면을 클릭하면 사진이 사라집니다.</strong>
	<div class="pic"><!-- showImage(fileCallPath) --></div>	
</div>

 <div id="wrapper">
	<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">	
		<div class="navbar-header">	
			<!-- 반응형 : data-toggle_클릭시 토글, data-target_토글할 대상 -->
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand main" href="/"><i class="fa fa-flask"></i>YH.LAB</a>
		</div>
		
		<!-- 좌측 사이드바 -->
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<!-- 1.Home-->
					<li><a href="/"><i class="fa fa-home fa-fw"></i> Home</a></li>
					<!-- 2.User -->
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-user fa-fw"></i> User
					<c:if test="${pageContext.request.userPrincipal != null}">(@<c:out value="${pageContext.request.userPrincipal.name}"/>)</c:if> <span class="fa fa-caret-down"></span></a>
						<ul class="nav nav-second-level">
							<!-- 로그인된 상태(운영자, 일반회원) -->							
							<sec:authorize access="isAuthenticated()">								
								<li><a href="../user/profile?userid=<c:out value="${pageContext.request.userPrincipal.name}"/>"><i class="fa fa-user fa-fw"></i> Profile</a></li>
								<li><a href="../user/contents?userid=<c:out value="${pageContext.request.userPrincipal.name}"/>&boardType=1"><i class="fa fa-th-list fa-fw"></i> MyContent</a></li>								
								<li><a href="../user/comments?userid=<c:out value="${pageContext.request.userPrincipal.name}"/>&boardType=1.1"><i class="fa fa-comment fa-fw"></i> MyComment</a></li>
								<li class="divider"></li>
								<li><form action="../main/logoutPage" method="post" id="logoutForm" style="height: 40px; display: flex;align-items: center;">
									<a href="#" id="logout"><i class="fa fa-lock fa-fw"></i> Logout</a>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
								</form></li>
							</sec:authorize>
							<!-- 로그아웃/ 로그인하지 않은 모든 사용자 -->
							<sec:authorize access="isAnonymous()">
								<li><a href="/main/loginPage"><i class="fa fa-unlock fa-fw"></i> Login</a></li>
							</sec:authorize>
						</ul>
					</li>
					<!-- 2-1-1.Admin -->
					<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-gear fa-fw"></i> Admin Page <span class="fa fa-caret-down"></span></a>
						<ul class="nav nav-second-level">
							<!-- 로그인된 상태(운영자, 일반회원) -->
							<li><a href="../admin/auth"><i class="fa fa-gear fa-fw"></i> Auth </a></li>
							<li><a href="../admin/list"><i class="fa fa-user fa-fw"></i> User List</a></li>
							<li><a href="../admin/home"><i class="fa fa-home fa-fw"></i> Main Setting</a></li>
							<li><a href="../admin/intro"><i class="fa fa-paper-plane fa-fw"></i> Intro Setting</a></li>
						</ul>
					</li>
					</sec:authorize>
					<!-- 2-1-2.Manager -->
					<sec:authorize access="hasRole('ROLE_MANAGER')">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-gear fa-fw"></i> Manager Page <span class="fa fa-caret-down"></span></a>
						<ul class="nav nav-second-level">
							<!-- 로그인된 상태(운영자, 일반회원) -->							
							<li><a href="../manager/list"><i class="fa fa-user fa-fw"></i> User List</a></li>							
						</ul>
					</li>
					</sec:authorize>
					<!-- 3.Board -->					
					<li><a href="../board/list"><i class="fa fa-edit fa-fw"></i> Board</a></li>					
					<!-- 4.Album -->
					<li><a href="../album/list"><i class="fa fa-camera-retro fa-fw"></i> Album</a></li>
					<sec:authorize access="isAnonymous()">
						<!-- 5.회원가입 -->
						<li><a href="../user/join"><i class="fa fa-users fa-fw"></i> Join</a></li>
					</sec:authorize>					
					<!-- 6.introduce -->
					<li><a href="../main/intro"><i class="fa fa-paper-plane fa-fw"></i> Introduce</a></li>
					
				</ul>
			</div>
		</div>	
	</nav>
	<script>
		$("#logout").on("click", function(e){
		    e.preventDefault(); // 이벤트의 기본 동작 방지
		    console.log("click");
		    confirm("로그아웃하시겠습니까?");
		    $("#logoutForm").submit();
		});
	</script>
	<!-- 본문화면 -->
	<div id="page-wrapper" style="min-height:639px">
		<div id="top"></div>