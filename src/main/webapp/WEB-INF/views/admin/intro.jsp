<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<title>Intro Update Page</title>
<%@ include file="../includes/header.jsp"%>
<!-- 페이지 제목 -->
<div class="row">		
	<form id="introForm" action="/admin/intro" method="post">
	<!-- 소개 -->
	<div id="introCol12" class="col-lg-12">
		<div style="width: 75%;">
			<h1 id="introPageHeader" class="page-header">
				<i class="fa fa-bookmark-o"></i>&nbsp;&nbsp;<input class="form-control" style="height:50px; width:82%; font-size: 36px" id="title" name="title_title" placeholder="제목을 입력해주세요" value="${intro.title_title}">
				<span id="title_title_byte_info" data-input="title_title" style="font-size: 12px; margin-left: 5px;"><%-- ${intro.i_m_tLength} --%></span>
			</h1>
		</div>
		<div style="display:flex; align-content: center; width:10%; margin-bottom: 10px;">
			<button type="button" id="modifyBtn" class="btn btn-info btn" style="margin-right:5px; height: 50px;">수정하기</button>
			<button type="button" id="backBtn" class="btn btn-default btn" style="height: 50px;">뒤로가기</button>
		</div>
	</div>
	<p class="mb-4">
		<label>소개글</label>
<textarea class="form-control" name="title_intro" style="width:73.5%" placeholder="간단한 페이지 소개글 입력"><c:out value="${intro.title_intro}"/></textarea>
	</p>
	<!-- 지도 -->	
	<div class="form-group" style="margin-left: 20px;">	
		<label>장소명</label>
		<div style="display: flex; align-items: center;">		
			<div style="width:95%; display: flex; align-items:center;">
				<input class="form-control" name="map_title" placeholder="여기는 어떤 곳인가요?" style="width:89%" value="${intro.map_title}"> 
				<span style="margin-left:5px" id="map_title_byte_info" data-input="map_title"><%-- ${intro.i_m_tLength} --%></span>
			</div>
			<div style="width: 20%;">
			    <em class="link"><a href="javascript:void(0);" onclick="window.open('http://fiy.daum.net/fiy/map/CsGeneral.daum', '_blank', 'width=981, height=650')" style="font-size: 12px">주소수정제안</a></em>
			</div>
		</div>
	</div>

	<div id="map" style="border-radius:5px;  width:88.3%; height:350px; margin: 0 0 20px 20px;"></div>
	<div style="display: flex; justify-content: space-between; margin: 0 0 0 20px;">
		<div class="form-group" style="width:15%">
			<label>마커제목</label><input class="form-control" name="map_caption" style="width: 90%" placeholder="장소설명" value="${intro.map_caption}">
			<span id="map_caption_byte_info" data-input="map_caption"><%-- ${intro.i_m_cLength} --%></span> 
		</div>
		<div class="form-group" style="width:75%">
			<label>주소</label>
			<div style="display: flex; align-items: flex-start;">
			<div style="width: 46.7%">
				<input name="map_address" class="form-control" id="address_kakao" placeholder="주소입력" value="${intro.map_address}" readonly>&nbsp;
			</div>
			<div style="width: 30%; margin-left: 5px">
				<input class="form-control" name="map_addressdetail" placeholder="상세주소" value="${intro.map_addressdetail}">&nbsp;
				<span id="map_addressdetail_byte_info" data-input="map_addressdetail"><%-- ${intro.i_m_aDLength} --%></span>	
			</div>
			<div style="width: 20%; margin-left: 5px">
				<button id="searchBtn" class="btn btn-default" style="height: 34px">search</button>
			</div>
			</div>
		</div>
	</div>							
	<div class="form-group" style="margin-left: 20px;">
		<label>장소소개</label>
<textarea class="form-control" name="map_intro" placeholder="이 장소는 어떤 곳인지 알려주세요"><c:out value="${intro.map_intro}"/></textarea>
	</div>	
	<!-- 본문-->
	<div class="panel panel-default" style="margin-left: 20px;">
		<div class="panel-heading">	Intro </div>
		<div class="panel-body">
<textarea class="form-control" name="intro"><c:out value="${intro.intro}"/></textarea>
		</div>				
	</div>	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<input type="hidden" name="boardtype" value="2">
	</form>
	
	<!-- 이동용 화살표 -->
	<a href="#top" class="btn-nav-arrow up"><i class="fa fa-arrow-up"></i></a>
	<a href="#bottom" class="btn-nav-arrow down"><i class="fa fa-arrow-down"></i></a>
	<a class="btn-nav-arrow back"><i class="fa fa-arrow-left"></i></a>
