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
	
	/* ��й�ȣ �ʱ�ȭ ���� - �ӽú�й�ȣ �߱� */
	@Test
	public void testRenewalPwMail() {
		String ranPw = "HI4g41";
		Mail mail = new Mail();
		mail.setFromUser("kevinyh18@gmail.com");
		mail.setToUser("kevinyh@naver.com");                    
		mail.setTitle("[YH_PROJECT] ��й�ȣ �ʱ�ȭ ����");
		mail.setContent("�ȳ��ϼ��� ����, �ӽú�й�ȣ�� "+ranPw+"�Դϴ�. �� �ӽú�й�ȣ�� ���������� ������, Ȩ�������� �湮�ϼż� ��й�ȣ�� �����ϴ°��� ��õ�帳�ϴ�.");
		
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

