package org.project.controller;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.project.service.IntroService;
import org.project.service.MailService;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/main/*")
public class MainController {	
	@Setter(onMethod_=@Autowired)
	private UserService uservice;
		
	@Setter(onMethod_=@Autowired)
	private BCryptPasswordEncoder pwEncoder; //비밀번호 인코딩
	
	@Setter(onMethod_=@Autowired)
	private MailService mservice;

	
	@Setter(onMethod_=@Autowired)
    private IntroService iservice;
	
	/*홈/인트로 화면 조회*/
    @GetMapping({"/home","/intro"})
    public void introPage(Model model) {    	
    	model.addAttribute("home", iservice.read(1));
    	model.addAttribute("intro", iservice.read(2));
    }
	
	/*접근권한에러*/
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		model.addAttribute("msg", "Access Denied");
	}
	
	/*로그인*/
	@GetMapping("/loginPage")
	public void loginPage(String error, String logout, Model model) {		
		if(error != null) {//로그인에러발생
			model.addAttribute("error", "일치하는 정보가 없습니다. 다시 로그인해주세요.");
		}		
		if(logout != null) {//로그아웃
			model.addAttribute("logout", "로그아웃되었습니다.");
		}
	}	
	
	/*로그아웃*/
	@GetMapping("/logoutPage")
	public void logoutPage() {
	
	}
	
	/* 아이디 찾기 */
	@ResponseBody
	@RequestMapping(value="/findId", method = RequestMethod.POST)
	public ResponseEntity<List<String>> findId(@RequestParam("name") String name, @RequestParam("email") String email) throws Exception {
		try {
	        List<String> userids = uservice.findId(name, email); // 아이디 저장
	        log.info("[SecurityController]List<String>userids------------------------------"+userids);	
	        if (userids.isEmpty()) {
	        	log.info("[SecurityController]cant search your id---------------------");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } else {
	        	log.info("[SecurityController]this is user id-------------------------"+userids);
	            return ResponseEntity.ok(userids);
	        } 
	    } catch (Exception e) {	       
	    	log.info("[SecurityController]fail search userid------------------------------");
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/* 비밀번호 초기화 */
	@PostMapping("/renewalPw")
	public ResponseEntity<String> renewalPw(String randomPw, @RequestParam("userid") String userid, @RequestParam("email") String email, Model model){
		String ranPw = RandomStringUtils.randomAlphanumeric(8); //8자리 영어+숫자 난수
		String encodePw = pwEncoder.encode(ranPw);
		
		if(uservice.renewalPw(encodePw, userid, email) == 1) {			
			log.info("[SecurityController]ranPw----------------------------------------"+ranPw);
			log.info("[SecurityController]mail to--------------------------------------"+email);
			mservice.renewalPwMail(email, ranPw);	        
			return new ResponseEntity<>("success", HttpStatus.OK);
		} else {
			log.info("[SecurityController]fail renewal password------------------------------");
			return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}		  
	}
}