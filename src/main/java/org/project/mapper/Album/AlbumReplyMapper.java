package org.project.mapper.Album;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyVO;

public interface AlbumReplyMapper {	
	//등록
	public int insert(AlbumReplyVO reply); // 새 댓글 등록 
	
	//조회
	public AlbumReplyVO read(Long rno); // 특정 댓글 조회 
	public List<AlbumReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("ano") Long ano); // 특정 게시글에 달린 댓글의 목록 조회 | 페이징처리를 위해 Criteria사용
	public int getCountByAno(Long ano); // 특정 게시글에 달린 댓글 개수 파악

	//수정
	public int update(AlbumReplyVO reply); // 특정 댓글 수정
	
	//삭제
	public int delete(Long rno); // 특정 댓글 삭제
}

