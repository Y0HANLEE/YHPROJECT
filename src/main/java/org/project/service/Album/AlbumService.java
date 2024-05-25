package org.project.service.Album;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumAttachVO;
import org.project.domain.Album.AlbumVO;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {
	//���
	public void register(AlbumVO album);
	//��ȸ
	public AlbumVO read(Long ano);
	public List<AlbumVO> getList(Criteria cri); //�Խù����
	public List<AlbumAttachVO> attachList(Long ano); //÷�����ϸ��
	public int totalCnt(Criteria cri); //����¡ ó���� ���� �Խñ� �� ���� �ľ�
	public AlbumVO move(Long ano);	
	//����
	public int modify(AlbumVO album);
	public void upHit(Long ano); //��ȸ�� ����	
	//����
	public int remove(Long ano);
}
