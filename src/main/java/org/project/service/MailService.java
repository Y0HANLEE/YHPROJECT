package org.project.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {		
	//public String sendMail(Mail mail); // ���� ���� ����(�⺻)-����
	public String joinMail(String email, String ranStr); //������������
	public String renewalPwMail(String ranPw, String email); //��й�ȣ �ʱ�ȭ ���� ���� ����
}
