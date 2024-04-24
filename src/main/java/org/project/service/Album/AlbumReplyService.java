package org.project.service.Album;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyPageDTO;
import org.project.domain.Album.AlbumReplyVO;

public interface AlbumReplyService {
	//���
	public int register(AlbumReplyVO reply);	
	//��ȸ
	public AlbumReplyVO get(Long rno);	
	public AlbumReplyPageDTO getListPage(Criteria cri, Long bno); //�Խù������ȸ+����¡	
	//����
	public int modify(AlbumReplyVO reply);	
	//����
	public int remove(Long rno);
}

