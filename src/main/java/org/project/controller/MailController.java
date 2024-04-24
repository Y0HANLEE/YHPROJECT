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
		
	/* �ȳ� ���� */
	@GetMapping("/send/mail")
	public ResponseEntity<HttpStatus> getEmail(String toUser){
		try {
            Mail mail = new Mail();
            mail.setFromUser("kevinyh18@gmail.com");
            mail.setToUser(toUser);                    
            mail.setTitle("[YH_PROJECT] ��й�ȣ �ʱ�ȭ ����");
            mail.setContent("�ȳ��ϼ��� ����!");                    
        } catch (Exception e) {
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
