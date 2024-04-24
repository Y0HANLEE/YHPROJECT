console.log("Reply Module.........");

var replyService = ( function(){
	
	/* 댓글 추가 */
	function add(reply, callback, error){
		console.log("add reply........");
		
		$.ajax({
			type:'post',
			url:'/replies/new',
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
	
 	function getList(param, callback, error){
 		var bno = param.bno;
 		var page = param.page || 1;
 		
 		$.getJSON("/replies/pages/"+bno+"/"+page,
			function(data) {
				if(callback){
					callback(data); // 댓글 리스트만 가져옴
					//callback(data.replyCnt, data.list); // 댓글 개수 + 리스트
				}				
			}).fail(function(xhr,status,err) {
				if(error){
					error();
				}
			});
 	}	
	 	
	
	// 메서드 호출
	return {
		add:add,		
		getList:getList
	};
	
})();