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
	private BCryptPasswordEncoder pwEncoder; //비밀번호 인코딩
	
	@Setter(onMethod_=@Autowired)
	private MailService mservice;
	
	/* 회원가입 화면 */
	@PreAuthorize("isAnonymous()")
	@GetMapping("/join")
	public void join() {}
	
	/* 회원가입 처리 */
	@PreAuthorize("isAnonymous()")
	@Transactional
	@PostMapping("/join")
	public String join(UserVO user, AuthVO auth, RedirectAttributes rttr) {
		user.setUserpw(pwEncoder.encode(user.getUserpw()));
		uservice.join(user, auth);
		rttr.addFlashAttribute("result", user.getUserid()+"님 회원가입을 축하드립니다.");
		log.info("[controller]------------------------------"+user);
		return "redirect:/";
	}
	
	/* 본인인증 매일발송 */
	@Transactional
	@ResponseBody
	@PostMapping("/checkMailSend")
	public ResponseEntity<String> checkMailSend(@RequestParam("email") String email){
		String checkStr = RandomStringUtils.randomAlphanumeric(10); //8자리 영어+숫자 난수
		log.info("isMailExist-------------------------------"+uservice.isMailExist(email));

	    if(uservice.isMailExist(email) == 0) { // 최초 버튼 클릭 시, DB에 email, checkStr 저장
	        CheckVO vo = new CheckVO();
	        vo.setCheckStr(checkStr);
	        vo.setEmail(email);
	        uservice.checkStr(vo);
	        mservice.joinMail(checkStr, email);
	        log.info("[UserController]insert CheckVO----------------------------------"+vo);
	        log.info("[UserController]mail to-----------------------------------------"+email);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } else if (uservice.isMailExist(email) == 1 && uservice.ranStr(checkStr, email) == 1) { // 이메일이 있고(=기존에 발송된 인증번호가 있다), 인증번호 수정이 이루어졌다면 - 메일전송  
	        mservice.joinMail(checkStr, email);
	        log.info("[UserController]checkStr----------------------------------------"+checkStr);
	        log.info("[UserController]mail to-----------------------------------------"+email);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } else {
	        log.info("[UserController]fail-------------------------------------------------");
	        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/* 본인인증 체크 */
	@ResponseBody
	@PostMapping("/checkMailUser")
	public int checkMailUser(@RequestParam("ranStr") String ranStr) throws Exception {		
		int result = uservice.mailCheck(ranStr);
		return result;
	}	 
	
	/* 회원정보 조회 화면 : 로그인유저=현재사용자 + 관리자 */
	@PreAuthorize("principal.username == #userid or hasRole('ADMIN')")
	@GetMapping("/profile")
	public void profile(@RequestParam("userid") String userid, Principal principal, Model model) {
		UserVO vo = uservice.read(userid);				
		model.addAttribute("user", vo);		
		AuthVO auth = uservice.readAuth(userid);		
		model.addAttribute("auth", auth);
	}
	
	/* 회원정보 수정 화면 : 로그인유저=현재사용자 */
	@PreAuthorize("principal.username == #userid")
	@GetMapping("/update")
	public void update(@RequestParam("userid") String userid, Principal principal, Model model) {
		UserVO vo = uservice.read(userid);				
		model.addAttribute("user", vo);		
		AuthVO auth = uservice.readAuth(userid);		
		model.addAttribute("auth", auth);
	}
	
	/* 회원정보 수정 : 로그인유저=현재사용자 */
	@PreAuthorize("#user.userid==principal.username")
	@Transactional
	@PostMapping("/update")
	public String updateUser(UserVO user, RedirectAttributes rttr) {
		log.info("[UserController]------------------user : "+user);
		log.info("[UserController]------------------profile : "+user.getProfileImg());
		int result = uservice.modify(user);
		if(result>0) {        	
            rttr.addFlashAttribute("result", user.getUserid()+"님의 회원정보가 수정되었습니다.");
        }
		return "redirect:/user/profile?userid="+user.getUserid();
	}
		
	/* 아이디 중복 검사 */
	@ResponseBody
	@RequestMapping(value="/checkId", method = RequestMethod.POST)
	public int checkId(@RequestParam("userid") String userid) throws Exception {		
		int result = uservice.checkId(userid);
		return result;
	}	
			
	/* 본인확인 : 비밀번호 검증 */	
	@PreAuthorize("principal.username == #user.userid")
	@ResponseBody	
	@PostMapping("/checkUser")
	public int checkUser(Principal principal, @RequestBody UserVO user) throws Exception {
	    UserVO vo = uservice.read(principal.getName()); // DB에서 가져오는 user객체
	    String inputPw = user.getUserpw(); // 입력된 객체(profile에서 입력)
	    int result = 0;	    
		
	    if (principal.getName() != null && principal.getName() != "") {	    	
	        if (pwEncoder.matches(inputPw, vo.getUserpw())) { // matches메서드로 raw pw와 DB의 인코딩된 pw를 비교
	            return result = 1; // profile:data===1
			} 
	    }
	    return result;   
	}
	   
	/* 비밀번호 수정 */
	@PreAuthorize("principal.username == #userid")
	@PostMapping("/updatePw")
	public String updatePassword(Principal principal, @RequestParam("newPw") String newPw, @RequestParam("userid") String userid, @RequestParam("oldPw") String oldPw, RedirectAttributes rttr) {
		 	UserVO user = uservice.read(principal.getName()); // DB에서 가져오는 객체 user
		 	String userpw = user.getUserpw(); // DB의 비밀번호를 userpw로 저장
	        String newPwen = pwEncoder.encode(newPw); // newPw를 인코딩 >> DB에 인코딩해서 저장해야하므로	        
	        
		try {
			uservice.updatePw(newPwen, userid, userpw); // newPw:인코딩된 newPw, userid:userid, userpw:DB에서 가져온 userpw
			rttr.addFlashAttribute("result", "비밀번호가 변경되었습니다.");
			return "redirect:/user/update?userid="+userid;
		} catch (Exception e) {
			rttr.addFlashAttribute("result", "비밀번호 변경에 실패하였습니다.<br>Error원인 : "+e.getMessage());
			return "redirect:/user/update?userid="+userid; 
		}
	}
			
	/* 회원탈퇴 */
	@PreAuthorize("principal.username == #userid")
	@Transactional
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("userid") String userid, RedirectAttributes rttr) throws Exception {		
		uservice.remove(userid);		
		SecurityContextHolder.clearContext(); // 사용자 인증 context삭제 : 로그아웃으로 간주 (로그아웃 혹은 탈퇴시 사용자정보 비움)		
		rttr.addFlashAttribute("result", userid+"님의 탈퇴처리가 완료되었습니다.");		
		return "redirect:/";
	}
	
	/* 내가 쓴 게시글(일반/사진) 조회 */	
	@PreAuthorize("principal.username == #cri.userid")
	@GetMapping("/contents")
	public void getMyContentList(MyCriteria cri, Model model) {
		cri.setAmount(10000);
		//사용자
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName();
		model.addAttribute("userid", user);
				
		model.addAttribute("board", uservice.findBoardByUserid(cri));
		model.addAttribute("boardPage", new PageDTO(cri, uservice.getBoardCnt(user)));
		model.addAttribute("album", uservice.findAlbumByUserid(cri));
		model.addAttribute("albumPage", new PageDTO(cri, uservice.getAlbumCnt(user)));
	}
	
	/* 내가 쓴 댓글(일반/사진) 조회 */
	@PreAuthorize("principal.username == #cri.userid")
	@GetMapping("/comments")
	public void getMyCommentList(MyCriteria cri, Model model) {
		cri.setAmount(10000);
		//사용자
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName();
		model.addAttribute("userid", user);
				
		model.addAttribute("boardReply", uservice.findBoardReplyByUserid(cri));
		model.addAttribute("boardReplyPage", new PageDTO(cri, uservice.getBoardReplyCnt(user)));
		model.addAttribute("albumReply", uservice.findAlbumReplyByUserid(cri));
		model.addAttribute("albumReplyPage", new PageDTO(cri, uservice.getAlbumReplyCnt(user)));		
	}	
	
	
	/* 첨부파일 관련 화면처리 : json으로 데이터 반환*/ 
	@GetMapping(value="/getProfileImg", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ProfileImageVO> getProfileImg(String userid) {
		return new ResponseEntity<>(uservice.getProfileByUserid(userid), HttpStatus.OK);
	}	
}


