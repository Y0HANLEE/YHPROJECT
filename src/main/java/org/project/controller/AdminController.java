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
	
	/* �Խñ� ��� ȭ�� (LIST) : ����¡ */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {	
		AuthVO auth = new AuthVO();
		model.addAttribute("auth", auth);
		model.addAttribute("list", uservice.getUserList(cri));		
		model.addAttribute("pageMaker", new PageDTO(cri, uservice.getTotalUser(cri)));
	}
				
	/*���� �α��� ����� ���� Ȯ��*/	
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
	
	/* ����� ���� ����(�������) */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/auth")
	public String updateAuth(@RequestParam("auth") String auth, @RequestParam("userid") String userid, @ModelAttribute("cri") Criteria cri,  RedirectAttributes rttr) {		
		if(uservice.updateAuth(auth, userid) == 1) {
			String authVal = "";
			if("ROLE_ADMIN".equals(auth)) { authVal = "������"; } else if("ROLE_MANAGER".equals(auth)) { authVal = "���"; 	} else { authVal = "�Ϲ�ȸ��"; }
			rttr.addFlashAttribute("result", userid+"���� ����� ["+authVal+"](��)�� �����Ǿ����ϴ�.");			
		}		
		return "redirect:/admin/list" + cri.getListLink();
	}
	
	/* ȸ��Ż��  */
	@Transactional
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("userid") String userid, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception {		
		uservice.remove(userid);
		log.info("[controller]-------------------------------------deleted userid:"+userid);
		rttr.addFlashAttribute("result", userid+"���� ������ �����Ǿ����ϴ�.");
		
		return "redirect:/admin/list" + cri.getListLink();
	}		
	
	@GetMapping({"/home","/intro"})
	public void getUpdateIntroduce(Model model) throws NullPointerException{
		model.addAttribute("home", iservice.read(1));
		model.addAttribute("intro", iservice.read(2));		
	}
	
	/* ����/��Ʈ�� ������ ���� */
	@PostMapping({"/home","/intro"})
	public String updateIntroduce(IntroVO intro, RedirectAttributes rttr, Model model) {
		iservice.update(intro);
		log.info(intro);
		//÷�������� �ִٸ�
		if(intro.getAttachList() != null) {
			intro.getAttachList().forEach(attach -> log.info(attach));
		}

		if(intro.getBoardtype() == 1) {
			rttr.addFlashAttribute("result", "����ȭ���� ������ �Ϸ�Ǿ����ϴ�.");
			return "redirect:/main/home";
		} else if(intro.getBoardtype() == 2) {
			rttr.addFlashAttribute("result", "�Ұ��������� ������ �Ϸ�Ǿ����ϴ�.");
			return "redirect:/main/intro";
		}
		
		return "redirect:/main/home";
	}	
	
	/* ÷������ ���� ȭ��ó�� : json���� ������ ��ȯ */
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<IntroAttachVO>> getAttachList(int boardtype) {	
		return new ResponseEntity<>(iservice.attachList(boardtype), HttpStatus.OK);
	}
}
