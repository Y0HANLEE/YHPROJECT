package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardAttachVO;
import org.project.domain.Board.BoardVO;

public interface BoardService {
	//등록
	public void register(BoardVO board);	
	//조회
	public BoardVO get(Long bno);
	public List<BoardVO> getList(Criteria cri); //게시글목록
	public List<BoardAttachVO> getAttachList(Long bno); //첨부파일목록
	public int getTotal(Criteria cri); //페이징처리를 위한 게시물 총개수 파악	
	//수정
	public boolean modify(BoardVO board);
	public void upHit(Long bno); //조회수 증가	
	//삭제
	public boolean remove(Long bno);
}
