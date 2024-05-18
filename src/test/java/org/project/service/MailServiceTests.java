package org.project.service;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MailServiceTests {
	@Autowired
    private JavaMailSender mailSender;
	
	/* 비밀번호 초기화 메일 - 임시비밀번호 발급 */
	@Test
	public void testRenewalPwMail() {
		String ranPw = "HI4g41";
		Mail mail = new Mail();
		mail.setFromUser("kevinyh18@gmail.com");
		mail.setToUser("kevinyh@naver.com");                    
		mail.setTitle("[YH_PROJECT] 비밀번호 초기화 메일");
		mail.setContent("안녕하세요 고객님, 임시비밀번호는 "+ranPw+"입니다. 이 임시비밀번호는 삭제기한이 없으나, 홈페이지에 방문하셔서 비밀번호를 수정하는것을 추천드립니다.");
		
		log.info("-------------------------mail:"+mail);
		
		
		try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");
            message.setTo(mail.getToUser());
            message.setText(mail.getContent());
            message.setFrom(mail.getFromUser());
            message.setSubject(mail.getTitle());
            
            mailSender.send(msg);

        } catch(Exception e){
            log.info(e+"error");
            
        }
        log.info("success");
	}

}

