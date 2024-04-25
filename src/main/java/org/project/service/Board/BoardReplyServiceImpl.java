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
	
	/* 댓글 등록 + 댓글수+1 */
	@Transactional
	@Override
	public int register(BoardReplyVO reply) {
		bmapper.updateReplyCnt(reply.getBno(), 1); // bno에 1증가(댓글이 1개 추가되었으므로)
		return rmapper.insert(reply);
	}
	
	/* 댓글 조회 */
	@Override
	public BoardReplyVO get(Long rno) {		 
		return rmapper.read(rno);
	}
	
	/* 게시글에 달린 댓글 목록 조회(페이징) */
	@Override
	public BoardReplyPageDTO getListPage(Criteria cri, Long bno) {	
		return new BoardReplyPageDTO(rmapper.getCountByBno(bno), rmapper.getListWithPaging(cri, bno));
	}
	
	/* 홈화면 출력 */
	@Override
	public List<BoardReplyVO> getListAll(Criteria cri){
		return rmapper.getListAll(cri);
	}
	
	/* 댓글 수정 */
	@Override
	public int modify(BoardReplyVO reply) {
		return rmapper.update(reply);
	}
	
	/* 댓글 삭제 + 댓글수-1 */
	@Transactional
	@Override
	public int remove(Long rno) {
		BoardReplyVO reply = rmapper.read(rno);
		bmapper.updateReplyCnt(reply.getBno(), -1); // bno에 1감소(댓글이 1개 삭제되었으므로)
		return rmapper.delete(rno);
	}		
}
