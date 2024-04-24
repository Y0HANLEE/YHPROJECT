package org.project.mapper.Album;

import java.util.List;

import org.project.domain.Album.AlbumAttachVO;

public interface AlbumAttachMapper {
	//���
	public void insert(AlbumAttachVO attach); // �� ÷������ ���

	//��ȸ
	public List<AlbumAttachVO> findByAno(Long ano); // Ư�� �Խñۿ� ���Ե� ÷�����ϸ�� ��ȸ 
	public List<AlbumAttachVO> getOldFiles(); // �Ϸ��� ÷�����ϸ�� ��ȸ | �� �� ������ ����

	//���� : ��� ���� �� �������� ó��
	
	//����
	public void delete(String uuid);
	public void deleteAll(Long ano); // Ư�� �Խñۿ� ���Ե� ÷������ ���� ����	
}
