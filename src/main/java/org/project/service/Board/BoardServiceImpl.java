package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardAttachVO;
import org.project.domain.Board.BoardVO;
import org.project.mapper.Board.BoardAttachMapper;
import org.project.mapper.Board.BoardMapper;
import org.project.mapper.Board.BoardReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bmapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper amapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardReplyMapper rmapper;
	
	/* 게시글 등록 + 첨부파일(tx) */
	@Transactional
	@Override
	public void register(BoardVO board) {
		bmapper.insertSelectKey(board);		
		//첨부파일x
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		} 		
		//첨부파일o
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno()); // attach의 bno를 board의 bno로 지정
			amapper.insert(attach); // 지정된 attach의 bno값으로 amapper를 통해 첨부파일 등록
		});	
	}
	
	/* 게시글 조회 */
	@Override
	public BoardVO get(Long bno) {
		upHit(bno); // 게시글 조회때 조회수 1 증가
		return bmapper.read(bno);
	}
	
	/* 게시글 목록 조회(페이징) */
	@Override
	public List<BoardVO> getList(Criteria cri){
		return bmapper.getListWithPaging(cri);
	}
	
	/* 첨부파일 목록 조회 */
	@Override
	public List<BoardAttachVO> getAttachList(Long bno){	
		return amapper.findByBno(bno);
	}
	
	/* 총 게시물 수 계산 */
	@Override
	public int getTotal(Criteria cri) {
		return bmapper.getTotalCount(cri);
	}
	
	/* 게시물 이동 */
	@Override
	public BoardVO move(Long bno) {
		return bmapper.move(bno);
	}
		
	/* 게시글 수정 + 첨부파일 수정(tx) */
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		// 첨부파일의 수정 : 전체삭제 후 다시 등록
		amapper.deleteAll(board.getBno()); // 전부 삭제
		
		boolean modifyResult = bmapper.update(board) == 1;
		log.info("[BoardService]--------------------------delete");
		
		if(modifyResult && board.getAttachList()!=null && board.getAttachList().size()>0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				amapper.insert(attach); // 다시 등록
				log.info("[BoardService]--------------------------"+attach);
			});
		}
		
		return modifyResult;
	}
	
	/* 조회수 증가 */
	@Override
	public void upHit(Long bno) {
		bmapper.upHit(bno);
	}
	
	/* 게시글 삭제 + 첨부파일 삭제(tx) */
	@Transactional
	@Override
	public boolean remove(Long bno) {
		amapper.deleteAll(bno); // 게시물 삭제시 첨부파일도 함께 지우기		
		return bmapper.delete(bno) == 1;
	}
}
