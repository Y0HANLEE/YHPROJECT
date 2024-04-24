console.log("Album Reply Module.........");

var replyService = ( function(){
	
	// 댓글 추가
	function add(reply, callback, error){
		console.log("add reply........");
		
		$.ajax({
			type:'post',
			url:'/album/replies/new',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr){
				if(callback){
					callback(result);
				}			
			},
			error : function(xhr, status, er) {
				if(error){
					error(er);
				}
			}			
		})
	}	
	
	//댓글 목록 조회
 	function getList(param, callback, error){
 		var ano = param.ano;
 		var page = param.page || 1;
 		
 		$.getJSON("/album/replies/pages/"+ano+"/"+page,
			function(data) {
				if(callback){
					//callback(data); // 댓글 리스트만 가져옴
					callback(data.replyCnt, data.list); // 댓글 개수 + 리스트
				}				
			}).fail(function(xhr,status,err) {
				if(error){
					error();
				}
			});
 	}	
	 	
	//댓글 삭제 >> 작성자와 로그인사용자 비교를 위해 Controller에서 사용하도록 replyer추가
	function remove(rno, replyer, callback, error){
		$.ajax({
			type:'delete',
			url:'/album/replies/'+rno,
			data:JSON.stringify({rno:rno, replyer:replyer}),
			contentType:"application/json; charset=utf-8",
			success : function(deleteResult, status, xhr){
				if(callback){
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er){
				if(error) {
					error(er);
				}
			}
		});
	}
	
	// 댓글 수정
	function update(reply, callback, error){
		$.ajax({
			type:'put',
			url:'/album/replies/'+reply.rno,
			data:JSON.stringify(reply),
			contentType:"application/json; charset=utf-8",
			success:function(result, status, xhr) {
				if(callback){
					callback(result);
				}
			},
			error:function(xhr, status, er){
				if(error){
					error(er);
				}
			}		
		});
	}
	
	// 댓글 조회
	function get(rno, callback, error){
		$.get("/album/replies/"+rno,
			function(result) {
				if(callback){
					callback(result);
				}
			}
		).fail(function(xhr, status, er){
			if(error){
				error(er);
			}
		});		
	}
	
	// 시간설정
	function displayTime(time){
		var today = new Date(); 
		var gap = today.getTime() - time; 
		var dateObj = new Date(time);		
		var str="";
		
		if(gap < (1000*60*60*24)) {
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return[(hh>9?'':'0')+hh,':',(mi>9?'':'0')+mi,':',(ss>9?'':'0')+ss].join('');			
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();

			return[yy,'/',(mm>9?'':'0')+mm,'/',(dd>9?'':'0')+dd].join('');						
		}
	};
	
	// 메서드 호출
	return {
		add:add,		
		getList:getList,
		remove:remove,
		update:update,
		get:get,
		displayTime:displayTime
	};
	
})();