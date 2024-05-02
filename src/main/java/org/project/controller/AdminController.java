package org.project.controller;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.IntroAttachVO;
import org.project.domain.IntroVO;
import org.project.domain.PageDTO;
import org.project.domain.User.AuthVO;
import org.project.service.IntroService;
import org.project.service.MailService;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Setter(onMethod_=@Autowired)
	private UserService uservice;
		
	@Setter(onMethod_=@Autowired)
	private MailService mservice;	
	
	@Setter(onMethod_=@Autowired)
	private IntroService iservice;
	
	/* 게시글 목록 화면 (LIST) : 페이징 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {	
		AuthVO auth = new AuthVO();
		model.addAttribute("auth", auth);
		model.addAttribute("list", uservice.getUserList(cri));		
		model.addAttribute("pageMaker", new PageDTO(cri, uservice.getTotalUser(cri)));
	}
				
	/*현재 로그인 사용자 권한 확인*/	
	@GetMapping("/auth")
	public void getCurrentUserAuthorities(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("auth", authentication.getAuthorities().toString());
            model.addAttribute("id", userid);
        } else {
            model.addAttribute("auth", "Anonymous");
        }        
    }
	
	/* 사용자 권한 조정(등급조정) */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/auth")
	public String updateAuth(@RequestParam("auth") String auth, @RequestParam("userid") String userid, @ModelAttribute("cri") Criteria cri,  RedirectAttributes rttr) {		
		if(uservice.updateAuth(auth, userid) == 1) {
			String authVal = "";
			if("ROLE_ADMIN".equals(auth)) { authVal = "관리자"; } else if("ROLE_MANAGER".equals(auth)) { authVal = "운영자"; 	} else { authVal = "일반회원"; }
			rttr.addFlashAttribute("result", userid+"님의 등급이 ["+authVal+"](으)로 조정되었습니다.");			
		}		
		return "redirect:/admin/list" + cri.getListLink();
	}
	
	/* 회원탈퇴  */
	@Transactional
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("userid") String userid, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception {		
		uservice.remove(userid);
		log.info("[controller]-------------------------------------deleted userid:"+userid);
		rttr.addFlashAttribute("result", userid+"님의 계정이 삭제되었습니다.");
		
		return "redirect:/admin/list" + cri.getListLink();
	}		
	
	@GetMapping({"/home","/intro"})
	public void getUpdateIntroduce(Model model) throws NullPointerException{
		model.addAttribute("home", iservice.read(1));
		model.addAttribute("intro", iservice.read(2));		
	}
	
	/* 메인/인트로 페이지 수정 */
	@PostMapping({"/home","/intro"})
	public String updateIntroduce(IntroVO intro, RedirectAttributes rttr, Model model) {
		iservice.update(intro);
		log.info(intro);
		//첨부파일이 있다면
		if(intro.getAttachList() != null) {
			intro.getAttachList().forEach(attach -> log.info(attach));
		}

		if(intro.getBoardtype() == 1) {
			rttr.addFlashAttribute("result", "메인화면의 수정이 완료되었습니다.");
			return "redirect:/main/home";
		} else if(intro.getBoardtype() == 2) {
			rttr.addFlashAttribute("result", "소개페이지의 수정이 완료되었습니다.");
			return "redirect:/main/intro";
		}
		
		return "redirect:/main/home";
	}	
	
	/* 첨부파일 관련 화면처리 : json으로 데이터 반환 */
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<IntroAttachVO>> getAttachList(int boardtype) {	
		return new ResponseEntity<>(iservice.attachList(boardtype), HttpStatus.OK);
	}
}
