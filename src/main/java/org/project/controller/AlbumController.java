package org.project.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.PageDTO;
import org.project.domain.Album.AlbumAttachVO;
import org.project.domain.Album.AlbumVO;
import org.project.service.Album.AlbumService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor
@RequestMapping("/album/*")
@Log4j
public class AlbumController {	
	
	private AlbumService alservice;

	private void deleteFiles(List<AlbumAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("c:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);

				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbnail = Paths.get("c:\\upload\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbnail);
				}
			} catch (Exception e) {
				log.error("delete file error "+e.getMessage());
			}
		});			 
	}
	
	/* 목록 조회 */	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {		
		model.addAttribute("list", alservice.getList(cri)); // list정보
		model.addAttribute("pageMaker", new PageDTO(cri, alservice.totalCnt(cri))); //페이징
	}
	
	/* 게시글 조회화면 */
	@GetMapping("/get")
	public void get(@RequestParam("ano") Long ano, @ModelAttribute("cri") Criteria cri, Model model) {	
		model.addAttribute("album", alservice.read(ano)); // 게시글 정보
	}
	
	/* 게시글 수정화면 */
	@GetMapping("/modify")
	public void modify(@RequestParam("ano") Long ano, @ModelAttribute("cri") Criteria cri, Model model) {	
		AlbumVO vo = alservice.read(ano); // ano번 글 정보 불러오기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //날짜포맷지정

		Date date = new Date(); //오늘 날짜정보 객체
		String today = sdf.format(date);
		
		Date st = vo.getStartDate(); //ano번 글의 여행시작일자 세팅
		Date ed = vo.getEndDate(); //ano번 글의 여행종료일자 세팅
 		
		if(st==null && ed == null) {// 둘 다 null일경우 | 둘 다 빈칸으로 넘겨줌
			model.addAttribute("start", "");
			model.addAttribute("end", "");
		}else if(st == null) { // 시작일자만 null | start는 빈칸, 날짜포맷에 맞게 end를 넘겨줌 
			String end = sdf.format(vo.getEndDate()); 
		    model.addAttribute("start", "");
			model.addAttribute("end", end);
		} else if(ed == null){ // 종료일자만 null | end는 빈칸, 날짜포맷에 맞게 start를 넘겨줌
			String start = sdf.format(vo.getStartDate());
			model.addAttribute("start", start);
			model.addAttribute("end", "");			
		} else { // 둘 다 제대로 입력 | 날짜포맷에 맞게 start,end로 넘겨줌
			String start = sdf.format(vo.getStartDate());
			String end = sdf.format(vo.getEndDate());
			model.addAttribute("start", start);
			model.addAttribute("end", end);			
		}
		
		model.addAttribute("today", today); // 오늘날짜정보 				
		model.addAttribute("album", vo); // ano게시글 정보		
	}
	
	/* 게시글 등록 화면 (REGISTER) + 인증된 사용자(로그인) */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void register(Model model) {
		Date now = new Date();
		model.addAttribute("now",now);
	}
	
	/* 게시글 등록 처리 (REGISTER) + 인증된 사용자(로그인) */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(AlbumVO album, RedirectAttributes rttr) {
		//첨부파일이 있다면
		if(album.getAttachList() != null) {
			album.getAttachList().forEach(attach -> log.info(attach));
		}
		
		alservice.register(album);		
		rttr.addFlashAttribute("result", album.getAno());		
		
		return "redirect:/album/list";
	}
	
	/* 게시글 수정 처리 (MODIFY) + 인증된 사용자(로그인=작성자), #:메서드의 파라미터를 받아옴.→ board로부터 writer정보를 받아옴. */
	@PreAuthorize("principal.username == #album.writer")
	@PostMapping("/modify")
	public String modify(AlbumVO album, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if(alservice.modify(album)==1) {
			rttr.addFlashAttribute("result", "success");
		}		
		
		return "redirect:/album/list" + cri.getListLink();
	}
	
	/* 게시글 삭제 처리 (REMOVE) + 인증된 사용자(로그인=작성자), #:메서드의 파라미터를 받아옴.→ String writer 추가 후 받아옴. */
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("ano") Long ano, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) {
		List<AlbumAttachVO> attachList = alservice.attachList(ano);
		
		if(alservice.remove(ano)==1) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}		
		
		return "redirect:/album/list" + cri.getListLink();
	}	
	
	/* 첨부파일 관련 화면처리 : json으로 데이터 반환 */
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AlbumAttachVO>> getAttachList(Long ano) {	
		return new ResponseEntity<>(alservice.attachList(ano), HttpStatus.OK);
	}	
}
