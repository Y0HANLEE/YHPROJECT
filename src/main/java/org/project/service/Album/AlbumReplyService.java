package org.project.service.Album;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyPageDTO;
import org.project.domain.Album.AlbumReplyVO;

public interface AlbumReplyService {
	//등록
	public int register(AlbumReplyVO reply);	
	//조회
	public AlbumReplyVO get(Long rno);	
	public AlbumReplyPageDTO getListPage(Criteria cri, Long bno); //게시물목록조회+페이징	
	//수정
	public int modify(AlbumReplyVO reply);	
	//삭제
	public int remove(Long rno);
}

