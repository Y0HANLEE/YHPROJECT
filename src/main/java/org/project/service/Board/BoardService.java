package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardAttachVO;
import org.project.domain.Board.BoardVO;

public interface BoardService {
	//���
	public void register(BoardVO board);	
	//��ȸ
	public BoardVO get(Long bno);
	public List<BoardVO> getList(Criteria cri); //�Խñ۸��
	public List<BoardAttachVO> getAttachList(Long bno); //÷�����ϸ��
	public int getTotal(Criteria cri); //����¡ó���� ���� �Խù� �Ѱ��� �ľ�	
	//����
	public boolean modify(BoardVO board);
	public void upHit(Long bno); //��ȸ�� ����	
	//����
	public boolean remove(Long bno);
}
