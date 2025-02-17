package org.project.service;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.project.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class MailServiceImpl implements MailService{	
	@Autowired
    private JavaMailSender mailSender;
	
	/* 기본메일전송 - 보류 
	public String sendMail(Mail mail) {
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
            log.info(e);
            return "error";
        }
        return "success";    
	}
	*/
	
	public String joinMail(String ranStr, String email) {
		Mail mail = new Mail();
		mail.setFromUser("kevinyh18@gmail.com");
		mail.setToUser(email);                    
		mail.setTitle("[YH_PROJECT] 회원가입 본인인증 메일");
		mail.setContent(
				"안녕하세요 고객님,"
				+ "당신의 인증번호는 "+ranStr+" 입니다."
				+ "회원가입을 진행해주시기 바랍니다.");
		
		log.info("-------------------------mail:"+mail);
		
		try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");
            message.setTo(mail.getToUser());
            message.setText(mail.getContent(), true);
            message.setFrom(mail.getFromUser());
            message.setSubject(mail.getTitle());
            message.setSentDate(new Date());
            mailSender.send(msg);

        } catch(Exception e){
            log.info(e);
            return "error";
        }
        return "success";
	}
	
	public String renewalPwMail(String email, String ranPw) {
		Mail mail = new Mail();
		mail.setFromUser("kevinyh18@gmail.com");
		mail.setToUser(email);                    
		mail.setTitle("[YH_PROJECT] 비밀번호 초기화 메일");
		mail.setContent("안녕하세요 고객님,<br>당신의 임시비밀번호는 <b>"+ranPw+"</b>입니다.<br>이 비밀번호는 임시비밀번호이나 삭제기한이 없습니다.<br>하지만 홈페이지에 방문하셔서 비밀번호를 수정하는것을 추천드립니다.");
		
		log.info("-------------------------mail:"+mail);
		
		try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");
            message.setTo(mail.getToUser());
            message.setText(mail.getContent(), true); // HTML 형식 ok
            message.setFrom(mail.getFromUser());
            message.setSubject(mail.getTitle());
            
            mailSender.send(msg);

        } catch(Exception e){
            log.info(e);
            return "error";
        }
        return "success";
	}
}
