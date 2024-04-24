package org.project.controller;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyPageDTO;
import org.project.domain.Album.AlbumReplyVO;
import org.project.service.Album.AlbumReplyService;
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
@RequestMapping("/album/replies/*")
public class AlbumReplyController {

	private AlbumReplyService rservice;
	
	/* 댓글 등록 + 인증된 사용자(로그인) */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/new", consumes="application/json", produces={MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody AlbumReplyVO reply) {		
		int insertCount = rservice.register(reply);				
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 댓글 목록 확인 */
	@GetMapping(value="/pages/{ano}/{page}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AlbumReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("ano") Long ano){
		Criteria cri = new Criteria(page, 10);
		return new ResponseEntity<>(rservice.getListPage(cri, ano), HttpStatus.OK);
	}
	
	/* 댓글 조회 */
	@GetMapping(value="/{rno}", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<AlbumReplyVO> get(@PathVariable("rno") Long rno){
		return new ResponseEntity<>(rservice.get(rno), HttpStatus.OK);
	}
	
	/* 댓글 삭제 + 인증된 사용자(로그인=작성자) */
	@PreAuthorize("principal.username == #reply.replyer")
	@DeleteMapping(value="/{rno}", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno, @RequestBody AlbumReplyVO reply){
		return rservice.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	/* 댓글 수정 + 인증된 사용자(로그인=작성자) */
	@PreAuthorize("principal.username == #reply.replyer")
	@RequestMapping(value="/{rno}", method={RequestMethod.PATCH, RequestMethod.PUT}, consumes="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody AlbumReplyVO reply, @PathVariable("rno") Long rno){
		reply.setRno(rno);		
		return rservice.modify(reply) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
