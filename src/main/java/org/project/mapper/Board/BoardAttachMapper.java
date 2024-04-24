package org.project.mapper.Board;

import java.util.List;

import org.project.domain.Board.BoardAttachVO;

public interface BoardAttachMapper {
	//���
	public void insert(BoardAttachVO attach); // �� ÷������ ���
	
	//��ȸ
	public List<BoardAttachVO> findByBno(Long bno); // Ư�� �Խñۿ� ���Ե� ÷�����ϸ�� ��ȸ
	public List<BoardAttachVO> getOldFiles(); // �Ϸ��� ÷�����ϸ�� ��ȸ | �� �� ������ ����
	
	//���� : ��� ���� �� �������� ó��
	
	//����
	public void delete(String uuid);
	public void deleteAll(Long bno); // Ư�� �Խñۿ� ���Ե� ÷������ ���� ����	
}
