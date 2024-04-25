package org.project.service.Album;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyPageDTO;
import org.project.domain.Album.AlbumReplyVO;

public interface AlbumReplyService {
	//���
	public int register(AlbumReplyVO reply);	
	//��ȸ
	public AlbumReplyVO get(Long rno);	
	public AlbumReplyPageDTO getListPage(Criteria cri, Long bno); //�Խù������ȸ+����¡	
	public List<AlbumReplyVO> getListAll(Criteria cri); //Ȩȭ�� ���
	//����
	public int modify(AlbumReplyVO reply);	
	//����
	public int remove(Long rno);
}

