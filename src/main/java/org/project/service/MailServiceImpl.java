package org.project.service;

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
	
	/* �⺻�������� - ���� 
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
	
	public String renewalPwMail(String email, String ranPw) {
		Mail mail = new Mail();
		mail.setFromUser("kevinyh18@gmail.com");
		mail.setToUser(email);                    
		mail.setTitle("[YH_PROJECT] ��й�ȣ �ʱ�ȭ ����");
		mail.setContent("�ȳ��ϼ��� ����,<br>����� �ӽú�й�ȣ�� "+ranPw+"�Դϴ�.<br>�� ��й�ȣ�� �ӽú�й�ȣ�̳� ���������� �����ϴ�.<br>������ Ȩ�������� �湮�ϼż� ��й�ȣ�� �����ϴ°��� ��õ�帳�ϴ�.");
		
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
}
