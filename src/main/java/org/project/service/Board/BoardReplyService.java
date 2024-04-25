package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyPageDTO;
import org.project.domain.Board.BoardReplyVO;

public interface BoardReplyService {
	//등록
	public int register(BoardReplyVO reply);	
	//조회
	public BoardReplyVO get(Long rno);	
	public BoardReplyPageDTO getListPage(Criteria cri, Long bno); //게시물목록조회+페이징
	public List<BoardReplyVO> getListAll(Criteria cri); //홈화면 출력
	//수정
	public int modify(BoardReplyVO reply);	
	//삭제
	public int remove(Long rno);
}

