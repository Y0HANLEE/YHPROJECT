package org.project.controller;

import org.project.domain.Mail;
import org.project.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Setter;

@Controller
@RequestMapping("/mail/*")
public class MailController {
	@Setter(onMethod_ = @Autowired)
	private MailService mservice;
		
	/* 안내 메일 */
	@GetMapping("/send/mail")
	public ResponseEntity<HttpStatus> getEmail(String toUser){
		try {
            Mail mail = new Mail();
            mail.setFromUser("kevinyh18@gmail.com");
            mail.setToUser(toUser);                    
            mail.setTitle("[YH_PROJECT] 비밀번호 초기화 메일");
            mail.setContent("안녕하세요 고객님!");                    
        } catch (Exception e) {
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
