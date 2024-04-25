package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyPageDTO;
import org.project.domain.Board.BoardReplyVO;

public interface BoardReplyService {
	//���
	public int register(BoardReplyVO reply);	
	//��ȸ
	public BoardReplyVO get(Long rno);	
	public BoardReplyPageDTO getListPage(Criteria cri, Long bno); //�Խù������ȸ+����¡
	public List<BoardReplyVO> getListAll(Criteria cri); //Ȩȭ�� ���
	//����
	public int modify(BoardReplyVO reply);	
	//����
	public int remove(Long rno);
}

