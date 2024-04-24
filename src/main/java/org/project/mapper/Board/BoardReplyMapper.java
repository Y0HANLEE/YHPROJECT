package org.project.mapper.Board;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyVO;

public interface BoardReplyMapper {	
	//���
	public int insert(BoardReplyVO reply); // �� ��� ���

	//��ȸ
	public BoardReplyVO read(Long rno); // Ư�� ��� ��ȸ
	public List<BoardReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno); // Ư�� �Խñۿ� �޸� ����� ��� ��ȸ | ����¡ó���� ���� Criteria��� 
	public int getCountByBno(Long bno); // Ư�� �Խñۿ� �޸� ��� ���� �ľ�

	//����
	public int update(BoardReplyVO reply); // Ư�� ��� ����
	
	//����
	public int delete(Long rno); // Ư�� ��� ����	
}

