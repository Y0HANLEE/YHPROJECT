package org.project.service;

import org.project.domain.Mail;
import org.springframework.stereotype.Service;

@Service
public interface MailService {		
	public String sendMail(Mail mail); // 메일 전송 서비스(기본)
	public String renewalPwMail(String email, String ranPw); // 비밀번호 초기화 메일 전송 서비스
}
