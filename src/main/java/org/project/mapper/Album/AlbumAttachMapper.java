package org.project.mapper.Album;

import java.util.List;

import org.project.domain.Album.AlbumAttachVO;

public interface AlbumAttachMapper {
	//등록
	public void insert(AlbumAttachVO attach); // 새 첨부파일 등록

	//조회
	public List<AlbumAttachVO> findByAno(Long ano); // 특정 게시글에 포함된 첨부파일목록 조회 
	public List<AlbumAttachVO> getOldFiles(); // 하루전 첨부파일목록 조회 | 비교 후 삭제를 위함

	//수정 : 모두 삭제 후 재등록으로 처리
	
	//삭제
	public void delete(String uuid);
	public void deleteAll(Long ano); // 특정 게시글에 포함된 첨부파일 전부 삭제	
}
