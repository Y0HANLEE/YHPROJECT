package org.project.service;

import org.project.domain.Mail;
import org.springframework.stereotype.Service;

@Service
public interface MailService {		
	public String sendMail(Mail mail); // ���� ���� ����(�⺻)
	public String renewalPwMail(String email, String ranPw); // ��й�ȣ �ʱ�ȭ ���� ���� ����
}
