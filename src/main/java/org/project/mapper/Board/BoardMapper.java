package org.project.mapper.Board;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardVO;

public interface BoardMapper {		
	//���
	public void insertSelectKey(BoardVO board); // �� �Խñ� ��� | �Խñ� ��Ͻ� bno�� �����ϱ� ���� �켱������ bno�� ���� ���ϴ� <selectKey> ��� 
	
	//��ȸ
	public BoardVO read(Long bno); // Ư�� �Խñ� ��ȸ
	public List<BoardVO> getListWithPaging(Criteria cri); // �Խñ� ��� ��ȸ | ����(�˻�,����¡)�� ���� �Ķ���ͷ� Criteria���
	public int getTotalCount(Criteria cri); // ����¡ó���� ���� �Խù��� �Ѱ��� �ľ� | ����(�˻�,����¡)�� ���� �Ķ���ͷ� Criteria���
	public BoardVO move(Long bno); //�Խñ� �̵�

	//����
	public int update(BoardVO board); // Ư�� �Խñ� ����
	public void upHit(Long bno); // Ư�� �Խñ� ��ȸ�� ����
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount); // Ư�� �Խñ��� ��ۼ� ���� | amount�� ��������(+1, -1 ��)_reply���, reply����
	
	//����
	public int delete(Long bno); // Ư�� �Խñ� ����
}
