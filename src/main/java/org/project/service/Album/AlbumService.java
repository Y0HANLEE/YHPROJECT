package org.project.service.Album;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumAttachVO;
import org.project.domain.Album.AlbumVO;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {
	//등록
	public void register(AlbumVO album);
	//조회
	public AlbumVO read(Long ano);
	public List<AlbumVO> getList(Criteria cri); //게시물목록
	public List<AlbumAttachVO> attachList(Long ano); //첨부파일목록
	public int totalCnt(Criteria cri); //페이징 처리를 위한 게시글 총 개수 파악
	public AlbumVO move(Long ano);	
	//수정
	public int modify(AlbumVO album);
	public void upHit(Long ano); //조회수 증가	
	//삭제
	public int remove(Long ano);
}
