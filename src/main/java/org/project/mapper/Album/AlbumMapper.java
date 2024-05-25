package org.project.mapper.Album;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumVO;

public interface AlbumMapper {
	//���
	public void insertSelectKey(AlbumVO album); // �� �Խñ� ��� | �Խñ� ��Ͻ� ano�� �����ϱ� ���� �켱������ ano�� ���� ���ϴ� <selectKey> ���
	
	//��ȸ
	public AlbumVO read(Long ano); // Ư�� �Խñ� ��ȸ
	public List<AlbumVO> getListWithPaging(Criteria cri); // �Խñ� ��� ��ȸ | ����(�˻�,����¡)�� ���� �Ķ���ͷ� Criteria���
	public int getTotalCnt(Criteria cri); // ����¡ó���� ���� �Խù��� �Ѱ��� �ľ� | ����(�˻�,����¡)�� ���� �Ķ���ͷ� Criteria���
	public AlbumVO move(Long ano); //�Խñ� �̵�
	
	//����
	public int update(AlbumVO album); // Ư�� �Խñ� ���� 
	public void upHit(Long ano); // Ư�� �Խñ� ��ȸ�� ����
	public void updateReplyCnt(@Param("ano") Long ano, @Param("amount") int amount); // Ư�� �Խñ��� ��ۼ� ���� | amount�� ��������(+1, -1 ��)_reply���, reply���� 
	
	//����
	public int delete(Long ano); // Ư�� �Խñ� ����
}
