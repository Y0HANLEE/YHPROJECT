package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyPageDTO;
import org.project.domain.Board.BoardReplyVO;
import org.project.mapper.Board.BoardMapper;
import org.project.mapper.Board.BoardReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class BoardReplyServiceImpl implements BoardReplyService{
	
	@Setter(onMethod_ = @Autowired)
	private BoardReplyMapper rmapper;
			
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bmapper;
	
	/* ��� ��� + ��ۼ�+1 */
	@Transactional
	@Override
	public int register(BoardReplyVO reply) {
		bmapper.updateReplyCnt(reply.getBno(), 1); // bno�� 1����(����� 1�� �߰��Ǿ����Ƿ�)
		return rmapper.insert(reply);
	}
	
	/* ��� ��ȸ */
	@Override
	public BoardReplyVO get(Long rno) {		 
		return rmapper.read(rno);
	}
	
	/* �Խñۿ� �޸� ��� ��� ��ȸ(����¡) */
	@Override
	public BoardReplyPageDTO getListPage(Criteria cri, Long bno) {	
		return new BoardReplyPageDTO(rmapper.getCountByBno(bno), rmapper.getListWithPaging(cri, bno));
	}
	
	/* Ȩȭ�� ��� */
	@Override
	public List<BoardReplyVO> getListAll(Criteria cri){
		return rmapper.getListAll(cri);
	}
	
	/* ��� ���� */
	@Override
	public int modify(BoardReplyVO reply) {
		return rmapper.update(reply);
	}
	
	/* ��� ���� + ��ۼ�-1 */
	@Transactional
	@Override
	public int remove(Long rno) {
		BoardReplyVO reply = rmapper.read(rno);
		bmapper.updateReplyCnt(reply.getBno(), -1); // bno�� 1����(����� 1�� �����Ǿ����Ƿ�)
		return rmapper.delete(rno);
	}		
}
