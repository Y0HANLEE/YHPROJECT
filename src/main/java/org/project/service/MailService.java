package org.project.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {		
	//public String sendMail(Mail mail); // 메일 전송 서비스(기본)-보류
	public String joinMail(String email, String ranStr); //본인인증메일
	public String renewalPwMail(String ranPw, String email); //비밀번호 초기화 메일 전송 서비스
}
