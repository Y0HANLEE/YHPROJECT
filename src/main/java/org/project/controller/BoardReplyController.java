package org.project.controller;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyPageDTO;
import org.project.domain.Board.BoardReplyVO;
import org.project.service.Board.BoardReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins="*")
@RequestMapping("/board/replies/*")
public class BoardReplyController {

	private BoardReplyService rservice;
	
	/* 댓글 등록 + 인증된 사용자(로그인) */
	//@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/new", consumes="application/json", produces={MediaType.TEXT_PLAIN_VALUE}) 
	public ResponseEntity<String> create(@RequestBody BoardReplyVO reply) {	
		// ①consumes:브라우저(클라이언트)에서 입력해 Controller로 보내는 자료타입		| JSON형태로 입력 
		// ②@RequestBody : 받아온 데이터를 Controller에서 처리할 자료타입 			| BoardReplyVO의 형태로 처리
		// ③produces: Controller에서 생성하여 브라우저(클라이언트)로 반환하는 자료타입	| text형태로 리턴. 
		int insertCount = rservice.register(reply);
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 댓글 목록 확인 : url에 page, bno사용 */
	@GetMapping(value="/pages/{bno}/{page}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		Criteria cri = new Criteria(page, 10);
		return new ResponseEntity<>(rservice.getListPage(cri, bno), HttpStatus.OK);
	}
	
	/* 댓글 조회 : url에 bno사용  */
	@GetMapping(value="/{rno}", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<BoardReplyVO> get(@PathVariable("rno") Long rno){
		return new ResponseEntity<>(rservice.get(rno), HttpStatus.OK);
	}
	
	/* 댓글 삭제 + 인증된 사용자(로그인=작성자) : url에 bno사용, 객체 데이터(BoardReplyVO)를 전달하겠다. */
	//@PreAuthorize("principal.username == #reply.replyer")
	@DeleteMapping(value="/{rno}", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno, @RequestBody BoardReplyVO reply){
		return rservice.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	/* 댓글 수정 + 인증된 사용자(로그인=작성자) */
	//@PreAuthorize("principal.username == #reply.replyer")
	@RequestMapping(value="/{rno}", method={RequestMethod.PATCH, RequestMethod.PUT}, consumes="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody BoardReplyVO reply, @PathVariable("rno") Long rno){
		reply.setRno(rno);		
		return rservice.modify(reply) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
