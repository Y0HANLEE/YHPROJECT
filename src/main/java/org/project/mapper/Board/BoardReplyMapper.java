package org.project.mapper.Board;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyVO;

public interface BoardReplyMapper {	
	//등록
	public int insert(BoardReplyVO reply); // 새 댓글 등록

	//조회
	public BoardReplyVO read(Long rno); // 특정 댓글 조회
	public List<BoardReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno); // 특정 게시글에 달린 댓글의 목록 조회 | 페이징처리를 위해 Criteria사용 
	public int getCountByBno(Long bno); // 특정 게시글에 달린 댓글 개수 파악

	//수정
	public int update(BoardReplyVO reply); // 특정 댓글 수정
	
	//삭제
	public int delete(Long rno); // 특정 댓글 삭제	
}