</div>

<!-- 본문내용 -->
<script>
$(document).ready(function(){	
	$("#modifyBtn").on("click", function(e){
		e.preventDefault();
		inputCheck();		
	});
	
	handleByteLimit("input[name='title_title']", 50);
	handleByteLimit("input[name='map_title']", 50);
	handleByteLimit("input[name='map_caption']", 50);
	handleByteLimit("input[name='map_addressdetail']", 50);	
	
	// nav버튼 클릭횟수 : 페이지 내부 이동시 history에 기록이 남아 이전페이지로 이동이 불가
	var navBtnCnt=0;	
	$("a.btn-nav-arrow.up, a.btn-nav-arrow.down").on("click", function(e){ navBtnCnt++; });
	
	/* 뒤로가기버튼 : nav버튼 횟수보다 -1을 해야 이전페이지로 이동  */
	$(".btn-nav-arrow.back, #backBtn").on("click", function(e){
		e.preventDefault();				
		window.history.back(-navBtnCnt-1);
	});
	
	function inputCheck(){
		if($("input[name='title_title']").val() == null || $("input[name='title_title']").val() == ""){
			alert("제목은 반드시 입력해주세요.");
			$("input[name='title_title']").focus();
			return false;
		}		
		$("#introForm").submit();
	}	
});
</script>

<!-- byte체크 -->
<script>
/*
$(document).ready(function() {
    // input 필드에서 input 이벤트 감지
    $("input[name='title_title'], textarea[name='title_intro'], textarea[name='map_intro'], textarea[name='intro']").on('input', function() {
        var inputName = $(this).attr('name');
        var byteLimit = 100; // 예시로 100으로 설정, 필요에 따라 변경
        
        // 입력 내용 가져오기
        var inputValue = $(this).val();
        
        // 입력 내용의 byte 계산
        var byteLength = 0;
        for (var i = 0; i < inputValue.length; i++) {
            var charCode = inputValue.charCodeAt(i);
            if (charCode <= 127) {
                byteLength += 1; // ASCII 문자는 1 byte
            } else {
                byteLength += 2; // 한글 등의 다중 바이트 문자는 2 byte
            }
        }
        
        // byte 표시 엘리먼트 선택
        var byteDisplayElement = $("span[data-input='" + inputName + "']");
        
        // byte 제한을 넘으면 스타일 변경 및 입력 제한
        if (byteLength > byteLimit) {
            byteDisplayElement.css('color', 'red');
            // 입력 값을 제한된 길이로 잘라내기
            var trimmedValue = trimToByteLimit(inputValue, byteLimit);
            $(this).val(trimmedValue); // 입력 필드에 변경된 값을 설정
            alert("입력된 내용이 너무 깁니다. " + byteLimit + " byte 이하로 입력해주세요.");
            byteLength = byteLimit; // byteLength를 제한된 길이로 변경
        } else {
            byteDisplayElement.css('color', 'black');
        }
        
        // byte 표시 업데이트
        byteDisplayElement.text(byteLength + '/' + byteLimit + ' bytes');
    });
    
    // 지정된 바이트 제한에 맞게 문자열을 자르는 함수
    function trimToByteLimit(str, limit) {
        var trimmedStr = '';
        var byteLength = 0;
        for (var i = 0; i < str.length; i++) {
            var charCode = str.charCodeAt(i);
            if (charCode <= 127) {
                byteLength += 1; // ASCII 문자는 1 byte
            } else {
                byteLength += 2; // 한글 등의 다중 바이트 문자는 2 byte
            }
            if (byteLength <= limit) {
                trimmedStr += str[i];
            } else {
                break;
            }
        }
        return trimmedStr;
    }
});
*/
//바이트 제한을 감지하고 처리하는 함수
function handleByteLimit(inputFieldSelector, byteLimit) {
    $(document).ready(function() {
        // input 필드에서 input 이벤트 감지
        $(inputFieldSelector).on('input', function() {
            var inputName = $(this).attr('name');
            
            // 입력 내용 가져오기
            var inputValue = $(this).val();
            
            // 입력 내용의 byte 계산
            var byteLength = 0;
            for (var i = 0; i < inputValue.length; i++) {
                var charCode = inputValue.charCodeAt(i);
                if (charCode <= 127) {
                    byteLength += 1; // ASCII 문자는 1 byte
                } else {
                    byteLength += 2; // 한글 등의 다중 바이트 문자는 2 byte
                }
            }
            
            // byte 표시 엘리먼트 선택
            var byteDisplayElement = $("span[data-input='" + inputName + "']");
            
            // byte 제한을 넘으면 스타일 변경 및 입력 제한
            if (byteLength > byteLimit) {
                byteDisplayElement.css('color', 'red');
                // 입력 값을 제한된 길이로 잘라내기
                var trimmedValue = trimToByteLimit(inputValue, byteLimit);
                $(this).val(trimmedValue); // 입력 필드에 변경된 값을 설정
                alert("입력된 내용이 너무 깁니다. " + byteLimit + " byte 이하로 입력해주세요.");
                byteLength = byteLimit; // byteLength를 제한된 길이로 변경
            } else {
                byteDisplayElement.css('color', 'black');
            }
            
            // byte 표시 업데이트
            byteDisplayElement.text(byteLength + '/' + byteLimit + ' bytes');
        });
        
        // 지정된 바이트 제한에 맞게 문자열을 자르는 함수
        function trimToByteLimit(str, limit) {
            var trimmedStr = '';
            var byteLength = 0;
            for (var i = 0; i < str.length; i++) {
                var charCode = str.charCodeAt(i);
                if (charCode <= 127) {
                    byteLength += 1; // ASCII 문자는 1 byte
                } else {
                    byteLength += 2; // 한글 등의 다중 바이트 문자는 2 byte
                }
                if (byteLength <= limit) {
                    trimmedStr += str[i];
                } else {
                    break;
                }
            }
            return trimmedStr;
        }
    });
}
</script>

