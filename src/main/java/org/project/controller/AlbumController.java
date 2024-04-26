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
	
	/* ��� ��ȸ */	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {		
		model.addAttribute("list", alservice.getList(cri)); // list����
		model.addAttribute("pageMaker", new PageDTO(cri, alservice.totalCnt(cri))); //����¡
	}
	
	/* �Խñ� ��ȸȭ�� */
	@GetMapping("/get")
	public void get(@RequestParam("ano") Long ano, @ModelAttribute("cri") Criteria cri, Model model) {	
		model.addAttribute("album", alservice.read(ano)); // �Խñ� ����
	}
	
	/* �Խñ� ����ȭ�� */
	@GetMapping("/modify")
	public void modify(@RequestParam("ano") Long ano, @ModelAttribute("cri") Criteria cri, Model model) {	
		AlbumVO vo = alservice.read(ano); // ano�� �� ���� �ҷ�����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //��¥��������

		Date date = new Date(); //���� ��¥���� ��ü
		String today = sdf.format(date);
		
		Date st = vo.getStartDate(); //ano�� ���� ����������� ����
		Date ed = vo.getEndDate(); //ano�� ���� ������������ ����
 		
		if(st==null && ed == null) {// �� �� null�ϰ�� | �� �� ��ĭ���� �Ѱ���
			model.addAttribute("start", "");
			model.addAttribute("end", "");
		}else if(st == null) { // �������ڸ� null | start�� ��ĭ, ��¥���˿� �°� end�� �Ѱ��� 
			String end = sdf.format(vo.getEndDate()); 
		    model.addAttribute("start", "");
			model.addAttribute("end", end);
		} else if(ed == null){ // �������ڸ� null | end�� ��ĭ, ��¥���˿� �°� start�� �Ѱ���
			String start = sdf.format(vo.getStartDate());
			model.addAttribute("start", start);
			model.addAttribute("end", "");			
		} else { // �� �� ����� �Է� | ��¥���˿� �°� start,end�� �Ѱ���
			String start = sdf.format(vo.getStartDate());
			String end = sdf.format(vo.getEndDate());
			model.addAttribute("start", start);
			model.addAttribute("end", end);			
		}
		
		model.addAttribute("today", today); // ���ó�¥���� 				
		model.addAttribute("album", vo); // ano�Խñ� ����		
	}
	
	/* �Խñ� ��� ȭ�� (REGISTER) + ������ �����(�α���) */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void register(Model model) {
		Date now = new Date();
		model.addAttribute("now",now);
	}
	
	/* �Խñ� ��� ó�� (REGISTER) + ������ �����(�α���) */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(AlbumVO album, RedirectAttributes rttr) {
		//÷�������� �ִٸ�
		if(album.getAttachList() != null) {
			album.getAttachList().forEach(attach -> log.info(attach));
		}
		
		alservice.register(album);		
		rttr.addFlashAttribute("result", album.getAno());		
		
		return "redirect:/album/list";
	}
	
	/* �Խñ� ���� ó�� (MODIFY) + ������ �����(�α���=�ۼ���), #:�޼����� �Ķ���͸� �޾ƿ�.�� board�κ��� writer������ �޾ƿ�. */
	@PreAuthorize("principal.username == #album.writer")
	@PostMapping("/modify")
	public String modify(AlbumVO album, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if(alservice.modify(album)==1) {
			rttr.addFlashAttribute("result", "success");
		}		
		
		return "redirect:/album/list" + cri.getListLink();
	}
	
	/* �Խñ� ���� ó�� (REMOVE) + ������ �����(�α���=�ۼ���), #:�޼����� �Ķ���͸� �޾ƿ�.�� String writer �߰� �� �޾ƿ�. */
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
	
	/* ÷������ ���� ȭ��ó�� : json���� ������ ��ȯ */
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AlbumAttachVO>> getAttachList(Long ano) {	
		return new ResponseEntity<>(alservice.attachList(ano), HttpStatus.OK);
	}	
}
