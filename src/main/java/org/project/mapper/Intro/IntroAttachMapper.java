package org.project.mapper.Intro;

import java.util.List;

import org.project.domain.IntroAttachVO;

public interface IntroAttachMapper {
	//���
	public void insert(IntroAttachVO attach); // �� ÷������ ���
	//��ȸ
	public List<IntroAttachVO> findByBoard(int boardtype); //�Խ��ǿ� �޸� ÷�����ϸ����ȸ 
	public List<IntroAttachVO> getOldFiles(); // �Ϸ��� ÷�����ϸ�� ��ȸ | �� �� ������ ����
	//���� : ��� ���� �� �������� ó��
	//����
	public void deleteAll(int boardtype);	
}
