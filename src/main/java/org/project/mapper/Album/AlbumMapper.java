package org.project.mapper.Album;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumVO;

public interface AlbumMapper {
	//등록
	public void insertSelectKey(AlbumVO album); // 새 게시글 등록 | 게시글 등록시 ano를 기입하기 위해 우선적으로 ano의 값을 정하는 <selectKey> 사용
	
	//조회
	public AlbumVO read(Long ano); // 특정 게시글 조회
	public List<AlbumVO> getListWithPaging(Criteria cri); // 게시글 목록 조회 | 조건(검색,페이징)을 위해 파라미터로 Criteria사용
	public int getTotalCnt(Criteria cri); // 페이징처리를 위한 게시물의 총개수 파악 | 조건(검색,페이징)을 위해 파라미터로 Criteria사용
	public AlbumVO move(Long ano); //게시글 이동
	
	//수정
	public int update(AlbumVO album); // 특정 게시글 수정 
	public void upHit(Long ano); // 특정 게시글 조회수 증가
	public void updateReplyCnt(@Param("ano") Long ano, @Param("amount") int amount); // 특정 게시글의 댓글수 변동 | amount는 변동사항(+1, -1 등)_reply등록, reply삭제 
	
	//삭제
	public int delete(Long ano); // 특정 게시글 삭제
}
