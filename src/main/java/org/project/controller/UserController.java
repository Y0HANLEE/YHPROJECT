package org.project.controller;

import java.security.Principal;

import org.apache.commons.lang3.RandomStringUtils;
import org.project.domain.MyCriteria;
import org.project.domain.PageDTO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.CheckVO;
import org.project.domain.User.ProfileImageVO;
import org.project.domain.User.UserVO;
import org.project.service.MailService;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user/*")
public class UserController {
	
	@Setter(onMethod_=@Autowired)
	private UserService uservice;
		
	@Setter(onMethod_=@Autowired)
	private BCryptPasswordEncoder pwEncoder; //��й�ȣ ���ڵ�
	
	@Setter(onMethod_=@Autowired)
	private MailService mservice;
	
	/* ȸ������ ȭ�� */
	@PreAuthorize("isAnonymous()")
	@GetMapping("/join")
	public void join() {}
	
	/* ȸ������ ó�� */
	@PreAuthorize("isAnonymous()")
	@Transactional
	@PostMapping("/join")
	public String join(UserVO user, AuthVO auth, RedirectAttributes rttr) {
		user.setUserpw(pwEncoder.encode(user.getUserpw()));
		uservice.join(user, auth);
		rttr.addFlashAttribute("result", user.getUserid()+"�� ȸ�������� ���ϵ帳�ϴ�.");
		log.info("[controller]------------------------------"+user);
		return "redirect:/";
	}
	
	/* �������� ���Ϲ߼� */
	@Transactional
	@ResponseBody
	@PostMapping("/checkMailSend")
	public ResponseEntity<String> checkMailSend(@RequestParam("email") String email){
		String checkStr = RandomStringUtils.randomAlphanumeric(10); //8�ڸ� ����+���� ����
		log.info("isMailExist-------------------------------"+uservice.isMailExist(email));

	    if(uservice.isMailExist(email) == 0) { // ���� ��ư Ŭ�� ��, DB�� email, checkStr ����
	        CheckVO vo = new CheckVO();
	        vo.setCheckStr(checkStr);
	        vo.setEmail(email);
	        uservice.checkStr(vo);
	        mservice.joinMail(checkStr, email);
	        log.info("[UserController]insert CheckVO----------------------------------"+vo);
	        log.info("[UserController]mail to-----------------------------------------"+email);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } else if (uservice.isMailExist(email) == 1 && uservice.ranStr(checkStr, email) == 1) { // �̸����� �ְ�(=������ �߼۵� ������ȣ�� �ִ�), ������ȣ ������ �̷�����ٸ� - ��������  
	        mservice.joinMail(checkStr, email);
	        log.info("[UserController]checkStr----------------------------------------"+checkStr);
	        log.info("[UserController]mail to-----------------------------------------"+email);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } else {
	        log.info("[UserController]fail-------------------------------------------------");
	        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/* �������� üũ */
	@ResponseBody
	@PostMapping("/checkMailUser")
	public int checkMailUser(@RequestParam("ranStr") String ranStr) throws Exception {		
		int result = uservice.mailCheck(ranStr);
		return result;
	}	 
	
	/* ȸ������ ��ȸ ȭ�� : �α�������=�������� + ������ */
	@PreAuthorize("principal.username == #userid or hasRole('ADMIN')")
	@GetMapping("/profile")
	public void profile(@RequestParam("userid") String userid, Principal principal, Model model) {
		UserVO vo = uservice.read(userid);				
		model.addAttribute("user", vo);		
		AuthVO auth = uservice.readAuth(userid);		
		model.addAttribute("auth", auth);
	}
	
	/* ȸ������ ���� ȭ�� : �α�������=�������� */
	@PreAuthorize("principal.username == #userid")
	@GetMapping("/update")
	public void update(@RequestParam("userid") String userid, Principal principal, Model model) {
		UserVO vo = uservice.read(userid);				
		model.addAttribute("user", vo);		
		AuthVO auth = uservice.readAuth(userid);		
		model.addAttribute("auth", auth);
	}
	
	/* ȸ������ ���� : �α�������=�������� */
	@PreAuthorize("#user.userid==principal.username")
	@Transactional
	@PostMapping("/update")
	public String updateUser(UserVO user, RedirectAttributes rttr) {
		log.info("[UserController]------------------user : "+user);
		log.info("[UserController]------------------profile : "+user.getProfileImg());
		int result = uservice.modify(user);
		if(result>0) {        	
            rttr.addFlashAttribute("result", user.getUserid()+"���� ȸ�������� �����Ǿ����ϴ�.");
        }
		return "redirect:/user/profile?userid="+user.getUserid();
	}
		
	/* ���̵� �ߺ� �˻� */
	@ResponseBody
	@RequestMapping(value="/checkId", method = RequestMethod.POST)
	public int checkId(@RequestParam("userid") String userid) throws Exception {		
		int result = uservice.checkId(userid);
		return result;
	}	
			
	/* ����Ȯ�� : ��й�ȣ ���� */	
	@PreAuthorize("principal.username == #user.userid")
	@ResponseBody	
	@PostMapping("/checkUser")
	public int checkUser(Principal principal, @RequestBody UserVO user) throws Exception {
	    UserVO vo = uservice.read(principal.getName()); // DB���� �������� user��ü
	    String inputPw = user.getUserpw(); // �Էµ� ��ü(profile���� �Է�)
	    int result = 0;	    
		
	    if (principal.getName() != null && principal.getName() != "") {	    	
	        if (pwEncoder.matches(inputPw, vo.getUserpw())) { // matches�޼���� raw pw�� DB�� ���ڵ��� pw�� ��
	            return result = 1; // profile:data===1
			} 
	    }
	    return result;   
	}
	   
	/* ��й�ȣ ���� */
	@PreAuthorize("principal.username == #userid")
	@PostMapping("/updatePw")
	public String updatePassword(Principal principal, @RequestParam("newPw") String newPw, @RequestParam("userid") String userid, @RequestParam("oldPw") String oldPw, RedirectAttributes rttr) {
		 	UserVO user = uservice.read(principal.getName()); // DB���� �������� ��ü user
		 	String userpw = user.getUserpw(); // DB�� ��й�ȣ�� userpw�� ����
	        String newPwen = pwEncoder.encode(newPw); // newPw�� ���ڵ� >> DB�� ���ڵ��ؼ� �����ؾ��ϹǷ�	        
	        
		try {
			uservice.updatePw(newPwen, userid, userpw); // newPw:���ڵ��� newPw, userid:userid, userpw:DB���� ������ userpw
			rttr.addFlashAttribute("result", "��й�ȣ�� ����Ǿ����ϴ�.");
			return "redirect:/user/update?userid="+userid;
		} catch (Exception e) {
			rttr.addFlashAttribute("result", "��й�ȣ ���濡 �����Ͽ����ϴ�.<br>Error���� : "+e.getMessage());
			return "redirect:/user/update?userid="+userid; 
		}
	}
			
	/* ȸ��Ż�� */
	@PreAuthorize("principal.username == #userid")
	@Transactional
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("userid") String userid, RedirectAttributes rttr) throws Exception {		
		uservice.remove(userid);		
		SecurityContextHolder.clearContext(); // ����� ���� context���� : �α׾ƿ����� ���� (�α׾ƿ� Ȥ�� Ż��� ��������� ���)		
		rttr.addFlashAttribute("result", userid+"���� Ż��ó���� �Ϸ�Ǿ����ϴ�.");		
		return "redirect:/";
	}
	
	/* ���� �� �Խñ�(�Ϲ�/����) ��ȸ */	
	@PreAuthorize("principal.username == #cri.userid")
	@GetMapping("/contents")
	public void getMyContentList(MyCriteria cri, Model model) {
		cri.setAmount(10000);
		//�����
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName();
		model.addAttribute("userid", user);
				
		model.addAttribute("board", uservice.findBoardByUserid(cri));
		model.addAttribute("boardPage", new PageDTO(cri, uservice.getBoardCnt(user)));
		model.addAttribute("album", uservice.findAlbumByUserid(cri));
		model.addAttribute("albumPage", new PageDTO(cri, uservice.getAlbumCnt(user)));
	}
	
	/* ���� �� ���(�Ϲ�/����) ��ȸ */
	@PreAuthorize("principal.username == #cri.userid")
	@GetMapping("/comments")
	public void getMyCommentList(MyCriteria cri, Model model) {
		cri.setAmount(10000);
		//�����
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName();
		model.addAttribute("userid", user);
				
		model.addAttribute("boardReply", uservice.findBoardReplyByUserid(cri));
		model.addAttribute("boardReplyPage", new PageDTO(cri, uservice.getBoardReplyCnt(user)));
		model.addAttribute("albumReply", uservice.findAlbumReplyByUserid(cri));
		model.addAttribute("albumReplyPage", new PageDTO(cri, uservice.getAlbumReplyCnt(user)));		
	}	
	
	
	/* ÷������ ���� ȭ��ó�� : json���� ������ ��ȯ*/ 
	@GetMapping(value="/getProfileImg", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ProfileImageVO> getProfileImg(String userid) {
		return new ResponseEntity<>(uservice.getProfileByUserid(userid), HttpStatus.OK);
	}	
}


