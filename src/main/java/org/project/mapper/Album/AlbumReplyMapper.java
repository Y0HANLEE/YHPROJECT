package org.project.mapper.Album;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyVO;

public interface AlbumReplyMapper {	
	//���
	public int insert(AlbumReplyVO reply); // �� ��� ��� 
	
	//��ȸ
	public AlbumReplyVO read(Long rno); // Ư�� ��� ��ȸ 
	public List<AlbumReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("ano") Long ano); // Ư�� �Խñۿ� �޸� ����� ��� ��ȸ | ����¡ó���� ���� Criteria���
	public int getCountByAno(Long ano); // Ư�� �Խñۿ� �޸� ��� ���� �ľ�

	//����
	public int update(AlbumReplyVO reply); // Ư�� ��� ����
	
	//����
	public int delete(Long rno); // Ư�� ��� ����
}

