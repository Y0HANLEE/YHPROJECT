<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<title>Intro Page</title>
<%@ include file="../includes/header.jsp"%>
<!-- 페이지 제목 -->
<div class="row">
	<!-- 소개 -->
	<div class="col-lg-12" style="display: flex; align-content: center; align-items: center;">
		<div style="width: 75%;">
			<h1 class="page-header" style="display: flex; justify-content: flex-start; align-items: center; width: 99.7%;">
				<i class="fa fa-bookmark-o"></i>&nbsp;&nbsp;<c:out value="${intro.title_title}"/>
			</h1>
		</div>		
		<sec:authorize access="hasRole('ROLE_ADMIN')">			
			<div style="display:flex; align-content: center; width:10%; margin-bottom: 10px;">
				<a href="/admin/intro" class="btn btn-info btn" style="margin-right:5px; height: 50px;">수정하기</a>			
			</div>
		</sec:authorize>
	</div>
	<div class="mb-4" style="margin: 0 0 20px 20px; width:73.5%; white-space: pre-wrap">		
		- <c:out value="${intro.title_intro}"/>
	</div>
	
	<!-- 지도 -->	
	<div class="form-group" style="margin: 50px 0 5px 20px; width: 72.3%; display: flex; align-items: center;">
		<label><c:out value="${intro.map_title}"></c:out>(<i class="fa fa-map-marker fa-fw"></i>address_<c:out value="${intro.map_address}"/>. <c:out value="${intro.map_addressdetail}"/>)</label>		 
	</div>
	
	<div id="map" style="border-radius:5px;  width:88.3%; height:350px; margin: 0 0 0 20px;"></div>							
	<div class="form-group" style="margin-left: 20px;">	
		
	</div>
	<div class="form-group" style="margin: 5px 0 70px 20px;">		
	&nbsp;&nbsp;&nbsp;&nbsp;- <c:out value="${intro.map_intro}"/>
	</div>	
	<!-- 본문-->
	<div class="panel panel-default" style="margin-left: 20px; width: 88.3%">
		<div class="panel-heading">	Intro </div>
		<div class="panel-body" style="white-space: pre-wrap;">
<c:out value="${intro.intro}"/>
		</div>				
	</div>
		
	<!-- 이동용 화살표 -->
	<a href="#top" class="btn-nav-arrow up"><i class="fa fa-arrow-up"></i></a>
	<a href="#bottom" class="btn-nav-arrow down"><i class="fa fa-arrow-down"></i></a>
	<a class="btn-nav-arrow back"><i class="fa fa-arrow-left"></i></a>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel" style="position: left;">Modal Title</h4>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: right;">×</button>
			</div>
			<div class="modal-body">수정이 완료되었습니다.</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>							
			</div>
		</div>
	</div>
</div>

<!-- KAKAO MAP -->
<script>	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
		mapCenter = new kakao.maps.LatLng(37.5034138, 126.7660309),//(위도, 경도)
    	mapOption = { center: mapCenter, level: 3 }; // 위치, 확대(1(대)~14(소))  
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 객체	
	var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
	
	
	var inputAddress = $('#address_kakao').val();
	var inputPlace = $("input[name='map_caption']").val();
	var mapCaption = '<c:out value="${intro.map_caption}"/>',
		mapAddress = '<c:out value="${intro.map_address}"/>';			

	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(mapAddress, function(result, status) {	     
		if (status === kakao.maps.services.Status.OK) { // 정상적으로 검색이 완료됐으면				
			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);			
			var marker = new kakao.maps.Marker({ map: map, position: coords });// 결과값으로 받은 위치를 마커로 표시합니다		
			if(mapCaption != null){
				var infowindow = new kakao.maps.InfoWindow({ content: '<div style="width:150px;text-align:center;padding:6px 0;">'+mapCaption+'</div>'});// 인포윈도우로 장소에 대한 설명을 표시합니다
				infowindow.open(map, marker);
			}
			map.setCenter(coords);// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	    } 
	});	
</script>
<!-- 본문내용 -->
<script>
$(document).ready(function(){	
	var result = '<c:out value="${result}"/>';	
	checkModal(result);	
	history.replaceState({},null,null); //뒤로가기문제		
	
	/* MODAL창 설정 */
	function checkModal(result) {
		if(result === "" || history.state){ return; }		
		if(result > 0) {
			$(".modal-body").html(result);
		}		
		$("#myModal").modal("show");
	} 
});
</script>

<%@ include file="../includes/footer.jsp"%>