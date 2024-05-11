package org.project.mapper.User;

import java.util.List;

import org.project.domain.User.ProfileImageVO;

public interface ProfileImageMapper {
	//���
	public void insert(ProfileImageVO profile);
	//��ȸ
	public ProfileImageVO getProfileByUserid(String userid);
	public List<ProfileImageVO> getOldFiles(); // �Ϸ��� ÷�����ϸ�� ��ȸ | �� �� ������ ����
	//����
	public int update(ProfileImageVO profile);	
	//����
	public int delete(String userid);	
}