<!-- KAKAO 주소검색 -->
<script>
window.onload = function(){
    document.getElementById("address_kakao").addEventListener("click", function(e){
    	e.preventDefault();
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅            	
                document.getElementById("address_kakao").value = data.address; // 주소 넣기                
                //document.querySelector("input[name=address_detail]").focus(); //상세입력 포커싱
                $("input[name='map_addressdetail']").focus(); //상세입력 포커싱
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

<!-- KAKAO MAP -->
<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
		mapCenter = new kakao.maps.LatLng(37.5034138, 126.7660309),//(위도, 경도)
    	mapOption = { center: mapCenter, level: 3 }; // 위치, 확대(1(대)~14(소))  
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 객체	
	var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
	
	$("#searchBtn").on("click", function(e){
		e.preventDefault();
		var inputAddress = $('#address_kakao').val();
		var inputPlace = $("input[name='map_caption']").val();
		/*
		var ps = new kakao.maps.services.Places();	
		ps.keywordSearch(inputAddress, placesSearchCB);// 키워드로 장소를 검색합니다
		*/
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(inputAddress, function(result, status) {	     
			if (status === kakao.maps.services.Status.OK) { // 정상적으로 검색이 완료됐으면				
				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);			
				var marker = new kakao.maps.Marker({ map: map, position: coords });// 결과값으로 받은 위치를 마커로 표시합니다		
				if(inputPlace != null){
					var infowindow = new kakao.maps.InfoWindow({ content: '<div style="width:150px;text-align:center;padding:6px 0;">'+inputPlace+'</div>'});// 인포윈도우로 장소에 대한 설명을 표시합니다
					infowindow.open(map, marker);
				}
				map.setCenter(coords);// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		    } 
		});		
	});

/*	function placesSearchCB (data, status, pagination) {	// 키워드 검색 완료 시 호출되는 콜백함수 입니다
		if (status === kakao.maps.services.Status.OK) {
			var bounds = new kakao.maps.LatLngBounds();
			for (var i=0; i<data.length; i++) {
				displayMarker(data[i]);    
				bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
			}       
			map.setBounds(bounds);
		} 
	}

	// 지도에 마커를 표시하는 함수입니다
	function displayMarker(place) {
		var marker = new kakao.maps.Marker({ map: map, position: new kakao.maps.LatLng(place.y, place.x) });
		kakao.maps.event.addListener(marker, 'click', function() {	        
			infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
			infowindow.open(map, marker);
		});
	}*/
</script>


<%@ include file="../includes/footer.jsp"%>