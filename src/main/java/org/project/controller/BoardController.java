package org.project.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.PageDTO;
import org.project.domain.Board.BoardAttachVO;
import org.project.domain.Board.BoardVO;
import org.project.service.Board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	@Setter(onMethod_=@Autowired)
	private BoardService bservice;
	
	private static final String UPLOAD_PATH = "/opt/tomcat/upload/"; //AWS
	//private static final String UPLOAD_PATH = "c:\\upload\\";
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get(UPLOAD_PATH+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);

				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbnail = Paths.get(UPLOAD_PATH+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbnail);
				}
			} catch (Exception e) {
				log.error("delete file error "+e.getMessage());
			}
		});			 
	}
	
	/* 게시글 목록 화면 (LIST) : 페이징 */
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {		
		model.addAttribute("list", bservice.getList(cri)); 
		
		int total = bservice.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	/* 게시글 등록 화면 (REGISTER) + 인증된 사용자(로그인) */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void register(Model model) {
		Date now = new Date();
		model.addAttribute("now", now);
	}
	
	/* 게시글 등록 처리 (REGISTER) + 인증된 사용자(로그인) */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		//첨부파일이 있다면
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		bservice.register(board);		
		rttr.addFlashAttribute("result", board.getBno());		
		
		return "redirect:/board/list";
	}
	
	/* 게시글 조회, 수정 화면 : 같은 파라미터 사용으로 함께 사용 */
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {		
		model.addAttribute("board", bservice.get(bno));		
	}	
	
	/* 게시글 수정 처리 (MODIFY) + 인증된 사용자(로그인=작성자), #:메서드의 파라미터를 받아옴.→ board로부터 writer정보를 받아옴. */
	@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if(bservice.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	/* 게시글 삭제 처리 (REMOVE) + 인증된 사용자(로그인=작성자), #:메서드의 파라미터를 받아옴.→ String writer 추가 후 받아옴. */
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) {
		List<BoardAttachVO> attachList = bservice.getAttachList(bno);
				
		if(bservice.remove(bno)) {			
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}		
		
		return "redirect:/board/list" + cri.getListLink();
	}	
	
	/* 첨부파일 관련 화면처리 : json으로 데이터 반환 */
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		return new ResponseEntity<>(bservice.getAttachList(bno), HttpStatus.OK);
	}
}
