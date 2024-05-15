<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<title>소개페이지 관리</title>
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
		<div style="display:flex; align-content: center; width:25%; margin-bottom: 10px;">
				<button type="button" id="modifyBtn" class="btn btn-info btn" style="margin-right:5px; height: 50px;">수정하기</button>
				<button type="button" id="backBtn" class="btn btn-default btn" style="height: 50px;">뒤로가기</button>
			</div>
	</div>
	<p class="mb-4">
		<label>소개글</label>
<textarea class="form-control" name="title_intro" style="width:73.5%" placeholder="간단한 페이지 소개글 입력"><c:out value="${intro.title_intro}"/></textarea>
	</p>
	<!-- 파일업로드 -->
	<div class="form-group" style="margin-left: 10px; width: 89%;">
		<i class="fa fa-tags fa-fw"></i><label>File</label>
		<div class="form-group uploadDiv">
			<input type="file" name="uploadFile" multiple>
		</div>
		<!-- 결과 -->
		<div class="uploadResult" style="height: 150px;">
			<ul>
				<!-- function showUploadResult(uploadResultArr) -->
			</ul>
		</div>
	</div>
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
				<button id="searchBtn" class="btn btn-default" style="height: 34px">Mark</button>
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
	<input type="hidden" name="attachList.boardtype" value="2">
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
		// DB에 등록시키기 위해 BoardVO.attachList에 정보를 전송
		var str = "";
					
		$(".uploadResult ul li").each(function(i, obj){
			var jobj = $(obj);			
			console.dir(jobj); // 체의 속성을 나열하여 출력			
			str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
		});
		$("#introForm").append(str);	
		
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

	/* 업로드 상세처리(확장자, 크기 등) */
	var regex = new RegExp("(.*?)\.(jpg|jpeg|png|gif)"); // 업로드 가능 확장자
	var maxSize = 31457280; // 30MB		
	
	function checkFile(fileName, fileSize){
		// 파일사이즈 검토
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과 : 파일당 최대 30MB까지 업로드 가능합니다.");
			return false;
		}
		
		// 파일이름(확장자) 검토
		if(!regex.test(fileName)){
			alert("jpg,jpeg,png,gif 확장자만 업로드 가능합니다.");
			return false;
		}
			
		return true; // 성공시
	}
		
	/* 등록버튼 없이 변화가 감지되면 처리할 기능 */
	$("input[type='file']").change(function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");//첨부된 파일
		var files = inputFile[0].files;
		
		for(i=0; i<files.length; i++){
			if(!checkFile(files[i].name, files[i].size)){				
				return false;
			}
			formData.append("uploadFile", files[i]);
		}
		
		$.ajax({
			type:'post',
			url:'/uploadAjaxAction',
			processData:false,
			contentType:false,
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
			data:formData,
			dataType:'json',
			success:function(result){					
				showUploadResult(result);
				
			}
		});
	});

	/* 업로드 결과 보이기 */
	function showUploadResult(uploadResultArr){
		if(!uploadResultArr || uploadResultArr.length == 0){ return; };
		
		var uploadUL = $(".uploadResult ul");		
		var str="";				
		
		$(uploadResultArr).each(
			function(i,obj){								
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);													
				
				str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, type(img)을 추가한다.
				str += "<img src='/display?fileName="+fileCallPath+"'>"; // 첨부파일 이미지(썸네일)						
				str += "<span>"+' '+obj.fileName+' '+"</span>"; // 파일명 
				str += "<button type='button' class='btn btn-danger btn-circle btn-xs' data-file=\'"+fileCallPath+"\' data-type='image'><i class='fa fa-times'></i></button> </li>"; // x버튼 data-file:삭제할 경로, data-type:삭제할 파일의 타입 >> image:원본+썸네일 삭제
			});
		uploadUL.append(str);
	}
	
	/* 업로드 취소 기능 구현*/
	$(".uploadResult").on("click", ".btn-danger", function(e){
		var target = $(this).data("file");
		var type = $(this).data("type");
		var targetLi = $(this).closest("li"); // target(삭제 파일)이 속한 li태그
				
		$.ajax({
			type:'POST',
			url:'/deleteFile', // 서버(폴더)에서만 삭제
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
			},
			data:{fileName:target, type:type},
			dataType:'text',
			success: function(result){				
				targetLi.remove(); // 화면에서도 삭제
			}
		});		
	});		
	
	/* 첨부파일 클릭시 이벤트 처리 */
	$(".uploadResult").on("click", "li", function(e){
		var element = $(e.target);
		var liObj = $(this);
		var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));//li태그에 저장되어있는 정보들 >> 경로/uuid_파일명
		
		//span이나 img일경우만 이벤트 >> x버튼은 삭제만 처리
		if(element.is("span") || element.is("img")){
			if(liObj.data("type")){
				showImage(path.replace(new RegExp(/\\/g),"/")); // 이미지파일 : showImage함수 실행
			}
		} 
	});
	
	// 원본사진 확대보기 on
	function showImage(fileCallPath){			
		$(".picWrap").css("display","flex").show(); // none > flex 설정 변경
		$(".pic").html("<img src='/display?fileName="+fileCallPath+"'>").animate({width:'100%', height:'100%'}, 0);//pic의 html속성은 controller의 display메서드, animate는 크기변경(배경창 100%*100%) 0.3초 후 실행 
	}
	
	// 원본사진 확대보기 off 
	$(".picWrap").on("click", function(e){
		$(".pic").animate({width:'0%', height:'0%'}, 0); // (0%*0%) 로 0.3초 후 크기변경
		setTimeout(() => {$(this).hide();}, 0);	// chrome의 ES6화살표함수
		//IE : setTimeout(function(){$('.picWrap').hide();}, 300);
	});
	
	/* 첨부파일 조회화면 : 즉시실행함수*/
	(function(){
		var boardtype = '<c:out value="${intro.boardtype}"/>';
		$.getJSON("/main/getAttachList", {boardtype:boardtype}, function(arr){
			console.log(boardtype)
			
			var str="";
			
			$(arr).each(function(i, attach){
				var fileCallPath = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);						
				str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"; // 게시물의 등록을 위해 첨부파일과 관련된 정보 uploadpath, uuid, filename, filetype을 추가한다.
				str += "<img src='/display?fileName="+fileCallPath+"'> <span>"+attach.fileName+"</span> "; //파일명
				str += "<button type='button' class='btn btn-danger btn-circle btn-xs' data-file='"+fileCallPath+"' data-type='image'><i class='fa fa-times'></i></button> </li>"; // x버튼 data-file:삭제할 경로, data-type:삭제할 파일의 타입 >> image:원본,썸네일삭제
			});
			$(".uploadResult ul").html(str);
		});
	})();

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